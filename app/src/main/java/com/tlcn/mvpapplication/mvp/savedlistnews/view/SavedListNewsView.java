package com.tlcn.mvpapplication.mvp.savedlistnews.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.api.request.BaseListRequest;
import com.tlcn.mvpapplication.dialog.DialogProgress;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.model.MetaData;
import com.tlcn.mvpapplication.mvp.savedlistnews.adapter.SavedAdapter;
import com.tlcn.mvpapplication.mvp.savedlistnews.presenter.SavedListNewsPresenter;
import com.tlcn.mvpapplication.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by apple on 10/4/17.
 */

public class SavedListNewsView extends AppCompatActivity implements ISavedListNewsView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, SavedAdapter.OnItemClick {
    @Bind(R.id.imv_back)
    ImageView imvBack;
    @Bind(R.id.rcv_saved_list_news)
    RecyclerView rcvSavedListNews;
    @Bind(R.id.swp_layout)
    SwipeRefreshLayout swpLayout;
    //TODO: Declaring
    private DialogProgress mProgressDialog;
    SavedListNewsPresenter mPresenter = new SavedListNewsPresenter();
    SavedAdapter adapter;

    BaseListRequest request;
    List<Locations> mList;
    MetaData mMetaData;
    int visibleItemCount;
    int totalItemCount;
    int pastVisiblesItems;
    boolean isLoading = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_list_news);
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        mPresenter.onCreate();
        initData();
        initListener();
    }

    private void initData() {
        swpLayout.setColorSchemeColors(getResources().getColor(R.color.color_main));
        request = new BaseListRequest();
        mList = new ArrayList<>();
        isLoading = true;
        mPresenter.getSavedListLocation(request);
    }

    private void initListener() {
        imvBack.setOnClickListener(this);
        swpLayout.setOnRefreshListener(this);
        rcvSavedListNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                try {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) rcvSavedListNews.getLayoutManager();
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        if (!isLoading && mMetaData.isHas_more_page()) {
                            isLoading = true;
                            //bottom of recyclerview
                            request.setPage(mMetaData.getCurrent_page() + 1);
                            mPresenter.getSavedListLocation(request);
                        }
                    }
                } catch (Exception ignored) {

                }
            }
        });

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
        mProgressDialog = DialogUtils.showProgressDialog(this);
    }

    protected void dismissDialogLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public void onGetSavedListLocationSuccess(List<Locations> result, MetaData metaData) {
        isLoading = false;
        swpLayout.setRefreshing(false);
        if (mList.size() == 0) {
            mList.addAll(result);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            adapter = new SavedAdapter(mList, this, this);
            rcvSavedListNews.setLayoutManager(layoutManager);
            rcvSavedListNews.setAdapter(adapter);
        } else {
            if (!mList.containsAll(result)) {
                mList.addAll(result);
                adapter.notifyDataSetChanged();
            }
        }
        if (metaData != null) {
            mMetaData = metaData;
        }
    }

    @Override
    public void notifyDataSetChanged() {
        mPresenter.setChange(true);
        mList = new ArrayList<>();
        mPresenter.getSavedListLocation(new BaseListRequest());
    }

    @Override
    public void onContributingSuccess() {
        Toast.makeText(this, getString(R.string.thanks_for_your_contribution), Toast.LENGTH_SHORT).show();
        mList = new ArrayList<>();
        mPresenter.getSavedListLocation(new BaseListRequest());
    }


    @Override
    public void onFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_back:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        isLoading = true;
        mPresenter.getSavedListLocation(new BaseListRequest());
    }

    //TODO: on Item View Click
    @Override
    public void onContributingListener(View view, int position, Object item) {
        mPresenter.contributing((Locations) item);
    }

    @Override
    public void onCloseClickListener(View view, int position, Object item) {
        mPresenter.unSavedLocation((Locations) item);
    }
    //TODO: END on Item View Click
}
