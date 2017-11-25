package com.tlcn.mvpapplication.model;

import java.io.Serializable;

public class Locations implements Serializable {
    private String id;
    private double lat;
    private double lng;
    private boolean status;
    private double current_level;
    private String last_modify;
    private String title;
    private int rate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getLevel() {
        return current_level;
    }

    public void setLevel(double level) {
        this.current_level = level;
    }

    public String getLast_modify() {
        return last_modify;
    }

    public void setLast_modify(String last_modify) {
        this.last_modify = last_modify;
    }

    public double getCurrent_level() {
        return current_level;
    }

    public void setCurrent_level(double current_level) {
        this.current_level = current_level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object obj) {
        return !super.equals(obj);
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
