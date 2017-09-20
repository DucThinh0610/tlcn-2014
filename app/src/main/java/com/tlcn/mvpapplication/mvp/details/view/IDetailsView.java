package com.tlcn.mvpapplication.mvp.details.view;

import com.tlcn.mvpapplication.base.IView;
import com.tlcn.mvpapplication.model.News;

/**
 * Created by tskil on 9/20/2017.
 */

public interface IDetailsView extends IView {
    void getNewsSuccess(News result);

    void onFail(String message);
}
