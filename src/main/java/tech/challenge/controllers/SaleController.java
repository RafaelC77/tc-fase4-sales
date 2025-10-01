package tech.challenge.controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.UUID;

import tech.challenge.entities.SaleEntity;
import tech.challenge.entities.SaleRequest;
import tech.challenge.services.SaleService;

@Path("/sales")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @POST
    public Response sellCar(SaleRequest saleRequest) {
        try {
            var sale = saleService.sellCar(saleRequest);
            return Response.status(Response.Status.CREATED)
                    .entity(sale)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    public Response findAllSales() {
            var sales = saleService.findAllSales();
            return Response.status(Response.Status.OK)
                    .entity(sales)
                    .build();
    }
}
