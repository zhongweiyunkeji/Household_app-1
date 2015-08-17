package com.cdhxqh.household_app.ui.actvity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.utils.UIUtils;

/**
 * Created by hexian on 2015/8/17.
 */
public class Activity_Help_Center_Search extends  BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_title);
        findViewById();
        initView();
    }

    public void findViewById(){
        TextView view = (TextView)findViewById(R.id.title_text_id);
        UIUtils.drawableRadiusTextView(view, "#0A3C7E");  // 画出圆角
    }

    public void initView(){

    }

}
