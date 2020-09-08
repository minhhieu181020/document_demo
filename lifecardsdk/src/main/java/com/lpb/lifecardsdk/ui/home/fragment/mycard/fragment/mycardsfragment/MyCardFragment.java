package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.mycardsfragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.EKYCConfig;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.response.default_.EKYCConfigureResponse;
import com.lpb.lifecardsdk.data.model.response.default_.ListCardResponseDefault;
import com.lpb.lifecardsdk.data.model.response.default_.PackageSearchResponse;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.facedetector.FaceDetectorActivity;
import com.lpb.lifecardsdk.ui.home.LCHomeActivity;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.content.adapter.BuyCardAdapter;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.permissionsacnqr.PermissionScanFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.scanqr.ScanQRFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.adapter.MyCardStillValidateAdapter;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.addcard.AddCardFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetails.CardDetailsFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.CardDetailsWaitFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard.MainMyCardsFragment;
import com.lpb.lifecardsdk.ui.provider.ProviderFragment;
import com.lpb.lifecardsdk.ui.usermanager.UserManagerFragment;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.CustomEditTextClearable;
import com.lpb.lifecardsdk.widget.recyclerview.EndlessRecyclerViewScrollListener;

import java.util.List;

import io.github.yavski.fabspeeddial.FabSpeedDial;


public class MyCardFragment extends BaseDataFragment<MyCardPresenter> implements MyCardContract.View {
    private CustomEditTextClearable edtSearch;

    private RecyclerView rvCard;
    private MyCardStillValidateAdapter adapter;
    private Button btnMyCardBuyCard;

    private RecyclerView rvCardWait;
    private BuyCardAdapter buyCardAdapter;
    private ImageView imgBack;
    private LinearLayoutManager linearLayoutManager, linearLayoutManager2;
    private LinearLayout llRvCardWait, llGuideFace;
    private RelativeLayout llbuy_card_now;
    private TextView tvTitleToolbar, tvGuideFace;
    private ImageView imgClose;
    private int pageSize = 20;
    private int pageIndex = 0;
    //    private CoordinatorLayout clContainer;
    private FabSpeedDial fabSpeedDial;
    //    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout rlmycard;
    private AppBarLayout.LayoutParams params;

    private boolean isShowGuideFace = true;
    private BroadcastReceiver changeStatusFaceVerify = null;
    private BroadcastReceiver refreshListMyCard = null;
    private int position = 0;
    private String mainParentClass;
    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_mycard2;
    }

    @Override
    protected void initView() {

        changeStatusFaceVerify = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                isShowGuideFace = false;
                llGuideFace.setVisibility(View.GONE);
            }
        };

        LocalBroadcastManager.getInstance(mActivity).registerReceiver(changeStatusFaceVerify,
                new IntentFilter("changeStatusGuideFace"));

        refreshListMyCard = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int i = intent.getIntExtra(Constants.BundleConstants.USER_NUMBER, 0);
                List<ListCardResponseDefault.OwnCardDto> ownCardDtos = adapter.getMyCardSList();
                ownCardDtos.get(position).setNumberOfShareCardUsers(i);
                adapter.notifyItemChanged(position,0);
            }
        };

        LocalBroadcastManager.getInstance(mActivity).registerReceiver(refreshListMyCard,
                new IntentFilter(Constants.Actions.REFRESH_LIST_MY_CARD));


//        clContainer = view.findViewById(R.id.clContainer);
        rvCard = view.findViewById(R.id.rvCard);
        adapter = new MyCardStillValidateAdapter(mActivity);
        linearLayoutManager = new LinearLayoutManager(mActivity);
        rvCard.setAdapter(adapter);
        rvCard.setLayoutManager(linearLayoutManager);
        imgClose = view.findViewById(R.id.imgClose);
        tvGuideFace = view.findViewById(R.id.tvGuideFace);
        llGuideFace = view.findViewById(R.id.llGuideFace);
//        rvCard.setNestedScrollingEnabled(false);

// Buy card

        rvCardWait = view.findViewById(R.id.rvCardWait);
        buyCardAdapter = new BuyCardAdapter(mActivity, true);
        linearLayoutManager2 = new LinearLayoutManager(mActivity);
        rvCardWait.setAdapter(buyCardAdapter);
        rvCardWait.setLayoutManager(linearLayoutManager2);


        llRvCardWait = view.findViewById(R.id.llRvCardWait);
        llbuy_card_now = view.findViewById(R.id.ll_buy_card_now);
        rlmycard = view.findViewById(R.id.rlmycard);

//        edtSearch = view.findViewById(R.id.edtSearch);
//        edtSearch.setFocusable(false);
        imgBack = view.findViewById(R.id.imgBack);
        btnMyCardBuyCard = view.findViewById(R.id.btnMyCardBuyCard);
        tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);
        tvTitleToolbar.setText(R.string.lifecardsdk_my_card);
        fabSpeedDial = (FabSpeedDial) view.findViewById(R.id.fab_speed_dial);
        swipeRefreshLayout = view.findViewById(R.id.main_swiperefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.lifecardsdk_orange);
//        params = (AppBarLayout.LayoutParams) llbuy_card_now.getLayoutParams();
//        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
//                | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
    }

    @Override
    protected void initData() {

        mPresenter = new MyCardPresenter(mActivity, this);
        if (PresenterUtils.isNetworkConnected(mActivity)) {
            mPresenter.getDataMyCard(LCConfig.getPhoneNumber(), "", pageIndex, pageSize, false);
        } else {
            showError(mActivity.getString(R.string.lifecardsdk_cant_connect_internet), "");
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (changeStatusFaceVerify != null) {
            try {
                LocalBroadcastManager.getInstance(mActivity).unregisterReceiver(changeStatusFaceVerify);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }

        if (refreshListMyCard != null) {
            try {
                LocalBroadcastManager.getInstance(mActivity).unregisterReceiver(refreshListMyCard);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void initAction() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (PresenterUtils.isNetworkConnected(mActivity)) {
                    mPresenter.getDataMyCard(LCConfig.getPhoneNumber(), "", 0, pageSize, false);
                    adapter.clearDataList();
                    pageIndex = 0;
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    hideLoading();
                    showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
                }

            }
        });
        fabSpeedDial.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                if (menuItem.getTitle().toString().equalsIgnoreCase(mActivity.getString(R.string.lifecardsdk_add_card))) {
                    MainMyCardsFragment.getInstance().addFragment(new AddCardFragment(), MyCardFragment.this);
                } else {
                    if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_DENIED) {
                        PermissionScanFragment permissionScanFragment = new PermissionScanFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainMyCardFragment);
                        permissionScanFragment.setArguments(bundle);
                        MainMyCardsFragment.getInstance().addFragment(permissionScanFragment, MyCardFragment.this);
                    } else {
                        ScanQRFragment scanQRFragment = new ScanQRFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainMyCardFragment);
                        scanQRFragment.setArguments(bundle);
                        MainMyCardsFragment.getInstance().addFragment(scanQRFragment, MyCardFragment.this);
                    }
                }
                return true;
            }

            @Override
            public void onMenuClosed() {

            }
        });
        rvCard.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPresenter.getDataMyCard(LCConfig.getPhoneNumber(), "", page, pageSize, false);
            }
        });
        rvCardWait.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager2) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPresenter.getDataCardWaitForPayMent(1, "", Constants.SortType.MN, LCConfig.getPhoneNumber(), page + 1, pageSize, true);
            }
        });
        btnMyCardBuyCard.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                ((LCHomeActivity) getActivity()).setPosView(0);
            }
        });


        buyCardAdapter.setOnClickListener(new BuyCardAdapter.OnClickListener() {
            @Override
            public void onClickBuyCard(PackageSearchResponse.DefCardDto item) {
                com.lpb.lifecardsdk.ui.home.fragment.buycard.carddetails_v2.CardDetailsFragment cardDetailsFragment = new com.lpb.lifecardsdk.ui.home.fragment.buycard.carddetails_v2.CardDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.BundleConstants.PRODUCT_CODE, item.getCode());
                bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainMyCardFragment);
                cardDetailsFragment.setArguments(bundle);
                MainMyCardsFragment.getInstance().addFragment(cardDetailsFragment, MyCardFragment.this);
            }
        });
        buyCardAdapter.setOnClickLogoListener(new BuyCardAdapter.OnClickLogo() {
            @Override
            public void onClickItem(PackageSearchResponse.ProviderDto item) {

                ProviderFragment providerFragment = new ProviderFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.BundleConstants.PROVIDER_ID, item.getId());
                bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainMyCardFragment);
                providerFragment.setArguments(bundle);
                MainMyCardsFragment.getInstance().addFragment(providerFragment, MyCardFragment.this);
            }
        });
        //setview();
//        edtSearch.setOnChangeItemChecked(new View.OnChangeItemChecked() {
//            @Override
//            public void onClick(View v) {
//                MainMyCardsFragment.getInstance().addFragment(new SearchMyCardsFragment(), MyCardFragment.this);
////                MainMyCardsFragment.getInstance().addFragment(new CardDetailsWaitFragment(), MyCardFragment.this);
//            }
//        });

//        adapter.setOnChangeItemChecked(new MyCardStillValidateAdapter.OnChangeItemChecked() {
//            @Override
//            public void onClick(ListCardResponseDefault.OwnCardDto item) {
//                mPresenter.onClickCardItem(item);
//                MainMyCardsFragment.getInstance().addFragment(new CardDetailsFragment(), MyCardFragment.this);
////                mActivity.onBackPressed();
//            }
//        });
        adapter.setOnClickListener(new MyCardStillValidateAdapter.OnClickListener() {
            @Override
            public void onClick(ListCardResponseDefault.OwnCardDto item,int i) {
                mPresenter.onClickCardItem(item);
                position = i;
//                MainMyCardsFragment.getInstance().addFragment(new CardDetailsFragment(), MyCardFragment.this);
            }

            @Override
            public void onClickAO(ListCardResponseDefault.OwnCardDto myCardSAO,int i) {
                position = i;
                mPresenter.onClickCardItem(myCardSAO);
                CardDetailsFragment cardInforFragment = new CardDetailsFragment();
                ListCardResponseDefault.OwnCardDto ownCardDto = myCardSAO;
                Bundle bundle1 = new Bundle();
                bundle1.putString(Constants.BundleConstants.MY_CARD_INFORMATION, ownCardDto.getCardNo());
                bundle1.putString(Constants.BundleConstants.MY_CARD_INFORMATION_DISPLAY, ownCardDto.getCardNoDisplay());
                bundle1.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainMyCardFragment);
                cardInforFragment.setArguments(bundle1);
                MainMyCardsFragment.getInstance().addFragment(cardInforFragment, MyCardFragment.this);
            }

            @Override
            public void onClickEC(ListCardResponseDefault.OwnCardDto myCardS,int i) {
                position = i;
                mPresenter.onClickCardItem(myCardS);
                CardDetailsFragment cardInforFragment = new CardDetailsFragment();
                ListCardResponseDefault.OwnCardDto OwnServiceDto = myCardS;
                Bundle bundle1 = new Bundle();
                bundle1.putString(Constants.BundleConstants.MY_CARD_INFORMATION, OwnServiceDto.getCardNo());
                bundle1.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainMyCardFragment);
                cardInforFragment.setArguments(bundle1);
                MainMyCardsFragment.getInstance().addFragment(cardInforFragment, MyCardFragment.this);

            }


            @Override
            public void onClickW(ListCardResponseDefault.OwnCardDto myCardSW,int i) {
                position = i;
                mPresenter.onClickCardItem(myCardSW);
                CardDetailsWaitFragment cardDetailsWaitFragment = new CardDetailsWaitFragment();
                ListCardResponseDefault.OwnCardDto OwnServiceDto = myCardSW;
                Bundle bundle1 = new Bundle();
                bundle1.putString(Constants.BundleConstants.MY_CARD_INFORMATION, OwnServiceDto.getCardNo());
                bundle1.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainMyCardFragment);
                cardDetailsWaitFragment.setArguments(bundle1);
                MainMyCardsFragment.getInstance().addFragment(cardDetailsWaitFragment, MyCardFragment.this);
            }

            @Override
            public void onClickUserNumber(ListCardResponseDefault.OwnCardDto ownCardDto,int i) {
                position = i;
                Integer mNumberUser = ownCardDto.getNumberOfShareCardUsers();
                String mStatusShare = ownCardDto.getCardShare();
                String mStatusCard = ownCardDto.getStatus();

                if (StringUtils.isEmpty(mStatusShare))
                    return;

                if (mStatusShare.equalsIgnoreCase(Constants.ShareStatus.N)) {
                    Exception.handleMessageRequestFailure(mActivity, getString(R.string.lifecardsdk_user_manager_cant_add_user_case_cant_share), "");
                } else if (mStatusShare.equalsIgnoreCase(Constants.ShareStatus.K)) {
                    Exception.handleMessageRequestFailure(mActivity, getString(R.string.lifecardsdk_user_manager_cant_add_user_case_cant_share_2), "");
                } else if (mStatusShare.equalsIgnoreCase(Constants.ShareStatus.Y)) {

                    UserManagerFragment userManagerFragment = new UserManagerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.BundleConstants.CARD_NUMBER, ownCardDto.getCardNo());
                    bundle.putString(Constants.BundleConstants.CARD_NAME, ownCardDto.getName());
                    bundle.putString(Constants.BundleConstants.CARD_NUMBER_DISPLAY, ownCardDto.getCardNoDisplay());
                    if (mNumberUser != null)
                        bundle.putInt(Constants.BundleConstants.USER_NUMBER, mNumberUser);
                    if (!StringUtils.isEmpty(mStatusCard)) {
                        if (mStatusCard.equalsIgnoreCase("E") || mStatusCard.equalsIgnoreCase("C")) {
                            if (mNumberUser == null || mNumberUser < 1) {
                                Exception.handleMessageRequestFailure(mActivity, getString(R.string.lifecardsdk_user_manager_cant_add_user_case_card_expire), "");
                                return;
                            } else {
                                bundle.putBoolean(Constants.BundleConstants.CAN_SHARE_CARD, false);
                            }
                        } else if (mStatusCard.equalsIgnoreCase("W")) {
                            Exception.handleMessageRequestFailure(mActivity, getString(R.string.lifecardsdk_user_manager_cant_add_user_case_wait_payment), "");
                            return;
                        } else {
                            bundle.putBoolean(Constants.BundleConstants.CAN_SHARE_CARD, true);
                        }
                    } else {
                        bundle.putBoolean(Constants.BundleConstants.CAN_SHARE_CARD, false);
                    }

                    userManagerFragment.setArguments(bundle);
                    MainMyCardsFragment.getInstance().addFragment(userManagerFragment, MyCardFragment.this);
                }
            }
        });
        adapter.setOnClickLogoListener(new MyCardStillValidateAdapter.OnClickLogo() {
            @Override
            public void onClickItem(ListCardResponseDefault.ProviderDto item) {
                ProviderFragment providerFragment = new ProviderFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.BundleConstants.PROVIDER_ID, item.getId());
                providerFragment.setArguments(bundle);
                MainMyCardsFragment.getInstance().addFragment(providerFragment, MyCardFragment.this);

            }
        });
        adapter.setOnClickOwnService(new MyCardStillValidateAdapter.onClickOwnService() {
            @Override
            public void onClickOwnService(String code, String cardNoDisplay,int i) {
                position = i;
                CardDetailsFragment cardInforFragment = new CardDetailsFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString(Constants.BundleConstants.MY_CARD_INFORMATION, code);
                bundle1.putString(Constants.BundleConstants.MY_CARD_INFORMATION_DISPLAY, cardNoDisplay);
                bundle1.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainMyCardFragment);
                cardInforFragment.setArguments(bundle1);
                MainMyCardsFragment.getInstance().addFragment(cardInforFragment, MyCardFragment.this);
            }
        });
        imgBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                mActivity.finish();
            }
        });


        imgClose.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                llGuideFace.setVisibility(View.GONE);
                isShowGuideFace = false;
                LocalBroadcastManager.getInstance(mActivity).sendBroadcast(new Intent("changeStatusGuideFace"));
            }
        });
        llGuideFace.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (EKYCConfig.isCallAPIConfigure()) {
                    startActivity(new Intent(mActivity, FaceDetectorActivity.class));
                } else {
                    if (PresenterUtils.isNetworkConnected(mActivity)) {
                        mPresenter.getEKYCConfigure();
                    } else {
                        showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
                    }
                }
            }
        });
    }

    public void stopLoadMore2() {
        rvCardWait.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager2) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
            }
        });
        buyCardAdapter.setOnLoadMore(false);
    }

    public void addDataCardWaitForPayMent(List<PackageSearchResponse.DefCardDto> packageDtos) {
        if (packageDtos.size() == 0) {
            stopLoadMore2();
        }
        buyCardAdapter.addItems(packageDtos);
    }

    @Override
    public void setDataCardWaitForPayMent(List<PackageSearchResponse.DefCardDto> packageDtos) {
        buyCardAdapter.setItems(packageDtos);

    }

    @Override
    public void addDataMyCard(List<ListCardResponseDefault.OwnCardDto> dataMyCard) {
        if (dataMyCard.size() == 0) {
            stopLoadMore();
        }
        if (dataMyCard == null) {

        } else {
            adapter.addItems(dataMyCard);
        }
    }

    @Override
    public void setDataMyCard(ListCardResponseDefault dataMyCard) {
        if (dataMyCard.getOwnCardDtos().size() == 0) {
            stopLoadMore();
        }

        if (isShowGuideFace) {
            if (!StringUtils.isEmpty(dataMyCard.getFaceRegistrationStatus())) {
                if (dataMyCard.getFaceRegistrationStatus().equalsIgnoreCase("N")) {
                    if (StringUtils.isEmpty(dataMyCard.getGuideFaceRegistration())) {
                        llGuideFace.setVisibility(View.GONE);
                    } else {
                        llGuideFace.setVisibility(View.VISIBLE);
                        tvGuideFace.setText(dataMyCard.getGuideFaceRegistration());
                    }
                } else {
                    llGuideFace.setVisibility(View.GONE);
                }
            } else {
                llGuideFace.setVisibility(View.GONE);
            }
        } else {
            llGuideFace.setVisibility(View.GONE);
        }

        adapter.setItems(dataMyCard.getOwnCardDtos());
    }

    public void stopLoadMore() {
        rvCardWait.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

            }
        });
        adapter.setOnLoadMore(false);
    }


    @Override
    public void showContent() {

    }

    @Override
    public void setView(String s) {
        if (s.equalsIgnoreCase("ListNoA")) {
//            Trường không có thẻ còn hiệu lực
            rvCard.setVisibility(View.VISIBLE);
            llbuy_card_now.setVisibility(View.GONE);
            rvCardWait.setVisibility(View.GONE);
            llRvCardWait.setVisibility(View.GONE);
            rlmycard.setVisibility(View.VISIBLE);
        } else if (s.equalsIgnoreCase("NoListAOECW")) {
//            Trường không có danh sách thẻ
            rvCard.setVisibility(View.GONE);
            rvCardWait.setVisibility(View.GONE);
            llbuy_card_now.setVisibility(View.VISIBLE);
            llRvCardWait.setVisibility(View.GONE);
            rlmycard.setVisibility(View.VISIBLE);
        } else {
//            Trường hợp còn lại có thẻ còn hiệu lực
            rvCardWait.setVisibility(View.GONE);
            rvCard.setVisibility(View.VISIBLE);
            llbuy_card_now.setVisibility(View.GONE);
            llRvCardWait.setVisibility(View.GONE);
            ((LCHomeActivity) getActivity()).setPosView(1);
            rlmycard.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setEKYCConfigure(EKYCConfigureResponse ekycConfigureResponse) {
        try {
            EKYCConfig.setCallAPIConfigure(true);
            EKYCConfig.setHeight(Integer.parseInt(ekycConfigureResponse.getHeight()));
            EKYCConfig.setWidth(Integer.parseInt(ekycConfigureResponse.getWidth()));
            EKYCConfig.setImageQuality(Integer.parseInt(ekycConfigureResponse.getImageQuality()));
            EKYCConfig.setNumberOfActions(Integer.parseInt(ekycConfigureResponse.getNumberOfActions()));
            EKYCConfig.setActions(ekycConfigureResponse.getActions());
            startActivity(new Intent(mActivity, FaceDetectorActivity.class));
        } catch (java.lang.Exception e) {
            setDefaultEKYCConfigure();
        }
    }

    @Override
    public void setDefaultEKYCConfigure() {
        EKYCConfig.setHeight(null);
        EKYCConfig.setWidth(null);
        EKYCConfig.setImageQuality(null);
        EKYCConfig.setNumberOfActions(null);
        EKYCConfig.setActions(null);

        startActivity(new Intent(mActivity, FaceDetectorActivity.class));
    }
}
