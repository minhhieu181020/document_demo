package com.lpb.lifecardsdk.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lpb.lifecardsdk.R;

public class CustomToast extends Toast {
    public static final int SUCCESS = 1;
    public static final int ERROR = 2;


    public CustomToast(Context context) {
        super(context);
    }

    public static void makeText(Context context, String message, int duration, int type) {
        final Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        View layout = LayoutInflater.from(context).inflate(R.layout.lifecardsdk_layout_custom_toast, null, false);
        TextView tvContent = layout.findViewById(R.id.tvContent);
        LinearLayout linearLayout = layout.findViewById(R.id.llBackground);
        ImageView imgStatus = layout.findViewById(R.id.imgStatus);
        tvContent.setText(message);

        switch (type) {
            case SUCCESS:
                linearLayout.setBackgroundResource(R.drawable.lifecardsdk_bg_toast_success);
                imgStatus.setImageResource(R.drawable.lifecardsdk_ic_check_circle_black_24dp);
                break;
            case ERROR:
                linearLayout.setBackgroundResource(R.drawable.lifecardsdk_bg_toast_success);
                imgStatus.setImageResource(R.drawable.lifecardsdk_ic_error_black_24dp);
                break;
        }
        toast.setView(layout);
        toast.show();
//        CountDownTimer toastCountDown;
//        toastCountDown = new CountDownTimer(duration, 1000 /*Tick duration*/) {
//            public void onTick(long millisUntilFinished) {
//                toast.show();
//            }
//            public void onFinish() {
//                toast.cancel();
//            }
//        };
//        toast.show();
//        toastCountDown.start();
    }
}
