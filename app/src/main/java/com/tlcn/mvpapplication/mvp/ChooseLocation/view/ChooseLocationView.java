package com.tlcn.mvpapplication.mvp.ChooseLocation.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.mvp.ChooseLocation.presenter.ChooseLocationPresenter;
import com.tlcn.mvpapplication.mvp.Main.adapter.PlaceSearchAdapter;

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
        PlaceSearchAdapter.OnItemClick {

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

    //Todo: Declaring
    ChooseLocationPresenter mPresenter = new ChooseLocationPresenter();
    private static final LatLngBounds HCM = new LatLngBounds(new LatLng(10.748822, 106.594357), new LatLng(10.902364, 106.839401));
    private PlaceSearchAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);
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
        mAdapter = new PlaceSearchAdapter(getBaseContext(), mPresenter.getGoogleApiClient(), HCM, new AutocompleteFilter.Builder().setCountry("VN").build(), this);
        rcvSearch.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        rcvSearch.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_back_activity:
                finish();
                break;
            case R.id.tv_save:
                break;
            case R.id.cv_choose_on_map:
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
}
