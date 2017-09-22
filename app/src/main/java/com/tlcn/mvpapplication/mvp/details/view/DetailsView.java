package com.tlcn.mvpapplication.mvp.details.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.dialog.DialogProgress;
import com.tlcn.mvpapplication.model.News;
import com.tlcn.mvpapplication.mvp.details.adapter.ImagesAdapter;
import com.tlcn.mvpapplication.mvp.details.presenter.DetailsPresenter;
import com.tlcn.mvpapplication.utils.DateUtils;
import com.tlcn.mvpapplication.utils.DialogUtils;
import com.tlcn.mvpapplication.utils.KeyUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tskil on 9/20/2017.
 */

public class DetailsView extends AppCompatActivity implements IDetailsView,
        View.OnClickListener,
        OnMapReadyCallback {
    //Todo: Binding
    @Bind(R.id.tv_title_header)
    TextView tvTitleHeader;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_description)
    TextView tvDescription;
    @Bind(R.id.tv_created_at)
    TextView tvCreatedAt;
    @Bind(R.id.rcv_images)
    RecyclerView rcvImages;
    @Bind(R.id.imv_back)
    ImageView imvBack;
    //Todo: Declaring
    DialogProgress mProgressDialog;
    DetailsPresenter mPresenter = new DetailsPresenter();
    SupportMapFragment supportMapFragment;
    GoogleMap mGoogleMap;
    ImagesAdapter imagesAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_news);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        mPresenter.onCreate();
        initData();
        initListener();
    }

    private void initListener() {
        //các sự kiện click view được khai báo ở đây
        imvBack.setOnClickListener(this);
    }

    private void initData() {
        // hiển thị các view được làm ở đây. như các nút hoặc các dữ liệu cứng, intent, adapter
        if (getIntent().getExtras() != null) {
            long id = getIntent().getLongExtra(KeyUtils.INTENT_KEY_ID, 0);
            mPresenter.getDetailedNews(id);
        }
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_back:
                finish();
                break;
        }
    }

    @Override
    public void getImagesSuccess() {
        if(mPresenter.getNews()!=null){
            imagesAdapter = new ImagesAdapter(this, mPresenter.getNews().getImages());
            rcvImages.setLayoutManager(new LinearLayoutManager(this));
            rcvImages.setAdapter(imagesAdapter);
            rcvImages.setNestedScrollingEnabled(false);
        }
    }

    @Override
    public void getNewsSuccess() {
        if (mPresenter.getNews() != null) {
            News result = mPresenter.getNews();
            tvTitle.setText(result.getTitle());
            tvCreatedAt.setText(DateUtils.formatDateToString(result.getCreated()));
            tvDescription.setText(result.getDescription());
            if (mGoogleMap != null){
                mGoogleMap.addMarker(
                        new MarkerOptions()
                                .position(new LatLng(result.getLatitude(),result.getLongitude()))
                                .title(getString(R.string.traffic_jam_location_non_star))
                ).showInfoWindow();
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(result.getLatitude(),result.getLongitude()),KeyUtils.DEFAULT_MAP_ZOOM));
            }
        }
    }

    @Override
    public void onFail(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setAllGesturesEnabled(false);
    }
}
