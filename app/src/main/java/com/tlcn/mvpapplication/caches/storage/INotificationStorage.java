package com.tlcn.mvpapplication.caches.storage;

/**
 * Created by apple on 10/4/17.
 */

public interface INotificationStorage {

    void setNotificationState(boolean state);

    boolean isNotificationOn();
}
