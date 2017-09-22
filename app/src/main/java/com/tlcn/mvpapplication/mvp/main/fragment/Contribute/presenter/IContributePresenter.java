package com.tlcn.mvpapplication.mvp.main.fragment.Contribute.presenter;

import com.tlcn.mvpapplication.api.request.contribution.ContributionRequest;

/**
 * Created by tskil on 9/16/2017.
 */

public interface IContributePresenter {
    void sendContribution(ContributionRequest contribution);

    void uploadImage();
}
