package com.example.yuanyuanlai.uav.util;

import android.util.TypedValue;

import com.example.yuanyuanlai.uav.MyApplication;

public class ScreenUtils {

    /** dp转px **/
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, MyApplication.getContext().getResources().getDisplayMetrics());
    }

    public static int sp2px(float spValue) {
        final float fontScale = MyApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
