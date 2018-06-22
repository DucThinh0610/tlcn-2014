package com.tlcn.mvpapplication.base;

import com.tlcn.mvpapplication.api.ApiManager;
import com.tlcn.mvpapplication.app.App;
import com.tlcn.mvpapplication.caches.storage.LocalStorage;
import com.tlcn.mvpapplication.interactor.event_bus.EventManager;
import com.tlcn.mvpapplication.interactor.event_bus.type.Empty;
import com.tlcn.mvpapplication.interactor.event_bus.type.ObjectEvent;
import com.tlcn.mvpapplication.interactor.socketIO.SocketManager;

import org.greenrobot.eventbus.Subscribe;

public abstract class BasePresenter {
    private ApiManager manager = new ApiManager();
    private IView mView;
    private LocalStorage mStorage;
    private SocketManager socketManager;
    private EventManager eventManager;

    public void attachView(IView view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
    }

    public boolean isViewAttached() {
        return mView != null;
    }

    public IView getIView() {
        if (mView == null) {
            throw new IllegalStateException("Presenter must be attach IView");
        }
        return mView;
    }


    public void onCreate() {
        mStorage = new LocalStorage(App.getSharedPreferences());
        socketManager = App.getSocketManager();
        eventManager = App.getEventManager();
    }


    public void onDestroy() {
        mView = null;
    }

    public ApiManager getManager() {
        return this.manager;
    }

    public LocalStorage getStorage() {
        return mStorage;
    }

    public SocketManager getSocketManager() {
        return socketManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    @Subscribe
    public void onEvent(Empty empty) {

    }
}
