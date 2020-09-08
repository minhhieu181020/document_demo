package com.lpb.lifecardsdk.ui.home.fragment.mycodev2.fragment.qrpersonal;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Function;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.request.default_.QrPersonalRequestDefault;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.data.model.response.default_.QrPersonalResponseDefault;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.zxing.BarcodeEncoder;
import com.lpb.lifecardsdk.widget.zxing.zxing.barcodescaner.BarcodeFormat;
import com.lpb.lifecardsdk.widget.zxing.zxing.barcodescaner.MultiFormatWriter;
import com.lpb.lifecardsdk.widget.zxing.zxing.barcodescaner.WriterException;
import com.lpb.lifecardsdk.widget.zxing.zxing.common.BitMatrix;

import java.nio.charset.StandardCharsets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QrPersonalFragment extends BaseDataFragment<QrPersonalPresenter> implements QrPersonalContract.ViewModel {
    private SwipeRefreshLayout mainSwiperefresh;
    private NestedScrollView nsvContainer;
    private LinearLayout llMycodeFragment;
    private TextView tvName;
    private TextView tvPhoneNumber;
    private ImageView imgGenerateQR;
    private LinearLayout lltimer;
    private TextView tvTimeCount;
    private LinearLayout lltimeout;
    private TextView tvContent;
    private int i = 180;
    private int i1 = 179;
    private int i2 = 181;


    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_qr_personal;
    }

    @Override
    protected void initView() {
        mainSwiperefresh = view.findViewById(R.id.main_swiperefresh);
        nsvContainer = view.findViewById(R.id.nsvContainer);
        llMycodeFragment = view.findViewById(R.id.llMycodeFragment);
        tvName = view.findViewById(R.id.tvName);
        tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);
        imgGenerateQR = view.findViewById(R.id.imgGenerateQR);
        lltimer = view.findViewById(R.id.lltimer);
        tvTimeCount = view.findViewById(R.id.tvTimeCount);
        lltimeout = view.findViewById(R.id.lltimeout);
        tvContent = view.findViewById(R.id.tvContent);

        tvContent.setText(StringUtils.convertHTMLToString(mActivity.getString(R.string.lifecardsdk_my_code_conten), mActivity));
        tvName.setText(LCConfig.getCustomerName());
        tvPhoneNumber.setText(LCConfig.getPhoneNumber());
        mainSwiperefresh = view.findViewById(R.id.main_swiperefresh);
        mainSwiperefresh.setColorSchemeResources(R.color.lifecardsdk_orange);
    }

    @Override
    protected void initData() {
        mPresenter = new QrPersonalPresenter(mActivity, this);
        if (PresenterUtils.isNetworkConnected(mActivity)) {
            perform_action();
        } else {
            hideLoading();
            showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
        }
    }

    @Override
    protected void initAction() {
        mainSwiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (PresenterUtils.isNetworkConnected(mActivity)) {
                    call_perform_action();
                    mainSwiperefresh.setRefreshing(false);
                } else {
                    mainSwiperefresh.setRefreshing(false);
                    hideLoading();
                    showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
                }

            }
        });
    }

    private void perform_action() {

        final String functionName = Function.FunctionName.GENERATE_QR_FRAGMENT;
        final String functionCode = Function.FunctionCode.GENERATE_QR_FRAGMENT;

        final Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                i--;
                if (i <= -1) {
                    lltimer.setVisibility(View.GONE);
                    lltimeout.setVisibility(View.VISIBLE);
                    if (i == -2) {
                        i = i2;
                    }
                } else if (i == i1) {

                    lltimeout.setVisibility(View.GONE);
                    lltimer.setVisibility(View.VISIBLE);
                    RequestBase64 requestBase64 = new RequestBase64();
                    requestBase64.setBody(StringUtils.convertObjectToBase64(new QrPersonalRequestDefault("", LCConfig.getPhoneNumber(), null)));
                    requestBase64.setRestHeader(Config.Header.getHeader());
                    Repository.getInstance().getDataQrPersonal(requestBase64).enqueue(new Callback<ResponseBase64>() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                            if (response.body() != null && response.isSuccessful()) {
                                byte[] data = Base64.decode(response.body().getBody(), Base64.DEFAULT);
                                try {
                                    String text = new String(data, StandardCharsets.UTF_8);
                                    QrPersonalResponseDefault model = new Gson().fromJson(text, QrPersonalResponseDefault.class);
                                    GenerateQR(model.getToken());
//                                    i = 180;
                                    i = model.getTimeToken();
                                    i1 = i;
                                    i2 = i + 1;
//
                                    Log.e("MyCodeQr", "onResponse: " + model.getToken());
//                                    Toast.makeText(mActivity, "" + model.getToken(), Toast.LENGTH_SHORT).show();
                                } catch (java.lang.Exception e) {
                                    Exception.handleException(e, mActivity, functionName, functionCode);
                                    hideLoading();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBase64> call, Throwable t) {
                            hideLoading();
                            Exception.handleMessageRequestFailure(t, mActivity, functionName, functionCode);
                        }
                    });

                }
                tvTimeCount.setText(String.valueOf(i));
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);
    }

    private void GenerateQR(String string) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(string, BarcodeFormat.QR_CODE, 600, 600);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imgGenerateQR.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void call_perform_action() {

        final String functionName = Function.FunctionName.GENERATE_QR_FRAGMENT;
        final String functionCode = Function.FunctionCode.GENERATE_QR_FRAGMENT;
        RequestBase64 requestBase64 = new RequestBase64();
        requestBase64.setBody(StringUtils.convertObjectToBase64(new QrPersonalRequestDefault("", LCConfig.getPhoneNumber(), null)));
        requestBase64.setRestHeader(Config.Header.getHeader());
        Repository.getInstance().getDataQrPersonal(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                if (response.body() != null && response.isSuccessful()) {
                    byte[] data = Base64.decode(response.body().getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, StandardCharsets.UTF_8);
                        QrPersonalResponseDefault model = new Gson().fromJson(text, QrPersonalResponseDefault.class);
                        GenerateQR(model.getToken());
//                        Toast.makeText(mActivity, "" + model.getToken(), Toast.LENGTH_SHORT).show();
                        i = model.getTimeToken();
                    } catch (java.lang.Exception e) {
                        Exception.handleException(e, mActivity, functionName, functionCode);
                        hideLoading();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                hideLoading();
                Exception.handleMessageRequestFailure(t, mActivity, functionName, functionCode);
            }
        });
    }
}
