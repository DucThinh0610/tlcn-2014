package com.tlcn.mvpapplication.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.model.MetaData;
import com.tlcn.mvpapplication.model.Post;

import java.util.List;

/**
 * Created by apple on 3/6/18.
 */

public class NewsResponse extends BaseResponse {
    @SerializedName("body")
    @Expose
    private List<Post> data;

    @SerializedName("meta_data")
    @Expose
    private MetaData metaData;

    public List<Post> getData() {
        return data;
    }

    public MetaData getMetaData() {
        return metaData;
    }
}
