package com.tritiger.doubanmovie.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

public class HiddenTextView extends TextView {
    public HiddenTextView(Context context) {
        super(context);
    }

    public HiddenTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HiddenTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setNullableText(CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            setText("");
            ((ViewGroup) getParent()).setVisibility(GONE);
        } else {
            setText(text);
            ((ViewGroup) getParent()).setVisibility(VISIBLE);
        }
    }
}
