package com.lpb.lifecardsdk.ui.provider.fragment.branch;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.BranchResponse;
import com.lpb.lifecardsdk.data.model.restheader.Location;
import com.lpb.lifecardsdk.ui.all_branch.AllBranchesActivity;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.bottomsheet.AreaBottomFragment;
import com.lpb.lifecardsdk.ui.provider.fragment.branch.adapter.BranchAdapter;
import com.lpb.lifecardsdk.util.LocationUtil;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.ScreenUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.recyclerview.EndlessRecyclerViewScrollListener;

import java.util.List;

public class BranchFragment extends BaseDataFragment<BranchPresenter> implements BranchContract.View, AreaBottomFragment.BottomSheetListener {
    private RelativeLayout rlCity;
    private TextView tvCity;
    private RelativeLayout rlDistrict;
    private TextView tvDistrict;
    private RelativeLayout rlVillage;
    private TextView tvVillage, tvSeeMap;
    private RecyclerView rvContent;
    private ProgressBar pbBranch;
    private LinearLayout llCantFindBranch, llContent;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout refreshLayout;
    private BranchAdapter branchAdapter;
    private boolean isUserVisibleHint = true;

    private String providerID, areaCode = "", areaType = "";
    private String cityCode = "", districtCode = "", villageCode = "";
    private String cityName, districtName;
    private int pageIndex = 0;
    private int rowCount = 5;
    private boolean isFirstOpenLocation, isFirstGrantLocation;

    private static BranchFragment instance = null;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && this.isUserVisibleHint) {
            if (PresenterUtils.isNetworkConnected(mActivity)) {
                if (providerID.equals(String.valueOf((Config.DEAULT_VALUE_INT)))) {
                    showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                    return;
                }
                if (PresenterUtils.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, mActivity)) {
                    if (!PresenterUtils.isLocationEnabled(mActivity)) {
                        isFirstOpenLocation = true;
                        ScreenUtils.displayLocationSettingsRequest(mActivity.getApplicationContext(), mActivity);
                    } else {
                        pbBranch.setVisibility(View.VISIBLE);
                        LocationUtil.requestSingleUpdate(mActivity,
                                new LocationUtil.LocationCallback() {
                                    @Override
                                    public void onLocationChanged(LocationUtil.GPSCoordinates loc) {
                                        double lat = loc.latitude;
                                        double lng = loc.longitude;
                                        Config.Header.setLocation(new Location(String.valueOf(lat), String.valueOf(lng)));
                                        mPresenter.getDataBranch("", "", providerID, pageIndex, rowCount);
                                        Log.e("BranchFragment: ", "Khi bat dau vao co quyen va bat vi tri");
                                    }
                                });
                    }
                } else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Config.PERMISSION_REQUEST_LOCATION);
                    isFirstGrantLocation = true;
                }
                isUserVisibleHint = false;
            } else {
                showError(mActivity.getString(R.string.lifecardsdk_cant_connect_internet), "");
            }
        }
    }


    @Override
    protected int getLayoutId() {
        instance = this;
        return R.layout.lifecardsdk_fragment_provider_branch;
    }

    public static BranchFragment getInstance() {
        return instance;
    }

    @Override
    protected void initView() {
        rlCity = view.findViewById(R.id.rlCity);
        tvCity = view.findViewById(R.id.tvCity);
        rlDistrict = view.findViewById(R.id.rlDistrict);
        tvDistrict = view.findViewById(R.id.tvDistrict);
        rlVillage = view.findViewById(R.id.rlVillage);
        tvVillage = view.findViewById(R.id.tvVillage);
        llContent = view.findViewById(R.id.llContent);
        tvSeeMap = view.findViewById(R.id.tvSeeMap);
        pbBranch = view.findViewById(R.id.pbBranch);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeResources(R.color.lifecardsdk_orange);
        rvContent = view.findViewById(R.id.rvContent);
        llCantFindBranch = view.findViewById(R.id.llCantFindBranch);

        branchAdapter = new BranchAdapter(mActivity);
        rvContent.setAdapter(branchAdapter);
        linearLayoutManager = new LinearLayoutManager(mActivity);
        rvContent.setLayoutManager(linearLayoutManager);

        tvSeeMap.setText(StringUtils.underlineText(getString(R.string.lifecardsdk_provider_branch_see_map)));
    }

    @Override
    protected void initData() {
        mPresenter = new BranchPresenter(mActivity, this);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        providerID = String.valueOf(bundle.getInt(Constants.BundleConstants.PROVIDER_ID, Config.DEAULT_VALUE_INT));
    }

    @Override
    public void setRefreshing(boolean b) {
        refreshLayout.setRefreshing(b);
    }

    @Override
    public void hideProgressBar() {
        pbBranch.setVisibility(View.GONE);
    }

    @Override
    protected void initAction() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (PresenterUtils.isNetworkConnected(mActivity)) {
                    branchAdapter.setOnLoadMore(true);
                    branchAdapter.clearData();
                    mPresenter.getDataBranch(areaCode, areaType, providerID, pageIndex, rowCount);
                } else {
                    setRefreshing(false);
                    showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
                }
            }
        });
        branchAdapter.setOnClickListener(new BranchAdapter.OnClickListener() {
            @Override
            public void onClickItem(BranchResponse.List item) {
                if (item != null) {
                    if (item.getLocation() != null) {
                        if (!StringUtils.isEmpty(item.getLocation().getLatitude()) && !StringUtils.isEmpty(item.getLocation().getLongitude())) {
                            PresenterUtils.openGoogleMap(item.getLocation().getLatitude() + "," + item.getLocation().getLongitude(), getActivity());
                        } else if (!StringUtils.isEmpty(item.getFullAddress())) {
                            PresenterUtils.openGoogleMap(item.getFullAddress(), getActivity());
                        } else {
                            showError(getString(R.string.lifecardsdk_common_cant_open_google_map), "");
                        }
                    } else if (!StringUtils.isEmpty(item.getFullAddress())) {
                        PresenterUtils.openGoogleMap(item.getFullAddress(), getActivity());
                    } else {
                        showError(getString(R.string.lifecardsdk_common_cant_open_google_map), "");
                    }
                } else {
                    showError(getString(R.string.lifecardsdk_common_cant_open_google_map), "");
                }
            }

            @Override
            public void onClickPhone(String phoneNumber) {
                PresenterUtils.callPhoneNumber(getActivity(), phoneNumber);
            }
        });
        rvContent.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPresenter.getDataBranch(areaCode, areaType, providerID, page, rowCount);
            }
        });
        rlCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheet(Constants.AreaType.CITY, "", cityCode, "");
            }
        });

        rlDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cityCode.equals(""))
                    openBottomSheet(Constants.AreaType.DISTRICT, cityCode, districtCode, cityName);
            }
        });

        rlVillage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cityCode.equals("") && !districtCode.equals(""))
                    openBottomSheet(Constants.AreaType.VILLAGE, districtCode, villageCode, districtName);
            }
        });


        tvSeeMap.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(mActivity, AllBranchesActivity.class);
                intent.putExtra(Constants.BundleConstants.PRODUCT_ID, providerID);
                startActivity(new Intent(intent));
            }
        });

    }

    // String areaType, String parentCode,
    //String areaCode xử lý trùng khi click
    //String parentName lấy tên cha khi click trùng
    private void openBottomSheet(String areaType, String parentCode, String areaCode, String parentName) {
        AreaBottomFragment areaBottomFragment = new AreaBottomFragment();
        Bundle b = new Bundle();
        areaBottomFragment.setListenner(this);
        b.putString("areaType", areaType);
        b.putString("parentCode", parentCode);
        b.putString("areaCode", areaCode);
        b.putString("parentName", parentName);
        areaBottomFragment.setArguments(b);
        areaBottomFragment.show(getChildFragmentManager(), areaBottomFragment.getTag());
    }

    public void enableSwipeRefreshLayout(boolean enable) {
        refreshLayout.setEnabled(enable);
    }

    @Override
    public void setDataBranch(List<BranchResponse.List> items) {
        if (items.size() == 0) {
            rvContent.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                }
            });

            branchAdapter.setOnLoadMore(false);
            llCantFindBranch.setVisibility(View.VISIBLE);
            llContent.setVisibility(View.GONE);
        } else {
            llCantFindBranch.setVisibility(View.GONE);
//            llContent.setVisibility(View.VISIBLE);
            llContent.setVisibility(View.GONE);
        }
        branchAdapter.setItems(items);
    }

    @Override
    public void addDataBranch(List<BranchResponse.List> items) {
        if (items.size() == 0) {
            rvContent.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                }
            });

            branchAdapter.setOnLoadMore(false);

        }
        branchAdapter.addItems(items);
    }

    @Override
    public void callBack(String areaCode, String areaName, String areaType) {
        if (PresenterUtils.isNetworkConnected(mActivity)) {
            if (areaType.equalsIgnoreCase(Constants.AreaType.CITY)) {
                tvCity.setText(areaName);
                pbBranch.setVisibility(View.VISIBLE);
                this.areaCode = areaCode;
                this.areaType = areaType;

                this.cityCode = areaCode;
                this.cityName = areaName;

                branchAdapter.setOnLoadMore(true);
                branchAdapter.clearData();
                mPresenter.getDataBranch(this.areaCode, this.areaType, providerID, pageIndex, rowCount);
                Log.e("BranchFragment: ", "City");
                tvDistrict.setText(getString(R.string.lifecardsdk_provider_district));
                tvVillage.setText(getString(R.string.lifecardsdk_provider_village));

                villageCode = "";
                districtCode = "";
                this.districtName = "";
            } else if (areaType.equalsIgnoreCase(Constants.AreaType.DISTRICT)) {

                tvDistrict.setText(areaName);
                pbBranch.setVisibility(View.VISIBLE);
                this.areaCode = areaCode;
                this.areaType = areaType;

                this.districtCode = areaCode;
                this.districtName = areaName;

                branchAdapter.setOnLoadMore(true);
                branchAdapter.clearData();
                mPresenter.getDataBranch(this.areaCode, this.areaType, providerID, pageIndex, rowCount);
                Log.e("BranchFragment: ", "District");
                tvVillage.setText(getString(R.string.lifecardsdk_provider_village));
                villageCode = "";
            } else if (areaType.equalsIgnoreCase(Constants.AreaType.VILLAGE)) {
                tvVillage.setText(areaName);
                pbBranch.setVisibility(View.VISIBLE);
                this.areaCode = areaCode;
                this.areaType = areaType;

                this.villageCode = areaCode;

                branchAdapter.setOnLoadMore(true);
                branchAdapter.clearData();
                mPresenter.getDataBranch(this.areaCode, this.areaType, providerID, pageIndex, rowCount);
                Log.e("BranchFragment: ", "Village");
            } else {
                pbBranch.setVisibility(View.VISIBLE);
                this.areaCode = areaCode;
                this.areaType = areaType;

                this.cityCode = areaCode;

                branchAdapter.setOnLoadMore(true);
                branchAdapter.clearData();
                mPresenter.getDataBranch(this.areaCode, this.areaType, providerID, pageIndex, rowCount);

                tvDistrict.setText(getString(R.string.lifecardsdk_provider_district));
                tvVillage.setText(getString(R.string.lifecardsdk_provider_village));
                tvCity.setText(getString(R.string.lifecardsdk_provider_city));

                villageCode = "";
                districtCode = "";
            }
        } else {
            showError(mActivity.getString(R.string.lifecardsdk_cant_connect_internet), "");
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if (isFirstGrantLocation && PresenterUtils.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, mActivity)) {
            isFirstGrantLocation = false;
            if (!PresenterUtils.isLocationEnabled(mActivity)) {
                isFirstOpenLocation = true;
                ScreenUtils.displayLocationSettingsRequest(mActivity.getApplicationContext(), mActivity);
            } else {
                pbBranch.setVisibility(View.VISIBLE);
                LocationUtil.requestSingleUpdate(mActivity,
                        new LocationUtil.LocationCallback() {
                            @Override
                            public void onLocationChanged(LocationUtil.GPSCoordinates loc) {
                                double lat = loc.latitude;
                                double lng = loc.longitude;
                                Config.Header.setLocation(new Location(String.valueOf(lat), String.valueOf(lng)));
                                mPresenter.getDataBranch("", "", providerID, pageIndex, rowCount);
                                Log.e("BranchFragment: ", "Dong y cap quyen va vi tri da duoc mo san");
                            }
                        });
            }
        } else if (isFirstOpenLocation && PresenterUtils.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, mActivity)) {
            isFirstOpenLocation = false;
            if (!PresenterUtils.isLocationEnabled(mActivity)) {
                pbBranch.setVisibility(View.VISIBLE);
                mPresenter.getDataBranch("", "", providerID, pageIndex, rowCount);
                Log.e("BranchFragment: ", "Co quyen nhung tu choi bat vi tri trong dialog");
            } else {
                pbBranch.setVisibility(View.VISIBLE);
                LocationUtil.requestSingleUpdate(mActivity,
                        new LocationUtil.LocationCallback() {
                            @Override
                            public void onLocationChanged(LocationUtil.GPSCoordinates loc) {
                                double lat = loc.latitude;
                                double lng = loc.longitude;
                                Config.Header.setLocation(new Location(String.valueOf(lat), String.valueOf(lng)));
                                mPresenter.getDataBranch("", "", providerID, pageIndex, rowCount);
                                Log.e("BranchFragment: ", "Co quyen va dong y bat vi tri trong dialog");
                            }
                        });
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Config.PERMISSION_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Log.e("BranchFragment: ", "Tu choi cap quyen");
                pbBranch.setVisibility(View.VISIBLE);
                mPresenter.getDataBranch("", "", providerID, pageIndex, rowCount);
            }
        }
    }
}
