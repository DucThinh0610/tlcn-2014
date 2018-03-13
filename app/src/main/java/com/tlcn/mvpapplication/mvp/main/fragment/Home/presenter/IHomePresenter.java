package com.tlcn.mvpapplication.mvp.main.fragment.Home.presenter;

import com.google.android.gms.maps.model.LatLng;
import com.tlcn.mvpapplication.api.request.user.LoginRequest;

interface IHomePresenter {
    void getDetailPlace(String idPlace);

    void getDirectionFromTwoPoint();

    void getDetailLocation(LatLng latLng);

    void getInfoPlace(LatLng latLng);

    void saveCurrentStateMap();

    void login(LoginRequest request);

    void logout();
}
