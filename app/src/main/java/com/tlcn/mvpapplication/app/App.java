package com.tlcn.mvpapplication.app;

import android.app.Application;

public class App extends Application {
    private GoogleApiHelper googleApiHelper;
    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        AppManager.load(this);
        mInstance = this;
        googleApiHelper = new GoogleApiHelper(mInstance);
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    public GoogleApiHelper getGoogleApiHelperInstance() {
        return this.googleApiHelper;
    }

    public static GoogleApiHelper getGoogleApiHelper() {
        return getInstance().getGoogleApiHelperInstance();
    }

}
