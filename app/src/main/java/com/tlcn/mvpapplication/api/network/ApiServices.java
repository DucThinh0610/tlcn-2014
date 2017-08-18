package com.tlcn.mvpapplication.api.network;

import com.tlcn.mvpapplication.model.UserInfoGit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiServices {
    @GET("users/{user}")
    Call<UserInfoGit> getUserInfo(@Path("user") String user);
}
