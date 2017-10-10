package com.tlcn.mvpapplication.model.direction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ducthinh on 17/09/2017.
 */

public class Duration implements Serializable{
    @SerializedName("text")
    @Expose
    private String times;
    @SerializedName("value")
    @Expose
    private int value;

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
