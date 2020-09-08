package com.lpb.lifecardsdk.ui.home.fragment.setting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.setting.fragment.setting.SettingFragment;

/**
 * Created by vannh.lvt on 17/07/2020
 */
public class MainSettingFragment extends BaseDataFragment<MainSettingPresenter> implements MainSettingContract.View {
    private static MainSettingFragment instance = null;

    public static MainSettingFragment getInstance() {
        return instance;
    }

    @Override
    protected int getLayoutId() {
        instance = this;
        return R.layout.lifecardsdk_fragment_main_setting;
    }

    @Override
    protected void initView() {

        Fragment fragment = new SettingFragment();
        Bundle bundle = new Bundle();
        assert getArguments() != null;
        bundle.putBoolean(Constants.BundleConstants.SHOW_FACE_VERIFY_INFOR, getArguments().getBoolean(Constants.BundleConstants.SHOW_FACE_VERIFY_INFOR, false));
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.flSetting, fragment).commit();

    }

    @Override
    protected void initData() {
        mPresenter = new MainSettingPresenter(mActivity, this);
    }

    @Override
    protected void initAction() {

    }

    public void addFragment(Fragment newFragment, Fragment oldFragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.flSetting, newFragment);
        fragmentTransaction.hide(oldFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public boolean backPressed() {
        boolean status = false;
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.flSetting);
        if (fragment instanceof SettingFragment) {
            status = true;
        }
        return status;
    }
}
