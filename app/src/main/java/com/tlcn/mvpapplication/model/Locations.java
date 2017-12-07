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
    private String latest_image_url;

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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getCurrent_level() {
        return current_level;
    }

    public void setCurrent_level(double current_level) {
        this.current_level = current_level;
    }

    public String getLast_modify() {
        return last_modify;
    }

    public void setLast_modify(String last_modify) {
        this.last_modify = last_modify;
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

    public String getLatest_image_url() {
        return latest_image_url;
    }

    public void setLatest_image_url(String latest_image_url) {
        this.latest_image_url = latest_image_url;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId() == ((Locations) obj).getId();
    }
}
