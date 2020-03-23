package at.htl.buscompany.business;


import at.htl.buscompany.model.*;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@ApplicationScoped
public class InitBean {

    @Inject
    EntityManager em;

    @Transactional
    public void init(@Observes StartupEvent init)
    {
        for (int i = 1; i <= 10; i++) {
            Bus bus = new Bus("Typ"+i, "Name"+i);
            BusStop busStop = new BusStop("Busstop"+i);
            Schedule s1 = new Schedule(bus, busStop, LocalDateTime.of(2018, 12, 1, 10, 5));
            bus.setSchedule(s1);
            busStop.setSchedule(s1);
            Ticket t1 = new Ticket("TimeTicket"+i, 5, 0, 5, LocalDateTime.of(2018, 12, 1, 10, 5), busStop);

            t1.setBus(bus);


            em.persist(bus);
            em.persist(busStop);
            em.persist(s1);
            em.persist(t1);
        }
    }
}
