package com.tlcn.mvpapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Locations implements Serializable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("location")
    @Expose
    private Point location;
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("current_level")
    @Expose
    private double current_level;
    @SerializedName("last_modify")
    @Expose
    private String last_modify;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("rate")
    @Expose
    private double rate;
    @SerializedName("lastest_image")
    @Expose
    private String latest_image;

    @SerializedName("average_rate")
    @Expose
    private double average_rate;

    @SerializedName("total_news")
    @Expose
    private int total_news;

    @SerializedName("total_level")
    @Expose
    private double total_level;

    @SerializedName("stop_count")
    @Expose
    private int stop_count;

    @SerializedName("is_save")
    @Expose
    private boolean is_save;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLat() {
        return location.getCoordinates().get(1);
    }

    public double getLng() {
        return location.getCoordinates().get(0);
    }

    public boolean isStatus() {
        return status;
    }

    public double getCurrent_level() {
        return current_level;
    }

    public String getLast_modify() {
        return last_modify;
    }

    public String getTitle() {
        return title;
    }

    public String getShortTitle() {
        return title.replace("Kẹt xe tại", "");
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLatest_image() {
        return latest_image;
    }

    public Point getLocation() {
        return location;
    }

    public double getRate() {
        return rate;
    }

    public double getAverage_rate() {
        return average_rate;
    }

    public int getTotal_news() {
        return total_news;
    }

    public double getTotal_level() {
        return total_level;
    }

    public int getStop_count() {
        return stop_count;
    }

    public boolean isIs_save() {
        return is_save;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setCurrent_level(double current_level) {
        this.current_level = current_level;
    }

    public void setLast_modify(String last_modify) {
        this.last_modify = last_modify;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setLatest_image(String latest_image) {
        this.latest_image = latest_image;
    }

    public void setAverage_rate(double average_rate) {
        this.average_rate = average_rate;
    }

    public void setTotal_news(int total_news) {
        this.total_news = total_news;
    }

    public void setTotal_level(int total_level) {
        this.total_level = total_level;
    }

    public void setStop_count(int stop_count) {
        this.stop_count = stop_count;
    }

    public void setIs_save(boolean is_save) {
        this.is_save = is_save;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId().equals(((Locations) obj).getId());
    }

}
