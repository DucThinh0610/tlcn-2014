package com.tlcn.mvpapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by apple on 2/7/18.
 */

public class User implements Serializable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("full_name")
    @Expose
    private String full_name;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("fcm_token")
    @Expose
    private String fcm_token;
    @SerializedName("lasted_device")
    @Expose
    private String lasted_device;
    @SerializedName("total_news")
    @Expose
    private int total_news;
    @SerializedName("total_likes")
    @Expose
    private int total_likes;
    @SerializedName("total_dislikes")
    @Expose
    private int total_dislikes;
    @SerializedName("status_login")
    @Expose
    private int status_login;
    @SerializedName("type")
    @Expose
    private int type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    public String getLasted_device() {
        return lasted_device;
    }

    public void setLasted_device(String lasted_device) {
        this.lasted_device = lasted_device;
    }

    public int getTotal_news() {
        return total_news;
    }

    public void setTotal_news(int total_news) {
        this.total_news = total_news;
    }

    public int getTotal_likes() {
        return total_likes;
    }

    public void setTotal_likes(int total_likes) {
        this.total_likes = total_likes;
    }

    public int getTotal_dislikes() {
        return total_dislikes;
    }

    public void setTotal_dislikes(int total_dislikes) {
        this.total_dislikes = total_dislikes;
    }

    public int getStatus_login() {
        return status_login;
    }

    public void setStatus_login(int status_login) {
        this.status_login = status_login;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
