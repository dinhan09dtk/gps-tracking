package com.karros.challenge.domain;



import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.List;

@Entity
@Table(name = "trip")
public class Trip extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = true, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "metadata_id")
    private Metadata metadata;

    @Column(columnDefinition = "TEXT")
    private String extensions;
    private String version;
    private String creator;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WayPoint> wpt;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Route> rte;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Track> trk;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<WayPoint> getWpt() {
        return wpt;
    }

    public void setWpt(List<WayPoint> wpt) {
        this.wpt = wpt;
    }

    public List<Route> getRte() {
        return rte;
    }

    public void setRte(List<Route> rte) {
        this.rte = rte;
    }

    public List<Track> getTrk() {
        return trk;
    }

    public void setTrk(List<Track> trk) {
        this.trk = trk;
    }
}
