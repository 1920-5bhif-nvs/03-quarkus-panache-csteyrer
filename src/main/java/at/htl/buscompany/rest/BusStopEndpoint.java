package at.htl.buscompany.rest;

import at.htl.buscompany.model.Bus;
import at.htl.buscompany.model.BusStop;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("busStop")
public class BusStopEndpoint {

    @Inject
    EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public BusStop getBusStop(@PathParam("id") long id) {
        return em.find(BusStop.class, id);
    }

    @POST
    public void postBusStop(BusStop busStop) {
        em.persist(busStop);
    }

    @PUT
    @Path("{id}")
    public void putBusStop(@PathParam("id") long id, BusStop newBusStop)
    {
        BusStop busStop = em.find(BusStop.class, id);
        if(busStop != null) {
            busStop.setBusStopName(newBusStop.getBusStopName());
        }
        else
            em.persist(newBusStop);
    }

    @DELETE
    @Path("{id}")
    public void deleteBusStop(@PathParam("id") long id)
    {
        BusStop busStop = em.find(BusStop.class, id);
        if(busStop != null)
        {
            em.remove(busStop);
        }
    }
}
