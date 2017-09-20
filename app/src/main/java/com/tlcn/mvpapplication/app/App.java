package com.tlcn.mvpapplication.app;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;
import com.tlcn.mvpapplication.caches.storage.LocationStorage;

public class App extends Application {
    private GoogleApiHelper googleApiHelper;
    private static App mInstance;
    private LocationStorage mLocationStorage;
    private Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        AppManager.load(this);
        mInstance = this;
        googleApiHelper = new GoogleApiHelper(mInstance);
        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    public GoogleApiHelper getGoogleApiHelperInstance() {
        return this.googleApiHelper;
    }

    public LocationStorage getLocationStorageInstance(){
        if(mLocationStorage == null) {
            mLocationStorage = new LocationStorage(this);
            return mLocationStorage;
        }
        return mLocationStorage;
    }
    public Context getContextInstance(){
        if(mContext == null) {
            mContext = this;
            return mContext;
        }
        return mContext;
    }
    public static LocationStorage getLocationStorage(){
        return getInstance().getLocationStorageInstance();
    }
    public static GoogleApiHelper getGoogleApiHelper() {
        return getInstance().getGoogleApiHelperInstance();
    }

    public static Context getContext(){
        return getInstance().getContextInstance();
    }
}
