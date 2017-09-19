package com.tlcn.mvpapplication.mvp.main.fragment.Contribute.view;

import com.tlcn.mvpapplication.base.IView;
import com.tlcn.mvpapplication.model.Result;

/**
 * Created by tskil on 9/16/2017.
 */

public interface IContributeView extends IView {
    void sendContributionSuccess(Result response);

    void onFail(String message);
}
