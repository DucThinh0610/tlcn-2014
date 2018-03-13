package com.tlcn.mvpapplication.api.request;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by apple on 3/13/18.
 */

public class FavouriteLocationsRequest {
    private LatLng latLng1;

    private LatLng latLng2;

    private LatLng latLng3;

    private double distance;

    public LatLng getLatLng1() {
        return latLng1;
    }

    public void setLatLng1(LatLng latLng1) {
        this.latLng1 = latLng1;
    }

    public LatLng getLatLng2() {
        return latLng2;
    }

    public void setLatLng2(LatLng latLng2) {
        this.latLng2 = latLng2;
    }

    public LatLng getLatLng3() {
        return latLng3;
    }

    public void setLatLng3(LatLng latLng3) {
        this.latLng3 = latLng3;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
