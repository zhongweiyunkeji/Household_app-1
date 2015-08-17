package com.cdhxqh.household_app.utils;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;

/**
 * Created by hexian on 2015/8/17.
 */
public class UIUtils {


    /**
     * 画出两端圆角的TextView
     * @param view  传入的TextView
     * @param color 背景颜色
     * @return  传入的TexiView
     */
    public static TextView drawableRadiusTextView(TextView view, String color){
        int height = view.getHeight();
        if(height == 0){
            view.measure(0, 0);  // 测量宽高
            height = view.getMeasuredWidth();  // 当前获得的宽高不包括padding
        }
        int paddingTop = view.getPaddingTop();
        int paddingBottom = view.getPaddingBottom();
        int strokeWidth = 1;
        int roundRadius = (height + paddingTop + paddingBottom)/2; // 圆角的半径
        int strokeColor = Color.parseColor(color);
        int fillColor   = Color.parseColor(color);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        view.setBackgroundDrawable(gd);

        return view;
    }


}
