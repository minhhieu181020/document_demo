package com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.carddetails_v2.CardDetailsFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.permissionsacnqr.PermissionScanFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.qrcodedeatailpayment.QrCodePaymentDetailFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.qrcodepayment.QrCodePaymentFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.scanqr.ScanQRFragment;

public class MainScanQrCodeFragment extends BaseDataFragment<MainScanQrCodePresenter> implements MainScanQrCodeContract.View {
    private static MainScanQrCodeFragment instance = null;

    public static MainScanQrCodeFragment getInstance() {
        return instance;
    }

    @Override
    protected int getLayoutId() {
        instance = this;
        return R.layout.lifecardsdk_fragment_main_scanqrcode;
    }

    @Override
    protected void initView() {
            GetDataFragment();
    }

    public void GetDataFragment() {
        Fragment fragment = new ScanQRFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainScanQrCodeFragment);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.flContainer2, fragment).commit();
    }

    public void claerData() {
        getActivity().getFragmentManager().popBackStack();
    }

    public void addFragment(Fragment newFragment, Fragment oldFragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.flContainer2, newFragment);
        fragmentTransaction.hide(oldFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void addFragment(Fragment newFragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flContainer2, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public boolean backPressed() {
        boolean status = false;
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.flContainer2);
        if (fragment instanceof ScanQRFragment || fragment instanceof PermissionScanFragment) {
            status = true;
        }
        if (fragment instanceof QrCodePaymentFragment) {
            LocalBroadcastManager.getInstance(mActivity).sendBroadcast(new Intent("startCamera"));
        }
        if (fragment instanceof QrCodePaymentDetailFragment) {
            LocalBroadcastManager.getInstance(mActivity).sendBroadcast(new Intent("backToScanQR"));
            LocalBroadcastManager.getInstance(mActivity).sendBroadcast(new Intent("startCamera"));
        }
        if (fragment instanceof CardDetailsFragment) {
            LocalBroadcastManager.getInstance(mActivity).sendBroadcast(new Intent("startCamera"));
        }
        return status;
    }

    @Override
    protected void initData() {
        mPresenter = new MainScanQrCodePresenter(mActivity, this);
    }

    @Override
    protected void initAction() {

    }

}
