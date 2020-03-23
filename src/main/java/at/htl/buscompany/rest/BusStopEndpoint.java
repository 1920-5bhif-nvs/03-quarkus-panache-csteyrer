package at.htl.buscompany.rest;

import at.htl.buscompany.database.BusStopRepository;
import at.htl.buscompany.model.BusStop;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("busStop")
public class BusStopEndpoint {

    @Inject
    BusStopRepository busStopRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public BusStop getBusStop(@PathParam("id") long id) {
        return busStopRepository.findById(id);
    }

    @POST
    public void postBusStop(BusStop busStop) {
        busStopRepository.persist(busStop);
    }

    @PUT
    @Path("{id}")
    public void putBusStop(@PathParam("id") long id, BusStop newBusStop)
    {
        BusStop busStop = busStopRepository.findById(id);
        if(busStop != null) {
            busStop.setBusStopName(newBusStop.getBusStopName());
        }
        else
            busStopRepository.persist(newBusStop);
    }

    @DELETE
    @Path("{id}")
    public void deleteBusStop(@PathParam("id") long id)
    {
        BusStop busStop = busStopRepository.findById(id);
        if(busStop != null)
        {
            busStopRepository.delete(busStop);
        }
    }
}
