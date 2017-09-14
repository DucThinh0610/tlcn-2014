package com.tlcn.mvpapplication.mvp.chooselocation.view;

import com.google.android.gms.location.places.PlaceBuffer;
import com.tlcn.mvpapplication.base.IView;

/**
 * Created by tskil on 9/13/2017.
 */

public interface IChooseLocationView extends IView {
    void getDetailPlaceSuccess(PlaceBuffer places);

    void onFail(String message);
}
