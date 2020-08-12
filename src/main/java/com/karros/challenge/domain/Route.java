package com.karros.challenge.domain;



import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.List;

@Entity
@Table(name = "route")
public class Route extends AbstractRoute implements Serializable {

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoutePoint> rtept;

    public List<RoutePoint> getRtept() {
        return rtept;
    }

    public void setRtept(List<RoutePoint> rtept) {
        this.rtept = rtept;
    }
}
