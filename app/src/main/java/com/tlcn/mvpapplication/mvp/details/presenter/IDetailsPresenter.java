package com.tlcn.mvpapplication.mvp.details.presenter;

import com.tlcn.mvpapplication.api.request.BaseListRequest;

/**
 * Created by tskil on 9/20/2017.
 */

public interface IDetailsPresenter {

    void getSaveState();

    void saveLocations();

    void getListPostFromSV(BaseListRequest request);

    void actionDislike(String idPost);

    void actionLike(String idPost);

    void getInfoLocation();
}
