package com.tlcn.mvpapplication.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;
import com.tlcn.mvpapplication.model.direction.Route;
import com.tlcn.mvpapplication.model.direction.Step;
import com.tlcn.mvpapplication.utils.KeyUtils;
import com.tlcn.mvpapplication.utils.MapUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PolylineInfo implements Serializable {
    private NewLocationListener callback;
    private Route route;
    private List<Locations> locations;

    public PolylineInfo(Route route, List<Locations> locations) {
        this.route = route;
        this.locations = locations;
        calculated();
    }

    public PolylineInfo() {

    }

    private void calculated() {
        for (Step step : route.getLeg().get(0).getStep()) {
            List<Locations> temp = new ArrayList<>();
            temp.addAll(locations);
            for (int i = 0; i < temp.size(); i++) {
                LatLng point = new LatLng(temp.get(i).getLat(), temp.get(i).getLng());
                if (PolyUtil.isLocationOnPath(point, step.getPoints(), true, KeyUtils.DEFAULT_DISTANCE_TO_POLYLINE)) {
                    step.addLocation(temp.get(i));
                    temp.remove(i);
                }
            }
        }
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void addLocationToDirection() {
        for (Step step : route.getStepNonePass()) {
            for (int l = 0; l < locations.size(); l++) {
                LatLng point = new LatLng(locations.get(l).getLat(), locations.get(l).getLng());
                if (PolyUtil.isLocationOnPath(point, step.getLocationNonePass(), true, KeyUtils.DEFAULT_DISTANCE_TO_POLYLINE)) {
                    if (step.checkAddLocation(locations.get(l)))
                        callback.onHaveANewLocation(locations.get(l));
                }
            }
        }
    }

    public void setLocations(List<Locations> locations) {
        this.locations = locations;
    }

    public Route getRoute() {
        return route;
    }

    public interface NewLocationListener {

        void onHaveANewLocation(Locations lct);

    }

    public void setOnNewLocationListener(NewLocationListener callback) {
        this.callback = callback;
    }
}
