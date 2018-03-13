package com.tlcn.mvpapplication.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.model.MetaData;

import java.util.List;

/**
 * Created by apple on 3/6/18.
 */

public class LocationsResponse extends BaseResponse {
    @SerializedName("body")
    @Expose
    private List<Locations> data;

    @SerializedName("meta_data")
    @Expose
    private MetaData metaData;

    public List<Locations> getData() {
        return data;
    }

    public MetaData getMetaData() {
        return metaData;
    }
}
