package com.lpb.lifecardsdk.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.data.model.response.default_.Style;
import com.lpb.lifecardsdk.util.ScreenUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.autofilltextview.AutofitTextView;

/**
 * Created by vannh.lvt on 12/08/2020
 */
public class TextViewUtils {

    private static final String TOP = "top";
    private static final String RIGHT = "right";
    private static final String BOTTOM = "bottom";
    private static final String LEFT = "left";
    private static final String CENTER = "center";
    private static final String TEMPLATE_SDP = "_%s_sdp";

    public static void setStyleTextView(AutofitTextView textView, Context context, Style style, String textColor) {
        try {
            if (StringUtils.isEmpty(textColor))
                textView.setTextColor(context.getResources().getColor(R.color.lifecardsdk_white));
            else
                textView.setTextColor(Color.parseColor(textColor));

        } catch (Exception e) {
            textView.setTextColor(context.getResources().getColor(R.color.lifecardsdk_white));
        }
        if (style == null) {
            textView.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.lifecardsdk_dp_32));
            textView.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.lifecardsdk_sp_16));
            textView.setPadding(ScreenUtils.sdpToPixel(context, 18, 18), ScreenUtils.sdpToPixel(context, 0, 0), ScreenUtils.sdpToPixel(context, 18, 18), ScreenUtils.sdpToPixel(context, 9, 9));
            textView.setGravity(Gravity.BOTTOM | Gravity.CENTER);
            textView.setShadowLayer(0, 0, 0, context.getResources().getColor(R.color.lifecardsdk_transparent));
            return;
        }
        if (!NumberUtils.isEmpty(style.getTextSizeMax())) {
            try {
                textView.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(ScreenUtils.getResId(String.format(TEMPLATE_SDP, style.getTextSizeMax().toString()), R.dimen.class)));
            } catch (Exception e) {
                textView.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.lifecardsdk_dp_32));
            }
        } else {
            textView.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.lifecardsdk_dp_32));
        }

        if (!NumberUtils.isEmpty(style.getTextSizeMin())) {
            try {
                textView.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(ScreenUtils.getResId(String.format(TEMPLATE_SDP, style.getTextSizeMin().toString()), R.dimen.class)));
            } catch (Exception e) {
                textView.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.lifecardsdk_sp_16));
            }

        } else {
            textView.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.lifecardsdk_sp_16));
        }

        Integer gravityVertical = getGravity(style.getGravityVertical());
        Integer gravityHorizontal = getGravity(style.getGravityHorizontal());

        if (gravityHorizontal != Integer.MAX_VALUE && gravityVertical != Integer.MAX_VALUE) {
            textView.setGravity(gravityVertical | gravityHorizontal);
        } else if (gravityHorizontal != Integer.MAX_VALUE || gravityVertical != Integer.MAX_VALUE) {
            if (gravityHorizontal != Integer.MAX_VALUE) {
                textView.setGravity(gravityHorizontal | Gravity.BOTTOM);
            } else {
                textView.setGravity(gravityVertical | Gravity.CENTER);
            }
        } else {
            textView.setGravity(Gravity.BOTTOM | Gravity.CENTER);
        }

        textView.setPadding(ScreenUtils.sdpToPixel(context, style.getPaddingLeft(), 18), ScreenUtils.sdpToPixel(context, style.getPaddingTop(), 0), ScreenUtils.sdpToPixel(context, style.getPaddingRight(), 18), ScreenUtils.sdpToPixel(context, style.getPaddingBottom(), 9));

        try {
            if (StringUtils.isEmpty(style.getShadowColor()) || NumberUtils.isEmpty(style.getShadowRadius()) || NumberUtils.isEmpty(style.getShadowX()) || NumberUtils.isEmpty(style.getShadowY())) {
                textView.setShadowLayer(0, 0, 0, context.getResources().getColor(R.color.lifecardsdk_transparent));
            } else {
                textView.setShadowLayer(style.getShadowRadius(), style.getShadowX(), style.getShadowY(), Color.parseColor(style.getShadowColor()));
            }
        } catch (Exception e) {
            textView.setShadowLayer(0, 0, 0, context.getResources().getColor(R.color.lifecardsdk_transparent));
        }
    }


    public static Integer getGravity(String gravity) {
        if (StringUtils.isEmpty(gravity))
            return Integer.MAX_VALUE;
        gravity = gravity.toLowerCase();
        switch (gravity) {
            case TOP:
                return Gravity.TOP;
            case RIGHT:
                return Gravity.END;
            case BOTTOM:
                return Gravity.BOTTOM;
            case LEFT:
                return Gravity.START;
            case CENTER:
                return Gravity.CENTER;
            default:
                return Integer.MAX_VALUE;
        }
    }
}
