package com.tlcn.mvpapplication.mvp.main.fragment.Favourite.presenter;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tlcn.mvpapplication.app.App;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.caches.storage.LocationStorage;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.mvp.main.fragment.Favourite.view.IFavouriteView;
import com.tlcn.mvpapplication.utils.DateUtils;
import com.tlcn.mvpapplication.utils.KeyUtils;
import com.tlcn.mvpapplication.utils.LogUtils;
import com.tlcn.mvpapplication.utils.Utilities;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by tskil on 9/12/2017.
 */

public class FavouritePresenter extends BasePresenter implements IFavouritePresenter {
    private List<Locations> list;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private LocationStorage mLocationStorage;

    public void attachView(IFavouriteView view) {
        super.attachView(view);
    }

    public IFavouriteView getView() {
        return (IFavouriteView) getIView();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationStorage = App.getLocationStorage();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child(KeyUtils.LOCATIONS);
        list = new ArrayList<>();
    }

    public List<Locations> getListNewsResult() {
        return list;
    }

    @Override
    public void getListNews() {
        getView().showLoading();
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                Iterable<DataSnapshot> listData = dataSnapshot.getChildren();
                for (DataSnapshot data : listData) {
                    Locations item = data.getValue(Locations.class);
                    LatLng start = new LatLng(item.getLat(), item.getLng());
                    LogUtils.LOGE("item", mLocationStorage.getOtherLocation().toString() + " "+ start.toString());
                    LogUtils.LOGE("item2",Utilities.calculationByDistance(start, mLocationStorage.getOtherLocation())+"");
                    if(item.getStatus()) {
                        if (Utilities.calculationByDistance(start, mLocationStorage.getHouseLocation()) <= KeyUtils.DEFAULT_DISTANCE_TO_LOAD
                                || Utilities.calculationByDistance(start, mLocationStorage.getWorkLocation()) <= KeyUtils.DEFAULT_DISTANCE_TO_LOAD
                                || Utilities.calculationByDistance(start, mLocationStorage.getOtherLocation()) <= KeyUtils.DEFAULT_DISTANCE_TO_LOAD)
                            list.add(item);
                    }
                }
                Collections.sort(list, new Comparator<Locations>() {
                    @Override
                    public int compare(Locations locations, Locations t1) {
                        Date date1 = DateUtils.parseStringToDate(locations.getLast_modify());
                        Date date2 = DateUtils.parseStringToDate(t1.getLast_modify());
                        return date2.compareTo(date1);
                    }
                });
                LogUtils.LOGE("itemsize",list.size()+"");
                getView().hideLoading();
                getView().getListNewsSuccess();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getView().hideLoading();
                getView().onFail(databaseError.getMessage());
            }
        });
    }

    @Override
    public void setHouseLocation(LatLng location) {
        mLocationStorage.createHouseLocation(location);
        getView().changeLocationSuccess();
    }

    @Override
    public void setWorkLocation(LatLng location) {
        mLocationStorage.createWorkLocation(location);
        getView().changeLocationSuccess();
    }

    @Override
    public void setOtherLocation(LatLng location) {
        mLocationStorage.createOtherLocation(location);
        getView().changeLocationSuccess();
    }

    @Override
    public void removeHouseLocation() {
        mLocationStorage.removeHouseLocation();
        getView().changeLocationSuccess();
    }

    @Override
    public void removeWorkLocation() {
        mLocationStorage.removeWorkLocation();
        getView().changeLocationSuccess();
    }

    @Override
    public void removeOtherLocation() {
        mLocationStorage.removeOtherLocation();
        getView().changeLocationSuccess();
    }
}
