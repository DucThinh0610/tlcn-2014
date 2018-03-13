package com.tlcn.mvpapplication.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.FacebookSdk;
import com.tlcn.mvpapplication.caches.storage.LocationStorage;
import com.tlcn.mvpapplication.caches.storage.NotificationStorage;
import com.tlcn.mvpapplication.caches.storage.UserInfomationStorage;
import com.tlcn.mvpapplication.interactor.event_bus.EventManager;
import com.tlcn.mvpapplication.interactor.socketIO.SocketManager;
import com.zxy.tiny.Tiny;

public class App extends Application {
    private GoogleApiHelper googleApiHelper;
    private static App mInstance;
    private LocationStorage mLocationStorage;
    private NotificationStorage mNotificationStorage;
    private UserInfomationStorage mUserInfomationStorage;
    private static Context mContext;
    private static SharedPreferences mSharedPreferences;
    private static EventManager eventManager;
    private static SocketManager socketManager;

    @Override

    public void onCreate() {
        super.onCreate();
        AppManager.load(this);
        mInstance = this;
        googleApiHelper = new GoogleApiHelper(mInstance);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mContext = getApplicationContext();
        eventManager = new EventManager();
        socketManager = new SocketManager(eventManager);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        Tiny.getInstance().init(this);
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    public GoogleApiHelper getGoogleApiHelperInstance() {
        return this.googleApiHelper;
    }

    private UserInfomationStorage getUserInfoInstance(){
        if (mUserInfomationStorage == null){
            mUserInfomationStorage = new UserInfomationStorage(this);
        }
        return mUserInfomationStorage;
    }

    public static UserInfomationStorage getUserInfo(){
        return getInstance().getUserInfoInstance();
    }

    private LocationStorage getLocationStorageInstance() {
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

    public static EventManager getEventManager() {
        return eventManager;
    }

    public static SocketManager getSocketManager() {
        return socketManager;
    }
}
