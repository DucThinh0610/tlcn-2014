package com.tlcn.mvpapplication.mvp.details.presenter;

/**
 * Created by tskil on 9/20/2017.
 */

public interface IDetailsPresenter {

    void getSaveState();

    void saveLocations();

    void getListPostFromSV();

    void actionDislike(String idPost);

    void actionLike(String idPost);
}
