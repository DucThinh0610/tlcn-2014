package com.tlcn.mvpapplication.utils;

import android.util.Log;

import com.tlcn.mvpapplication.BuildConfig;

public class LogUtils {
    private static final String LOG_PREFIX = "LogUtils_";
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH = 30;

    public static String makeLogTag(String str) {
        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
        }

        return LOG_PREFIX + str;
    }

    public static String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }

    public static void LOGI(final String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message);
        }
    }

    public static void LOGI(final String tag, String message, Throwable cause) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message, cause);
        }
    }

    public static void LOGW(final String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message);
        }
    }

    public static void LOGW(final String tag, String message, Throwable cause) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message, cause);
        }
    }

    public static void LOGE(final String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message);
        }
    }

    public static void LOGE(final String tag, String message, Throwable cause) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message, cause);
        }
    }

    public static void d(String TAG, String message) {
        int maxLogSize = 2000;
        for(int i = 0; i <= message.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > message.length() ? message.length() : end;
            android.util.Log.d(TAG, message.substring(start, end));
        }
    }

    private LogUtils() {

    }
}
