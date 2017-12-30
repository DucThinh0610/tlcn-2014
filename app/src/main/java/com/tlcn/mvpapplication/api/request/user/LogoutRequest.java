package com.tlcn.mvpapplication.api.request.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by apple on 12/30/17.
 */

public class LogoutRequest implements Serializable {
    @SerializedName("user_id")
    @Expose
    private String user_id;

    public String getUser_id() {
        return user_id;
    }

    public LogoutRequest(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
