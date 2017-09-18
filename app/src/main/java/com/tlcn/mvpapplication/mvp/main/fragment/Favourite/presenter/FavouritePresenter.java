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
import com.tlcn.mvpapplication.model.News;
import com.tlcn.mvpapplication.mvp.main.fragment.Favourite.view.IFavouriteView;
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
    private List<News> list;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private LocationStorage mPreferenceUtils;

    public void attachView(IFavouriteView view) {
        super.attachView(view);
    }

    public IFavouriteView getView() {
        return (IFavouriteView) getIView();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPreferenceUtils = App.getPreferenceUtils();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child(KeyUtils.NEWS);
        list = new ArrayList<>();
    }

    public List<News> getListNewsResult() {
        return list;
    }

    @Override
    public void getListNews() {
        getView().showLoading();
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> listData = dataSnapshot.getChildren();
                for (DataSnapshot data : listData) {
                    News item = data.getValue(News.class);
                    LatLng start = new LatLng(item.getLatitude(), item.getLongitude());
                    LogUtils.LOGE("item",mPreferenceUtils.getOtherLocation().toString() + " "+ start.toString());
                    LogUtils.LOGE("item2",Utilities.calculationByDistance(start,mPreferenceUtils.getOtherLocation())+"");
                    if(item.isStatus()) {
                        if (Utilities.calculationByDistance(start, mPreferenceUtils.getHouseLocation()) <= KeyUtils.DEFAULT_DISTANCE_TO_LOAD
                                || Utilities.calculationByDistance(start, mPreferenceUtils.getWorkLocation()) <= KeyUtils.DEFAULT_DISTANCE_TO_LOAD
                                || Utilities.calculationByDistance(start, mPreferenceUtils.getOtherLocation()) <= KeyUtils.DEFAULT_DISTANCE_TO_LOAD)
                            list.add(item);
                    }
                }
                Collections.sort(list, new Comparator<News>() {
                    @Override
                    public int compare(News news, News t1) {
                        Date date1 = Utilities.parseStringToDate(news.getCreated());
                        Date date2 = Utilities.parseStringToDate(t1.getCreated());
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
        mPreferenceUtils.createHouseLocation(location);
    }

    @Override
    public void setWorkLocation(LatLng location) {
        mPreferenceUtils.createWorkLocation(location);
    }

    @Override
    public void setOtherLocation(LatLng location) {
        mPreferenceUtils.createOtherLocation(location);
    }
}
