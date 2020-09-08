package com.lpb.lifecardsdk.ui.home;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.ui.base.BaseDataActivity;
import com.lpb.lifecardsdk.ui.base.BaseFragment;
import com.lpb.lifecardsdk.ui.home.adapter.HomePagerAdapter;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.MainBuyCardFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.MainScanQrCodeFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard.MainMyCardsFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycodev2.MainMyCodeV2Fragment;
import com.lpb.lifecardsdk.ui.home.fragment.setting.MainSettingFragment;
import com.lpb.lifecardsdk.util.GlideUtils;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.widget.bottomnav.AHBottomNavigation;
import com.lpb.lifecardsdk.widget.bottomnav.AHBottomNavigationAdapter;

import java.util.ArrayList;
import java.util.List;

public class LCHomeActivity extends BaseDataActivity<LCHomePresenter> implements LCHomeContract.View {
    private AHBottomNavigation bottomNav;
    private ViewPager vpContainer;
    private HomePagerAdapter homePagerAdapter;
    private int tabPosition;
    private int providerID;
    private String cardNo = "";
    private String status = "";
    private boolean showFaceVerifyInfor;
    private BroadcastReceiver backToScanQR = null;


    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_activity_home;
    }

    @Override
    protected void initView() {
        tabPosition = getIntent().getIntExtra(Constants.BundleConstants.TAB_POSITION, -1);
        providerID = getIntent().getIntExtra(Constants.BundleConstants.PROVIDER_ID, Config.DEAULT_VALUE_INT);
        cardNo = getIntent().getStringExtra(Constants.BundleConstants.CARD_NO);
        status = getIntent().getStringExtra(Constants.BundleConstants.STATUS);
        showFaceVerifyInfor = getIntent().getBooleanExtra(Constants.BundleConstants.SHOW_FACE_VERIFY_INFOR,false);
        bottomNav = findViewById(R.id.bottom_nav);
        vpContainer = findViewById(R.id.vp_container);

        initInformation();
        initBottomNav();
        initFragments();


        backToScanQR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                homePagerAdapter.getItem(3).getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        };
        LocalBroadcastManager.getInstance(LCHomeActivity.this).registerReceiver(backToScanQR,
                new IntentFilter("backToScanQR"));
    }

    private void initInformation() {
        Config.Header.setHeader(LCConfig.getChannelCode(), PresenterUtils.getIPAddress(true),
                "", PresenterUtils.getDeviceId(this), "",
                "vi", "", "android", PresenterUtils.getVersionName(),
                "123", "", "", LCConfig.getSystemCode(), 0L, "");
    }

    private void initBottomNav() {
        AHBottomNavigationAdapter navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.lifecardsdk_bottom_nav);
        navigationAdapter.setupWithBottomNavigation(bottomNav);
        bottomNav.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNav.setTitleTextSize(getResources().getDimensionPixelSize(R.dimen.lifecardsdk_sp_14), getResources().getDimensionPixelSize(R.dimen.lifecardsdk_sp_12));
        if (tabPosition != -1) {
            bottomNav.setCurrentItem(tabPosition);
        }
        bottomNav.setAccentColor(ContextCompat.getColor(this, R.color.lifecardsdk_orange));
        bottomNav.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (wasSelected) {
                    Fragment fragment = homePagerAdapter.getItem(position);
                    if (fragment instanceof MainScanQrCodeFragment) {
                        LocalBroadcastManager.getInstance(LCHomeActivity.this).sendBroadcast(new Intent("startCamera"));
                    }
                    homePagerAdapter.getItem(position).getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }

                vpContainer.setCurrentItem(position, false);
                homePagerAdapter.getItem(0).getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                homePagerAdapter.getItem(1).getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                homePagerAdapter.getItem(2).getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                homePagerAdapter.getItem(3).getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                homePagerAdapter.getItem(4).getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                Fragment fragment = homePagerAdapter.getItem(0);
                ((MainBuyCardFragment) fragment).backPressed(false);
                if (position == 3) {
                    LocalBroadcastManager.getInstance(LCHomeActivity.this).sendBroadcast(new Intent("startCamera"));
                } else {
                    LocalBroadcastManager.getInstance(LCHomeActivity.this).sendBroadcast(new Intent("stopCamera"));
                }
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (backToScanQR != null) {
            try {
                LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(backToScanQR);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initFragments() {
        List<BaseFragment> lstItems = new ArrayList<>();
        MainBuyCardFragment mainBuyCardFragment = new MainBuyCardFragment();
        Bundle b = new Bundle();
        if (providerID != Config.DEAULT_VALUE_INT) {
            b.putInt(Constants.BundleConstants.PRODUCT_ID, providerID);
            b.putBoolean(Constants.BundleConstants.IS_SHOW_SCREEN_PROVIDER, true);
        } else {
            b.putBoolean(Constants.BundleConstants.IS_SHOW_SCREEN_PROVIDER, false);
        }
        mainBuyCardFragment.setArguments(b);

        MainMyCardsFragment myCardsFragment = new MainMyCardsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleConstants.CARD_NO, cardNo);
        bundle.putString(Constants.BundleConstants.STATUS, status);
        myCardsFragment.setArguments(bundle);

        MainSettingFragment mainSettingFragment = new MainSettingFragment();
        Bundle bund = new Bundle();
        bund.putBoolean(Constants.BundleConstants.SHOW_FACE_VERIFY_INFOR, showFaceVerifyInfor);
        mainSettingFragment.setArguments(bund);


        lstItems.add(mainBuyCardFragment);
        lstItems.add(myCardsFragment);
        lstItems.add(new MainMyCodeV2Fragment());
        lstItems.add(new MainScanQrCodeFragment());
        lstItems.add(mainSettingFragment);

        homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), lstItems);
        vpContainer.setOffscreenPageLimit(lstItems.size());
        vpContainer.setAdapter(homePagerAdapter);
        if (tabPosition != -1) {
            vpContainer.setCurrentItem(tabPosition);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LocalBroadcastManager.getInstance(LCHomeActivity.this).sendBroadcast(new Intent("stopCamera"));
            }
        }, 4000);
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void initData() {
        mPresenter = new LCHomePresenter(this, this);


        SharedPreferences spGlideCache = getSharedPreferences(Constants.SharePref.GLIDE_CACHE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editGlideCache = spGlideCache.edit();

        GlideUtils.setEditGlideCache(editGlideCache);
        GlideUtils.setSpGlideCache(spGlideCache);
    }

    @Override
    protected void initAction() {
    }

    public void onBackPressed(boolean isShowBottomNav) {
        showBottomNav(isShowBottomNav);
        onBackPressed();
    }

    public void showBottomNav(boolean isShowBottomNav) {
        if (isShowBottomNav)
            bottomNav.setVisibility(View.VISIBLE);
        else bottomNav.setVisibility(View.GONE);
    }

    public void setPosView(int pos) {
        if (pos == 1 && tabPosition == 0) {
            return;
        }
        if (pos == 1 && tabPosition == 1) {
            return;
        }
        if (pos == 1 && tabPosition == 4) {
            return;
        }
        vpContainer.setCurrentItem(pos, false);
        bottomNav.setCurrentItem(pos);
    }

    @Override
    public void onBackPressed() {
        int selectedFragmentIndex = vpContainer.getCurrentItem();
        Fragment fragment = homePagerAdapter.getItem(selectedFragmentIndex);
        if (fragment instanceof MainBuyCardFragment) {
            if (((MainBuyCardFragment) fragment).backPressed(true))
                super.onBackPressed();
            else fragment.getChildFragmentManager().popBackStackImmediate();
        } else if (fragment instanceof MainMyCardsFragment) {
            if (((MainMyCardsFragment) fragment).backPressed())
                super.onBackPressed();
            else fragment.getChildFragmentManager().popBackStackImmediate();
        } else if (fragment instanceof MainMyCodeV2Fragment) {
            if (((MainMyCodeV2Fragment) fragment).backPressed())
                super.onBackPressed();
            else fragment.getChildFragmentManager().popBackStackImmediate();
        } else if (fragment instanceof MainScanQrCodeFragment) {
            if (((MainScanQrCodeFragment) fragment).backPressed())
                super.onBackPressed();
            else fragment.getChildFragmentManager().popBackStackImmediate();
        } else if (fragment instanceof MainSettingFragment) {
            if (((MainSettingFragment) fragment).backPressed())
                super.onBackPressed();
            else fragment.getChildFragmentManager().popBackStackImmediate();
        } else super.onBackPressed();
    }
}
