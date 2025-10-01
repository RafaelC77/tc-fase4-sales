package tech.challenge.interfaces;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import tech.challenge.dtos.SellCarDto;

@Path( "/cars")
@RegisterRestClient(configKey = "cars-api")
public interface CarsRestClient {

    @PUT()
    @Path("set-as-sold")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response sellCar(SellCarDto sellCarDto);
}
