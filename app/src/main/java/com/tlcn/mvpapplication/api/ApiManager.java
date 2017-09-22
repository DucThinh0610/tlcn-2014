package com.tlcn.mvpapplication.api;

import com.tlcn.mvpapplication.api.network.ApiCallback;
import com.tlcn.mvpapplication.api.network.ApiServices;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.api.network.RestCallback;
import com.tlcn.mvpapplication.api.network.RestError;
import com.tlcn.mvpapplication.api.request.contribution.ContributionRequest;
import com.tlcn.mvpapplication.api.request.home.GetInfoRequest;
import com.tlcn.mvpapplication.api.response.file.UploadFileResponse;
import com.tlcn.mvpapplication.api.response.home.GetInfoResponse;
import com.tlcn.mvpapplication.app.AppManager;

import okhttp3.MultipartBody;

public class ApiManager {
    public void addContribution(ContributionRequest request, final ApiCallback<BaseResponse> callback) {
        AppManager.http_local().from(ApiServices.class).contribute(request).enqueue(new RestCallback<BaseResponse>() {
            @Override
            public void success(BaseResponse res) {
                callback.success(res);
            }

            @Override
            public void failure(RestError error) {
                callback.failure(error);
            }
        });
    }

    public void getInfoPlace(GetInfoRequest request, final ApiCallback<GetInfoResponse> callback) {
        AppManager.http_local().from(ApiServices.class).getInfoPlace(request).enqueue(new RestCallback<GetInfoResponse>() {
            @Override
            public void success(GetInfoResponse res) {
                callback.success(res);
            }

            @Override
            public void failure(RestError error) {
                callback.failure(error);
            }
        });
    }

    public void uploadFile(final MultipartBody.Part file, final ApiCallback<UploadFileResponse> callback) {
        AppManager.http_local().from(ApiServices.class).uploadFile(file).enqueue(new RestCallback<UploadFileResponse>() {
            @Override
            public void success(UploadFileResponse res) {
                callback.success(res);
            }

            @Override
            public void failure(RestError error) {
                callback.failure(error);
            }
        });
    }
}
