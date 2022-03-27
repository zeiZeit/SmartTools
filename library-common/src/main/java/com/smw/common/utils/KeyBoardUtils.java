package com.smw.common.utils;


import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public class KeyBoardUtils
{
    /**
     * 打卡软键盘
     * @param mEditText 输入框
     * @param mContext 上下文
     */
    public static void openKeybord(EditText mEditText, Context mContext)
    {
        if (mEditText!=null) mEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     * @param mEditText 输入框
     * @param mContext 上下文
     */
    public static void closeKeybord(EditText mEditText, Context mContext)
    {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }



    /**
     * 软键盘弹出向上推动显示需要显示的内容
     * @param rootView  根布局
     * @param bottomView 向上推后要显示布局
     */
    public static void addLayoutListener(View rootView, View bottomView) {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect rect = new Rect();
            rootView.getWindowVisibleDisplayFrame(rect);//获取rootView的可视区域
            int invisibleHeight = rootView.getRootView().getHeight() - rect.bottom;//获取rootView的不可视区域高度
            if (invisibleHeight > 150) { //键盘显示
                int[] location = new int[2];
                bottomView.getLocationInWindow(location); //获取bottomView的坐标
                int scrollHeight = (location[1] + bottomView.getHeight()) - rect.bottom;//算出需要滚动的高度
                if (scrollHeight != 0) {//防止界面元素改变调用监听,使界面上下跳动,如验证码倒计时
                    rootView.scrollTo(0, scrollHeight);
                }
            } else {
                rootView.scrollTo(0, 0);
            }
        });
    }
}
