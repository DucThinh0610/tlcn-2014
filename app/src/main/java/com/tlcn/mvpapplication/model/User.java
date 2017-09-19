package com.tlcn.mvpapplication.model;

/**
 * Created by tskil on 9/19/2017.
 */

public class User {
    String id;
    String imageUrl;
    String name;

    public User() {

    }

    public User(String id, String imageUrl, String name) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
