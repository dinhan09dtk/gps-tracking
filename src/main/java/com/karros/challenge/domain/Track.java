package com.karros.challenge.domain;


import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.List;

@Entity(name = "track")
public class Track extends AbstractRoute implements Serializable {
    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrackSegment> trkseg;

    public List<TrackSegment> getTrkseg() {
        return trkseg;
    }

    public void setTrkseg(List<TrackSegment> trkseg) {
        this.trkseg = trkseg;
    }
}
