package com.tlcn.mvpapplication.mvp.main.fragment.Home.view;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.caches.image.ImageLoader;
import com.tlcn.mvpapplication.custom_view.EditTextCustom;
import com.tlcn.mvpapplication.dialog.ConfirmDialog;
import com.tlcn.mvpapplication.dialog.DialogProgress;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.model.ObjectSerializable;
import com.tlcn.mvpapplication.model.direction.Route;
import com.tlcn.mvpapplication.mvp.details.view.DetailsView;
import com.tlcn.mvpapplication.mvp.direction_screen.view.DirectionActivity;
import com.tlcn.mvpapplication.mvp.main.adapter.PlaceSearchAdapter;
import com.tlcn.mvpapplication.mvp.main.fragment.Home.adapter.DirectionAdapter;
import com.tlcn.mvpapplication.mvp.main.fragment.Home.presenter.HomePresenter;
import com.tlcn.mvpapplication.mvp.main.view.MainActivity;
import com.tlcn.mvpapplication.mvp.setting.view.SettingView;
import com.tlcn.mvpapplication.service.GPSTracker;
import com.tlcn.mvpapplication.utils.DialogUtils;
import com.tlcn.mvpapplication.utils.KeyUtils;
import com.tlcn.mvpapplication.utils.LogUtils;
import com.tlcn.mvpapplication.utils.MapUtils;

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.github.kobakei.materialfabspeeddial.FabSpeedDial;

import static java.util.Arrays.asList;

public class HomeFragment extends Fragment implements OnMapReadyCallback,
        View.OnClickListener,
        FabSpeedDial.OnMenuItemClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        PlaceSearchAdapter.OnItemClick,
        GoogleMap.OnCameraIdleListener,
        IHomeView, GoogleMap.OnPolylineClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnCameraMoveListener,
        SlidingUpPanelLayout.PanelSlideListener, MainActivity.OnBackPressedListener {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    private static final String TAG = HomeFragment.class.getSimpleName();
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
    @Bind(R.id.rl_location)
    RelativeLayout rlLocation;
    @Bind(R.id.imv_center)
    ImageView imvCenter;
    @Bind(R.id.sliding_layout)
    SlidingUpPanelLayout mSlidingUpPanelLayout;
    @Bind(R.id.rlt_search)
    RelativeLayout rltSearch;
    @Bind(R.id.tv_dis_time)
    TextView tvDistanceTime;
    @Bind(R.id.rtb_level)
    AppCompatRatingBar rtbLevel;
    @Bind(R.id.tv_start_location)
    TextView tvStartLocation;
    @Bind(R.id.tv_end_location)
    TextView tvEndLocation;
    @Bind(R.id.tv_count)
    TextView tvCountLocation;
    @Bind(R.id.rcv_route)
    RecyclerView rcvDirection;
    @Bind(R.id.ll_start_direction)
    LinearLayout llStartDirection;
    @Bind(R.id.btn_start)
    Button btnStart;

    private DialogProgress mProgressDialog;
    private ConfirmDialog mConfirmDialog;
    private HomePresenter mPresenter = new HomePresenter();
    private Marker currentMarker;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private List<Marker> placeMarker = new ArrayList<>();
    Circle circle;
    private boolean isShowCircle = true;
    private LatLng locationCircleCenter;
    private PlaceSearchAdapter mAdapter;
    GoogleMap mGoogleMap;
    GPSTracker gpsTracker;
    boolean isFirst = true;
    SupportMapFragment supportMapFragment;
    CallbackManager mCallbackManager;
    FirebaseAuth mFirebaseAuth;
    private Context mContext;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        LogUtils.LOGE("Facebook Login", loginResult.getAccessToken().getToken());
                        handleFacebookAccessToken(loginResult.getAccessToken());

                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.e("Facebook Login", exception.toString());
                    }
                });
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        mFirebaseAuth = FirebaseAuth.getInstance();
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
        ButterKnife.bind(this, v);
        mPresenter.attachView(this);
        mPresenter.onCreate();
        initData();
        initListener();
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        if (isFirst) {
            if (gpsTracker.canGetLocation()) {
                mPresenter.setLngStart(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()));
                CameraUpdate cameraUpdate;
                if (mPresenter.getCameraPosition() == null) {
                    cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()), KeyUtils.DEFAULT_MAP_ZOOM);
                } else {
                    cameraUpdate = CameraUpdateFactory.newCameraPosition(mPresenter.getCameraPosition());
                }
                mGoogleMap.moveCamera(cameraUpdate);
            }
            locationCircleCenter = mGoogleMap.getCameraPosition().target;
            circle = mGoogleMap.addCircle(MapUtils.circleOptions(getContext(),
                    locationCircleCenter,
                    mPresenter.getBoundRadiusLoad()));
            mPresenter.getInfoPlace(mGoogleMap.getCameraPosition().target);
        }
        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                showDialog(marker);
            }
        });
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                showDialog(marker);
                return false;
            }
        });
        checkOffset(mPresenter.isStateUI());
        if (mPresenter.getRoutes().size() != 0) {
            showDirection();
        }
        mGoogleMap.setOnPolylineClickListener(this);
        mGoogleMap.setOnCameraIdleListener(this);
        mGoogleMap.setOnMapLongClickListener(this);
        mGoogleMap.setOnCameraMoveListener(this);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            mPresenter.pushNotificationToken(user.getUid(), FirebaseInstanceId.getInstance().getToken());
                            View v = nvDrawer.getHeaderView(0);
                            final Button btnLogin = (Button) v.findViewById(R.id.btn_login);
                            final LinearLayout lnlLoginSuccess = (LinearLayout) v.findViewById(R.id.lnl_login_success);
                            CircleImageView imvAvatar = (CircleImageView) v.findViewById(R.id.imv_avatar);
                            TextView tvName = (TextView) v.findViewById(R.id.tv_name);
                            TextView tvLogOut = (TextView) v.findViewById(R.id.tv_log_out);
                            tvLogOut.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    btnLogin.setVisibility(View.VISIBLE);
                                    lnlLoginSuccess.setVisibility(View.GONE);
                                    LoginManager.getInstance().logOut();
                                    mFirebaseAuth.signOut();
                                }
                            });
                            btnLogin.setVisibility(View.GONE);
                            lnlLoginSuccess.setVisibility(View.VISIBLE);
                            ImageLoader.load(getContext(), String.valueOf(user.getPhotoUrl()), imvAvatar);
                            tvName.setText(user.getDisplayName());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getContext(), getString(R.string.authentication_failed),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void showDialog(final Marker marker) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_direct);
        LinearLayout lnl_xemthongtin = (LinearLayout) dialog.findViewById(R.id.lnl_xemthongtin);
        LinearLayout lnl_chiduong = (LinearLayout) dialog.findViewById(R.id.lnl_chiduong);
        lnl_xemthongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getDetailLocation(marker.getPosition());
                dialog.dismiss();
            }
        });
        lnl_chiduong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getDirectionFromTwoPoint();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void initListener() {
        nvDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.setting:
                        startActivity(new Intent(getContext(), SettingView.class));
                        break;
                    case R.id.feedback:
                        break;
                    case R.id.about_info:
                        break;
                    case R.id.share_app:
                        break;
                }
                return false;
            }
        });
        imvMenu.setOnClickListener(this);
        fsdFloating.addOnMenuItemClickListener(this);
        rlLocation.setOnClickListener(this);
        llStartDirection.setOnClickListener(this);
        btnStart.setOnClickListener(this);
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
        mSlidingUpPanelLayout.addPanelSlideListener(this);
    }

    private void initData() {
        rcvSearch.setVisibility(View.GONE);
        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_header_navigation, null);
        final Button btnLogin = (Button) v.findViewById(R.id.btn_login);
        final LinearLayout lnlLoginSuccess = (LinearLayout) v.findViewById(R.id.lnl_login_success);
        CircleImageView imvAvatar = (CircleImageView) v.findViewById(R.id.imv_avatar);
        TextView tvName = (TextView) v.findViewById(R.id.tv_name);
        TextView tvLogOut = (TextView) v.findViewById(R.id.tv_log_out);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(HomeFragment.this, asList("email", "public_profile"));
            }
        });
        if (mFirebaseAuth.getCurrentUser() != null) {
            FirebaseUser user = mFirebaseAuth.getCurrentUser();
            btnLogin.setVisibility(View.GONE);
            lnlLoginSuccess.setVisibility(View.VISIBLE);
            ImageLoader.load(getContext(), String.valueOf(user.getPhotoUrl()), imvAvatar);
            tvName.setText(user.getDisplayName());
            tvLogOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnLogin.setVisibility(View.VISIBLE);
                    lnlLoginSuccess.setVisibility(View.GONE);
                    LoginManager.getInstance().logOut();
                    mFirebaseAuth.signOut();
                }
            });
        } else {
            btnLogin.setVisibility(View.VISIBLE);
            lnlLoginSuccess.setVisibility(View.GONE);
        }
        nvDrawer.addHeaderView(v);
        gpsTracker = new GPSTracker(getContext());

        //adapter search
        mAdapter = new PlaceSearchAdapter(getContext(), mPresenter.getGoogleApiClient(), MapUtils.HCM, new AutocompleteFilter.Builder().setCountry("VN").build(), this);
        rcvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvSearch.setAdapter(mAdapter);

        editSearch.setSingleLine(true);
        editSearch.setEllipsize(TextUtils.TruncateAt.END);
        mSlidingUpPanelLayout.setAnchorPoint(0.5f);
        mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        mSlidingUpPanelLayout.setCoveredFadeColor(getResources().getColor(R.color.transparent));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_menu:
                drlContainer.openDrawer(Gravity.START);
                break;
            case R.id.rl_location: {
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()), 15f);
                mGoogleMap.animateCamera(cameraUpdate);
                break;
            }
            case R.id.ll_start_direction:
            case R.id.btn_start:
                Intent intent = new Intent(getContext(), DirectionActivity.class);
                intent.putExtra(KeyUtils.KEY_INTENT_DIRECTION, new ObjectSerializable(mPresenter.getRouteSelected()));
                startActivity(intent);
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
    public void onClickItem(String placeId, String placeDetail) {
        editSearch.setText(placeDetail);
        rcvSearch.setVisibility(View.GONE);
        mPresenter.getDetailPlace(placeId);
    }

    @Override
    public void getDetailLocationSuccess(Locations res) {
        if (res != null) {
            Intent intent = new Intent(getActivity(), DetailsView.class);
            intent.putExtra(KeyUtils.KEY_INTENT_LOCATION, res.getId());
            startActivity(intent);
        }
    }

    @Override
    public void getDetailPlaceSuccess(PlaceBuffer places) {
        if (null != currentMarker) {
            currentMarker.remove();
        }
        currentMarker = mGoogleMap.addMarker(new MarkerOptions()
                .position(places.get(0).getLatLng())
                .title(places.get(0).getName().toString()));
        currentMarker.showInfoWindow();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(places.get(0).getLatLng(), mGoogleMap.getCameraPosition().zoom);
        mPresenter.setLngEnd(places.get(0).getLatLng());
        mGoogleMap.animateCamera(cameraUpdate);
    }

    @Override
    public void onFail(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getDirectionSuccess() {
        mGoogleMap.clear();
        mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        showDirection();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(mPresenter.getRoutes().get(0).getBound().getNortheast().getLatLag());
        builder.include(mPresenter.getRoutes().get(0).getBound().getSouthwest().getLatLag());
        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.15);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));
    }

    @Override
    public void onStartFindDirection() {
        if (currentMarker != null)
            currentMarker.remove();
        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void showDialogConfirmNewRadius() {
        if (mConfirmDialog == null || !mConfirmDialog.isShowing()) {
            mConfirmDialog = new ConfirmDialog(getContext(), "Không tìm thấy thông tin.",
                    "Bạn có muốn mở rộng tìm kiếm?",
                    new ConfirmDialog.IClickConfirmListener() {
                        @Override
                        public void onClickOk() {
                            circle.remove();
                            mPresenter.setBoundRadiusLoad(mPresenter.getBoundRadiusLoad() + 100);
                            circle = mGoogleMap.addCircle(MapUtils.circleOptions(getContext(),
                                    locationCircleCenter,
                                    mPresenter.getBoundRadiusLoad()));
                            onCameraIdle();
                        }

                        @Override
                        public void onClickCancel() {
                            mPresenter.setContinuousShowDialog(false);
                        }
                    });
            mConfirmDialog.show();
        }
    }

    @Override
    public void showPlaces() {
        if (placeMarker != null) {
            for (Marker marker : placeMarker) {
                marker.remove();
            }
        }
        for (Locations item : mPresenter.getListPlace()) {
//            IconGenerator iconGenerator = new IconGenerator(getContext());
            BitmapDescriptor bitmapDescriptor;
            if (item.getLevel() <= 3.75) {
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_green);
            } else if (item.getLevel() > 3.75 && item.getLevel() <= 4.5) {
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_yellow);
            } else {
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_red);
            }
            placeMarker.add(mGoogleMap.addMarker(new MarkerOptions()
                    .icon(bitmapDescriptor)
                    .position(new LatLng(item.getLat(), item.getLng()))));
        }
    }

    @Override
    public void showDirection() {
        polylinePaths.clear();
        originMarkers.add(mGoogleMap.addMarker(new MarkerOptions()
                .title(mPresenter.getRoutes().get(0).getLeg().get(0).getStartAddress())
                .position(mPresenter.getRoutes().get(0).getLeg().get(0).getStartLocation().getLatLag())));
        destinationMarkers.add(mGoogleMap.addMarker(new MarkerOptions()
                .title(mPresenter.getRoutes().get(0).getLeg().get(0).getEndAddress())
                .position(mPresenter.getRoutes().get(0).getLeg().get(0).getEndLocation().getLatLag())));
        for (Route route : mPresenter.getRoutes()) {
            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(ContextCompat.getColor(getContext(), R.color.color_polyline)).
                    width(20).clickable(true);

            for (LatLng latLng : route.getPoints()) {
                polylineOptions.add(latLng);
            }
            polylinePaths.add(mGoogleMap.addPolyline(polylineOptions));
        }
        for (int i = 0; i < polylinePaths.size(); i++) {
            Polyline item = polylinePaths.get(i);
            if (mPresenter.getRoutes().get(i).isSelected()) {
                item.setColor(ContextCompat.getColor(getContext(), R.color.color_polyline_chose));
                item.setZIndex(1.0f);
                mPresenter.getRoutes().get(i).setSelected(true);
            } else {
                item.setColor(ContextCompat.getColor(getContext(), R.color.color_polyline));
                item.setZIndex(0.0f);
                mPresenter.getRoutes().get(i).setSelected(false);
            }
        }
        showInfoDirection();
    }

    @Override
    public void showInfoDirection() {
        tvDistanceTime.setText(Html.fromHtml(mPresenter.getRouteSelected().getTimeAndDistance()));
        tvEndLocation.setText(mPresenter.getRouteSelected().getEndLocation());
        tvStartLocation.setText(mPresenter.getRouteSelected().getStartLocation());
        rtbLevel.setRating(mPresenter.getRouteSelected().getCurrentLevel());
        tvCountLocation.setText(Html.fromHtml(mPresenter.getRouteSelected().getCountLocation()));
        //adapter direction
        rcvDirection.setLayoutManager(new StickyHeaderLayoutManager());
        rcvDirection.setAdapter(new DirectionAdapter(getContext(), mPresenter.getRouteSelected(), new DirectionAdapter.OnClickItemListener() {
            @Override
            public void onClickStartDetail(String id) {
                Intent intent = new Intent(getActivity(), DetailsView.class);
                intent.putExtra(KeyUtils.KEY_INTENT_LOCATION, id);
                startActivity(intent);
            }
        }));
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
        mProgressDialog = DialogUtils.showProgressDialog(getContext());
    }

    protected void dismissDialogLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public void onCameraIdle() {
        mPresenter.setCameraPosition(mGoogleMap.getCameraPosition());
        imvCenter.setVisibility(View.GONE);
        if (isShowCircle) {
            float[] distance = new float[2];
            CircleOptions opts = new CircleOptions()
                    .center(locationCircleCenter)
                    .radius(mPresenter.getBoundRadiusLoad())
                    .strokeColor(mContext.getResources().getColor(R.color.color_transparent))
                    .fillColor(mContext.getResources().getColor(R.color.color_bound_transparent));
            Location.distanceBetween(mGoogleMap.getCameraPosition().target.latitude,
                    mGoogleMap.getCameraPosition().target.longitude,
                    opts.getCenter().latitude, opts.getCenter().longitude, distance);
            if (distance[0] > opts.getRadius()) {
                if (circle != null)
                    circle.remove();
                opts.center(mGoogleMap.getCameraPosition().target);
                circle = mGoogleMap.addCircle(opts);
                locationCircleCenter = mGoogleMap.getCameraPosition().target;
                mPresenter.getInfoPlace(mGoogleMap.getCameraPosition().target);
            }
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
    public void onPolylineClick(Polyline polyline) {
        for (int i = 0; i < polylinePaths.size(); i++) {
            Polyline item = polylinePaths.get(i);
            if (polyline.getId().equals(item.getId())) {
                item.setColor(ContextCompat.getColor(getContext(), R.color.color_polyline_chose));
                item.setZIndex(1.0f);
                mPresenter.getRoutes().get(i).setSelected(true);
            } else {
                item.setColor(ContextCompat.getColor(getContext(), R.color.color_polyline));
                item.setZIndex(0.0f);
                mPresenter.getRoutes().get(i).setSelected(false);
            }
        }
        showInfoDirection();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addressList.get(0).getAddressLine(0);
            if (null != currentMarker) {
                currentMarker.remove();
            }
            mPresenter.setLngEnd(latLng);
            currentMarker = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(address));
            currentMarker.showInfoWindow();
        } catch (IOException e) {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter.mListenerDetail != null) {
            mPresenter.mReference.removeEventListener(mPresenter.mListenerDetail);
        }
        if (mPresenter.mListenerInfo != null) {
            mPresenter.mReference.removeEventListener(mPresenter.mListenerInfo);
        }
        mPresenter.saveCurrentStateMap();
        ((MainActivity) getActivity()).setOnBackPressedListener(null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCameraMove() {
        imvCenter.setVisibility(View.VISIBLE);
        CameraPosition cameraPosition = mGoogleMap.getCameraPosition();
        if (cameraPosition.zoom < 14) {
            isShowCircle = false;
            circle.remove();
        } else {
            isShowCircle = true;
        }
        if (cameraPosition.zoom > KeyUtils.MAX_MAP_ZOOM) {
            mGoogleMap.setMaxZoomPreference(KeyUtils.MAX_MAP_ZOOM);
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(KeyUtils.MAX_MAP_ZOOM));
        }
        if (cameraPosition.zoom < KeyUtils.MIN_MAP_ZOOM) {
            mGoogleMap.setMinZoomPreference(KeyUtils.MIN_MAP_ZOOM);
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(KeyUtils.MIN_MAP_ZOOM));
        }
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        checkOffset(slideOffset > 0.45f);
    }

    @Override
    public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

    }

    private void checkOffset(boolean isShow) {
        if (isShow) {
            rltSearch.setVisibility(View.GONE);
            rlLocation.setVisibility(View.GONE);
            mGoogleMap.getUiSettings().setAllGesturesEnabled(false);

        } else {
            rltSearch.setVisibility(View.VISIBLE);
            rlLocation.setVisibility(View.VISIBLE);
            mGoogleMap.getUiSettings().setAllGesturesEnabled(true);
        }
        mPresenter.setStateUI(isShow);
    }

    @Override
    public void doBack() {
        if (mSlidingUpPanelLayout.getPanelState() != SlidingUpPanelLayout.PanelState.HIDDEN) {
            mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            mPresenter.getRoutes().clear();
            onStartFindDirection();
        } else {
            DialogUtils.showExitDialog(getActivity());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setOnBackPressedListener(this);
    }
}
