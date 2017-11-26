package com.tlcn.mvpapplication.mvp.direction_screen.view;

import com.tlcn.mvpapplication.base.IView;
import com.tlcn.mvpapplication.model.Locations;

public interface IDirectionView extends IView {
    void onFail(String s);

    void notifyNewLocation();

    void drawANewLocation(Locations locations);
}
