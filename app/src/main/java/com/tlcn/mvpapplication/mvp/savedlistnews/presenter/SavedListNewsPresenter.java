package com.tlcn.mvpapplication.mvp.savedlistnews.presenter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.api.network.ApiCallback;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.api.network.RestError;
import com.tlcn.mvpapplication.api.request.BaseListRequest;
import com.tlcn.mvpapplication.api.request.action.ActionRequest;
import com.tlcn.mvpapplication.api.request.save.SaveRequest;
import com.tlcn.mvpapplication.api.response.DetailLocationResponse;
import com.tlcn.mvpapplication.api.response.LocationsResponse;
import com.tlcn.mvpapplication.app.App;
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

    private boolean isChange = false;

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

    public void setChange(boolean change) {
        isChange = change;
    }

    @Override
    public void getSavedListLocation(BaseListRequest request) {
        getView().showLoading();
        if (App.getUserInfo().getInfo() == null || App.getUserInfo().getInfo().getToken().isEmpty()) {
            getView().onFailed(App.getContext().getString(R.string.please_login));
            return;
        }

        getManager().getSavedLocations(App.getUserInfo().getInfo().getToken(), request, new ApiCallback<LocationsResponse>() {
            @Override
            public void success(LocationsResponse res) {
                if (!isViewAttached())
                    return;
                getView().onGetSavedListLocationSuccess(res.getData(), res.getMetaData());
                getView().hideLoading();
            }

            @Override
            public void failure(RestError error) {
                if (!isViewAttached())
                    return;
                getView().onFailed(error.message);
                getView().hideLoading();
            }
        });
    }

    @Override
    public void unSavedLocation(Locations location) {
        getView().showLoading();
        if (App.getUserInfo().getInfo() == null || App.getUserInfo().getInfo().getToken().isEmpty()) {
            getView().onFailed(App.getContext().getString(R.string.please_login));
            return;
        }
        SaveRequest request = new SaveRequest();
        request.setToken(App.getUserInfo().getInfo().getToken());
        request.setLocation_id(location.getId());
        getManager().saveLocation(request, new ApiCallback<DetailLocationResponse>() {
            @Override
            public void success(DetailLocationResponse res) {
                if (!isViewAttached())
                    return;
                getView().hideLoading();
                getView().notifyDataSetChanged();
            }

            @Override
            public void failure(RestError error) {
                if (!isViewAttached())
                    return;
                getView().hideLoading();
                getView().onFailed(error.message);
            }
        });
    }

    @Override
    public void contributing(Locations location) {
        getView().showLoading();
        if (App.getUserInfo().getInfo() == null || App.getUserInfo().getInfo().getToken().isEmpty()) {
            return;
        }
        ActionRequest request = new ActionRequest();
        request.setToken(App.getUserInfo().getInfo().getToken());
        request.setIdLocation(location.getId());
        if (location.isStatus()) {
            getManager().actionStop(request, new ApiCallback<BaseResponse>() {
                @Override
                public void success(BaseResponse res) {
                    if (!isViewAttached())
                        return;
                    getView().hideLoading();
                    getView().onContributingSuccess();
                }

                @Override
                public void failure(RestError error) {
                    if (!isViewAttached())
                        return;
                    getView().hideLoading();
                    getView().onFailed(error.message);
                }
            });
        } else {
//            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//                request.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
//            }
            getManager().actionOn(request, new ApiCallback<BaseResponse>() {
                @Override
                public void success(BaseResponse res) {
                    if (!isViewAttached())
                        return;
                    getView().hideLoading();
                    getView().onContributingSuccess();
                }

                @Override
                public void failure(RestError error) {
                    if (!isViewAttached())
                        return;
                    getView().hideLoading();
                    getView().onFailed(error.message);
                }
            });
        }
    }
}
