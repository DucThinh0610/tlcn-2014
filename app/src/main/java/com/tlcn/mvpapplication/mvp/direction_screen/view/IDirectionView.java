package com.tlcn.mvpapplication.mvp.direction_screen.view;

import com.google.android.gms.maps.model.LatLng;
import com.tlcn.mvpapplication.base.IView;
import com.tlcn.mvpapplication.model.Locations;

public interface IDirectionView extends IView {
    void onFail(String s);

    void notifyNewLocation(Locations locations);

    void drawANewLocation(Locations locations);

    void registerSensor();

    void unRegisterSensor();

    void moveCameraToMyLocation();

    void drawAPolyline(LatLng latLngStart, LatLng latLngStart1);
}
