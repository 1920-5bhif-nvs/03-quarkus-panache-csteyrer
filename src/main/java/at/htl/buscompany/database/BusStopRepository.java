package at.htl.buscompany.database;

import at.htl.buscompany.model.BusStop;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class BusStopRepository implements PanacheRepository<BusStop> {

    @Transactional
    public BusStop save(BusStop busStop) {
        this.persistAndFlush(busStop);
        return this.findById(busStop.getId());
    }

    public List<BusStop> getAll() {
        return this.listAll();
    }
}