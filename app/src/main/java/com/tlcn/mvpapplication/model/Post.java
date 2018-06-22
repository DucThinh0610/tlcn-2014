package com.tlcn.mvpapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("count_dislike")
    @Expose
    private int count_dislike;
    @SerializedName("count_like")
    @Expose
    private int count_like;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("url_image")
    @Expose
    private String url_image;
    @SerializedName("user_id")
    @Expose
    private User user_id;
    @SerializedName("level")
    @Expose
    private double level;
    @SerializedName("is_like")
    @Expose
    private boolean is_like;
    @SerializedName("is_dislike")
    @Expose
    private boolean is_dislike;
    @SerializedName("location_id")
    @Expose
    private String idLocation;

    public String getIdLocation() {
        return idLocation;
    }

    public int getCount_dislike() {
        return count_dislike;
    }

    public void setCount_dislike(int count_dislike) {
        this.count_dislike = count_dislike;
    }

    public int getCount_like() {
        return count_like;
    }

    public void setCount_like(int count_like) {
        this.count_like = count_like;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public boolean isIs_like() {
        return is_like;
    }

    public void setIs_like(boolean is_like) {
        this.is_like = is_like;
    }

    public boolean isIs_dislike() {
        return is_dislike;
    }

    public void setIs_dislike(boolean is_dislike) {
        this.is_dislike = is_dislike;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId().equals(((Post) obj).getId());
    }
}
