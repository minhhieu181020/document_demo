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

public class CustomToasNotOK extends Toast {
    public static final int SUCCESS = 1;
    public static final int ERROR = 2;


    public CustomToasNotOK(Context context) {
        super(context);
    }

    public static Toast makeText(Context context, String message, int duration, int type) {
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(duration);
        View layout = LayoutInflater.from(context).inflate(R.layout.lifecardsdk_layout_custom_toast, null, false);
        TextView tvContent = layout.findViewById(R.id.tvContent);
        LinearLayout linearLayout = layout.findViewById(R.id.llBackground);
        ImageView imgStatus = layout.findViewById(R.id.imgStatus);
        tvContent.setText(message);

        switch (type) {
            case SUCCESS:
                linearLayout.setBackgroundResource(R.drawable.lifecardsdk_bg_toast_success);
                imgStatus.setImageResource(R.mipmap.lifecardsdk_bg_main_category);
                break;
            case ERROR:
                break;
        }
        toast.setView(layout);
        return toast;
    }
}
