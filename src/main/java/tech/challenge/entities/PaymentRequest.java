package tech.challenge.entities;

import java.math.BigDecimal;

public record PaymentRequest(
        Payer payer,
        Item item,
        String saleId
) {
    public record Payer(
            String name,
            String email
    ) {}

    public record Item(
            String id,
            String title,
            BigDecimal price
    ) {}
}


