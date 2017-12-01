package com.tlcn.mvpapplication.mvp.main.fragment.Favourite.view;

import com.tlcn.mvpapplication.base.IView;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.model.ShareLink;

import java.util.List;

/**
 * Created by tskil on 9/12/2017.
 */

public interface IFavouriteView extends IView{

    void notifyChangeStopped();

    void getListLocationSuccess(List<Locations>result);

    void getShareLinkSuccess(ShareLink result);

    void changeLocationSuccess();

    void changeDistanceFavouriteSuccess();

    void onFail(String message);

    void showLoading();

    void hideLoading();
}
