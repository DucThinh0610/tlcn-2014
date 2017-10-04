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
import com.google.firebase.auth.FirebaseAuth;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.dialog.DialogProgress;
import com.tlcn.mvpapplication.model.ObjectSerializable;
import com.tlcn.mvpapplication.mvp.details.adapter.PostAdapter;
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
        OnMapReadyCallback, PostAdapter.OnItemClick {
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
    @Bind(R.id.imv_save)
    ImageView imvSave;

    //Todo: Declaring
    DialogProgress mProgressDialog;
    DetailsPresenter mPresenter = new DetailsPresenter();
    SupportMapFragment supportMapFragment;
    GoogleMap mGoogleMap;
    PostAdapter mPostAdapter;

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
        imvSave.setOnClickListener(this);
    }

    private void initData() {
        // hiển thị các view được làm ở đây. như các nút hoặc các dữ liệu cứng, intent, adapter
        if (getIntent().getExtras() != null) {
            ObjectSerializable objectSerialized = (ObjectSerializable) getIntent().getSerializableExtra(KeyUtils.KEY_INTENT_LOCATION);
            mPresenter.setLocation(objectSerialized.getObject());
            mPresenter.getListPostFromSV();
            mPresenter.getSaveState();
        }
        tvTitle.setText(mPresenter.getLocation().getTitle());
        tvCreatedAt.setText(DateUtils.formatDateToString(mPresenter.getLocation().getLast_modify()));
        mPostAdapter = new PostAdapter(mPresenter.getListPost(), DetailsView.this, this);
        rcvImages.setLayoutManager(new LinearLayoutManager(this));
        rcvImages.setNestedScrollingEnabled(false);
        rcvImages.setAdapter(mPostAdapter);
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
            case R.id.imv_save:
                if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
                    mPresenter.saveLocations();
                    mPresenter.getSaveState();
                }
                else {
                    Toast.makeText(this, "abc", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void getSaveStateSuccess(boolean isSave) {
        if(isSave){
            imvSave.setImageResource(R.drawable.ic_save_red);
        }
        else {
            imvSave.setImageResource(R.drawable.ic_save_white);
        }
    }

    @Override
    public void saveLocationSuccess() {
        mPresenter.getSaveState();
    }

    @Override
    public void onFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getPostSuccess() {
        mPostAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActionSuccess() {
        mPostAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setAllGesturesEnabled(false);
        if (mGoogleMap != null) {
            mGoogleMap.addMarker(
                    new MarkerOptions()
                            .position(new LatLng(mPresenter.getLocation().getLat(), mPresenter.getLocation().getLng()))
                            .title(getString(R.string.traffic_jam_location_non_star))
            ).showInfoWindow();
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mPresenter.getLocation().getLat(), mPresenter.getLocation().getLng()), KeyUtils.DEFAULT_MAP_ZOOM));
        }
    }

    @Override
    public void onClickDislike(String id) {
        mPresenter.actionDislike(id);
    }

    @Override
    public void onClickLike(String id) {
        mPresenter.actionLike(id);
    }
}
