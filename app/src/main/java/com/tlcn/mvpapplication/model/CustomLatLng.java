package com.tlcn.mvpapplication.model;

import com.google.android.gms.maps.model.LatLng;

public class CustomLatLng {
    public final double latitude;
    public final double longitude;
    private int state;

    public CustomLatLng(double var1, double var3) {
        if (-180.0D <= var3 && var3 < 180.0D) {
            this.longitude = var3;
        } else {
            this.longitude = ((var3 - 180.0D) % 360.0D + 360.0D) % 360.0D - 180.0D;
        }

        this.latitude = Math.max(-90.0D, Math.min(90.0D, var1));
        state = 0;
    }

    public CustomLatLng(LatLng latLng) {
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
        state = 0;
    }

    public CustomLatLng(int state, LatLng latLng) {
        this.state = state;
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }
}
