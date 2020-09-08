package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.guide;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.request.default_.GuideRequest;
import com.lpb.lifecardsdk.data.model.response.default_.BranchResponse;
import com.lpb.lifecardsdk.data.model.response.default_.GuideResponse;
import com.lpb.lifecardsdk.data.model.response.default_.ProviderDTO;
import com.lpb.lifecardsdk.data.model.restheader.Location;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.bottomsheet.adapter.BranchAdapter;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.MainBuyCardFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.MainScanQrCodeFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard.MainMyCardsFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycode.MainMyCodeFragment;
import com.lpb.lifecardsdk.ui.provider.ProviderFragment;
import com.lpb.lifecardsdk.util.GlideUtils;
import com.lpb.lifecardsdk.util.LocationUtil;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.ScreenUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.util.TextViewLinkHandler;

import java.util.List;

public class GuideFragment extends BaseDataFragment<GuidePresenter> implements GuideContract.View {
    private LinearLayout llTerms;
    private TextView tvTerms;
    private LinearLayout llProvider,ll_payment_guide;
    private ImageView imgLogo;
    private TextView tvProviderName;
    private TextView tvAddress;
    private LinearLayout llPhoneNumber;
    private TextView tvPhoneNumber;
    private LinearLayout llEmail;
    private TextView tvEmail;
    private View viewLine;
    private TextView tvGuide,tv_payment_guide;
    private LinearLayout llBranch, llProviderInfor, llContainer;
    private RecyclerView rvBranch;
    private ProgressBar pbBranch,pbProvider;

    private boolean isUserVisibleHint = true;
    private String mainParentClass, defCardCode, cardNo;
    private String type = Constants.TypeCardDetails.GUIDE;
    private BranchAdapter branchAdapter;
    private int pageIndex = 0, rowCount = 1000;
    private String providerID;
    private boolean isFirstOpenLocation, isFirstGrantLocation;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && this.isUserVisibleHint) {
            if (type.equals(Constants.TypeCardDetails.ALL)) {
                if (PresenterUtils.isNetworkConnected(mActivity)) {
                    if (StringUtils.isEmpty(mainParentClass) || (StringUtils.isEmpty(defCardCode) && StringUtils.isEmpty(cardNo))) {
                        showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                        return;
                    }
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isUserVisibleHint = false;

                            GuideRequest guideRequest = new GuideRequest();
                            guideRequest.setCardNo(cardNo);
                            guideRequest.setDefCardCode(defCardCode);
                            String body = StringUtils.convertObjectToBase64(guideRequest);
                            mPresenter.getDataGuide(body);
                        }
                    }, Config.DELAY_REQ_API);
                } else {
                    showError(mActivity.getString(R.string.lifecardsdk_cant_connect_internet), "");
                }
            }
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_mycard_guide;
    }


    @Override
    protected void initView() {
        pbBranch = view.findViewById(R.id.pbBranch);
        pbProvider =view.findViewById(R.id.pbProvider);
        llTerms = view.findViewById(R.id.llTerms);
        tvTerms = view.findViewById(R.id.tvTerms);
        llProvider = view.findViewById(R.id.llProvider);
        imgLogo = view.findViewById(R.id.imgLogo);
        llContainer = view.findViewById(R.id.llContainer);
        tvProviderName = view.findViewById(R.id.tvProviderName);
        tvAddress = view.findViewById(R.id.tvAddress);
        llPhoneNumber = view.findViewById(R.id.llPhoneNumber);
        llProviderInfor = view.findViewById(R.id.llProviderInfor);
        tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);
        llEmail = view.findViewById(R.id.llEmail);
        tvEmail = view.findViewById(R.id.tvEmail);
        viewLine = view.findViewById(R.id.viewLine);
        tvGuide = view.findViewById(R.id.tvGuide);
        llBranch = view.findViewById(R.id.llBranch);
        ll_payment_guide = view.findViewById(R.id.ll_payment_guide);
        tv_payment_guide = view.findViewById(R.id.tv_payment_guide);
        rvBranch = view.findViewById(R.id.rvBranch);

        rvBranch.setNestedScrollingEnabled(false);
        branchAdapter = new BranchAdapter(mActivity);
        rvBranch.setAdapter(branchAdapter);
        rvBranch.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @Override
    protected void initData() {
        mPresenter = new GuidePresenter(mActivity, this);
        Bundle bundle = this.getArguments();
        String terms = bundle.getString(Constants.BundleConstants.TERMS, "");
        cardNo = bundle.getString(Constants.BundleConstants.CARD_NO);
        defCardCode = bundle.getString(Constants.BundleConstants.DEFCARDCODE);
        mainParentClass = bundle.getString(Constants.BundleConstants.MAIN_PARENT_CLASS);
        type = bundle.getString(Constants.BundleConstants.TYPE);
        if (StringUtils.isEmpty(terms)) {
            llTerms.setVisibility(View.GONE);
        } else {
            llContainer.setVisibility(View.VISIBLE);
            llTerms.setVisibility(View.VISIBLE);
            tvTerms.setText(StringUtils.convertHTMLToString(terms, mActivity.getApplicationContext()));
        }

        if (type.equals(Constants.TypeCardDetails.GUIDE)) {
            if (StringUtils.isEmpty(mainParentClass) || (StringUtils.isEmpty(defCardCode) && StringUtils.isEmpty(cardNo))) {
                showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                return;
            }

            GuideRequest guideRequest = new GuideRequest();
            guideRequest.setCardNo(cardNo);
            guideRequest.setDefCardCode(defCardCode);
            String body = StringUtils.convertObjectToBase64(guideRequest);
            mPresenter.getDataGuide(body);
        }
    }

    @Override
    protected void initAction() {
        branchAdapter.setOnClickListener(new BranchAdapter.OnClickListener() {
            @Override
            public void onClickItem(BranchResponse.List item) {
                if (item != null) {
                    if (item.getLocation() != null) {
                        if (!StringUtils.isEmpty(item.getLocation().getLatitude()) && !StringUtils.isEmpty(item.getLocation().getLongitude())) {
                            PresenterUtils.openGoogleMap(item.getLocation().getLatitude() + "," + item.getLocation().getLongitude(), mActivity);
                        } else if (!StringUtils.isEmpty(item.getFullAddress())) {
                            PresenterUtils.openGoogleMap(item.getFullAddress(), mActivity);
                        } else {
                            Exception.handleMessageRequestFailure(mActivity, getString(R.string.lifecardsdk_common_cant_open_google_map), "");
                        }
                    } else if (!StringUtils.isEmpty(item.getFullAddress())) {
                        PresenterUtils.openGoogleMap(item.getFullAddress(), mActivity);
                    } else {
                        Exception.handleMessageRequestFailure(mActivity, getString(R.string.lifecardsdk_common_cant_open_google_map), "");
                    }
                } else {
                    Exception.handleMessageRequestFailure(mActivity, getString(R.string.lifecardsdk_common_cant_open_google_map), "");
                }
            }

            @Override
            public void onClickPhone(String phoneNumber) {
                PresenterUtils.callPhoneNumber(mActivity, phoneNumber);
            }
        });
    }

    @Override
    public void setDataGuide(GuideResponse dataGuide) {
        if (dataGuide != null) {
            if (dataGuide.getProviderDTOs().size() > 0) {
                final ProviderDTO providerDTO = dataGuide.getProviderDTOs().get(0);

                tvAddress.setText(providerDTO.getHeadquarterAddress());

                if (StringUtils.isEmpty(providerDTO.getHeadquarterPhone()))
                    llPhoneNumber.setVisibility(View.GONE);
                else
                    tvPhoneNumber.setText(StringUtils.underlineText(providerDTO.getHeadquarterPhone()));

                if (StringUtils.isEmpty(providerDTO.getEmail()))
                    llEmail.setVisibility(View.GONE);
                else
                    tvEmail.setText(StringUtils.underlineText(providerDTO.getEmail()));

                llEmail.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        try {
                            startActivity(PresenterUtils.openEmailApp(providerDTO.getEmail()));
                        } catch (java.lang.Exception ignored) {

                        }
                    }
                });

                llPhoneNumber.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        PresenterUtils.callPhoneNumber(mActivity, providerDTO.getHeadquarterPhone());
                    }
                });
                if (StringUtils.isEmpty(providerDTO.getUsageGuide())) {
                    tvGuide.setVisibility(View.GONE);
                    viewLine.setVisibility(View.GONE);
                    tvGuide.setText("");
                } else {
                    tvGuide.setVisibility(View.VISIBLE);
                    viewLine.setVisibility(View.VISIBLE);
                    tvGuide.setText(StringUtils.convertHTMLToString("<ul>" + providerDTO.getUsageGuide() + "</ul>", mActivity));
                }
                llProviderInfor.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        ProviderFragment providerFragment = new ProviderFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constants.BundleConstants.PROVIDER_ID, providerDTO.getId());
                        bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
                        providerFragment.setArguments(bundle);
                        switch (mainParentClass) {
                            case Constants.ParentClass.MainBuyCardFragment:
                                MainBuyCardFragment.getInstance().addFragment(providerFragment, getParentFragment());
                                break;
                            case Constants.ParentClass.MainMyCardFragment:
                                MainMyCardsFragment.getInstance().addFragment(providerFragment, getParentFragment());
                                break;
                            case Constants.ParentClass.MainMyCodeFragment:
                                MainMyCodeFragment.getInstance().addFragment(providerFragment, getParentFragment());
                                break;
                            case Constants.ParentClass.MainScanQrCodeFragment:
                                MainScanQrCodeFragment.getInstance().addFragment(providerFragment, getParentFragment());
                                break;
                            default:
                                showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                                break;
                        }
                    }
                });
                tvGuide.setMovementMethod(new TextViewLinkHandler() {
                    @Override
                    public void onLinkClick(String url) {
                        if (url.startsWith("https://providernumber.")) {
                            String phoneNumber = url.substring(23);
                            PresenterUtils.callPhoneNumber(mActivity, phoneNumber);
                        } else if (url.startsWith("https://phonenumber.")) {
                            String phoneNumber = url.substring(20);
                            PresenterUtils.callPhoneNumber(mActivity, phoneNumber);
                        }
                    }
                });
                tvProviderName.setText(providerDTO.getName());

                GlideUtils.loadImageUrl(imgLogo, providerDTO.getLogo(), mActivity, Constants.PlaceHolderType.LOGO_PROVIDER);


                if (StringUtils.isEmpty(providerDTO.getId().toString())){
                    showError(getString(R.string.lifecardsdk_realtime_error), "");
                }else {
                    providerID = providerDTO.getId().toString();
                    if (PresenterUtils.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, mActivity)) {
                        if (!PresenterUtils.isLocationEnabled(mActivity)) {
                            isFirstOpenLocation = true;
                            ScreenUtils.displayLocationSettingsRequest(mActivity.getApplicationContext(), mActivity);
                        } else {
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
                }


                llContainer.setVisibility(View.VISIBLE);
                llProvider.setVisibility(View.VISIBLE);
            } else {
                llProvider.setVisibility(View.GONE);
            }

        } else {
            llProvider.setVisibility(View.GONE);
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
                mPresenter.getDataBranch("", "", providerID, pageIndex, rowCount);
                Log.e("BranchFragment: ", "Co quyen nhung tu choi bat vi tri trong dialog");
            } else {
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
                mPresenter.getDataBranch("", "", providerID, pageIndex, rowCount);
            }
        }
    }


    @Override
    public void setDataBranch(List<BranchResponse.List> items) {

        if (items != null && items.size() > 0) {
            branchAdapter.setItems(items);
            llContainer.setVisibility(View.VISIBLE);
            llBranch.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void hideProgressBar(String type) {
        if (type.equalsIgnoreCase(Constants.BRANCH)){
            pbBranch.setVisibility(View.GONE);
        }else if (type.equalsIgnoreCase(Constants.PROVIDER)){
            pbProvider.setVisibility(View.GONE);
        }
    }
}
