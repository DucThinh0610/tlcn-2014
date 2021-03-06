package com.tlcn.mvpapplication.mvp.direction_screen.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.dialog.NotifyDialog;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.model.ObjectSerializable;
import com.tlcn.mvpapplication.mvp.details.view.DetailsView;
import com.tlcn.mvpapplication.mvp.direction_screen.presenter.DirectionPresenter;
import com.tlcn.mvpapplication.service.GPSTracker;
import com.tlcn.mvpapplication.utils.KeyUtils;
import com.tlcn.mvpapplication.utils.MapUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DirectionActivity extends AppCompatActivity implements LocationListener,
        OnMapReadyCallback,
        View.OnClickListener,
        IDirectionView,
        SensorEventListener,
        GoogleMap.OnCameraMoveStartedListener {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5;
    private static final long MIN_TIME_BW_UPDATES = 1000;
    private static final int STATE_NORMAL = 0, STATE_COMPASS = 1, STATE_POSITION = 2;
    private int stateImvPosition = 2;
    private static float oldBearing = 0;
    boolean isConfigMapDone = false;
    private LocationManager mLocationManager;
    private SensorManager mSensorManager;
    Sensor accelerometer;
    Sensor magnetometer;
    float[] mGravity;
    float[] mGeomagnetic;

    private GoogleMap mGoogleMap;
    @Bind(R.id.imv_position)
    CircleImageView imvPosition;
    @Bind(R.id.imv_quick_upload)
    CircleImageView imvUpload;
    DirectionPresenter mPresenter;
    private List<Polyline> polylinePaths = new ArrayList<>();
    private List<Polyline> polylineLocationPassed = new ArrayList<>();

    private List<Marker> placeMarker = new ArrayList<>();

    private NotifyDialog mNotifyDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_direction);
        ButterKnife.bind(this);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        supportMapFragment.getMapAsync(this);
        mPresenter = new DirectionPresenter();
        mPresenter.attachView(this);
        mPresenter.onCreate();
        initData();
//        initSensor();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (stateImvPosition == STATE_COMPASS)
//            registerSensor();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        unRegisterSensor();
    }

    private void initSensor() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    private void initListener() {
        imvUpload.setOnClickListener(this);
        imvPosition.setOnClickListener(this);
    }

    private void initData() {
        GPSTracker gpsTracker = new GPSTracker(this);
        ObjectSerializable objectSerialized = (ObjectSerializable) getIntent().getSerializableExtra(KeyUtils.KEY_INTENT_DIRECTION);
        mPresenter.setRouteFromObj(objectSerialized.getObject(), gpsTracker.getLatLng());
    }

    private void initDirection() {
        mGoogleMap.addMarker(new MarkerOptions()
                .title(mPresenter.getRoutes().getLeg().get(0).getStartAddress())
                .position(mPresenter.getRoutes().getLeg().get(0).getStartLocation().getLatLag()))
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mGoogleMap.addMarker(new MarkerOptions()
                .title(mPresenter.getRoutes().getLeg().get(0).getEndAddress())
                .position(mPresenter.getRoutes().getLeg().get(0).getEndLocation().getLatLag()))
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        PolylineOptions polylineOptions = new PolylineOptions().
                geodesic(true).
                color(ContextCompat.getColor(DirectionActivity.this, R.color.color_polyline_chose)).
                width(25).clickable(false).zIndex(0.0f);

        for (LatLng latLng : mPresenter.getRoutes().getPoints()) {
            polylineOptions.add(latLng);
        }
        polylinePaths.add(mGoogleMap.addPolyline(polylineOptions));
        CameraUpdate factory = CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                        .target(mPresenter.getRoutes().getLeg().get(0).getStartLocation().getLatLag())
                        .zoom(KeyUtils.DEFAULT_MAP_ZOOM_DIRECTION)
                        .build());
        mGoogleMap.animateCamera(factory);

        for (Locations location : mPresenter.getRoutes().getListLocations()) {
            drawANewLocation(location);
        }
        isConfigMapDone = true;
    }

    @Override
    public void onLocationChanged(Location location) {
        mPresenter.onChangeLocation(location);
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
        mGoogleMap.setOnCameraMoveStartedListener(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.getUiSettings().setCompassEnabled(false);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        initDirection();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_position:
                if (stateImvPosition == STATE_NORMAL) {
                    stateImvPosition = STATE_POSITION;
                    moveCameraToMyLocation();
                } else if (stateImvPosition == STATE_POSITION) {
                    stateImvPosition = STATE_COMPASS;
                    setBearingMap(mPresenter.getBearing(), new LatLng(mGoogleMap.getCameraPosition().target.latitude,
                            mGoogleMap.getCameraPosition().target.longitude));
//                    registerSensor();
                } else {
                    stateImvPosition = STATE_POSITION;
                    moveCameraToMyLocation();
//                    unRegisterSensor();
                }
                setUIImvPosition(stateImvPosition);
                break;
            case R.id.imv_quick_upload:
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onFail(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void drawANewLocation(Locations locations) {
        placeMarker.add(mGoogleMap.addMarker(new MarkerOptions()
                .icon(createBitmap(locations))
                .position(new LatLng(locations.getLat(), locations.getLng()))));
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float bearing;
        if (sensorEvent.sensor.getType() == Sensor.TYPE_GRAVITY)
            mGravity = sensorEvent.values.clone();
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = sensorEvent.values.clone();
        if (mGravity != null && mGeomagnetic != null) {
            float[] rotationMatrix = new float[9];
            if (SensorManager.getRotationMatrix(rotationMatrix, null,
                    mGravity, mGeomagnetic)) {
                float[] remappedRotationMatrix = new float[9];
                switch (getWindowManager().getDefaultDisplay()
                        .getRotation()) {
                    case Surface.ROTATION_0:
                        SensorManager.remapCoordinateSystem(rotationMatrix,
                                SensorManager.AXIS_X, SensorManager.AXIS_Y,
                                remappedRotationMatrix);
                        break;
                    case Surface.ROTATION_90:
                        SensorManager.remapCoordinateSystem(rotationMatrix,
                                SensorManager.AXIS_Y,
                                SensorManager.AXIS_MINUS_X,
                                remappedRotationMatrix);
                        break;
                    case Surface.ROTATION_180:
                        SensorManager.remapCoordinateSystem(rotationMatrix,
                                SensorManager.AXIS_MINUS_X,
                                SensorManager.AXIS_MINUS_Y,
                                remappedRotationMatrix);
                        break;
                    case Surface.ROTATION_270:
                        SensorManager.remapCoordinateSystem(rotationMatrix,
                                SensorManager.AXIS_MINUS_Y,
                                SensorManager.AXIS_X, remappedRotationMatrix);
                        break;
                }
                float results[] = new float[3];
                SensorManager.getOrientation(remappedRotationMatrix,
                        results);

                /* Get measured value */
                bearing = (float) (results[0] * 180 / Math.PI);
                if (bearing < 0) {
                    bearing += 360;
                }
                if (Math.abs(Math.abs(bearing) - Math.abs(oldBearing)) > 2) {
                    oldBearing = bearing;
                }
            }
        }
    }

    @Override
    public void setBearingMap(float bearing, LatLng latLng) {
        if (mGoogleMap != null && isConfigMapDone && stateImvPosition == STATE_COMPASS) {
            CameraUpdate factory = CameraUpdateFactory.newCameraPosition(
                    new CameraPosition.Builder()
                            .target(latLng)
                            .zoom(mGoogleMap.getCameraPosition().zoom < KeyUtils.DEFAULT_MAP_ZOOM_BEARING ? KeyUtils.DEFAULT_MAP_ZOOM_BEARING : mGoogleMap.getCameraPosition().zoom)
                            .bearing(bearing).tilt(65.5f)
                            .build());
            mGoogleMap.animateCamera(factory);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    private void setUIImvPosition(int state) {
        switch (state) {
            case STATE_NORMAL:
                imvPosition.setImageResource(R.drawable.ic_location_grey);
                break;
            case STATE_COMPASS:
                imvPosition.setImageResource(R.drawable.ic_compass);
                break;
            case STATE_POSITION:
                imvPosition.setImageResource(R.drawable.ic_location);
        }
    }

    @Override
    public void registerSensor() {
        oldBearing = 0;
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void unRegisterSensor() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onCameraMoveStarted(int reason) {
        if (reason == REASON_GESTURE) {
//            unRegisterSensor();
            stateImvPosition = STATE_NORMAL;
            setUIImvPosition(stateImvPosition);
        }
    }

    @Override
    public void moveCameraToMyLocation() {
        GPSTracker gpsTracker = new GPSTracker(DirectionActivity.this);
        CameraUpdate factory = CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                        .target(gpsTracker.getLatLng())
                        .zoom(KeyUtils.DEFAULT_MAP_ZOOM_DIRECTION)
                        .bearing(0).tilt(0)
                        .build());
        mGoogleMap.animateCamera(factory);
    }

    @Override
    public void drawAPolyline(final LatLng latLngStart, final LatLng latLngEnd) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PolylineOptions polylineOptions = new PolylineOptions().
                        geodesic(true).
                        color(ContextCompat.getColor(DirectionActivity.this, R.color.color_polyline)).
                        width(25).clickable(false).zIndex(1.0f);
                polylineOptions.add(latLngStart);
                polylineOptions.add(latLngEnd);
                polylineLocationPassed.add(mGoogleMap.addPolyline(polylineOptions));
            }
        });
    }

    @Override
    public void updateLocation(Locations locations) {
        Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                .icon(createBitmap(locations))
                .position(new LatLng(locations.getLat(), locations.getLng())));
        if (placeMarker.contains(marker)) {
            Log.d("Contains", "Contains");
        }
    }

    private static BitmapDescriptor createBitmap(Locations locations) {
        BitmapDescriptor bitmapDescriptor = null;
        switch (KeyUtils.checkLevel(locations.getCurrent_level())) {
            case 1:
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_green);
                break;
            case 2:
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_yellow);
                break;
            case 3:
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_red);
                break;
        }
        return bitmapDescriptor;
    }

    @Override
    public void notifyLocationAdded(final Locations locations, int type) {
        if (mNotifyDialog == null || !mNotifyDialog.isShowing()) {
            mNotifyDialog = new NotifyDialog(DirectionActivity.this, locations, new NotifyDialog.NotifyDialogListener() {
                @Override
                public void OnButtonRightClick() {
                    Intent intent = new Intent(DirectionActivity.this, DetailsView.class);
                    intent.putExtra(KeyUtils.KEY_INTENT_LOCATION, locations.getId());
                    startActivity(intent);
                }
            }, 15, type);
            mNotifyDialog.show();
        }
    }
}
