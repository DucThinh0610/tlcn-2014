package com.tlcn.mvpapplication.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.model.ShareLink;

/**
 * Created by apple on 12/1/17.
 */

public class ShareResponse extends BaseResponse {
    @SerializedName("body")
    @Expose
    private ShareLink shareLink;

    public ShareLink getShareLink() {
        return shareLink;
    }
}
