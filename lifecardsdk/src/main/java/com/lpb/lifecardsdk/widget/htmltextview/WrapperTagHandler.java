package com.lpb.lifecardsdk.widget.htmltextview;

import android.support.annotation.Nullable;
import android.text.Editable;

import org.xml.sax.Attributes;

public interface WrapperTagHandler {
    boolean handleTag(boolean opening, String tag, Editable output, @Nullable Attributes attributes);
}
