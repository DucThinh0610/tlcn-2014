package com.tlcn.mvpapplication.mvp.savedlistnews.presenter;

import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.mvp.savedlistnews.view.ISavedListNewsView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 10/4/17.
 */

public class SavedListNewsPresenter extends BasePresenter implements ISavedListNewsPresenter {

    private List<Locations> list;

    public void attachView(ISavedListNewsView view) {
        super.attachView(view);
    }

    public ISavedListNewsView getView() {
        return (ISavedListNewsView) getIView();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        list = new ArrayList<>();
    }


    @Override
    public void getSavedListLocation() {
        list.clear();
        getView().showLoading();

        getView().onGetSavedListLocationSuccess(list);
        getView().hideLoading();
    }
}
