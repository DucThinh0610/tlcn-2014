package com.tlcn.mvpapplication.api.network;

import com.tlcn.mvpapplication.api.request.contribution.ContributionRequest;
import com.tlcn.mvpapplication.api.response.GetDirectionResponse;
import com.tlcn.mvpapplication.model.Contribution;
import com.tlcn.mvpapplication.model.Result;
import com.tlcn.mvpapplication.model.UserInfoGit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {
    @GET("users/{user}")
    Call<UserInfoGit> getUserInfo(@Path("user") String user);

    @GET("/maps/api/directions/json")
    Call<GetDirectionResponse> getDirection(@Query("origin") String origin,
                                            @Query("destination") String destination,
                                            @Query("key") String key,
                                            @Query("alternatives") boolean alternatives);

    @POST("contribute")
    Call<BaseResponse> contribute(@Body ContributionRequest contribution);

    @GET("test")
    Call<Result> test();
}
