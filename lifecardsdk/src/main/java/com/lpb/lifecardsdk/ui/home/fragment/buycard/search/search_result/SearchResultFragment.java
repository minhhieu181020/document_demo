package com.lpb.lifecardsdk.ui.home.fragment.buycard.search.search_result;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.response.default_.CategoryResponse;
import com.lpb.lifecardsdk.data.model.response.default_.PackageSearchResponse;
import com.lpb.lifecardsdk.data.model.restheader.Location;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.MainBuyCardFragment;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.carddetails_v2.CardDetailsFragment;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.content.adapter.BuyCardAdapter;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.content.adapter.CategoryAdapter;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.MainScanQrCodeFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard.MainMyCardsFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycode.MainMyCodeFragment;
import com.lpb.lifecardsdk.util.LocationUtil;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.ScreenUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.CustomEditTextClearable;
import com.lpb.lifecardsdk.widget.recyclerview.EndlessRecyclerViewScrollListener;

import java.util.List;

public class SearchResultFragment extends BaseDataFragment<SearchResultPresenter> implements SearchResultContract.View {
    //todo
    // sửa phần category khi bị null hoặc dưới size <=1 + show Content ==2 + refresh + getCategory first
    //
    private String sKeyWord = "";
    private ImageView imgBack;
    private CustomEditTextClearable edtSearch;
    private RecyclerView rvCategoryMain;
    private Button btnLasted;
    private Button btnSaving;
    private Button btnNearest;
    private Button btnPrice;
    private RelativeLayout rlCantFindLocation;
    private Button btnSettings;
    private RelativeLayout rlTurnOnLocation;
    private Button btnTurnOnLocation;
    private RecyclerView rvContent;
    private RelativeLayout rlSearchNoResult;
    private CoordinatorLayout clContainer;
    private LinearLayoutManager linearLayoutManager;
    private AppBarLayout.LayoutParams params;

    private BuyCardAdapter buyCardAdapter;
    private CategoryAdapter categoryAdapter;
    private boolean isClicked = false, isLasted = true, isSave = false, isNearest = false;
    private String sortType = Constants.SortType.MN;
    private boolean priceStatus, isFirstTurnOnLocation;
    private Integer providerID, categoryID = 1;
    private String mainParentClass;
    private boolean isCategoryEmpty;
    private int pageSize = 20;
    private int pageIndex = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsk_fragment_search_result;
    }

    @Override
    protected void initView() {
        imgBack = view.findViewById(R.id.imgBack);
        edtSearch = view.findViewById(R.id.edtSearch);
        rvCategoryMain = view.findViewById(R.id.rvCategoryMain);
        btnLasted = view.findViewById(R.id.btnLasted);
        btnSaving = view.findViewById(R.id.btnSaving);
        btnNearest = view.findViewById(R.id.btnNearest);
        btnPrice = view.findViewById(R.id.btnPrice);
        rvContent = view.findViewById(R.id.rvContent);
        HorizontalScrollView hsvOption = view.findViewById(R.id.hsvOption);
        rlSearchNoResult = view.findViewById(R.id.rlSearchNoResult);
        rlCantFindLocation = view.findViewById(R.id.rlCantFindLocation);
        btnSettings = view.findViewById(R.id.btnSettings);
        rlTurnOnLocation = view.findViewById(R.id.rlTurnOnLocation);
        btnTurnOnLocation = view.findViewById(R.id.btnTurnOnLocation);
        clContainer = view.findViewById(R.id.clContainer);

        LinearLayout llOptions = view.findViewById(R.id.llOptions);
        params = (AppBarLayout.LayoutParams) llOptions.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);

        rvContent = view.findViewById(R.id.rvContent);
        buyCardAdapter = new BuyCardAdapter(mActivity, true);
        linearLayoutManager = new LinearLayoutManager(mActivity);
        rvContent.setAdapter(buyCardAdapter);
        rvContent.setLayoutManager(linearLayoutManager);

        rvCategoryMain = view.findViewById(R.id.rvCategoryMain);
        categoryAdapter = new com.lpb.lifecardsdk.ui.home.fragment.buycard.content.adapter.CategoryAdapter(mActivity);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false);
        rvCategoryMain.setAdapter(categoryAdapter);
        rvCategoryMain.setLayoutManager(layoutManager2);
    }

    @Override
    protected void initData() {
        mPresenter = new SearchResultPresenter(mActivity, this);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        sKeyWord = bundle.getString(Constants.BundleConstants.KEYWORD);
        providerID = bundle.getInt(Constants.BundleConstants.PROVIDER_ID, Config.DEAULT_VALUE_INT);
        mainParentClass = bundle.getString(Constants.BundleConstants.MAIN_PARENT_CLASS);
        if (StringUtils.isEmpty(mainParentClass)) {
            showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
            return;
        }
        edtSearch.setText(sKeyWord);

        if (PresenterUtils.isNetworkConnected(mActivity)) {
            if (providerID == Config.DEAULT_VALUE_INT) {
                providerID = null;
                mPresenter.getDataCategory("");
            } else {
                btnNearest.setVisibility(View.GONE);
                mPresenter.getDataCategory(providerID.toString());
            }
            mPresenter.getDataCard(categoryID, sKeyWord, Constants.SortType.MN, LCConfig.getPhoneNumber(), pageIndex, pageSize, providerID, Constants.QueryType.FIRST);
        } else {
            if (providerID == Config.DEAULT_VALUE_INT) {
                providerID = null;
            }
            showError(mActivity.getString(R.string.lifecardsdk_cant_connect_internet), "");
        }
    }

    @Override
    protected void initAction() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });
        rvContent.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPresenter.getDataCard(categoryID, sKeyWord, sortType, LCConfig.getPhoneNumber(), page, pageSize, providerID, Constants.QueryType.WITH_OPTION);
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenUtils.openPermissionSettings(mActivity);
            }
        });
        btnTurnOnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenUtils.openTurnOnLocation(mActivity);
            }
        });
        edtSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    sKeyWord = edtSearch.getText().toString().trim();
                    if (sKeyWord.isEmpty()) {
                        showError(mActivity.getString(R.string.lifecardsdk_search_notify_empty_key_word), "");
                    } else {
                        if (PresenterUtils.isNetworkConnected(mActivity)) {
                            if (isNearest && Config.Header.getHeader().getLocation() == null) {
                                //todo
                            } else {
                                showLoading(false);
                                if (isCategoryEmpty) {
                                    // category = 1 <=> tất cả
                                    categoryID = 1;
                                    sortType = Constants.SortType.MN;

                                    rvCategoryMain.scrollToPosition(0);
                                    categoryAdapter.setPosSelected(0);
                                    categoryAdapter.notifyDataSetChanged();

                                    btnLasted.setTextColor(getResources().getColor(R.color.lifecardsdk_white));
                                    btnLasted.setBackgroundResource(R.drawable.lifecardsdk_round_button_orange);

                                    btnSaving.setTextColor(getResources().getColor(R.color.lifecardsdk_gray));
                                    btnSaving.setBackgroundResource(R.drawable.lifecardsdk_round_button_defaut);

                                    btnNearest.setTextColor(getResources().getColor(R.color.lifecardsdk_gray));
                                    btnNearest.setBackgroundResource(R.drawable.lifecardsdk_round_button_defaut);

                                    btnPrice.setTextColor(getResources().getColor(R.color.lifecardsdk_gray));
                                    btnPrice.setBackgroundResource(R.drawable.lifecardsdk_round_button_defaut);
                                    btnPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.lifecardsdk_ic_unfold_more_big, 0);
                                    buyCardAdapter.clearData();
                                    mPresenter.getDataCard(categoryID, sKeyWord, sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize, providerID, Constants.QueryType.NEXT);

                                } else {
                                    if (providerID == null) {
                                        mPresenter.getDataCategory("");
                                    } else {
                                        mPresenter.getDataCategory(providerID.toString());
                                    }
                                    buyCardAdapter.clearData();
                                    mPresenter.getDataCard(categoryID, sKeyWord, Constants.SortType.MN, LCConfig.getPhoneNumber(), pageIndex, pageSize, providerID, Constants.QueryType.FIRST);
                                }
                            }
                        } else {
                            showError(mActivity.getString(R.string.lifecardsdk_cant_connect_internet), "");
                        }
                    }
                    return true;
                }
                return false;
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
                    sortType = Constants.SortType.MN;
                    showLoading(false);
                    buyCardAdapter.clearData();
                    mPresenter.getDataCard(categoryID, sKeyWord, sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize, providerID, Constants.QueryType.WITH_OPTION);

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
                                                sortType = Constants.SortType.GN;
                                                showLoading(false);
                                                buyCardAdapter.clearData();
                                                mPresenter.getDataCard(categoryID, sKeyWord, sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize, providerID, Constants.QueryType.WITH_OPTION);
                                            }
                                        });
                            }
                        } else {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Config.PERMISSION_REQUEST_LOCATION);
                        }
                    } else {
                        showLoading(false);
                        sortType = Constants.SortType.GN;
                        sortType = Constants.SortType.GN;
                        showLoading(false);
                        buyCardAdapter.clearData();
                        mPresenter.getDataCard(categoryID, sKeyWord, sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize, providerID, Constants.QueryType.WITH_OPTION);
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
                    sortType = Constants.SortType.TKN;
                    showLoading(false);
                    buyCardAdapter.clearData();
                    mPresenter.getDataCard(categoryID, sKeyWord, sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize, providerID, Constants.QueryType.WITH_OPTION);
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
                        sortType = Constants.SortType.PASC;
                        showLoading(false);
                        buyCardAdapter.clearData();
                        mPresenter.getDataCard(categoryID, sKeyWord, sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize, providerID, Constants.QueryType.WITH_OPTION);

                        btnPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.lifecardsdk_ic_expand_less_big, 0);
                        priceStatus = false;
                    } else {
                        sortType = Constants.SortType.PDESC;
                        showLoading(false);
                        buyCardAdapter.clearData();
                        mPresenter.getDataCard(categoryID, sKeyWord, sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize, providerID, Constants.QueryType.WITH_OPTION);
                        btnPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.lifecardsdk_ic_expand_more_big, 0);
                        priceStatus = true;
                    }
                } else {
                    showError(mActivity.getString(R.string.lifecardsdk_cant_connect_internet), "");
                }

            }
        });
        buyCardAdapter.setOnClickListener(new BuyCardAdapter.OnClickListener() {
            @Override
            public void onClickBuyCard(PackageSearchResponse.DefCardDto item) {
                if (!isClicked) {
                    CardDetailsFragment cardDetailsFragment = new CardDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.BundleConstants.PRODUCT_CODE, item.getCode());
                    bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
                    cardDetailsFragment.setArguments(bundle);
                    switch (mainParentClass) {
                        case Constants.ParentClass.MainBuyCardFragment:
                            MainBuyCardFragment.getInstance().addFragment(cardDetailsFragment, SearchResultFragment.this);
                            break;
                        case Constants.ParentClass.MainMyCardFragment:
                            MainMyCardsFragment.getInstance().addFragment(cardDetailsFragment, SearchResultFragment.this);
                            break;
                        case Constants.ParentClass.MainMyCodeFragment:
                            MainMyCodeFragment.getInstance().addFragment(cardDetailsFragment, SearchResultFragment.this);
                            break;
                        case Constants.ParentClass.MainScanQrCodeFragment:
                            MainScanQrCodeFragment.getInstance().addFragment(cardDetailsFragment, SearchResultFragment.this);
                            break;
                        default:
                            showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                            break;
                    }
                    startTimer();
                }
            }
        });
        categoryAdapter.setOnClickListener(new com.lpb.lifecardsdk.ui.home.fragment.buycard.content.adapter.CategoryAdapter.OnClickListener() {
            @Override
            public void onClick(CategoryResponse.LstCate item, int position) {

                if (PresenterUtils.isNetworkConnected(mActivity)) {
                    if (categoryID.equals(item.getId()))
                        return;
                    categoryID = item.getId();
                    rvCategoryMain.scrollToPosition(position);
                    categoryAdapter.setPosSelected(position);
                    categoryAdapter.notifyDataSetChanged();
                    if (isNearest && Config.Header.getHeader().getLocation() == null) {
                        return;
                    }
                    showLoading(false);
                    buyCardAdapter.clearData();
                    mPresenter.getDataCard(categoryID, sKeyWord, sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize, providerID, Constants.QueryType.WITH_OPTION);
                } else {
                    showError(mActivity.getString(R.string.lifecardsdk_cant_connect_internet), "");
                }
            }
        });
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
                                public void onLocationChanged(LocationUtil.GPSCoordinates loc) {
                                    double lat = loc.latitude;
                                    double lng = loc.longitude;
                                    Config.Header.setLocation(new Location(String.valueOf(lat), String.valueOf(lng)));
                                    clearLocation();
                                    buyCardAdapter.clearData();
                                    mPresenter.getDataCard(categoryID, sKeyWord, sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize, providerID, Constants.QueryType.WITH_OPTION);
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

    private void startTimer() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                isClicked = false;
            }
        }, Config.DELAY_CLICK);
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

    @Override
    public void showContent(boolean isEmpty) {
        if (isEmpty) {
            clContainer.setVisibility(View.VISIBLE);
            rlSearchNoResult.setVisibility(View.GONE);
        } else {
            clContainer.setVisibility(View.GONE);
            rlSearchNoResult.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setDataCategory(List<CategoryResponse.LstCate> categoryItemList) {
        isCategoryEmpty = true;
        categoryAdapter.setItems(categoryItemList);
    }

    @Override
    public void setDataCard(List<PackageSearchResponse.DefCardDto> packageDtos) {
        if (packageDtos.size() == 0) {
            stopLoadMore();
        }
        buyCardAdapter.setItems(packageDtos);
    }

    @Override
    public void addDataCard(List<PackageSearchResponse.DefCardDto> packageDtos) {
        if (packageDtos.size() == 0) {
            stopLoadMore();
        }
        buyCardAdapter.addItems(packageDtos);
    }

    @Override
    public void stopLoadMore() {
        rvContent.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

            }
        });

        buyCardAdapter.setOnLoadMore(false);
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
}
