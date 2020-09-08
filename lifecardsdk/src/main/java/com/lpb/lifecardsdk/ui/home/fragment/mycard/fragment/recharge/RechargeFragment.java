package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.recharge;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.callback.OnKeyboardVisibilityListener;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.request.default_.DiscountRechargeRequest;
import com.lpb.lifecardsdk.data.model.request.default_.RechargeRequest;
import com.lpb.lifecardsdk.data.model.response.default_.DiscountRechargeResponse;
import com.lpb.lifecardsdk.data.model.response.default_.RechargeResponse;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.bottomsheet.BottomSheetPayMethodAPI;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.recharge.confirm.ConfirmFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard.MainMyCardsFragment;
import com.lpb.lifecardsdk.util.GlideUtils;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.TextViewUtils;
import com.lpb.lifecardsdk.widget.autofilltextview.AutofitTextView;

/**
 * Created by vannh.lvt on 24/07/2020
 */
public class RechargeFragment extends BaseDataFragment<RechargePresenter> implements RechargeContract.View, BottomSheetPayMethodAPI.BottomSheetListener {
    private ImageView imgBack, imgCard;
    private TextView tvTitleToolbar;
    private ImageView imgSpace;
    private TextView tvCardName;
    private AutofitTextView tvNumberCard;
    private TextView tvCardNumber;
    private NestedScrollView svContainer;
    private LinearLayout llDiscount, llExpireDate, llRecharge;
    private RecyclerView rvRecharge;
    private LinearLayout llListedPrice;
    private LinearLayout llPaymentPrice;
    private LinearLayout llAmount;
    private EditText edtAmount;
    private LinearLayout llRange;
    private TextView tvMin;
    private TextView tvMax;
    private TextView tvListedPrice;
    private TextView tvPaymentPrice;
    private TextView tvDiscountPrice;
    private TextView tvExpireDate;
    private Button btnPayment;

    private String code;
    private RechargeResponse rechargeResponse;
    private String priceDisplay, listedPriceDisplay;
    private Long amountDeposit, amountPayment, defRechargeID;
    private String accountNo;
    private Long min, max;
    private boolean isShowKeyboard;

    private RechargeAdapter rechargeAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_recharge;
    }

    @Override
    protected void initView() {
        imgBack = view.findViewById(R.id.imgBack);
        imgCard = view.findViewById(R.id.imgCard);
        tvNumberCard = view.findViewById(R.id.tvNumberCard);
        tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);
        imgSpace = view.findViewById(R.id.imgSpace);
        tvCardName = view.findViewById(R.id.tvCardName);
        llRecharge = view.findViewById(R.id.llRecharge);
        tvCardNumber = view.findViewById(R.id.tvCardNumber);
        tvListedPrice = view.findViewById(R.id.tvListedPrice);
        llExpireDate = view.findViewById(R.id.llExpireDate);
        llDiscount = view.findViewById(R.id.llDiscount);
        tvPaymentPrice = view.findViewById(R.id.tvPaymentPrice);
        tvDiscountPrice = view.findViewById(R.id.tvDiscountPrice);
        tvExpireDate = view.findViewById(R.id.tvExpireDate);
        btnPayment = view.findViewById(R.id.btnPayment);
        svContainer = view.findViewById(R.id.svContainer);
        rvRecharge = view.findViewById(R.id.rvRecharge);
        llListedPrice = view.findViewById(R.id.llListedPrice);
        llPaymentPrice = view.findViewById(R.id.llPaymentPrice);
        llAmount = view.findViewById(R.id.llAmount);
        edtAmount = view.findViewById(R.id.edtAmount);
        llRange = view.findViewById(R.id.llRange);
        tvMin = view.findViewById(R.id.tvMin);
        tvMax = view.findViewById(R.id.tvMax);
        tvTitleToolbar.setText(getString(R.string.lifecardsdk_recharge_title));

        rechargeAdapter = new RechargeAdapter(mActivity);
        rvRecharge.setLayoutManager(new GridLayoutManager(mActivity, 3));
        rvRecharge.setAdapter(rechargeAdapter);

    }

    @Override
    protected void initData() {
        mPresenter = new RechargePresenter(mActivity, this);
        Bundle bundle = getArguments();

        code = bundle.getString(Constants.BundleConstants.CODE);
        if (StringUtils.isEmpty(code)) {
            showError(getString(R.string.lifecardsdk_realtime_error), "");
            return;
        }
        String body = StringUtils.convertObjectToBase64(new RechargeRequest(code));
        mPresenter.ownCardRecharge(body);
    }

    @Override
    protected void initAction() {
        btnPayment.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (PresenterUtils.isNetworkConnected(mActivity)) {
                    BottomSheetPayMethodAPI bottomSheetPayMethod = new BottomSheetPayMethodAPI();
                    bottomSheetPayMethod.setmListener(RechargeFragment.this);
                    bottomSheetPayMethod.show(getChildFragmentManager(), bottomSheetPayMethod.getTag());
                } else {
                    showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
                }
            }
        });
        rechargeAdapter.setOnClickListener(new RechargeAdapter.OnClickListener() {
            @Override
            public void onClickItem(RechargeResponse.DefRechargeDto defRechargeDto) {
                amountDeposit = defRechargeDto.getListedPrice();
                amountPayment = defRechargeDto.getPrice();
                defRechargeID = defRechargeDto.getId();
                priceDisplay = defRechargeDto.getPriceDisplay();
                listedPriceDisplay = defRechargeDto.getListedPriceDisplay();

                tvListedPrice.setText(StringUtils.convertHTMLToString(StringUtils.boldString(defRechargeDto.getListedPriceDisplay()), mActivity));
                tvPaymentPrice.setText(defRechargeDto.getPriceDisplay());

                if (!StringUtils.isEmpty(defRechargeDto.getDiscountDisplay())) {
                    tvDiscountPrice.setText(StringUtils.convertHTMLToString(defRechargeDto.getDiscountDisplay(), mActivity));
                    llDiscount.setVisibility(View.VISIBLE);
                }
                if (!StringUtils.isEmpty(defRechargeDto.getExpireDate())) {
                    tvExpireDate.setText(StringUtils.convertHTMLToString(defRechargeDto.getExpireDate(), mActivity));
                    llExpireDate.setVisibility(View.VISIBLE);
                }
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowKeyboard) {
                    hideKeyboard(edtAmount);
                    return;
                }

                mActivity.onBackPressed();
            }
        });

        edtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = s.toString().trim();
                if (StringUtils.isEmpty(value)) {
                    return;
                }
                edtAmount.removeTextChangedListener(this);
                edtAmount.setText(StringUtils.formatPrice(StringUtils.formatPrice(value)));
                edtAmount.setSelection(StringUtils.formatPrice(StringUtils.formatPrice(value)).length());
                edtAmount.addTextChangedListener(this);

            }
        });

        edtAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    return;
                hideKeyboard(v);
                String value = edtAmount.getText().toString().trim();

                amountDeposit = null;
                amountPayment = null;
                priceDisplay = null;
                listedPriceDisplay = null;
                defRechargeID = null;

                llListedPrice.setVisibility(View.GONE);
                llPaymentPrice.setVisibility(View.GONE);
                llDiscount.setVisibility(View.GONE);
                llExpireDate.setVisibility(View.GONE);

                btnPayment.setTextColor(getResources().getColor(R.color.lifecardsdk_gray1));
                btnPayment.setBackgroundResource(R.drawable.lifecardsdk_round_button_gray);
                btnPayment.setEnabled(false);

                if (StringUtils.isEmpty(value)) {
                    showError(getString(R.string.lifecardsdk_recharge_empty_amount), "");
                    return;
                }
                if (min != null) {
                    if (StringUtils.formatPrice(value) < min) {
                        showError(getString(R.string.lifecardsdk_recharge_check_min), "");
                        return;
                    }
                }
                if (max != null) {
                    if (StringUtils.formatPrice(value) > max) {
                        showError(getString(R.string.lifecardsdk_recharge_check_max), "");
                        return;
                    }
                }

                DiscountRechargeRequest discountRechargeRequest = new DiscountRechargeRequest(accountNo, String.valueOf(StringUtils.formatPrice(value)));
                mPresenter.getDiscountRecharge(StringUtils.convertObjectToBase64(discountRechargeRequest));
            }
        });

        setKeyboardVisibilityListener(new OnKeyboardVisibilityListener() {
            @Override
            public void onVisibilityChanged(boolean visible) {
                isShowKeyboard = visible;
                if (!visible)
                    edtAmount.clearFocus();
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    // listener hide keyboard
    private void setKeyboardVisibilityListener(final OnKeyboardVisibilityListener onKeyboardVisibilityListener) {
        final View parentView = ((ViewGroup) mActivity.findViewById(android.R.id.content)).getChildAt(0);
        parentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            private boolean alreadyOpen;
            private final int defaultKeyboardHeightDP = 100;
            private final int EstimatedKeyboardDP = defaultKeyboardHeightDP + (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? 48 : 0);
            private final Rect rect = new Rect();

            @Override
            public void onGlobalLayout() {
                int estimatedKeyboardHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, EstimatedKeyboardDP, parentView.getResources().getDisplayMetrics());
                parentView.getWindowVisibleDisplayFrame(rect);
                int heightDiff = parentView.getRootView().getHeight() - (rect.bottom - rect.top);
                boolean isShown = heightDiff >= estimatedKeyboardHeight;

                if (isShown == alreadyOpen) {
                    Log.i("Keyboard state", "Ignoring global layout change...");
                    return;
                }
                alreadyOpen = isShown;
                onKeyboardVisibilityListener.onVisibilityChanged(isShown);
            }
        });
    }

    @Override
    public void getMethodPayment(String s) {
        if (s.equalsIgnoreCase(Constants.TypePaymentMethod.TIENMAT)) {

            if (StringUtils.isEmpty(accountNo) || StringUtils.isEmpty(priceDisplay) || StringUtils.isEmpty(listedPriceDisplay) || defRechargeID == null || amountDeposit == null || amountPayment == null) {
                showError(getString(R.string.lifecardsdk_realtime_error), "");
                return;
            }
            ConfirmFragment confirmFragment = new ConfirmFragment();
            Bundle bundle = new Bundle();

            bundle.putString("priceDisplay", priceDisplay);
            bundle.putString("listedPriceDisplay", listedPriceDisplay);
            bundle.putLong("amountDeposit", amountDeposit);
            bundle.putLong("amountPayment", amountPayment);
            bundle.putLong("defRechargeID", defRechargeID);
            bundle.putString("accountNo", accountNo);

            bundle.putSerializable(Constants.BundleConstants.RECHARGE_DATA, rechargeResponse);
            confirmFragment.setArguments(bundle);
            MainMyCardsFragment.getInstance().addFragment(confirmFragment, RechargeFragment.this);
        } else {
            showError(getString(R.string.lifecardsdk_buy_card_function_developing), "");
        }
    }

    @Override
    public void setOwnCardRecharge(RechargeResponse recharge) {
        if (recharge == null) {
            showError(getString(R.string.lifecardsdk_realtime_error), "");
            return;
        }

        rechargeResponse = recharge;

        accountNo = recharge.getAccountNo();
        if (StringUtils.isEmpty(accountNo) || recharge.getOwnCardDto() == null) {
            showError(getString(R.string.lifecardsdk_realtime_error), "");
            return;
        }

        tvCardName.setText(recharge.getOwnCardDto().getName());
        tvCardNumber.setText(StringUtils.isEmpty(recharge.getOwnCardDto().getCardNoDisplay()) ? recharge.getOwnCardDto().getCardNo() : recharge.getOwnCardDto().getCardNoDisplay());

        GlideUtils.loadImageUrl(imgCard, recharge.getOwnCardDto().getImage(), mActivity, Constants.PlaceHolderType.BACKGROUND_CARD);
        TextViewUtils.setStyleTextView(tvNumberCard, mActivity, recharge.getOwnCardDto().getStyleDto().getAppStyle(), recharge.getOwnCardDto().getStyleDto().getRgb());
        tvNumberCard.setText(StringUtils.isEmpty(recharge.getOwnCardDto().getCardNoDisplay()) ? recharge.getOwnCardDto().getCardNo() : recharge.getOwnCardDto().getCardNoDisplay());

        if (recharge.getRechargeType().equalsIgnoreCase(Constants.RECHARGE_TYPE.INPUT) || recharge.getRechargeType().equalsIgnoreCase(Constants.RECHARGE_TYPE.LADDER)) {
            llRecharge.setVisibility(View.VISIBLE);
            llRange.setVisibility(View.VISIBLE);

            min = recharge.getFromValue();
            max = recharge.getToValue();

            // check empty min max value
            if (!StringUtils.isEmpty(recharge.getFromValueDisplay()) || !StringUtils.isEmpty(recharge.getToValueDisplay())) {
                llAmount.setVisibility(View.VISIBLE);
                if (!StringUtils.isEmpty(recharge.getFromValueDisplay())) {
                    tvMin.setText(getString(R.string.lifecardsdk_recharge_amount_min, recharge.getFromValueDisplay()));
                } else {
                    tvMin.setVisibility(View.GONE);
                }
                if (!StringUtils.isEmpty(recharge.getToValueDisplay())) {
                    tvMax.setText(getString(R.string.lifecardsdk_recharge_amount_max, recharge.getToValueDisplay()));
                    if (recharge.getToValue() != null) {
                        PresenterUtils.setMaxLength(edtAmount, StringUtils.formatPrice(recharge.getToValue()).length());
                    }
                } else {
                    tvMax.setVisibility(View.GONE);
                }
            }

        } else if (recharge.getRechargeType().equalsIgnoreCase(Constants.RECHARGE_TYPE.PREDEF)) {

            if (recharge.getDefRechargeDtos() == null || recharge.getDefRechargeDtos().size() == 0) {
                showError(getString(R.string.lifecardsdk_realtime_error), "");
                return;
            }

            // show content
            llRecharge.setVisibility(View.VISIBLE);
            rvRecharge.setVisibility(View.VISIBLE);
            llListedPrice.setVisibility(View.VISIBLE);
            llPaymentPrice.setVisibility(View.VISIBLE);
            btnPayment.setTextColor(getResources().getColor(R.color.lifecardsdk_white));
            btnPayment.setBackgroundResource(R.drawable.lifecardsdk_round_button_orange);
            btnPayment.setEnabled(true);


            recharge.getDefRechargeDtos().get(0).setChecked(true);
            rechargeAdapter.setItems(recharge.getDefRechargeDtos());
            RechargeResponse.DefRechargeDto defRechargeDto = recharge.getDefRechargeDtos().get(0);

            amountDeposit = defRechargeDto.getListedPrice();
            amountPayment = defRechargeDto.getPrice();
            defRechargeID = defRechargeDto.getId();

            priceDisplay = defRechargeDto.getPriceDisplay();
            listedPriceDisplay = defRechargeDto.getListedPriceDisplay();

            tvListedPrice.setText(StringUtils.convertHTMLToString(StringUtils.boldString(defRechargeDto.getListedPriceDisplay()), mActivity));
            tvPaymentPrice.setText(defRechargeDto.getPriceDisplay());

            if (!StringUtils.isEmpty(defRechargeDto.getDiscountDisplay())) {
                tvDiscountPrice.setText(StringUtils.convertHTMLToString(defRechargeDto.getDiscountDisplay(), mActivity));
                llDiscount.setVisibility(View.VISIBLE);
            }
            if (!StringUtils.isEmpty(defRechargeDto.getExpireDate())) {
                tvExpireDate.setText(StringUtils.convertHTMLToString(defRechargeDto.getExpireDate(), mActivity));
                llExpireDate.setVisibility(View.VISIBLE);
            }

        } else {
            showError(getString(R.string.lifecardsdk_realtime_error), "");
            return;
        }

        // show content
        svContainer.setVisibility(View.VISIBLE);
        btnPayment.setVisibility(View.VISIBLE);
    }

    @Override
    public void setDiscountRecharge(DiscountRechargeResponse discountRecharge) {
        if (discountRecharge == null || discountRecharge.getDefRechargeDto() == null) {
            showError(getString(R.string.lifecardsdk_realtime_error), "");
            return;
        }

        amountDeposit = discountRecharge.getDefRechargeDto().getListedPrice();
        amountPayment = discountRecharge.getDefRechargeDto().getPrice();
        defRechargeID = discountRecharge.getDefRechargeDto().getId();
        priceDisplay = discountRecharge.getDefRechargeDto().getPriceDisplay();
        listedPriceDisplay = discountRecharge.getDefRechargeDto().getListedPriceDisplay();

        llListedPrice.setVisibility(View.VISIBLE);
        llPaymentPrice.setVisibility(View.VISIBLE);

        btnPayment.setTextColor(getResources().getColor(R.color.lifecardsdk_white));
        btnPayment.setBackgroundResource(R.drawable.lifecardsdk_round_button_orange);
        btnPayment.setEnabled(true);

        tvListedPrice.setText(StringUtils.convertHTMLToString(StringUtils.boldString(discountRecharge.getDefRechargeDto().getListedPriceDisplay()), mActivity));
        tvPaymentPrice.setText(discountRecharge.getDefRechargeDto().getPriceDisplay());

        if (!StringUtils.isEmpty(discountRecharge.getDefRechargeDto().getDiscountDisplay())) {
            tvDiscountPrice.setText(StringUtils.convertHTMLToString(discountRecharge.getDefRechargeDto().getDiscountDisplay(), mActivity));
            llDiscount.setVisibility(View.VISIBLE);
        }
        if (!StringUtils.isEmpty(discountRecharge.getDefRechargeDto().getExpireDate())) {
            tvExpireDate.setText(StringUtils.convertHTMLToString(discountRecharge.getDefRechargeDto().getExpireDate(), mActivity));
            llExpireDate.setVisibility(View.VISIBLE);
        }
    }
}
