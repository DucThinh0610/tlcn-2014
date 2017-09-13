package com.tlcn.mvpapplication.mvp.Main.fragment.Favourite.presenter;

import com.google.android.gms.maps.model.LatLng;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.model.News;
import com.tlcn.mvpapplication.mvp.Main.fragment.Favourite.view.IFavouriteView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017,9,12,9,30,30);
        Date date = calendar.getTime();
        news = new News("1","Kẹt xe tại ngã tư hàng xanh",4,date,"Kẹt xe rất cao");
        getView().getNewsSuccess();
        getView().hideLoading();
    }

    @Override
    public void getListNews() {
        getView().showLoading();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017,8,12);
        Date date = calendar.getTime();
        list.add(new News("1","Kẹt xe tại ngã tư hàng xanh",4,date,"Kẹt nhiều xe tải tại giao lộ của ngã tư"));
        list.add(new News("2","Kẹt xe tại Phạm Văn Đồng",3,date,"Đèn đỏ trục trặc khiến giao thông ùn tắc"));
        list.add(new News("3","Kẹt xe tại Lê Văn Việt",1,date,"Kẹt xe tại điểm giao giữa Đình phong phú và Lê văn việt"));
        list.add(new News("4","Kẹt xe tại ngã tư Bình Thái",5,date,"Kẹt xe rất đông"));
        getView().getListNewsSuccess();
        getView().hideLoading();
    }

    @Override
    public void setHouseLocation(LatLng location) {

    }

    @Override
    public void setWorkLocation(LatLng location) {

    }

    @Override
    public void setOtherLocation(LatLng location) {

    }

}
