package com.tlcn.mvpapplication.mvp.Main.presenter;

import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.model.News;
import com.tlcn.mvpapplication.mvp.Main.view.IFavouriteView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tskil on 9/12/2017.
 */

public class FavouritePresenter extends BasePresenter implements IFavouritePresenter {
    private List<News> list;
    private News news;

    public void attachView(IFavouriteView view) {
        super.attachView(view);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        list = new ArrayList<>();
        news = new News();
    }

    @Override
    public void getNewsInfo(String idNews) {

    }

    @Override
    public void getListNews() {

    }
}
