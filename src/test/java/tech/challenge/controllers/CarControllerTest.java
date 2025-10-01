package tech.challenge.controllers;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class CarControllerTest {

    @Test
    public void testListCarsEndpoint() {
        given()
                .when().get("/cars")
                .then()
                .statusCode(200);
    }

    @Test
    public void testListAvailableCars() {
        given()
                .when().get("cars/available")
                .then()
                .statusCode(200);
    }

    @Test
    public void testListSoldCars() {
        given()
                .when().get("cars/sold")
                .then()
                .statusCode(200);
    }
}
