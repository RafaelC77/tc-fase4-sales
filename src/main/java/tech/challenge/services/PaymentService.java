package tech.challenge.services;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import tech.challenge.entities.*;
import tech.challenge.enums.CarStatus;
import tech.challenge.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import java.util.UUID;

@ApplicationScoped
public class PaymentService {

    @ConfigProperty(name = "mercado-pago-access-token")
    String accessToken;

    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        MercadoPagoConfig.setAccessToken(accessToken);

        PreferenceClient client = new PreferenceClient();

        if (paymentRequest.item().price().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
        if (paymentRequest.payer().email().isEmpty()) {
            throw new IllegalArgumentException("Email do pagador é obrigatório");
        }
        if (paymentRequest.item().title().isEmpty()) {
            throw new IllegalArgumentException("Título do item é obrigatório");
        }

        PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                .title(paymentRequest.item().title())
                .quantity(1)
                .currencyId("BRL")
                .unitPrice(paymentRequest.item().price())
                .build();

        PreferencePayerRequest payerRequest = PreferencePayerRequest.builder()
                .name(paymentRequest.payer().name())
                .email(paymentRequest.payer().email())
                .build();

        String saleId = paymentRequest.saleId();

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("http://localhost:8080/success")
                .failure("http://localhost:8080/failure")
                .pending("http://localhost:8080/pending")
                .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(List.of(itemRequest))
                .payer(payerRequest)
                .externalReference(saleId)
                .backUrls(backUrls)
                .build();

        Preference preference;
        try {
            preference = client.create(preferenceRequest);
        } catch (MPException | MPApiException e) {
            throw new RuntimeException(e);
        }

            UUID saleUuid = UUID.fromString(paymentRequest.saleId());

            SaleEntity sale = SaleEntity.findById(saleUuid);
            sale.setPaymentId(preference.getId());

        return new PaymentResponse(preference.getId(), preference.getInitPoint());
    }

    public void processPaymentWebhook(PaymentWebhookRequest webhookRequest) throws MPException, MPApiException {

        String paymentId = webhookRequest.getData().getId();

        MercadoPagoConfig.setAccessToken(accessToken);

        PaymentClient client = new PaymentClient();

        Payment payment = client.get(Long.valueOf(paymentId));


        String saleId = payment.getExternalReference();

        UUID saleUuid = UUID.fromString(saleId);
        SaleEntity sale = SaleEntity.find("saleId = ?1", saleUuid).firstResult();

        String paymentStatus = payment.getStatus();

        if ("approved".equals(paymentStatus)) {

            sale.setPaymentStatus(PaymentStatus.PAID);
            sale.setSaleDate(LocalDateTime.now());
            sale.persist();

            CarReadModelEntity car = CarReadModelEntity.findById(sale.getCarId());
            car.setStatus(CarStatus.SOLD);

        }
    }
}
