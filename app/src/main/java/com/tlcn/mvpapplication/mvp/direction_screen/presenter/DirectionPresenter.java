package com.tlcn.mvpapplication.mvp.direction_screen.presenter;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.model.PolylineInfo;
import com.tlcn.mvpapplication.model.direction.Location;
import com.tlcn.mvpapplication.model.direction.Route;
import com.tlcn.mvpapplication.mvp.direction_screen.view.IDirectionView;
import com.tlcn.mvpapplication.utils.KeyUtils;
import com.tlcn.mvpapplication.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class DirectionPresenter extends BasePresenter implements IDirectionPresenter, PolylineInfo.NewLocationListener {
    private static final String TAG = DirectionPresenter.class.getSimpleName();

    private Route mRoute;
    private ValueEventListener mListenerLocation;
    private DatabaseReference mReference;
    private List<Locations> allLocation;
    private List<Locations> listNewLocationAdded;

    private PolylineInfo polylineInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        allLocation = new ArrayList<>();
        polylineInfo = new PolylineInfo();
        listNewLocationAdded = new ArrayList<>();
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
                if (allLocation.size() != 0)
                    new addNewLocationTask().execute();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mReference.addValueEventListener(mListenerLocation);
    }

    @Override
    public void onDestroy() {
        mReference.removeEventListener(mListenerLocation);
    }

    public void attachView(IDirectionView view) {
        super.attachView(view);
    }

    public IDirectionView getView() {
        return (IDirectionView) getIView();
    }

    public void setRouteFromObj(Object routeFromObj) {
        if (routeFromObj != null) {
            this.mRoute = (Route) routeFromObj;
            mRoute.createMarkPlace();
        } else
            getView().onFail("Lỗi không xác định! Thử lại sau.");
    }

    public Route getRoutes() {
        return this.mRoute;
    }

    @Override
    public void onHaveANewLocation(Locations lct) {
        Log.d(TAG, new Gson().toJson(lct));
        listNewLocationAdded.add(lct);
    }

    private class addNewLocationTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            polylineInfo.setLocations(allLocation);
            polylineInfo.setRoute(mRoute);
            polylineInfo.addLocationToDirection();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (isViewAttached() && listNewLocationAdded.size() != 0) {
                notifyHaveANewLocation();
            }
        }
    }

    @Override
    public void notifyHaveANewLocation() {
        for (Locations locations : listNewLocationAdded) {
            getView().notifyNewLocation();
            getView().drawANewLocation(locations);
        }
        listNewLocationAdded.clear();
    }
}
