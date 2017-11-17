package com.tlcn.mvpapplication.mvp.direction_screen.view;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.model.ObjectSerializable;
import com.tlcn.mvpapplication.mvp.direction_screen.presenter.DirectionPresenter;
import com.tlcn.mvpapplication.utils.KeyUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DirectionActivity extends AppCompatActivity implements LocationListener,
        OnMapReadyCallback,
        View.OnClickListener,
        IDirectionView {
    private GoogleMap mGoogleMap;
    @Bind(R.id.imv_quick_upload)
    CircleImageView imvUpload;
    DirectionPresenter mPresenter;
    private List<Polyline> polylinePaths = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        ButterKnife.bind(this);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        supportMapFragment.getMapAsync(this);
        mPresenter = new DirectionPresenter();
        mPresenter.attachView(this);
        mPresenter.onCreate();
        initData();
        initListener();
    }

    private void initListener() {
        imvUpload.setOnClickListener(this);
    }

    private void initData() {
        ObjectSerializable objectSerialized = (ObjectSerializable) getIntent().getSerializableExtra(KeyUtils.KEY_INTENT_DIRECTION);
        mPresenter.setRouteFromObj(objectSerialized.getObject());
    }

    private void initDirection() {
        mGoogleMap.addMarker(new MarkerOptions()
                .title(mPresenter.getRoutes().getLeg().get(0).getStartAddress())
                .position(mPresenter.getRoutes().getLeg().get(0).getStartLocation().getLatLag()));
        mGoogleMap.addMarker(new MarkerOptions()
                .title(mPresenter.getRoutes().getLeg().get(0).getEndAddress())
                .position(mPresenter.getRoutes().getLeg().get(0).getEndLocation().getLatLag()));
        PolylineOptions polylineOptions = new PolylineOptions().
                geodesic(true).
                color(ContextCompat.getColor(DirectionActivity.this, R.color.color_polyline_chose)).
                width(25).clickable(false);

        for (LatLng latLng : mPresenter.getRoutes().getPoints()) {
            polylineOptions.add(latLng);
        }
        polylinePaths.add(mGoogleMap.addPolyline(polylineOptions));
        CameraUpdate factory = CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                        .target(mPresenter.getRoutes().getLeg().get(0).getStartLocation().getLatLag())
                        .zoom(KeyUtils.DEFAULT_MAP_ZOOM_DIRECTION)
                        .tilt(60)
                        .build());
        mGoogleMap.animateCamera(factory);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        initDirection();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onFail(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
