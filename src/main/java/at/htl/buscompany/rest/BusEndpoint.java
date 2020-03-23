package at.htl.buscompany.rest;

import at.htl.buscompany.business.InitBean;
import at.htl.buscompany.model.Bus;

import javax.decorator.Delegate;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.util.List;

@Path("bus")
public class BusEndpoint {

    @Inject
    EntityManager em;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bus> getBuses()
    {
        return em.createNamedQuery("Bus.findAll", Bus.class).getResultList();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{name}")
    public Bus getBusByDriverName(@PathParam("name") String driverName) {
        return em.createNamedQuery("Bus.findByDriverName", Bus.class)
                .setParameter("DRIVERNAME", driverName).getSingleResult();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Bus getBus(@PathParam("id") long id) {
        return em.find(Bus.class, id);
    }

    @POST
    public void postBus(Bus bus) {
        em.persist(bus);
    }

    @PUT
    @Path("{id}")
    public void putBus(@PathParam("id") long id, Bus newBus)
    {
        Bus bus = em.find(Bus.class, id);
        if(bus != null) {
            bus.setDriverName(newBus.getDriverName());
            bus.setBusType(newBus.getBusType());
        }
        else
            em.persist(newBus);
    }

    @DELETE
    @Path("{id}")
    public void deleteBus(@PathParam("id") long id)
    {
        Bus bus = em.find(Bus.class, id);
        if(bus != null)
        {
            em.remove(bus);
        }
    }


}
