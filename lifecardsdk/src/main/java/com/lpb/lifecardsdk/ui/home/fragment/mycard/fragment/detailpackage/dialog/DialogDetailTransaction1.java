package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.TransactionHistoryResponseDefault;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.StringUtils;

public class DialogDetailTransaction1 extends DialogFragment {
    private ImageView imgBack;
    private TextView tvTitle;
    private TextView tvUseTo;
    private TextView tvProviderName;
    private TextView tvProviderAddress;
    private TextView tvamountUnit;
    private TextView tvEntryTimeDetail;
    private TextView tvtransactionId;
    private TextView tvamountUnitall;
    private TextView tvavailableAfterUnit, tvTitleToolbar;

    private View viewUsedBy;
    private LinearLayout llUsedBy;
    private TextView tvUsedBy;
    private View viewPhoneNumber;
    private LinearLayout llPhoneNumber;
    private TextView tvPhoneNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.LifeCardSDK_AppThemeFullScreen);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lifecardsdk_dialog_detatil_transaction2, container, false);
        imgBack = view.findViewById(R.id.imgBack);
        tvUseTo = view.findViewById(R.id.tvUseTo);
        tvProviderName = view.findViewById(R.id.tvProviderName);
        tvProviderAddress = view.findViewById(R.id.tvProviderAddress);
        tvamountUnit = view.findViewById(R.id.tvamountUnit);
        tvEntryTimeDetail = view.findViewById(R.id.tvEntryTimeDetail);
        tvtransactionId = view.findViewById(R.id.tvtransactionId);
        tvamountUnitall = view.findViewById(R.id.tvamountUnitall);
        tvavailableAfterUnit = view.findViewById(R.id.tvavailableAfterUnit);
        tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);
        viewUsedBy = view.findViewById(R.id.viewUsedBy);
        llUsedBy = view.findViewById(R.id.llUsedBy);
        tvUsedBy = view.findViewById(R.id.tvUsedBy);
        viewPhoneNumber = view.findViewById(R.id.viewPhoneNumber);
        llPhoneNumber = view.findViewById(R.id.llPhoneNumber);
        tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);
        tvTitleToolbar.setText(R.string.lifecardsdk_Use_the_service);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TransactionHistoryResponseDefault.AccountEntryDto data = (TransactionHistoryResponseDefault.AccountEntryDto) getArguments().getSerializable(Constants.BundleConstants.MY_CARD_DETAIL_PACKGE_HISTORY);
        if (data.getTransType().equalsIgnoreCase(Constants.TransType.PAYMENT)) {
            tvTitleToolbar.setText(R.string.lifecardsdk_Refund_of_service);
            tvUseTo.setText(R.string.lifecardsdk_my_card_From_the_supplier);
            tvamountUnit.setTextColor(getResources().getColor(R.color.lifecardsdk_green));
        } else if (data.getTransType().equalsIgnoreCase(Constants.TransType.USAGE)) {
            tvTitleToolbar.setText(R.string.lifecardsdk_Use_the_service);
            tvUseTo.setText(R.string.lifecardsdk_my_card_Use_at);
            tvamountUnit.setTextColor(getResources().getColor(R.color.lifecardsdk_red));
        } else {
            tvTitleToolbar.setText(R.string.lifecardsdk_Refund_of_service);
            tvUseTo.setText(R.string.lifecardsdk_my_card_From_the_supplier);
        }
        tvProviderName.setText(data.getProviderName());
        if (data.getProviderAddress() == null) {
            tvProviderAddress.setVisibility(View.GONE);
        } else {
            tvProviderAddress.setText(data.getProviderAddress());
        }
        if (!StringUtils.isEmpty(data.getCustomerName())) {
            viewUsedBy.setVisibility(View.VISIBLE);
            llUsedBy.setVisibility(View.VISIBLE);
            tvUsedBy.setText(data.getCustomerName());
        }
        if (!StringUtils.isEmpty(data.getMobilePhone())) {
            viewPhoneNumber.setVisibility(View.VISIBLE);
            llPhoneNumber.setVisibility(View.VISIBLE);
            tvPhoneNumber.setText(StringUtils.underlineText(data.getMobilePhone()));

            tvPhoneNumber.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    PresenterUtils.callPhoneNumber(getActivity(), data.getMobilePhone());
                }
            });
        }
        tvamountUnit.setText(data.getAmountUnit());
        tvEntryTimeDetail.setText("Thời gian: " + data.getEntryTimeDetail());
        tvtransactionId.setText(data.getTransactionId().toString());

        String str = data.getAmountUnit();
        char[] ch = new char[50];
        str.getChars(1, str.length(), ch, 0);
        tvamountUnitall.setText(String.valueOf(ch));
        tvavailableAfterUnit.setText(data.getAvailableAfterUnit());
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
