package com.tlcn.mvpapplication.api.network;

import com.tlcn.mvpapplication.api.request.action.ActionRequest;
import com.tlcn.mvpapplication.api.request.contribution.ContributionRequest;
import com.tlcn.mvpapplication.api.request.save.SaveRequest;
import com.tlcn.mvpapplication.api.request.user.LoginRequest;
import com.tlcn.mvpapplication.api.response.DetailLocationResponse;
import com.tlcn.mvpapplication.api.response.DetailNewsResponse;
import com.tlcn.mvpapplication.api.response.GetDirectionResponse;
import com.tlcn.mvpapplication.api.response.LocationsResponse;
import com.tlcn.mvpapplication.api.response.LoginResponse;
import com.tlcn.mvpapplication.api.response.NewsResponse;
import com.tlcn.mvpapplication.api.response.ShareResponse;
import com.tlcn.mvpapplication.api.response.chart.ChartResponse;
import com.tlcn.mvpapplication.api.response.file.UploadFileResponse;
import com.tlcn.mvpapplication.model.Result;
import com.tlcn.mvpapplication.model.UserInfoGit;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiServices {
    @GET("users/{user}")
    Call<UserInfoGit> getUserInfo(@Path("user") String user);

    @GET("/maps/api/directions/json")
    Call<GetDirectionResponse> getDirection(@Query("origin") String origin,
                                            @Query("destination") String destination,
                                            @Query("key") String key,
                                            @Query("alternatives") boolean alternatives,
                                            @Query("language") String language);

    @GET("test")
    Call<Result> test();


    @Multipart
    @POST("upload/image")
    Call<UploadFileResponse> uploadFile(@Query("token") String token, @Part MultipartBody.Part file);

    @POST("locations/contribute")
    Call<BaseResponse> contribute(@Query("token") String token, @Body ContributionRequest contribution);

    @POST("locations/save")
    Call<DetailLocationResponse> saveLocation(@Query("token") String token, @Body SaveRequest save);

    @PUT("locations/off")
    Call<BaseResponse> actionStop(@Query("token") String token, @Body ActionRequest action);

    @PUT("locations/on")
    Call<BaseResponse> actionOn(@Query("token") String token, @Body ActionRequest action);

    @GET("locations/{location_id}/share")
    Call<ShareResponse> shareLink(@Path("location_id") String location_id);

    @GET("locations/save")
    Call<LocationsResponse> getSavedLocations(@QueryMap Map<String,String> parameters);

    @GET("locations")
    Call<LocationsResponse> getAllLocations(@QueryMap Map<String, String> parameters);

    @GET("locations/loadByDistance")
    Call<LocationsResponse> getLocationsByDistance(@QueryMap Map<String, String> parameters);

    @GET("locations/favourite")
    Call<LocationsResponse> getFavouriteLocations(@QueryMap Map<String, Double> parameters);

    @GET("locations/{location_id}")
    Call<DetailLocationResponse> getDetailLocation(@Path("location_id") String location_id, @Query("token") String token);

    @POST("news/like")
    Call<DetailNewsResponse> likeNews(@Query("token") String token, @Body ActionRequest action);

    @POST("news/dislike")
    Call<DetailNewsResponse> dislikeNews(@Query("token") String token, @Body ActionRequest action);

    @GET("news")
    Call<NewsResponse> getNewsByLocation(@Query("location_id") String location_id, @Query("token") String token);

    @POST("user/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("user/logout")
    Call<BaseResponse> logout(@Query("token") String token);

    @GET("chart/{location_id}")
    Call<ChartResponse> getChartInfo(@Path("location_id") String id_location, @QueryMap Map<String,String> parameters);
}
