package tech.challenge.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SellCarDtoTest {

    private ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Test
    void noArgsConstructorShouldInitializeNulls() {
        SellCarDto dto = new SellCarDto();
        assertNull(dto.getCarId());
        assertNull(dto.getSaleDate());
    }

    @Test
    void allArgsConstructorShouldSetFields() {
        LocalDateTime dt = LocalDateTime.of(2023, 7, 15, 10, 30, 45);
        SellCarDto dto = new SellCarDto("CAR-123", dt);

        assertEquals("CAR-123", dto.getCarId());
        assertEquals(dt, dto.getSaleDate());
    }

    @Test
    void gettersAndSettersShouldWork() {
        SellCarDto dto = new SellCarDto();
        LocalDateTime dt = LocalDateTime.now();

        dto.setCarId("ABC");
        dto.setSaleDate(dt);

        assertEquals("ABC", dto.getCarId());
        assertEquals(dt, dto.getSaleDate());
    }

    @Test
    void toStringShouldMatchExpectedFormat() {
        LocalDateTime dt = LocalDateTime.of(2024, 1, 2, 3, 4, 5);
        SellCarDto dto = new SellCarDto("X-1", dt);

        String expected = "CarSalesDto{carId='X-1', saleDate='2024-01-02T03:04:05'}";
        assertEquals(expected, dto.toString());
    }

    @Test
    void jacksonSerializationShouldUseConfiguredPropertyNames() throws JsonProcessingException {
        LocalDateTime dt = LocalDateTime.of(2023, 7, 15, 10, 30, 45);
        SellCarDto dto = new SellCarDto("CAR-123", dt);

        String json = objectMapper().writeValueAsString(dto);

        assertTrue(json.contains("\"carId\":\"CAR-123\""), "JSON deve conter a propriedade 'carId'");
        assertTrue(json.contains("\"saleDate\":\"2023-07-15T10:30:45\""), "JSON deve conter a propriedade 'saleDate' em ISO-8601");
    }

    @Test
    void jacksonDeserializationShouldMapFields() throws JsonProcessingException {
        String json = "{\"carId\":\"CAR-999\",\"saleDate\":\"2025-09-30T12:00:10\"}";

        SellCarDto dto = objectMapper().readValue(json, SellCarDto.class);

        assertEquals("CAR-999", dto.getCarId());
        assertEquals(LocalDateTime.of(2025, 9, 30, 12, 0, 10), dto.getSaleDate());
    }
}