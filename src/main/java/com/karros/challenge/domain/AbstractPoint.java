package com.karros.challenge.domain;



import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ConfirmationCodeGenerator")
    @TableGenerator(table = "sequences", name = "ConfirmationCodeGenerator")
    private Long id;
    private BigDecimal ele;
    private Date time;
    private BigDecimal magvar;
    private BigDecimal geoidheight;
    private String name;
    private String cmt;
    @Column(name = "`desc`", columnDefinition = "TEXT")
    private String desc;
    private String src;
    @Column(columnDefinition = "TEXT")
    private String link;
    private String sym;
    private String type;
    private String fix;
    @Min(value = 0)
    private BigInteger sat;
    private BigDecimal hdop;
    private BigDecimal vdop;
    private BigDecimal pdop;
    private BigDecimal ageofdgpsdata;
    private Integer dgpsid;
    @Column(columnDefinition = "TEXT")
    private String extensions;
    private BigDecimal lat;
    private BigDecimal lon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getEle() {
        return ele;
    }

    public void setEle(BigDecimal ele) {
        this.ele = ele;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public BigDecimal getMagvar() {
        return magvar;
    }

    public void setMagvar(BigDecimal magvar) {
        this.magvar = magvar;
    }

    public BigDecimal getGeoidheight() {
        return geoidheight;
    }

    public void setGeoidheight(BigDecimal geoidheight) {
        this.geoidheight = geoidheight;
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

    public String getSym() {
        return sym;
    }

    public void setSym(String sym) {
        this.sym = sym;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFix() {
        return fix;
    }

    public void setFix(String fix) {
        this.fix = fix;
    }

    public BigInteger getSat() {
        return sat;
    }

    public void setSat(BigInteger sat) {
        this.sat = sat;
    }

    public BigDecimal getHdop() {
        return hdop;
    }

    public void setHdop(BigDecimal hdop) {
        this.hdop = hdop;
    }

    public BigDecimal getVdop() {
        return vdop;
    }

    public void setVdop(BigDecimal vdop) {
        this.vdop = vdop;
    }

    public BigDecimal getPdop() {
        return pdop;
    }

    public void setPdop(BigDecimal pdop) {
        this.pdop = pdop;
    }

    public BigDecimal getAgeofdgpsdata() {
        return ageofdgpsdata;
    }

    public void setAgeofdgpsdata(BigDecimal ageofdgpsdata) {
        this.ageofdgpsdata = ageofdgpsdata;
    }

    public Integer getDgpsid() {
        return dgpsid;
    }

    public void setDgpsid(Integer dgpsid) {
        this.dgpsid = dgpsid;
    }

    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }
}
