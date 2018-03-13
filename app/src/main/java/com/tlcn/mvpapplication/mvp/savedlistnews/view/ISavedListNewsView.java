package com.tlcn.mvpapplication.mvp.savedlistnews.view;

import com.tlcn.mvpapplication.base.IView;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.model.MetaData;

import java.util.List;

/**
 * Created by apple on 10/4/17.
 */

public interface ISavedListNewsView extends IView {

    void onGetSavedListLocationSuccess(List<Locations> result, MetaData metaData);

    void notifyDataSetChanged();

    void onContributingSuccess();

    void onFailed(String message);
}
