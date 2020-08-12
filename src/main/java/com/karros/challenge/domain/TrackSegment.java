package com.karros.challenge.domain;



import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "track_segment")
public class TrackSegment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "trackSegment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrackPoint> trkpt;
    @Column(columnDefinition = "TEXT")
    private String extensions;

    @ManyToOne
    @JoinColumn(name = "track_id")
    private Track track;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TrackPoint> getTrkpt() {
        return trkpt;
    }

    public void setTrkpt(List<TrackPoint> trkpt) {
        this.trkpt = trkpt;
    }

    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }
}
