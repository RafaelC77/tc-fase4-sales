package tech.challenge.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class SellCarDto {

    @JsonProperty("carId")
    private String carId;

    @JsonProperty("saleDate")
    private LocalDateTime saleDate;

    public SellCarDto() {}

    public SellCarDto(String carId, LocalDateTime saleDate) {
        this.carId = carId;
        this.saleDate = saleDate;
    }

    public String getCarId() { return carId; }
    public void setCarId(String carId) { this.carId = carId; }

    public LocalDateTime getSaleDate() { return saleDate; }
    public void setSaleDate(LocalDateTime saleDate) { this.saleDate = saleDate; }

    @Override
    public String toString() {
        return String.format("CarSalesDto{carId='%s', saleDate='%s'}",
                carId, saleDate);
    }
}