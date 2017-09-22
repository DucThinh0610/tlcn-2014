package com.tlcn.mvpapplication.caches.storage;

import android.content.SharedPreferences;

/**
 * Created by ducthinh on 22/09/2017.
 */

public class LocalStorage {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;

    public LocalStorage(SharedPreferences sharedPreferences) {
        this.mSharedPreferences = sharedPreferences;
    }

    private static final String ASK_EXTEND_AGAIN = "ASK_EXTEND_AGAIN";
}
