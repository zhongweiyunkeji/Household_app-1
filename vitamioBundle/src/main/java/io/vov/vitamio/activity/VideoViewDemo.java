package io.vov.vitamio.activity;


import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.R;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VideoViewDemo extends Activity {

    // http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8
    // http://vshare.ys7.com:80/hcnp/513351987_1_2_1_0_183.136.184.7_6500.m3u8
    private String path = "http://vshare.ys7.com:80/hcnp/497827719_1_2_1_0_183.136.184.7_6500.m3u8";
    private VideoView mVideoView;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
            return;

        setContentView(R.layout.videoview);
        mVideoView = (VideoView) findViewById(R.id.surface_view);
        mVideoView.setVideoPath(path);
        mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
        mVideoView.setMediaController(new MediaController(this));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (mVideoView != null)
            mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        super.onConfigurationChanged(newConfig);
    }
}
