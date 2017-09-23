package com.tlcn.mvpapplication.base;

import com.tlcn.mvpapplication.api.ApiManager;
import com.tlcn.mvpapplication.app.App;
import com.tlcn.mvpapplication.caches.storage.LocalStorage;

public abstract class BasePresenter {
    private ApiManager manager = new ApiManager();
    private IView mView;
    private LocalStorage mStorage;

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
    }


    public void onDestroy() {

    }

    public ApiManager getManager() {
        return this.manager;
    }

    public LocalStorage getStorage() {
        return mStorage;
    }
}
