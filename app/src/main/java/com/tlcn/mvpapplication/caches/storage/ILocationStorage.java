package com.tlcn.mvpapplication.caches.storage;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by tskil on 9/18/2017.
 */

public interface ILocationStorage {

    void createDistanceFavourite(int progress);
    int getDistanceFavourite();

    void createHouseLocation(LatLng location);
    LatLng getHouseLocation();
    void removeHouseLocation();

    void createWorkLocation(LatLng location);
    LatLng getWorkLocation();
    void removeWorkLocation();

    void createOtherLocation(LatLng location);
    LatLng getOtherLocation();
    void removeOtherLocation();
}
