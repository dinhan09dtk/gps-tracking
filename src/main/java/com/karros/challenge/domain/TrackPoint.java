package com.karros.challenge.domain;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity(name = "track_point")
public class TrackPoint extends AbstractPoint implements Serializable {

    @ManyToOne
    @JoinColumn(name = "track_seg_id")
    private TrackSegment trackSegment;

    public TrackSegment getTrackSegment() {
        return trackSegment;
    }

    public void setTrackSegment(TrackSegment trackSegment) {
        this.trackSegment = trackSegment;
    }
}
