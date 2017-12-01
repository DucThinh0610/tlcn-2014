package com.tlcn.mvpapplication.api.network;

import com.tlcn.mvpapplication.api.request.action.ActionRequest;
import com.tlcn.mvpapplication.api.request.contribution.ContributionRequest;
import com.tlcn.mvpapplication.api.request.home.GetInfoRequest;
import com.tlcn.mvpapplication.api.request.save.SaveRequest;
import com.tlcn.mvpapplication.api.response.GetDirectionResponse;
import com.tlcn.mvpapplication.api.response.ShareResponse;
import com.tlcn.mvpapplication.api.response.file.UploadFileResponse;
import com.tlcn.mvpapplication.api.response.home.GetInfoResponse;
import com.tlcn.mvpapplication.model.Result;
import com.tlcn.mvpapplication.model.UserInfoGit;

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

public interface ApiServices {
    @GET("users/{user}")
    Call<UserInfoGit> getUserInfo(@Path("user") String user);

    @GET("/maps/api/directions/json")
    Call<GetDirectionResponse> getDirection(@Query("origin") String origin,
                                            @Query("destination") String destination,
                                            @Query("key") String key,
                                            @Query("alternatives") boolean alternatives,
                                            @Query("language") String language);

    @POST("contribute")
    Call<BaseResponse> contribute(@Body ContributionRequest contribution);

    @GET("test")
    Call<Result> test();

    @POST("info")
    Call<GetInfoResponse> getInfoPlace(@Body GetInfoRequest getInfoRequest);

    @Multipart
    @POST("upload")
    Call<UploadFileResponse> uploadFile(
            @Part MultipartBody.Part file
    );

    @POST("action")
    Call<BaseResponse> action(@Body ActionRequest action);

    @PUT("stopped")
    Call<BaseResponse> actionStop(@Body ActionRequest action);

    @POST("save")
    Call<BaseResponse> saveLocation(@Body SaveRequest save);

    @PUT("on")
    Call<BaseResponse> actionOn(@Body ActionRequest action);

    @POST("{user_id}")
    Call<BaseResponse> push_notification(@Path("user_id") String user_id, @Query("token") String token);

    @GET("share/{location_id}")
    Call<ShareResponse> shareLink(@Path("location_id") String location_id);
}
