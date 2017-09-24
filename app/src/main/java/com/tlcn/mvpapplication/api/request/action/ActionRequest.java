package com.tlcn.mvpapplication.api.request.action;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tskil on 9/24/2017.
 */

public class ActionRequest implements Serializable {
    @SerializedName("id")
    @Expose
    long id;

    @SerializedName("type")
    @Expose
    int type;

    public ActionRequest(){

    }
    public ActionRequest(int id, int type) {
        this.id = id;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
