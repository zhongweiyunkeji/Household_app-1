package com.cdhxqh.household_app.ui.actvity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.cdhxqh.household_app.R;

/**
 * Created by hexian on 2015/8/18.
 */
public class Activity_alarm_play extends BaseActivity {

    ImageView backImg;
    TextView titleText;
    ImageView titleImg;

    VideoView video;
    TextView qeuipName;
    TextView qeuipAddr;
    TextView alarmType;
    TextView alarmContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_alarm_play);
        findViewById();
        initView();
    }

    public void findViewById(){

        backImg = (ImageView)findViewById(R.id.back_imageview_id);
        titleImg = (ImageView)findViewById(R.id.title_add_id);
        titleText = (TextView)findViewById(R.id.title_text_id);

        video = (VideoView)findViewById(R.id.video_paly);
        qeuipName = (TextView)findViewById(R.id.equip_name);
        qeuipAddr = (TextView)findViewById(R.id.equip_addr);
        alarmType = (TextView)findViewById(R.id.alarm_type);
        alarmContent = (TextView)findViewById(R.id.alarm_content);
    }

    public void initView(){
        titleImg.setVisibility(View.GONE);
        titleText.setText("报警回放");
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
