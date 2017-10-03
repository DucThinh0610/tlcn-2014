package com.tlcn.mvpapplication.mvp.main.fragment.Favourite.view;

import com.tlcn.mvpapplication.base.IView;

/**
 * Created by tskil on 9/12/2017.
 */

public interface IFavouriteView extends IView{

    void getListNewsSuccess();

    void changeLocationSuccess();

    void changeDistanceFavouriteSuccess();

    void onFail(String message);

    void showLoading();

    void hideLoading();
}
