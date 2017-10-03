package com.tlcn.mvpapplication.mvp.details.view;

import com.tlcn.mvpapplication.base.IView;

/**
 * Created by tskil on 9/20/2017.
 */

public interface IDetailsView extends IView {

    void onFail(String message);

    void getPostSuccess();
}
