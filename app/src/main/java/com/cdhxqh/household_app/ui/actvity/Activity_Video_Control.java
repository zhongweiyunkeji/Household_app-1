package com.cdhxqh.household_app.ui.actvity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.ezviz.DeviceDiscoverInfo;
import com.cdhxqh.household_app.ezviz.OpenYSService;
import com.cdhxqh.household_app.ezviz.SecureValidate;
import com.videogo.constant.Constant;
import com.videogo.exception.ErrorCode;
import com.videogo.exception.HCNetSDKException;
import com.videogo.exception.InnerException;
import com.videogo.exception.RtspClientException;
import com.videogo.openapi.EzvizAPI;
import com.videogo.openapi.bean.req.GetCameraInfoList;
import com.videogo.openapi.bean.resp.CameraInfo;
import com.videogo.realplay.RealPlayMsg;
import com.videogo.realplay.RealPlayerHelper;
import com.videogo.realplay.RealPlayerManager;
import com.videogo.util.ConnectionDetector;
import com.videogo.util.LogUtil;
import com.videogo.util.Utils;

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
public class Activity_Video_Control extends BaseActivity implements SecureValidate.SecureValidateListener,OpenYSService.OpenYSServiceListener {

    LinearLayout layout;
    ScrollView   scrollView;
    VideoView videoView;
    ImageView imgView;
    CameraInfo info;  // 摄像头信息
    TextView titleText; // 标题
    ImageView settingImg; // 标题栏右侧按钮
    ImageView backImg;  // 退回按钮

    /** 标识是否正在播放 */
    boolean mIsPlaying = false;
    /** 视频窗口可以显示的区域 */
    Rect mCanDisplayRect = null;
    /** 竖屏时的宽度 */
    private int mDisplayWidth = 0;
    /** 竖屏时的高度 */
    private int mDisplayHeight = 0;

    RealPlayerHelper mRealPlayerHelper;
    Handler mHandler;
    SurfaceView mSurfaceView;
    RealPlayerManager mRealPlayMgr;
    SurfaceHolder mSurfaceHolder;
    DeviceDiscoverInfo mDeviceDiscoverInfo;
    AlertDialog mPlayFailDialog;
    ImageButton mStopBtn;
    ImageButton mPlayBtn;

    ImageView pauseBtn;
    RelativeLayout waitLayout;
    TextView processText;
    RelativeLayout coverImg;

    private double mPlayRatio = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_control);

        findViewById();
        initDate();
        initView();
    }

    private void findViewById(){
        layout = (LinearLayout)findViewById(R.id.video_monitoring_layout);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        titleText = (TextView)findViewById(R.id.title_text_id);
        settingImg = (ImageView)findViewById(R.id.title_add_id);
        backImg = (ImageView)findViewById(R.id.back_imageview_id);
        mSurfaceView = (SurfaceView) findViewById(R.id.realplay_wnd_sv);
        mStopBtn = (ImageButton) findViewById(R.id.realplay_stop_btn);  // 停止按钮
        mPlayBtn = (ImageButton) findViewById(R.id.realplay_play_btn);  //  播放按钮

        pauseBtn = (ImageView) findViewById(R.id.realplay_play_iv);  //  播放按钮
        waitLayout = (RelativeLayout) findViewById(R.id.process);  //  播放按钮
        processText = (TextView) findViewById(R.id.processtext);  //  缓冲进度

        coverImg = (RelativeLayout) findViewById(R.id.realplay_display_view);  //  缓冲进度

    }

    public void initDate(){
        Intent intent = getIntent();
        if(intent!=null){
            Bundle bundle = intent.getExtras();
            if(bundle!=null){ // 获取数据
                info = bundle.getParcelable("device_name");
            }
        }

        mRealPlayerHelper = RealPlayerHelper.getInstance(getApplication());
        mHandler = new Handler(new Handler.Callback() {// 视频播放状态监听器
            @Override
            public boolean handleMessage(Message msg) {
                int code = msg.what;
                switch (code) {
                    case RealPlayMsg.MSG_REALPLAY_PLAY_SUCCESS:
                        //处理预览成功
                        handlePlaySuccess(msg);
                        break;
                    case RealPlayMsg.MSG_REALPLAY_PLAY_FAIL:
                        //处理预览失败
                        handlePlayFail(msg.arg1);
                        break;
                    case RealPlayMsg.MSG_REALPLAY_PASSWORD_ERROR:
                        //处理播放密码错误
                        if(TextUtils.isEmpty(info.getCameraId())) {
                            Utils.showToast(Activity_Video_Control.this, R.string.realplay_login_password_error, msg.arg1);
                            handlePasswordError(R.string.realplay_password_error_title,  R.string.realplay_login_password_msg, 0, false);
                        } else {
                            handlePasswordError(R.string.realplay_password_error_title,  R.string.realplay_password_error_message3, R.string.realplay_password_error_message1, false);
                        }
                        break;
                    case RealPlayMsg.MSG_REALPLAY_ENCRYPT_PASSWORD_ERROR:
                        // 处理播放密码错误
                        handlePasswordError(R.string.realplay_encrypt_password_error_title, R.string.realplay_encrypt_password_error_message, 0, true);
                        break;

                    case RealPlayMsg.MSG_REALPLAY_PLAY_START:
                        updateLoadingProgress(40);
                        break;
                    case RealPlayMsg.MSG_REALPLAY_CONNECTION_START:
                        updateLoadingProgress(60);
                        break;
                    case RealPlayMsg.MSG_REALPLAY_CONNECTION_SUCCESS:
                        updateLoadingProgress(80);
                        break;
                    case RealPlayMsg.MSG_GET_CAMERA_INFO_SUCCESS:
                        updateLoadingProgress(20);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

    }

    private void initView(){
        // 保持屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        pauseBtn.setVisibility(View.GONE);
        titleText.setText(info.getCameraName());
        settingImg.setVisibility(View.GONE);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseBtn.setVisibility(View.GONE);
                startRealPlay();
            }
        });
        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRealPlay(false);
                pauseBtn.setVisibility(View.VISIBLE);
            }
        });

        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRealPlay();
                pauseBtn.setVisibility(View.GONE);
            }
        });


        //获取屏幕长宽
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mDisplayWidth = metric.widthPixels;
        mDisplayHeight = metric.heightPixels;
        mCanDisplayRect = new Rect(0, 0, 0, 0);



        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // 获取surfaceView可现实区域的大小
                if (mCanDisplayRect.width() <= 0 || mCanDisplayRect.height() <= 0) {
                    int left = 0;
                }

                if (mRealPlayMgr != null) {
                    //设置播放Surface
                    mRealPlayMgr.setPlaySurface(holder);
                }
                mSurfaceHolder = holder;
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (mRealPlayMgr != null) {
                    //设置播放Surface
                    mRealPlayMgr.setPlaySurface(holder);
                }
                mSurfaceHolder = null;
            }
        });
        mSurfaceView.setVisibility(View.VISIBLE);

    }

    /**
     * 开始播放
     */
    private void startRealPlay() {
        // 检查网络是否可用
        if (!ConnectionDetector.isNetworkAvailable(this)) {
            // 提示没有连接网络
            showPlayFailDialog("视频播放失败，请检查您的网络");
            setPlayStopUI();
            return;
        }

        setPlayLoadingUI();

        mRealPlayMgr = new RealPlayerManager(this);
        //设置Handler，接收处理消息
        mRealPlayMgr.setHandler(mHandler);
        //设置播放Surface
        mRealPlayMgr.setPlaySurface(mSurfaceHolder);
        //开启预览任务
        if(TextUtils.isEmpty(info.getCameraId())) {
            if(!TextUtils.isEmpty(EzvizAPI.getInstance().getUserCode())) {
                mRealPlayerHelper.startLocalRealPlayTask(mRealPlayMgr, info.getDeviceId(), mDeviceDiscoverInfo.localIP, null);
            } else {
                mRealPlayerHelper.startEncryptLocalRealPlayTask(this, mRealPlayMgr, info.getDeviceId(),
                        mDeviceDiscoverInfo.localIP, R.string.realplay_password_error_title, R.string.realplay_login_password_msg, 0);
            }
        } else {
            mRealPlayerHelper.startRealPlayTask(mRealPlayMgr, info.getCameraId());
        }

        updateLoadingProgress(0);
    }


    /**
     * 更新进度条
     */
    private void updateLoadingProgress(final int progress) {
        if(processText!=null){
            processText.setText(progress + "%");
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Random r = new Random();
                    processText.setText((progress + r.nextInt(20)) + "%");
                }
            }, 500);
        }
    }

    /**
     * 显示暂停按钮
     */
    private void setPlayStopUI() {
        mSurfaceView.setVisibility(View.INVISIBLE);
        mPlayBtn.setVisibility(View.VISIBLE);
        mStopBtn.setVisibility(View.GONE);
        coverImg.setVisibility(View.VISIBLE);
        waitLayout.setVisibility(View.GONE); // 隐藏进度条
    }

    /**
     * 显示继续播放按钮
     */
    private void setPlayStartUI() {
        coverImg.setVisibility(View.GONE);
        mSurfaceView.setVisibility(View.VISIBLE);
        mPlayBtn.setVisibility(View.GONE);
        mStopBtn.setVisibility(View.VISIBLE);
        waitLayout.setVisibility(View.GONE); // 隐藏进度条
    }

    /**
     * 显示初次加载时的图片
     */
    private void setPlayLoadingUI() {
        coverImg.setVisibility(View.GONE);
        mSurfaceView.setVisibility(View.VISIBLE);
        mPlayBtn.setVisibility(View.GONE);
        mStopBtn.setVisibility(View.VISIBLE);
        waitLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 处理播放失败的情况
     */
    private void showPlayFailDialog(String msg) {
        if(mPlayFailDialog!=null){
            mPlayFailDialog.dismiss();;
        }
        mPlayFailDialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setPositiveButton("确定", null);
        if (!isFinishing()) {
            mPlayFailDialog = builder.show();
        }
    }

    /**
     * 处理播放成功的情况
     */
    private void handlePlaySuccess(Message msg) {
        if (msg.arg1 != 0) {
            mPlayRatio = Math.min((double) msg.arg2 / msg.arg1, Constant.LIVE_VIEW_RATIO);
        }
        if (mPlayRatio != 0) {
            setPlayViewPosition();
        }

        setPlayStartUI();

        // 开启旋转传感器
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    /**
     * 处理播放失败的情况
     */
    private void handlePlayFail(int errorCode) {
        stopRealPlay(false);

        String msg = null;
        // 判断返回的错误码
        switch (errorCode) {
            case HCNetSDKException.NET_DVR_PASSWORD_ERROR:
            case HCNetSDKException.NET_DVR_NOENOUGHPRI:
            case ErrorCode.ERROR_DVR_LOGIN_USERID_ERROR:
                // 弹出密码输入框
                handlePasswordError(R.string.realplay_password_error_title,
                        R.string.realplay_password_error_message3,
                        R.string.realplay_password_error_message1,
                        false);
                return;
            case ErrorCode.ERROR_WEB_SESSION_ERROR:
            case ErrorCode.ERROR_WEB_SESSION_EXPIRE:
            case ErrorCode.ERROR_CAS_PLATFORM_CLIENT_NO_SIGN_RELEATED:
            case ErrorCode.ERROR_WEB_HARDWARE_SIGNATURE_ERROR:
                EzvizAPI.getInstance().gotoLoginPage();
                return;
            case RtspClientException.RTSPCLIENT_DEVICE_CONNECTION_LIMIT:
            case HCNetSDKException.NET_DVR_RTSP_OVER_MAX_CHAN:
            case RtspClientException.RTSPCLIENT_OVER_MAXLINK:
            case HCNetSDKException.NET_DVR_OVER_MAXLINK:
                msg = getString(R.string.remoteplayback_over_link);
                break;
            case ErrorCode.ERROR_WEB_DIVICE_NOT_ONLINE:
            case ErrorCode.ERROR_RTSP_NOT_FOUND:
            case ErrorCode.ERROR_CAS_PLATFORM_CLIENT_REQUEST_NO_PU_FOUNDED:
                msg = getString(R.string.realplay_fail_device_not_exist);
                break;
            case ErrorCode.ERROR_WEB_DIVICE_SO_TIMEOUT:
                msg = getString(R.string.realplay_fail_connect_device);
                break;
            case HCNetSDKException.NET_DVR_RTSP_PRIVACY_STATUS:
            case RtspClientException.RTSPCLIENT_PRIVACY_STATUS:
                msg = getString(R.string.realplay_set_fail_status);
                break;
            case InnerException.INNER_DEVICE_NOT_EXIST:
                msg = getString(R.string.camera_not_online);
                break;
            case ErrorCode.ERROR_WEB_CODE_ERROR:
                OpenYSService.openYSServiceDialog(this, this);
                break;
            case ErrorCode.ERROR_WEB_HARDWARE_SIGNATURE_OP_ERROR:
                SecureValidate.secureValidateDialog(this, this);
                break;
            default:
                msg = Utils.getErrorTip(this, R.string.realplay_play_fail, errorCode);
                break;
        }

        if(!TextUtils.isEmpty(msg)) {
            showPlayFailDialog(msg);
        }
    }

    /**
     * 停止播放
     */
    private void stopRealPlay(boolean onScroll) {
        if (mRealPlayMgr != null) {
            //停止预览任务
            mRealPlayerHelper.stopRealPlayTask(mRealPlayMgr);
        }
        setPlayStopUI();
    }

    // 处理密码错误
    private void handlePasswordError(int title_resid, int msg1_resid, int msg2_resid, final boolean isEncrypt) {
        setPlayStopUI();
        // 检查网络是否可用
        if (!ConnectionDetector.isNetworkAvailable(this)) {
            // 提示没有连接网络
            showPlayFailDialog(getString(R.string.realplay_play_fail_becauseof_network));
            return;
        }

        setPlayLoadingUI();

        mRealPlayMgr = new RealPlayerManager(this);
        //设置Handler，接收处理消息
        mRealPlayMgr.setHandler(mHandler);
        //设置播放Surface
        mRealPlayMgr.setPlaySurface(mSurfaceHolder);
        //开启加密预览任务
        if(TextUtils.isEmpty(info.getCameraId())) {
            mRealPlayerHelper.startEncryptLocalRealPlayTask(this, mRealPlayMgr, info.getDeviceId(),
                    mDeviceDiscoverInfo.localIP, title_resid, msg1_resid, msg2_resid);
        } else {
            mRealPlayerHelper.startEncryptRealPlayTask(this, mRealPlayMgr, info.getCameraId(), title_resid, msg1_resid, msg2_resid);
        }

        updateLoadingProgress(0);
    }

    /**
     * 设置播放窗口位置
     */
    private void setPlayViewPosition() {
        int canDisplayWidth = mCanDisplayRect.width();
        int canDisplayHeight = mCanDisplayRect.height();
        if (canDisplayHeight == 0 || canDisplayWidth == 0) {
            return;
        }

    }


    /**
     * 回到界面，继续播放
     */
    @Override
    protected void onResume() {  // 继续播放
        super.onResume();
        // 开始播放
        startRealPlay();
    }

    /**
     *
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onSecureValidate(int result) {
        if(result == 0) {
            // 开始播放
            startRealPlay();
        }
    }

    @Override
    public void onOpenYSService(int result) {
        if(result == 0) {
            // 开始播放
            startRealPlay();
        }
    }
}
