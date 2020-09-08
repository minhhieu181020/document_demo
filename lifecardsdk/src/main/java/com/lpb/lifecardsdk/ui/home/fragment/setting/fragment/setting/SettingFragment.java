package com.lpb.lifecardsdk.ui.home.fragment.setting.fragment.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.EKYCConfig;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.constant.MobileConfig;
import com.lpb.lifecardsdk.data.model.request.default_.MobileConfigRequest;
import com.lpb.lifecardsdk.data.model.response.default_.EKYCConfigureResponse;
import com.lpb.lifecardsdk.data.model.response.default_.mobile_config.DataConfig;
import com.lpb.lifecardsdk.data.model.response.default_.mobile_config.MobileConfigResponse;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.facedetector.FaceDetectorActivity;
import com.lpb.lifecardsdk.ui.home.fragment.setting.MainSettingFragment;
import com.lpb.lifecardsdk.ui.home.fragment.setting.fragment.face_verify_infor.FaceVerifyInforFragment;
import com.lpb.lifecardsdk.ui.home.fragment.setting.fragment.setting.adapter.SettingAdapter;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vannh.lvt on 15/07/2020
 */
public class SettingFragment extends BaseDataFragment<SettingPresenter> implements SettingContract.View {
    private ImageView imgBack;
    private TextView tvTitleToolbar;
    private RecyclerView rvSettings;
    private SettingAdapter settingAdapter;
    private LinearLayout llSettings;
    private String mDesc, mTime, mNotify;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_setting;
    }

    @Override
    protected void initView() {
        imgBack = view.findViewById(R.id.imgBack);
        llSettings = view.findViewById(R.id.llSettings);
        tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);

        rvSettings = view.findViewById(R.id.rvSettings);
        rvSettings.setLayoutManager(new LinearLayoutManager(mActivity));
        settingAdapter = new SettingAdapter(mActivity);
        rvSettings.setAdapter(settingAdapter);
        tvTitleToolbar.setText(getString(R.string.lifecardsdk_common_settings));
    }

    @Override
    protected void initData() {
        mPresenter = new SettingPresenter(mActivity, this);

        if (PresenterUtils.isNetworkConnected(mActivity)) {
            MobileConfigRequest mobileConfigRequest = new MobileConfigRequest();

            List<String> configCodes = new ArrayList<>();
            configCodes.add(MobileConfig.EKYCRegister);
            configCodes.add(MobileConfig.EKYCNoRegister);

            mobileConfigRequest.setConfigCodes(configCodes);
            mobileConfigRequest.setMobilePhone(LCConfig.getPhoneNumber());

            String body = StringUtils.convertObjectToBase64(mobileConfigRequest);
            mPresenter.getDataSettings(body);
        } else {
            showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
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
        llSettings.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                showError(getString(R.string.lifecardsdk_settings_please_buy_card_after_register_face),"");
            }
        });
        settingAdapter.setOnClickListener(new SettingAdapter.OnClickListener() {
            @Override
            public void onClickItem(DataConfig item) {
                if (item.getCode().equalsIgnoreCase(MobileConfig.EKYCNoRegister)) {
                    if (EKYCConfig.isCallAPIConfigure()) {
                        startActivity(new Intent(mActivity, FaceDetectorActivity.class));
                    } else {
                        if (PresenterUtils.isNetworkConnected(mActivity)) {
                            mPresenter.getEKYCConfigure();
                        } else {
                            showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
                        }
                    }
                } else if (item.getCode().equalsIgnoreCase(MobileConfig.EKYCRegister)) {
                    FaceVerifyInforFragment faceVerifyInforFragment = new FaceVerifyInforFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.BundleConstants.TIMES, mTime);
                    bundle.putString(Constants.BundleConstants.DESC, mDesc);
                    bundle.putString(Constants.BundleConstants.NOTIFY, mNotify);
                    faceVerifyInforFragment.setArguments(bundle);
                    MainSettingFragment.getInstance().addFragment(faceVerifyInforFragment, SettingFragment.this);
                } else {
                    showError(getString(R.string.lifecardsdk_buy_card_function_developing), "");
                }
            }
        });
    }

    @Override
    public void setDataSettings(MobileConfigResponse mobileConfigResponse) {
        if (mobileConfigResponse == null || mobileConfigResponse.getDataConfigs() == null || mobileConfigResponse.getDataConfigs().size() == 0) {
            rvSettings.setVisibility(View.GONE);
            llSettings.setVisibility(View.VISIBLE);
            return;
        }
        mNotify = mobileConfigResponse.getFaceRegisteredInfo();
        mTime = mobileConfigResponse.getFaceRegisteredTime();
        mDesc = mobileConfigResponse.getFaceDescription();

        assert getArguments() != null;
        if (getArguments().getBoolean(Constants.BundleConstants.SHOW_FACE_VERIFY_INFOR, false)) {
            FaceVerifyInforFragment faceVerifyInforFragment = new FaceVerifyInforFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BundleConstants.TIMES, mTime);
            bundle.putString(Constants.BundleConstants.DESC, mDesc);
            bundle.putString(Constants.BundleConstants.NOTIFY, mNotify);
            faceVerifyInforFragment.setArguments(bundle);
            MainSettingFragment.getInstance().addFragment(faceVerifyInforFragment, SettingFragment.this);
        }

        settingAdapter.setItems(mobileConfigResponse.getDataConfigs());
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
        } catch (Exception e) {
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
