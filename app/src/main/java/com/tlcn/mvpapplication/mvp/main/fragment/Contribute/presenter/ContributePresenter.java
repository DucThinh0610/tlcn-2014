package com.tlcn.mvpapplication.mvp.main.fragment.Contribute.presenter;

import com.tlcn.mvpapplication.api.network.ApiServices;
import com.tlcn.mvpapplication.api.network.RestCallback;
import com.tlcn.mvpapplication.api.network.RestError;
import com.tlcn.mvpapplication.app.AppManager;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.model.Contribute;
import com.tlcn.mvpapplication.model.Result;
import com.tlcn.mvpapplication.mvp.main.fragment.Contribute.view.IContributeView;
import com.tlcn.mvpapplication.utils.LogUtils;

/**
 * Created by tskil on 9/16/2017.
 */

public class ContributePresenter extends BasePresenter implements IContributePresenter {

    public void attachView(IContributeView view) {
        super.attachView(view);
    }
    public IContributeView getView() {
        return (IContributeView) getIView();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void sendContribution(Contribute contribute) {
        getView().showLoading();
        AppManager.http_api_server().from(ApiServices.class).contribute(contribute).enqueue(new RestCallback<Result>() {
            @Override
            public void success(Result res) {
                LogUtils.LOGE("RESPONE",res.toString());
            }

            @Override
            public void failure(RestError error) {

            }
        });
    }
}
