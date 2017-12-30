package com.tlcn.mvpapplication.mvp.main.fragment.Home.presenter;

import com.google.android.gms.maps.model.LatLng;
import com.tlcn.mvpapplication.api.request.user.LoginRequest;
import com.tlcn.mvpapplication.api.request.user.LogoutRequest;

interface IHomePresenter {
    void getDetailPlace(String idPlace);

    void getDirectionFromTwoPoint();

    void getDetailLocation(LatLng latLng);

    void getInfoPlace(LatLng latLng);

    void saveCurrentStateMap();

    void pushNotificationToken(String UID, String token);

    void login(LoginRequest request);

    void logout(LogoutRequest request);
}
