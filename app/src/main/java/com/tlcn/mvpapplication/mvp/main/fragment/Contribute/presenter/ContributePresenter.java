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
    public ContributionRequest contribution = new ContributionRequest();

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
    public void sendContribution() {
        getManager().addContribution(contribution, new ApiCallback<BaseResponse>() {
            @Override
            public void success(BaseResponse res) {
                getView().hideLoading();
                contribution = new ContributionRequest();
                getView().onSuccess();
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
        getView().showLoading();
        if(mtlPart==null){
            sendContribution();
        }
        else {
            getManager().uploadFile(this.mtlPart, new ApiCallback<UploadFileResponse>() {
                @Override
                public void success(UploadFileResponse res) {
                    mtlPart = null;
                    contribution.setFile(res.getImageFile().getUrl());
                    sendContribution();
                    getView().removeImageView();
                }

                @Override
                public void failure(RestError error) {
                    sendContribution();
                }
            });
        }
    }
}
