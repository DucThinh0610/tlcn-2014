package com.tlcn.mvpapplication.caches.storage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by apple on 10/4/17.
 */

public class NotificationStorage implements INotificationStorage {
    private final String TAG = LocationStorage.class.getSimpleName();
    private static final String PREF_NAME = "Notification";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context mContext;

    //TODO: Key of PREF
    private static final String STATE = "state";

    public NotificationStorage(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    @Override
    public void setNotificationState(boolean state) {
        editor.putBoolean(STATE,state);
        editor.commit();
    }

    @Override
    public boolean isNotificationOn() {
        return pref.getBoolean(STATE,true);
    }
}
