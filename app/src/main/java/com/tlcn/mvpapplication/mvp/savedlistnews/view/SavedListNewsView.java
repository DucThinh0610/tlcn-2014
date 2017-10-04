package com.tlcn.mvpapplication.mvp.savedlistnews.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.model.News;

import java.util.List;

import butterknife.Bind;

/**
 * Created by apple on 10/4/17.
 */

public class SavedListNewsView extends AppCompatActivity implements ISavedListNewsView {
    @Bind(R.id.imv_back)
    ImageView imvBack;
    @Bind(R.id.rcv_saved_list_news)
    RecyclerView rcvSavedListNews;

    //TODO: Declaring

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_list_news);
    }

    private void initData(){

    }

    private void initListener(){

    }
    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onGetSavedListNewsSuccess(List<News> result) {

    }
}
