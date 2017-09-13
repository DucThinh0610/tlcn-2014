package com.tlcn.mvpapplication.mvp.ChooseLocation.view;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.mvp.ChooseLocation.presenter.ChooseLocationPresenter;
import com.tlcn.mvpapplication.mvp.Main.adapter.PlaceSearchAdapter;
import com.tlcn.mvpapplication.service.GPSTracker;
import com.tlcn.mvpapplication.utils.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tskil on 9/13/2017.
 */

public class ChooseLocationView extends AppCompatActivity implements
        View.OnClickListener,
        IChooseLocationView,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        PlaceSearchAdapter.OnItemClick,
        OnMapReadyCallback {

    //Todo: Binding
    @Bind(R.id.imv_back_activity)
    ImageView imvBackActivity;
    @Bind(R.id.tv_save)
    TextView tvSave;
    @Bind(R.id.edit_search)
    EditText editSearch;
    @Bind(R.id.cv_choose_on_map)
    CardView cvChooseOnMap;
    @Bind(R.id.rcv_search)
    RecyclerView rcvSearch;
    @Bind(R.id.tb_choose_on_map)
    Toolbar tbChooseOnMap;
    @Bind(R.id.tb_default)
    Toolbar tbDefault;
    @Bind(R.id.rl_map)
    RelativeLayout rlMap;
    @Bind(R.id.imv_back_com)
    ImageView imvBackCOM;
    @Bind(R.id.tv_save_com)
    TextView tvSaveCOM;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_title_com)
    TextView tvTitleCOM;

    //Todo: Declaring
    SupportMapFragment supportMapFragment;
    ChooseLocationPresenter mPresenter = new ChooseLocationPresenter();
    private static final LatLngBounds HCM = new LatLngBounds(new LatLng(10.748822, 106.594357), new LatLng(10.902364, 106.839401));
    private PlaceSearchAdapter mAdapter;
    GoogleMap mGoogleMap;
    GPSTracker gpsTracker;
    boolean isFirst = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);
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
        imvBackActivity.setOnClickListener(this);
        cvChooseOnMap.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        imvBackCOM.setOnClickListener(this);
        tvSaveCOM.setOnClickListener(this);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString())) {
                    rcvSearch.setVisibility(View.GONE);
                }
                if (!s.toString().equals("") && mPresenter.getGoogleApiClient().isConnected()) {
                    mAdapter.getFilter().filter(s.toString());
                    rcvSearch.setVisibility(View.VISIBLE);
                } else if (!mPresenter.getGoogleApiClient().isConnected()) {
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initData() {
        // hiển thị các view được làm ở đây. như các nút hoặc các dữ liệu cứng, intent, adapter
        gpsTracker = new GPSTracker(getBaseContext());
        mAdapter = new PlaceSearchAdapter(getBaseContext(), mPresenter.getGoogleApiClient(), HCM, new AutocompleteFilter.Builder().setCountry("VN").build(), this);
        rcvSearch.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        rcvSearch.setAdapter(mAdapter);
        if (getIntent().getExtras() != null) {
            String title = "Đặt địa chỉ " + getIntent().getStringExtra("title").toLowerCase();
            tvTitle.setText(title);
            tvTitleCOM.setText(title);
        }
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.imv_back_activity:
                finish();
                break;
            case R.id.tv_save:
                Intent intent = new Intent();
                intent.putExtra("latitude", mGoogleMap.getCameraPosition().target.latitude);
                intent.putExtra("longitude", mGoogleMap.getCameraPosition().target.longitude);
                setResult(101, intent);
                break;
            case R.id.tv_save_com:
                Intent intent2 = new Intent();
                intent2.putExtra("latitude", mGoogleMap.getCameraPosition().target.latitude);
                intent2.putExtra("longitude", mGoogleMap.getCameraPosition().target.longitude);
                setResult(101, intent2);
                break;
            case R.id.cv_choose_on_map:
                tbDefault.setVisibility(View.GONE);
                tbChooseOnMap.setVisibility(View.VISIBLE);
                rlMap.setVisibility(View.VISIBLE);
                cvChooseOnMap.animate()
                        .alpha(0.0f)
                        .setDuration(100)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                cvChooseOnMap.setVisibility(View.GONE);
                            }
                        });
                break;
            case R.id.imv_back_com:
                tbChooseOnMap.setVisibility(View.GONE);
                rlMap.setVisibility(View.GONE);
                tbDefault.setVisibility(View.VISIBLE);
                cvChooseOnMap.animate()
                        .alpha(1)
                        .setDuration(100)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                cvChooseOnMap.setVisibility(View.VISIBLE);
                            }
                        });

                break;
        }
    }

    @Override
    public void getDetailPlaceSuccess(PlaceBuffer places) {

    }

    @Override
    public void onFail(String message) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.v("Google API Callback", "Connection Done");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v("Google API Callback", "Connection Suspended");
        Log.v("Code", String.valueOf(i));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.v("Google API Callback", "Connection Failed");
        Log.v("Error Code", String.valueOf(connectionResult.getErrorCode()));
    }

    @Override
    public void onClickItem(String placeId, String placeDetail) {
        editSearch.setText(placeDetail);
        rcvSearch.setVisibility(View.GONE);
        mPresenter.getDetailPlace(placeId);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        if (isFirst) {
            if (gpsTracker.canGetLocation()) {
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()), Utilities.DEFAULT_MAP_ZOOM));
            }
        }
        mGoogleMap.setMyLocationEnabled(true);
    }

    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}