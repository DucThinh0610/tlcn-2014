package com.tlcn.mvpapplication.mvp.main.fragment.News.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.api.request.BaseListRequest;
import com.tlcn.mvpapplication.dialog.DialogProgress;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.model.MetaData;
import com.tlcn.mvpapplication.model.ShareLink;
import com.tlcn.mvpapplication.mvp.details.view.DetailsView;
import com.tlcn.mvpapplication.mvp.main.adapter.LocationAdapter;
import com.tlcn.mvpapplication.mvp.main.fragment.News.presenter.NewsPresenter;
import com.tlcn.mvpapplication.mvp.main.view.MainActivity;
import com.tlcn.mvpapplication.mvp.savedlistnews.view.SavedListNewsView;
import com.tlcn.mvpapplication.utils.DialogUtils;
import com.tlcn.mvpapplication.utils.KeyUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tskil on 8/23/2017.
 */

public class NewsFragment extends Fragment implements INewsView, SwipeRefreshLayout.OnRefreshListener, LocationAdapter.OnItemClick, View.OnClickListener, MainActivity.OnBackPressedListener {
    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    //Todo: Binding
    @Bind(R.id.rcv_news)
    RecyclerView rcvNews;
    @Bind(R.id.swp_layout)
    SwipeRefreshLayout swpLayout;
    @Bind(R.id.tv_saved_list_news)
    TextView tvSavedListNews;
    //Todo: Declaring
    private DialogProgress mProgressDialog;
    NewsPresenter mPresenter = new NewsPresenter();
    LocationAdapter newsAdapter;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    BaseListRequest request;

    List<Locations> mList;
    MetaData mMetaData;
    int visibleItemCount;
    int totalItemCount;
    int pastVisiblesItems;
    boolean isLoading = false;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        callbackManager = CallbackManager.Factory.create();

        View v = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, v);
        mPresenter.attachView(this);
        mPresenter.onCreate();
        initData(v);
        initListener(v);
        return v;
    }

    private void initListener(View v) {
        //các sự kiện click view được khai báo ở đây
        swpLayout.setOnRefreshListener(this);
        tvSavedListNews.setOnClickListener(this);

        rcvNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                try {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) rcvNews.getLayoutManager();
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        if (!isLoading && mMetaData.isHas_more_page()) {
                            isLoading = true;
                            //bottom of recyclerview
                            request.setPage(mMetaData.getCurrent_page() + 1);
                            mPresenter.getListNews(request);
                        }
                    }
                } catch (Exception ignored) {

                }
            }
        });
    }

    private void initData(View v) {
        // hiển thị các view được làm ở đây. như các nút hoặc các dữ liệu cứng, intent, adapter
        swpLayout.setColorSchemeColors(getResources().getColor(R.color.color_main));
        request = new BaseListRequest();
        mList = new ArrayList<>();
        isLoading = true;
        mPresenter.getListNews(request);
    }

    @Override
    public void getListNewsSuccess(List<Locations> result, MetaData metaData) {
        if (result != null) {
            isLoading = false;
            swpLayout.setRefreshing(false);
            if (mList.size() == 0) {
                mList.addAll(result);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                newsAdapter = new LocationAdapter(mList, getContext(), this);
                rcvNews.setLayoutManager(layoutManager);
                rcvNews.setAdapter(newsAdapter);
            } else {
                if (!mList.containsAll(result)) {
                    mList.addAll(result);
                    newsAdapter.notifyDataSetChanged();
                }
            }
        }
        if (metaData != null) {
            mMetaData = metaData;
        }
    }

    @Override
    public void getShareLinkSuccess(ShareLink result) {
        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(getContext(), "Cảm ơn bạn đã chia sẻ !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(result.getShare_link()))
                .build();
        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
    }

    @Override
    public void onFail(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyChangeStopped() {
        Toast.makeText(getContext(), getString(R.string.thanks_for_your_contribution), Toast.LENGTH_SHORT).show();
//        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyNewLocation(Locations locations) {
        if (mList == null || newsAdapter == null)
            return;
        if (!mList.contains(locations)) {
            mMetaData.increasePage(1);
        } else {
            if (!locations.isStatus()){
                mList.remove(locations);
                newsAdapter.notifyDataSetChanged();
                return;
            }
            mList.remove(locations);
        }
        mList.add(0, locations);
        newsAdapter.notifyDataSetChanged();
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
        request.setPage(1);
        isLoading = false;
        mList = new ArrayList<>();
        mPresenter.getListNews(request);
    }

    @Override
    public void OnClickDetail(Locations item) {
        Intent intent = new Intent(getActivity(), DetailsView.class);
        intent.putExtra(KeyUtils.KEY_INTENT_LOCATION, item.getId());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onClickStopped(String id) {
        mPresenter.onChangeStopped(id);
    }

    @Override
    public void OnClickShare(Locations item) {
        mPresenter.getShareLink(item.getId());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_saved_list_news:
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    startActivity(new Intent(getContext(), SavedListNewsView.class));
                } else
                    Toast.makeText(getContext(), "Vui lòng đăng nhập để sử dụng chức năng !", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setOnBackPressedListener(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) getActivity()).setOnBackPressedListener(null);

    }

    //listener call back from activity
    @Override
    public void doBack() {

    }

    @Override
    public void onDestroyView() {
        mPresenter.onDestroy();
        super.onDestroyView();
    }
}
