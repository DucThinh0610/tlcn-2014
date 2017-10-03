package com.tlcn.mvpapplication.mvp.main.fragment.News.view;

import com.tlcn.mvpapplication.base.IView;

/**
 * Created by tskil on 9/14/2017.
 */

public interface INewsView extends IView {
    void getListNewsSuccess();

    void onFail(String message);

    void notifyChangeStopped();
}
