package com.lpb.lifecardsdk.ui.payment.case2.customer_infor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.ui.base.BaseDataActivity;
import com.lpb.lifecardsdk.util.StringUtils;

public class CustomerInforActivity extends BaseDataActivity<CustomerInforPresenter> implements CustomerInforContract.View {
    private RelativeLayout rlToolbar;
    private ImageView imgBack;
    private TextView tvTitleToolbar;
    private LinearLayout llContainer;
    private TextView tvRequireName;
    private EditText edtCustomerName;
    private TextView tvRequirePhoneNumber;
    private EditText edtPhoneNumber;
    private TextView tvProduceName;
    private TextView tvProductCode;
    private RadioGroup rgPaymentGateways;
    private RadioButton rbViVietApp;
    private RadioButton rbViVietWeb;
    private RadioButton rbTienMat;
    private TextView tvProductPrice;
    private Button btnContinue;

    private boolean isEmptyName, isEmptyPhone;
    private boolean isClicked = false;
    private String productCode, productName, productPrice;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_activity_customer_information_case2;
    }

    @Override
    protected void initView() {
        rlToolbar = findViewById(R.id.rlToolbar);
        imgBack = findViewById(R.id.imgBack);
        tvTitleToolbar = findViewById(R.id.tvTitleToolbar);
        llContainer = findViewById(R.id.llContainer);
        tvRequireName = findViewById(R.id.tvRequireName);
        edtCustomerName = findViewById(R.id.edtCustomerName);
        tvRequirePhoneNumber = findViewById(R.id.tvRequirePhoneNumber);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        tvProduceName = findViewById(R.id.tvProduceName);
        tvProductCode = findViewById(R.id.tvProductCode);
        rgPaymentGateways = findViewById(R.id.rgPaymentGateways);
        rbViVietApp = findViewById(R.id.rbViVietApp);
        rbViVietWeb = findViewById(R.id.rbViVietWeb);
        rbTienMat = findViewById(R.id.rbTienMat);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setEnabled(false);
        tvTitleToolbar.setText(getString(R.string.lifecardsdk_payment_customer_information));
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        mPresenter = new CustomerInforPresenter(this, this);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;

        productName = bundle.getString(Constants.BundleConstants.PRODUCT_NAME);
        productPrice = bundle.getString(Constants.BundleConstants.PRODUCT_PRICE);
        productCode = bundle.getString(Constants.BundleConstants.PRODUCT_CODE);

        tvProduceName.setText(productName);
        tvProductCode.setText(getString(R.string.lifecardsdk_buycard_card_product_code) + productCode);
        tvProductPrice.setText(productPrice);
        if (!StringUtils.isEmpty(LCConfig.getCustomerName())) {
            isEmptyName = true;
            tvRequireName.setVisibility(View.GONE);
            edtCustomerName.setFocusable(false);
            edtCustomerName.setText(LCConfig.getCustomerName());
        }
        if (!StringUtils.isEmpty(LCConfig.getPhoneNumber())) {
            isEmptyPhone = true;
            tvRequirePhoneNumber.setVisibility(View.GONE);
            edtPhoneNumber.setFocusable(false);
            edtPhoneNumber.setText(LCConfig.getPhoneNumber());
        }
    }

    @Override
    protected void initAction() {

        imgBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });

        edtCustomerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equalsIgnoreCase("")) {
                    isEmptyName = true;
                    tvRequireName.setVisibility(View.GONE);
                } else {
                    tvRequireName.setVisibility(View.VISIBLE);
                    isEmptyName = false;
                }
                setEnableButtonContinue();
            }
        });

        edtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equalsIgnoreCase("")) {
                    isEmptyPhone = true;
                    tvRequirePhoneNumber.setVisibility(View.GONE);
                } else {
                    tvRequirePhoneNumber.setVisibility(View.VISIBLE);
                    isEmptyPhone = false;
                }
                setEnableButtonContinue();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
                // dừng phần không có thông tin khách hàng

//                String customerName = edtCustomerName.getText().toString().trim();
//                String phoneNumber = edtPhoneNumber.getText().toString().trim();
//                if (!isClicked && !StringUtils.isEmpty(customerName) && !StringUtils.isEmpty(phoneNumber)) {
//                    isClicked = true;
//                    startTimer();
//                    int selectedId = rgPaymentGateways.getCheckedRadioButtonId();
//                    if (selectedId == R.id.rbTienMat) {
//                        if (StringUtils.checkPhonePattern(phoneNumber)) {
//                            LCConfig.setCustomerName(customerName);
//                            LCConfig.setPhoneNumber(phoneNumber);
//                            Intent intent = new Intent(CustomerInforActivity.this, LCPaymentActivity.class);
//
//                            Bundle bundle = new Bundle();
//                            bundle.putString(Constants.BundleConstants.PRODUCT_CODE, productCode);
//                            bundle.putString(Constants.BundleConstants.PRODUCT_NAME, productName);
//                            bundle.putString(Constants.BundleConstants.PRODUCT_PRICE, productPrice);
//                            bundle.putInt(Constants.BundleConstants.PAYMENT_GETWAYS, selectedId);
//
//                            intent.putExtras(bundle);
//                            startActivity(intent);
//                        } else {
//                            showError(getString(R.string.lifecardsdk_buy_card_customer_infor_wrong_format_phone), "");
//                        }
//                    } else {
//                        showError("Chức năng đang phát triển, bạn vui lòng thử lại sau.", "");
//                    }
//
//                }

            }
        });
    }

    private void startTimer() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                isClicked = false;
            }
        }, Config.DELAY_CLICK);
    }

    private void setEnableButtonContinue() {
        if (isEmptyName && isEmptyPhone) {
            btnContinue.setEnabled(true);
            btnContinue.setTextColor(getResources().getColor(R.color.lifecardsdk_white));
            btnContinue.setBackgroundResource(R.drawable.lifecardsdk_round_button_orange);
        } else {
            btnContinue.setTextColor(getResources().getColor(R.color.lifecardsdk_gray1));
            btnContinue.setBackgroundResource(R.drawable.lifecardsdk_round_button_gray);
            btnContinue.setEnabled(false);
        }
    }
}
