package com.tlcn.mvpapplication.mvp.main.fragment.News.presenter;

import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.model.News;
import com.tlcn.mvpapplication.mvp.main.fragment.News.view.INewsView;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by tskil on 9/14/2017.
 */

public class NewsPresenter  extends BasePresenter implements INewsPresenter {

    List<News> list;

    public List<News> getListNewsResult(){
        return list;
    }

    public void attachView(INewsView view) {
        super.attachView(view);
    }

    public INewsView getView(){
        return (INewsView) getIView();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        list = new ArrayList<>();
    }

    @Override
    public void getListNews() {
        getView().showLoading();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017,8,12);
        Date date = calendar.getTime();
        list.add(new News("1","Kẹt xe tại ngã tư hàng xanh",4,date,"Kẹt nhiều xe tải tại giao lộ của ngã tư",10,20));
        list.add(new News("2","Kẹt xe tại Phạm Văn Đồng",3,date,"Đèn đỏ trục trặc khiến giao thông ùn tắc",5,1));
        list.add(new News("3","Kẹt xe tại Lê Văn Việt",1,date,"Kẹt xe tại điểm giao giữa Đình phong phú và Lê văn việt",10,2));
        list.add(new News("4","Kẹt xe tại ngã tư Bình Thái",5,date,"Kẹt xe rất đông",3,1));
        getView().getListNewsSuccess();
        getView().hideLoading();
    }
}
