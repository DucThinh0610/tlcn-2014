package com.tlcn.mvpapplication.mvp.main.fragment.News.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.dialog.DialogProgress;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.model.ObjectSerializable;
import com.tlcn.mvpapplication.mvp.details.view.DetailsView;
import com.tlcn.mvpapplication.mvp.main.adapter.LocationAdapter;
import com.tlcn.mvpapplication.mvp.main.fragment.News.presenter.NewsPresenter;
import com.tlcn.mvpapplication.utils.DialogUtils;
import com.tlcn.mvpapplication.utils.KeyUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tskil on 8/23/2017.
 */

public class NewsFragment extends Fragment implements INewsView, SwipeRefreshLayout.OnRefreshListener, LocationAdapter.OnItemClick {
    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    //Todo: Binding
    @Bind(R.id.rcv_news)
    RecyclerView rcvNews;
    @Bind(R.id.swp_layout)
    SwipeRefreshLayout swpLayout;
    //Todo: Declaring
    private DialogProgress mProgressDialog;
    NewsPresenter mPresenter = new NewsPresenter();
    LocationAdapter newsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, v);
        initData(v);
        initListener(v);
        mPresenter.attachView(this);
        mPresenter.onCreate();
        mPresenter.getListNews();
        return v;
    }

    private void initListener(View v) {
        //các sự kiện click view được khai báo ở đây
        swpLayout.setOnRefreshListener(this);
    }

    private void initData(View v) {
        // hiển thị các view được làm ở đây. như các nút hoặc các dữ liệu cứng, intent, adapter

    }

    @Override
    public void getListNewsSuccess() {
        if (mPresenter.getListNewsResult() != null) {
            newsAdapter = new LocationAdapter(mPresenter.getListNewsResult(), getContext(), this);
            rcvNews.setLayoutManager(new LinearLayoutManager(getContext()));
            rcvNews.setAdapter(newsAdapter);
        }
        swpLayout.setRefreshing(false);
    }

    @Override
    public void onFail(String message) {

    }

    @Override
    public void showLoading() {
        swpLayout.setRefreshing(false);
        showDialogLoading();
    }

    @Override
    public void hideLoading() {
        swpLayout.setRefreshing(false);
        dismissDialogLoading();
    }

    protected void showDialogLoading() {
        dismissDialogLoading();
        mProgressDialog = DialogUtils.showProgressDialog(getContext());
    }

    protected void dismissDialogLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.getListNews();
    }

    @Override
    public void OnClickDetail(Locations item) {
        Intent intent = new Intent(getActivity(), DetailsView.class);
        intent.putExtra(KeyUtils.KEY_INTENT_LOCATION, new ObjectSerializable(item));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void OnClickShare(Locations item) {

    }
}
