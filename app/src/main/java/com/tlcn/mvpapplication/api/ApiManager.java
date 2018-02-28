package com.tlcn.mvpapplication.api;

import com.tlcn.mvpapplication.api.network.ApiCallback;
import com.tlcn.mvpapplication.api.network.ApiServices;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.api.network.RestCallback;
import com.tlcn.mvpapplication.api.network.RestError;
import com.tlcn.mvpapplication.api.request.action.ActionRequest;
import com.tlcn.mvpapplication.api.request.chart.ChartRequest;
import com.tlcn.mvpapplication.api.request.contribution.ContributionRequest;
import com.tlcn.mvpapplication.api.request.user.LoginRequest;
import com.tlcn.mvpapplication.api.request.save.SaveRequest;
import com.tlcn.mvpapplication.api.request.user.LogoutRequest;
import com.tlcn.mvpapplication.api.response.LoginResponse;
import com.tlcn.mvpapplication.api.response.ShareResponse;
import com.tlcn.mvpapplication.api.response.chart.ChartResponse;
import com.tlcn.mvpapplication.app.AppManager;

public class ApiManager {
    public void addContribution(ContributionRequest request, final ApiCallback<BaseResponse> callback) {
        AppManager.http_firebase_server().from(ApiServices.class).contribute(request).enqueue(new RestCallback<BaseResponse>() {
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

//    public void getInfoPlace(GetInfoRequest request, final ApiCallback<GetInfoResponse> callback) {
//        AppManager.http_firebase_server().from(ApiServices.class).getInfoPlace(request).enqueue(new RestCallback<GetInfoResponse>() {
//            @Override
//            public void success(GetInfoResponse res) {
//                callback.success(res);
//            }
//
//            @Override
//            public void failure(RestError error) {
//                callback.failure(error);
//            }
//        });
//    }

//    public void uploadFile(final MultipartBody.Part file, final ApiCallback<UploadFileResponse> callback) {
//        AppManager.http_firebase_server().from(ApiServices.class).uploadFile(file).enqueue(new RestCallback<UploadFileResponse>() {
//            @Override
//            public void success(UploadFileResponse res) {
//                callback.success(res);
//            }
//
//            @Override
//            public void failure(RestError error) {
//                callback.failure(error);
//            }
//        });
//    }

    public void action(ActionRequest request, final ApiCallback<BaseResponse> callback) {
        AppManager.http_firebase_server().from(ApiServices.class).action(request).enqueue(new RestCallback<BaseResponse>() {
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

    public void actionStop(ActionRequest request, final ApiCallback<BaseResponse> callback) {
        AppManager.http_firebase_server().from(ApiServices.class).actionStop(request).enqueue(new RestCallback<BaseResponse>() {
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

    public void actionOn(ActionRequest request, final ApiCallback<BaseResponse> callback) {
        AppManager.http_firebase_server().from(ApiServices.class).actionOn(request).enqueue(new RestCallback<BaseResponse>() {
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

    public void saveLocation(SaveRequest request, final ApiCallback<BaseResponse> callback) {
        AppManager.http_firebase_server().from(ApiServices.class).saveLocation(request).enqueue(new RestCallback<BaseResponse>() {

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

    public void pushNotificationToken(String user_id, String token, final ApiCallback<BaseResponse> callback) {
        AppManager.http_firebase_server().from(ApiServices.class).push_notification(user_id, token).enqueue(new RestCallback<BaseResponse>() {
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

    public void login(LoginRequest request, final ApiCallback<LoginResponse> callback) {
        AppManager.http_api_v1_server().from(ApiServices.class).login(request).enqueue(new RestCallback<LoginResponse>() {
            @Override
            public void success(LoginResponse res) {
                callback.success(res);
            }

            @Override
            public void failure(RestError error) {
                callback.failure(error);
            }
        });
    }

    public void logout(LogoutRequest request, final ApiCallback<BaseResponse> callback) {
        AppManager.http_firebase_server().from(ApiServices.class).logout(request).enqueue(new RestCallback<BaseResponse>() {
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

    public void getShareLink(String location_id, final ApiCallback<ShareResponse> callback) {
        AppManager.http_firebase_server().from(ApiServices.class).shareLink(location_id).enqueue(new RestCallback<ShareResponse>() {
            @Override
            public void success(ShareResponse res) {
                callback.success(res);
            }

            @Override
            public void failure(RestError error) {
                callback.failure(error);
            }
        });
    }

    public void getInfoChart(String id_location, ChartRequest chartRequest, final ApiCallback<ChartResponse> callback) {
        AppManager.http_firebase_server().from(ApiServices.class).getChartInfo(id_location, chartRequest).enqueue(new RestCallback<ChartResponse>() {
            @Override
            public void success(ChartResponse res) {
                callback.success(res);
            }

            @Override
            public void failure(RestError error) {
                callback.failure(error);
            }
        });
    }
}
