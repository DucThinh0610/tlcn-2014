package com.tlcn.mvpapplication.base;

public abstract class BasePresenter {
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
}
