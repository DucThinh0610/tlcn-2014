package com.tlcn.mvpapplication.mvp.main.fragment.Favourite.presenter;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by tskil on 9/12/2017.
 */

public interface IFavouritePresenter {
    void getListNews();

    void setHouseLocation(LatLng location);

    void setWorkLocation(LatLng location);

    void setOtherLocation(LatLng location);
}
