package com.smw.common.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class NumberTextView  extends AppCompatTextView {

    public NumberTextView(Context context) {
        super(context);
        initTypeface();
    }

    public NumberTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypeface();
    }

    public NumberTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypeface();
    }

    static Typeface typeface;
    protected void initTypeface() {
        if (typeface == null) {
            typeface = Typeface.createFromAsset(getContext().getAssets(), "font/DIN_Alternate_Bold.ttf");
        }
        setTypeface(typeface);
    }
}