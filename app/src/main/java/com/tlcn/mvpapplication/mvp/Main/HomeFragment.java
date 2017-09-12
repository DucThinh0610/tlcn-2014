package com.tlcn.mvpapplication.mvp.Main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.custom_view.EditTextCustom;
import com.tlcn.mvpapplication.mvp.Main.adapter.PlaceSearchAdapter;
import com.tlcn.mvpapplication.service.GPSTracker;
import com.tlcn.mvpapplication.utils.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.github.kobakei.materialfabspeeddial.FabSpeedDial;

public class HomeFragment extends Fragment implements OnMapReadyCallback,
        View.OnClickListener,
        FabSpeedDial.OnMenuItemClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        PlaceSearchAdapter.OnItemClick {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Bind(R.id.imv_menu)
    ImageView imvMenu;
    @Bind(R.id.fsd_floating)
    FabSpeedDial fsdFloating;
    @Bind(R.id.nv_drawer)
    NavigationView nvDrawer;
    @Bind(R.id.drl_container)
    DrawerLayout drlContainer;
    @Bind(R.id.imv_location)
    CircleImageView imvGetLocation;
    @Bind(R.id.edit_search)
    EditTextCustom editSearch;
    @Bind(R.id.rcv_search)
    RecyclerView rcvSearch;

    private static final LatLngBounds HCM = new LatLngBounds(
            new LatLng(10.748822, 106.594357), new LatLng(10.902364, 106.839401));
    private Marker currentMarker;
    protected GoogleApiClient mGoogleApiClient;
    private PlaceSearchAdapter mAdapter;
    GoogleMap mGoogleMap;
    GPSTracker gpsTracker;
    boolean isFirst = true;
    SupportMapFragment supportMapFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        buildGoogleApiClient();
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
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
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()), Utilities.DEFAULT_MAP_ZOOM));
            }
        }
    }

    private void initListener() {
        imvMenu.setOnClickListener(this);
        fsdFloating.addOnMenuItemClickListener(this);
        imvGetLocation.setOnClickListener(this);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString())) {
                    rcvSearch.setVisibility(View.GONE);
                }
                if (!s.toString().equals("") && mGoogleApiClient.isConnected()) {
                    mAdapter.getFilter().filter(s.toString());
                    rcvSearch.setVisibility(View.VISIBLE);
                } else if (!mGoogleApiClient.isConnected()) {
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initData() {
        gpsTracker = new GPSTracker(getContext());
        mAdapter = new PlaceSearchAdapter(getContext(), mGoogleApiClient, HCM, new AutocompleteFilter.Builder().setCountry("VN").build(), this);
        rcvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvSearch.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_menu:
                drlContainer.openDrawer(Gravity.START);
                break;
            case R.id.imv_location: {
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()), mGoogleMap.getCameraPosition().zoom);
                mGoogleMap.animateCamera(cameraUpdate);
            }
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
        PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                .getPlaceById(mGoogleApiClient, placeId);
        placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(@NonNull PlaceBuffer places) {
                if (places.getCount() == 1) {
                    if (null != currentMarker) {
                        currentMarker.remove();
                    }
                    currentMarker = mGoogleMap.addMarker(new MarkerOptions()
                            .position(places.get(0).getLatLng())
                            .title(places.get(0).getName().toString()));
                    currentMarker.showInfoWindow();
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(places.get(0).getLatLng(), mGoogleMap.getCameraPosition().zoom);
                    mGoogleMap.animateCamera(cameraUpdate);
                } else {
                }
            }
        });
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();
        mGoogleApiClient.connect();
    }

}
