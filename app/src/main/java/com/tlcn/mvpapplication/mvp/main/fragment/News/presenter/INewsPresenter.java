package com.tlcn.mvpapplication.mvp.main.fragment.News.presenter;

/**
 * Created by tskil on 9/14/2017.
 */

public interface INewsPresenter {
    void getListNews();

    void onChangeStopped(String id);
}
