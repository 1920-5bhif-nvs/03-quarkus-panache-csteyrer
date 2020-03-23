package at.htl.buscompany.database;

import at.htl.buscompany.model.BusStop;
import at.htl.buscompany.model.Schedule;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ScheduleRepository implements PanacheRepository<Schedule> {

    @Transactional
    public Schedule save(Schedule schedule) {
        this.persistAndFlush(schedule);
        return this.findById(schedule.getId());
    }

    public List<Schedule> getAll() {
        return this.listAll();
    }
}