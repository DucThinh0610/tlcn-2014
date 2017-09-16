package com.tlcn.mvpapplication.caches.storage;

import com.google.android.gms.maps.model.CameraPosition;

/**
 * Created by ducthinh on 16/09/2017.
 */

public class MapStorage implements IMapStorage {
    private static final MapStorage INSTANCE = new MapStorage();

    public synchronized static final MapStorage getInstance() {
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
