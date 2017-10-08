package com.tlcn.mvpapplication.model;

import com.google.android.gms.maps.model.LatLng;
import com.tlcn.mvpapplication.model.direction.Route;
import com.tlcn.mvpapplication.model.direction.Step;
import com.tlcn.mvpapplication.utils.MapUtils;

import java.io.Serializable;
import java.util.List;

public class PolylineInfo implements Serializable {
    private Route route;
    private List<Locations> locations;

    public PolylineInfo(Route route, List<Locations> locations) {
        this.route = route;
        this.locations = locations;
        calculated();
    }

    private void calculated() {
        for (Step step : route.getLeg().get(0).getStep()) {
            for (int j = 1; j < step.getPoints().size(); j++) {
                for (int i = 0; i < locations.size(); i++) {
                    LatLng startLocation = step.getPoints().get(j - 1);
                    LatLng endLocation = step.getPoints().get(j);
                    LatLng point = new LatLng(locations.get(i).getLat(), locations.get(i).getLng());
                    if (MapUtils.distanceFromPointToPolyline(startLocation, endLocation, point) != -1 &&
                            MapUtils.distanceFromPointToPolyline(startLocation, endLocation, point) < 100) {
                        step.addLocation(locations.get(i));
                        locations.remove(i);
                    }
                }
            }
        }
    }

    public Route getRoute() {
        return route;
    }
}
