package com.tlcn.mvpapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;


/**
 * Created by tskil on 9/12/2017.
 */

public class News {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("rating")
    @Expose
    private float rating;
    @SerializedName("created")
    @Expose
    private Date created;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("num_like")
    @Expose
    private int numLike;
    @SerializedName("num_dislike")
    @Expose
    private int numDislike;
    public News(){

    }

    public News(String id, String title, float rating, Date created, String description ,int numLike ,int numDislike) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.created = created;
        this.description = description;
        this.numLike = numLike;
        this.numDislike = numDislike;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumLike() {
        return numLike;
    }

    public void setNumLike(int numLike) {
        this.numLike = numLike;
    }

    public int getNumDislike() {
        return numDislike;
    }

    public void setNumDislike(int numDislike) {
        this.numDislike = numDislike;
    }
}
