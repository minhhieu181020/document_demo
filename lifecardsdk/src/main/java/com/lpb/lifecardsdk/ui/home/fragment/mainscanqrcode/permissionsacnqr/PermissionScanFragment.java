package com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.permissionsacnqr;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.MainScanQrCodeFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.scanqr.ScanQRFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard.MainMyCardsFragment;

public class PermissionScanFragment extends BaseDataFragment<PermissionScanPresenter> implements PermissionScanContract.View {
    private Button permessioncamera;
    private ImageView imgBack;
    private String mainParentClass;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_permission_sacn_qr;
    }

    @Override
    protected void initView() {
        permessioncamera = view.findViewById(R.id.permessioncamera);
        imgBack = view.findViewById(R.id.imgBack);
    }

    @Override
    protected void initData() {
        Bundle bundle = this.getArguments();
        assert bundle != null;
        mainParentClass = bundle.getString(Constants.BundleConstants.MAIN_PARENT_CLASS);
    }

    @Override
    protected void initAction() {
//        permessioncamera.setOnChangeItemChecked(new View.OnChangeItemChecked() {
//            @Override
//            public void onClick(View view) {
//                addFragment();
//            }
//        });
//        imgBack.setOnChangeItemChecked(new View.OnChangeItemChecked() {
//            @Override
//            public void onClick(View view) {
//                mActivity.finish();
//            }
//        });
    }

    private void addFragment() {
        ScanQRFragment scanQRFragment = new ScanQRFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
        scanQRFragment.setArguments(bundle);
        switch (mainParentClass) {
            case Constants.ParentClass.MainMyCardFragment:
                MainMyCardsFragment.getInstance().addFragment(scanQRFragment, PermissionScanFragment.this);
                break;
            case Constants.ParentClass.MainScanQrCodeFragment:
                MainScanQrCodeFragment.getInstance().addFragment(scanQRFragment, PermissionScanFragment.this);
                break;
            default:
                showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                break;
        }


//        Fragment fragment = new ScanQRFragment();
//        FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
//        transaction.add(R.id.flContainer2, fragment).commit();
    }

}
