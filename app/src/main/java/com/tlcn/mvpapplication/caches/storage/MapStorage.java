package com.tlcn.mvpapplication.caches.storage;

import com.google.android.gms.maps.model.CameraPosition;

public class MapStorage implements IMapStorage {
    private static final MapStorage INSTANCE = new MapStorage();

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
}
