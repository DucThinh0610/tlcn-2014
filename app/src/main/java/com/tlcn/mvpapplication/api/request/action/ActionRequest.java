package com.tlcn.mvpapplication.api.request.action;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ActionRequest implements Serializable {
    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("type")
    @Expose
    private Integer type;

    @SerializedName("news_id")
    @Expose
    private String news_id;

    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("id")
    @Expose
    private String idLocation;

    public ActionRequest() {
    }

    public ActionRequest(String user_id, int type, String news_id) {
        this.user_id = user_id;
        this.type = type;
        this.news_id = news_id;
    }


    public ActionRequest(String idLocation, String time) {
        this.idLocation = idLocation;
        this.time = time;
    }

    public ActionRequest(String idLocation, String time, String user_id) {
        this.idLocation = idLocation;
        this.time = time;
        this.user_id = user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setIdLocation(String idLocation) {
        this.idLocation = idLocation;
    }
}
