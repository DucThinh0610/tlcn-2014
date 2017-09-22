package com.tlcn.mvpapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tskil on 9/22/2017.
 */

public class Image implements Serializable {
    @SerializedName("created_at")
    @Expose
    String created_at;

    @SerializedName("url")
    @Expose
    String url;

    @SerializedName("user_id")
    @Expose
    String user_id;

    @SerializedName("user_name")
    @Expose
    String user_name;
    public Image() {

    }

    public Image(String created_at, String url, String user_id) {
        this.created_at = created_at;
        this.url = url;
        this.user_id = user_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}

