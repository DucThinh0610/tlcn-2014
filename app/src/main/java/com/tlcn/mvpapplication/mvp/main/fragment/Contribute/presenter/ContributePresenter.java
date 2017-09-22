package com.tlcn.mvpapplication.mvp.main.fragment.Contribute.presenter;

import com.tlcn.mvpapplication.api.network.ApiCallback;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.api.network.RestError;
import com.tlcn.mvpapplication.api.request.contribution.ContributionRequest;
import com.tlcn.mvpapplication.api.response.file.UploadFileResponse;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.mvp.main.fragment.Contribute.view.IContributeView;

import okhttp3.MultipartBody;

public class ContributePresenter extends BasePresenter implements IContributePresenter {
    private MultipartBody.Part mtlPart;

    public void setMtlPart(MultipartBody.Part mtlPart) {
        this.mtlPart = mtlPart;
    }

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
        getView().showLoading();
        getManager().addContribution(contribution, new ApiCallback<BaseResponse>() {
            @Override
            public void success(BaseResponse res) {
                getView().hideLoading();
                uploadImage();
            }

            @Override
            public void failure(RestError error) {
                getView().onFail(error.message);
                getView().hideLoading();
            }
        });
    }

    @Override
    public void uploadImage() {
        if (mtlPart == null) {
            return;
        }
        getManager().uploadFile(this.mtlPart, new ApiCallback<UploadFileResponse>() {
            @Override
            public void success(UploadFileResponse res) {
                getView().onSuccess();
            }

            @Override
            public void failure(RestError error) {
                getView().onFail(error.message);
            }
        });
    }
}
