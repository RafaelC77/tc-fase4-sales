package tech.challenge.controllers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import tech.challenge.entities.PaymentRequest;
import tech.challenge.entities.PaymentWebhookRequest;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class PaymentControllerTest {

    private static final String VALID_PAYER_NAME = "João Silva";
    private static final String VALID_PAYER_EMAIL = "joao.silva@email.com";
    private static final String VALID_ITEM_ID = "item-123";
    private static final String VALID_ITEM_TITLE = "Carro Toyota Corolla";
    private static final BigDecimal VALID_ITEM_PRICE = new BigDecimal("50000.00");
    private static final String VALID_SALE_ID = "7bc672a8-7296-468a-a305-7bb97058f3b0";

    private static final String VALID_PAYMENT_ID = "12345678901";
    private static final String VALID_WEBHOOK_ACTION = "payment.updated";
    private static final String VALID_WEBHOOK_TYPE = "payment";

    @Test
    public void testCreatePaymentPreferenceEndpoint() {
        PaymentRequest paymentRequest = createValidPaymentRequest();

        given()
                .contentType("application/json")
                .body(paymentRequest)
                .when()
                .post("/payments/create-payment")
                .then()
                .statusCode(200)
                .contentType("application/json");
    }

    @Test
    public void testCreatePaymentPreferenceWithInvalidPrice() {
        PaymentRequest paymentRequest = createInvalidPricePaymentRequest();

        given()
                .contentType("application/json")
                .body(paymentRequest)
                .when()
                .post("/payments/create-payment")
                .then()
                .statusCode(500);
    }

    @Test
    public void testCreatePaymentPreferenceWithInvalidPayerEmail() {
        PaymentRequest paymentRequest = createInvalidPayerEmailPaymentRequest();

        given()
                .contentType("application/json")
                .body(paymentRequest)
                .when()
                .post("/payments/create-payment")
                .then()
                .statusCode(500);
    }

    @Test
    public void testCreatePaymentWithInvalidItemTitle() {
        PaymentRequest paymentRequest = createInvalidItemTitlePaymentRequest();

        given()
                .contentType("Application/json")
                .body(paymentRequest)
                .when()
                .post("/payments/create-payment")
                .then()
                .statusCode(500);
    }

    private PaymentRequest createValidPaymentRequest() {
        PaymentRequest.Payer payer = new PaymentRequest.Payer(VALID_PAYER_NAME, VALID_PAYER_EMAIL);
        PaymentRequest.Item item = new PaymentRequest.Item(VALID_ITEM_ID, VALID_ITEM_TITLE, VALID_ITEM_PRICE);
        return new PaymentRequest(payer, item, VALID_SALE_ID);
    }

    private PaymentRequest createInvalidPricePaymentRequest() {
        PaymentRequest.Payer payer = new PaymentRequest.Payer(VALID_PAYER_NAME, VALID_PAYER_EMAIL);
        PaymentRequest.Item item = new PaymentRequest.Item(VALID_ITEM_ID, VALID_ITEM_TITLE, BigDecimal.valueOf(-1));
        return new PaymentRequest(payer, item, VALID_SALE_ID);
    }

    private PaymentRequest createInvalidPayerEmailPaymentRequest() {
        PaymentRequest.Payer payer = new PaymentRequest.Payer(VALID_PAYER_NAME, "");
        PaymentRequest.Item item = new PaymentRequest.Item(VALID_ITEM_ID, VALID_ITEM_TITLE, VALID_ITEM_PRICE);
        return new PaymentRequest(payer, item, VALID_SALE_ID);
    }

    private PaymentRequest createInvalidItemTitlePaymentRequest() {
        PaymentRequest.Payer payer = new PaymentRequest.Payer(VALID_PAYER_NAME, VALID_PAYER_EMAIL);
        PaymentRequest.Item item = new PaymentRequest.Item(VALID_ITEM_ID, "", VALID_ITEM_PRICE);
        return new PaymentRequest(payer, item, VALID_SALE_ID);
    }

    private PaymentWebhookRequest createValidPaymentWebhookRequest () {
        PaymentWebhookRequest.Data data = new PaymentWebhookRequest.Data(VALID_PAYMENT_ID);
        return new PaymentWebhookRequest(
                VALID_WEBHOOK_ACTION,
                "v1",
                data,
                "2023-09-29T19:10:21.534Z",
                "webhook-123",
                false,
                VALID_WEBHOOK_TYPE,
                123456L
        );
    }
}
