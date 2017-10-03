package com.tlcn.mvpapplication.mvp.setting.presenter;

import com.tlcn.mvpapplication.app.App;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.caches.storage.NotificationStorage;
import com.tlcn.mvpapplication.mvp.chooselocation.view.IChooseLocationView;
import com.tlcn.mvpapplication.mvp.setting.view.ISettingView;

/**
 * Created by apple on 01/10/2017.
 */

public class SettingPresenter extends BasePresenter implements ISettingPresenter {

    private NotificationStorage mNotificationStorage;
    public void attachView(IChooseLocationView view) {
        super.attachView(view);
    }

    public ISettingView getView() {
        return (ISettingView) getIView();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationStorage = App.getNotificationStorage();
    }

    @Override
    public void setNotificationState(boolean state) {
        mNotificationStorage.setNotificationState(state);
        getView().onStateChangeSuccess(state);
    }
}
