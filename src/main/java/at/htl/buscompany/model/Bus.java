package at.htl.buscompany.model;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
    @NamedQuery(name = "Bus.findAll", query = "select b from Bus b"),
    @NamedQuery(name = "Bus.findByDriverName", query = "select b from Bus b where b.driverName = :DRIVERNAME")
})
public class Bus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String driverName;
    private String busType;
    @OneToMany(mappedBy = "bus", cascade =
            {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "bus")
    @JsonbTransient
    private List<Schedule> scheduleList;

    //region Constructor
    public Bus() {
        tickets = new ArrayList<>();
        scheduleList = new ArrayList<>();
    }

    public Bus(String driverName, String busType) {
        this();
        this.driverName = driverName;
        this.busType = busType;
    }
    //endregion

    public void addTicket(Ticket ticket)
    {
        if(ticket != null)
            this.tickets.add(ticket);
    }

    public void removeTicket(Ticket ticket)
    {
        if(ticket != null)
            this.tickets.remove(ticket);
    }

    public void removeSchedule(Schedule schedule)
    {
        if(schedule != null)
            this.scheduleList.remove(schedule);
    }

    //region Getter and Setter
    public Long getId() {
        return id;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setSchedule(Schedule schedule) {
        scheduleList.add(schedule);
    }

    //endregion
}
