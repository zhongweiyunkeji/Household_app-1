package io.vov.vitamio.activity;


import android.app.Activity;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.R;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VideoViewDemo extends Activity {

    // http://vshare.ys7.com:80/hcnp/497827719_1_2_1_0_183.136.184.7_6500.m3u8
    // rtsp://211.139.194.251:554/live/2/13E6330A31193128/5iLd2iNl5nQ2s8r8.sdp
    // http://192.168.1.99:8080/security/vedio/111.flv
    private String path = "rtsp://211.139.194.251:554/live/2/13E6330A31193128/5iLd2iNl5nQ2s8r8.sdp";
    private VideoView mVideoView;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
            return;

        setContentView(R.layout.videoview);
        mVideoView = (VideoView) findViewById(R.id.surface_view);
        final Uri uri = Uri.parse(path);
        mVideoView.setVideoPath(path);
        mVideoView.requestFocus();
        mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
        // mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();
        mVideoView.start();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoView.start();
            }
        });


        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mVideoView.stopPlayback();    // 首先停止视频播放
                mVideoView.setVideoPath(path);  // 设置播放地址
                mVideoView.requestFocus();    // 获取请求码
                mVideoView.start();          // 开始播放
                return true;                //如果设置true就可以防止他弹出错误的提示框！
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (mVideoView != null)
            mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        super.onConfigurationChanged(newConfig);
    }
}
