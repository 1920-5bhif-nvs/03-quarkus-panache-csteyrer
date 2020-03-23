package at.htl.buscompany.database;

import at.htl.buscompany.model.Bus;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class BusRepository implements PanacheRepository<Bus> {

    @Inject
    EntityManager em;

    @Transactional
    public Bus save(Bus bus) {
        this.persistAndFlush(bus);
        return this.findById(bus.getId());
    }

    public List<Bus> getAll() {
        return this.listAll();
    }

    public Bus getById(long id) {
        EntityGraph eg = em.getEntityGraph("bus-entity-graph");
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", eg);
        return em.find(Bus.class, id, properties);
    }
}

