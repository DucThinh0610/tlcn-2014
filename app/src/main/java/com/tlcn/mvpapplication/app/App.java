package com.tlcn.mvpapplication.app;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppManager.load(this);
    }

}
