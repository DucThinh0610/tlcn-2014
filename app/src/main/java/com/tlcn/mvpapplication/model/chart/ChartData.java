package com.tlcn.mvpapplication.model.chart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChartData implements Serializable {
    @SerializedName("new_id")
    @Expose
    private String newId;
    @SerializedName("level")
    @Expose
    private double level;
    @SerializedName("count_like")
    @Expose
    private int countLike;
    @SerializedName("count_dislike")
    @Expose
    private int countDislike;
    @SerializedName("day")
    @Expose
    private int day;
    @SerializedName("hour")
    @Expose
    private int hour;

    public String getNewId() {
        return newId;
    }

    public double getLevel() {
        return level;
    }

    public int getCountLike() {
        return countLike;
    }

    public int getCountDislike() {
        return countDislike;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }
}
