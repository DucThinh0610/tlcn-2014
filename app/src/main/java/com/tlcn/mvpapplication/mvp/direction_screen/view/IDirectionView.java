package com.tlcn.mvpapplication.mvp.direction_screen.view;

import com.google.android.gms.maps.model.LatLng;
import com.tlcn.mvpapplication.base.IView;
import com.tlcn.mvpapplication.model.Locations;

public interface IDirectionView extends IView {
    void onFail(String s);

    void drawANewLocation(Locations locations);

    void setBearingMap(float bearing, LatLng latLng);

    void registerSensor();

    void unRegisterSensor();

    void moveCameraToMyLocation();

    void drawAPolyline(LatLng latLngStart, LatLng latLngStart1);

    void updateLocation(Locations locations);

    void notifyLocationAdded(Locations locations, int type);
}
