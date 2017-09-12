package com.tlcn.mvpapplication.mvp.Main.presenter;

import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.model.News;
import com.tlcn.mvpapplication.mvp.Main.view.IFavouriteView;

import java.sql.Date;
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

    public IFavouriteView getView() {
        return (IFavouriteView) getIView();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        list = new ArrayList<>();
        news = new News();
    }

    public List<News> getListNewsResult(){
        return list;
    }

    public News getNewsInfoResult(String idNews){

        return news;
    }

    @Override
    public void getNewsInfo(String idNews) {
        getView().showLoading();
        news = new News("1","Kẹt xe tại ngã tư hàng xanh",4,new Date(2017,9,13),"Kẹt xe rất cao");
        getView().hideLoading();
    }

    @Override
    public void getListNews() {

    }
}
