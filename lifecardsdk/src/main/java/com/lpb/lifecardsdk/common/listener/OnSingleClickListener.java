package com.lpb.lifecardsdk.common.listener;

import android.os.SystemClock;
import android.view.View;

import com.lpb.lifecardsdk.constant.Config;

public abstract class OnSingleClickListener implements View.OnClickListener {

    private static final long MIN_CLICK_INTERVAL = Config.DELAY_CLICK;
    private long mLastClickTime;
    public abstract void onSingleClick(View v);

    @Override
    public final void onClick(View v) {
        long currentClickTime = SystemClock.uptimeMillis();
        long elapsedTime = currentClickTime - mLastClickTime;
        if (elapsedTime < MIN_CLICK_INTERVAL) {
            mLastClickTime = currentClickTime;
            return;
        }
        onSingleClick(v);
    }

}