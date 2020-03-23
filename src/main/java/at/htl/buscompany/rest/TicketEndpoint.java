package at.htl.buscompany.rest;

import at.htl.buscompany.model.Bus;
import at.htl.buscompany.model.Ticket;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("ticket")
public class TicketEndpoint {
    @Inject
    EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Ticket getTicket(@PathParam("id") long id) {
        return em.find(Ticket.class, id);
    }

    @POST
    @Path("{busId}")
    public Response postTicket(@PathParam("busId") long busId, Ticket ticket) {
        Bus bus = em.find(Bus.class, busId);
        if(bus == null) return Response.status(404).build();

        ticket.setBus(bus);
        bus.addTicket(ticket);
        em.persist(ticket);

        return Response.noContent().build();
    }

    @PUT
    @Path("{busId}/{id}")
    public Response putTicket(@PathParam("busId") long busId, @PathParam("id") long id, Ticket newTicket)
    {
        Bus bus = em.find(Bus.class, busId);
        if(bus == null) return Response.status(404).build();

        Ticket ticket = em.find(Ticket.class, id);
        if(ticket != null) {
            ticket.getBus().removeTicket(ticket);
            bus.addTicket(ticket);
            ticket.setPrice(newTicket.getPrice());
            ticket.setStops(newTicket.getStops());
        }
        else {
            newTicket.setBus(bus);
            bus.addTicket(newTicket);
            em.persist(newTicket);
        }

        return Response.noContent().build();
    }

    @DELETE
    @Path("{id}")
    public void deleteTicket(@PathParam("id") long id) {
        Ticket routeTicket = em.find(Ticket.class, id);
        if(routeTicket != null) {
            routeTicket.getBus().removeTicket(routeTicket);
            em.remove(routeTicket);
        }
    }
}
