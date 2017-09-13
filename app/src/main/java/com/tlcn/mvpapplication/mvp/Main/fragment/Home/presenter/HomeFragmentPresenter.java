package com.tlcn.mvpapplication.mvp.Main.fragment.Home.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.tlcn.mvpapplication.api.network.ApiServices;
import com.tlcn.mvpapplication.app.App;
import com.tlcn.mvpapplication.app.AppManager;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.model.Direction;
import com.tlcn.mvpapplication.mvp.Main.fragment.Home.view.IHomeFragmentView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ducthinh on 12/09/2017.
 */

public class HomeFragmentPresenter extends BasePresenter implements IHomeFragmentPresenter {
    private LatLng lngStart, lngEnd;

    public void setLngStart(LatLng lngStart) {
        this.lngStart = lngStart;
    }

    public void setLngEnd(LatLng lngEnd) {
        this.lngEnd = lngEnd;
    }

    private GoogleApiClient mGoogleApiClient;

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (App.getGoogleApiHelper().isConnected()) {
            mGoogleApiClient = App.getGoogleApiHelper().getGoogleApiClient();
        }
    }

    public void attachView(IHomeFragmentView view) {
        super.attachView(view);
    }

    public IHomeFragmentView getView() {
        return (IHomeFragmentView) getIView();
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
                    getView().onFail("Can't get detail place!");
                }
            }
        });
    }

    @Override
    public void getDirectionFromTwoPoint() {
        if (lngEnd == null || lngStart == null) {
            getView().onFail("Null");
            return;
        }
        getView().showLoading();
        AppManager.http_api_direction().from(ApiServices.class).getDirection(convertLatLngToString(lngStart),
                convertLatLngToString(lngEnd),
                "AIzaSyDfcXtZwtuMYSZWe6LxP3V6k3WcbAwyetc").enqueue(new Callback<Direction>() {
            @Override
            public void onResponse(Call<Direction> call, Response<Direction> response) {
                getView().hideLoading();
                Log.d("Result", response.body().toString());
            }

            @Override
            public void onFailure(Call<Direction> call, Throwable t) {
                getView().hideLoading();
                Log.d("OnFail", t.getMessage());
            }
        });
    }

    private String convertLatLngToString(LatLng latLng) {
        return String.valueOf(latLng.latitude) + "," + String.valueOf(latLng.longitude);
    }
}
