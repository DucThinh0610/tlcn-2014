package com.tlcn.mvpapplication.base;

import com.tlcn.mvpapplication.api.ApiManager;

public abstract class BasePresenter {
    private ApiManager manager = new ApiManager();
    private IView mView;

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

    }


    public void onDestroy() {

    }

    public ApiManager getManager() {
        return this.manager;
    }
}
