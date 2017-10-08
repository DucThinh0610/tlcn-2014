package com.tlcn.mvpapplication.mvp.savedlistnews.presenter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.mvp.savedlistnews.view.ISavedListNewsView;
import com.tlcn.mvpapplication.utils.KeyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 10/4/17.
 */

public class SavedListNewsPresenter extends BasePresenter implements ISavedListNewsPresenter {

    private List<Locations> list;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mLocationReference;
    private DatabaseReference mSaveReference;

    public void attachView(ISavedListNewsView view) {
        super.attachView(view);
    }

    public ISavedListNewsView getView() {
        return (ISavedListNewsView) getIView();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        list = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance();
        mLocationReference = mDatabase.getReference().child(KeyUtils.LOCATIONS);
        mSaveReference = mDatabase.getReference().child(KeyUtils.SAVE);
    }


    @Override
    public void getSavedListLocation() {
        list.clear();
        getView().showLoading();
        mSaveReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    String location_id = data.child("location_id").getValue().toString();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        getView().onGetSavedListLocationSuccess(list);
        getView().hideLoading();
    }
}
