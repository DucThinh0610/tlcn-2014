package com.tlcn.mvpapplication.api.network;


import android.util.Log;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.app.App;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RestCallback<T> implements Callback<T> {

    private final int API_ERROR_NO_NETWORK = -1;
    private final int API_ERROR_TIMED_OUT = -2;
    private final int API_ERROR_UNKNOWN = -4;

    public abstract void success(T res);

    public abstract void failure(RestError error);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        Log.d("Response", response.message());

        if (response.isSuccessful()) {
            T bodyResponse = response.body();
            success(bodyResponse);
        } else {
            RestError error = new RestError(API_ERROR_UNKNOWN, "Unknown error");
            failure(error);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        Log.e("OnFailure", throwable.toString());

        RestError error = null;
        if (throwable instanceof IOException) {
            if (throwable instanceof SocketTimeoutException) {
                error = new RestError(API_ERROR_TIMED_OUT, "Request time out");
            } else {
                error = new RestError(API_ERROR_NO_NETWORK, "No internet connection");
            }
        }
        if (error == null) {
            error = new RestError(API_ERROR_UNKNOWN, "Unknown error");
        }
        failure(error);
    }


}