package com.tlcn.mvpapplication.mvp.details.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.dialog.DialogProgress;
import com.tlcn.mvpapplication.mvp.details.adapter.PostAdapter;
import com.tlcn.mvpapplication.mvp.details.presenter.DetailsPresenter;
import com.tlcn.mvpapplication.utils.DateUtils;
import com.tlcn.mvpapplication.utils.DialogUtils;
import com.tlcn.mvpapplication.utils.KeyUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

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
    CallbackManager mCallbackManager;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallbackManager = CallbackManager.Factory.create();
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
            mPresenter.setIdLocation(getIntent().getStringExtra(KeyUtils.KEY_INTENT_LOCATION));
            mPresenter.getInfoLocation();
            mPresenter.getSaveState();
        }
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
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    mPresenter.saveLocations();
                    mPresenter.getSaveState();
                } else {
                    Toast.makeText(this, R.string.please_login, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void getSaveStateSuccess(boolean isSave) {
        if (isSave) {
            imvSave.setImageResource(R.drawable.ic_save_red);
        } else {
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
        tvTitle.setText(mPresenter.getLocations().getTitle());
        tvCreatedAt.setText(DateUtils.formatDateToString(mPresenter.getLocations().getLast_modify()));
        if (mGoogleMap != null) {
            mGoogleMap.addMarker(
                    new MarkerOptions()
                            .position(new LatLng(mPresenter.getLocations().getLat(), mPresenter.getLocations().getLng()))
                            .title(getString(R.string.traffic_jam_location_non_star))
            ).showInfoWindow();
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mPresenter.getLocations().getLat(), mPresenter.getLocations().getLng()), KeyUtils.DEFAULT_MAP_ZOOM));
        }
    }

    @Override
    public void onActionSuccess() {
        mPostAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setAllGesturesEnabled(false);
    }

    @Override
    public void onClickDislike(String id) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            mPresenter.actionDislike(id);
        } else {
            Toast.makeText(this, R.string.please_login, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickLike(String id) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            mPresenter.actionLike(id);
        } else {
            Toast.makeText(this, R.string.please_login, Toast.LENGTH_SHORT).show();
        }
    }
}
