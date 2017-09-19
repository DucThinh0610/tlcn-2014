package com.tlcn.mvpapplication.api.network;

public abstract class ApiCallback<T extends BaseResponse> {

    public abstract void success(T res);

    public abstract void failure(RestError error);

}