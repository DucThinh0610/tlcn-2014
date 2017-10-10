package com.tlcn.mvpapplication.api.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ducthinh on 19/09/2017.
 */

public class BaseResponse implements Serializable {
    @SerializedName("code")
    @Expose
    public int code;
    @SerializedName("message")
    @Expose
    public String message;

    @Override
    public String toString() {
        return code +" "+message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
