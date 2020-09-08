package com.lpb.lifecardsdk.ui.bottomsheet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.ui.paymentguide.LCPaymentGuideActivity;
import com.lpb.lifecardsdk.widget.RelativeRadioGroup;

public class PaymentMethodFragment extends BottomSheetDialogFragment {
    private RelativeRadioGroup rgPaymentMethod;
    private RadioButton rbViViet;
    private RadioButton rbSacombank;
    private RadioButton rbTheQuocTe;
    private RadioButton rbATMNoiDia;
    private RadioButton rbTienMat;
    private TextView tvHuongDan;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.LifeCardSDK_AppBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lifecardsdk_payment_method_bottom_sheet, container, false);
        rgPaymentMethod = view.findViewById(R.id.rgPaymentMethod);
        rbViViet = view.findViewById(R.id.rbViViet);
        rbSacombank = view.findViewById(R.id.rbSacombank);
        rbTheQuocTe = view.findViewById(R.id.rbTheQuocTe);
        rbATMNoiDia = view.findViewById(R.id.rbATMNoiDia);
        rbTienMat = view.findViewById(R.id.rbTienMat);
        tvHuongDan = view.findViewById(R.id.tvHuongDan);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SpannableString ss = new SpannableString("Thanh toán bằng tiền mặt theo cú pháp. Xem hướng dẫn");
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                rbTienMat.setChecked(true);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setFakeBoldText(false);
                ds.setUnderlineText(false);
                ds.setColor(getActivity().getResources().getColor(R.color.lifecardsdk_black_effective));
            }
        };

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                textView.setEnabled(false);
                startActivity(new Intent(getActivity(), LCPaymentGuideActivity.class));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setFakeBoldText(true);
                ds.setUnderlineText(true);
            }
        };
        ss.setSpan(clickableSpan1, 0,ss.length() - 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan2, ss.length() - 13, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvHuongDan.setText(ss);
        tvHuongDan.setMovementMethod(LinkMovementMethod.getInstance());
        tvHuongDan.setHighlightColor(Color.TRANSPARENT);
    }

    @Override
    public void onResume() {
        super.onResume();
        tvHuongDan.setEnabled(true);
    }
}
