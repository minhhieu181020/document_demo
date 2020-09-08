package com.lpb.lifecardsdk.ui.bottomsheet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Function;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.data.model.response.default_.PaymentMethodResponse;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.ui.bottomsheet.adapter.PaymentMethodAdapter;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.ReqApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetPayMethodAPI extends BottomSheetDialogFragment {
    private Button btnContinue;
    private BottomSheetListener mListenner;
    private String mStatus = "";
    private RecyclerView rcPaymentMethod;
    private PaymentMethodAdapter adapter;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.LifeCardSDK_AppBottomSheetDialogTheme);
    }

    public void setmListener(BottomSheetListener mListenner) {
        this.mListenner = mListenner;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lifecardsdk_payment_method_bottom_sheet_api, container, false);
        rcPaymentMethod = view.findViewById(R.id.rcPaymentMethod);
        btnContinue = view.findViewById(R.id.btnContinue);
        progressBar = view.findViewById(R.id.progressBar);
        adapter = new PaymentMethodAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcPaymentMethod.setAdapter(adapter);
        rcPaymentMethod.setLayoutManager(linearLayoutManager);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initAction();
        if (PresenterUtils.isNetworkConnected(getActivity())) {
            callAPIMethod();
        } else {
            progressBar.setVisibility(View.GONE);
            dismiss();
            Exception.handleMessageRequestFailure(getActivity(), getActivity().getString(R.string.lifecardsdk_cant_connect_internet), "");
        }

    }

    private void callAPIMethod() {
        final String functionName = Function.FunctionName.GET_PAYMENT_METHOD;
        final String functionCode = Function.FunctionCode.GET_PAYMENT_METHOD;
        // body không cần truyền đúng
        final String body = "eyAiMSI6ICIiIH0=";
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, functionName, getActivity());

        Repository.getInstance().get_Payment_Method(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String status = Exception.checkError(response, functionName, functionCode);
                if (status.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        PaymentMethodResponse paymentMethodResponse = new Gson().fromJson(text, PaymentMethodResponse.class);
                        if (paymentMethodResponse.getData() == null || paymentMethodResponse.getData().size() == 0) {
                            dismiss();
                            Exception.handleMessageRequestFailure(getActivity(), getString(R.string.lifecardsdk_cant_fint_payment_method), functionCode);
                            return;
                        }
                        List<PaymentMethodResponse.Datum> datumList = paymentMethodResponse.getData();
                        mStatus = datumList.get(0).getCode();
                        datumList.get(0).setChecked(true);
                        adapter.setItems(datumList);
                    } catch (java.lang.Exception e) {
                        Exception.handleException(e, getActivity(), functionName, functionCode);
                        dismiss();
                    }
                    progressBar.setVisibility(View.GONE);
                } else if (status.equals(Exception.Type.KNOWN)) {
                    progressBar.setVisibility(View.GONE);
                    ResponseBase64 responseBase64 = response.body();
                    Exception.handleMessageRequestFailure(getActivity(), responseBase64.getResultDesc(), functionCode);
                    dismiss();
                } else if (status.equals(Exception.Type.UNKNOWN)) {
                    progressBar.setVisibility(View.GONE);
                    Exception.handleMessageRequestFailure(getActivity(), getActivity().getString(R.string.lifecardsdk_unknown_error), functionCode);
                    dismiss();
                } else if (status.equals(Exception.Type.SERVER)) {
                    progressBar.setVisibility(View.GONE);
                    Exception.handleMessageRequestFailure(getActivity(), getActivity().getString(R.string.lifecardsdk_sever_error), functionCode);
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                dismiss();
                Exception.handleMessageRequestFailure(t, getActivity(), functionName, functionCode);
            }
        });
    }

    private void initAction() {
//
        adapter.setOnChangeItemChecked(new PaymentMethodAdapter.OnChangeItemChecked() {
            @Override
            public void onChangeChecked(PaymentMethodResponse.Datum item) {
                mStatus = item.getCode();
            }
        });


        btnContinue.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mListenner.getMethodPayment(mStatus);
                dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public interface BottomSheetListener {
        void getMethodPayment(String s);
    }
}

