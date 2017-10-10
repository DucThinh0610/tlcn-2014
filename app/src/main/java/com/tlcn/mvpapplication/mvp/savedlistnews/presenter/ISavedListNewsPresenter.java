package com.tlcn.mvpapplication.mvp.savedlistnews.presenter;

import com.tlcn.mvpapplication.model.Locations;

/**
 * Created by apple on 10/4/17.
 */

public interface ISavedListNewsPresenter {
    void getSavedListLocation();

    void unSavedLocation(Locations locationID);

    void contributing(Locations locationID);
}
