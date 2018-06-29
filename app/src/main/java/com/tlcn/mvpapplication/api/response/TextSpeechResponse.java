package com.tlcn.mvpapplication.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tlcn.mvpapplication.api.network.BaseResponse;

public class TextSpeechResponse{
    @SerializedName("async")
    @Expose
    private String async;
    @SerializedName("error")
    @Expose
    private int error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("request_id")
    @Expose
    private String requestId;

    public String getAsync() {
        return async;
    }

    public int getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getRequestId() {
        return requestId;
    }
}
