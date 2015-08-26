package com.cdhxqh.household_app.ui.actvity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.VideoView;
import com.cdhxqh.household_app.R;

/**
 * Created by hexian on 2015/8/25.
 */
public class Activity_pay_video extends Activity {

    VideoView videoView;
    TableLayout tableLayout;
    LinearLayout centerLayout;
    ImageView settingImg;
    TextView titleView;
    ImageView backImg;
    ImageView payBtn; // 播放或停止按钮
    String payUrl; // 播放地址
    private boolean fullscreen = false;
    boolean flag = false;
    int width = 0;
    int height = 0;

    String[] times = new String[]{ "上午 10:30:30", "下午 13:40:20", "下午 15:50:10",
                                    "下午 19:20:20", "下午 22:50:10", "下午 23:30:30",
                                    "上午 04:50:20", "上午 07:20:10", "上午 10:20:30",
                                    "上午 11:20:10", "下午 14:15:06", "上午 15:50:30"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_video);
        findViewById();
        initView();
    }

    public void findViewById(){
        videoView = (VideoView)findViewById(R.id.audtoView);
        centerLayout = (LinearLayout)findViewById(R.id.center_video);
        settingImg = (ImageView)findViewById(R.id.title_scan_id);
        titleView = (TextView)findViewById(R.id.title_text_id);
        backImg = (ImageView)findViewById(R.id.back_imageview_id);
        payBtn = (ImageView)findViewById(R.id.menu_stop_or_start);
    }

    public void initView(){
        settingImg.setVisibility(View.GONE);
        titleView.setText("海康DS-2CD2412F");
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    startPayVideo(payUrl);
                } else {
                    pausePayVideo();
                }
            }
        });

        // 准备播放环境
        planVideo();

        /**
         * 获取图片： R.drawable.alarm_history 的宽高
         */
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.alarm_history, opts);
        // 缩放图片
        Matrix matrix = new Matrix();
        matrix.postScale(0.15F, 0.15F);
        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
        int width  = mBitmap.getWidth();

        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;

        tableLayout = new TableLayout(this);
        TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        tableLayout.setLayoutParams(params);

        int cellMargin = 10;
        int rowCount = 3;    // 总行数
        int columnCount = screenWidth/(width+2*cellMargin); // 总列数
        for (int row = 0;row< rowCount; row ++) {
            TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            rowParams.gravity = Gravity.CENTER;
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(rowParams);
            for (int column = 0; column < columnCount; column++) {
                TableRow.LayoutParams tl = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                tl.setMargins(cellMargin, cellMargin, cellMargin, cellMargin);

                LinearLayout ll = new LinearLayout(this);
                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                llp.gravity = Gravity.LEFT;
                ll.setOrientation(LinearLayout.VERTICAL); // 垂直摆放

                ImageView imgView = new ImageView(this);
                imgView.setImageBitmap(mBitmap);
                ll.addView(imgView);

                final int index = row*columnCount+column;
                TextView tv = new TextView(this);
                tv.setText(times[index]);
                ll.addView(tv);

                ll.setLayoutParams(tl);

                ll.setBackgroundColor(Color.parseColor("#F1F6FA"));
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pausePayVideo();
                        if (index % 2 == 0) {
                            payUrl = "http://vshare.ys7.com:80/hcnp/513351987_1_2_1_0_183.136.184.7_6500.m3u8";
                        } else {
                            payUrl = "rtsp://211.139.194.251:554/live/2/13E6330A31193128/5iLd2iNl5nQ2s8r8.sdp";
                        }
                        // 播放视频
                        startPayVideo(payUrl);
                    }
                });

                if( row==rowCount-1 && column>columnCount- 3) {
                    continue;
                }
                tableRow.addView(ll, tl);
            }


            tableLayout.addView(tableRow, rowParams);
        }

        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        centerLayout.addView(tableLayout, ll);
    }

    public void startPayVideo(String url){
        pausePayVideo();
        Uri uri = Uri.parse(url);
        videoView.setVideoURI(uri);
        if(payUrl!=null){
            videoView.start();
        }
        flag = false;
    }

    public void pausePayVideo(){
        if (videoView.isPlaying()) { // 暂停原来播放的视频
            videoView.stopPlayback();
        }
        flag = true;
    }

    public void planVideo(){
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Uri uri = Uri.parse(payUrl);
                videoView.stopPlayback();    // 首先停止视频播放
                videoView.setVideoURI(uri);  // 设置播放地址
                videoView.requestFocus();    // 获取请求码
                videoView.start();          // 开始播放
                return true;                //如果设置true就可以防止他弹出错误的提示框！
            }
        });
    }

}
