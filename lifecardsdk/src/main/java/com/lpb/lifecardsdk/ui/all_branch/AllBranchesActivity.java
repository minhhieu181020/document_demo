package com.lpb.lifecardsdk.ui.all_branch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.request.default_.BranchRequest;
import com.lpb.lifecardsdk.data.model.response.default_.BranchResponse;
import com.lpb.lifecardsdk.data.model.restheader.Location;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.ui.base.BaseDataActivity;
import com.lpb.lifecardsdk.util.LocationUtil;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.ScreenUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import java.util.List;

/**
 * Created by vannh.lvt on 07/07/2020
 */
public class AllBranchesActivity extends BaseDataActivity<AllBranchPresenter> implements AllBranchContract.View, OnMapReadyCallback {
    private ImageView imgBack;
    private GoogleMap mMap;
    private FrameLayout containerInfo;
    private PopupWindow popupWindow;
    private RelativeLayout rlToolbar;

    private String providerId;
    private boolean isFirstOpenLocation, isFirstGrantLocation;
    private int pageIndex = 0, rowCount = 1000;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_activity_all_branches;
    }

    @Override
    protected void initView() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        imgBack = findViewById(R.id.imgBack);
        TextView tvTitleToolbar = findViewById(R.id.tvTitleToolbar);
        containerInfo = findViewById(R.id.container_info);
        tvTitleToolbar.setText(getString(R.string.lifecardsdk_provider_branch));
        rlToolbar = findViewById(R.id.rlToolbar);
    }

    @Override
    protected void initData() {
        mPresenter = new AllBranchPresenter(this, this);
        providerId = getIntent().getStringExtra(Constants.BundleConstants.PRODUCT_ID);
        if (PresenterUtils.isNetworkConnected(this)) {

            if (PresenterUtils.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, this)) {
                if (!PresenterUtils.isLocationEnabled(this)) {
                    isFirstOpenLocation = true;
                    ScreenUtils.displayLocationSettingsRequest(this, this);
                } else {
                    showLoading(true);
                    LocationUtil.requestSingleUpdate(this,
                            new LocationUtil.LocationCallback() {
                                @Override
                                public void onLocationChanged(LocationUtil.GPSCoordinates loc) {
                                    double lat = loc.latitude;
                                    double lng = loc.longitude;
                                    Config.Header.setLocation(new Location(String.valueOf(lat), String.valueOf(lng)));
                                    BranchRequest branchRequest = new BranchRequest("", "", providerId, pageIndex, rowCount, true);
                                    String body = StringUtils.convertObjectToBase64(branchRequest);
                                    mPresenter.getAllBranches(body);
                                }
                            });
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Config.PERMISSION_REQUEST_LOCATION);
                isFirstGrantLocation = true;
            }


        } else {
            hideLoading();
            showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
        }
    }

    @Override
    protected void initAction() {
        imgBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        LatLng vietnam = new LatLng(16.4534699, 106.5420937);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(vietnam, 5.5f));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                BranchResponse.List branch = (BranchResponse.List) marker.getTag();
                if (popupWindow != null) {
                    popupWindow.dismiss();
                    AllBranchesActivity.this.showPopupWindow(branch);
                } else
                    AllBranchesActivity.this.showPopupWindow(branch);
                return false;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng_) {
                if (popupWindow != null && popupWindow.isShowing())
                    popupWindow.dismiss();
            }
        });
    }

    @Override
    public void setAllBranches2Map(List<BranchResponse.List> items) {
        if (items != null) {
            if (items.size() > 0) {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (BranchResponse.List list : items) {
                    if (list.getLocation() != null && list.getLocation().getLatitude() != null && list.getLocation().getLongitude() != null) {
                        LatLng latLng = new LatLng(Double.parseDouble(list.getLocation().getLatitude()), Double.parseDouble(list.getLocation().getLongitude()));
                        builder.include(latLng);
                        Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
                        marker.setTag(list);
                    }
                }

                LatLngBounds bounds = builder.build();

                int width = getResources().getDisplayMetrics().widthPixels;
                int height = getResources().getDisplayMetrics().heightPixels;

                int paddingWidth = (int) (width * 0.05);
                int paddingHeight = (int) (height * 0.14);

                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, 0);
                mMap.moveCamera(cu);
                mMap.setPadding(paddingWidth, paddingHeight, paddingWidth, paddingHeight);
                mMap.animateCamera(cu);
            }
        }
    }


    private void showPopupWindow(final BranchResponse.List branch) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            View popupView = inflater.inflate(R.layout.lifecardsdk_popup_window_marker_info, null);

            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            popupWindow = new PopupWindow(popupView, width, height, false);

            TextView tvName = popupView.findViewById(R.id.tvName);
            TextView tvKm = popupView.findViewById(R.id.tvKm);
            TextView tvAddress = popupView.findViewById(R.id.tvAddress);
            TextView tvOpen = popupView.findViewById(R.id.tvOpen);
            TextView tvClose = popupView.findViewById(R.id.tvClose);
            TextView tvTime = popupView.findViewById(R.id.tvTime);
            TextView tvPhoneNumber = popupView.findViewById(R.id.tvPhoneNumber);
            ImageView imgMap = popupView.findViewById(R.id.imgMap);

            tvName.setText(branch.getName());
            tvAddress.setText(branch.getFullAddress());
            if (StringUtils.isEmpty(branch.getPhone())) {
                tvPhoneNumber.setText("");
                tvPhoneNumber.setVisibility(View.GONE);
            } else {
                tvPhoneNumber.setText(StringUtils.underlineText(branch.getPhone()));
                tvPhoneNumber.setVisibility(View.VISIBLE);
            }

            if (StringUtils.isEmpty(branch.getDistanceFormat())) {
                tvKm.setText("");
            } else {
                tvKm.setText(branch.getDistanceFormat());
            }
            if (branch.getOpenStatus().equalsIgnoreCase("1")) {
                tvClose.setVisibility(View.GONE);
                tvOpen.setVisibility(View.VISIBLE);
            } else if (branch.getOpenStatus().equalsIgnoreCase("0")) {
                tvClose.setVisibility(View.VISIBLE);
                tvOpen.setVisibility(View.GONE);
            } else {
                tvClose.setVisibility(View.GONE);
                tvOpen.setVisibility(View.GONE);
            }
            tvTime.setText(StringUtils.convertHTMLToString(branch.getBusinessHourDisplay(), this));
            imgMap.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    if (branch.getLocation() != null) {
                        if (!StringUtils.isEmpty(branch.getLocation().getLatitude()) && !StringUtils.isEmpty(branch.getLocation().getLongitude())) {
                            PresenterUtils.openGoogleMap(branch.getLocation().getLatitude() + "," + branch.getLocation().getLongitude(), AllBranchesActivity.this);
                        } else if (!StringUtils.isEmpty(branch.getFullAddress())) {
                            PresenterUtils.openGoogleMap(branch.getFullAddress(), AllBranchesActivity.this);
                        } else {
                            Exception.handleMessageRequestFailure(AllBranchesActivity.this, getString(R.string.lifecardsdk_common_cant_open_google_map), "");
                        }
                    } else if (!StringUtils.isEmpty(branch.getFullAddress())) {
                        PresenterUtils.openGoogleMap(branch.getFullAddress(), AllBranchesActivity.this);
                    } else {
                        Exception.handleMessageRequestFailure(AllBranchesActivity.this, getString(R.string.lifecardsdk_common_cant_open_google_map), "");
                    }
                }
            });
            tvPhoneNumber.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    PresenterUtils.callPhoneNumber(AllBranchesActivity.this, branch.getPhone());
                }
            });

            popupWindow.showAtLocation(containerInfo, Gravity.CENTER, 0, 200 + rlToolbar.getHeight());


        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isFirstGrantLocation && PresenterUtils.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, this)) {
            isFirstGrantLocation = false;
            if (!PresenterUtils.isLocationEnabled(this)) {
                isFirstOpenLocation = true;
                ScreenUtils.displayLocationSettingsRequest(this, this);
            } else {
                showLoading(true);
                LocationUtil.requestSingleUpdate(this,
                        new LocationUtil.LocationCallback() {
                            @Override
                            public void onLocationChanged(LocationUtil.GPSCoordinates loc) {
                                double lat = loc.latitude;
                                double lng = loc.longitude;
                                Config.Header.setLocation(new Location(String.valueOf(lat), String.valueOf(lng)));
                                BranchRequest branchRequest = new BranchRequest("", "", providerId, pageIndex, rowCount, true);
                                String body = StringUtils.convertObjectToBase64(branchRequest);
                                mPresenter.getAllBranches(body);
                            }
                        });
            }
        } else if (isFirstOpenLocation && PresenterUtils.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, this)) {
            isFirstOpenLocation = false;
            if (!PresenterUtils.isLocationEnabled(this)) {
                showLoading(true);
                BranchRequest branchRequest = new BranchRequest("", "", providerId, pageIndex, rowCount, true);
                String body = StringUtils.convertObjectToBase64(branchRequest);
                mPresenter.getAllBranches(body);
            } else {
                showLoading(true);
                LocationUtil.requestSingleUpdate(this,
                        new LocationUtil.LocationCallback() {
                            @Override
                            public void onLocationChanged(LocationUtil.GPSCoordinates loc) {
                                double lat = loc.latitude;
                                double lng = loc.longitude;
                                Config.Header.setLocation(new Location(String.valueOf(lat), String.valueOf(lng)));
                                BranchRequest branchRequest = new BranchRequest("", "", providerId, pageIndex, rowCount, true);
                                String body = StringUtils.convertObjectToBase64(branchRequest);
                                mPresenter.getAllBranches(body);
                            }
                        });
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Config.PERMISSION_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                showLoading(true);
                BranchRequest branchRequest = new BranchRequest("", "", providerId, pageIndex, rowCount, true);
                String body = StringUtils.convertObjectToBase64(branchRequest);
                mPresenter.getAllBranches(body);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (popupWindow != null && popupWindow.isShowing())
            popupWindow.dismiss();
    }
}
