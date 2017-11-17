package com.tlcn.mvpapplication.model.direction;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ducthinh on 12/09/2017.
 */

public class Location implements Serializable {
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;

    public LatLng getLatLag() {
        return new LatLng(lat, lng);
    }
}
