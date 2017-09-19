package com.tlcn.mvpapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tskil on 9/16/2017.
 */

public class Contribution implements Serializable{
    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("device_id")
    @Expose
    private String device_id;

    @SerializedName("latitude")
    @Expose
    private double latitude;

    @SerializedName("longitude")
    @Expose
    private double longitude;

    @SerializedName("level")
    @Expose
    private int level;

    @SerializedName("created")
    @Expose
    private String created;

    @SerializedName("description")
    @Expose
    private String description;

    public Contribution(){

    }

    public Contribution(String user_id, String device_id, double latitude, double longitude, int level, String created, String description) {
        this.user_id = user_id;
        this.device_id = device_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.level = level;
        this.created = created;
        this.description = description;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
