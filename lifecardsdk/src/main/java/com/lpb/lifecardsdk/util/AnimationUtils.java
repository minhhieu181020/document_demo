package com.lpb.lifecardsdk.util;

import android.animation.ObjectAnimator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

public final class AnimationUtils {
    public static void startAnimatedProgress(ProgressBar bar, int progress) {
        ObjectAnimator animation = ObjectAnimator.ofInt(bar, "progress", progress);
        animation.setDuration(250);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }
}
