package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage.bottomsheet;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.Function;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.request.default_.QrPersonalRequestDefault;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.data.model.response.default_.MyCardDetailsWaitResponseDefault;
import com.lpb.lifecardsdk.data.model.response.default_.QrPersonalResponseDefault;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.zxing.BarcodeEncoder;
import com.lpb.lifecardsdk.widget.zxing.zxing.barcodescaner.BarcodeFormat;
import com.lpb.lifecardsdk.widget.zxing.zxing.barcodescaner.MultiFormatWriter;
import com.lpb.lifecardsdk.widget.zxing.zxing.barcodescaner.WriterException;
import com.lpb.lifecardsdk.widget.zxing.zxing.common.BitMatrix;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCodeQr extends BottomSheetDialogFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.LifeCardSDK_AppBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lifecardsdk_mycode_bottom_sheet, container, false);
        return view;
    }

    TextView tv;
    private int i = 180;
    private int i1 = 179;
    private int i2 = 181;
    private LinearLayout lltimer, lltimeout;
    private ImageView imgGenerateQR;
    private TextView tvNamePackage;
    private String CodeCard;
    private String NamDefService;
    private String CardNo = "";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv = view.findViewById(R.id.tvTimeCount);
        lltimer = view.findViewById(R.id.lltimer);
        lltimeout = view.findViewById(R.id.lltimeout);
        imgGenerateQR = view.findViewById(R.id.imgGenerateQR);
        tvNamePackage = view.findViewById(R.id.tvNamePackage);

        MyCardDetailsWaitResponseDefault.OwnServiceDto data = (MyCardDetailsWaitResponseDefault.OwnServiceDto) getArguments().getSerializable(Constants.BundleConstants.SEE_QR_CODE);

        MyCardDetailsWaitResponseDefault datawait = (MyCardDetailsWaitResponseDefault) getArguments().getSerializable(Constants.BundleConstants.SEE_QR_CODE_W);
        try {
            CodeCard = getArguments().getString(Constants.BundleConstants.CODECARD);
            NamDefService = getArguments().getString(Constants.BundleConstants.PRODUCT_NAME);
            CardNo = getArguments().getString(Constants.BundleConstants.CARD_NO);
        } catch (java.lang.Exception e) {

        }
        if (data != null) {
            tvNamePackage.setText(data.getNamDefService());
            perform_action(CardNo, LCConfig.getPhoneNumber(), data.getCode().toString());
            Log.e("QrPersonal", " " + data.getCode().toString());
        } else if (datawait != null) {
            tvNamePackage.setText(datawait.getOwnCardDto().getName());
            perform_action_w(LCConfig.getChannelCode() + ";CARD_WAIT;" + datawait.getOwnCardDto().getTransactionCode());
//            GenerateQR(datawait.getOwnCardDto().getDefCardCode().toString());
            GenerateQR(LCConfig.getChannelCode() + ";CARD_WAIT;" + datawait.getOwnCardDto().getTransactionCode());
            Log.e("QrPersonal23", "" + LCConfig.getChannelCode() + ";CARD_WAIT;" + datawait.getOwnCardDto().getTransactionCode());

        } else {
            tvNamePackage.setText(NamDefService);
            perform_action(CardNo, LCConfig.getPhoneNumber(), CodeCard);
        }

//        perform_action();
    }

    private void getDataQr(final String mobilePhone, final String code) {
        final String functionName = Function.FunctionName.GET_DATA_QR;
        final String functionCode = Function.FunctionCode.GET_DATA_QR;
        RequestBase64 requestBase64 = new RequestBase64();
        requestBase64.setBody(StringUtils.convertObjectToBase64(new QrPersonalRequestDefault("", LCConfig.getPhoneNumber(), null)));
        requestBase64.setRestHeader(Config.Header.getHeader());
        Repository.getInstance().getDataQrPersonal(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String s = Exception.checkError(response, functionName, functionCode);
                if (s.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, "UTF-8");
                        QrPersonalResponseDefault model = new Gson().fromJson(text, QrPersonalResponseDefault.class);
                        if (model != null) {
//                            perform_action(model.getToken(), mobilePhone, code);
                            Log.e("MyCodeQr", "onResponse: " + model.getToken());
                        } else {
                        }
                    } catch (java.lang.Exception e) {
                        Exception.handleException(e, getActivity(), functionName, functionCode);
                    }
                } else if (s.equals(Exception.Type.KNOWN)) {
                    Exception.handleMessageRequestFailure(getActivity(), getActivity().getString(R.string.lifecardsdk_card_invalid), "");
                    dismiss();
                } else if (s.equals(Exception.Type.UNKNOWN)) {
                    Exception.handleMessageRequestFailure(getActivity(), getActivity().getString(R.string.lifecardsdk_unknown_error), "");
                    dismiss();
                } else if (s.equals(Exception.Type.SERVER)) {
                    Exception.handleMessageRequestFailure(getActivity(), getActivity().getString(R.string.lifecardsdk_sever_error), "");
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                Exception.handleMessageRequestFailure(t, getActivity(), functionName, functionCode);
            }
        });

    }

    private void perform_action(final String cardNo, final String mobilePhone, final String code) {
        final String functionName = Function.FunctionName.GENERATE_QR_BOTTOM;
        final String functionCode = Function.FunctionCode.GENERATE_QR_BOTTOM;

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
                    requestBase64.setBody(StringUtils.convertObjectToBase64(new QrPersonalRequestDefault(cardNo, LCConfig.getPhoneNumber(), code)));
                    requestBase64.setRestHeader(Config.Header.getHeader());
                    Log.e("APIQR", "getCardMyCode: " + requestBase64.getBody());

                    Repository.getInstance().getDataQrPersonal(requestBase64).enqueue(new Callback<ResponseBase64>() {
                        @Override
                        public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                            String s = Exception.checkError(response, functionName, functionCode);
                            if (s.equals(Exception.Type.SUCCESS)) {
                                ResponseBase64 responseBase64 = response.body();
                                byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                                try {
                                    String text = new String(data, "UTF-8");
                                    QrPersonalResponseDefault model = new Gson().fromJson(text, QrPersonalResponseDefault.class);
                                    if (data != null) {
                                        GenerateQR(model.getToken());
//                                        i = 180;
                                        i = model.getTimeToken();
                                        i1 = i;
                                        i2 = i + 1;
                                        Log.e("MyCodeQr", "onResponse: " + model.getToken());
                                    }
                                } catch (java.lang.Exception e) {
                                    Exception.handleException(e, getActivity(), functionName, functionCode);
                                }
                            } else if (s.equals(Exception.Type.KNOWN)) {
                                ResponseBase64 responseBase64 = response.body();
                                Exception.handleMessageRequestFailure(getActivity(), responseBase64.getResultDesc(), "");
                                dismiss();
                            } else if (s.equals(Exception.Type.UNKNOWN)) {
                                Exception.handleMessageRequestFailure(getActivity(), getString(R.string.lifecardsdk_unknown_error), "");
                            } else if (s.equals(Exception.Type.SERVER)) {
                                Exception.handleMessageRequestFailure(getActivity(), getString(R.string.lifecardsdk_sever_error), "");

                            }
                        }


                        @Override
                        public void onFailure(Call<ResponseBase64> call, Throwable t) {
                            Exception.handleMessageRequestFailure(t, getActivity(), functionName, functionCode);
                        }
                    });

                }
                tv.setText(String.valueOf(i));
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);
    }

    private void perform_action_w(final String s) {
        final Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                i--;
                if (i <= -1) {
                    lltimer.setVisibility(View.GONE);
                    lltimeout.setVisibility(View.VISIBLE);
                    GenerateQR(s);
                    if (i == -1) {
                        i = 181;
                    }
                } else if (i == 179) {
                    GenerateQR(s);
                    lltimeout.setVisibility(View.GONE);
                    lltimer.setVisibility(View.VISIBLE);
                }
                tv.setText(String.valueOf(i));
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);
    }

    private void GenerateQR(String string) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(string, BarcodeFormat.QR_CODE, 400, 400);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imgGenerateQR.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
