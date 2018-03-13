package com.tlcn.mvpapplication.mvp.main.fragment.News.presenter;

import com.tlcn.mvpapplication.api.request.BaseListRequest;

/**
 * Created by tskil on 9/14/2017.
 */

public interface INewsPresenter {
    void getListNews(BaseListRequest baseListRequest);

    void getShareLink(String location_id);

    void onChangeStopped(String id);
}
