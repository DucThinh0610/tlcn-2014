package com.tlcn.mvpapplication.mvp.chooselocation.presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.app.App;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.mvp.chooselocation.view.IChooseLocationView;

/**
 * Created by tskil on 9/13/2017.
 */

public class ChooseLocationPresenter extends BasePresenter implements IChooseLocationPresenter {
    private GoogleApiClient mGoogleApiClient;

    public void attachView(IChooseLocationView view) {
        super.attachView(view);
    }

    public IChooseLocationView getView() {
        return (IChooseLocationView) getIView();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (App.getGoogleApiHelper().isConnected()) {
            mGoogleApiClient = App.getGoogleApiHelper().getGoogleApiClient();
        }
    }
    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }
    @Override
    public void getDetailPlace(String idPlace) {
        PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                .getPlaceById(mGoogleApiClient, idPlace);
        placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(@NonNull PlaceBuffer places) {
                if (places.getCount() == 1) {
                    getView().getDetailPlaceSuccess(places);
                } else {
                    getView().onFail(App.getContext().getString(R.string.cant_get_detail_place));
                }
            }
        });
    }
}
