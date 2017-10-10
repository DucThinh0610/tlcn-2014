package com.tlcn.mvpapplication.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.FacebookSdk;
import com.tlcn.mvpapplication.caches.storage.LocationStorage;
import com.tlcn.mvpapplication.caches.storage.NotificationStorage;

public class App extends Application {
    private GoogleApiHelper googleApiHelper;
    private static App mInstance;
    private LocationStorage mLocationStorage;
    private NotificationStorage mNotificationStorage;
    private static Context mContext;
    private static SharedPreferences mSharedPreferences;

    @Override

    public void onCreate() {
        super.onCreate();
        AppManager.load(this);
        mInstance = this;
        googleApiHelper = new GoogleApiHelper(mInstance);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mContext = getApplicationContext();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    public GoogleApiHelper getGoogleApiHelperInstance() {
        return this.googleApiHelper;
    }


    public LocationStorage getLocationStorageInstance() {
        if (mLocationStorage == null) {
            mLocationStorage = new LocationStorage(this);
        }
        return mLocationStorage;
    }

    public NotificationStorage getNotificationStorageInstance() {
        if (mNotificationStorage == null) {
            mNotificationStorage = new NotificationStorage(this);
        }
        return mNotificationStorage;
    }

    public static NotificationStorage getNotificationStorage() {
        return getInstance().getNotificationStorageInstance();
    }

    public static LocationStorage getLocationStorage() {
        return getInstance().getLocationStorageInstance();
    }

    public static GoogleApiHelper getGoogleApiHelper() {
        return getInstance().getGoogleApiHelperInstance();
    }

    public static Context getContext() {
        return mContext;
    }

    public static SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

}
