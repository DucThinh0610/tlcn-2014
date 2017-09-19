package com.tlcn.mvpapplication.mvp.main.fragment.Home.presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.tlcn.mvpapplication.api.network.ApiServices;
import com.tlcn.mvpapplication.api.network.RestCallback;
import com.tlcn.mvpapplication.api.network.RestError;
import com.tlcn.mvpapplication.api.response.GetDirectionResponse;
import com.tlcn.mvpapplication.app.App;
import com.tlcn.mvpapplication.app.AppManager;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.caches.storage.MapStorage;
import com.tlcn.mvpapplication.model.direction.Route;
import com.tlcn.mvpapplication.mvp.main.fragment.Home.view.IHomeFragmentView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentPresenter extends BasePresenter implements IHomeFragmentPresenter {
    private LatLng lngStart, lngEnd;
    private List<Route> routes = new ArrayList<>();

    private GoogleApiClient mGoogleApiClient;

    private CameraPosition mCameraPosition;


    public void setCameraPosition(CameraPosition cameraPosition) {
        this.mCameraPosition = cameraPosition;
        MapStorage.getInstance().setCameraPosition(cameraPosition);
    }

    public CameraPosition getCameraPosition() {
        return mCameraPosition;
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    public void setLngStart(LatLng lngStart) {
        this.lngStart = lngStart;
    }

    public void setLngEnd(LatLng lngEnd) {
        this.lngEnd = lngEnd;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (App.getGoogleApiHelper().isConnected()) {
            mGoogleApiClient = App.getGoogleApiHelper().getGoogleApiClient();
        }
        mCameraPosition = MapStorage.getInstance().getCameraPosition();
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
                    places.release();
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
                "AIzaSyCL8C2wURzDuzgF8VRSZ8GOLG0YEBT07Ig", true).enqueue(new RestCallback<GetDirectionResponse>() {
            @Override
            public void success(GetDirectionResponse res) {
                getView().onStartFindDirection();
                if (routes != null) {
                    routes.clear();
                }
                routes.addAll(res.getRoutes());
                getView().getDirectionSuccess(routes);
                getView().hideLoading();
            }

            @Override
            public void failure(RestError error) {
                getView().onFail(error.message);
                getView().hideLoading();
            }
        });
    }

    private String convertLatLngToString(LatLng latLng) {
        return String.valueOf(latLng.latitude) + "," + String.valueOf(latLng.longitude);
    }
}
