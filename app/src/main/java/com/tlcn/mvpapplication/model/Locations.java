package com.tlcn.mvpapplication.model;

import java.io.Serializable;

public class Locations implements Serializable {
    private int id;
    private double lat;
    private double lng;
    private int status;
    private double level;
    private String last_modify;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public String getLast_modify() {
        return last_modify;
    }

    public void setLast_modify(String last_modify) {
        this.last_modify = last_modify;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
