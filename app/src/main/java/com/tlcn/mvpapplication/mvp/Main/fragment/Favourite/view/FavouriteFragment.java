package com.tlcn.mvpapplication.mvp.Main.fragment.Favourite.view;

import android.app.Dialog;
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

import com.google.android.gms.maps.model.LatLng;
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

    //Todo: Binding
    @Bind(R.id.rcv_favourite)
    RecyclerView rcvFavourite;
    @Bind(R.id.lnl_house)
    LinearLayout lnlHouse;
    @Bind(R.id.lnl_work)
    LinearLayout lnlWork;
    @Bind(R.id.tv_add)
    TextView tvAdd;
    @Bind(R.id.tv_location_house)
    TextView tvLocationHouse;
    @Bind(R.id.tv_location_work)
    TextView tvLocationWork;

    //Todo: Declaring
    FavouritePresenter mPresenter = new FavouritePresenter();
    NewsAdapter newsAdapter;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            //Todo: House
            if (resultCode == 101 || resultCode == 102) {
                if (data.getExtras() != null) {
                    LatLng location = new LatLng(data.getDoubleExtra("latitude", 0), data.getDoubleExtra("longitude", 0));
                    mPresenter.setHouseLocation(location);
                    tvLocationHouse.setText(getString(R.string.have_set));
                }
            }
        } else if (requestCode == 102) {
            //Todo: Work
            if (resultCode == 101 || resultCode == 102) {
                if (data.getExtras() != null) {
                    LatLng location = new LatLng(data.getDoubleExtra("latitude", 0), data.getDoubleExtra("longitude", 0));
                    mPresenter.setWorkLocation(location);
                    tvLocationWork.setText(getString(R.string.have_set));
                }
            }
        } else {
            //Todo: Other
            if (resultCode == 101 || resultCode == 102) {
                if (data.getExtras() != null) {
                    LatLng location = new LatLng(data.getDoubleExtra("latitude", 0), data.getDoubleExtra("longitude", 0));
                    mPresenter.setOtherLocation(location);
                }
            }
        }
    }

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
        lnlHouse.setOnClickListener(this);
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
        switch (view.getId()) {
            case R.id.lnl_house:
                if (!tvLocationHouse.getText().equals(getString(R.string.have_set))) {
                    Intent intent = new Intent(getContext(), ChooseLocationView.class);
                    intent.putExtra("title", getString(R.string.house));
                    startActivityForResult(intent, 101);
                } else
                    showDialog();
                break;
            case R.id.lnl_work:
                if (!tvLocationHouse.getText().equals(getString(R.string.have_set))) {
                    Intent intent2 = new Intent(getContext(), ChooseLocationView.class);
                    intent2.putExtra("title", getString(R.string.work));
                    startActivityForResult(intent2, 102);
                } else showDialog();
                break;
            case R.id.tv_add:
                break;
        }
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.show();
    }
}
