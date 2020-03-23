package at.htl.buscompany.database;

import at.htl.buscompany.model.Schedule;
import at.htl.buscompany.model.Ticket;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class TicketRepository implements PanacheRepository<Ticket> {

    @Transactional
    public Ticket save(Ticket ticket) {
        this.persistAndFlush(ticket);
        return this.findById(ticket.getId());
    }

    public List<Ticket> getAll() {
        return this.listAll();
    }
}