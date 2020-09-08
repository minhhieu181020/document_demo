package com.lpb.lifecardsdk.ui.bottomsheet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.widget.RelativeRadioGroup;

public class BottomSheetPayMethod extends BottomSheetDialogFragment {
    private RelativeRadioGroup rgPaymentMethod;
    private RadioButton rbTienMat;
    private RadioButton rbViViet;
    private RadioButton rbSacombank;
    private RadioButton rbTheQuocTe;
    private RadioButton rbATMNoiDia;
    private Button btnContinue;
    private BottomSheetListener mListenner;
    private String mStatus = "";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.LifeCardSDK_AppBottomSheetDialogTheme);
    }

    public void setmListenner(BottomSheetListener mListenner) {
        this.mListenner = mListenner;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lifecardsdk_payment_method_bottom_sheet2, container, false);
        rgPaymentMethod = view.findViewById(R.id.rgPaymentMethod);
        rbTienMat = view.findViewById(R.id.rbTienMat);
        rbViViet = view.findViewById(R.id.rbViViet);
        rbSacombank = view.findViewById(R.id.rbSacombank);
        rbTheQuocTe = view.findViewById(R.id.rbTheQuocTe);
        rbATMNoiDia = view.findViewById(R.id.rbATMNoiDia);
        btnContinue = view.findViewById(R.id.btnContinue);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAction();

    }

    private void initAction() {
        rgPaymentMethod.setOnCheckedChangeListener(new RelativeRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RelativeRadioGroup group, int checkedId) {
                if (checkedId == R.id.rbTienMat) {
                    mStatus = Constants.TypePaymentMethod.TIENMAT;
                }  else if (checkedId == R.id.rbViViet) {
                    mStatus = Constants.TypePaymentMethod.VIPAY;
                } else if (checkedId == R.id.rbSacombank) {
                    mStatus = Constants.TypePaymentMethod.SACOMBANK;
                } else if (checkedId == R.id.rbTheQuocTe) {
                    mStatus = Constants.TypePaymentMethod.THEQUOCTE;
                } else if (checkedId == R.id.rbATMNoiDia) {
                    mStatus = Constants.TypePaymentMethod.THENOIDIA;
                }
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

