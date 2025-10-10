package tech.challenge.services;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.challenge.entities.CarReadModelEntity;
import tech.challenge.enums.CarStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class CarsReadModelServiceTest {

    @Inject
    CarsReadModelService service;

    @BeforeEach
    @Transactional
    void cleanDatabase() {
        CarReadModelEntity.deleteAll();
    }

    @Test
    @Transactional
    void save_shouldPersistEntity() {
        CarReadModelEntity car = new CarReadModelEntity();
        car.setCarId("CAR-100");
        car.setModel("Model A");
        car.setBrand("Brand A");
        car.setYear(2020);
        car.setPrice(100_000.0);
        car.setStatus(CarStatus.AVAILABLE);

        service.save(car);

        CarReadModelEntity stored = CarReadModelEntity.findById("CAR-100");
        assertNotNull(stored);
        assertEquals("Model A", stored.getModel());
        assertEquals("Brand A", stored.getBrand());
        assertEquals(2020, stored.getYear());
        assertEquals(100_000.0, stored.getPrice());
        assertEquals(CarStatus.AVAILABLE, stored.getStatus());
    }

    @Test
    @Transactional
    void update_shouldUpdateExistingEntityWhenFound() {
        // cria e persiste entidade existente
        CarReadModelEntity existing = new CarReadModelEntity();
        existing.setCarId("CAR-1");
        existing.setModel("Old Model");
        existing.setBrand("Old Brand");
        existing.setYear(2019);
        existing.setPrice(90_000.0);
        existing.setStatus(CarStatus.AVAILABLE);
        existing.persist();

        // cria DTO de entrada com mesmo id e novos dados
        CarReadModelEntity input = new CarReadModelEntity();
        input.setCarId("CAR-1");
        input.setModel("Model X");
        input.setBrand("Brand Y");
        input.setYear(2024);
        input.setPrice(123_456.78);

        service.update(input);

        CarReadModelEntity reloaded = CarReadModelEntity.findById("CAR-1");
        assertNotNull(reloaded);
        assertEquals("Model X", reloaded.getModel());
        assertEquals("Brand Y", reloaded.getBrand());
        assertEquals(2024, reloaded.getYear());
        assertEquals(123_456.78, reloaded.getPrice());
        // status não é atualizado pelo service
        assertEquals(CarStatus.AVAILABLE, reloaded.getStatus());
    }

    @Test
    @Transactional
    void update_shouldDoNothingWhenEntityNotFound() {
        // não persiste CAR-2
        CarReadModelEntity input = new CarReadModelEntity();
        input.setCarId("CAR-2");
        input.setModel("Whatever");
        input.setBrand("Whatever");
        input.setYear(2022);
        input.setPrice(10.0);

        assertDoesNotThrow(() -> service.update(input));
        assertNull(CarReadModelEntity.findById("CAR-2"));
    }

    @Test
    @Transactional
    void findAll_shouldReturnListFromEntity() {
        CarReadModelEntity c1 = new CarReadModelEntity();
        c1.setCarId("A");
        c1.setModel("M1");
        c1.setBrand("B1");
        c1.setYear(2020);
        c1.setPrice(50_000.0);
        c1.setStatus(CarStatus.AVAILABLE);
        c1.persist();

        CarReadModelEntity c2 = new CarReadModelEntity();
        c2.setCarId("B");
        c2.setModel("M2");
        c2.setBrand("B2");
        c2.setYear(2021);
        c2.setPrice(60_000.0);
        c2.setStatus(CarStatus.SOLD);
        c2.persist();

        List<CarReadModelEntity> result = service.findAll();
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(e -> "A".equals(e.getCarId())));
        assertTrue(result.stream().anyMatch(e -> "B".equals(e.getCarId())));
    }

    @Test
    @Transactional
    void findAllAvailable_shouldQueryByStatusAvailableOrderedByPriceAsc() {
        CarReadModelEntity a1 = new CarReadModelEntity();
        a1.setCarId("AV-1");
        a1.setModel("M");
        a1.setBrand("B");
        a1.setYear(2020);
        a1.setPrice(40_000.0);
        a1.setStatus(CarStatus.AVAILABLE);
        a1.persist();

        CarReadModelEntity a2 = new CarReadModelEntity();
        a2.setCarId("AV-2");
        a2.setModel("M");
        a2.setBrand("B");
        a2.setYear(2021);
        a2.setPrice(30_000.0); // menor preço
        a2.setStatus(CarStatus.AVAILABLE);
        a2.persist();

        CarReadModelEntity s1 = new CarReadModelEntity();
        s1.setCarId("SD-1");
        s1.setModel("M");
        s1.setBrand("B");
        s1.setYear(2019);
        s1.setPrice(10_000.0);
        s1.setStatus(CarStatus.SOLD);
        s1.persist();

        List<CarReadModelEntity> result = service.findAllAvailable();
        assertEquals(2, result.size());
        // deve vir ordenado por preço ascendente: AV-2 (30k), AV-1 (40k)
        assertEquals("AV-2", result.get(0).getCarId());
        assertEquals("AV-1", result.get(1).getCarId());
        assertTrue(result.stream().allMatch(e -> e.getStatus() == CarStatus.AVAILABLE));
    }

    @Test
    @Transactional
    void findAllSold_shouldQueryByStatusSoldOrderedByPriceAsc() {
        CarReadModelEntity s1 = new CarReadModelEntity();
        s1.setCarId("SO-1");
        s1.setModel("M");
        s1.setBrand("B");
        s1.setYear(2020);
        s1.setPrice(70_000.0);
        s1.setStatus(CarStatus.SOLD);
        s1.persist();

        CarReadModelEntity s2 = new CarReadModelEntity();
        s2.setCarId("SO-2");
        s2.setModel("M");
        s2.setBrand("B");
        s2.setYear(2022);
        s2.setPrice(65_000.0); // menor preço
        s2.setStatus(CarStatus.SOLD);
        s2.persist();

        CarReadModelEntity a1 = new CarReadModelEntity();
        a1.setCarId("AV-9");
        a1.setModel("M");
        a1.setBrand("B");
        a1.setYear(2018);
        a1.setPrice(20_000.0);
        a1.setStatus(CarStatus.AVAILABLE);
        a1.persist();

        List<CarReadModelEntity> result = service.findAllSold();
        assertEquals(2, result.size());
        // deve vir ordenado por preço ascendente: SO-2 (65k), SO-1 (70k)
        assertEquals("SO-2", result.get(0).getCarId());
        assertEquals("SO-1", result.get(1).getCarId());
        assertTrue(result.stream().allMatch(e -> e.getStatus() == CarStatus.SOLD));
    }
}