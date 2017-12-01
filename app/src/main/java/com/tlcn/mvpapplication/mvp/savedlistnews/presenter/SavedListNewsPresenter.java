package com.tlcn.mvpapplication.mvp.savedlistnews.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tlcn.mvpapplication.api.network.ApiCallback;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.api.network.RestError;
import com.tlcn.mvpapplication.api.request.action.ActionRequest;
import com.tlcn.mvpapplication.api.request.save.SaveRequest;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.mvp.savedlistnews.view.ISavedListNewsView;
import com.tlcn.mvpapplication.utils.DateUtils;
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
        getView().showLoading();
        Query saveQuery = mSaveReference.orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        saveQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Query query = mLocationReference.orderByChild("id").equalTo(data.child("location_id").getValue().toString());
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            getView().hideLoading();
                            boolean isChild = false;
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                Locations item = child.getValue(Locations.class);
                                for (int i = 0; i < list.size(); i++) {
                                    if (list.get(i).getId() == item.getId()) {
                                        list.set(i, item);
                                        isChild = true;
                                    }
                                }
                                if (!isChild) {
                                    list.add(item);
                                }
                            }
                            getView().onGetSavedListLocationSuccess(list);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            getView().hideLoading();
                            getView().onFailed(databaseError.getMessage());
                        }
                    });
                }
                getView().hideLoading();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getView().hideLoading();
                getView().onFailed(databaseError.getMessage());
            }
        });
    }

    @Override
    public void unSavedLocation(Locations location) {
        getView().showLoading();
        SaveRequest request = new SaveRequest(location.getId(), FirebaseAuth.getInstance().getCurrentUser().getUid());
        getManager().saveLocation(request, new ApiCallback<BaseResponse>() {
            @Override
            public void success(BaseResponse res) {
                getView().hideLoading();
                getView().notifyDataSetChanged();
            }

            @Override
            public void failure(RestError error) {
                getView().hideLoading();
                getView().onFailed(error.message);
            }
        });
    }

    @Override
    public void contributing(Locations location) {
        getView().showLoading();
        ActionRequest request = new ActionRequest(location.getId(), DateUtils.getCurrentDate());
        if (location.getStatus()) {
            getManager().actionStop(request, new ApiCallback<BaseResponse>() {
                @Override
                public void success(BaseResponse res) {
                    getView().hideLoading();
                    getView().onContributingSuccess();
                }

                @Override
                public void failure(RestError error) {
                    getView().hideLoading();
                    getView().onFailed(error.message);
                }
            });
        } else {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                request.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
            }
            getManager().actionOn(request, new ApiCallback<BaseResponse>() {
                @Override
                public void success(BaseResponse res) {
                    getView().hideLoading();
                    getView().onContributingSuccess();
                }

                @Override
                public void failure(RestError error) {
                    getView().hideLoading();
                    getView().onFailed(error.message);
                }
            });
        }
    }
}
