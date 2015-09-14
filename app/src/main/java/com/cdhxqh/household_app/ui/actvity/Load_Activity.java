package com.cdhxqh.household_app.ui.actvity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.app.HttpManager;
import com.cdhxqh.household_app.config.Constants;
import com.cdhxqh.household_app.ui.action.HttpCallBackHandle;
import com.cdhxqh.household_app.utils.filedownload.DownloadProgressListener;
import com.cdhxqh.household_app.utils.filedownload.FileDownloader;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Entry Activity
 *
 * @author bxbxbai
 */
public class Load_Activity extends BaseActivity {

    private static final int ANIMATION_DURATION = 2000;
    private static final float SCALE_END = 1.13F;
    // 更新文件的大小
    public  int filesize = -10;

    public int threadSize = 5;
    // 文件保存位置(不包括文件名)
    public String savePath = "";

    public long startTime = 0;  // 下载开始时间
    public long endTime = 0;    // 下载结束时间

    /**
     * 当Handler被创建会关联到创建它的当前线程的消息队列，该类用于往消息队列发送消息
     * 消息队列中的消息由当前线程内部进行处理
     */
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                   /* progressBar.setProgress(msg.getData().getInt("size"));
                    float num = (float)progressBar.getProgress()/(float)progressBar.getMax();
                    int result = (int)(num*100);
                    resultView.setText(result+ "%");

                    if(progressBar.getProgress()==progressBar.getMax()){
                        Toast.makeText(Load_Activity.this, R.string.success, 1).show();
                    }*/
                    break;
                case 2:
                    endTime = System.currentTimeMillis();
                    Toast.makeText(Load_Activity.this, "下载文件费时 "+(endTime-startTime)/1000+"秒", Toast.LENGTH_LONG).show();;
                    String apkname = msg.getData().getString("apkname");
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse("file:/" + Load_Activity.this.savePath + apkname), "application/and.android.package-archive");
                    startActivity(intent);
                    Load_Activity.this.finish();
                    break;
                case -1:
                    // Toast.makeText(Load_Activity.this, R.string.error, 1).show();
                    break;
            }
        }
    };

    private static final int[] SPLASH_ARRAY = {
            R.drawable.front_cover,

    };

    ImageView mSplashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_);
        mSplashImage=(ImageView)findViewById(R.id.iv_entry);
        mSplashImage.setImageResource(R.drawable.front_cover);
        animateImage();
    }


    private void animateImage() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mSplashImage, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mSplashImage, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_DURATION).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // 第1步：检查软件更新
                // checkSoftVersion();
                startMainActivity();
            }
        });
    }

    public void checkSoftVersion(){
        RequestParams maps = new RequestParams();
        HttpManager.sendHttpRequest(this, Constants.SOFT_UPDATE_PATH, maps, "get", false, new HttpCallBackHandle() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                if (responseBody != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody);
                        String errcode = jsonObject.getString("errcode");
                        if ("SECURITY-GLOBAL-S-0".equals(errcode)) {
                            JSONArray array = jsonObject.getJSONArray("result");
                            if (array != null) {
                                JSONObject result = array.getJSONObject(0);
                                int versionCode = result.getInt("versionCode");
                                int size = result.getInt("size");
                                String url = result.getString("url");
                                String versionName = result.getString("versionCode");

                                PackageManager pm = getPackageManager();
                                PackageInfo info = pm.getPackageInfo(Load_Activity.this.getPackageName(), PackageManager.GET_CONFIGURATIONS);
                                int curVersionCode = info.versionCode;
                                String curVersionName = info.versionName;
                                if (curVersionCode > versionCode) {
                                    // 第2步：更新软件
                                    String path = "";
                                    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){ // SD卡存在且已挂载成功
                                        // --->  mnt/sdcord/household_app/
                                        path = Environment.getExternalStorageDirectory().getAbsolutePath() +File.separator+"household_app"+File.separator;
                                    } else {
                                        // 获取文件安装包位置
                                        path = getApplicationContext().getPackageResourcePath();
                                        path = path.substring(0, path.lastIndexOf('/') + 1);
                                    }

                                    Load_Activity.this.savePath = path;
                                    File savePath =  new File(path);
                                    if(!savePath.exists()){
                                        savePath.mkdir();
                                    }
                                    download("https://dl.wandoujia.com/files/jupiter/latest/wandoujia-wandoujia_web.apk", savePath);
                                    return;
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // 打开应用程序主界面
                    startMainActivity();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                String path = "";
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){ // SD卡存在且已挂载成功
                    // --->  mnt/sdcord/household_app/
                    path = Environment.getExternalStorageDirectory().getAbsolutePath() +File.separator+"household_app"+File.separator;
                } else {
                    // 获取文件安装包位置
                    path = getApplicationContext().getPackageResourcePath();
                    path = path.substring(0, path.lastIndexOf('/') + 1);
                }
                Load_Activity.this.savePath = path;
                File savePath =  new File(path);
                download("https://dl.wandoujia.com/files/jupiter/latest/wandoujia-wandoujia_web.apk", savePath);
                // startMainActivity();
            }
        });
    }


    /**
     * 主线程(UI线程)
     * 对于显示控件的界面更新只是由UI线程负责，如果是在非UI线程更新控件的属性值，更新后的显示界面不会反映到屏幕上
     * @param path     文件下载网址
     * @param savedir  保存在本地的文件路径(不包括文件名)
     */
    private void download(final String path, final File savedir) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                startTime = System.currentTimeMillis();
                final FileDownloader loader = new FileDownloader(Load_Activity.this, path, savedir, threadSize);
                // progressBar.setMax(loader.getFileSize());//设置进度条的最大刻度为文件的长度
                try {
                    loader.download(new DownloadProgressListener() {
                        @Override
                        public void onDownloadSize(int downloadsize, int filesize) {//实时获知文件已经下载的数据长度
                            Message msg = new Message();
                            if(downloadsize!=filesize){
                                msg.what = 1;
                                msg.getData().putInt("size", downloadsize);
                                handler.sendMessage(msg);//发送消息
                            } else { // 文件下载完成
                                msg.what = 2;
                                msg.getData().putString("apkname", loader.getApkname());
                                handler.sendMessage(msg);//发送消息
                            }
                        }
                    });
                } catch (Exception e) {
                    handler.obtainMessage(-1).sendToTarget();
                }
            }
        }).start();
    }

    public void startMainActivity() {
        Intent intent = new Intent();
        if (!mIsLogin || myshared.getString(Constants.PASS_KEY, "").equals("")) {
            intent.setClass(Load_Activity.this, Activity_Login.class);
        } else {
            intent.setClass(Load_Activity.this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
