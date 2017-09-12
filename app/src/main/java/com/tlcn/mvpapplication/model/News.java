package com.tlcn.mvpapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;

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
    @SerializedName("created")
    @Expose
    private Date created;
    @SerializedName("description")
    @Expose
    private String description;
}
