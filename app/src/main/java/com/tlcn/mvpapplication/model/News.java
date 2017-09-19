package com.tlcn.mvpapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by tskil on 9/12/2017.
 */

public class News implements Serializable{
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("rating")
    @Expose
    private double rating;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("num_like")
    @Expose
    private long num_like;
    @SerializedName("num_dislike")
    @Expose
    private long num_dislike;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;
    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("created_by")
    @Expose
    private String created_by;

    @SerializedName("num_rating")
    @Expose
    private long num_rating;
    public News(){

    }

    public News(long id, String title, double rating, String created, long num_like, long num_dislike, String description, double latitude, double longitude, boolean status, String created_by, long num_rating) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.created = created;
        this.num_like = num_like;
        this.num_dislike = num_dislike;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.created_by = created_by;
        this.num_rating = num_rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public long getNum_like() {
        return num_like;
    }

    public void setNum_like(long num_like) {
        this.num_like = num_like;
    }

    public long getNum_dislike() {
        return num_dislike;
    }

    public void setNum_dislike(long num_dislike) {
        this.num_dislike = num_dislike;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public long getNum_rating() {
        return num_rating;
    }

    public void setNum_rating(long num_rating) {
        this.num_rating = num_rating;
    }
}
