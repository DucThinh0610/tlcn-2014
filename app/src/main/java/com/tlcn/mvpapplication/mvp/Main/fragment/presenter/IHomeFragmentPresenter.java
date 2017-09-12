package com.tlcn.mvpapplication.mvp.Main.fragment.presenter;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ducthinh on 12/09/2017.
 */

public interface IHomeFragmentPresenter {
    void getDetailPlace(String idPlace);

    void getDirectionFromTwoPoint();
}
