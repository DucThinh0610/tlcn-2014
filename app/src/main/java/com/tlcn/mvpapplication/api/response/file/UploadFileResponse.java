package com.tlcn.mvpapplication.api.response.file;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.model.file.ImageFile;

/**
 * Created by ducthinh on 22/09/2017.
 */

public class UploadFileResponse extends BaseResponse{
    @SerializedName("body")
    @Expose
    private ImageFile imageFile;

    public ImageFile getImageFile() {
        return imageFile;
    }
}
