package com.tlcn.mvpapplication.api.request.action;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ActionRequest implements Serializable {
    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("news_id")
    @Expose
    private String news_id;

    @SerializedName("location_id")
    @Expose
    private String idLocation;

    public ActionRequest() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(String idLocation) {
        this.idLocation = idLocation;
    }

    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }
}
