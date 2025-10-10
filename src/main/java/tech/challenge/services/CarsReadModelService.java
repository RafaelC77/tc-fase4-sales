package tech.challenge.services;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

import tech.challenge.entities.CarReadModelEntity;
import tech.challenge.enums.CarStatus;

@ApplicationScoped
public class CarsReadModelService {

    public void save(CarReadModelEntity car) {
        car.persist();
    }

    public void update(CarReadModelEntity car) {
        CarReadModelEntity existingCar = CarReadModelEntity.findById(car.getCarId());
        if (existingCar != null) {
            existingCar.setModel(car.getModel());
            existingCar.setBrand(car.getBrand());
            existingCar.setPrice(car.getPrice());
            existingCar.setYear(car.getYear());
        }
    }

    public List<CarReadModelEntity> findAll() {
        return CarReadModelEntity.listAll();
    }

    public List<CarReadModelEntity> findAllAvailable() {
        return CarReadModelEntity.list("status = ?1 order by price asc", CarStatus.AVAILABLE);
    }

    public List<CarReadModelEntity> findAllSold() {
        return CarReadModelEntity.list("status = ?1 order by price asc", CarStatus.SOLD);
    }
}
