package com.smw.common.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.widget.NestedScrollView;


public class CustomScrollView extends NestedScrollView {

    private GestureDetector mGestureDetector;

    View.OnTouchListener mGestureListener;

    @SuppressWarnings("deprecation")
    public CustomScrollView(Context context, AttributeSet attrs) {

        super(context,attrs);

        mGestureDetector= new GestureDetector(new YScrollDetector());

        setFadingEdgeLength(0);

    }
    //通过手势判断，来判断是否拦截触摸事件。
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        boolean d = ev.getPointerCount()>=2;

        return d&&(super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev));
    }

// Return false if we're scrolling in the x direction

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if(Math.abs(distanceY) > Math.abs(distanceX)) {
                return true;
            }
            return false;
        }
    }
}