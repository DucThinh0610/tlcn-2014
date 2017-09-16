package com.tlcn.mvpapplication.mvp.main.fragment.Contribute.presenter;

import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.model.Contribute;
import com.tlcn.mvpapplication.mvp.main.fragment.Contribute.view.IContributeView;

/**
 * Created by tskil on 9/16/2017.
 */

public class ContributePresenter extends BasePresenter implements IContributePresenter {

    public void attachView(IContributeView view) {
        super.attachView(view);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void sendContribution(Contribute contribute) {

    }
}
