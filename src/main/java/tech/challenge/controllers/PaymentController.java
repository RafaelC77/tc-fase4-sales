package tech.challenge.controllers;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import tech.challenge.entities.PaymentRequest;
import tech.challenge.entities.PaymentResponse;
import tech.challenge.entities.PaymentWebhookRequest;
import tech.challenge.services.PaymentService;


@Path("/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentController {

    @Inject
    PaymentService paymentService;

    private static final Logger LOG = Logger.getLogger(PaymentController.class);

    @POST
    @Path("/create-payment")
    @Transactional
    public Response createPaymentPreference(PaymentRequest paymentRequest) {
        try {
            PaymentResponse paymentResponse = paymentService.createPayment(paymentRequest);
            return Response.ok(paymentResponse).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("/webhook")
    @Transactional
    public Response paymentWebhook(PaymentWebhookRequest webhookRequest) {
        try {
            paymentService.processPaymentWebhook(webhookRequest);
        } catch (Exception e) {
            LOG.error("Erro ao processar webhook: " + e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao processar webhook: " + e.getMessage())
                    .build();
        }

        return Response.ok().build();
    }

}