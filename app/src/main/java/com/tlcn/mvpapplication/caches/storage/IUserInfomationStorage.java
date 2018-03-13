package com.tlcn.mvpapplication.caches.storage;

import com.tlcn.mvpapplication.model.User;

/**
 * Created by apple on 3/6/18.
 */

public interface IUserInfomationStorage {

    void saveInfo(User user);

    void deleteInfo();

    User getInfo();
}
