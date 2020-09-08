package com.lpb.lifecardsdk.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.DrawableClickListener;
import com.lpb.lifecardsdk.util.StringUtils;


public class CustomEditTextClearable extends CustomEditText {

    private Drawable rightDrawable;

    public CustomEditTextClearable(Context context, AttributeSet attrs) {
        super(context, attrs);

        initUI();
    }

    public CustomEditTextClearable(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initUI();
    }

    private void initUI() {
        rightDrawable = ContextCompat.getDrawable(getContext(), R.drawable.lifecardsdk_ic_close_big);
        setCompoundDrawables(getDrawableLeft(), getDrawableTop(), null, getDrawableBottom());

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isEnabled() && getDefaultEditable()) {
                    if (!StringUtils.isEmpty(charSequence)) {
                        setCompoundDrawables(getDrawableBound(getDrawableLeft()),
                                getDrawableBound(getDrawableTop()), getDrawableBound(rightDrawable),
                                getDrawableBound(getDrawableBottom()));
                    } else {
                        setCompoundDrawables(getDrawableBound(getDrawableLeft()),
                                getDrawableBound(getDrawableTop()), null,
                                getDrawableBound(getDrawableBottom()));

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                if (target == DrawablePosition.RIGHT) {
                    if (!isEnabled()) return;

                    if (isEnabled() && getDefaultEditable())
                        setText("");
                }
            }
        });
    }

    private Drawable getDrawableBound(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        int h = drawable.getIntrinsicHeight();
        int w = drawable.getIntrinsicWidth();
        drawable.setBounds(0, 0, w, h);
        return drawable;
    }
}
