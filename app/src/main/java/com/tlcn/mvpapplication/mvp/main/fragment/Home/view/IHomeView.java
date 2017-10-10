package com.tlcn.mvpapplication.mvp.main.fragment.Home.view;

import com.google.android.gms.location.places.PlaceBuffer;
import com.tlcn.mvpapplication.base.IView;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.model.direction.Route;

import java.util.List;

public interface IHomeView extends IView{

    void getDetailLocationSuccess(Locations result);

    void getDetailPlaceSuccess(PlaceBuffer places);

    void onFail(String message);

    void getDirectionSuccess();

    void onStartFindDirection();

    void showDialogConfirmNewRadius();

    void showPlaces();

    void showDirection();

    void showInfoDirection();
}
