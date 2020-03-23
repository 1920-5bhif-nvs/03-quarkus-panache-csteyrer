package at.htl.buscompany.model;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BusStop implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String busStopName;

    @OneToMany(mappedBy = "busStop")
    private List<Schedule> scheduleList;

    //region Constructor
    public BusStop() {
        scheduleList = new ArrayList<>();
    }

    public BusStop(String busStopName)
    {
        this();
        this.busStopName = busStopName;
    }
    //endregion

    public void removeSchedule(Schedule schedule)
    {
        if(schedule != null)
            this.scheduleList.remove(schedule);
    }

    //region Getter and Setter
    public Long getId() {
        return id;
    }

    public String getBusStopName() {
        return busStopName;
    }

    public void setBusStopName(String busStopName) {
        this.busStopName = busStopName;
    }
    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setSchedule(Schedule schedule) {
        scheduleList.add(schedule);
    }
//endregion
}
