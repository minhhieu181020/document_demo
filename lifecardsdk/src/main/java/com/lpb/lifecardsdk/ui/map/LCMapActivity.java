package com.lpb.lifecardsdk.ui.map;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.ui.base.BaseDataActivity;
import com.lpb.lifecardsdk.util.LocationUtil;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.ScreenUtils;

public class LCMapActivity extends BaseDataActivity<MapPresenter> implements MapContract.View, OnMapReadyCallback, LocationListener {
    private GoogleMap mMap;
    private RelativeLayout rlToolbar;
    private ImageView imgBack;
    private ImageView imgStartLocation;
    private TextView tvStartLocation;
    private ImageView imgEndLocation;
    private RelativeLayout rlCantFindLocation;
    private RelativeLayout rlContent;
    private TextView tvEndLocation;
    private ImageView imgToggle;
    private LinearLayout llSelectLocation;
    private Button btnSettings;
    private TextView tvTitleToolbar;
    private FloatingActionButton fabLocation;
    private RelativeLayout rlTurnOnLocation;
    private Button btnTurnOnLocation;


    private Bitmap bgMarkerIcon;
    private Bitmap fgMarkerIcon;

    // diem bat dau ket thuc k thay doi
    private String startLocation, endLocation;
    // diem bat dau ket thuc point co thay doi
    private String startAddress, endAddress, point;
    // trang thai chi duong , trang thai goi API,
    private boolean directionState, isCallAPIMap, isGrantFail, isFirstInit, isFirstTurnOnLocation;
    // diem bat dau ket thuc vi tri hien tai co thay doi

    private LatLng startLatLng, endLatLng, realTimeLatLng;
    private LocationManager mLocationManager;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_activity_map;
    }

    @Override
    protected void initView() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        rlToolbar = findViewById(R.id.rlToolbar);
        imgBack = findViewById(R.id.imgBack);
        imgStartLocation = findViewById(R.id.imgStartLocation);
        tvStartLocation = findViewById(R.id.tvStartLocation);
        imgEndLocation = findViewById(R.id.imgEndLocation);
        tvEndLocation = findViewById(R.id.tvEndLocation);
        imgToggle = findViewById(R.id.imgToggle);
        llSelectLocation = findViewById(R.id.llSelectLocation);
        fabLocation = findViewById(R.id.fabLocation);
        rlCantFindLocation = findViewById(R.id.rlCantFindLocation);
        rlContent = findViewById(R.id.rlContent);
        btnSettings = findViewById(R.id.btnSettings);
        tvTitleToolbar = findViewById(R.id.tvTitleToolbar);
        rlTurnOnLocation = findViewById(R.id.rlTurnOnLocation);
        btnTurnOnLocation = findViewById(R.id.btnTurnOnLocation);


        bgMarkerIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.lifecardsdk_bg_marker);
        fgMarkerIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.lifecardsdk_circular);

        tvTitleToolbar.setText(getString(R.string.lifecardsdk_map_direction));
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PresenterUtils.isNetworkConnected(this)) {
            if (PresenterUtils.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, getApplicationContext())) {
                rlCantFindLocation.setVisibility(View.GONE);
                if (!PresenterUtils.isLocationEnabled(this)) {
                    if (!isFirstTurnOnLocation) {
                        ScreenUtils.displayLocationSettingsRequest(this, this);
                        isFirstTurnOnLocation = true;
                    } else {
                        showScreenTurnOnLocation();
                    }
                } else {
                    if (!isFirstInit) {
                        isFirstInit = true;
                        showLoading(false);
                        LocationUtil.requestSingleUpdate(this,
                                new LocationUtil.LocationCallback() {
                                    @Override
                                    public void onLocationChanged(LocationUtil.GPSCoordinates loc) {
                                        double lat = loc.latitude;
                                        double lng = loc.longitude;
                                        startLocation = lat + "," + lng;
                                        mPresenter.getMapDirection(startLocation, endLocation, getString(R.string.lifecardsdk_google_maps_key));
                                    }
                                });
                        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10,
                                1, this);
                    }
                    fetchLocationData();
                }
            } else {
                if (!isGrantFail)
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Config.PERMISSION_REQUEST_LOCATION);
            }
        } else {
            showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.REQUEST_TURN_ON_LOCATION) {
            if (resultCode != Activity.RESULT_OK) {
                showScreenTurnOnLocation();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Config.PERMISSION_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (PresenterUtils.isLocationEnabled(this)) {
                    fetchLocationData();
                }

            } else {
                isGrantFail = true;
                showScreenSettingLocation();
            }
        }
    }

    private void fetchLocationData() {
        rlCantFindLocation.setVisibility(View.GONE);
        rlContent.setVisibility(View.VISIBLE);
        rlTurnOnLocation.setVisibility(View.GONE);
    }

    private void showScreenSettingLocation() {
        rlCantFindLocation.setVisibility(View.VISIBLE);
        rlContent.setVisibility(View.GONE);
        rlTurnOnLocation.setVisibility(View.GONE);
    }

    private void showScreenTurnOnLocation() {
        rlCantFindLocation.setVisibility(View.GONE);
        rlContent.setVisibility(View.GONE);
        rlTurnOnLocation.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        endLocation = getIntent().getStringExtra(Constants.BundleConstants.END_ADDRESS);
        mPresenter = new MapPresenter(this, this);
    }

    @Override
    protected void initAction() {
        tvEndLocation.setText(endLocation);
        llSelectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fabLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PresenterUtils.isLocationEnabled(LCMapActivity.this)) {
                    ScreenUtils.displayLocationSettingsRequest(LCMapActivity.this, LCMapActivity.this);
                    isFirstTurnOnLocation = true;
                } else {
                    LocationUtil.requestSingleUpdate(LCMapActivity.this,
                            new LocationUtil.LocationCallback() {
                                @Override
                                public void onLocationChanged(LocationUtil.GPSCoordinates loc) {
                                    double lat = loc.latitude;
                                    double lng = loc.longitude;
                                    mMap.clear();
                                    mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("").icon(BitmapDescriptorFactory.fromBitmap(
                                            makeMarkerIcon(bgMarkerIcon,
                                                    fgMarkerIcon,
                                                    "Now", getResources().getColor(R.color.lifecardsdk_blue_dark), 12
                                            ))));
                                    if (isCallAPIMap)
                                        setMapDirection(startLatLng, endLatLng, startAddress, endAddress, point, false);
                                }
                            });
                }
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenUtils.openPermissionSettings(LCMapActivity.this);
            }
        });
        btnTurnOnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenUtils.openTurnOnLocation(LCMapActivity.this);
            }
        });
        imgToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (directionState) {
                    tvStartLocation.setText(getString(R.string.lifecardsdk_map_my_location));
                    tvEndLocation.setText(endLocation);
                    directionState = false;
                    isCallAPIMap = false;
                    showLoading(false);
                    mMap.clear();
                    mPresenter.getMapDirection(startLocation, endLocation, getString(R.string.lifecardsdk_google_maps_key));
                } else {
                    tvStartLocation.setText(endLocation);
                    tvEndLocation.setText(getString(R.string.lifecardsdk_map_my_location));
                    directionState = true;
                    isCallAPIMap = false;
                    showLoading(false);
                    mMap.clear();
                    mPresenter.getMapDirection(endLocation, startLocation, getString(R.string.lifecardsdk_google_maps_key));
                }

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        LatLng vietnam = new LatLng(21.027763, 105.834160);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(vietnam));
    }

    @Override
    public void setMapDirection(LatLng startLatLng, LatLng endLatLng, String startAddress, String endAddress, String point, boolean isAnimate) {

        isCallAPIMap = true;
        this.startLatLng = startLatLng;
        this.endLatLng = endLatLng;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.point = point;

        mMap.addMarker(new MarkerOptions().position(startLatLng).title(startAddress).icon(BitmapDescriptorFactory.fromBitmap(
                makeMarkerIcon(bgMarkerIcon,
                        fgMarkerIcon,
                        "A", getResources().getColor(R.color.lifecardsdk_orange), 16
                ))));
        mMap.addMarker(new MarkerOptions().position(endLatLng).title(endAddress).icon(BitmapDescriptorFactory.fromBitmap(
                makeMarkerIcon(bgMarkerIcon,
                        fgMarkerIcon,
                        "B", getResources().getColor(R.color.lifecardsdk_orange), 16
                ))));
        mMap.addPolyline(new PolylineOptions()
                .addAll(mPresenter.decodePoly(point))
                .width(10)
                .color(getResources().getColor(R.color.lifecardsdk_colorAccent)));

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(startLatLng);
        builder.include(endLatLng);
        LatLngBounds bounds = builder.build();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        int paddingWidth = (int) (width * 0.05);
        int paddingHeight = (int) (height * 0.14);

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, 0);
        if (isAnimate)
            mMap.moveCamera(cu);
        mMap.setPadding(paddingWidth, paddingHeight + llSelectLocation.getHeight(), paddingWidth, paddingHeight);
        if (isAnimate)
            mMap.animateCamera(cu);

    }

    private Bitmap makeMarkerIcon(Bitmap bgMarker, Bitmap fgMarker, String text, int color, int textSize) {
        Bitmap.Config config = Bitmap.Config.ARGB_8888;

        int bgSize = ScreenUtils.dpToPx(40);
        int fgSize = ScreenUtils.dpToPx(30);
        float left = (bgSize - fgSize) / 2;

        Bitmap result = Bitmap.createBitmap(bgSize, bgSize, config);

        Bitmap background = Bitmap.createScaledBitmap(bgMarker, bgSize, bgSize, false);
        Bitmap foreground = Bitmap.createScaledBitmap(fgMarker, fgSize, fgSize, false);

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(background, 0f, 0f, null);
        canvas.drawBitmap(foreground, left, left / 4, paint);

        Paint textPaint = new Paint();
        textPaint.setTextSize(ScreenUtils.dpToPx(textSize));
        textPaint.setColor(Color.WHITE);
        drawCenter(canvas, textPaint, text);

        return result;
    }

    private void drawCenter(Canvas canvas, Paint paint, String text) {
        Rect r = new Rect();
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(text, 0, text.length(), r);

        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y - ScreenUtils.dpToPx(4), paint);
    }

    @Override
    public void onLocationChanged(Location location) {
        realTimeLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(realTimeLatLng).title("").icon(BitmapDescriptorFactory.fromBitmap(
                makeMarkerIcon(bgMarkerIcon,
                        fgMarkerIcon,
                        "Now", getResources().getColor(R.color.lifecardsdk_blue_dark), 12
                ))));
        if (isCallAPIMap)
            setMapDirection(startLatLng, endLatLng, startAddress, endAddress, point, false);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
