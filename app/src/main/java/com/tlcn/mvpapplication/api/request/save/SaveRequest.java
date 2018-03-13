package com.tlcn.mvpapplication.api.request.save;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by apple on 10/4/17.
 */

public class SaveRequest implements Serializable {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("location_id")
    @Expose
    private String location_id;

    public SaveRequest() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }
}
