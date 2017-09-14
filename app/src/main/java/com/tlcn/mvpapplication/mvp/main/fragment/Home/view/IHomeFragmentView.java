package com.tlcn.mvpapplication.mvp.main.fragment.Home.view;

import com.google.android.gms.location.places.PlaceBuffer;
import com.tlcn.mvpapplication.base.IView;

public interface IHomeFragmentView extends IView{

    void getDetailPlaceSuccess(PlaceBuffer places);

    void onFail(String message);
}
