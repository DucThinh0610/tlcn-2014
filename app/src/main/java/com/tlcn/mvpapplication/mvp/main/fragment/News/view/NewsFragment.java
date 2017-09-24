package com.tlcn.mvpapplication.mvp.main.fragment.News.view;

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
import com.tlcn.mvpapplication.mvp.main.adapter.NewsAdapter;
import com.tlcn.mvpapplication.mvp.main.fragment.News.presenter.NewsPresenter;
import com.tlcn.mvpapplication.utils.DialogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tskil on 8/23/2017.
 */

public class NewsFragment extends Fragment implements INewsView, SwipeRefreshLayout.OnRefreshListener {
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
    NewsAdapter newsAdapter;

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
            newsAdapter = new NewsAdapter(getContext(), mPresenter.getListNewsResult(),this);
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
        showDialogLoading();
    }

    @Override
    public void hideLoading() {
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
}
