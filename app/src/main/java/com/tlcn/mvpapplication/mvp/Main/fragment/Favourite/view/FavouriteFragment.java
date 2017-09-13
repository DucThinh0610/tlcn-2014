package com.tlcn.mvpapplication.mvp.Main.fragment.Favourite.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.mvp.ChooseLocation.view.ChooseLocationView;
import com.tlcn.mvpapplication.mvp.Main.adapter.NewsAdapter;
import com.tlcn.mvpapplication.mvp.Main.fragment.Favourite.presenter.FavouritePresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tskil on 8/24/2017.
 */

public class FavouriteFragment extends Fragment implements IFavouriteView, View.OnClickListener {
    public static FavouriteFragment newInstance() {
        return new FavouriteFragment();
    }

    @Bind(R.id.rcv_favourite)
    RecyclerView rcvFavourite;
    @Bind(R.id.lnl_home)
    LinearLayout lnlHome;
    @Bind(R.id.lnl_work)
    LinearLayout lnlWork;
    @Bind(R.id.tv_add)
    TextView tvAdd;

    FavouritePresenter mPresenter = new FavouritePresenter();
    NewsAdapter newsAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favourite, container, false);
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
        lnlHome.setOnClickListener(this);
        lnlWork.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
    }

    private void initData(View v) {
        // hiển thị các view được làm ở đây. như các nút hoặc các dữ liệu cứng, intent, adapter
    }

    @Override
    public void getListNewsSuccess() {
        if (mPresenter.getListNewsResult() != null) {
            newsAdapter = new NewsAdapter(getContext(), mPresenter.getListNewsResult());
            rcvFavourite.setLayoutManager(new LinearLayoutManager(getContext()));
            rcvFavourite.setAdapter(newsAdapter);
        }
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lnl_home:
                Intent intent = new Intent(getContext(), ChooseLocationView.class);
                intent.putExtra("title",getString(R.string.house));
                startActivityForResult(intent,101);
                break;
            case R.id.lnl_work:
                Intent intent2 = new Intent(getContext(), ChooseLocationView.class);
                intent2.putExtra("title",getString(R.string.work));
                startActivityForResult(intent2,102);
                break;
            case R.id.tv_add:
                break;
        }
    }
}
