package com.smw.common.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.smw.common.utils.FontUtil;

/**
 * function: 中粗 textVew
 *
 * <p></p>
 * Created by hl on  2018/11/13 0013.
 */
public class MediumTextView extends AppCompatTextView {
    public MediumTextView(Context context) {
        super(context);
        init();
    }

    public MediumTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MediumTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        FontUtil.mediumFont(this);
    }
}
