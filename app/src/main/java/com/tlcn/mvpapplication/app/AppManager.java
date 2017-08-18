package com.tlcn.mvpapplication.app;

import android.content.Context;

import com.tlcn.mvpapplication.BuildConfig;
import com.tlcn.mvpapplication.api.HttpHelper;

public class AppManager {

    private static Context context;
    private static HttpHelper httpApi = null;

    public static void load(Context context) {
        AppManager.context = context;
    }

    synchronized static public HttpHelper http_api() {
        if (httpApi == null) httpApi = new HttpHelper(BuildConfig.API_URL);
        return httpApi;
    }
}
