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

    public ActionRequest(String user_id, int type, String news_id) {
        this.user_id = user_id;
        this.type = type;
        this.news_id = news_id;
    }
    @SerializedName("id")
    @Expose
    private String idLocation;

    public ActionRequest(String idLocation) {
        this.idLocation = idLocation;
    }
}
