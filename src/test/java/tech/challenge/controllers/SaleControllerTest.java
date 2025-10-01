package tech.challenge.controllers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import tech.challenge.entities.SaleEntity;
import tech.challenge.entities.SaleRequest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.smallrye.common.constraint.Assert.*;

@QuarkusTest
public class SaleControllerTest {

    @Test
    public void testSellCarEndpoint() {
        var carId = "550e8400-e29b-41d4-a716-446655440000";

        SaleRequest saleRequest = new SaleRequest();
        saleRequest.setCarId(carId);
        saleRequest.setBuyerCpf("99999999999");
        saleRequest.setBuyerName("Name");
        saleRequest.setBuyerEmail("teste@mail.com");
        saleRequest.setBuyerPhone("123456789");

        given()
                .contentType("Application/json")
                .body(saleRequest)
                .when()
                .post("/sales")
                .then()
                .statusCode(201);
    }

    @Test
    public void testFindAllSalesEndpoint() {
        List<SaleEntity> response = given()
                .when().get("/sales")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().body().jsonPath().getList(".", SaleEntity.class);

        assertFalse(response.isEmpty());
        assertNotNull(response.getFirst().getSaleId());
    }


    @Test
    public void testFindSaleByIdWithNonExistentId() {
        var saleId = java.util.UUID.randomUUID();

        given()
                .contentType("Application/json")
                .when().get("/sales/" + saleId)
                .then()
                .statusCode(404);
    }
}
