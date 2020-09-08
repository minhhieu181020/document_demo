package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage.bottomsheet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;

public class SelectLookUpTime extends BottomSheetDialogFragment {
    private ImageView img5transaction, img10transaction, imgthisweek, imgthismonth, imgSelectTime;
    private LinearLayout llselecttime, llthismonth, llthisweek, ll10transaction, ll5transaction;
    boolean re5TRAN, re10TRAN, re1W, re1MONTH, reTime;
    private BottomSheetListener mListenner;

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
        View view = inflater.inflate(R.layout.lifecardsdk_select_lookup_time_bottom_sheet, container, false);
        img5transaction = view.findViewById(R.id.img5transaction);
        img10transaction = view.findViewById(R.id.img10transaction);
        imgthisweek = view.findViewById(R.id.imgthisweek);
        imgthismonth = view.findViewById(R.id.imgthismonth);
        imgSelectTime = view.findViewById(R.id.imgSelectTime);
        ll5transaction = view.findViewById(R.id.ll5transaction);
        ll10transaction = view.findViewById(R.id.ll10transaction);
        llthisweek = view.findViewById(R.id.llthisweek);
        llthismonth = view.findViewById(R.id.llthismonth);
        llselecttime = view.findViewById(R.id.llselecttime);
        re5TRAN = false;
        re10TRAN = false;
        re1W = false;
        re1MONTH = false;
        reTime = false;
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        String time = bundle.getString(Constants.BundleConstants.SELECT_TIME);
//        Log.e("Time", "onViewCreated: " + time);
        if (time.equalsIgnoreCase(getResources().getString(R.string.lifecardsdk_card_Last_5_transactions))) {
            img5transaction.setVisibility(View.VISIBLE);
            img10transaction.setVisibility(View.GONE);
            imgthisweek.setVisibility(View.GONE);
            imgthismonth.setVisibility(View.GONE);
            imgSelectTime.setVisibility(View.GONE);
        } else if (time.equalsIgnoreCase(getResources().getString(R.string.lifecardsdk_card_Last_10_transactions))) {
            img5transaction.setVisibility(View.GONE);
            img10transaction.setVisibility(View.VISIBLE);
            imgthisweek.setVisibility(View.GONE);
            imgthismonth.setVisibility(View.GONE);
            imgSelectTime.setVisibility(View.GONE);
        } else if (time.equalsIgnoreCase(getResources().getString(R.string.lifecardsdk_card_Last_1_week_transactions))) {
            img5transaction.setVisibility(View.GONE);
            img10transaction.setVisibility(View.GONE);
            imgthisweek.setVisibility(View.VISIBLE);
            imgthismonth.setVisibility(View.GONE);
            imgSelectTime.setVisibility(View.GONE);
        } else if (time.equalsIgnoreCase(getResources().getString(R.string.lifecardsdk_card_Last_1_month_transactions))) {
            img5transaction.setVisibility(View.GONE);
            img10transaction.setVisibility(View.GONE);
            imgthisweek.setVisibility(View.GONE);
            imgthismonth.setVisibility(View.VISIBLE);
            imgSelectTime.setVisibility(View.GONE);
        } else {
            img5transaction.setVisibility(View.GONE);
            img10transaction.setVisibility(View.GONE);
            imgthisweek.setVisibility(View.GONE);
            imgthismonth.setVisibility(View.GONE);
            imgSelectTime.setVisibility(View.VISIBLE);
        }
        inAction();

    }

    private void inAction() {
        ll5transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                re5TRAN = true;
                img5transaction.setVisibility(View.VISIBLE);
                img10transaction.setVisibility(View.GONE);
                imgthisweek.setVisibility(View.GONE);
                imgthismonth.setVisibility(View.GONE);
                imgSelectTime.setVisibility(View.GONE);
                mListenner.getDataTime("5TRAN");
                dismiss();
            }
        });
        ll10transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                re10TRAN = true;
                img5transaction.setVisibility(View.GONE);
                img10transaction.setVisibility(View.VISIBLE);
                imgthisweek.setVisibility(View.GONE);
                imgthismonth.setVisibility(View.GONE);
                imgSelectTime.setVisibility(View.GONE);
                mListenner.getDataTime("10TRAN");
                dismiss();
            }
        });
        llthisweek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                re1W = true;
                img5transaction.setVisibility(View.GONE);
                img10transaction.setVisibility(View.GONE);
                imgthisweek.setVisibility(View.VISIBLE);
                imgthismonth.setVisibility(View.GONE);
                imgSelectTime.setVisibility(View.GONE);
                mListenner.getDataTime("1W");
                dismiss();
            }
        });
        llthismonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                re1MONTH = true;
                img5transaction.setVisibility(View.GONE);
                img10transaction.setVisibility(View.GONE);
                imgthisweek.setVisibility(View.GONE);
                imgthismonth.setVisibility(View.VISIBLE);
                imgSelectTime.setVisibility(View.GONE);
                mListenner.getDataTime("1MONTH");
                dismiss();
            }
        });
        llselecttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reTime = true;
                img5transaction.setVisibility(View.GONE);
                img10transaction.setVisibility(View.GONE);
                imgthisweek.setVisibility(View.GONE);
                imgthismonth.setVisibility(View.GONE);
                imgSelectTime.setVisibility(View.VISIBLE);
                mListenner.getDataTime("RANGE_TIME");
                Log.e("selcet", "reTime" + reTime);
                dismiss();
            }
        });
    }

    public interface BottomSheetListener {
        void getDataTime(String s);
    }


}
