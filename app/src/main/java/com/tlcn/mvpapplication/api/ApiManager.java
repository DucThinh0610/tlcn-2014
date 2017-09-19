package com.tlcn.mvpapplication.api;

import com.tlcn.mvpapplication.api.network.ApiCallback;
import com.tlcn.mvpapplication.api.network.ApiServices;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.api.network.RestCallback;
import com.tlcn.mvpapplication.api.network.RestError;
import com.tlcn.mvpapplication.api.request.contribution.ContributionRequest;
import com.tlcn.mvpapplication.app.AppManager;

public class ApiManager {
    public void addContribution(ContributionRequest request, final ApiCallback<BaseResponse> callback) {
        AppManager.http_api_server().from(ApiServices.class).contribute(request).enqueue(new RestCallback<BaseResponse>() {
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
}
