package com.cdhxqh.household_app.ui.actvity;

import android.annotation.TargetApi;
import android.app.Activity;
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

    Map<String, String> urls = new HashMap<String, String>(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_control);
        urls.put("1", "rtsp://211.139.194.251:554/live/2/13E6330A31193128/5iLd2iNl5nQ2s8r8.sdp");
        urls.put("2", "http://ws.jia.360.cn/qhwljia/36020187375/index.m3u8");
        urls.put("3", "http://vshare.ys7.com:80/hcnp/497827719_1_2_1_0_183.136.184.7_6500.m3u8");
        urls.put("4", "http://vshare.ys7.com:80/hcnp/517678458_1_1_1_0_183.136.184.7_6500.m3u8");
        urls.put("5", "http://vshare.ys7.com:80/hcnp/516427804_3_2_1_0_183.136.184.7_6500.m3u8");
        urls.put("6", "http://ws.jia.360.cn/qhwljia/36020166912/index.m3u8");
        urls.put("7", "http://vshare.ys7.com:80/hcnp/513351987_1_2_1_0_183.136.184.7_6500.m3u8");
        urls.put("8", "http://vshare.ys7.com:80/hcnp/500894371_1_2_1_0_183.136.184.7_6500.m3u8");
        urls.put("9", "http://vshare.ys7.com:80/hcnp/497827719_1_2_1_0_183.136.184.7_6500.m3u8");


        findViewById();
        initView();
    }

    private void findViewById(){
        videoView = (VideoView)findViewById(R.id.video_m3u8);
        layout = (LinearLayout)findViewById(R.id.video_monitoring_layout);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        imgView = (ImageView)findViewById(R.id.buffer);
        pay();
        // linearLayout = (FixGridLayout)findViewById(R.id.linearLayout);
    }

    public void pay(){
        Random random1 = new Random(9);
        int x = random1.nextInt();
        if(x<0){
            x = (-x)% 10;
        }
        if(x == 0){
            x = 3;
        }
        // http://vshare.ys7.com:80/hcnp/497827719_1_2_1_0_183.136.184.7_6500.m3u8
        // rtsp://211.139.194.251:554/live/2/13E6330A31193128/5iLd2iNl5nQ2s8r8.sdp
        final Uri uri = Uri.parse("http://vshare.ys7.com:80/hcnp/513351987_1_2_1_0_183.136.184.7_6500.m3u8");
        // videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                imgView.setVisibility(View.GONE);
            }
        });


        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if (!videoView.isPlaying()) {
                    imgView.setVisibility(View.VISIBLE);
                }
                videoView.stopPlayback();    // 首先停止视频播放
                videoView.setVideoURI(uri);  // 设置播放地址
                videoView.requestFocus();    // 获取请求码
                videoView.start();          // 开始播放
                return true;                //如果设置true就可以防止他弹出错误的提示框！
            }
        });

    }

    @Override
    protected void onResume() {  // 继续播放
        imgView.setVisibility(View.VISIBLE);
        pay();
        super.onResume();
    }

    @Override
    protected void onPause() {
        videoView.stopPlayback();
        super.onPause();
    }

    private void initView(){
        /*linearLayout.setmCellHeight(200);
        linearLayout.setmCellWidth(200);*/




        /*for (int i = 0; i < 30; i++) {
            // CheckBox box = new CheckBox(this);
            // box.setText("第"+i+"个");
            TextView view = getTextView();
            if(i == 0){
                linearLayout.setmCellWidth(200);
                linearLayout.setmCellHeight(100);
            }
            linearLayout.addView(view);
        }*/
    }

    private TextView getTextView(){
        final TextView view = new TextView(this);
        view.setBackgroundColor(Color.parseColor("#0C4DA2"));
        view.setGravity(Gravity.CENTER);
        view.setWidth(180);
        // view.getLayout().getPar.setMargins(280, 0, 0, 0);

        view.post(new Runnable() {
            @Override
            public void run() {
                //把网络访问的代码放在这里
                view.setText(Html.fromHtml("<img src=\"http://192.168.1.99:8080/qdm/qrcode/checkbox_sel.png\"><br/>抓图", new Html.ImageGetter() {
                    @Override
                    public Drawable getDrawable(String source) {
                        Drawable drawable = null;
                        InputStream in;
                        try {
                            in = new URL(source).openStream();
                            drawable = Drawable.createFromStream(in, ""); // 获取网路图片
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                        return drawable;

                    }
                }, null));
            }
        });

        return view;
    }


}
