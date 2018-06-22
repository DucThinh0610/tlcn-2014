package com.tlcn.mvpapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by apple on 3/6/18.
 */

public class MetaData implements Serializable {
    @SerializedName("total")
    @Expose
    private int total;

    @SerializedName("current_page")
    @Expose
    private int current_page;

    @SerializedName("has_more_page")
    @Expose
    private boolean has_more_page;

    public int getTotal() {
        return total;
    }

    public void increaseTotal(int count) {
        total += count;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public boolean isHas_more_page() {
        return has_more_page;
    }

    public void increasePage(int count) {
        current_page += count;
    }
}
