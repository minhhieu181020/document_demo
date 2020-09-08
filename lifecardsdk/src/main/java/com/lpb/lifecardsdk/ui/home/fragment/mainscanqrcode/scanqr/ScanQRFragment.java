package com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.scanqr;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.ListServicePaymentQrBillResponse;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.base.DialogClickListener;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.carddetails_v2.CardDetailsFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.MainScanQrCodeFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.qrcodepayment.QrCodePaymentFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard.MainMyCardsFragment;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.ScreenUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.zxing.ZXingScannerView;
import com.lpb.lifecardsdk.widget.zxing.zxing.barcodescaner.Result;


public class ScanQRFragment extends BaseDataFragment<ScanQRPresenter>
        implements ScanQRContract.View, ActivityCompat.OnRequestPermissionsResultCallback, ZXingScannerView.ResultHandler {
    private ImageView imgBack, imgBack2;
    private RelativeLayout rlScanView;
    private ZXingScannerView scanView;
    private LinearLayout llTurnOnFlash;
    private LinearLayout llTurnOffFlash;
    private LinearLayout llOpenGallery;
    private RelativeLayout rlOpenCamera;
    private Button btnSettings;
    private boolean isOpenDialogGrantCamera, isInitCamera, isFirstInitCamera, isScanWithImage;
    private boolean isOpenSettingImage;
    private String mainParentClass;
    private BroadcastReceiver startCamera = null;
    private BroadcastReceiver stopCamera = null;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_scan_qr;
    }

    @Override
    protected void initView() {
        imgBack = view.findViewById(R.id.imgBack);
        imgBack2 = view.findViewById(R.id.imgBack2);
        TextView tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);
        rlScanView = view.findViewById(R.id.rlScanView);
        scanView = view.findViewById(R.id.scanView);
        llTurnOnFlash = view.findViewById(R.id.llTurnOnFlash);
        llTurnOffFlash = view.findViewById(R.id.llTurnOffFlash);
        llOpenGallery = view.findViewById(R.id.llOpenGallery);
        rlOpenCamera = view.findViewById(R.id.rlOpenCamera);
        btnSettings = view.findViewById(R.id.permessioncamera);
        tvTitleToolbar.setText(getString(R.string.lifecardsdk_Scan_the_code));

        initBroadCast();
        initAction();
    }

    private void initBroadCast() {

        startCamera = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (isInitCamera) {
                    initQRCodeView();
                } else {
                    Log.e("scanQR: ", "initQRCodeView khi co quyen cap thu cong");
                    initPermission();
                }

            }
        };
        stopCamera = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                onPause();
            }
        };

        LocalBroadcastManager.getInstance(mActivity).registerReceiver(startCamera,
                new IntentFilter("startCamera"));

        LocalBroadcastManager.getInstance(mActivity).registerReceiver(stopCamera,
                new IntentFilter("stopCamera"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (startCamera != null) {
            try {
                LocalBroadcastManager.getInstance(mActivity).unregisterReceiver(startCamera);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (stopCamera != null) {
            try {
                LocalBroadcastManager.getInstance(mActivity).unregisterReceiver(stopCamera);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void initData() {
        mPresenter = new ScanQRPresenter(mActivity, this);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        mainParentClass = bundle.getString(Constants.BundleConstants.MAIN_PARENT_CLASS);
    }

    @Override
    protected void initAction() {
        imgBack.setOnClickListener(new View.OnClickListener() {
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
        llTurnOnFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFirstInitCamera)
                    return;
                llTurnOffFlash.setVisibility(View.VISIBLE);
                llTurnOnFlash.setVisibility(View.GONE);
                scanView.setFlash(true);
            }
        });

        llTurnOffFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFirstInitCamera)
                    return;
                llTurnOffFlash.setVisibility(View.GONE);
                llTurnOnFlash.setVisibility(View.VISIBLE);
                scanView.setFlash(false);
            }
        });
        llOpenGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (!PresenterUtils.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, mActivity)) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Config.PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
                    } else {
                        pickImage();
                    }
                } else {
                    pickImage();
                }
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenUtils.openPermissionSettings(mActivity);
            }
        });
    }

    private void pickImage() {
        Intent pickImageIntent = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImageIntent, Config.REQUEST_PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.REQUEST_PICK_IMAGE) {
            Log.e("Scan QR: ", "REQUEST_PICK_IMAGE");
            isScanWithImage = false;
            if (Activity.RESULT_OK == resultCode) {
                Uri imageUri = data.getData();
                String imgPath = mPresenter.getPathFromUri(imageUri);
                if (imgPath != null && !StringUtils.isEmpty(imgPath)) {
                    String token = mPresenter.decodeQRImage(imgPath);
                    if (StringUtils.isEmpty(token)) {
                        initQRCodeView();
                        showError("Mã QR không đúng định dạng", "");
                        return;
                    }
                    Log.e("scannerView", token);
                    String TokenQR = "";
                    String CODE = "";
                    try {
                        String[] token2 = token.split(";");
                        TokenQR = token2[1];
                        CODE = token2[2];
                        Log.e("scannerView", CODE);

                    } catch (Exception e) {
                        TokenQR = token;
                    }
                    if (PresenterUtils.isNetworkConnected(mActivity)) {
                        //            Todu
                        if (TokenQR.equalsIgnoreCase("PAYMENT")) {
                            if (PresenterUtils.isNetworkConnected(mActivity)) {
                                mPresenter.getServicesByQRBIll(token);
                            } else {
                                hideLoading();
                                showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
                                initQRCodeView();
                            }
                        } else {
                            mPresenter.getDataCardInformation(CODE);
                        }
                    } else {
                        hideLoading();
                        showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
                        initQRCodeView();
                    }
                }
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (isScanWithImage) {
            if (PresenterUtils.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, mActivity)) {
                pickImage();
            } else {
                if (isOpenSettingImage) {
                    initQRCodeView();
                    Log.e("scanQR: ", "resumeCameraPreview khi trở về từ màn hình cài đặt");
                }
                isOpenSettingImage = false;
            }
            return;
        }
        Log.e("scanQR: ", "Bo qua kiem tra cap quyen");
        if (PresenterUtils.checkPermission(Manifest.permission.CAMERA, mActivity)) {
            // isInitCamera check xem da init chua -> neu chua init thi init
            // isFirstInitCamera tranh viec vua initQRCodeView vua resumeCameraPreview
            if (isInitCamera) {
                if (isFirstInitCamera) {
                    isFirstInitCamera = false;
                } else {
                    Log.e("scanQR: ", "resumeCameraPreview khi co quyen");
                    initQRCodeView();
                }
            } else {
                Log.e("scanQR: ", "initQRCodeView khi co quyen cap thu cong");
                initQRCodeView();
            }
            rlScanView.setVisibility(View.VISIBLE);
            rlOpenCamera.setVisibility(View.GONE);
        } else {
            if (isOpenDialogGrantCamera) {
                // tranh viec chua hoi quyen da bat man hinh cai dat
                // isOpenDialogGrantCamera = dialog cap quyen camera ca thanh cong va that bai(kiem tra xem bat dialog chua)
                rlScanView.setVisibility(View.GONE);
                rlOpenCamera.setVisibility(View.VISIBLE);
                Log.e("scanQR: ", "Mo man hinh cai dat khi cap quyen khong thanh cong");
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (PresenterUtils.checkPermission(Manifest.permission.CAMERA, mActivity)) {
            scanView.stopCamera();
            Log.e("scanQR: ", "stopCamera");
        }
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!PresenterUtils.checkPermission(Manifest.permission.CAMERA, mActivity)) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, Config.PERMISSION_REQUEST_CAMERA);
            } else {
                Log.e("scanQR: ", "initQRCodeView khi co quyen tu dau");
                initQRCodeView();
                isFirstInitCamera = true;
            }
        } else {
            Log.e("scanQR: ", "initQRCodeView khi android duoi 6.0");
            initQRCodeView();
            isFirstInitCamera = true;
        }
    }


    private void initQRCodeView() {
        isInitCamera = true;
        //scanView.setFlash(false);
        scanView.setResultHandler(this);
        scanView.startCamera();
        scanView.setAspectTolerance(0.2f);
        scanView.setAutoFocus(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Config.PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initQRCodeView();
                    isFirstInitCamera = true;
                    Log.e("scanQR: ", "initQRCodeView khi cap quyen dialog");
                    rlScanView.setVisibility(View.VISIBLE);
                    rlOpenCamera.setVisibility(View.GONE);
                } else {
                    rlScanView.setVisibility(View.GONE);
                    rlOpenCamera.setVisibility(View.VISIBLE);
                }
            }
            isOpenDialogGrantCamera = true;
        } else if (requestCode == Config.PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImage();
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        isScanWithImage = true;
                        showDialogConfirm(mActivity.getString(R.string.lifecardsdk_scan_qr_grant_permission), mActivity.getString(R.string.lifecardsdk_scan_qr_grant_permission_desc), mActivity.getString(R.string.lifecardsdk_common_cancel), mActivity.getString(R.string.lifecardsdk_settings), new DialogClickListener() {
                            @Override
                            public void close() {
                                Log.e("scanQR: ", "resumeCameraPreview khi hủy dialog cấp quyền bộ nhớ");
                                initQRCodeView();
                                isScanWithImage = false;
                            }

                            @Override
                            public void allow() {
                                isOpenSettingImage = true;
                                ScreenUtils.openPermissionSettings(mActivity);
                            }
                        }, false, false);
                    }
                }
            }
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        scanView.stopCamera();
        Log.e("scannerView", rawResult.getText());
        if (PresenterUtils.isNetworkConnected(mActivity)) {
//            Todu
            String TokenQR = "";
            String CODE = "";

            try {
                String[] token2 = rawResult.getText().split(";");
                TokenQR = token2[1];
                CODE=token2[2];
                Log.e("scannerView", CODE);

            } catch (Exception e) {
                TokenQR = rawResult.getText();
                Log.e("scannerView", CODE);

            }
            if (PresenterUtils.isNetworkConnected(mActivity)) {
                //            Todu
                if (TokenQR.equalsIgnoreCase("PAYMENT")) {
                    if (PresenterUtils.isNetworkConnected(mActivity)) {
                        mPresenter.getServicesByQRBIll(rawResult.getText());
                    } else {
                        hideLoading();
                        showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
                        initQRCodeView();
                    }
                } else {
                    mPresenter.getDataCardInformation(CODE);
                }
            } else {
                hideLoading();
                showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
                initQRCodeView();
            }
        }
    }

    @Override
    public void setview(boolean b, String cardCode) {
        if (b) {
            CardDetailsFragment cardDetailsFragment = new CardDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BundleConstants.PRODUCT_CODE, cardCode);
            bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
            cardDetailsFragment.setArguments(bundle);
            switch (mainParentClass) {
                case Constants.ParentClass.MainMyCardFragment:
                    MainMyCardsFragment.getInstance().addFragment(cardDetailsFragment, ScanQRFragment.this);
                    break;
                case Constants.ParentClass.MainScanQrCodeFragment:
                    MainScanQrCodeFragment.getInstance().addFragment(cardDetailsFragment, ScanQRFragment.this);
                    break;
                default:
                    showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                    break;
            }
        } else {
            showError(getString(R.string.scan_qr_invalid_code), "");
            initQRCodeView();
        }

    }

    @Override
    public void setviewServicesByQRBIll(boolean b, ListServicePaymentQrBillResponse listservicePaymentQrBillResponse) {
        if (b) {
            QrCodePaymentFragment qrCodePaymentFragment = new QrCodePaymentFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.BundleConstants.QRPAYMENT, listservicePaymentQrBillResponse);
            bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
            qrCodePaymentFragment.setArguments(bundle);
            switch (mainParentClass) {
                case Constants.ParentClass.MainMyCardFragment:
                    MainMyCardsFragment.getInstance().addFragment(qrCodePaymentFragment, ScanQRFragment.this);
                    break;
                case Constants.ParentClass.MainScanQrCodeFragment:
                    MainScanQrCodeFragment.getInstance().addFragment(qrCodePaymentFragment, ScanQRFragment.this);
                    break;
                default:
                    showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                    break;
            }
        } else {
            showError(getString(R.string.scan_qr_invalid_code), "");
            initQRCodeView();
        }
    }

}