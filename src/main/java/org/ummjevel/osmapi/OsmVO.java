package org.ummjevel.osmapi;

import javax.annotation.processing.Generated;
import javax.persistence.*;

@Entity
@Table(name="osm")
public class OsmVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fid;
    private Long osm_id;
    private String natural;
    private String name_en;
    private double lat;
    private double lon;

    public OsmVO() {
    }

    public OsmVO(Integer fid, Long osm_id, String natural, String name_en, double lat, double lon) {
        this.fid = fid;
        this.osm_id = osm_id;
        this.natural = natural;
        this.name_en = name_en;
        this.lat = lat;
        this.lon = lon;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Long getOsm_id() {
        return osm_id;
    }

    public void setOsm_id(Long osm_id) {
        this.osm_id = osm_id;
    }

    public String getNatural() {
        return natural;
    }

    public void setNatural(String natural) {
        this.natural = natural;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "OsmVO [fid=" + fid + ", osm_id=" + osm_id + ", name_en=" + name_en + ", natural=" + natural + ", lat="
                + lat + ", lon=" + lon + "]";
    }
}
