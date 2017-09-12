package com.tlcn.mvpapplication.mvp.Main.fragment;

import com.google.android.gms.location.places.PlaceBuffer;
import com.tlcn.mvpapplication.base.IView;

/**
 * Created by ducthinh on 12/09/2017.
 */

public interface IHomeFragmentView extends IView{

    void getDetailPlaceSuccess(PlaceBuffer places);

    void onFail(String message);
}
