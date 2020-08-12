package com.karros.challenge.domain;



import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "way_point")
public class WayPoint extends AbstractPoint implements Serializable {
    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
