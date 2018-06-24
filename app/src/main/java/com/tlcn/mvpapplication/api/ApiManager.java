package com.tlcn.mvpapplication.api;

import com.tlcn.mvpapplication.api.network.ApiCallback;
import com.tlcn.mvpapplication.api.network.ApiServices;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.api.network.RestCallback;
import com.tlcn.mvpapplication.api.network.RestError;
import com.tlcn.mvpapplication.api.request.BaseListRequest;
import com.tlcn.mvpapplication.api.request.FavouriteLocationsRequest;
import com.tlcn.mvpapplication.api.request.LocationByDistanceRequest;
import com.tlcn.mvpapplication.api.request.action.ActionRequest;
import com.tlcn.mvpapplication.api.request.chart.ChartRequest;
import com.tlcn.mvpapplication.api.request.contribution.ContributionRequest;
import com.tlcn.mvpapplication.api.request.save.SaveRequest;
import com.tlcn.mvpapplication.api.request.user.LoginRequest;
import com.tlcn.mvpapplication.api.response.DetailLocationResponse;
import com.tlcn.mvpapplication.api.response.DetailNewsResponse;
import com.tlcn.mvpapplication.api.response.LocationsResponse;
import com.tlcn.mvpapplication.api.response.LoginResponse;
import com.tlcn.mvpapplication.api.response.NewsResponse;
import com.tlcn.mvpapplication.api.response.ShareResponse;
import com.tlcn.mvpapplication.api.response.chart.ChartResponse;
import com.tlcn.mvpapplication.api.response.file.UploadFileResponse;
import com.tlcn.mvpapplication.app.AppManager;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;

public class ApiManager {
    public void addContribution(ContributionRequest request, final ApiCallback<BaseResponse> callback) {
        AppManager.http_local().from(ApiServices.class).contribute(request.getToken(), request).enqueue(new RestCallback<BaseResponse>() {
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


    public void uploadFile(String token, final MultipartBody.Part file, final ApiCallback<UploadFileResponse> callback) {
        AppManager.http_local().from(ApiServices.class).uploadFile(token, file).enqueue(new RestCallback<UploadFileResponse>() {
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

    public void actionStop(ActionRequest request, final ApiCallback<BaseResponse> callback) {
        AppManager.http_local().from(ApiServices.class).actionStop(request.getToken(), request).enqueue(new RestCallback<BaseResponse>() {
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
        AppManager.http_local().from(ApiServices.class).actionOn(request.getToken(), request).enqueue(new RestCallback<BaseResponse>() {
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

    public void saveLocation(SaveRequest request, final ApiCallback<DetailLocationResponse> callback) {
        AppManager.http_local().from(ApiServices.class).saveLocation(request.getToken(), request).enqueue(new RestCallback<DetailLocationResponse>() {

            @Override
            public void success(DetailLocationResponse res) {
                callback.success(res);
            }

            @Override
            public void failure(RestError error) {
                callback.failure(error);
            }
        });
    }

    public void login(LoginRequest request, final ApiCallback<LoginResponse> callback) {
        AppManager.http_local().from(ApiServices.class).login(request).enqueue(new RestCallback<LoginResponse>() {
            public void success(LoginResponse res) {
                callback.success(res);
            }

            @Override
            public void failure(RestError error) {
                callback.failure(error);
            }
        });
    }

    public void logout(String token, final ApiCallback<BaseResponse> callback) {
        AppManager.http_local().from(ApiServices.class).logout(token).enqueue(new RestCallback<BaseResponse>() {
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
        AppManager.http_local().from(ApiServices.class).shareLink(location_id).enqueue(new RestCallback<ShareResponse>() {
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
        Map<String, String> params = new HashMap<>();
        params.put("start_date", chartRequest.getStartDate());
        params.put("end_date", chartRequest.getEndDate());
        AppManager.http_local().from(ApiServices.class).getChartInfo(id_location, params).enqueue(new RestCallback<ChartResponse>() {
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

    public void getAllLocations(BaseListRequest request, final ApiCallback<LocationsResponse> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("limit", String.valueOf(request.getLimit()));
        params.put("page", String.valueOf(request.getPage()));
        AppManager.http_local().from(ApiServices.class).getAllLocations(params).enqueue(new RestCallback<LocationsResponse>() {
            @Override
            public void success(LocationsResponse res) {
                callback.success(res);
            }

            @Override
            public void failure(RestError error) {
                callback.failure(error);
            }
        });
    }

    public void getLocationsByDistance(LocationByDistanceRequest request, final ApiCallback<LocationsResponse> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("latitude", String.valueOf(request.getLatitude()));
        params.put("longitude", String.valueOf(request.getLongitude()));
        params.put("distance", String.valueOf(request.getDistance()));
        AppManager.http_local().from(ApiServices.class).getLocationsByDistance(params).enqueue(new RestCallback<LocationsResponse>() {
            @Override
            public void success(LocationsResponse res) {
                callback.success(res);
            }

            @Override
            public void failure(RestError error) {
                callback.failure(error);
            }
        });
    }

    public void getFavouriteLocations(FavouriteLocationsRequest request, final ApiCallback<LocationsResponse> callback) {
        Map<String, Double> params = new HashMap<>();
        params.put("lat1", request.getLatLng1().latitude);
        params.put("long1", request.getLatLng1().longitude);
        params.put("distance", request.getDistance());
        if (request.getLatLng2() != null) {
            params.put("lat2", request.getLatLng2().latitude);
            params.put("long2", request.getLatLng2().longitude);
            if (request.getLatLng3() != null) {
                params.put("lat3", request.getLatLng3().latitude);
                params.put("long3", request.getLatLng3().longitude);
            }
        }

        AppManager.http_local().from(ApiServices.class).getFavouriteLocations(params).enqueue(new RestCallback<LocationsResponse>() {
            @Override
            public void success(LocationsResponse res) {
                callback.success(res);
            }

            @Override
            public void failure(RestError error) {
                callback.failure(error);
            }
        });
    }

    public void getDetailLocation(String token, String location_id, final ApiCallback<DetailLocationResponse> callback) {
        AppManager.http_local().from(ApiServices.class).getDetailLocation(location_id, token).enqueue(new RestCallback<DetailLocationResponse>() {
            @Override
            public void success(DetailLocationResponse res) {
                callback.success(res);
            }

            @Override
            public void failure(RestError error) {
                callback.failure(error);
            }
        });
    }

    public void getSavedLocations(String token, BaseListRequest request, final ApiCallback<LocationsResponse> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        if (request.getPage() != 0) {
            params.put("page", String.valueOf(request.getPage()));
        }
        if (request.getLimit() != 0) {
            params.put("limit", String.valueOf(request.getLimit()));
        }
        AppManager.http_local().from(ApiServices.class).getSavedLocations(params).enqueue(new RestCallback<LocationsResponse>() {
            @Override
            public void success(LocationsResponse res) {
                callback.success(res);
            }

            @Override
            public void failure(RestError error) {
                callback.failure(error);
            }
        });
    }

    public void getListNews(String token, String location_id, final ApiCallback<NewsResponse> callback) {
        AppManager.http_local().from(ApiServices.class).getNewsByLocation(location_id, token).enqueue(new RestCallback<NewsResponse>() {
            @Override
            public void success(NewsResponse res) {
                callback.success(res);
            }

            @Override
            public void failure(RestError error) {
                callback.failure(error);
            }
        });
    }

    public void likeNews(ActionRequest request, final ApiCallback<DetailNewsResponse> callback) {
        AppManager.http_local().from(ApiServices.class).likeNews(request.getToken(), request).enqueue(new RestCallback<DetailNewsResponse>() {
            @Override
            public void success(DetailNewsResponse res) {
                callback.success(res);
            }

            @Override
            public void failure(RestError error) {
                callback.failure(error);
            }
        });
    }

    public void dislikeNews(ActionRequest request, final ApiCallback<DetailNewsResponse> callback) {
        AppManager.http_local().from(ApiServices.class).dislikeNews(request.getToken(), request).enqueue(new RestCallback<DetailNewsResponse>() {
            @Override
            public void success(DetailNewsResponse res) {
                callback.success(res);
            }

            @Override
            public void failure(RestError error) {
                callback.failure(error);
            }
        });
    }

    public void getTrafficJamLocation(final ApiCallback<LocationsResponse> callback){
        AppManager.http_local().from(ApiServices.class).getTrafficJamLocation().enqueue(new RestCallback<LocationsResponse>() {
            @Override
            public void success(LocationsResponse res) {
                callback.success(res);
            }

            @Override
            public void failure(RestError error) {
                callback.failure(error);
            }
        });
    }
}
