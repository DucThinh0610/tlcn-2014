package com.tlcn.mvpapplication.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.model.User;

/**
 * Created by apple on 2/7/18.
 */

public class LoginResponse extends BaseResponse {
    @SerializedName("body")
    @Expose
    private User data;

    public User getData() {
        return data;
    }
}
