package com.tlcn.mvpapplication.mvp.savedlistnews.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.mvp.savedlistnews.presenter.SavedListNewsPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by apple on 10/4/17.
 */

public class SavedListNewsView extends AppCompatActivity implements ISavedListNewsView, View.OnClickListener {
    @Bind(R.id.imv_back)
    ImageView imvBack;
    @Bind(R.id.rcv_saved_list_news)
    RecyclerView rcvSavedListNews;

    //TODO: Declaring
    SavedListNewsPresenter mPresenter = new SavedListNewsPresenter();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_list_news);
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        mPresenter.onCreate();
        mPresenter.getSavedListLocation();
        initData();
        initListener();
    }

    private void initData(){

    }

    private void initListener(){
        imvBack.setOnClickListener(this);
    }
    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onGetSavedListLocationSuccess(List<Locations> result) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imv_back:
                finish();
                break;
        }
    }
}
