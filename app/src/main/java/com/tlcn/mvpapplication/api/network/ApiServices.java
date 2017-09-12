package com.tlcn.mvpapplication.api.network;

import com.tlcn.mvpapplication.model.Direction;
import com.tlcn.mvpapplication.model.UserInfoGit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {
    @GET("users/{user}")
    Call<UserInfoGit> getUserInfo(@Path("user") String user);

    @GET("/maps/api/directions/json")
    Call<Direction> getDirection(@Query("origin") String origin,
                                 @Query("destination") String destination,
                                 @Query("key") String key);
}
