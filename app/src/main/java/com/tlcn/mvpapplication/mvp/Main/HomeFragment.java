package com.tlcn.mvpapplication.mvp.Main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.service.GPSTracker;
import com.tlcn.mvpapplication.utils.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.kobakei.materialfabspeeddial.FabSpeedDial;

/**
 * Created by tskil on 8/23/2017.
 */

public class HomeFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener, FabSpeedDial.OnMenuItemClickListener {
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Bind(R.id.imv_menu)
    ImageView imvMenu;
    @Bind(R.id.fsd_floating)
    FabSpeedDial fsdFloating;
    @Bind(R.id.nv_drawer)
    NavigationView nvDrawer;
    @Bind(R.id.frl_menu)
    FrameLayout frlMenu;
    @Bind(R.id.drl_container)
    DrawerLayout drlContainer;

    GoogleMap mGoogleMap;
    GPSTracker gpsTracker;
    boolean isFirst = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
        ButterKnife.bind(this, v);
        initData();
        initListener();
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        if (isFirst) {
            if (gpsTracker.canGetLocation()) {
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsTracker.getLatitude(),gpsTracker.getLongitude()), Utilities.DEFAULT_MAP_ZOOM));
            }
        }
    }

    private void initListener() {
        frlMenu.setOnClickListener(this);
        fsdFloating.addOnMenuItemClickListener(this);
    }

    private void initData() {
        // hiển thị các view được làm ở đây. như các nút hoặc các dữ liệu cứng, intent, adapter
        gpsTracker = new GPSTracker(getContext());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frl_menu:
                drlContainer.openDrawer(Gravity.START);
                break;
        }
    }

    @Override
    public void onMenuItemClick(FloatingActionButton miniFab, @Nullable TextView label, int itemId) {
        switch (itemId) {
            case R.id.item_tinhtrang:
                break;
            case R.id.item_mucdo:
                break;
        }
    }
}
