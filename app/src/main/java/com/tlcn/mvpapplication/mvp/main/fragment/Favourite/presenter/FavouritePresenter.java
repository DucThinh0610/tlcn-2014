package com.tlcn.mvpapplication.mvp.main.fragment.Favourite.presenter;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.model.News;
import com.tlcn.mvpapplication.mvp.main.fragment.Favourite.view.IFavouriteView;
import com.tlcn.mvpapplication.utils.KeyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tskil on 9/12/2017.
 */

public class FavouritePresenter extends BasePresenter implements IFavouritePresenter {
    private List<News> list;
    private News news;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    public void attachView(IFavouriteView view) {
        super.attachView(view);
    }

    public IFavouriteView getView() {
        return (IFavouriteView) getIView();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child(KeyUtils.NEWS);
        list = new ArrayList<>();
        news = new News();
    }

    public List<News> getListNewsResult(){
        return list;
    }

    @Override
    public void getListNews() {
        getView().showLoading();
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> listData = dataSnapshot.getChildren();
                for(DataSnapshot data : listData){
                    News item = data.getValue(News.class);
                    list.add(item);
                }
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

    }

    @Override
    public void setWorkLocation(LatLng location) {

    }

    @Override
    public void setOtherLocation(LatLng location) {

    }
}
