package com.karros.challenge.domain;



import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AbstractRoute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ConfirmationCodeGenerator")
    @TableGenerator(table = "sequences", name = "ConfirmationCodeGenerator")
    private Long id;
    private String name;
    private String cmt;
    @Column(name = "`desc`", columnDefinition = "TEXT")
    private String desc;
    private String src;
    @Column(columnDefinition = "TEXT")
    private String link;
    private BigInteger number;
    private String type;
    @Column(columnDefinition = "TEXT")
    private String extensions;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCmt() {
        return cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public BigInteger getNumber() {
        return number;
    }

    public void setNumber(BigInteger number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
