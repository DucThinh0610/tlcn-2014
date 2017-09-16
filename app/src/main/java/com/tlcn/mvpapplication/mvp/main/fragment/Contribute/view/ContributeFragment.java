package com.tlcn.mvpapplication.mvp.main.fragment.Contribute.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.mvp.main.fragment.Contribute.presenter.ContributePresenter;

import butterknife.ButterKnife;

/**
 * Created by tskil on 8/23/2017.
 */

public class ContributeFragment extends Fragment implements IContributeView{
    public static ContributeFragment newInstance() {
        return new ContributeFragment();
    }

    //Todo: Binding

    //Todo: Declaring
    ContributePresenter mPresenter = new ContributePresenter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, v);
        initData(v);
        initListener(v);
        mPresenter.attachView(this);
        mPresenter.onCreate();

        return v;
    }
    private void initListener(View v) {
        //các sự kiện click view được khai báo ở đây

    }

    private void initData(View v) {
        // hiển thị các view được làm ở đây. như các nút hoặc các dữ liệu cứng, intent, adapter

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
