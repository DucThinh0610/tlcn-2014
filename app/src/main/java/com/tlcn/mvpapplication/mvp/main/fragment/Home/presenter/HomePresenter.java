package com.tlcn.mvpapplication.mvp.main.fragment.Home.presenter;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.api.network.ApiCallback;
import com.tlcn.mvpapplication.api.network.ApiServices;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.api.network.RestCallback;
import com.tlcn.mvpapplication.api.network.RestError;
import com.tlcn.mvpapplication.api.request.user.LoginRequest;
import com.tlcn.mvpapplication.api.request.user.LogoutRequest;
import com.tlcn.mvpapplication.api.response.GetDirectionResponse;
import com.tlcn.mvpapplication.api.response.LoginResponse;
import com.tlcn.mvpapplication.app.App;
import com.tlcn.mvpapplication.app.AppManager;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.caches.storage.MapStorage;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.model.PolylineInfo;
import com.tlcn.mvpapplication.model.direction.Route;
import com.tlcn.mvpapplication.mvp.main.fragment.Home.view.IHomeView;
import com.tlcn.mvpapplication.utils.KeyUtils;
import com.tlcn.mvpapplication.utils.LogUtils;
import com.tlcn.mvpapplication.utils.MapUtils;
import com.tlcn.mvpapplication.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter extends BasePresenter implements IHomePresenter {
    private int boundRadiusLoad = 300;
    private LatLng lngStart, lngEnd;
    private List<Route> routes;
    private List<Locations> listPlace = new ArrayList<>();
    private List<Locations> allLocation = new ArrayList<>();
    private GoogleApiClient mGoogleApiClient;
    public DatabaseReference mReference;
    private CameraPosition mCameraPosition;
    private boolean continuousShowDialog = true;
    public ValueEventListener mListenerDetail, mListenerInfo;
    private boolean stateUI;

    public void setContinuousShowDialog(boolean continuousShowDialog) {
        this.continuousShowDialog = continuousShowDialog;
    }

    public void setCameraPosition(CameraPosition cameraPosition) {
        this.mCameraPosition = cameraPosition;
    }

    public boolean isStateUI() {
        return stateUI;
    }

    public CameraPosition getCameraPosition() {
        return mCameraPosition;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public Route getRouteSelected() {
        Route item = new Route();
        for (Route route : routes) {
            if (route.isSelected()) {
                item = route;
                break;
            }
        }
        return item;
    }

    public GoogleApiClient getGoogleApiClient() {
        if (mGoogleApiClient == null)
            mGoogleApiClient = App.getGoogleApiHelper().getGoogleApiClient();
        return mGoogleApiClient;
    }

    public void setLngStart(LatLng lngStart) {
        this.lngStart = lngStart;
    }

    public void setLngEnd(LatLng lngEnd) {
        this.lngEnd = lngEnd;
    }


    public int getBoundRadiusLoad() {
        return boundRadiusLoad;
    }

    public void setBoundRadiusLoad(int boundRadiusLoad) {
        this.boundRadiusLoad = boundRadiusLoad;
    }

    public List<Locations> getListPlace() {
        if (listPlace == null)
            listPlace = new ArrayList<>();
        return listPlace;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (App.getGoogleApiHelper().isConnected()) {
            mGoogleApiClient = App.getGoogleApiHelper().getGoogleApiClient();
        }
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child(KeyUtils.LOCATIONS);
        mCameraPosition = MapStorage.getInstance().getCameraPosition();
        stateUI = MapStorage.getInstance().getStateUI();
        routes = MapStorage.getInstance().getDirection();
    }

    public void attachView(IHomeView view) {
        super.attachView(view);
    }

    public IHomeView getView() {
        return (IHomeView) getIView();
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
                    getView().onFail(App.getContext().getString(R.string.cant_get_detail_place));
                }
            }
        });
    }

    @Override
    public void getDirectionFromTwoPoint() {
        if (lngEnd == null || lngStart == null) {
            getView().onFail(App.getContext().getString(R.string.null_data));
            return;
        }
        getView().showLoading();
        AppManager.http_api_direction().from(ApiServices.class).getDirection(convertLatLngToString(lngStart),
                convertLatLngToString(lngEnd),
                KeyUtils.KEY_DIRECTION_API, true, "vi").enqueue(new RestCallback<GetDirectionResponse>() {
            @Override
            public void success(GetDirectionResponse res) {
                getView().onStartFindDirection();
                if (routes != null) {
                    routes.clear();
                }
                new getDirectionTask().execute(res);
            }

            @Override
            public void failure(RestError error) {
                getView().onFail(error.message);
                getView().hideLoading();
            }
        });
    }

    @Override
    public void getDetailLocation(final LatLng latLng) {
        mListenerInfo = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> listData = dataSnapshot.getChildren();
                for (DataSnapshot data : listData) {
                    Locations item = data.getValue(Locations.class);
                    LatLng start = new LatLng(item.getLat(), item.getLng());
                    if (item.isStatus()) {
                        if (Utilities.calculationByDistance(start, latLng) <= KeyUtils.DEFAULT_DISTANCE_TO_LOAD) {
                            getView().getDetailLocationSuccess(item);
                            getView().hideLoading();
                            return;
                        }
                    }
                }
                getView().hideLoading();
                getView().onFail(App.getContext().getString(R.string.there_is_not_current_infomation));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getView().hideLoading();
                getView().onFail(databaseError.getMessage());
            }
        };
        mReference.addValueEventListener(mListenerInfo);
    }

    @Override
    public void getInfoPlace(final LatLng latLng) {
        mListenerDetail = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
                listPlace.clear();
                allLocation.clear();
                for (DataSnapshot data : dataSnapshots) {
                    Locations item = data.getValue(Locations.class);
                    LatLng start = new LatLng(item.getLat(), item.getLng());
                    allLocation.add(item);
                    if (item.isStatus()) {
                        if (MapUtils.distanceBetweenTwoPoint(start, latLng) <= boundRadiusLoad) {
                            listPlace.add(item);
                        }
                    }
                }
                if (listPlace.size() == 0 && boundRadiusLoad < 500 && continuousShowDialog) {
                    getView().showDialogConfirmNewRadius();
                } else {
                    getView().showPlaces();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getView().hideLoading();
                getView().onFail(databaseError.getMessage());
            }
        };
        mReference.addValueEventListener(mListenerDetail);

    }

    @Override
    public void saveCurrentStateMap() {
        if (mCameraPosition != null)
            MapStorage.getInstance().setCameraPosition(mCameraPosition);
        MapStorage.getInstance().setStateUI(stateUI);
        if (routes != null) {
            MapStorage.getInstance().setDirection(routes);
        }
    }

    @Override
    public void pushNotificationToken(String UID, String token) {
        getView().showLoading();
        getManager().pushNotificationToken(UID, token, new ApiCallback<BaseResponse>() {
            @Override
            public void success(BaseResponse res) {
                getView().hideLoading();
                LogUtils.LOGE("MESSAGE", res.getMessage());
            }

            @Override
            public void failure(RestError error) {
                getView().hideLoading();
                getView().onFail(error.message);
            }
        });
    }

    @Override
    public void login(LoginRequest request) {
        getView().showLoading();
        getManager().login(request, new ApiCallback<LoginResponse>() {
            @Override
            public void success(LoginResponse res) {

                getView().hideLoading();
            }

            @Override
            public void failure(RestError error) {
                getView().hideLoading();
                getView().onFail(error.message);
            }
        });
    }

    @Override
    public void logout(LogoutRequest request) {
        getView().showLoading();
        getManager().logout(request, new ApiCallback<BaseResponse>() {
            @Override
            public void success(BaseResponse res) {
                getView().hideLoading();
            }

            @Override
            public void failure(RestError error) {
                getView().hideLoading();
                getView().onFail(error.message);
            }
        });
    }

    private String convertLatLngToString(LatLng latLng) {
        return String.valueOf(latLng.latitude) + "," + String.valueOf(latLng.longitude);
    }

    public void setStateUI(boolean stateUI) {
        this.stateUI = stateUI;
    }

    private class getDirectionTask extends AsyncTask<GetDirectionResponse, Void, Void> {

        @Override
        protected Void doInBackground(GetDirectionResponse... params) {
            for (Route route : params[0].getRoutes()) {
                routes.add(new PolylineInfo(route, allLocation).getRoute());
            }
            routes.get(0).setSelected(true);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            getView().getDirectionSuccess();
            getView().hideLoading();
        }
    }
}
