package tech.challenge.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentStatusMPTest {

    @Test
    void shouldContainAllValuesInExpectedOrder() {
        PaymentStatusMP[] expected = new PaymentStatusMP[] {
                PaymentStatusMP.PENDING,
                PaymentStatusMP.APPROVED,
                PaymentStatusMP.AUTHORIZED,
                PaymentStatusMP.IN_PROCESS,
                PaymentStatusMP.IN_MEDIATION,
                PaymentStatusMP.REJECTED,
                PaymentStatusMP.CANCELLED,
                PaymentStatusMP.REFUNDED,
                PaymentStatusMP.CHARGED_BACK,
                PaymentStatusMP.UNKNOWN
        };

        assertArrayEquals(expected, PaymentStatusMP.values(),
                "Os valores do enum PaymentStatusMP mudaram (lista e/ou ordem).");
    }

    @Test
    void valueOfShouldReturnCorrespondingEnumForEachName() {
        for (PaymentStatusMP status : PaymentStatusMP.values()) {
            assertSame(status, PaymentStatusMP.valueOf(status.name()),
                    "valueOf deve retornar a mesma constante para o nome " + status.name());
        }
    }

    @Test
    void toStringShouldMatchNameForEachConstant() {
        for (PaymentStatusMP status : PaymentStatusMP.values()) {
            assertEquals(status.name(), status.toString(),
                    "toString deve ser igual ao name() para " + status.name());
        }
    }

}