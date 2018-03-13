package com.tlcn.mvpapplication.caches.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.tlcn.mvpapplication.model.User;

/**
 * Created by apple on 3/6/18.
 */

public class UserInfomationStorage implements IUserInfomationStorage {
    private final String TAG = UserInfomationStorage.class.getSimpleName();
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context mContext;

    private Gson gson = new Gson();

    private static final String PREF_NAME = "UserInfo";

    private final String USER = "USER";

    public UserInfomationStorage(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    @Override
    public void saveInfo(User user) {
        editor.putString(USER, gson.toJson(user));
        editor.commit();
    }

    @Override
    public void deleteInfo() {
        editor.clear();
        editor.commit();
    }

    @Override
    public User getInfo() {
        if (pref.getString(USER, null) == null || pref.getString(USER, null).isEmpty()) {
            return null;
        }
        return gson.fromJson(pref.getString(USER, ""), User.class);
    }
}
