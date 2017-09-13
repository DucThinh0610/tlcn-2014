package com.tlcn.mvpapplication.mvp.Main.fragment.Favourite.presenter;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by tskil on 9/12/2017.
 */

public interface IFavouritePresenter {
    void getNewsInfo(String idNews);

    void getListNews();

    void setHouseLocation(LatLng location);
    void setWorkLocation(LatLng location);
    void setOtherLocation(LatLng location);
}
