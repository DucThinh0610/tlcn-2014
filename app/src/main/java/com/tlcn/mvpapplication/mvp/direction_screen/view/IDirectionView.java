package com.tlcn.mvpapplication.mvp.direction_screen.view;

import com.tlcn.mvpapplication.base.IView;

public interface IDirectionView extends IView {
    void onFail(String s);

    void notifyNewLocation();
}
