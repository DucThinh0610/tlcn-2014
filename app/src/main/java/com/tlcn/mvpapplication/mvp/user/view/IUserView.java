package com.tlcn.mvpapplication.mvp.user.view;

import com.tlcn.mvpapplication.base.IView;

public interface IUserView extends IView {
    void getUserSuccess();

    void onFail(String message);

    void showLoading();

    void hideLoading();
}
