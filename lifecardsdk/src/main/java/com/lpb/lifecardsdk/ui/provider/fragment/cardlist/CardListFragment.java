package com.lpb.lifecardsdk.ui.provider.fragment.cardlist;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.response.default_.CategoryResponse;
import com.lpb.lifecardsdk.data.model.response.default_.defcard.CardListConfig;
import com.lpb.lifecardsdk.data.model.response.default_.defcard.DefCard;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.MainBuyCardFragment;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.carddetails_v2.CardDetailsFragment;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.content.adapter.CategoryAdapter;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.defcard.adapter.DefCardAdapter;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.MainScanQrCodeFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard.MainMyCardsFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycode.MainMyCodeFragment;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.recyclerview.EndlessRecyclerViewScrollListener;

import java.util.List;

public class CardListFragment extends BaseDataFragment<CardListPresenter> implements CardListContract.View {
    private RecyclerView rvCategoryMain;
    private Button btnLasted;
    private Button btnSaving;
    private Button btnPrice;
    private CoordinatorLayout clContainer;
    private RecyclerView rvContent;
    private AppBarLayout ablCardList;
    private AppBarLayout.LayoutParams params;
    private SwipeRefreshLayout refreshLayout;
    private DefCardAdapter defCardAdapter;
    private CategoryAdapter categoryAdapter;
    private LinearLayoutManager llmBuyCard;

    private boolean isClicked = false, isLasted = true, isSave = false;
    private Integer categoryID = 1;// tat ca
    private Integer providerID;
    private String mainParentClass;
    private String sortType = Constants.SortType.MN;
    private boolean priceStatus;
    private boolean isUserVisibleHint = true;
    private int pageSize = 20;
    private int pageIndex = 1;

    private static CardListFragment instance = null;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && this.isUserVisibleHint) {
            if (PresenterUtils.isNetworkConnected(mActivity)) {
                if (StringUtils.isEmpty(mainParentClass) || providerID == Config.DEAULT_VALUE_INT) {
                    showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                    return;
                }
                showLoading(true);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showLoading(true);
                        isUserVisibleHint = false;
                        //mPresenter.getDataCategory(providerID.toString());
                        mPresenter.getDataCard(categoryID, "", sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize, providerID, Constants.QueryType.FIRST);
                    }
                }, Config.DELAY_REQ_API);
            } else {
                showError(mActivity.getString(R.string.lifecardsdk_cant_connect_internet), "");
            }


        }
    }

    @Override
    protected int getLayoutId() {
        instance = this;
        return R.layout.lifecardsdk_fragment_provider_card_list;
    }
    public static CardListFragment getInstance() {
        return instance;
    }
    @Override
    protected void initView() {
        rvCategoryMain = view.findViewById(R.id.rvCategoryMain);
        btnSaving = view.findViewById(R.id.btnSaving);
        btnPrice = view.findViewById(R.id.btnPrice);
        btnLasted = view.findViewById(R.id.btnLasted);
        clContainer = view.findViewById(R.id.clContainer);
        LinearLayout llOptions = view.findViewById(R.id.llOptions);
        ablCardList = view.findViewById(R.id.ablCardList);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeResources(R.color.lifecardsdk_orange);
        params = (AppBarLayout.LayoutParams) llOptions.getLayoutParams();
//        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
//                | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);

        rvContent = view.findViewById(R.id.rvContent);
        defCardAdapter = new DefCardAdapter(mActivity, false);
        llmBuyCard = new LinearLayoutManager(mActivity);
        rvContent.setAdapter(defCardAdapter);
        rvContent.setLayoutManager(llmBuyCard);

        rvCategoryMain = view.findViewById(R.id.rvCategoryMain);
        categoryAdapter = new com.lpb.lifecardsdk.ui.home.fragment.buycard.content.adapter.CategoryAdapter(mActivity);
        LinearLayoutManager llmCategory = new LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false);
        rvCategoryMain.setAdapter(categoryAdapter);
        rvCategoryMain.setLayoutManager(llmCategory);
    }
    public void enableSwipeRefreshLayout(boolean enable) {
        refreshLayout.setEnabled(enable);
    }
    @Override
    protected void initData() {
        mPresenter = new CardListPresenter(mActivity, this);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        providerID = bundle.getInt(Constants.BundleConstants.PROVIDER_ID, Config.DEAULT_VALUE_INT);
        mainParentClass = bundle.getString(Constants.BundleConstants.MAIN_PARENT_CLASS);
    }

    @Override
    protected void initAction() {
        rvContent.addOnScrollListener(new EndlessRecyclerViewScrollListener(llmBuyCard) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPresenter.getDataCard(categoryID, "", sortType, LCConfig.getPhoneNumber(), page + 1, pageSize, providerID, Constants.QueryType.WITH_OPTION);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (PresenterUtils.isNetworkConnected(mActivity)) {
                    defCardAdapter.setOnLoadMore(true);
                    defCardAdapter.clearData();
                    mPresenter.getDataCard(categoryID, "", sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize, providerID, Constants.QueryType.WITH_OPTION);
                } else {
                    setRefreshing(false);
                    showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
                }
            }
        });
        btnLasted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PresenterUtils.isNetworkConnected(mActivity)) {
                    if (isLasted) {
                        return;
                    }
                    isLasted = true;
                    isSave = false;

                    showLoading(false);
                    sortType = Constants.SortType.MN;
                    defCardAdapter.clearData();
                    mPresenter.getDataCard(categoryID, "", sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize, providerID, Constants.QueryType.WITH_OPTION);
                    btnLasted.setTextColor(getResources().getColor(R.color.lifecardsdk_white));
                    btnLasted.setBackgroundResource(R.drawable.lifecardsdk_round_button_orange);

                    btnSaving.setTextColor(getResources().getColor(R.color.lifecardsdk_gray));
                    btnSaving.setBackgroundResource(R.drawable.lifecardsdk_round_button_defaut);


                    btnPrice.setTextColor(getResources().getColor(R.color.lifecardsdk_gray));
                    btnPrice.setBackgroundResource(R.drawable.lifecardsdk_round_button_defaut);
                    btnPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.lifecardsdk_ic_unfold_more_big, 0);
                } else {
                    showError(mActivity.getString(R.string.lifecardsdk_cant_connect_internet), "");
                }

            }
        });
        btnSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PresenterUtils.isNetworkConnected(mActivity)) {
                    if (isSave) {
                        return;
                    }
                    isLasted = false;
                    isSave = true;

                    showLoading(false);
                    sortType = Constants.SortType.TKN;
                    defCardAdapter.clearData();
                    mPresenter.getDataCard(categoryID, "", sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize, providerID, Constants.QueryType.WITH_OPTION);
                    btnSaving.setTextColor(getResources().getColor(R.color.lifecardsdk_white));
                    btnSaving.setBackgroundResource(R.drawable.lifecardsdk_round_button_orange);

                    btnLasted.setTextColor(getResources().getColor(R.color.lifecardsdk_gray));
                    btnLasted.setBackgroundResource(R.drawable.lifecardsdk_round_button_defaut);


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
                if (PresenterUtils.isNetworkConnected(mActivity)) {
                    isLasted = false;
                    isSave = false;

                    btnLasted.setTextColor(getResources().getColor(R.color.lifecardsdk_gray));
                    btnLasted.setBackgroundResource(R.drawable.lifecardsdk_round_button_defaut);

                    btnSaving.setTextColor(getResources().getColor(R.color.lifecardsdk_gray));
                    btnSaving.setBackgroundResource(R.drawable.lifecardsdk_round_button_defaut);


                    btnPrice.setTextColor(getResources().getColor(R.color.lifecardsdk_white));
                    btnPrice.setBackgroundResource(R.drawable.lifecardsdk_round_button_orange);
                    if (priceStatus) {
                        showLoading(false);
                        sortType = Constants.SortType.PASC;
                        defCardAdapter.clearData();
                        mPresenter.getDataCard(categoryID, "", sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize, providerID, Constants.QueryType.WITH_OPTION);
                        btnPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.lifecardsdk_ic_expand_less_big, 0);
                        priceStatus = false;
                    } else {
                        showLoading(false);
                        sortType = Constants.SortType.PDESC;
                        defCardAdapter.clearData();
                        mPresenter.getDataCard(categoryID, "", sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize, providerID, Constants.QueryType.WITH_OPTION);
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
                    bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
                    cardDetailsFragment.setArguments(bundle);
                    switch (mainParentClass) {
                        case Constants.ParentClass.MainBuyCardFragment:
                            MainBuyCardFragment.getInstance().addFragment(cardDetailsFragment, getParentFragment());
                            break;
                        case Constants.ParentClass.MainMyCardFragment:
                            MainMyCardsFragment.getInstance().addFragment(cardDetailsFragment, getParentFragment());
                            break;
                        case Constants.ParentClass.MainMyCodeFragment:
                            MainMyCodeFragment.getInstance().addFragment(cardDetailsFragment, getParentFragment());
                            break;
                        case Constants.ParentClass.MainScanQrCodeFragment:
                            MainScanQrCodeFragment.getInstance().addFragment(cardDetailsFragment, getParentFragment());
                            break;
                        default:
                            showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                            break;
                    }
                    startTimer();
                }
            }
        });

        defCardAdapter.setOnClickPromotion(new DefCardAdapter.OnClickPromotion() {
            @Override
            public void onClickPromotion(String code) {
                if (!isClicked) {
                    CardDetailsFragment cardDetailsFragment = new CardDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.BundleConstants.PRODUCT_CODE, code);
                    bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
                    cardDetailsFragment.setArguments(bundle);
                    switch (mainParentClass) {
                        case Constants.ParentClass.MainBuyCardFragment:
                            MainBuyCardFragment.getInstance().addFragment(cardDetailsFragment, getParentFragment());
                            break;
                        case Constants.ParentClass.MainMyCardFragment:
                            MainMyCardsFragment.getInstance().addFragment(cardDetailsFragment, getParentFragment());
                            break;
                        case Constants.ParentClass.MainMyCodeFragment:
                            MainMyCodeFragment.getInstance().addFragment(cardDetailsFragment, getParentFragment());
                            break;
                        case Constants.ParentClass.MainScanQrCodeFragment:
                            MainScanQrCodeFragment.getInstance().addFragment(cardDetailsFragment, getParentFragment());
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
                    showLoading(false);
                    defCardAdapter.clearData();
                    mPresenter.getDataCard(categoryID, "", sortType, LCConfig.getPhoneNumber(), pageIndex, pageSize, providerID, Constants.QueryType.WITH_OPTION);
                    rvCategoryMain.scrollToPosition(position);
                    categoryAdapter.setPosSelected(position);
                    categoryAdapter.notifyDataSetChanged();
                } else {
                    showError(mActivity.getString(R.string.lifecardsdk_cant_connect_internet), "");
                }
            }
        });
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

    @Override
    public void setDataCategory(List<CategoryResponse.LstCate> categoryItemList) {
        if (categoryItemList == null) {
            rvCategoryMain.setVisibility(View.GONE);
            return;
        }
        if (categoryItemList.size() <= 1) {
            rvCategoryMain.setVisibility(View.GONE);
        }
        categoryAdapter.setItems(categoryItemList);
    }

    @Override
    public void setDataCard(List<CardListConfig> packageDtos) {
        if (packageDtos.size() == 0) {
            stopLoadMore();
        }
        defCardAdapter.setItems(packageDtos);
    }

    @Override
    public void addDataCard(List<CardListConfig> packageDtos) {
        if (packageDtos.size() == 0) {
            stopLoadMore();
        }
        defCardAdapter.addItems(packageDtos);
    }

    @Override
    public void stopLoadMore() {
        rvContent.addOnScrollListener(new EndlessRecyclerViewScrollListener(llmBuyCard) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

            }
        });

        defCardAdapter.setOnLoadMore(false);
    }

    @Override
    public void setRefreshing(boolean b) {
        refreshLayout.setRefreshing(b);
    }

    @Override
    public void showContent() {
        clContainer.setVisibility(View.VISIBLE);
    }
}
