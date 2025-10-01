package tech.challenge.entities;

public record PaymentResponse(
        String preferenceId,
        String redirectionUrl
) {
}
