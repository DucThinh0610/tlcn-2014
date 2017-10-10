package com.tlcn.mvpapplication.model.direction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ducthinh on 17/09/2017.
 */

public class Distance implements Serializable {
    @SerializedName("text")
    @Expose
    private String length;
    @SerializedName("value")
    @Expose
    private int value;

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
