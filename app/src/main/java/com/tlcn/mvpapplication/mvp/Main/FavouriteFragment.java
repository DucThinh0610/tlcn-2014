package com.tlcn.mvpapplication.mvp.Main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.mvp.Main.presenter.FavouritePresenter;
import com.tlcn.mvpapplication.mvp.Main.view.IFavouriteView;

/**
 * Created by tskil on 8/24/2017.
 */

public class FavouriteFragment extends Fragment implements IFavouriteView{
    public static FavouriteFragment newInstance() {
        return new FavouriteFragment();
    }
    FavouritePresenter mPresenter = new FavouritePresenter();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favourite,container,false);
        mPresenter.attachView(this);
        initData(v);
        initListener(v);
        return v;
    }
    private void initListener(View v) {
        //các sự kiện click view được khai báo ở đây
    }

    private void initData(View v) {
        // hiển thị các view được làm ở đây. như các nút hoặc các dữ liệu cứng, intent, adapter
    }

    @Override
    public void getListNewsSuccess() {

    }

    @Override
    public void getNewsSuccess() {

    }

    @Override
    public void onFail(String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
