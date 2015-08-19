package com.cdhxqh.household_app.ui.actvity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.household_app.R;

/**
 * Created by hexian on 2015/8/19.
 */
public class Activity_use_guide extends BaseActivity {

    ImageView backInmg;
    TextView titleText;
    ImageView settingImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        findViewById();
        initView();
    }

    public void findViewById(){
        backInmg = (ImageView)findViewById(R.id.back_imageview_id);  // 退回按钮
        titleText = (TextView)findViewById(R.id.title_text_id);      // 中间title
        settingImg = (ImageView)findViewById(R.id.title_add_id);     // 右上角设置按钮
    }

    public void initView(){
        titleText.setText("功能介绍");
        settingImg.setVisibility(View.GONE);
        backInmg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();;
            }
        });
    }

}
