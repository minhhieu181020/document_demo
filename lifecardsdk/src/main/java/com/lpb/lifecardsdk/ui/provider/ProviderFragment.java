package com.lpb.lifecardsdk.ui.provider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.adapter.ViewPagerWithTabAdapter;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.ProviderResponse;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.base.BaseFragment;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.MainBuyCardFragment;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.search.search.SearchFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.MainScanQrCodeFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard.MainMyCardsFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycode.MainMyCodeFragment;
import com.lpb.lifecardsdk.ui.provider.fragment.branch.BranchFragment;
import com.lpb.lifecardsdk.ui.provider.fragment.cardlist.CardListFragment;
import com.lpb.lifecardsdk.ui.provider.fragment.introduce.IntroduceFragment;
import com.lpb.lifecardsdk.util.GlideUtils;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import java.util.LinkedHashMap;

public class ProviderFragment extends BaseDataFragment<ProviderPresenter> implements ProviderContract.View, AppBarLayout.OnOffsetChangedListener {
    private TabLayout tabLayout;
    private ViewPager vpContainer;
    private RelativeLayout llToolbar;
    private LinearLayout llToolbar2;
    private AppBarLayout ablProvider;
    private ImageView imgBackground;
    private ImageView imgLogo;
    private CoordinatorLayout clContainer;
    private TextView tvProviderName, tvProviderName2;
    private LinearLayout llBack;
    private ImageView imgBack2;
    private EditText edtSearch;
    private EditText edtSearch2;

    private Integer providerID;
    private String mainParentClass;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_provider;
    }

    @Override
    protected void initView() {
        tabLayout = view.findViewById(R.id.tabLayout);
        vpContainer = view.findViewById(R.id.vpContainer);
        ablProvider = view.findViewById(R.id.ablProvider);
        llToolbar = view.findViewById(R.id.llToolbar);
        llToolbar2 = view.findViewById(R.id.llToolbar2);
        imgBackground = view.findViewById(R.id.imgBackground);
        imgLogo = view.findViewById(R.id.imgLogo);
        clContainer = view.findViewById(R.id.clContainer);
        tvProviderName = view.findViewById(R.id.tvProviderName);
        tvProviderName2 = view.findViewById(R.id.tvProviderName2);
        llBack = view.findViewById(R.id.llBack);
        imgBack2 = view.findViewById(R.id.imgBack2);
        edtSearch = view.findViewById(R.id.edtSearch);
        edtSearch.setFocusable(false);
        edtSearch2 = view.findViewById(R.id.edtSearch2);
        edtSearch2.setFocusable(false);
        ablProvider.addOnOffsetChangedListener(this);
    }


    @Override
    protected void initData() {
        mPresenter = new ProviderPresenter(mActivity, this);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        providerID = bundle.getInt(Constants.BundleConstants.PROVIDER_ID, Config.DEAULT_VALUE_INT);
        mainParentClass = bundle.getString(Constants.BundleConstants.MAIN_PARENT_CLASS);
        if (StringUtils.isEmpty(mainParentClass) || providerID == Config.DEAULT_VALUE_INT) {
            showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
            return;
        }
        if (PresenterUtils.isNetworkConnected(mActivity)) {
            showLoading(true);
            mPresenter.getDataProvider(Integer.toString(providerID));
        } else {
            showError(mActivity.getString(R.string.lifecardsdk_cant_connect_internet), "");
        }
    }

    @Override
    protected void initAction() {
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });
        imgBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });
        edtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragment searchFragment = new SearchFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.BundleConstants.PROVIDER_ID, providerID);
                bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
                searchFragment.setArguments(bundle);
                switch (mainParentClass) {
                    case Constants.ParentClass.MainBuyCardFragment:
                        MainBuyCardFragment.getInstance().addFragment(searchFragment, ProviderFragment.this);
                        break;
                    case Constants.ParentClass.MainMyCardFragment:
                        MainMyCardsFragment.getInstance().addFragment(searchFragment, ProviderFragment.this);
                        break;
                    case Constants.ParentClass.MainMyCodeFragment:
                        MainMyCodeFragment.getInstance().addFragment(searchFragment, ProviderFragment.this);
                        break;
                    case Constants.ParentClass.MainScanQrCodeFragment:
                        MainScanQrCodeFragment.getInstance().addFragment(searchFragment, ProviderFragment.this);
                        break;
                    default:
                        showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                        break;
                }

            }
        });
        edtSearch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragment searchFragment = new SearchFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.BundleConstants.PROVIDER_ID, providerID);
                bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
                searchFragment.setArguments(bundle);
                switch (mainParentClass) {
                    case Constants.ParentClass.MainBuyCardFragment:
                        MainBuyCardFragment.getInstance().addFragment(searchFragment, ProviderFragment.this);
                        break;
                    case Constants.ParentClass.MainMyCardFragment:
                        MainMyCardsFragment.getInstance().addFragment(searchFragment, ProviderFragment.this);
                        break;
                    case Constants.ParentClass.MainMyCodeFragment:
                        MainMyCodeFragment.getInstance().addFragment(searchFragment, ProviderFragment.this);
                        break;
                    case Constants.ParentClass.MainScanQrCodeFragment:
                        MainScanQrCodeFragment.getInstance().addFragment(searchFragment, ProviderFragment.this);
                        break;
                    default:
                        showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                        break;
                }
            }
        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        ViewGroup.LayoutParams params = llToolbar.getLayoutParams();
        llToolbar.setLayoutParams(params);
        int absVerticalOffset = Math.abs(i);
        int totalScrollRange = appBarLayout.getTotalScrollRange();
        if (absVerticalOffset >= totalScrollRange / 1.05) {
            llToolbar.setVisibility(View.VISIBLE);
        } else {
            llToolbar.setVisibility(View.INVISIBLE);
        }
        BranchFragment.getInstance().enableSwipeRefreshLayout(absVerticalOffset == 0);
        CardListFragment.getInstance().enableSwipeRefreshLayout(absVerticalOffset == 0);
//        params.height = (int) Math.round(((double) absVerticalOffset / totalScrollRange) * (int) (51 * mActivity.getResources().getDisplayMetrics().density));
        params.height = (int) Math.round(((double) absVerticalOffset / totalScrollRange) * llToolbar2.getHeight());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setDataProvider(ProviderResponse data) {
        clContainer.setVisibility(View.VISIBLE);
        initFragments(providerID, mainParentClass, data);
        edtSearch.setText(mActivity.getString(R.string.lifecardsdk_search_in) + data.getList().get(0).getName());
        edtSearch2.setText(mActivity.getString(R.string.lifecardsdk_search_in) + data.getList().get(0).getName());
        tvProviderName.setText(data.getList().get(0).getName());
        tvProviderName2.setText(data.getList().get(0).getName());
        GlideUtils.loadImageUrl(imgLogo, data.getList().get(0).getLogo(), mActivity, Constants.PlaceHolderType.LOGO_PROVIDER);
        GlideUtils.loadImageUrl(imgBackground, data.getList().get(0).getImage(), mActivity, Constants.PlaceHolderType.BACKGROUND_PROVIDER);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initFragments(Integer providerID, String mainParentClass, ProviderResponse data) {
        LinkedHashMap<BaseFragment, String> lstFragments = new LinkedHashMap<>();

        IntroduceFragment introduceFragment = new IntroduceFragment();
        Bundle bundleProvider = new Bundle();
        bundleProvider.putSerializable(Constants.BundleConstants.PROVIDER, data);
        introduceFragment.setArguments(bundleProvider);
        lstFragments.put(introduceFragment, getString(R.string.lifecardsdk_provider_introduce));

        BranchFragment branchFragment = new BranchFragment();
        Bundle bundleBranch = new Bundle();
        bundleBranch.putInt(Constants.BundleConstants.PROVIDER_ID, providerID);
        branchFragment.setArguments(bundleBranch);
        lstFragments.put(branchFragment, getString(R.string.lifecardsdk_provider_branch));

        CardListFragment cardListFragment = new CardListFragment();
        Bundle bundleCardList = new Bundle();
        bundleCardList.putInt(Constants.BundleConstants.PROVIDER_ID, providerID);
        bundleCardList.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
        cardListFragment.setArguments(bundleCardList);
        lstFragments.put(cardListFragment, getString(R.string.lifecardsdk_provider_card_list));

        ViewPagerWithTabAdapter adapter = new ViewPagerWithTabAdapter(getChildFragmentManager());
        adapter.setItems(lstFragments);
        vpContainer.setAdapter(adapter);
        vpContainer.setOffscreenPageLimit(lstFragments.size());
        tabLayout.setupWithViewPager(vpContainer);
    }
}
