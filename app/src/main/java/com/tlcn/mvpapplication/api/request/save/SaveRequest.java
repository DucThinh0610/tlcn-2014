package com.tlcn.mvpapplication.api.request.save;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by apple on 10/4/17.
 */

public class SaveRequest implements Serializable {
    @SerializedName("location_id")
    @Expose
    String location_id;

    @SerializedName("user_id")
    @Expose
    String user_id;

    public SaveRequest(){

    }

    public SaveRequest(String location_id, String user_id) {
        this.location_id = location_id;
        this.user_id = user_id;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
