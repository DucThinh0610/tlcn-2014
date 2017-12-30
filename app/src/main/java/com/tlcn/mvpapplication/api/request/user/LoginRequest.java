package com.tlcn.mvpapplication.api.request.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by apple on 12/5/17.
 */

public class LoginRequest implements Serializable {
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("device_id")
    @Expose
    private String device_id;

    public LoginRequest(String user_id, String token, String device_id) {
        this.user_id = user_id;
        this.token = token;
        this.device_id = device_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }
}
