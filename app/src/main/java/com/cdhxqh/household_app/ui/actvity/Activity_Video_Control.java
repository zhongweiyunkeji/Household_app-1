package com.cdhxqh.household_app.ui.actvity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.config.Constants;
import com.cdhxqh.household_app.ezviz.DeviceDiscoverInfo;
import com.cdhxqh.household_app.ezviz.OpenYSService;
import com.cdhxqh.household_app.ezviz.SecureValidate;
import com.cdhxqh.household_app.utils.AudioPlayUtil;
import com.videogo.constant.Constant;
import com.videogo.constant.IntentConsts;
import com.videogo.demo.DemoRealPlayer;
import com.videogo.exception.BaseException;
import com.videogo.exception.ErrorCode;
import com.videogo.exception.HCNetSDKException;
import com.videogo.exception.InnerException;
import com.videogo.exception.RtspClientException;
import com.videogo.openapi.EzvizAPI;
import com.videogo.openapi.bean.resp.CameraInfo;
import com.videogo.realplay.RealPlayMsg;
import com.videogo.realplay.RealPlayStatus;
import com.videogo.realplay.RealPlayerHelper;
import com.videogo.realplay.RealPlayerManager;
import com.videogo.util.ConnectionDetector;
import com.videogo.util.LocalInfo;
import com.videogo.util.RotateViewUtil;
import com.videogo.util.SDCardUtil;
import com.videogo.util.Utils;
import com.videogo.widget.CustomRect;
import com.videogo.widget.CustomTouchListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by hexian on 2015/8/10.
 * <p/>
 * 流媒体视频控制(放大、缩小，加大、减小以及方向控制主类)
 */
public class Activity_Video_Control extends BaseActivity implements SecureValidate.SecureValidateListener, OpenYSService.OpenYSServiceListener {
    private static final String TAG = "Activity_Video_Control";
    private static final String TAG1 = "Activity_Video_Control1";
    LinearLayout layout;
    ScrollView scrollView;
    VideoView videoView;
    ImageView imgView;
    CameraInfo info;  // 摄像头信息
    TextView titleText; // 标题
    ImageView settingImg; // 标题栏右侧按钮
    ImageView backImg;  // 退回按钮

    /**
     * 控制声音*
     */
    private ImageButton voice_btn;

    /**
     * 设置全屏*
     */
    private ImageButton fullScreen_btn;

    /**
     * 播放布局文件*
     */
    RelativeLayout mRealPlayPlayRl;
    LinearLayout linearLayout;
    private RelativeLayout.LayoutParams mRealPlayCaptureRlLp = null;

    /**
     * 标识是否正在播放
     */
    boolean mIsPlaying = false;
    /**
     * 视频窗口可以显示的区域
     */
    Rect mCanDisplayRect = null;
    /**
     * 竖屏时的宽度
     */
    private int mDisplayWidth = 0;
    /**
     * 竖屏时的高度
     */
    private int mDisplayHeight = 0;

    RealPlayerHelper mRealPlayerHelper;
    /**
     * 实时预览控制对象
     */
    private RealPlayerManager mRealPlayMgr = null;
    /**
     * 演示点预览控制对象
     */
    private DemoRealPlayer mDemoRealPlayer = null;
    Handler mHandler;
    /**
     * 画布*
     */
    SurfaceView mSurfaceView;

    /**
     * 屏幕当前方向
     */
    private int mOrientation = Configuration.ORIENTATION_PORTRAIT;
    /**
     * 实时预览控制对象
     */

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
    ImageView alarmBtn;
    ImageView secureBtn;

    /**
     * 录像布局*
     */
    private View mRealPlayRecordContainer = null;
    /**
     * 录像按钮*
     */
    private ImageButton mRealPlayRecordBtn = null;
    /**
     * 开始录像按钮*
     */
    private ImageButton mRealPlayRecordStartBtn = null;


    /**
     * 录像显示时间布局*
     */
    private LinearLayout mRealPlayRecordLy = null;

    /**
     * 显示时间*
     */
    private ImageView mRealPlayRecordIv = null;
    /**
     * 记时*
     */
    private TextView mRealPlayRecordTv = null;

    private RelativeLayout mRealPlayControlRl = null;
    private ImageView mRealPlayCaptureIv = null;

    private ImageView mRealPlayCaptureWatermarkIv = null;


    private LocalInfo mLocalInfo = null;


    public static final int MSG_HIDE_PTZ_DIRECTION = 201;

    private double mPlayRatio = 0;

    ImageView screenshot;

    private float mRealRatio = Constant.LIVE_VIEW_RATIO;


    /**
     * 标题布局*
     */
    private LinearLayout title_linearlayout;
    /**
     * 手柄区域*
     */
    private RelativeLayout handleRelativeLayout;

    /**
     * 控制布局*
     */

    private RelativeLayout mRealPlayCaptureRl;

    private CustomTouchListener mRealPlayTouchListener = null;

    /**
     * 标识是否正在播放
     */
    private int mStatus = RealPlayStatus.STATUS_INIT;

    private float mZoomScale = 0;

    /**
     * 播放比例*
     */
    private float mPlayScale = 1;
    /**
     * 布局文件是否显示*
     */
    private boolean isShow = false;

    /**
     * 视频的放大按钮*
     */
    LinearLayout e_LinearLayout;
    /**
     * 视频缩小按钮*
     */
    LinearLayout n_LinearLayout;



    /**视频**/
    private ImageButton playbackBtn;





    /**
     * 录像*
     */
    private int mControlDisplaySec = 0;

    /**
     * 录像文件路径
     */
    private String mRecordFilePath = null;
    private String mRecordTime = null;
    /**
     * 录像时间
     */
    private int mRecordSecond = 0;
    private int mCaptureDisplaySec = 0;

    private AudioPlayUtil mAudioPlayUtil = null;

    private boolean mIsOnStop = false;

    private RotateViewUtil mRecordRotateViewUtil = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_control);

        findViewById();
        initDate();
        initView();

    }

    private void findViewById() {
        title_linearlayout = (LinearLayout) findViewById(R.id.title_linearlayout_id); //标题区域
        handleRelativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout_handle_id); //手柄区域


        layout = (LinearLayout) findViewById(R.id.video_monitoring_layout);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        titleText = (TextView) findViewById(R.id.title_text_id);
        settingImg = (ImageView) findViewById(R.id.title_add_id);
        backImg = (ImageView) findViewById(R.id.back_imageview_id);
        mSurfaceView = (SurfaceView) findViewById(R.id.realplay_wnd_sv);
        mStopBtn = (ImageButton) findViewById(R.id.realplay_stop_btn);  // 停止按钮
        mPlayBtn = (ImageButton) findViewById(R.id.realplay_play_btn);  //  播放按钮

        pauseBtn = (ImageView) findViewById(R.id.realplay_play_iv);  //  播放按钮
        waitLayout = (RelativeLayout) findViewById(R.id.process);  //  播放按钮
        processText = (TextView) findViewById(R.id.processtext);  //  缓冲进度

        voice_btn = (ImageButton) findViewById(R.id.voice_control_btn); //控制声音

        fullScreen_btn = (ImageButton) findViewById(R.id.set_full_screen_btn); //设置全屏

        mRealPlayPlayRl = (RelativeLayout) findViewById(R.id.relativelayout_suface_id); //播放布局文件


        linearLayout = (LinearLayout) findViewById(R.id.linearlayout_surface_id); //播放布局文件

        mRealPlayCaptureRl = (RelativeLayout) findViewById(R.id.play_control_id); //控制布局

        coverImg = (RelativeLayout) findViewById(R.id.realplay_display_view);  //  缓冲进度

        topCtrl = (ImageView) findViewById(R.id.top);  //  向上控制
        leftCtrl = (ImageView) findViewById(R.id.left);  //  向左控制
        rightCtrl = (ImageView) findViewById(R.id.right);  //  向右控制
        bottomCtrl = (ImageView) findViewById(R.id.bottom);  //  向下控制

        alarmBtn = (ImageView) findViewById(R.id.device_alarm);  //  报警记录
        secureBtn = (ImageView) findViewById(R.id.device_secure);  //  报警记录

        screenshot = (ImageView) findViewById(R.id.screenshot);


        e_LinearLayout = (LinearLayout) findViewById(R.id.enlarge_btn_linearlayout_id); //放大
        n_LinearLayout = (LinearLayout) findViewById(R.id.narrow_btn_linearlayout_id); //缩小


        /**录像**/
        mRealPlayRecordContainer = findViewById(R.id.realplay_video_container);
        mRealPlayRecordBtn = (ImageButton) findViewById(R.id.realplay_video_btn);
        mRealPlayRecordStartBtn = (ImageButton) findViewById(R.id.realplay_video_start_btn);
        mRealPlayRecordLy = (LinearLayout) findViewById(R.id.realplay_record_ly);
        mRealPlayRecordIv = (ImageView) findViewById(R.id.realplay_record_iv);
        mRealPlayRecordTv = (TextView) findViewById(R.id.realplay_record_tv);

//        mRealPlayCaptureRl = (RelativeLayout) findViewById(R.id.realplay_capture_rl);
//        mRealPlayCaptureRlLp = (RelativeLayout.LayoutParams) mRealPlayCaptureRl.getLayoutParams();

        mRealPlayCaptureIv = (ImageView) findViewById(R.id.realplay_capture_iv);

        mRealPlayCaptureWatermarkIv = (ImageView) findViewById(R.id.realplay_capture_watermark_iv);


        playbackBtn=(ImageButton)findViewById(R.id.realplay_video_playback);



        setPlayScaleUI(1, null, null);


    }

    public void initDate() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) { // 获取数据
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
                        if (TextUtils.isEmpty(info.getCameraId())) {
                            Utils.showToast(Activity_Video_Control.this, R.string.realplay_login_password_error, msg.arg1);
                            handlePasswordError(R.string.realplay_password_error_title, R.string.realplay_login_password_msg, 0, false);
                        } else {
                            handlePasswordError(R.string.realplay_password_error_title, R.string.realplay_password_error_message3, R.string.realplay_password_error_message1, false);
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

    private void initView() {

        // 获取配置信息操作对象
        mLocalInfo = LocalInfo.getInstance();

        mLocalInfo.setSoundOpen(true);


        // 保持屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /**获取屏幕参数**/
        mRecordRotateViewUtil = new RotateViewUtil();

        screenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePic(getpic());
            }
        });

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
                stopRealPlay();
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


        // 获取屏幕参数
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mLocalInfo.setScreenWidthHeight(metric.widthPixels, metric.heightPixels);
        mLocalInfo.setNavigationBarHeight((int) Math.ceil(25 * getResources().getDisplayMetrics().density));

        // 注册控制按钮事件
        topCtrl.setOnTouchListener(mOnTouchListener);
        bottomCtrl.setOnTouchListener(mOnTouchListener);
        leftCtrl.setOnTouchListener(mOnTouchListener);
        rightCtrl.setOnTouchListener(mOnTouchListener);
        voice_btn.setOnClickListener(voiceBtnOnClickListener);

        fullScreen_btn.setOnClickListener(fullScreen_btnOnClickListener);

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
                Log.i(TAG, "大小被改变");

                Log.i(TAG, "width=" + width + ",height=" + height + ",format=" + format);
                if (mRealPlayMgr != null) {
                    //设置播放Surface
                    mRealPlayMgr.setPlaySurface(holder);
                }
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
//        mSurfaceView.setVisibility(View.VISIBLE);


        mRealPlayTouchListener = new CustomTouchListener() {

            @Override
            public boolean canZoom(float scale) {
                Log.i(TAG, "mStatus=" + mStatus);
                if (mStatus == RealPlayStatus.STATUS_PLAY) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public boolean canDrag(int direction) {
                Log.i(TAG, "canDrag");
                if (mRealPlayMgr != null) {
                    // 出界判断
                    if (DRAG_LEFT == direction || DRAG_RIGHT == direction) {
                        // 左移/右移出界判断
                        if (mRealPlayMgr.getSupportPtzLeftRight() == 1) {
                            return true;
                        }
                    } else if (DRAG_UP == direction || DRAG_DOWN == direction) {
                        // 上移/下移出界判断
                        if (mRealPlayMgr.getSupportPtzTopBottom() == 1) {
                            return true;
                        }
                    }
                }
                return false;
            }

            @Override
            public void onSingleClick() {
                Log.i(TAG, "onSingleClick");
//                onRealPlaySvClick();
            }

            @Override
            public void onDoubleClick(MotionEvent e) {
                Log.i(TAG, "onDoubleClick");
            }

            @Override
            public void onZoom(float scale) {
                Log.i(TAG, "****onZoom=" + scale + "mRealPlayMgr.getSupportPtzZoom()=" + mRealPlayMgr.getSupportPtzZoom());
                if (mRealPlayMgr != null && mRealPlayMgr.getSupportPtzZoom() == 1) {
                    startZoom(scale);
                }
            }


            @Override
            public void onDrag(int direction, float distance, float rate) {
                Log.i(TAG, "onDrag");
                if (mRealPlayMgr != null) {
                }
            }

            @Override
            public void onEnd(int mode) {
                Log.i(TAG, "onEnd");
                if (mRealPlayMgr != null) {
//                    stopDrag(false);
                }
                if (mRealPlayMgr != null && mRealPlayMgr.getSupportPtzZoom() == 1) {
                    stopZoom();
                }
            }

            @Override
            public void onZoomChange(float scale, CustomRect oRect, CustomRect curRect) {
                Log.i(TAG, "onZoomChange");
                if (mRealPlayMgr != null && mRealPlayMgr.getSupportPtzZoom() == 1) {
                    //采用云台调焦
                    return;
                }
                if (mStatus == RealPlayStatus.STATUS_PLAY) {
                    if (scale > 1.0f && scale < 1.1f) {
                        scale = 1.1f;
                    }
                    setPlayScaleUI(scale, oRect, curRect);
                }
            }
        };


//        mSurfaceView.setOnTouchListener(mRealPlayTouchListener);


        mSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow) {
                    isShow = false;
                    mRealPlayCaptureRl.setVisibility(View.GONE);
                } else {
                    isShow = true;
                    mRealPlayCaptureRl.setVisibility(View.VISIBLE);
                }
            }
        });


        e_LinearLayout.setOnClickListener(eOnClickListener);
        n_LinearLayout.setOnClickListener(nOnClickListener);


        /**录像事件**/

        mRealPlayRecordBtn.setOnClickListener(mRealPlayRecordBtnOnClickListener);
        mRealPlayRecordContainer.setOnClickListener(mRealPlayRecordBtnOnClickListener);
        mRealPlayRecordStartBtn.setOnClickListener(mRealPlayRecordBtnOnClickListener);



        /**视频回放**/
        playbackBtn.setOnClickListener(playbackBtnOnClickListener);


    }


    private View.OnClickListener playbackBtnOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.putExtra(IntentConsts.EXTRA_CAMERA_INFO, info);
            intent.setClass(Activity_Video_Control.this,Activity_Video_Playback.class);
            startActivity(intent);
        }
    };







    private View.OnClickListener mRealPlayRecordBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onRecordBtnClick();
        }
    };


    private View.OnClickListener eOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Log.i(TAG, "点击放大");
            maxenlarge();
        }
    };


    private View.OnClickListener nOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i(TAG, "点击缩小");
//            minnarrow();
        }


    };


    /**
     * 缩小方法*
     */
    private void minnarrow() {
        android.widget.LinearLayout.LayoutParams sufaceviewParams = (android.widget.LinearLayout.LayoutParams) mSurfaceView
                .getLayoutParams();
        sufaceviewParams.height = 200;
        sufaceviewParams.width = 200;
        sufaceviewParams.gravity = Gravity.CENTER;
        mSurfaceView.setLayoutParams(sufaceviewParams);
    }

    /**
     * 放大方法*
     */
    private void maxenlarge() {
        mSurfaceHolder.setFixedSize(600, 400);
        if (mRealPlayMgr != null) {
            //设置播放Surface
            mRealPlayMgr.setPlaySurface(mSurfaceHolder);
        }
    }


    private View.OnClickListener voiceBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onSoundBtnClick();
        }
    };

    /**
     * 设置全屏*
     */
    private View.OnClickListener fullScreen_btnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setlandscape();
            setRealPlaySvLayout();
        }
    };


    /**
     * 设置横屏*
     */
    private void setlandscape() {
        // 开启旋转传感器
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE || mOrientation == 2) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }


    private void onSoundBtnClick() {
        if (mLocalInfo.isSoundOpen()) {
            mLocalInfo.setSoundOpen(false);
            voice_btn.setBackgroundResource(R.drawable.ic_menu_mute);
        } else {
            mLocalInfo.setSoundOpen(true);
            voice_btn.setBackgroundResource(R.drawable.ic_menu_song3);
        }

        setRealPlaySound();

    }


    /**
     * 图片保存路径*
     */
    public void savePic(Bitmap b) {

        FileOutputStream fos = null;
        try {

            String sdpath = Constants.VIDEO_PATH;
            File f = new File(sdpath, "11.bmp");//设置图片名字
            if (f.exists()) {
                f.delete();
            }

            fos = new FileOutputStream(f);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getpic() {
        Bitmap bitmap = videoView.getDrawingCache();
        return bitmap;
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
        mStatus = RealPlayStatus.STATUS_START;
        mRealPlayMgr = new RealPlayerManager(this);
        //设置Handler，接收处理消息
        mRealPlayMgr.setHandler(mHandler);
        //设置播放Surface
        mRealPlayMgr.setPlaySurface(mSurfaceHolder);
        //开启预览任务
        if (TextUtils.isEmpty(info.getCameraId())) {
            if (!TextUtils.isEmpty(EzvizAPI.getInstance().getUserCode())) {
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
        if (processText != null) {
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
        if (mPlayFailDialog != null) {
            mPlayFailDialog.dismiss();
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
        mStatus = RealPlayStatus.STATUS_PLAY;
        if (msg.arg1 != 0) {
            mPlayRatio = Math.min((double) msg.arg2 / msg.arg1, Constant.LIVE_VIEW_RATIO);
        }
        if (mPlayRatio != 0) {
            setPlayViewPosition();
        }

        setPlayStartUI();
        setRealPlaySound();

        // 开启旋转传感器
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    /**
     * 处理播放失败的情况
     */
    private void handlePlayFail(int errorCode) {
        stopRealPlay();

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

        if (!TextUtils.isEmpty(msg)) {
            showPlayFailDialog(msg);
        }
    }

    /**
     * 停止播放
     */
    private void stopRealPlay() {
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
        if (TextUtils.isEmpty(info.getCameraId())) {
            mRealPlayerHelper.startEncryptLocalRealPlayTask(this, mRealPlayMgr, info.getDeviceId(),
                    mDeviceDiscoverInfo.localIP, title_resid, msg1_resid, msg2_resid);
        } else {
            mRealPlayerHelper.startEncryptRealPlayTask(getApplication(), R.color.black, mRealPlayMgr, info.getCameraId(), title_resid, msg1_resid, msg2_resid);
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
        if (result == 0) {
            // 开始播放
            startRealPlay();
        }
    }

    @Override
    public void onOpenYSService(int result) {
        if (result == 0) {
            // 开始播放
            startRealPlay();
        }
    }

    @Override
    public void onStop() {


        if (mStatus != RealPlayStatus.STATUS_STOP) {
            mIsOnStop = true;
            stopRealPlay();
            mStatus = RealPlayStatus.STATUS_PAUSE;
        }
        super.onStop();
    }

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent motionevent) {
            int action = motionevent.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    switch (view.getId()) {
                        case R.id.top://上控
                            setPtzDirectionIv(RealPlayStatus.PTZ_UP);
                            mRealPlayerHelper.setPtzCommand(mRealPlayMgr, mHandler, RealPlayStatus.PTZ_UP, true);
                            break;
                        case R.id.bottom://下控
                            setPtzDirectionIv(RealPlayStatus.PTZ_DOWN);
                            mRealPlayerHelper.setPtzCommand(mRealPlayMgr, mHandler, RealPlayStatus.PTZ_DOWN, true);
                            break;
                        case R.id.left://左控
                            setPtzDirectionIv(RealPlayStatus.PTZ_LEFT);
                            mRealPlayerHelper.setPtzCommand(mRealPlayMgr, mHandler, RealPlayStatus.PTZ_LEFT, true);
                            break;
                        case R.id.right://右控
                            setPtzDirectionIv(RealPlayStatus.PTZ_RIGHT);
                            mRealPlayerHelper.setPtzCommand(mRealPlayMgr, mHandler, RealPlayStatus.PTZ_RIGHT, true);
                            break;
                        default:
                            break;
                    }
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    switch (view.getId()) {
                        case R.id.top://上控
                            mRealPlayerHelper.setPtzCommand(mRealPlayMgr, mHandler, RealPlayStatus.PTZ_UP, false);
                            break;
                        case R.id.bottom://下控
                            mRealPlayerHelper.setPtzCommand(mRealPlayMgr, mHandler, RealPlayStatus.PTZ_DOWN, false);
                            break;
                        case R.id.left://左控
                            mRealPlayerHelper.setPtzCommand(mRealPlayMgr, mHandler, RealPlayStatus.PTZ_LEFT, false);
                            break;
                        case R.id.right://右控
                            mRealPlayerHelper.setPtzCommand(mRealPlayMgr, mHandler, RealPlayStatus.PTZ_RIGHT, false);
                            break;
                        default:
                            break;
                    }
                    break;
                }
                default: {
                    break;
                }
            }

            return true;
        }
    };


    private void setPtzDirectionIv(int command) {
        setPtzDirectionIv(command, 0);
    }

    private void setPtzDirectionIv(int command, int errorCode) {
        if (command != -1 && errorCode == 0) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            mHandler.removeMessages(MSG_HIDE_PTZ_DIRECTION);
            Message msg = new Message();
            msg.what = MSG_HIDE_PTZ_DIRECTION;
            msg.arg1 = 1;
            mHandler.sendMessageDelayed(msg, 500);
        } else if (errorCode != 0) {
            mHandler.removeMessages(MSG_HIDE_PTZ_DIRECTION);
            Message msg = new Message();
            msg.what = MSG_HIDE_PTZ_DIRECTION;
            msg.arg1 = 1;
            mHandler.sendMessageDelayed(msg, 500);
        } else {
            mHandler.removeMessages(MSG_HIDE_PTZ_DIRECTION);
        }
    }

    /**
     * 封装控制的方法
     *
     * @param msg
     */
    private void handleHidePtzDirection(Message msg) {
        if (mHandler == null) {
            return;
        }
        mHandler.removeMessages(MSG_HIDE_PTZ_DIRECTION);
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
        switch (msg.arg1) {
            case ErrorCode.ERROR_CAS_PTZ_CONTROL_CALLING_PRESET_FAILED://正在调用预置点，键控动作无效
                Utils.showToast(this, R.string.camera_lens_too_busy, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_PRESET_PRESETING_FAILE:// 当前正在调用预置点
                Utils.showToast(this, R.string.ptz_is_preseting, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_CONTROL_TIMEOUT_SOUND_LACALIZATION_FAILED://当前正在声源定位
                break;
            case ErrorCode.ERROR_CAS_PTZ_CONTROL_TIMEOUT_CRUISE_TRACK_FAILED://键控动作超时(当前正在轨迹巡航)
                Utils.showToast(this, R.string.ptz_control_timeout_cruise_track_failed, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_PRESET_INVALID_POSITION_FAILED://当前预置点信息无效
                Utils.showToast(this, R.string.ptz_preset_invalid_position_failed, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_PRESET_CURRENT_POSITION_FAILED://该预置点已是当前位置
                Utils.showToast(this, R.string.ptz_preset_current_position_failed, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_PRESET_SOUND_LOCALIZATION_FAILED:// 设备正在响应本次声源定位
                Utils.showToast(this, R.string.ptz_preset_sound_localization_failed, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_OPENING_PRIVACY_FAILED://当前正在开启隐私遮蔽
            case ErrorCode.ERROR_CAS_PTZ_CLOSING_PRIVACY_FAILED://当前正在关闭隐私遮蔽
            case ErrorCode.ERROR_CAS_PTZ_MIRRORING_FAILED://设备正在镜像操作（设备镜像要几秒钟，防止频繁镜像操作）
                Utils.showToast(this, R.string.ptz_operation_too_frequently, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_CONTROLING_FAILED://设备正在键控动作（上下左右）(一个客户端在上下左右控制，另外一个在开其它东西)
                break;
            case ErrorCode.ERROR_CAS_PTZ_FAILED://云台当前操作失败
                break;
            case ErrorCode.ERROR_CAS_PTZ_PRESET_EXCEED_MAXNUM_FAILED://当前预置点超过最大个数
                Utils.showToast(this, R.string.ptz_preset_exceed_maxnum_failed, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_PRIVACYING_FAILED:// 设备处于隐私遮蔽状态（关闭了镜头，再去操作云台相关）
                Utils.showToast(this, R.string.ptz_privacying_failed, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_TTSING_FAILED:// 设备处于语音对讲状态(区别以前的语音对讲错误码，云台单独列一个）
                Utils.showToast(this, R.string.ptz_mirroring_failed, msg.arg1);
                break;
            case ErrorCode.ERROR_CAS_PTZ_ROTATION_UP_LIMIT_FAILED://设备云台旋转到达上限位
            case ErrorCode.ERROR_CAS_PTZ_ROTATION_DOWN_LIMIT_FAILED://设备云台旋转到达下限位
            case ErrorCode.ERROR_CAS_PTZ_ROTATION_LEFT_LIMIT_FAILED://设备云台旋转到达左限位
            case ErrorCode.ERROR_CAS_PTZ_ROTATION_RIGHT_LIMIT_FAILED://设备云台旋转到达右限位
                setPtzDirectionIv(-1, msg.arg1);
                break;
            default:
                Utils.showToast(this, R.string.ptz_operation_failed, msg.arg1);
                break;
        }
    }


    private void setRealPlaySound() {
        if (mRealPlayMgr != null) {
            if (mLocalInfo.isSoundOpen()) {
                mRealPlayMgr.openSound();
            } else {
                mRealPlayMgr.closeSound();
            }
        } else if (mDemoRealPlayer != null) {
            if (mLocalInfo.isSoundOpen()) {
                mDemoRealPlayer.openSound();
            } else {
                mDemoRealPlayer.closeSound();
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        mOrientation = newConfig.orientation;
        setRealPlaySvLayout();
        updateOperatorUI();
        super.onConfigurationChanged(newConfig);
    }


    /**
     * 设置屏幕大小*
     */
    private void setRealPlaySvLayout() {


        // 设置播放窗口位置
        final int screenWidth = mLocalInfo.getScreenWidth();
        final int screenHeight = (mOrientation == Configuration.ORIENTATION_PORTRAIT) ? (mLocalInfo.getScreenHeight() - mLocalInfo
                .getNavigationBarHeight()) : mLocalInfo.getScreenHeight();

        final RelativeLayout.LayoutParams realPlaySvlp = Utils.getPlayViewLp(mRealRatio, mOrientation,
                screenWidth, screenHeight,
                screenWidth, screenHeight);

        //获取屏幕长宽
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mDisplayWidth = metric.widthPixels;
        mDisplayHeight = metric.heightPixels;

        RelativeLayout.LayoutParams svLp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        svLp.addRule(RelativeLayout.CENTER_IN_PARENT);
        mSurfaceView.setLayoutParams(svLp);
        if (mOrientation != Configuration.ORIENTATION_PORTRAIT) {
            LinearLayout.LayoutParams realPlayPlayRlLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            linearLayout.setLayoutParams(realPlayPlayRlLp);


            title_linearlayout.setVisibility(View.GONE);
            handleRelativeLayout.setVisibility(View.GONE);
            scrollView.setVisibility(View.GONE);
        } else {
            LinearLayout.LayoutParams realPlayPlayRlLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    600);
            linearLayout.setLayoutParams(realPlayPlayRlLp);

            title_linearlayout.setVisibility(View.VISIBLE);
            handleRelativeLayout.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.VISIBLE);
        }

    }


    private void updateOperatorUI() {
        if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
            // 显示状态栏
            fullScreen(false);
        } else {
            // 隐藏状态栏
            fullScreen(true);
        }
    }


    private void fullScreen(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }


    /**
     * 开始缩放*
     */
    private void startZoom(float scale) {
        Log.i(TAG, "开始缩放");
        if (mRealPlayMgr == null) {
            return;
        }

        boolean preZoomIn = mZoomScale > 1.01 ? true : false;
        boolean zoomIn = scale > 1.01 ? true : false;
        if (mZoomScale != 0 && preZoomIn != zoomIn) {
            mRealPlayerHelper.setPtzCommand(mRealPlayMgr, mHandler, mZoomScale > 1.01 ? RealPlayStatus.PTZ_ZOOMIN
                    : RealPlayStatus.PTZ_ZOOMOUT, RealPlayStatus.PTZ_SPEED_DEFAULT, false);
            mZoomScale = 0;
        }
        if (scale != 0 && (mZoomScale == 0 || preZoomIn != zoomIn)) {
            mZoomScale = scale;
            mRealPlayerHelper.setPtzCommand(mRealPlayMgr, mHandler, mZoomScale > 1.01 ? RealPlayStatus.PTZ_ZOOMIN
                    : RealPlayStatus.PTZ_ZOOMOUT, RealPlayStatus.PTZ_SPEED_DEFAULT, true);
        }
    }

    /**
     * 停止缩放*
     */
    private void stopZoom() {
        if (mRealPlayMgr == null) {
            return;
        }
        if (mZoomScale != 0) {
            mRealPlayerHelper.setPtzCommand(mRealPlayMgr, mHandler, mZoomScale > 1.01 ? RealPlayStatus.PTZ_ZOOMIN
                    : RealPlayStatus.PTZ_ZOOMOUT, RealPlayStatus.PTZ_SPEED_DEFAULT, false);
            mZoomScale = 0;
        }
    }


    private void setPlayScaleUI(float scale, CustomRect oRect, CustomRect curRect) {
        Log.i(TAG, "zhe***改变=" + scale + ",mPlayScale=" + mPlayScale);
        if (scale == 1) {
            if (mPlayScale == scale) {
                return;
            }
            try {
                if (mRealPlayMgr != null) {
                    mRealPlayMgr.setDisplayRegion(false, null, null);
                } else if (mDemoRealPlayer != null) {
                    mDemoRealPlayer.setDisplayRegion(false, null, null);
                }
            } catch (BaseException e) {
                e.printStackTrace();
            }
        } else {
            if (mPlayScale == scale) {
                try {
                    if (mRealPlayMgr != null) {
                        mRealPlayMgr.setDisplayRegion(true, oRect, curRect);
                    } else if (mDemoRealPlayer != null) {
                        mDemoRealPlayer.setDisplayRegion(true, oRect, curRect);
                    }
                } catch (BaseException e) {
                    e.printStackTrace();
                }
                return;
            }
//            RelativeLayout.LayoutParams realPlayRatioTvLp = (RelativeLayout.LayoutParams) mRealPlayRatioTv
//                    .getLayoutParams();
            if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
//                realPlayRatioTvLp.setMargins(Utils.dip2px(this, 10), Utils.dip2px(this, 10), 0, 0);
            } else {
//                realPlayRatioTvLp.setMargins(Utils.dip2px(this, 70), Utils.dip2px(this, 20), 0, 0);
            }
//            mRealPlayRatioTv.setLayoutParams(realPlayRatioTvLp);
            Log.i(TAG, "scale=" + scale);
            String sacleStr = String.valueOf(scale);
            Log.i(TAG, "s=" + sacleStr.subSequence(0, Math.min(3, sacleStr.length())) + "X");
//            mRealPlayRatioTv.setText(sacleStr.subSequence(0, Math.min(3, sacleStr.length())) + "X");
//            mRealPlayRatioTv.setVisibility(View.VISIBLE);
//            mRealPlayControlRl.setVisibility(View.GONE);
//            mRealPlayFullOperateBar.setVisibility(View.GONE);
            try {
                Log.i(TAG1, "执行到这里了");
                if (mRealPlayMgr != null) {
                    Log.i(TAG1, "放大缩小" + "sacleStr=" + sacleStr + ",oRect=" + oRect + ",curRect=" + curRect);
                    mRealPlayMgr.setDisplayRegion(true, oRect, curRect);
                } else if (mDemoRealPlayer != null) {
                    mDemoRealPlayer.setDisplayRegion(true, oRect, curRect);
                }
            } catch (BaseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        mPlayScale = scale;
    }


    /**
     * 开始录像
     *
     * @see
     * @since V1.0
     */
    private void onRecordBtnClick() {
        mControlDisplaySec = 0;
        if (mRecordFilePath != null) {
            stopRealPlayRecord();
            return;
        }

        if (!SDCardUtil.isSDCardUseable()) {
            // 提示SD卡不可用
            Utils.showToast(this, R.string.remoteplayback_SDCard_disable_use);
            return;
        }

        if (SDCardUtil.getSDCardRemainSize() < SDCardUtil.PIC_MIN_MEM_SPACE) {
            // 提示内存不足
            Utils.showToast(this, R.string.remoteplayback_record_fail_for_memory);
            return;
        }

        if (mRealPlayMgr != null) {
            mCaptureDisplaySec = 4;
            updateCaptureUI();
            mAudioPlayUtil.playAudioFile(AudioPlayUtil.RECORD_SOUND);
            mRealPlayerHelper.startRecordTask(mRealPlayMgr, getResources(),
                    R.drawable.video_file_watermark);
        }
    }

    /**
     * 停止录像
     *
     * @see
     * @since V1.0
     */
    private void stopRealPlayRecord() {
        if (mRealPlayMgr == null || mRecordFilePath == null) {
            return;
        }

        // 设置录像按钮为check状态
        if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
            if (!mIsOnStop) {
                mRecordRotateViewUtil.applyRotation(mRealPlayRecordContainer, mRealPlayRecordStartBtn,
                        mRealPlayRecordBtn, 0, 90);
            } else {
                mRealPlayRecordStartBtn.setVisibility(View.GONE);
                mRealPlayRecordBtn.setVisibility(View.VISIBLE);
            }
//            mRealPlayFullRecordStartBtn.setVisibility(View.GONE);
//            mRealPlayFullRecordBtn.setVisibility(View.VISIBLE);
        } else {
            if (!mIsOnStop) {
//                mRecordRotateViewUtil.applyRotation(mRealPlayFullRecordContainer, mRealPlayFullRecordStartBtn,
//                        mRealPlayFullRecordBtn, 0, 90);
            } else {
//                mRealPlayFullRecordStartBtn.setVisibility(View.GONE);
//                mRealPlayFullRecordBtn.setVisibility(View.VISIBLE);

            }
            mRealPlayRecordStartBtn.setVisibility(View.GONE);
            mRealPlayRecordBtn.setVisibility(View.VISIBLE);
        }
        mAudioPlayUtil.playAudioFile(AudioPlayUtil.RECORD_SOUND);
        mRealPlayerHelper.stopRecordTask(mRealPlayMgr);

        // 计时按钮不可见
        mRealPlayRecordLy.setVisibility(View.GONE);
        mRealPlayCaptureRl.setVisibility(View.VISIBLE);
        mCaptureDisplaySec = 0;
        try {
            mRealPlayCaptureIv.setImageURI(Uri.parse(mRecordFilePath));
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        mRealPlayCaptureWatermarkIv.setTag(mRecordFilePath);
        mRecordFilePath = null;
        updateCaptureUI();
    }


    // 更新抓图/录像显示UI
    private void updateCaptureUI() {
        if (mRealPlayCaptureRl.getVisibility() == View.VISIBLE) {
            if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
                if (mRealPlayControlRl.getVisibility() == View.VISIBLE) {
                    mRealPlayCaptureRlLp.setMargins(0, 0, 0, Utils.dip2px(this, 40));
                } else {
                    mRealPlayCaptureRlLp.setMargins(0, 0, 0, 0);
                }
                mRealPlayCaptureRl.setLayoutParams(mRealPlayCaptureRlLp);
            } else {
                RelativeLayout.LayoutParams realPlayCaptureRlLp = new RelativeLayout.LayoutParams(
                        Utils.dip2px(this, 65), Utils.dip2px(this, 45));
                realPlayCaptureRlLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                realPlayCaptureRlLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                mRealPlayCaptureRl.setLayoutParams(realPlayCaptureRlLp);
            }
            if (mRealPlayCaptureWatermarkIv.getTag() != null) {
                mRealPlayCaptureWatermarkIv.setVisibility(View.VISIBLE);
                mRealPlayCaptureWatermarkIv.setTag(null);
            }
        }
        if (mCaptureDisplaySec >= 4) {
            mCaptureDisplaySec = 0;
            mRealPlayCaptureRl.setVisibility(View.GONE);
            mRealPlayCaptureIv.setImageURI(null);
            mRealPlayCaptureWatermarkIv.setTag(null);
            mRealPlayCaptureWatermarkIv.setVisibility(View.GONE);
        }
    }


}
