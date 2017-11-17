package com.tlcn.mvpapplication.mvp.direction_screen.presenter;

import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.model.direction.Route;
import com.tlcn.mvpapplication.mvp.direction_screen.view.IDirectionView;

import java.util.concurrent.atomic.DoubleAccumulator;

public class DirectionPresenter extends BasePresenter implements IDirectionPresenter {
    private Route mRoute;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void attachView(IDirectionView view) {
        super.attachView(view);
    }

    public IDirectionView getView() {
        return (IDirectionView) getIView();
    }

    public void setRouteFromObj(Object routeFromObj) {
        if (routeFromObj != null)
            this.mRoute = (Route) routeFromObj;
        else
            getView().onFail("Lỗi không xác định! Thử lại sau.");
    }

    public Route getRoutes() {
        return this.mRoute;
    }
}
