package com.tlcn.mvpapplication.mvp.direction_screen.presenter;

import com.google.android.gms.maps.model.LatLng;

public interface IDirectionPresenter {
    void setRouteFromObj(Object routeFromObj, LatLng latLng);

    void notifyHaveANewLocation();
}
