package tech.challenge.controllers;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

import tech.challenge.entities.CarReadModelEntity;
import tech.challenge.services.CarsReadModelService;

@Path("/cars")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarController {

    @Inject
    CarsReadModelService service;

    @GET
    public Response listAll() {
        var carList = service.findAll();
        return Response.ok(carList).build();
    }

    @GET
    @Path("available")
    public Response listAvailable() {
        List<CarReadModelEntity> cars = service.findAllAvailable();
        return Response.status(Response.Status.OK).entity(cars).build();
    }

    @GET
    @Path("sold")
    public Response listSold() {
        List<CarReadModelEntity> cars = service.findAllSold();
        return Response.status(Response.Status.OK).entity(cars).build();
    }

    @POST
    @Path("save")
    @Transactional
    public Response save(CarReadModelEntity car) {
        service.save(car);
        return Response.ok().build();
    }

    @PUT
    @Path("update")
    @Transactional
    public Response update(CarReadModelEntity car) {
        service.update(car);
        return Response.ok().build();
    }
}