package com.tlcn.mvpapplication.app;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.tlcn.mvpapplication.caches.storage.LocationStorage;

public class App extends Application {
    private GoogleApiHelper googleApiHelper;
    private static App mInstance;
    private LocationStorage mPreferenceUtils;
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

    public LocationStorage getPreferenceUtilsInstance(){
        if(mPreferenceUtils == null)
            return new LocationStorage(this);
        return mPreferenceUtils;
    }

    public static LocationStorage getPreferenceUtils(){
        return getInstance().getPreferenceUtilsInstance();
    }
    public static GoogleApiHelper getGoogleApiHelper() {
        return getInstance().getGoogleApiHelperInstance();
    }
}
