package com.tlcn.mvpapplication.mvp.savedlistnews.view;

import com.tlcn.mvpapplication.base.IView;
import com.tlcn.mvpapplication.model.News;

import java.util.List;

/**
 * Created by apple on 10/4/17.
 */

public interface ISavedListNewsView extends IView {

    void onGetSavedListNewsSuccess(List<News> result);
}
