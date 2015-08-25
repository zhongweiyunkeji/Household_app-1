package com.cdhxqh.household_app.ui.actvity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.cdhxqh.household_app.R;

import java.io.InputStream;
import java.net.URL;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_control);
        findViewById();
        initView();
    }

    private void findViewById(){
        videoView = (VideoView)findViewById(R.id.video_m3u8);
        layout = (LinearLayout)findViewById(R.id.video_monitoring_layout);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        pay();
        // linearLayout = (FixGridLayout)findViewById(R.id.linearLayout);
    }

    public void pay(){
        // http://vshare.ys7.com:80/hcnp/497827719_1_2_1_0_183.136.184.7_6500.m3u8
        // rtsp://v2.cache2.c.youtube.com/CjgLENy73wIaLwm3JbT_%ED%AF%80%ED%B0%819HqWohMYESARFEIJbXYtZ29vZ2xlSARSB3Jlc3VsdHNg_vSmsbeSyd5JDA==/0/0/0/video.3gp
        // rtsp://183.136.184.33:8554/demo://520752318:1:1:1:0:183.136.184.7:6500
        final Uri uri = Uri.parse("rtsp://211.139.194.251:554/live/2/13E6330A31193128/5iLd2iNl5nQ2s8r8.sdp");
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                videoView.stopPlayback(); // 首先停止视频播放
                videoView.setVideoURI(uri);  // 设置播放地址
                videoView.requestFocus();  // 获取请求码
                videoView.start();        // 开始播放
                return true;//如果设置true就可以防止他弹出错误的提示框！
            }
        });

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
