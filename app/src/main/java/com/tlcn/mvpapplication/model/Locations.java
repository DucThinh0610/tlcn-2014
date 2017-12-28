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
    private double rate;
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

    public double getLng() {
        return lng;
    }

    public boolean isStatus() {
        return status;
    }

    public double getCurrent_level() {
        return current_level;
    }

    public String getLast_modify() {
        return last_modify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getLatest_image_url() {
        return latest_image_url;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId() == ((Locations) obj).getId();
    }
}
