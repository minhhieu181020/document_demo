package com.lpb.lifecard;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.request.default_.AreaRequest;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.data.model.response.default_.InitPaymentResponse;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.ReqApiUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.CustomToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LCPaymentActivity extends AppCompatActivity {
    private Button btnSuccess;
    private Button btnFail;
    private TextView tvOrdersID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        btnSuccess = findViewById(R.id.btnSuccess);
        btnFail = findViewById(R.id.btnFail);
        tvOrdersID = findViewById(R.id.tvOrdersID);

        btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                successPayment();
            }
        });
        btnFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                failPayment();
            }
        });


        Bundle bundle = getIntent().getExtras();

        String orderNo = bundle.getString(LCConfig.ORDER_NO);
        String productName = bundle.getString(LCConfig.PRODUCT_NAME);
        String productCode = bundle.getString(LCConfig.PRODUCT_CODE);
        Integer productPrice = bundle.getInt(LCConfig.PRODUCT_PRICE);
        tvOrdersID.append(orderNo + "\n tên sản phầm: " + productName + "\n mã sản phẩm: " + productCode + "\n giá sản phầm: " + productPrice.toString());


        queryOrder(orderNo);
    }

    private void queryOrder(String orderNo) {

        QueryOrderRequest request = new QueryOrderRequest(orderNo);
        String body = StringUtils.convertObjectToBase64(request);
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, "", this);

        LienVietRetrofit.getInstance().queryOrder(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String status = Exception.checkError(response, "queryOrder", "");
                if (status.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        QueryOrderResponse queryOrderResponse = new Gson().fromJson(text, QueryOrderResponse.class);
                        paymentOrder(queryOrderResponse.getAmount(), queryOrderResponse.getOrderNo(), queryOrderResponse.getPackageNo());
                    } catch (java.lang.Exception e) {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString(LCConfig.RESULT_DESC, getString(com.lpb.lifecardsdk.R.string.lifecardsdk_sever_error));
                        intent.putExtras(bundle);

                        setResult(LCConfig.FAIL, intent);
                        finish();
                    }
                } else if (status.equals(Exception.Type.KNOWN)) {
                    ResponseBase64 responseBase64 = response.body();
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString(LCConfig.RESULT_DESC, responseBase64.getResultDesc());
                    intent.putExtras(bundle);

                    setResult(LCConfig.FAIL, intent);
                    finish();
                } else if (status.equals(Exception.Type.UNKNOWN)) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString(LCConfig.RESULT_DESC, getString(com.lpb.lifecardsdk.R.string.lifecardsdk_sever_error));
                    intent.putExtras(bundle);

                    setResult(LCConfig.FAIL, intent);
                    finish();
                } else if (status.equals(Exception.Type.SERVER)) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString(LCConfig.RESULT_DESC, getString(com.lpb.lifecardsdk.R.string.lifecardsdk_sever_error));
                    intent.putExtras(bundle);

                    setResult(LCConfig.FAIL, intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(LCConfig.RESULT_DESC, getString(com.lpb.lifecardsdk.R.string.lifecardsdk_sever_error));
                intent.putExtras(bundle);

                setResult(LCConfig.FAIL, intent);
                finish();
            }
        });
    }

    private void paymentOrder(final Integer amount, String orderNo, String packageNo) {

        PaymentRequest request = new PaymentRequest(amount, packageNo, orderNo, "123");
        String body = StringUtils.convertObjectToBase64(request);
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, "", this);

        LienVietRetrofit.getInstance().paymentOrder(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String status = Exception.checkError(response, "paymentOrder", "");
                if (status.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        PaymentResponse paymentResponse = new Gson().fromJson(text, PaymentResponse.class);
                        if (paymentResponse.getOrderStatus().equals("0")) {

                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString(LCConfig.TOTAL_AMOUNT, StringUtils.formatPrice(amount));
                            intent.putExtras(bundle);

                            setResult(LCConfig.SUCCESS, intent);
                            finish();
                        } else {

                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString(LCConfig.RESULT_DESC, paymentResponse.getResultDesc());
                            intent.putExtras(bundle);

                            setResult(LCConfig.FAIL, intent);
                            finish();
                        }
                    } catch (java.lang.Exception e) {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString(LCConfig.RESULT_DESC, getString(com.lpb.lifecardsdk.R.string.lifecardsdk_sever_error));
                        intent.putExtras(bundle);

                        setResult(LCConfig.FAIL, intent);
                        finish();
                    }
                } else if (status.equals(Exception.Type.KNOWN)) {
                    ResponseBase64 responseBase64 = response.body();
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString(LCConfig.RESULT_DESC, responseBase64.getResultDesc());
                    intent.putExtras(bundle);

                    setResult(LCConfig.FAIL, intent);
                    finish();
                } else if (status.equals(Exception.Type.UNKNOWN)) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString(LCConfig.RESULT_DESC, getString(com.lpb.lifecardsdk.R.string.lifecardsdk_sever_error));
                    intent.putExtras(bundle);

                    setResult(LCConfig.FAIL, intent);
                    finish();
                } else if (status.equals(Exception.Type.SERVER)) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString(LCConfig.RESULT_DESC, getString(com.lpb.lifecardsdk.R.string.lifecardsdk_sever_error));
                    intent.putExtras(bundle);

                    setResult(LCConfig.FAIL, intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(LCConfig.RESULT_DESC, getString(com.lpb.lifecardsdk.R.string.lifecardsdk_sever_error));
                intent.putExtras(bundle);

                setResult(LCConfig.FAIL, intent);
                finish();
            }
        });
    }

    public void failPayment() {
        String resultDesc = "Tài khoản không đủ tiền để thực hiện giao dịch"; // lý do thất bại

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(LCConfig.RESULT_DESC, resultDesc);
        intent.putExtras(bundle);

        setResult(LCConfig.FAIL, intent);
        finish();

    }

    public void successPayment() {
        String totalAmount = "2.500.000 VNĐ";//tổng tiền(bắt buộc)(có định dạng 1.000.000 VNĐ)

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(LCConfig.TOTAL_AMOUNT, totalAmount);
        intent.putExtras(bundle);

        setResult(LCConfig.SUCCESS, intent);
        finish();


    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        setResult(LCConfig.CANCEL, intent);
        finish();


    }


    public void backtoHome(View view) {
        Intent intent = new Intent();
        setResult(LCConfig.BACK_TO_HOME, intent);
        finish();
    }
}
