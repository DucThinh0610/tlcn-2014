package com.tlcn.mvpapplication.mvp.main.fragment.Contribute.presenter;

import com.tlcn.mvpapplication.api.network.ApiServices;
import com.tlcn.mvpapplication.api.network.RestCallback;
import com.tlcn.mvpapplication.api.network.RestError;
import com.tlcn.mvpapplication.app.AppManager;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.model.Contribution;
import com.tlcn.mvpapplication.mvp.main.fragment.Contribute.view.IContributeView;

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
    public void sendContribution(Contribution contribution) {
        getView().showLoading();
        AppManager.http_api_server().from(ApiServices.class).contribute(contribution).enqueue(new RestCallback<Contribution>() {
            @Override
            public void success(Contribution res) {

            }

            @Override
            public void failure(RestError error) {

            }
        });
    }
}
