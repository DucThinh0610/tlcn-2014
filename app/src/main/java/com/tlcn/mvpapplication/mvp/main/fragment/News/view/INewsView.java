package com.tlcn.mvpapplication.mvp.main.fragment.News.view;

import com.tlcn.mvpapplication.base.IView;
import com.tlcn.mvpapplication.model.Locations;

import java.util.List;

/**
 * Created by tskil on 9/14/2017.
 */

public interface INewsView extends IView {
    void getListNewsSuccess(List<Locations> result);

    void onFail(String message);

    void notifyChangeStopped();
}
