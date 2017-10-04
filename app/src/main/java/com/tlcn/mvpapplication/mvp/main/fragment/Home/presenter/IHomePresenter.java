package com.tlcn.mvpapplication.mvp.main.fragment.Home.presenter;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ducthinh on 12/09/2017.
 */

public interface IHomePresenter {
    void getDetailPlace(String idPlace);

    void getDirectionFromTwoPoint();

    void getDetailLocation(LatLng latLng);

    void getInfoPlace(LatLng latLng);
}
