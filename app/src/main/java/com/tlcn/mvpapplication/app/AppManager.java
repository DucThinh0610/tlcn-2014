package com.tlcn.mvpapplication.app;

import android.content.Context;

import com.tlcn.mvpapplication.BuildConfig;
import com.tlcn.mvpapplication.api.HttpHelper;

public class AppManager {

    private static Context context;
    private static HttpHelper httpApi = null;
    private static HttpHelper httpDirection = null;

    public static void load(Context context) {
        AppManager.context = context;
    }

    synchronized static public HttpHelper http_api() {
        if (httpApi == null) httpApi = new HttpHelper(BuildConfig.API_URL);
        return httpApi;
    }

    synchronized static public HttpHelper http_api_direction() {
        if (httpDirection == null) httpDirection = new HttpHelper(BuildConfig.DIRECTION_URL_API);
        return httpDirection;
    }
}
