package at.htl.buscompany.rest;

import at.htl.buscompany.model.Bus;
import at.htl.buscompany.model.BusStop;
import at.htl.buscompany.model.Schedule;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;
import java.util.List;

@Path("schedule")
public class ScheduleEndpoint {

    @Inject
    EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Schedule getSchedule(@PathParam("id") long id) {
        return em.find(Schedule.class, id);
    }

    @POST
    @Path("{busId}/{busStopId}")
    public Response postSchedule(@PathParam("busId") long busId, @PathParam("busStopId") long busStopId,Schedule schedule) {
        Bus bus = em.find(Bus.class, busId);
        BusStop busStop = em.find(BusStop.class, busStopId);
        if(bus == null || busStop == null) return Response.status(404).build();

        schedule.setBus(bus);
        schedule.setBusStop(busStop);
        bus.setSchedule(schedule);
        busStop.setSchedule(schedule);
        em.persist(schedule);


        return Response.noContent().build();
    }

    @PUT
    @Path("{busId}/{busStopId}/{id}")
    public Response putSchedule(@PathParam("busId") long busId, @PathParam("busStopId") long busStopId, @PathParam("id") long id, Schedule newSchedule)
    {
        Bus bus = em.find(Bus.class, busId);
        BusStop busStop = em.find(BusStop.class, busStopId);
        if(bus == null || busStop == null) return Response.status(404).build();

        Schedule schedule = em.find(Schedule.class, id);
        if(schedule != null) {
            schedule.getBus().removeSchedule(schedule);
            schedule.getBusStop().removeSchedule(schedule);
            schedule.setBus(bus);
            schedule.setBusStop(busStop);
            schedule.setStopTime(newSchedule.getStopTime());
            bus.setSchedule(schedule);
            busStop.setSchedule(schedule);
        }
        else {
            newSchedule.setBus(bus);
            newSchedule.setBusStop(busStop);
            bus.setSchedule(newSchedule);
            busStop.setSchedule(newSchedule);
            em.persist(newSchedule);
        }

        return Response.noContent().build();
    }

    @DELETE
    @Path("{id}")
    public void deleteSchedule(@PathParam("id") long id)
    {
        Schedule schedule = em.find(Schedule.class, id);
        if(schedule != null)
        {
            em.remove(schedule);
        }
    }
}
