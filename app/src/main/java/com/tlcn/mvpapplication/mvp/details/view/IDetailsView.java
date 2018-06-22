package com.tlcn.mvpapplication.mvp.details.view;

import com.tlcn.mvpapplication.base.IView;
import com.tlcn.mvpapplication.model.MetaData;
import com.tlcn.mvpapplication.model.Post;

import java.util.List;

/**
 * Created by tskil on 9/20/2017.
 */

public interface IDetailsView extends IView {

    void getSaveStateSuccess(boolean isSave);

    void saveLocationSuccess();

    void onFail(String message);

    void getPostSuccess();

    void getListNewsSuccess(List<Post> result, MetaData metaData);

    void onActionSuccess(Post result);

    void notifyNew(Post socketPost);
}
