package com.tlcn.mvpapplication.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.model.Locations;

/**
 * Created by apple on 3/6/18.
 */

public class DetailLocationResponse extends BaseResponse {
    @SerializedName("body")
    @Expose
    private Locations data;

    public Locations getData() {
        return data;
    }
}
