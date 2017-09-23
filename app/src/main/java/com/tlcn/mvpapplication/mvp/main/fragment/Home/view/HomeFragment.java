package com.tlcn.mvpapplication.mvp.main.fragment.Home.view;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import com.google.maps.android.ui.BubbleIconFactory;
import com.google.maps.android.ui.IconGenerator;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.caches.image.ImageLoader;
import com.tlcn.mvpapplication.custom_view.EditTextCustom;
import com.tlcn.mvpapplication.dialog.ConfirmDialog;
import com.tlcn.mvpapplication.dialog.DialogProgress;
import com.tlcn.mvpapplication.model.News;
import com.tlcn.mvpapplication.model.direction.Route;
import com.tlcn.mvpapplication.mvp.details.view.DetailsView;
import com.tlcn.mvpapplication.mvp.main.adapter.PlaceSearchAdapter;
import com.tlcn.mvpapplication.mvp.main.fragment.Home.presenter.HomeFragmentPresenter;
import com.tlcn.mvpapplication.service.GPSTracker;
import com.tlcn.mvpapplication.utils.DialogUtils;
import com.tlcn.mvpapplication.utils.KeyUtils;
import com.tlcn.mvpapplication.utils.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.github.kobakei.materialfabspeeddial.FabSpeedDial;

import static com.facebook.login.widget.ProfilePictureView.TAG;
import static java.util.Arrays.asList;

public class HomeFragment extends Fragment implements OnMapReadyCallback,
        View.OnClickListener,
        FabSpeedDial.OnMenuItemClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        PlaceSearchAdapter.OnItemClick,
        GoogleMap.OnCameraIdleListener,
        IHomeFragmentView, GoogleMap.OnPolylineClickListener, GoogleMap.OnMapLongClickListener {

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
    @Bind(R.id.rl_location)
    RelativeLayout rlLocation;

    private DialogProgress mProgressDialog;
    private ConfirmDialog mConfirmDialog;
    private HomeFragmentPresenter mPresenter = new HomeFragmentPresenter();

    private static final LatLngBounds HCM = new LatLngBounds(new LatLng(10.748822, 106.594357), new LatLng(10.902364, 106.839401));
    private Marker currentMarker;

    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private List<Marker> placeMarker = new ArrayList<>();
    List<Circle> temps = new ArrayList<>();
    private PlaceSearchAdapter mAdapter;
    GoogleMap mGoogleMap;
    GPSTracker gpsTracker;
    boolean isFirst = true;
    SupportMapFragment supportMapFragment;
    CallbackManager mCallbackManager;
    FirebaseAuth mFirebaseAuth;

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
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setOnCameraIdleListener(this);
        mGoogleMap.setOnMapLongClickListener(this);
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
        }
        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                showDialog();
            }
        });
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                showDialog();
                return false;
            }
        });
        mGoogleMap.setOnPolylineClickListener(this);
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

    private void showDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_chiduong);
        LinearLayout lnl_xemthongtin = (LinearLayout) dialog.findViewById(R.id.lnl_xemthongtin);
        LinearLayout lnl_chiduong = (LinearLayout) dialog.findViewById(R.id.lnl_chiduong);
        lnl_xemthongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getDetailNews(currentMarker.getPosition());
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
        imvMenu.setOnClickListener(this);
        fsdFloating.addOnMenuItemClickListener(this);
        rlLocation.setOnClickListener(this);
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
        mAdapter = new PlaceSearchAdapter(getContext(), mPresenter.getGoogleApiClient(), HCM, new AutocompleteFilter.Builder().setCountry("VN").build(), this);
        rcvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvSearch.setAdapter(mAdapter);
        editSearch.setSingleLine(true);
        editSearch.setEllipsize(TextUtils.TruncateAt.END);
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
    public void onClickItem(String placeId, String placeDetail) {
        editSearch.setText(placeDetail);
        rcvSearch.setVisibility(View.GONE);
        mPresenter.getDetailPlace(placeId);
    }

    @Override
    public void getDetailNewsSuccess(News res) {
        if (res != null) {
            Intent intent = new Intent(getContext(), DetailsView.class);
            intent.putExtra(KeyUtils.INTENT_KEY_ID, res.getId());
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
    public void getDirectionSuccess(List<Route> routes) {
        mGoogleMap.clear();
        for (Route route : routes) {
            originMarkers.add(mGoogleMap.addMarker(new MarkerOptions()
                    .title(route.getLeg().get(0).getStartAddress())
                    .position(route.getLeg().get(0).getStartLocation().getLatLag())));
            destinationMarkers.add(mGoogleMap.addMarker(new MarkerOptions()
                    .title(route.getLeg().get(0).getEndAddress())
                    .position(route.getLeg().get(0).getEndLocation().getLatLag())));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(ContextCompat.getColor(getContext(), R.color.color_polyline)).
                    width(20).clickable(true);

            for (LatLng latLng : route.getPoints()) {
                polylineOptions.add(latLng);
            }

            polylinePaths.add(mGoogleMap.addPolyline(polylineOptions));
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(routes.get(0).getBound().getNortheast().getLatLag());
        builder.include(routes.get(0).getBound().getSouthwest().getLatLag());
        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.15);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));
        for (int i = 0; i < polylinePaths.size(); i++) {
            Polyline item = polylinePaths.get(i);
            if (i == 0) {
                item.setColor(ContextCompat.getColor(getContext(), R.color.color_polyline_chose));
                item.setZIndex(1.0f);
            } else {
                item.setColor(ContextCompat.getColor(getContext(), R.color.color_polyline));
                item.setZIndex(0.0f);
            }
        }

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
                            onCameraIdle();
                            mPresenter.setBoundRadiusLoad(mPresenter.getBoundRadiusLoad() + 100);
                            mPresenter.getInfoPlace(mGoogleMap.getCameraPosition().target);
                        }

                        @Override
                        public void onClickCancel() {
                            mPresenter.setContinousShowDialog(false);
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
        for (News item : mPresenter.getListPlace()) {
//            IconGenerator iconGenerator = new IconGenerator(getContext());
            BitmapDescriptor bitmapDescriptor;
            if (item.getRating() < 40) {
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_green);
            } else if (item.getRating() >= 40 && item.getRating() <= 80) {
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_yellow);
            } else {
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_red);
            }
            placeMarker.add(mGoogleMap.addMarker(new MarkerOptions()
                    .icon(bitmapDescriptor)
                    .position(new LatLng(item.getLatitude(), item.getLongitude()))));
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
        mPresenter.getInfoPlace(mGoogleMap.getCameraPosition().target);
        Circle circle = mGoogleMap.addCircle(new CircleOptions()
                .center(mGoogleMap.getCameraPosition().target)
                .radius(mPresenter.getBoundRadiusLoad())
                .strokeColor(getResources().getColor(R.color.color_transparent))
                .fillColor(getResources().getColor(R.color.color_bound_transparent)));
        for (Circle item : temps) {
            item.remove();
        }
        temps.add(circle);
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
        for (Polyline item : polylinePaths) {
            if (polyline.getId().equals(item.getId())) {
                item.setColor(ContextCompat.getColor(getContext(), R.color.color_polyline_chose));
                item.setZIndex(1.0f);
            } else {
                item.setColor(ContextCompat.getColor(getContext(), R.color.color_polyline));
                item.setZIndex(0.0f);
            }
        }
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

    ;
}
