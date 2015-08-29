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
import android.view.MotionEvent;
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
import com.videogo.realplay.RealPlayStatus;
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

    ImageView topCtrl;
    ImageView leftCtrl;
    ImageView rightCtrl;
    ImageView bottomCtrl;

    public static final int MSG_HIDE_PTZ_DIRECTION = 201;

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

        topCtrl = (ImageView) findViewById(R.id.top);  //  向上控制
        leftCtrl = (ImageView) findViewById(R.id.left);  //  向左控制
        rightCtrl = (ImageView) findViewById(R.id.right);  //  向右控制
        bottomCtrl = (ImageView) findViewById(R.id.bottom);  //  向下控制


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
                    case MSG_HIDE_PTZ_DIRECTION:
                        handleHidePtzDirection(msg);
                        break;
                    case RealPlayMsg.MSG_PTZ_SET_FAIL:
                        handlePtzControlFail(msg);
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

        // 注册控制按钮事件
        topCtrl.setOnTouchListener(mOnTouchListener);
        bottomCtrl.setOnTouchListener(mOnTouchListener);
        leftCtrl.setOnTouchListener(mOnTouchListener);
        rightCtrl.setOnTouchListener(mOnTouchListener);


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

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent motionevent) {
            int action = motionevent.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    switch (view.getId()) {
                        case R.id.top://上控
                           // mPtzControlLy.setBackgroundResource(R.drawable.ptz_up_sel);
                            setPtzDirectionIv(RealPlayStatus.PTZ_UP);
                            mRealPlayerHelper.setPtzCommand(mRealPlayMgr, mHandler, RealPlayStatus.PTZ_UP, true);
                            break;
                        case R.id.bottom://下控
                            //mPtzControlLy.setBackgroundResource(R.drawable.ptz_bottom_sel);
                            setPtzDirectionIv(RealPlayStatus.PTZ_DOWN);
                            mRealPlayerHelper.setPtzCommand(mRealPlayMgr, mHandler, RealPlayStatus.PTZ_DOWN, true);
                            break;
                        case R.id.left://左控
                            //mPtzControlLy.setBackgroundResource(R.drawable.ptz_left_sel);
                            setPtzDirectionIv(RealPlayStatus.PTZ_LEFT);
                            mRealPlayerHelper.setPtzCommand(mRealPlayMgr, mHandler, RealPlayStatus.PTZ_LEFT, true);
                            break;
                        case R.id.right://右控
                           // mPtzControlLy.setBackgroundResource(R.drawable.ptz_right_sel);
                            setPtzDirectionIv(RealPlayStatus.PTZ_RIGHT);
                            mRealPlayerHelper.setPtzCommand(mRealPlayMgr, mHandler, RealPlayStatus.PTZ_RIGHT, true);
                            break;
                        default:
                            break;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    switch (view.getId()) {
                        case R.id.top://上控
                            //mPtzControlLy.setBackgroundResource(R.drawable.ptz_bg);
                            mRealPlayerHelper.setPtzCommand(mRealPlayMgr, mHandler, RealPlayStatus.PTZ_UP, false);
                            break;
                        case R.id.bottom://下控
                            //mPtzControlLy.setBackgroundResource(R.drawable.ptz_bg);
                            mRealPlayerHelper.setPtzCommand(mRealPlayMgr, mHandler, RealPlayStatus.PTZ_DOWN, false);
                            break;
                        case R.id.left://左控
                            //mPtzControlLy.setBackgroundResource(R.drawable.ptz_bg);
                            mRealPlayerHelper.setPtzCommand(mRealPlayMgr, mHandler, RealPlayStatus.PTZ_LEFT, false);
                            break;
                        case R.id.right://右控
                            //mPtzControlLy.setBackgroundResource(R.drawable.ptz_bg);
                            mRealPlayerHelper.setPtzCommand(mRealPlayMgr, mHandler, RealPlayStatus.PTZ_RIGHT, false);
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    };


    private void setPtzDirectionIv(int command) {
        setPtzDirectionIv(command, 0);
    }

    private void setPtzDirectionIv(int command, int errorCode) {
        if (command != -1 && errorCode == 0) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            //mRealPlayPtzDirectionIv.setVisibility(View.VISIBLE);
            mHandler.removeMessages(MSG_HIDE_PTZ_DIRECTION);
            Message msg = new Message();
            msg.what = MSG_HIDE_PTZ_DIRECTION;
            msg.arg1 = 1;
            mHandler.sendMessageDelayed(msg, 500);
        } else if(errorCode != 0) {
            /*RelativeLayout.LayoutParams svParams = (RelativeLayout.LayoutParams)mRealPlaySv.getLayoutParams();
            RelativeLayout.LayoutParams params = null;*/
           // mRealPlayPtzDirectionIv.setVisibility(View.VISIBLE);
            mHandler.removeMessages(MSG_HIDE_PTZ_DIRECTION);
            Message msg = new Message();
            msg.what = MSG_HIDE_PTZ_DIRECTION;
            msg.arg1 = 1;
            mHandler.sendMessageDelayed(msg, 500);
        } else {
            //mRealPlayPtzDirectionIv.setVisibility(View.GONE);
            mHandler.removeMessages(MSG_HIDE_PTZ_DIRECTION);
        }
    }

    /**
     * 封装控制的方法
     * @param msg
     */
    private void handleHidePtzDirection(Message msg) {
        if (mHandler == null) {
            return;
        }
        mHandler.removeMessages(MSG_HIDE_PTZ_DIRECTION);
        Log.e("TAG", "------------------------------------------------------------------>SUCCESS");
        if (msg.arg1 > 2) {
            //mRealPlayPtzDirectionIv.setVisibility(View.GONE);
        } else {
            // mRealPlayPtzDirectionIv.setVisibility(msg.arg1 == 1 ? View.GONE : View.VISIBLE);
            Message message = new Message();
            message.what = MSG_HIDE_PTZ_DIRECTION;
            message.arg1 = msg.arg1 + 1;
            mHandler.sendMessageDelayed(message, 500);

        }
    }

    private void handlePtzControlFail(Message msg) {
        Log.e("TAG", "------------------------------------------------------------------>FAIL");
        switch(msg.arg1) {
            case ErrorCode.ERROR_CAS_PTZ_CONTROL_CALLING_PRESET_FAILED://正在调用预置点，键控动作无效
                Utils.showToast(this,R.string.camera_lens_too_busy, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_PRESET_PRESETING_FAILE:// 当前正在调用预置点
                Utils.showToast(this,R.string.ptz_is_preseting, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_CONTROL_TIMEOUT_SOUND_LACALIZATION_FAILED://当前正在声源定位
                break;
            case ErrorCode.ERROR_CAS_PTZ_CONTROL_TIMEOUT_CRUISE_TRACK_FAILED://键控动作超时(当前正在轨迹巡航)
                Utils.showToast(this,R.string.ptz_control_timeout_cruise_track_failed, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_PRESET_INVALID_POSITION_FAILED://当前预置点信息无效
                Utils.showToast(this,R.string.ptz_preset_invalid_position_failed, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_PRESET_CURRENT_POSITION_FAILED://该预置点已是当前位置
                Utils.showToast(this,R.string.ptz_preset_current_position_failed, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_PRESET_SOUND_LOCALIZATION_FAILED:// 设备正在响应本次声源定位
                Utils.showToast(this,R.string.ptz_preset_sound_localization_failed, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_OPENING_PRIVACY_FAILED://当前正在开启隐私遮蔽
            case ErrorCode.ERROR_CAS_PTZ_CLOSING_PRIVACY_FAILED://当前正在关闭隐私遮蔽
            case ErrorCode.ERROR_CAS_PTZ_MIRRORING_FAILED://设备正在镜像操作（设备镜像要几秒钟，防止频繁镜像操作）
                Utils.showToast(this,R.string.ptz_operation_too_frequently, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_CONTROLING_FAILED://设备正在键控动作（上下左右）(一个客户端在上下左右控制，另外一个在开其它东西)
                break;
            case ErrorCode.ERROR_CAS_PTZ_FAILED://云台当前操作失败
                break;
            case ErrorCode.ERROR_CAS_PTZ_PRESET_EXCEED_MAXNUM_FAILED://当前预置点超过最大个数
                Utils.showToast(this,R.string.ptz_preset_exceed_maxnum_failed, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_PRIVACYING_FAILED:// 设备处于隐私遮蔽状态（关闭了镜头，再去操作云台相关）
                Utils.showToast(this,R.string.ptz_privacying_failed, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_TTSING_FAILED:// 设备处于语音对讲状态(区别以前的语音对讲错误码，云台单独列一个）
                Utils.showToast(this,R.string.ptz_mirroring_failed, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_ROTATION_UP_LIMIT_FAILED://设备云台旋转到达上限位
            case ErrorCode.ERROR_CAS_PTZ_ROTATION_DOWN_LIMIT_FAILED://设备云台旋转到达下限位
            case ErrorCode.ERROR_CAS_PTZ_ROTATION_LEFT_LIMIT_FAILED://设备云台旋转到达左限位
            case ErrorCode.ERROR_CAS_PTZ_ROTATION_RIGHT_LIMIT_FAILED://设备云台旋转到达右限位
                setPtzDirectionIv(-1, msg.arg1);
                break;
            default:
                Utils.showToast(this,R.string.ptz_operation_failed, msg.arg1);
                break;
        }
    }

}
