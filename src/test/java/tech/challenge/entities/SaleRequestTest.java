package tech.challenge.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SaleRequestTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void defaultConstructor_shouldInitializeNulls() {
        SaleRequest req = new SaleRequest();
        assertNull(req.getCarId());
        assertNull(req.getBuyerCpf());
        assertNull(req.getBuyerName());
        assertNull(req.getBuyerEmail());
        assertNull(req.getBuyerPhone());
    }

    @Test
    void allArgsConstructor_shouldSetFields() {
        SaleRequest req = new SaleRequest(
                "CAR-001",
                "12345678900",
                "Maria Silva",
                "maria@example.com",
                "+55 11 99999-0000"
        );

        assertEquals("CAR-001", req.getCarId());
        assertEquals("12345678900", req.getBuyerCpf());
        assertEquals("Maria Silva", req.getBuyerName());
        assertEquals("maria@example.com", req.getBuyerEmail());
        assertEquals("+55 11 99999-0000", req.getBuyerPhone());
    }

    @Test
    void gettersAndSetters_shouldWork() {
        SaleRequest req = new SaleRequest();

        req.setCarId("CAR-XYZ");
        req.setBuyerCpf("98765432100");
        req.setBuyerName("João Souza");
        req.setBuyerEmail("joao@example.com");
        req.setBuyerPhone("+55 21 98888-7777");

        assertEquals("CAR-XYZ", req.getCarId());
        assertEquals("98765432100", req.getBuyerCpf());
        assertEquals("João Souza", req.getBuyerName());
        assertEquals("joao@example.com", req.getBuyerEmail());
        assertEquals("+55 21 98888-7777", req.getBuyerPhone());
    }

    @Test
    void jacksonSerialization_shouldUseFieldNames() throws JsonProcessingException {
        SaleRequest req = new SaleRequest(
                "CAR-777",
                "11122233344",
                "Alice",
                "alice@example.com",
                "11999990000"
        );

        String json = mapper.writeValueAsString(req);

        assertTrue(json.contains("\"carId\":\"CAR-777\""));
        assertTrue(json.contains("\"buyerCpf\":\"11122233344\""));
        assertTrue(json.contains("\"buyerName\":\"Alice\""));
        assertTrue(json.contains("\"buyerEmail\":\"alice@example.com\""));
        assertTrue(json.contains("\"buyerPhone\":\"11999990000\""));
    }

    @Test
    void jacksonDeserialization_shouldMapFields() throws JsonProcessingException {
        String json = "{"
                + "\"carId\":\"CAR-888\","
                + "\"buyerCpf\":\"55566677788\","
                + "\"buyerName\":\"Bruno\","
                + "\"buyerEmail\":\"bruno@example.com\","
                + "\"buyerPhone\":\"21911112222\""
                + "}";

        SaleRequest req = mapper.readValue(json, SaleRequest.class);

        assertEquals("CAR-888", req.getCarId());
        assertEquals("55566677788", req.getBuyerCpf());
        assertEquals("Bruno", req.getBuyerName());
        assertEquals("bruno@example.com", req.getBuyerEmail());
        assertEquals("21911112222", req.getBuyerPhone());
    }
}