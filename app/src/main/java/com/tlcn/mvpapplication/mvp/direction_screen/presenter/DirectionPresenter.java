package com.tlcn.mvpapplication.mvp.direction_screen.presenter;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.model.PolylineInfo;
import com.tlcn.mvpapplication.model.direction.Route;
import com.tlcn.mvpapplication.mvp.direction_screen.view.IDirectionView;
import com.tlcn.mvpapplication.utils.KeyUtils;
import com.tlcn.mvpapplication.utils.MapUtils;

import java.util.ArrayList;
import java.util.List;

public class DirectionPresenter extends BasePresenter implements IDirectionPresenter, PolylineInfo.NewLocationListener, Route.OnChangeLocationListener {
    private static final String TAG = DirectionPresenter.class.getSimpleName();
    private static float bearingMap;
    private Route mRoute;
    private Location currentLocation;
    private ValueEventListener mListenerLocation;
    private DatabaseReference mReference;
    private List<Locations> allLocation;
    private List<Locations> listNewLocationAdded, listReduceLocation, listIncreaseLocation;
    private addNewLocationTask asyncTask;
    private calculateLocation taskChangeLocation;
    private PolylineInfo polylineInfo;
    private boolean isFirst = true;

    @Override
    public void onCreate() {
        super.onCreate();
        allLocation = new ArrayList<>();
        polylineInfo = new PolylineInfo();
        listReduceLocation = new ArrayList<>();
        listNewLocationAdded = new ArrayList<>();
        listIncreaseLocation = new ArrayList<>();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child(KeyUtils.LOCATIONS);
        initListener();
    }

    private void initListener() {
        polylineInfo.setOnNewLocationListener(this);
        mListenerLocation = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
                allLocation.clear();
                for (DataSnapshot data : dataSnapshots) {
                    Locations item = data.getValue(Locations.class);
                    allLocation.add(item);
                }
                if (allLocation.size() != 0) {
                    asyncTask = new addNewLocationTask();
                    asyncTask.execute();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mReference.addValueEventListener(mListenerLocation);
    }

    @Override
    public void onDestroy() {
        taskChangeLocation.cancel(true);
        asyncTask.cancel(true);
        mReference.removeEventListener(mListenerLocation);
    }

    public void attachView(IDirectionView view) {
        super.attachView(view);
    }

    public IDirectionView getView() {
        return (IDirectionView) getIView();
    }

    @Override
    public void setRouteFromObj(Object routeFromObj, LatLng latLng) {
        if (routeFromObj != null) {
            this.mRoute = (Route) routeFromObj;
            mRoute.createMarkPlace();
            mRoute.addOnChangeLocation(this);
            bearingMap = MapUtils.getBearing(
                    mRoute.getLeg().get(0).getStep().get(0).getCustomLatLng().get(0).getLocation(),
                    mRoute.getLeg().get(0).getStep().get(0).getCustomLatLng().get(1).getLocation()
            );
        } else
            getView().onFail("Lỗi không xác định! Thử lại sau.");
    }

    public Route getRoutes() {
        return this.mRoute;
    }

    @Override
    public void onHaveANewLocation(Locations lct) {
        if (!isFirst) {
            listNewLocationAdded.add(lct);
        }
    }

    @Override
    public void onLevelLocationIsIncrease(Locations lct) {
        if (!isFirst) {
            listIncreaseLocation.add(lct);
        }
    }

    @Override
    public void onLevelLocationIsReduction(Locations locations) {
        if (!isFirst) {
            listReduceLocation.add(locations);
        }
    }

    public void onChangeLocation(Location location) {
        taskChangeLocation = new calculateLocation();
        currentLocation = location;
        taskChangeLocation.execute(location);
    }

    @Override
    public void drawPolyline(LatLng latLngStart, LatLng latLngEnd) {
        if (isViewAttached())
            getView().drawAPolyline(latLngStart, latLngEnd);
    }

    @Override
    public void changeBearing(float bearing) {
        bearingMap = bearing;
    }

    public float getBearing() {
        return bearingMap;
    }

    @SuppressLint("StaticFieldLeak")
    private class addNewLocationTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            polylineInfo.setLocations(allLocation);
            polylineInfo.setRoute(mRoute);
            polylineInfo.addLocationToDirection();
            if (isCancelled()) {
                Log.d("CancelPresenter", TAG);
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (isViewAttached() && !isFirst) {
                notifyHaveANewLocation();
                notifyIncreaseLocation();
                notifyReduceLocation();
            }
            isFirst = false;
        }
    }

    @Override
    public void notifyHaveANewLocation() {
        if (listNewLocationAdded.size() == 0)
            return;
        for (Locations locations : listNewLocationAdded) {
            getView().notifyLocationAdded(locations, KeyUtils.TYPE_NEW);
            getView().drawANewLocation(locations);
        }
        listNewLocationAdded.clear();
    }

    @Override
    public void notifyIncreaseLocation() {
        if (listIncreaseLocation.size() == 0)
            return;
        for (Locations locations : listIncreaseLocation) {
            getView().notifyLocationAdded(locations, KeyUtils.TYPE_INCREASE);
            getView().updateLocation(locations);
        }
        listIncreaseLocation.clear();
    }

    @Override
    public void notifyReduceLocation() {
        if (listReduceLocation.size() == 0)
            return;
        for (Locations locations : listReduceLocation) {
            getView().notifyLocationAdded(locations, KeyUtils.TYPE_REDUCE);
            getView().updateLocation(locations);
        }
        listReduceLocation.clear();
    }

    @SuppressLint("StaticFieldLeak")
    private class calculateLocation extends AsyncTask<Location, Void, Void> {

        @Override
        protected Void doInBackground(Location... locations) {
            Location location = locations[0];
            mRoute.onChangeLocation(location);
            if (isCancelled()) {
                Log.d("CancelPresenter", TAG);
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (isViewAttached())
                getView().setBearingMap(bearingMap, new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
        }
    }

}
