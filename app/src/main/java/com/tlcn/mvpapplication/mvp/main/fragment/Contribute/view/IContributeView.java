package com.tlcn.mvpapplication.mvp.main.fragment.Contribute.view;

import com.tlcn.mvpapplication.base.IView;

/**
 * Created by tskil on 9/16/2017.
 */

public interface IContributeView extends IView {
    void onSuccess();

    void onFail(String message);

    void removeImageView();
}
