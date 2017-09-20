package com.tlcn.mvpapplication.mvp.main.fragment.Contribute.presenter;

import com.tlcn.mvpapplication.api.network.ApiCallback;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.api.network.RestError;
import com.tlcn.mvpapplication.api.request.contribution.ContributionRequest;
import com.tlcn.mvpapplication.base.BasePresenter;
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
    public void sendContribution(ContributionRequest contribution) {
//        getView().showLoading();
//        AppManager.http_api_server().from(ApiServices.class).contribute(contribution).enqueue(new RestCallback<Contribution>() {
//            @Override
//            public void success(Contribution res) {
//                getView().hideLoading();
//            }
//
//            @Override
//            public void failure(RestError error) {
//                getView().hideLoading();
//            }
//        });
//        /*AppManager.http_api_server().from(ApiServices.class).test().enqueue(new RestCallback<Result>() {
//            @Override
//            public void success(Result res) {
//                LogUtils.LOGE("response server",res.toString());
//            }
//
//            @Override
//            public void failure(RestError error) {
//
//            }
//        });*/
        getView().showLoading();
        getManager().addContribution(contribution, new ApiCallback<BaseResponse>() {
            @Override
            public void success(BaseResponse res) {
                getView().hideLoading();
                getView().onSuccess();
            }

            @Override
            public void failure(RestError error) {
                getView().onFail(error.message);
                getView().hideLoading();
            }
        });
    }
}
