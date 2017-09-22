package com.tlcn.mvpapplication.mvp.main.fragment.Home.presenter;

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
import com.tlcn.mvpapplication.api.network.ApiServices;
import com.tlcn.mvpapplication.api.network.RestCallback;
import com.tlcn.mvpapplication.api.network.RestError;
import com.tlcn.mvpapplication.api.response.GetDirectionResponse;
import com.tlcn.mvpapplication.app.App;
import com.tlcn.mvpapplication.app.AppManager;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.caches.storage.MapStorage;
import com.tlcn.mvpapplication.model.News;
import com.tlcn.mvpapplication.model.direction.Route;
import com.tlcn.mvpapplication.mvp.main.fragment.Home.view.IHomeFragmentView;
import com.tlcn.mvpapplication.utils.KeyUtils;
import com.tlcn.mvpapplication.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentPresenter extends BasePresenter implements IHomeFragmentPresenter {
    private int boundRadiusLoad = 300;
    private LatLng lngStart, lngEnd;
    private List<Route> routes = new ArrayList<>();
    private List<News> listPlace = new ArrayList<>();
    private GoogleApiClient mGoogleApiClient;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
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


    public int getBoundRadiusLoad() {
        return boundRadiusLoad;
    }

    public void setBoundRadiusLoad(int boundRadiusLoad) {
        this.boundRadiusLoad = boundRadiusLoad;
    }

    public List<News> getListPlace() {
        if (listPlace==null)
            listPlace=new ArrayList<>();
        return listPlace;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (App.getGoogleApiHelper().isConnected()) {
            mGoogleApiClient = App.getGoogleApiHelper().getGoogleApiClient();
        }
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child(KeyUtils.NEWS);
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
                KeyUtils.KEY_DIRECTION_API, true).enqueue(new RestCallback<GetDirectionResponse>() {
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

    @Override
    public void getDetailNews(final LatLng latLng) {
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> listData = dataSnapshot.getChildren();
                for (DataSnapshot data : listData) {
                    News item = data.getValue(News.class);
                    LatLng start = new LatLng(item.getLatitude(), item.getLongitude());
                    if (item.isStatus()) {
                        if (Utilities.calculationByDistance(start, latLng) <= KeyUtils.DEFAULT_DISTANCE_TO_LOAD) {
                            getView().getDetailNewsSuccess(item);
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
        });
    }

    @Override
    public void getInfoPlace(final LatLng latLng) {
        if (this.mCameraPosition == null)
            return;
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
                listPlace.clear();
                for (DataSnapshot data : dataSnapshots) {
                    News item = data.getValue(News.class);
                    LatLng start = new LatLng(item.getLatitude(), item.getLongitude());
                    if (item.isStatus()) {
                        if (Utilities.calculationByDistance(start, latLng) <= boundRadiusLoad) {
                            listPlace.add(item);
                        }
                    }
                }
                if (listPlace.size() == 0) {
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
        });

    }

    private String convertLatLngToString(LatLng latLng) {
        return String.valueOf(latLng.latitude) + "," + String.valueOf(latLng.longitude);
    }
}
