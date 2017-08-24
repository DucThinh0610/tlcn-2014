package com.tlcn.mvpapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by tskil on 8/23/2017.
 */

public class Utilities {
    public static final float MIN_MAP_ZOOM = 0f;
    public static final float MAX_MAP_ZOOM = 23f;
    public static final float DEFAULT_MAP_ZOOM = 15f;

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }
}
