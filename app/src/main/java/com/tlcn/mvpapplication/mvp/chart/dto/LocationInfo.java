package com.tlcn.mvpapplication.mvp.chart.dto;

import com.google.android.gms.maps.model.LatLng;
import com.tlcn.mvpapplication.model.Locations;

public class LocationInfo implements IChartDto {

    private Locations locations;

    public LocationInfo(Locations locations) {
        this.locations = locations;
    }

    public LatLng getLatLng() {
        return new LatLng(locations.getLat(), locations.getLng());
    }
}
