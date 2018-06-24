package com.tlcn.mvpapplication.mvp.main.fragment.News.presenter;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.api.network.ApiCallback;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.api.network.RestError;
import com.tlcn.mvpapplication.api.request.BaseListRequest;
import com.tlcn.mvpapplication.api.request.action.ActionRequest;
import com.tlcn.mvpapplication.api.response.LocationsResponse;
import com.tlcn.mvpapplication.api.response.ShareResponse;
import com.tlcn.mvpapplication.app.App;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.interactor.event_bus.type.Empty;
import com.tlcn.mvpapplication.interactor.event_bus.type.ObjectEvent;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.mvp.main.fragment.News.view.INewsView;
import com.tlcn.mvpapplication.utils.KeyUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class NewsPresenter extends BasePresenter implements INewsPresenter {

    private List<Locations> list;

    public List<Locations> getListNewsResult() {
        return list;
    }

    public void attachView(INewsView view) {
        super.attachView(view);
    }

    public INewsView getView() {
        return (INewsView) getIView();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (!getEventManager().isRegister(this))
            getEventManager().register(this);
        list = new ArrayList<>();
    }

    @Override
    public void onDestroy() {
        if (getEventManager().isRegister(this))
            getEventManager().unRegister(this);
        super.onDestroy();
    }

    @Override
    public void getListNews(BaseListRequest request) {
        getView().showLoading();
        getManager().getAllLocations(request, new ApiCallback<LocationsResponse>() {
            @Override
            public void success(LocationsResponse res) {
                getView().hideLoading();
                getView().getListNewsSuccess(res.getData(), res.getMetaData());
            }

            @Override
            public void failure(RestError error) {
                getView().hideLoading();
                getView().onFail(error.message);
            }
        });
    }

    @Override
    public void getShareLink(String location_id) {
        getView().showLoading();
        getManager().getShareLink(location_id, new ApiCallback<ShareResponse>() {
            @Override
            public void success(ShareResponse res) {
                getView().hideLoading();
                getView().getShareLinkSuccess(res.getShareLink());
            }

            @Override
            public void failure(RestError error) {
                getView().hideLoading();
                getView().onFail(error.message);
            }
        });
    }

    @Override
    public void onChangeStopped(String id) {
        getView().showLoading();
        if (App.getUserInfo().getInfo() == null || App.getUserInfo().getInfo().getToken().isEmpty()) {
            getView().hideLoading();
            getView().onFail(App.getContext().getString(R.string.please_login));
            return;
        }
        ActionRequest request = new ActionRequest();
        request.setToken(App.getUserInfo().getInfo().getToken());
        request.setIdLocation(id);
        getManager().actionStop(request, new ApiCallback<BaseResponse>() {
            @Override
            public void success(BaseResponse res) {
                getView().notifyChangeStopped();
                getView().hideLoading();
            }

            @Override
            public void failure(RestError error) {
                getView().hideLoading();
                getView().onFail(error.message);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ObjectEvent objectEvent) {
        if (objectEvent == null || !isViewAttached())
            return;
        if (objectEvent.getKeyId().equals(KeyUtils.KEY_EVENT_LOCATIONS)) {
            Log.d("subscribe success", new Gson().toJson(objectEvent.getSocketLocation()));
            getView().notifyNewLocation(objectEvent.getSocketLocation());
        }
    }
}
