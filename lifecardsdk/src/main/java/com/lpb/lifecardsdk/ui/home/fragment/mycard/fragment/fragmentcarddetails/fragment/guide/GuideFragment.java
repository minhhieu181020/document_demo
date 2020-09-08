package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetails.fragment.guide;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.request.default_.GuideRequest;
import com.lpb.lifecardsdk.data.model.response.default_.GuideResponse;
import com.lpb.lifecardsdk.data.model.response.default_.ProviderDTO;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.bottomsheet.BranchFragment;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.MainBuyCardFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.MainScanQrCodeFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetails.fragment.guide.adapter.ProviderPageAdapter;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard.MainMyCardsFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycode.MainMyCodeFragment;
import com.lpb.lifecardsdk.ui.provider.ProviderFragment;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.ScreenUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vannh.lvt on 17/06/2020
 */
public class GuideFragment extends BaseDataFragment<GuidePresenter> implements GuideContract.View {
    private AppBarLayout appBarLayout;
    private LinearLayout llProvider;
    private CirclePageIndicator cpiViewPager;
    private ViewPager vpContent;
    private ProviderPageAdapter providerPageAdapter;
    private ImageView imgBack;
    private Button btnBranch;
    private LinearLayout llContent;

    private String mMainParentClass;
    private Integer providerID;
    private List<ProviderDTO> providerDTOS;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_guide;
    }

    @Override
    protected void initView() {
        appBarLayout = view.findViewById(R.id.appBarLayout);
        llProvider = view.findViewById(R.id.llProvider);
        cpiViewPager = view.findViewById(R.id.cpiViewPager);
        vpContent = view.findViewById(R.id.vpContent);
        imgBack = view.findViewById(R.id.imgBack);
        btnBranch = view.findViewById(R.id.btnBranch);
        llContent = view.findViewById(R.id.llContent);
        TextView tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);
        tvTitleToolbar.setText(getString(R.string.lifecardsdk_common_guide));

        providerDTOS = new ArrayList<>();
        providerPageAdapter = new ProviderPageAdapter(providerDTOS, mActivity,R.layout.lifecardsdk_item_page_provider_2);
        cpiViewPager.setRadius(ScreenUtils.sdpToPixel(mActivity, 4, 4));
        vpContent.setAdapter(providerPageAdapter);
        cpiViewPager.setViewPager(vpContent);
    }

    @Override
    protected void initData() {
        mPresenter = new GuidePresenter(mActivity, this);
        Bundle bundle = getArguments();

        String cardNo = bundle.getString(Constants.BundleConstants.CARD_NO);
        String defCardCode = bundle.getString(Constants.BundleConstants.DEFCARDCODE);
        mMainParentClass = bundle.getString(Constants.BundleConstants.MAIN_PARENT_CLASS);
        if (StringUtils.isEmpty(cardNo) && StringUtils.isEmpty(defCardCode)|| StringUtils.isEmpty(mMainParentClass)) {
            showError(getString(R.string.lifecardsdk_realtime_error), "");
        } else {
            GuideRequest guideRequest = new GuideRequest();
            guideRequest.setCardNo(cardNo);
            guideRequest.setDefCardCode(defCardCode);

            String body = StringUtils.convertObjectToBase64(guideRequest);
            showLoading(true);
            mPresenter.getDataGuide(body);
        }
    }

    @Override
    protected void initAction() {
        providerPageAdapter.setOnClickLogoListener(new ProviderPageAdapter.OnClickListener() {
            @Override
            public void onClickPhone(String phoneNumber) {
                PresenterUtils.callPhoneNumber(mActivity, phoneNumber);
            }

            @Override
            public void onClickEmail(String email) {
                try {
                    startActivity(PresenterUtils.openEmailApp(email));
                } catch (Exception ignored) {

                }
            }

            @Override
            public void onClickLogoProvider(Integer providerID) {
                ProviderFragment providerFragment = new ProviderFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.BundleConstants.PROVIDER_ID, providerID);
                bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mMainParentClass);
                providerFragment.setArguments(bundle);
                switch (mMainParentClass) {
                    case Constants.ParentClass.MainBuyCardFragment:
                        MainBuyCardFragment.getInstance().addFragment(providerFragment, GuideFragment.this);
                        break;
                    case Constants.ParentClass.MainMyCardFragment:
                        MainMyCardsFragment.getInstance().addFragment(providerFragment, GuideFragment.this);
                        break;
                    case Constants.ParentClass.MainMyCodeFragment:
                        MainMyCodeFragment.getInstance().addFragment(providerFragment, GuideFragment.this);
                        break;
                    case Constants.ParentClass.MainScanQrCodeFragment:
                        MainScanQrCodeFragment.getInstance().addFragment(providerFragment, GuideFragment.this);
                        break;
                    default:
                        showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                        break;
                }
            }
        });
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if (providerDTOS.size() == 0)
                    providerID = null;
                else
                    providerID = providerDTOS.get(position).getId();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        imgBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mActivity.onBackPressed();
            }
        });

        btnBranch.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (providerID == null) {
                    showError(getString(R.string.lifecardsdk_realtime_error), "");
                } else {
                    BranchFragment branchFragment = new BranchFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.BundleConstants.PROVIDER_ID, providerID);
                    branchFragment.setArguments(bundle);
                    branchFragment.show(getChildFragmentManager(), branchFragment.getTag());
                }
            }
        });
    }

    @Override
    public void setDataGuide(GuideResponse dataGuide) {
        if (dataGuide != null) {
            if (dataGuide.getProviderDTOs().size() > 0) {
                providerID = dataGuide.getProviderDTOs().get(0).getId();
                providerDTOS = dataGuide.getProviderDTOs();
                if (dataGuide.getProviderDTOs().size() > 1) {
                    cpiViewPager.setVisibility(View.VISIBLE);
                } else {
                    cpiViewPager.setVisibility(View.GONE);
                }
                providerPageAdapter.changeData(dataGuide.getProviderDTOs());
                llContent.setVisibility(View.VISIBLE);
                btnBranch.setVisibility(View.VISIBLE);
            } else {
                llContent.setVisibility(View.GONE);
                btnBranch.setVisibility(View.GONE);
            }

        } else {
            btnBranch.setVisibility(View.GONE);
            llContent.setVisibility(View.GONE);
        }
    }
}
