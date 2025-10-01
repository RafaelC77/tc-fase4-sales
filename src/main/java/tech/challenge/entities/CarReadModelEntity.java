package tech.challenge.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import tech.challenge.enums.CarStatus;

@Entity
@Table(name = "sales_cars")
public class CarReadModelEntity extends PanacheEntityBase {

    @Id
    @Column(nullable = false)
    private String carId;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String brand;

    @Column(name = "car_year", nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20)")
    private CarStatus status = CarStatus.AVAILABLE;

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setBrand(String brand) { this.brand = brand; }

    public void setYear(Integer year) { this.year = year; }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setStatus(CarStatus status) { this.status = status; }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

    public Integer getYear() {
        return year;
    }

    public Double getPrice() {
        return price;
    }

    public CarStatus getStatus() {
        return status;
    }
}
