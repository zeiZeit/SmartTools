package com.smw.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.ColorRes;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.smw.common.R;

/**
 * 继承TextView,实现文字闪烁
 */
public class GradientTextView extends AppCompatTextView {
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 宽度
     */
    private int mViewWidth;
    /**
     * 线性渐变对象
     */
    private LinearGradient mLinearGradient;
    /**
     * 矩阵对象
     */
    private Matrix mGradientMatrix;
    /**
     * 平移距离
     */
    private int mTranslate;

    private int mTextColor;

    public boolean ismBling() {
        return mBling;
    }

    public void setmBling(boolean mBling) {
        this.mBling = mBling;
        if (mBling&&mPaint!=null){
            mPaint.setShader(mLinearGradient);
        }
    }

    private boolean mBling;

    public GradientTextView(Context context) {
        this(context, null);

    }

    public GradientTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public GradientTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.GradientTextView);
        if(array!=null) {
            try {
                mTextColor = array.getColor(R.styleable.GradientTextView_gt_text_color, getColor(R.color.common_gt_text));
                mBling = array.getBoolean(R.styleable.GradientTextView_gt_text_bling,false);
            }catch(Exception e){
                e.printStackTrace();
            }
            finally{
                array.recycle();
            }
        }

        Log.e("zeit", "onSizeChanged: "+mTextColor+mBling );

    }

    /**
     * 测量出文本宽度后,再初始化画笔等基础设置
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0){
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0){
                mPaint = getPaint();
                Log.e("zeit", "onSizeChanged: "+mTextColor+mBling );
                mLinearGradient = new LinearGradient(
                        0,
                        0,
                        mViewWidth,
                        0,
                        new int[]{mTextColor,0xffffff,mTextColor},
                        null ,
                        Shader.TileMode.CLAMP);
                if (mBling){
                    mPaint.setShader(mLinearGradient);
                }
                mGradientMatrix = new Matrix();
            }
        }
    }

    private int getColor(@ColorRes int resId)
    {
        return ContextCompat.getColor(getContext(),resId);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制文字之前
        super.onDraw(canvas);
        //绘制文字之后
        if (mBling){
            if (mGradientMatrix != null){
                mTranslate += mViewWidth/5;
                if (mTranslate > 5 * mViewWidth){//决定文字闪烁的频繁:快慢
                    mTranslate =  -mViewWidth;
                }
                mGradientMatrix.setTranslate(mTranslate,0);
                mLinearGradient.setLocalMatrix(mGradientMatrix);
                postInvalidateDelayed(100);
            }
        }

    }
}