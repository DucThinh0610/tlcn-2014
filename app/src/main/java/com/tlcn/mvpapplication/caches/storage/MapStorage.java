package com.tlcn.mvpapplication.caches.storage;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Polyline;
import com.tlcn.mvpapplication.model.direction.Route;

import java.util.ArrayList;
import java.util.List;

public class MapStorage implements IMapStorage {
    private static final MapStorage INSTANCE = new MapStorage();
    private boolean stateUI;
    private List<Route> direction;
    private List<Polyline> polylines;

    public synchronized static MapStorage getInstance() {
        return MapStorage.INSTANCE;
    }

    @Override
    public void resetAllDataStorage() {

    }

    private CameraPosition CameraPosition;

    public CameraPosition getCameraPosition() {
        return CameraPosition;
    }

    public void setCameraPosition(CameraPosition mCameraPosition) {
        this.CameraPosition = mCameraPosition;
    }

    public void setStateUI(boolean stateUI) {
        this.stateUI = stateUI;
    }

    public boolean getStateUI() {
        return this.stateUI;
    }

    public void setDirection(List<Route> direction) {
        this.direction = direction;
    }

    public List<Route> getDirection() {
        if (direction == null)
            direction = new ArrayList<>();
        return this.direction;
    }

    public void setPolylines(List<Polyline> polylines) {
        this.polylines = polylines;
    }

    public List<Polyline> getPolylines() {
        if (polylines == null)
            polylines = new ArrayList<>();
        return this.polylines;
    }
}
