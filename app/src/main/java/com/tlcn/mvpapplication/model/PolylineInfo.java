package com.tlcn.mvpapplication.model;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.tlcn.mvpapplication.model.direction.Route;
import com.tlcn.mvpapplication.model.direction.Step;
import com.tlcn.mvpapplication.utils.MapUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            for (int j = 1; j < step.getPoints().size(); j++) {
                for (int i = 0; i < temp.size(); i++) {
                    LatLng startLocation = step.getPoints().get(j - 1);
                    LatLng endLocation = step.getPoints().get(j);
                    LatLng point = new LatLng(temp.get(i).getLat(), temp.get(i).getLng());
                    if (MapUtils.distanceFromPointToPolyline(startLocation, endLocation, point) != -1 &&
                            MapUtils.distanceFromPointToPolyline(startLocation, endLocation, point) < 100) {
                        step.addLocation(temp.get(i));
                        temp.remove(i);
                    }
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
                for (int i = 1; i < step.getLocationNonePass().size(); i++) {
                    LatLng startLocation = step.getLocationNonePass().get(i - 1);
                    LatLng endLocation = step.getLocationNonePass().get(i);
                    LatLng point = new LatLng(locations.get(l).getLat(), locations.get(l).getLng());
                    if (MapUtils.distanceFromPointToPolyline(startLocation, endLocation, point) != -1 &&
                            MapUtils.distanceFromPointToPolyline(startLocation, endLocation, point) < 100) {
                        if (step.checkAddLocation(locations.get(l)))
                            callback.onHaveANewLocation(locations.get(l));
                    }
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
