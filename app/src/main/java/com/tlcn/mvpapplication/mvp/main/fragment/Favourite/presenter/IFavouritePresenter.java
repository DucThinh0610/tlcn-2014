package com.tlcn.mvpapplication.mvp.main.fragment.Favourite.presenter;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by tskil on 9/12/2017.
 */

public interface IFavouritePresenter {
    void getListNews();

    void getShareLink(String location_id);

    void onChangeStopped(String id);

    void setFavouriteDistance(int progress);

    void setHouseLocation(LatLng location);

    void setWorkLocation(LatLng location);

    void setOtherLocation(LatLng location);

    void removeHouseLocation();

    void removeWorkLocation();

    void removeOtherLocation();
}
