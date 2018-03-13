package com.tlcn.mvpapplication.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.model.Post;

import java.io.Serializable;

/**
 * Created by apple on 3/12/18.
 */

public class DetailNewsResponse extends BaseResponse implements Serializable {
    @SerializedName("body")
    @Expose
    private Post data;

    public Post getData() {
        return data;
    }
}
