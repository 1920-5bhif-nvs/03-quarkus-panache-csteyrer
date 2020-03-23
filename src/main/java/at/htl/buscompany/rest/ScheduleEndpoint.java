package at.htl.buscompany.rest;

import at.htl.buscompany.database.BusRepository;
import at.htl.buscompany.database.BusStopRepository;
import at.htl.buscompany.database.ScheduleRepository;
import at.htl.buscompany.model.Bus;
import at.htl.buscompany.model.BusStop;
import at.htl.buscompany.model.Schedule;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("schedule")
public class ScheduleEndpoint {

    @Inject
    ScheduleRepository scheduleRepository;
    @Inject
    BusRepository busRepository;
    @Inject
    BusStopRepository busStopRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Schedule getSchedule(@PathParam("id") long id) {
        return scheduleRepository.findById(id);
    }

    @POST
    @Path("{busId}/{busStopId}")
    public Response postSchedule(@PathParam("busId") long busId, @PathParam("busStopId") long busStopId,Schedule schedule) {
        Bus bus = busRepository.findById(busId);
        BusStop busStop = busStopRepository.findById(busStopId);
        if(bus == null || busStop == null) return Response.status(404).build();

        schedule.setBus(bus);
        schedule.setBusStop(busStop);
        bus.setSchedule(schedule);
        busStop.setSchedule(schedule);
        scheduleRepository.persist(schedule);


        return Response.noContent().build();
    }

    @PUT
    @Path("{busId}/{busStopId}/{id}")
    public Response putSchedule(@PathParam("busId") long busId, @PathParam("busStopId") long busStopId, @PathParam("id") long id, Schedule newSchedule)
    {
        Bus bus = busRepository.findById(busId);
        BusStop busStop = busStopRepository.findById(busStopId);
        if(bus == null || busStop == null) return Response.status(404).build();

        Schedule schedule = scheduleRepository.findById(id);
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
            scheduleRepository.persist(newSchedule);
        }

        return Response.noContent().build();
    }

    @DELETE
    @Path("{id}")
    public void deleteSchedule(@PathParam("id") long id)
    {
        Schedule schedule = scheduleRepository.findById(id);
        if(schedule != null)
        {
            scheduleRepository.delete(schedule);
        }
    }
}
