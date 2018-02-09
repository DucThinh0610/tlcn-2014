package com.tlcn.mvpapplication.mvp.main.presenter;

import android.util.Log;

import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.interactor.event_bus.type.MessageEvent;
import com.tlcn.mvpapplication.mvp.main.MainContract;
import com.tlcn.mvpapplication.mvp.main.view.MainActivity;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by ducthinh on 07/02/2018.
 */

public class MainPresenter extends BasePresenter implements MainContract.IMainPresenter {

    public void attachView(MainActivity view) {
        super.attachView(view);
    }

    public MainActivity getView() {
        return (MainActivity) super.getIView();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getEventManager().register(this);
        getSocketManager().connectSocket();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getEventManager().unRegister(this);
    }

    @Subscribe
    public void onEvent(MessageEvent messageEvent) {
        Log.d("Message", messageEvent.getMessage());
    }
}
