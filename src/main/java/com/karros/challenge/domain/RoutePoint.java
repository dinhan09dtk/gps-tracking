package com.karros.challenge.domain;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity(name = "route_point")
public class RoutePoint extends AbstractPoint implements Serializable {

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
