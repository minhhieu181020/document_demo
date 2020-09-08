package com.lpb.lifecardsdk.widget.facedetector;

import android.util.Size;

import java.util.Comparator;

/* compiled from: CameraView */
class CompareSizesByArea implements Comparator<Size> {
    CompareSizesByArea() {
    }

    public int compare(Size lhs, Size rhs) {
        return Long.signum((((long) lhs.getWidth()) * ((long) lhs.getHeight())) - (((long) rhs.getWidth()) * ((long) rhs.getHeight())));
    }
}
