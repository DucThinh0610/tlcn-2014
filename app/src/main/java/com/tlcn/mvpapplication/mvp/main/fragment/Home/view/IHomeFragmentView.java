package com.tlcn.mvpapplication.mvp.main.fragment.Home.view;

import com.google.android.gms.location.places.PlaceBuffer;
import com.tlcn.mvpapplication.base.IView;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.model.News;
import com.tlcn.mvpapplication.model.direction.Route;

import java.util.List;

public interface IHomeFragmentView extends IView{

    void getDetailNewsSuccess(Locations res);

    void getDetailPlaceSuccess(PlaceBuffer places);

    void onFail(String message);

    void getDirectionSuccess(List<Route> routes);

    void onStartFindDirection();

    void showDialogConfirmNewRadius();

    void showPlaces();
}
