package com.tlcn.mvpapplication.app;

import android.content.Context;

import com.tlcn.mvpapplication.BuildConfig;
import com.tlcn.mvpapplication.api.HttpHelper;

public class AppManager {

    private static Context context;
    private static HttpHelper httpApi = null;
    private static HttpHelper httpDirection = null;
    private static HttpHelper httpServer = null;
    private static HttpHelper httpServerV1 = null;
    private static HttpHelper httpApiFpt = null;

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

    synchronized static public HttpHelper http_api_server() {
        if (httpServer == null) httpServer = new HttpHelper(BuildConfig.SERVER_URL_API);
        return httpServer;
    }

    synchronized static public HttpHelper http_local() {
        if (httpApi == null) httpApi = new HttpHelper(URL_LOCAL);
        return httpApi;
    }

    synchronized static public HttpHelper http_fpt() {
        if (httpApiFpt == null) httpApiFpt = new HttpHelper(URL_API_FPT);
        return httpApiFpt;
    }

    synchronized static public HttpHelper http_firebase_server() {
        if (httpServer == null) httpServer = new HttpHelper(BuildConfig.SERVER_FIREBASE_API);
        return httpServer;
    }

    synchronized static public HttpHelper http_api_v1_server() {
        if (httpServerV1 == null) httpServerV1 = new HttpHelper(BuildConfig.SERVER_API_V1);
        return httpServerV1;
    }

    public final static String URL_LOCAL = "http://150.95.110.242:3000/api/";
    public final static String URL_IMAGE = "http://150.95.110.242:3000";
    public final static String URL_SOCKET = "http://150.95.110.242:3000/";
    public final static String URL_API_FPT = "http://api.openfpt.vn/";
}
