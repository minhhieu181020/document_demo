package com.lpb.lifecardsdk.ui.home.fragment.buycard.defcard;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.adapter.CategoryAdapter;
import com.lpb.lifecardsdk.common.adapter.LocationAdapter;
import com.lpb.lifecardsdk.common.adapter.ProviderAdapter;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.response.default_.AreaResponse;
import com.lpb.lifecardsdk.data.model.response.default_.CategoryResponse;
import com.lpb.lifecardsdk.data.model.response.default_.ProviderResponse;
import com.lpb.lifecardsdk.data.model.response.default_.defcard.CardListConfig;
import com.lpb.lifecardsdk.data.model.response.default_.defcard.DefCard;
import com.lpb.lifecardsdk.data.model.response.default_.defcard.ProviderDto;
import com.lpb.lifecardsdk.data.model.restheader.Location;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.MainBuyCardFragment;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.carddetails_v2.CardDetailsFragment;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.defcard.adapter.DefCardAdapter;
import com.lpb.lifecardsdk.ui.provider.ProviderFragment;
import com.lpb.lifecardsdk.util.LocationUtil;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.ScreenUtils;
import com.lpb.lifecardsdk.widget.RelativeRadioGroup;
import com.lpb.lifecardsdk.widget.recyclerview.EndlessRecyclerViewScrollListener;

import java.util.LinkedHashMap;
import java.util.List;

public class DefCardFragment extends BaseDataFragment<DefCardPresenter> implements DefCardContract.View {
    private Button btnLasted, btnSaving, btnPrice, btnNearest;
    private boolean priceStatus, locationStatus;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private RecyclerView rvCategory, rvLocation, rvProvider, rvContent, rvCategoryMain;
    private TextView tvMoreCate, tvLessCate, tvMoreLocation, tvLessLocation, tvMoreProvider, tvLessProvider;
    private Button btnReset, btnApply;
    private ImageView imgBack;
    private TextView tvTitleToolbar;
    //private ImageView imgFilter;
    // TODO: 28/04/2020
//    private EditText edMinPrice, edMaxPrice;
    private RelativeRadioGroup rgPrice;
    private NestedScrollView nsvFilter;
    //private CustomEditTextClearable edSearchQuery;
    private CoordinatorLayout clContainer;
    private RelativeLayout rlCantFindLocation;
    private Button btnSettings;
    private RelativeLayout rlTurnOnLocation;
    private Button btnTurnOnLocation;
    private SwipeRefreshLayout refreshLayout;

    private LinearLayout llOptions;
    private HorizontalScrollView llSortType;
    private LinearLayoutManager linearLayoutManager;
    private AppBarLayout.LayoutParams params;

    private CategoryAdapter categoryAdapter;
    private LocationAdapter locationAdapter;
    private ProviderAdapter providerAdapter;
    private DefCardAdapter defCardAdapter;
    private com.lpb.lifecardsdk.ui.home.fragment.buycard.defcard.adapter.CategoryAdapter categoryAdapterMain;

    private LinkedHashMap<Integer, String> listCategorySelected;
    private LinkedHashMap<Integer, String> listLocationSelected;
    private LinkedHashMap<Integer, String> listProviderSelected;
    private boolean isClicked = false, isLasted = true, isSave = false, isNearest = false;
    private Integer categoryID = 1;// tat ca
    private String sortType = Constants.SortType.MN;
    private boolean isFirstTurnOnLocation;
    private int pageSize = 20;
    private int pageIndex = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_buycard;
    }

    @Override
    protected void initView() {

        clContainer = view.findViewById(R.id.clContainer);
        btnLasted = view.findViewById(R.id.btnLasted);
        btnLasted = view.findViewById(R.id.btnLasted);
        btnPrice = view.findViewById(R.id.btnPrice);
        btnNearest = view.findViewById(R.id.btnNearest);
        btnSaving = view.findViewById(R.id.btnSaving);
        drawer = view.findViewById(R.id.drawer_layout);
        tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);
        tvTitleToolbar.setText(getString(R.string.lifecardsdk_common_buy_card));
        refreshLayout = view.findViewById(R.id.refreshLayout);
        llSortType = view.findViewById(R.id.llSortType);
        refreshLayout.setColorSchemeResources(R.color.lifecardsdk_orange);
        // Làm xong màn hình bộ lọc nhưng do có thay đổi giao diện nên tạm ẩn -> sau này phát triển thêm
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        navigationView = view.findViewById(R.id.nav_view);
        imgBack = view.findViewById(R.id.imgBack);

//        edSearchQuery = view.findViewById(R.id.edtSearch);
//        edSearchQuery.setFocusable(false);

        //imgFilter = view.findViewById(R.id.imgFilter);
        //todo
//        edMaxPrice = view.findViewById(R.id.edMaxPrice);
//        edMinPrice = view.findViewById(R.id.edMinPrice);
        rgPrice = view.findViewById(R.id.rgPrice);

        rlCantFindLocation = view.findViewById(R.id.rlCantFindLocation);
        btnSettings = view.findViewById(R.id.btnSettings);
        rlTurnOnLocation = view.findViewById(R.id.rlTurnOnLocation);
        btnTurnOnLocation = view.findViewById(R.id.btnTurnOnLocation);

        llOptions = view.findViewById(R.id.llOptions);
        params = (AppBarLayout.LayoutParams) llOptions.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);

        rvContent = view.findViewById(R.id.rvContent);
        linearLayoutManager = new LinearLayoutManager(mActivity);
//        rvContent.setNestedScrollingEnabled(false);
        defCardAdapter = new DefCardAdapter(mActivity,true);
        rvContent.setAdapter(defCardAdapter);
        rvContent.setLayoutManager(linearLayoutManager);

        rvCategoryMain = view.findViewById(R.id.rvCategoryMain);
        categoryAdapterMain = new com.lpb.lifecardsdk.ui.home.fragment.buycard.defcard.adapter.CategoryAdapter(mActivity);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false);
        rvCategoryMain.setAdapter(categoryAdapterMain);
        rvCategoryMain.setLayoutManager(layoutManager2);
        initFilter();

    }

    @Override
    protected void initData() {
        mPresenter = new DefCardPresenter(mActivity, this);
        if (PresenterUtils.isNetworkConnected(mActivity)) {
            showLoading(true);
            mPresenter.getDataCategory();
            mPresenter.getDataCard(categoryID, "", Constants.SortType.MN, LCConfig.getPhoneNumber(), pageIndex, pageSize);
        } else {
            showError(mActivity.getString(R.string.lifecardsdk_cant_connect_internet), "");
        }
    }

    @Override
    public void setRefreshing(boolean b) {
        refreshLayout.setRefreshing(b);
    }


    @Override
    public void showContent() {
        //llSortType.setVisibility(View.VISIBLE);
    }


    private void refreshData() {
        if (PresenterUtils.isNetworkConnected(mActivity)) {
            defCardAdapter.clearData();
            mPresenter.getDataCategory();
            mPresenter.getDataCard(categoryID, "", sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize);
        } else {
            setRefreshing(false);
            showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
        }
    }

    @Override
    protected void initAction() {
        initActionFilter();
        rvContent.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPresenter.getDataCard(categoryID, "", sortType, LCConfig.getPhoneNumber(), page + 1, pageSize);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        btnSettings.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                ScreenUtils.openPermissionSettings(mActivity);
            }
        });
        btnTurnOnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenUtils.openTurnOnLocation(mActivity);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.finish();
            }
        });
//        imgFilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (drawer.isDrawerOpen(GravityCompat.END)) {
//                    drawer.closeDrawer(GravityCompat.END);
//                } else {
//
//                    drawer.openDrawer(GravityCompat.END);
//                }
//            }
//        });
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                nsvFilter.scrollTo(0, 0);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                nsvFilter.scrollTo(0, 0);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        btnLasted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContentW();
                if (PresenterUtils.isNetworkConnected(mActivity)) {
                    if (isLasted) {
                        return;
                    }
                    isLasted = true;
                    isSave = false;
                    isNearest = false;
                    showLoading(false);
                    sortType = Constants.SortType.MN;
                    defCardAdapter.clearData();
                    mPresenter.getDataCard(categoryID, "", sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize);
                    btnLasted.setTextColor(getResources().getColor(R.color.lifecardsdk_white));
                    btnLasted.setBackgroundResource(R.drawable.lifecardsdk_round_button_orange);

                    btnSaving.setTextColor(getResources().getColor(R.color.lifecardsdk_gray));
                    btnSaving.setBackgroundResource(R.drawable.lifecardsdk_round_button_defaut);

                    btnNearest.setTextColor(getResources().getColor(R.color.lifecardsdk_gray));
                    btnNearest.setBackgroundResource(R.drawable.lifecardsdk_round_button_defaut);

                    btnPrice.setTextColor(getResources().getColor(R.color.lifecardsdk_gray));
                    btnPrice.setBackgroundResource(R.drawable.lifecardsdk_round_button_defaut);
                    btnPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.lifecardsdk_ic_unfold_more_big, 0);
                } else {
                    showError(mActivity.getString(R.string.lifecardsdk_cant_connect_internet), "");
                }

            }
        });
        btnNearest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PresenterUtils.isNetworkConnected(mActivity)) {
                    if (isNearest) {
                        return;
                    }
                    isLasted = false;
                    isSave = false;
                    isNearest = true;

                    btnNearest.setTextColor(getResources().getColor(R.color.lifecardsdk_white));
                    btnNearest.setBackgroundResource(R.drawable.lifecardsdk_round_button_orange);

                    btnSaving.setTextColor(getResources().getColor(R.color.lifecardsdk_gray));
                    btnSaving.setBackgroundResource(R.drawable.lifecardsdk_round_button_defaut);

                    btnLasted.setTextColor(getResources().getColor(R.color.lifecardsdk_gray));
                    btnLasted.setBackgroundResource(R.drawable.lifecardsdk_round_button_defaut);

                    btnPrice.setTextColor(getResources().getColor(R.color.lifecardsdk_gray));
                    btnPrice.setBackgroundResource(R.drawable.lifecardsdk_round_button_defaut);
                    btnPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.lifecardsdk_ic_unfold_more_big, 0);
                    if (Config.Header.getHeader().getLocation() == null) {
                        if (PresenterUtils.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, mActivity)) {
                            rlCantFindLocation.setVisibility(View.GONE);
                            if (!PresenterUtils.isLocationEnabled(mActivity)) {
                                isFirstTurnOnLocation = true;
                                ScreenUtils.displayLocationSettingsRequest(mActivity.getApplicationContext(), mActivity);
                            } else {
                                showLoading(false);
                                sortType = Constants.SortType.GN;
                                showContentW();
                                setTimeoutGetLocation();
                                LocationUtil.requestSingleUpdate(mActivity,
                                        new LocationUtil.LocationCallback() {
                                            @Override
                                            public void onLocationChanged(LocationUtil.GPSCoordinates loc) {
                                                double lat = loc.latitude;
                                                double lng = loc.longitude;
                                                Config.Header.setLocation(new Location(String.valueOf(lat), String.valueOf(lng)));
                                                clearLocation();
                                                defCardAdapter.clearData();
                                                mPresenter.getDataCard(categoryID, "", sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize);
                                            }
                                        });
                            }
                        } else {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Config.PERMISSION_REQUEST_LOCATION);
                        }
                    } else {
                        showLoading(false);
                        sortType = Constants.SortType.GN;
                        defCardAdapter.clearData();
                        mPresenter.getDataCard(categoryID, "", sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize);
                        showContentW();
                    }
                } else {
                    showError(mActivity.getString(R.string.lifecardsdk_cant_connect_internet), "");
                }

            }
        });
        btnSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContentW();
                if (PresenterUtils.isNetworkConnected(mActivity)) {
                    if (isSave) {
                        return;
                    }
                    isLasted = false;
                    isSave = true;
                    isNearest = false;
                    showLoading(false);
                    sortType = Constants.SortType.TKN;
                    defCardAdapter.clearData();
                    mPresenter.getDataCard(categoryID, "", sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize);
                    btnSaving.setTextColor(getResources().getColor(R.color.lifecardsdk_white));
                    btnSaving.setBackgroundResource(R.drawable.lifecardsdk_round_button_orange);

                    btnLasted.setTextColor(getResources().getColor(R.color.lifecardsdk_gray));
                    btnLasted.setBackgroundResource(R.drawable.lifecardsdk_round_button_defaut);

                    btnNearest.setTextColor(getResources().getColor(R.color.lifecardsdk_gray));
                    btnNearest.setBackgroundResource(R.drawable.lifecardsdk_round_button_defaut);

                    btnPrice.setTextColor(getResources().getColor(R.color.lifecardsdk_gray));
                    btnPrice.setBackgroundResource(R.drawable.lifecardsdk_round_button_defaut);
                    btnPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.lifecardsdk_ic_unfold_more_big, 0);
                } else {
                    showError(mActivity.getString(R.string.lifecardsdk_cant_connect_internet), "");
                }

            }
        });
        btnPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContentW();
                if (PresenterUtils.isNetworkConnected(mActivity)) {
                    isLasted = false;
                    isSave = false;
                    isNearest = false;
                    btnLasted.setTextColor(getResources().getColor(R.color.lifecardsdk_gray));
                    btnLasted.setBackgroundResource(R.drawable.lifecardsdk_round_button_defaut);

                    btnSaving.setTextColor(getResources().getColor(R.color.lifecardsdk_gray));
                    btnSaving.setBackgroundResource(R.drawable.lifecardsdk_round_button_defaut);

                    btnNearest.setTextColor(getResources().getColor(R.color.lifecardsdk_gray));
                    btnNearest.setBackgroundResource(R.drawable.lifecardsdk_round_button_defaut);

                    btnPrice.setTextColor(getResources().getColor(R.color.lifecardsdk_white));
                    btnPrice.setBackgroundResource(R.drawable.lifecardsdk_round_button_orange);
                    if (priceStatus) {
                        showLoading(false);
                        sortType = Constants.SortType.PASC;
                        defCardAdapter.clearData();
                        mPresenter.getDataCard(categoryID, "", sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize);
                        btnPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.lifecardsdk_ic_expand_less_big, 0);
                        priceStatus = false;
                    } else {
                        showLoading(false);
                        sortType = Constants.SortType.PDESC;
                        defCardAdapter.clearData();
                        mPresenter.getDataCard(categoryID, "", sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize);
                        btnPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.lifecardsdk_ic_expand_more_big, 0);
                        priceStatus = true;
                    }
                } else {
                    showError(mActivity.getString(R.string.lifecardsdk_cant_connect_internet), "");
                }

            }
        });
        defCardAdapter.setOnClickItem(new DefCardAdapter.OnClickItem() {
            @Override
            public void onClickBuyCard(DefCard item) {
                if (!isClicked) {
                    CardDetailsFragment cardDetailsFragment = new CardDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.BundleConstants.PRODUCT_CODE, item.getCode());
                    bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainBuyCardFragment);
                    cardDetailsFragment.setArguments(bundle);
                    MainBuyCardFragment.getInstance().addFragment(cardDetailsFragment, DefCardFragment.this);
                    startTimer();
                }
            }
        });
        defCardAdapter.setOnClickLogoListener(new DefCardAdapter.OnClickLogo() {
            @Override
            public void onClickItem(ProviderDto item) {

                ProviderFragment providerFragment = new ProviderFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.BundleConstants.PROVIDER_ID, item.getId());
                bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainBuyCardFragment);
                providerFragment.setArguments(bundle);
                MainBuyCardFragment.getInstance().addFragment(providerFragment, DefCardFragment.this);
            }
        });
        defCardAdapter.setOnClickPromotion(new DefCardAdapter.OnClickPromotion() {
            @Override
            public void onClickPromotion(String code) {
                if (!isClicked) {
                    CardDetailsFragment cardDetailsFragment = new CardDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.BundleConstants.PRODUCT_CODE, code);
                    bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainBuyCardFragment);
                    cardDetailsFragment.setArguments(bundle);
                    MainBuyCardFragment.getInstance().addFragment(cardDetailsFragment, DefCardFragment.this);
                    startTimer();
                }
            }
        });
        categoryAdapterMain.setOnClickListener(new com.lpb.lifecardsdk.ui.home.fragment.buycard.defcard.adapter.CategoryAdapter.OnClickListener() {
            @Override
            public void onClick(CategoryResponse.LstCate item, int position) {
                if (PresenterUtils.isNetworkConnected(mActivity)) {
                    if (categoryID.equals(item.getId()))
                        return;
                    categoryID = item.getId();
                    rvCategoryMain.scrollToPosition(position);
                    categoryAdapterMain.setPosSelected(position);
                    categoryAdapterMain.notifyDataSetChanged();
                    if (isNearest && Config.Header.getHeader().getLocation() == null) {
                        return;
                    }
                    showLoading(false);
                    defCardAdapter.clearData();
                    mPresenter.getDataCard(categoryID, "", sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize);
                } else {
                    showError(mActivity.getString(R.string.lifecardsdk_cant_connect_internet), "");
                }
            }
        });
//        edSearchQuery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SearchFragment searchFragment = new SearchFragment();
//                Bundle bundle = new Bundle();
//                bundle.putInt(Constants.BundleConstants.PROVIDER_ID, Config.DEAULT_VALUE_INT);
//                bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainBuyCardFragment);
//                searchFragment.setArguments(bundle);
//                MainBuyCardFragment.getInstance().addFragment(searchFragment, DefCardFragment.this);
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isNearest && Config.Header.getHeader().getLocation() == null) {
            if (PresenterUtils.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, mActivity)) {
                rlCantFindLocation.setVisibility(View.GONE);
                if (!PresenterUtils.isLocationEnabled(mActivity)) {
                    if (!isFirstTurnOnLocation) {
                        ScreenUtils.displayLocationSettingsRequest(mActivity.getApplicationContext(), mActivity);
                        isFirstTurnOnLocation = true;
                    } else {
                        showScreenTurnOnLocation();
                    }
                } else {
                    showLoading(false);
                    sortType = Constants.SortType.GN;
                    showContentW();
                    setTimeoutGetLocation();
                    LocationUtil.requestSingleUpdate(mActivity,
                            new LocationUtil.LocationCallback() {
                                @Override
                                public void onLocationChanged(LocationUtil.GPSCoordinates location) {
                                    double lat = location.latitude;
                                    double lng = location.longitude;
                                    Config.Header.setLocation(new Location(String.valueOf(lat), String.valueOf(lng)));
                                    clearLocation();
                                    defCardAdapter.clearData();
                                    mPresenter.getDataCard(categoryID, "", sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize);
                                }
                            });

                }
            } else {
                showScreenSettingLocation();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Config.PERMISSION_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                showScreenSettingLocation();

            }
        }
    }


    private void initFilter() {
        nsvFilter = view.findViewById(R.id.nsvFilter);
        rvCategory = view.findViewById(R.id.rvCategory);
        rvCategory.setNestedScrollingEnabled(false);

        rvLocation = view.findViewById(R.id.rvLocation);
        rvLocation.setNestedScrollingEnabled(false);

        rvProvider = view.findViewById(R.id.rvProvider);
        rvProvider.setNestedScrollingEnabled(false);

        tvLessCate = view.findViewById(R.id.tvLessCate);
        tvLessLocation = view.findViewById(R.id.tvLessLocation);
        tvLessProvider = view.findViewById(R.id.tvLessProvider);

        tvMoreCate = view.findViewById(R.id.tvMoreCate);
        tvMoreLocation = view.findViewById(R.id.tvMoreLocation);
        tvMoreProvider = view.findViewById(R.id.tvMoreProvider);

        btnApply = view.findViewById(R.id.btnApply);
        btnReset = view.findViewById(R.id.btnReset);

        listCategorySelected = new LinkedHashMap<>();
        listLocationSelected = new LinkedHashMap<>();
        listProviderSelected = new LinkedHashMap<>();

        categoryAdapter = new CategoryAdapter(mActivity);
        locationAdapter = new LocationAdapter(mActivity);
        providerAdapter = new ProviderAdapter(mActivity);

        FlexboxLayoutManager categoryManager = new FlexboxLayoutManager(mActivity);
        categoryManager.setFlexWrap(FlexWrap.WRAP);
        categoryManager.setFlexDirection(FlexDirection.ROW);
        categoryManager.setJustifyContent(JustifyContent.FLEX_START);

        FlexboxLayoutManager locationManager = new FlexboxLayoutManager(mActivity);
        locationManager.setFlexWrap(FlexWrap.WRAP);
        locationManager.setFlexDirection(FlexDirection.ROW);
        locationManager.setJustifyContent(JustifyContent.FLEX_START);

        FlexboxLayoutManager providerManager = new FlexboxLayoutManager(mActivity);
        providerManager.setFlexWrap(FlexWrap.WRAP);
        providerManager.setFlexDirection(FlexDirection.ROW);
        providerManager.setJustifyContent(JustifyContent.FLEX_START);

        rvCategory.setAdapter(categoryAdapter);
        rvCategory.setLayoutManager(categoryManager);

        rvLocation.setAdapter(locationAdapter);
        rvLocation.setLayoutManager(locationManager);

        rvProvider.setAdapter(providerAdapter);
        rvProvider.setLayoutManager(providerManager);
    }

    private void initActionFilter() {
        locationAdapter.setOnClickListener(new LocationAdapter.OnClickListener() {
            @Override
            public void onClick(AreaResponse.ListArea item, int position) {
                // kiểm tra position có trong listLocationSelected hay k?
                // nếu chưa có thì thêm vào listLocationSelected
                // nếu có rồi thì xóa đi rồi vì click 2 lần
                if (listLocationSelected.get(position) == null) {
                    listLocationSelected.put(position, item.getAreaName());
                } else {
                    listLocationSelected.remove(position);
                }
                // lấy listLocationSelected set vào adapter
                locationAdapter.setListSelected(listLocationSelected);
            }
        });

        providerAdapter.setOnClickListener(new ProviderAdapter.OnClickListener() {
            @Override
            public void onClick(ProviderResponse.List item, int position) {
                if (listProviderSelected.get(position) == null) {
                    listProviderSelected.put(position, item.getName());
                } else {
                    listProviderSelected.remove(position);
                }
                providerAdapter.setListSelected(listProviderSelected);
            }
        });
        categoryAdapter.setOnClickListener(new CategoryAdapter.OnClickListener() {
            @Override
            public void onClick(CategoryResponse.LstCate item, int position) {
                // kiểm tra position có trong listCategorySelected hay k?
                // nếu chưa có thì xóa vị trí cũ và thêm vị trí mới vào
                // nếu có rồi thì xóa đi vì click 2 lần
                if (listCategorySelected.get(position) == null) {
                    listCategorySelected.clear();
                    listCategorySelected.put(position, item.getName());
                } else {
                    listCategorySelected.clear();
                }
                // lấy listCategorySelected set vào adapter
                categoryAdapter.setListSelected(listCategorySelected);
            }
        });
        tvMoreCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandRecyclerView(tvMoreCate, tvLessCate, true, rvCategory);
            }
        });
        tvLessCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandRecyclerView(tvLessCate, tvMoreCate, false, rvCategory);
            }
        });

        tvMoreProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandRecyclerView(tvMoreProvider, tvLessProvider, true, rvProvider);
            }
        });
        tvLessProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandRecyclerView(tvLessProvider, tvMoreProvider, false, rvProvider);

            }
        });

        tvMoreLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandRecyclerView(tvMoreLocation, tvLessLocation, true, rvLocation);
            }
        });
        tvLessLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandRecyclerView(tvLessLocation, tvMoreLocation, false, rvLocation);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listCategorySelected.clear();
                listLocationSelected.clear();
                listProviderSelected.clear();
                // TODO: 28/04/2020  
//                edMaxPrice.setText("");
//                edMinPrice.setText("");
                rgPrice.clearCheck();
                categoryAdapter.setListSelected(listCategorySelected);
                locationAdapter.setListSelected(listLocationSelected);
                providerAdapter.setListSelected(listProviderSelected);
                expandRecyclerView(tvLessLocation, tvMoreLocation, false, rvLocation);
                expandRecyclerView(tvLessProvider, tvMoreProvider, false, rvProvider);
                expandRecyclerView(tvLessCate, tvMoreCate, false, rvCategory);
            }
        });
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mActivity, "Danh sách đã chọn: \n Danh mục \n" + listCategorySelected.values().toString() + "\n Vị trí \n" + listLocationSelected.values().toString() + "\n Nhà cung cấp \n" + listProviderSelected.values().toString(), Toast.LENGTH_LONG).show();
                drawer.closeDrawer(GravityCompat.END);
            }
        });
    }

    private void expandRecyclerView(TextView tvHide, TextView tvShow, boolean isExpand, RecyclerView recyclerView) {
        tvShow.setVisibility(View.VISIBLE);
        tvHide.setVisibility(View.GONE);
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        if (isExpand)
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        else
            // default height 77dp
            params.height = (int) (77 * mActivity.getResources().getDisplayMetrics().density);
        recyclerView.setLayoutParams(params);
    }

    private void showContentW() {
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);

        rlCantFindLocation.setVisibility(View.GONE);
        rvContent.setVisibility(View.VISIBLE);
        rlTurnOnLocation.setVisibility(View.GONE);
    }

    private void showScreenSettingLocation() {
        params.setScrollFlags(0);

        rlCantFindLocation.setVisibility(View.VISIBLE);
        rvContent.setVisibility(View.GONE);
        rlTurnOnLocation.setVisibility(View.GONE);
    }

    private void showScreenTurnOnLocation() {
        params.setScrollFlags(0);

        rlCantFindLocation.setVisibility(View.GONE);
        rvContent.setVisibility(View.GONE);
        rlTurnOnLocation.setVisibility(View.VISIBLE);
    }

    public boolean backPressed(boolean animate) {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END, animate);
            return false;
        }
        return true;
    }


    @Override
    public void setDataLocation(List<AreaResponse.ListArea> locationItems) {
        locationAdapter.setItems(locationItems);
    }

    @Override
    public void setDataProvider(List<ProviderResponse.List> providerItems) {
        providerAdapter.setItems(providerItems);
    }

    @Override
    public void setDataCategory(List<CategoryResponse.LstCate> categoryItemList) {
        if (categoryItemList == null) {
            rvCategoryMain.setVisibility(View.GONE);
            return;
        }
        if (categoryItemList.size() <= 1) {
            rvCategoryMain.setVisibility(View.GONE);
        } else if (categoryItemList.size() == 2) {
            for (CategoryResponse.LstCate lstCate : categoryItemList) {
                if (lstCate.getId() == 1) {
                    rvCategoryMain.setVisibility(View.GONE);
                    break;
                }
            }
        }
        categoryAdapter.setItems(categoryItemList);
        categoryAdapterMain.setItems(categoryItemList);
    }

    @Override
    public void addDataCard(List<CardListConfig> cardListConfigs) {
        if (cardListConfigs.size() == 0) {
            stopLoadMore();
        }
        defCardAdapter.addItems(cardListConfigs);
    }

    @Override
    public void stopLoadMore() {
        rvContent.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
            }
        });
        defCardAdapter.setOnLoadMore(false);
    }

    @Override
    public void setDataCard(List<CardListConfig> cardListConfigs) {
        if (cardListConfigs.size() == 0) {
            stopLoadMore();
        }
        defCardAdapter.setItems(cardListConfigs);
    }

    private void startTimer() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                isClicked = false;
            }
        }, Config.DELAY_CLICK);
    }

    private void setTimeoutGetLocation() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (Config.Header.getHeader().getLocation() == null)
                    hideLoading();
            }
        }, Config.TIMEOUT_GET_LOCATION);
    }

    private void clearLocation() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Config.Header.setLocation(null);
            }
        }, Config.TIME_TO_UPDATE_LOCATION);
    }
}

