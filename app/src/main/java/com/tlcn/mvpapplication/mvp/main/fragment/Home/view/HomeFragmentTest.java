package com.tlcn.mvpapplication.mvp.main.fragment.Home.view;

public class HomeFragmentTest {
//        extends BaseFragment implements View.OnClickListener,
//        FabSpeedDial.OnMenuItemClickListener,
//        GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener,
//        PlaceSearchAdapter.OnItemClick,
//        GoogleMap.OnCameraIdleListener,
//        IHomeView, GoogleMap.OnPolylineClickListener, GoogleMap.OnMapLongClickListener {
//    public static HomeFragmentTest newInstance() {
//        return new HomeFragmentTest();
//    }
//
//    @Bind(R.id.imv_menu)
//    ImageView imvMenu;
//    @Bind(R.id.fsd_floating)
//    FabSpeedDial fsdFloating;
//    @Bind(R.id.nv_drawer)
//    NavigationView nvDrawer;
//    @Bind(R.id.drl_container)
//    DrawerLayout drlContainer;
//    @Bind(R.id.imv_location)
//    CircleImageView imvGetLocation;
//    @Bind(R.id.edit_search)
//    EditTextCustom editSearch;
//    @Bind(R.id.rcv_search)
//    RecyclerView rcvSearch;
//    @Bind(R.id.rl_location)
//    RelativeLayout rlLocation;
//
//    private HomePresenter mPresenter = new HomePresenter();
//    private static final LatLngBounds HCM = new LatLngBounds(new LatLng(10.748822, 106.594357), new LatLng(10.902364, 106.839401));
//    private Marker currentMarker;
//
//    private List<Marker> originMarkers = new ArrayList<>();
//    private List<Marker> destinationMarkers = new ArrayList<>();
//    private List<Polyline> polylinePaths = new ArrayList<>();
//    private List<Marker> placeMarker = new ArrayList<>();
//    List<Circle> temps = new ArrayList<>();
//    private PlaceSearchAdapter mAdapter;
//
//    GPSTracker gpsTracker;
//    boolean isFirst = true;
//    GoogleMap mGoogleMap;
//    CallbackManager mCallbackManager;
//    FirebaseAuth mFirebaseAuth;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        mCallbackManager = CallbackManager.Factory.create();
//        LoginManager.getInstance().registerCallback(mCallbackManager,
//                new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        LogUtils.LOGE("Facebook Login", loginResult.getAccessToken().getToken());
//                        handleFacebookAccessToken(loginResult.getAccessToken());
//
//                    }
//
//                    @Override
//                    public void onCancel() {
//
//                    }
//
//                    @Override
//                    public void onError(FacebookException exception) {
//                        Log.e("Facebook Login", exception.toString());
//                    }
//                });
//        mFirebaseAuth = FirebaseAuth.getInstance();
//        mPresenter.attachView(this);
//        mPresenter.onCreate();
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }
//
//    private void handleFacebookAccessToken(AccessToken token) {
//        Log.d(TAG, "handleFacebookAccessToken:" + token);
//
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        mFirebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
//                            View v = nvDrawer.getHeaderView(0);
//                            final Button btnLogin = (Button) v.findViewById(R.id.btn_login);
//                            final LinearLayout lnlLoginSuccess = (LinearLayout) v.findViewById(R.id.lnl_login_success);
//                            CircleImageView imvAvatar = (CircleImageView) v.findViewById(R.id.imv_avatar);
//                            TextView tvName = (TextView) v.findViewById(R.id.tv_name);
//                            TextView tvLogOut = (TextView) v.findViewById(R.id.tv_log_out);
//                            tvLogOut.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    btnLogin.setVisibility(View.VISIBLE);
//                                    lnlLoginSuccess.setVisibility(View.GONE);
//                                    LoginManager.getInstance().logOut();
//                                    mFirebaseAuth.signOut();
//                                }
//                            });
//                            btnLogin.setVisibility(View.GONE);
//                            lnlLoginSuccess.setVisibility(View.VISIBLE);
//                            ImageLoader.load(getContext(), String.valueOf(user.getPhotoUrl()), imvAvatar);
//                            tvName.setText(user.getDisplayName());
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(getContext(), getString(R.string.authentication_failed),
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//
//    @Override
//    protected void onMapReadyCustom(GoogleMap googleMap) {
//        mGoogleMap = googleMap;
//        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        mGoogleMap.setMyLocationEnabled(true);
//        mGoogleMap.setOnCameraIdleListener(this);
//        mGoogleMap.setOnMapLongClickListener(this);
//        if (isFirst) {
//            if (gpsTracker.canGetLocation()) {
//                mPresenter.setLngStart(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()));
//                CameraUpdate cameraUpdate;
//                if (mPresenter.getCameraPosition() == null) {
//                    cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()), KeyUtils.DEFAULT_MAP_ZOOM);
//                } else {
//                    cameraUpdate = CameraUpdateFactory.newCameraPosition(mPresenter.getCameraPosition());
//                }
//                mGoogleMap.moveCamera(cameraUpdate);
//            }
//        }
//        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//                showDialog(marker);
//            }
//        });
//        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                showDialog(marker);
//                return false;
//            }
//        });
//        mGoogleMap.setOnPolylineClickListener(this);
//    }
//
//    private void showDialog(final Marker marker) {
//        final Dialog dialog = new Dialog(getContext());
//        dialog.setContentView(R.layout.dialog_chiduong);
//        LinearLayout lnl_xemthongtin = (LinearLayout) dialog.findViewById(R.id.lnl_xemthongtin);
//        LinearLayout lnl_chiduong = (LinearLayout) dialog.findViewById(R.id.lnl_chiduong);
//        lnl_xemthongtin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPresenter.getDetailNews(marker.getPosition());
//                dialog.dismiss();
//            }
//        });
//        lnl_chiduong.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPresenter.getDirectionFromTwoPoint();
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }
//
//    @Override
//    protected int getIdMap() {
//        return R.id.map;
//    }
//
//    @Override
//    protected void onInitData() {
//        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_header_navigation, null);
//        final Button btnLogin = (Button) v.findViewById(R.id.btn_login);
//        final LinearLayout lnlLoginSuccess = (LinearLayout) v.findViewById(R.id.lnl_login_success);
//        CircleImageView imvAvatar = (CircleImageView) v.findViewById(R.id.imv_avatar);
//        TextView tvName = (TextView) v.findViewById(R.id.tv_name);
//        TextView tvLogOut = (TextView) v.findViewById(R.id.tv_log_out);
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LoginManager.getInstance().logInWithReadPermissions(HomeFragmentTest.this, asList("email", "public_profile"));
//            }
//        });
//        if (mFirebaseAuth.getCurrentUser() != null) {
//            FirebaseUser user = mFirebaseAuth.getCurrentUser();
//            btnLogin.setVisibility(View.GONE);
//            lnlLoginSuccess.setVisibility(View.VISIBLE);
//            ImageLoader.load(getContext(), String.valueOf(user.getPhotoUrl()), imvAvatar);
//            tvName.setText(user.getDisplayName());
//            tvLogOut.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    btnLogin.setVisibility(View.VISIBLE);
//                    lnlLoginSuccess.setVisibility(View.GONE);
//                    LoginManager.getInstance().logOut();
//                    mFirebaseAuth.signOut();
//                }
//            });
//        } else {
//            btnLogin.setVisibility(View.VISIBLE);
//            lnlLoginSuccess.setVisibility(View.GONE);
//        }
//        nvDrawer.addHeaderView(v);
//        gpsTracker = new GPSTracker(getContext());
//        mAdapter = new PlaceSearchAdapter(getContext(), mPresenter.getGoogleApiClient(), HCM, new AutocompleteFilter.Builder().setCountry("VN").build(), this);
//        rcvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
//        rcvSearch.setAdapter(mAdapter);
//        editSearch.setSingleLine(true);
//        editSearch.setEllipsize(TextUtils.TruncateAt.END);
//    }
//
//    @Override
//    protected void onInitListener() {
//        imvMenu.setOnClickListener(this);
//        fsdFloating.addOnMenuItemClickListener(this);
//        rlLocation.setOnClickListener(this);
//        editSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (TextUtils.isEmpty(s.toString())) {
//                    rcvSearch.setVisibility(View.GONE);
//                }
//                if (!s.toString().equals("") && mPresenter.getGoogleApiClient().isConnected()) {
//                    mAdapter.getFilter().filter(s.toString());
//                    rcvSearch.setVisibility(View.VISIBLE);
//                } else if (!mPresenter.getGoogleApiClient().isConnected()) {
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//    }
//
//    @Override
//    protected int setLayoutId() {
//        return R.layout.fragment_home_test;
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.imv_menu:
//                drlContainer.openDrawer(Gravity.START);
//                break;
//            case R.id.rl_location: {
//                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()), 15f);
//                mGoogleMap.animateCamera(cameraUpdate);
//            }
//        }
//    }
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        Log.v("Google API Callback", "Connection Done");
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//        Log.v("Google API Callback", "Connection Suspended");
//        Log.v("Code", String.valueOf(i));
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Log.v("Google API Callback", "Connection Failed");
//        Log.v("Error Code", String.valueOf(connectionResult.getErrorCode()));
//    }
//
//
//    @Override
//    public void onCameraIdle() {
//        mPresenter.setCameraPosition(mGoogleMap.getCameraPosition());
//        mPresenter.getInfoPlace(mGoogleMap.getCameraPosition().target);
//        Circle circle = mGoogleMap.addCircle(new CircleOptions()
//                .center(mGoogleMap.getCameraPosition().target)
//                .radius(mPresenter.getBoundRadiusLoad())
//                .strokeColor(getActivityContext().getResources().getColor(R.color.color_transparent))
//                .fillColor(getActivityContext().getResources().getColor(R.color.color_bound_transparent)));
//        for (Circle item : temps) {
//            item.remove();
//        }
//        temps.add(circle);
//    }
//
//    @Override
//    public void onMapLongClick(LatLng latLng) {
//        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
//        List<Address> addressList;
//        try {
//            addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//            String address = addressList.get(0).getAddressLine(0);
//            if (null != currentMarker) {
//                currentMarker.remove();
//            }
//            mPresenter.setLngEnd(latLng);
//            currentMarker = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(address));
//            currentMarker.showInfoWindow();
//        } catch (IOException e) {
//            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void onPolylineClick(Polyline polyline) {
//        for (Polyline item : polylinePaths) {
//            if (polyline.getId().equals(item.getId())) {
//                item.setColor(ContextCompat.getColor(getContext(), R.color.color_polyline_chose));
//                item.setZIndex(1.0f);
//            } else {
//                item.setColor(ContextCompat.getColor(getContext(), R.color.color_polyline));
//                item.setZIndex(0.0f);
//            }
//        }
//    }
//
//    @Override
//    public void getDetailNewsSuccess(Locations res) {
//        if (res != null) {
//            Intent intent = new Intent(getContext(), DetailsView.class);
//            intent.putExtra(KeyUtils.INTENT_KEY_ID, res.getId());
//            startActivity(intent);
//        }
//    }
//
//    @Override
//    public void getDetailPlaceSuccess(PlaceBuffer places) {
//        if (null != currentMarker) {
//            currentMarker.remove();
//        }
//        currentMarker = mGoogleMap.addMarker(new MarkerOptions()
//                .position(places.get(0).getLatLng())
//                .title(places.get(0).getName().toString()));
//        currentMarker.showInfoWindow();
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(places.get(0).getLatLng(), mGoogleMap.getCameraPosition().zoom);
//        mPresenter.setLngEnd(places.get(0).getLatLng());
//        mGoogleMap.animateCamera(cameraUpdate);
//    }
//
//    @Override
//    public void onFail(String message) {
//        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void getDirectionSuccess(List<Route> routes) {
//        mGoogleMap.clear();
//        for (Route route : routes) {
//            originMarkers.add(mGoogleMap.addMarker(new MarkerOptions()
//                    .title(route.getLeg().get(0).getStartAddress())
//                    .position(route.getLeg().get(0).getStartLocation().getLatLag())));
//            destinationMarkers.add(mGoogleMap.addMarker(new MarkerOptions()
//                    .title(route.getLeg().get(0).getEndAddress())
//                    .position(route.getLeg().get(0).getEndLocation().getLatLag())));
//
//            PolylineOptions polylineOptions = new PolylineOptions().
//                    geodesic(true).
//                    color(ContextCompat.getColor(getContext(), R.color.color_polyline)).
//                    width(20).clickable(true);
//
//            for (LatLng latLng : route.getPoints()) {
//                polylineOptions.add(latLng);
//            }
//
//            polylinePaths.add(mGoogleMap.addPolyline(polylineOptions));
//        }
//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//        builder.include(routes.get(0).getBound().getNortheast().getLatLag());
//        builder.include(routes.get(0).getBound().getSouthwest().getLatLag());
//        LatLngBounds bounds = builder.build();
//        int width = getResources().getDisplayMetrics().widthPixels;
//        int height = getResources().getDisplayMetrics().heightPixels;
//        int padding = (int) (width * 0.15);
//        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));
//        for (int i = 0; i < polylinePaths.size(); i++) {
//            Polyline item = polylinePaths.get(i);
//            if (i == 0) {
//                item.setColor(ContextCompat.getColor(getContext(), R.color.color_polyline_chose));
//                item.setZIndex(1.0f);
//            } else {
//                item.setColor(ContextCompat.getColor(getContext(), R.color.color_polyline));
//                item.setZIndex(0.0f);
//            }
//        }
//    }
//
//    @Override
//    public void onStartFindDirection() {
//        if (currentMarker != null)
//            currentMarker.remove();
//        if (originMarkers != null) {
//            for (Marker marker : originMarkers) {
//                marker.remove();
//            }
//        }
//
//        if (destinationMarkers != null) {
//            for (Marker marker : destinationMarkers) {
//                marker.remove();
//            }
//        }
//
//        if (polylinePaths != null) {
//            for (Polyline polyline : polylinePaths) {
//                polyline.remove();
//            }
//        }
//    }
//
//    @Override
//    public void showDialogConfirmNewRadius() {
//        showConfirmDialog("Không tìm thấy thông tin.", "Bạn có muốn mở rộng tìm kiếm?", new ConfirmDialog.IClickConfirmListener() {
//            @Override
//            public void onClickOk() {
//                onCameraIdle();
//                mPresenter.setBoundRadiusLoad(mPresenter.getBoundRadiusLoad() + 100);
//                mPresenter.getInfoPlace(mGoogleMap.getCameraPosition().target);
//            }
//
//            @Override
//            public void onClickCancel() {
//                mPresenter.setContinousShowDialog(false);
//            }
//        });
//    }
//
//    @Override
//    public void showPlaces() {
//        if (placeMarker != null) {
//            for (Marker marker : placeMarker) {
//                marker.remove();
//            }
//        }
//        for (Locations item : mPresenter.getListPlace()) {
////            IconGenerator iconGenerator = new IconGenerator(getContext());
//            BitmapDescriptor bitmapDescriptor;
//            if (item.getRating() < 3) {
//                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_green);
//            } else if (item.getRating() >= 3 && item.getRating() <= 4.5) {
//                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_yellow);
//            } else {
//                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_red);
//            }
//            placeMarker.add(mGoogleMap.addMarker(new MarkerOptions()
//                    .icon(bitmapDescriptor)
//                    .position(new LatLng(item.getLatitude(), item.getLongitude()))));
//        }
//    }
//
//    @Override
//    public void onClickItem(String placeId, String placeDetail) {
//        editSearch.setText(placeDetail);
//        rcvSearch.setVisibility(View.GONE);
//        mPresenter.getDetailPlace(placeId);
//    }
//
//    @Override
//    public void onMenuItemClick(FloatingActionButton miniFab, @Nullable TextView label, int itemId) {
//        switch (itemId) {
//            case R.id.item_tinhtrang:
//                break;
//            case R.id.item_mucdo:
//                break;
//        }
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        mCallbackManager.onActivityResult(requestCode, resultCode, data);
//    }
}
