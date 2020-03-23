package at.htl.buscompany.rest;

import at.htl.buscompany.database.BusRepository;
import at.htl.buscompany.model.Bus;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("bus")
public class BusEndpoint {

    @Inject
    BusRepository busRepository;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bus> getBuses()
    {
        return busRepository.getAll();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Bus getBus(@PathParam("id") long id) {
        return busRepository.getById(id);
    }

    @POST
    public void postBus(Bus bus) {
        busRepository.persist(bus);
    }

    @PUT
    @Path("{id}")
    public void putBus(@PathParam("id") long id, Bus newBus)
    {
        Bus bus = busRepository.findById(id);
        if(bus != null) {
            bus.setDriverName(newBus.getDriverName());
            bus.setBusType(newBus.getBusType());
        }
        else
            busRepository.persist(newBus);
    }

    @DELETE
    @Path("{id}")
    public void deleteBus(@PathParam("id") long id)
    {
        Bus bus = busRepository.findById(id);
        if(bus != null)
        {
            busRepository.delete(bus);
        }
    }


}
