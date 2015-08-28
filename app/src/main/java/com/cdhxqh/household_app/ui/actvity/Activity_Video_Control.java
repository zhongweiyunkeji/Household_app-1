package com.cdhxqh.household_app.ui.actvity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.cdhxqh.household_app.R;
import com.videogo.openapi.bean.resp.CameraInfo;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hexian on 2015/8/10.
 *
 * 流媒体视频控制(放大、缩小，加大、减小以及方向控制主类)
 *
 */
public class Activity_Video_Control extends BaseActivity {

    LinearLayout layout;
    ScrollView   scrollView;
    VideoView videoView;
    ImageView imgView;
    CameraInfo info;  // 摄像头信息
    TextView titleText; // 标题
    ImageView settingImg; // 标题栏右侧按钮
    ImageView backImg;  // 退回按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_control);

        findViewById();
        initDate();
        initView();
    }

    private void findViewById(){
        videoView = (VideoView)findViewById(R.id.video_m3u8);
        layout = (LinearLayout)findViewById(R.id.video_monitoring_layout);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        imgView = (ImageView)findViewById(R.id.buffer);
        titleText = (TextView)findViewById(R.id.title_text_id);
        settingImg = (ImageView)findViewById(R.id.title_add_id);
        backImg = (ImageView)findViewById(R.id.back_imageview_id);
    }

    public void initDate(){
        Intent intent = getIntent();
        if(intent!=null){
            Bundle bundle = intent.getExtras();
            if(bundle!=null){
                info = bundle.getParcelable("device_name");
            }
        }
    }

    private void initView(){
        titleText.setText(info.getCameraName());
        settingImg.setVisibility(View.GONE);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * 回到界面，继续播放
     */
    @Override
    protected void onResume() {  // 继续播放
        imgView.setVisibility(View.VISIBLE);
        super.onResume();
    }

    /**
     *
     */
    @Override
    protected void onPause() {
        super.onPause();
    }



}
