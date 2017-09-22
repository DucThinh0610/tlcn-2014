package com.tlcn.mvpapplication.api.request.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetInfoRequest implements Serializable {
    @SerializedName("lat")
    @Expose
    private double lat;
    @SerializedName("lng")
    @Expose
    private double lng;
    @SerializedName("length")
    @Expose
    private double length;

    public GetInfoRequest(double lat, double lng, double length) {
        this.lat = lat;
        this.lng = lng;
        this.length = length;
    }

    public GetInfoRequest(double lat) {
        this.lat = lat;
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

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
