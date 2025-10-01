package tech.challenge.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.challenge.entities.CarReadModelEntity;
import tech.challenge.entities.SaleEntity;
import tech.challenge.entities.SaleRequest;
import tech.challenge.enums.PaymentStatus;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class SaleService {

    private static final Logger log = LoggerFactory.getLogger(SaleService.class);

    @Transactional
    public SaleEntity sellCar(SaleRequest saleRequest) {
        String carId = saleRequest.getCarId();
        CarReadModelEntity car = CarReadModelEntity.findById(carId);
        if (car == null) {
            throw new RuntimeException();
        }

        SaleEntity sale = new SaleEntity();
        sale.setBuyerCpf(saleRequest.getBuyerCpf());
        sale.setBuyerName(saleRequest.getBuyerName());
        sale.setBuyerEmail(saleRequest.getBuyerEmail());
        sale.setBuyerPhone(saleRequest.getBuyerPhone());
        sale.setCarId(saleRequest.getCarId());
        sale.setPaymentStatus(PaymentStatus.PENDING);

        sale.persist();

        return sale;
    }

    public List<SaleEntity> findAllSales() {
        return SaleEntity.findAll().list();
    }

    public SaleEntity findSaleById(UUID id) {
        return SaleEntity.findById(id);
    }

}
