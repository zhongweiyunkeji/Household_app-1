package com.cdhxqh.household_app.utils.filedownload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.app.HttpManager;
import com.cdhxqh.household_app.config.Constants;
import com.cdhxqh.household_app.ui.action.HttpCallBackHandle;
import com.cdhxqh.household_app.ui.actvity.ActivityAbout;
import com.cdhxqh.household_app.ui.actvity.BaseActivity;
import com.cdhxqh.household_app.ui.actvity.Load_Activity;
import com.cdhxqh.household_app.ui.actvity.MainActivity;
import com.cdhxqh.household_app.ui.widget.dialog.CustomDialog;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 单线程下载文件
 */
public class UpdateManager {
    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    /* 更新时网络异常 */
    private static final int NETWORK_EXCEPTION = 3;

    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    private int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;

    private Activity mContext;
    /* 更新进度条 */
    private ProgressBar mProgress;
    private Dialog mDownloadDialog;
    private String apkname;

    private long startTime = 0;
    private long endTime = 0;

    CustomDialog dialog = null;
    String[] serverArray = null;
    TextView tipMsg;

    Button canBtn; // 取消按钮

    private long startDownloadTime = 0;  // 每次下载的开始时间
    private long endDownloadTime = 0;    // 每次下载的结束时间

    private long startDownloadSize = 0;  // 每次下载的开始字节数
    private long endDownloadSize = 0;    // 每次下载的结束字节数

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
            // 正在下载
            case DOWNLOAD:
                // 设置进度条位置
                mProgress.setProgress(progress);
                long time = (endDownloadTime-startDownloadTime)/1000;  // + 500是为减少时间误差(四舍五入)
                long size = (endDownloadSize-startDownloadSize + 512)/1024;  // + 512是为减少时间误差(四舍五入)
                double speed = 0;
                if(time == 0){
                    speed = (((double)size)/(endDownloadTime-startDownloadTime))/1000;
                } else {
                    speed = ((double)size)/time;
                }
                DecimalFormat df = new DecimalFormat("0.00");
                String speedStr =  df.format(speed);
                String tip =  MessageFormat.format("已完成{0}%，当前网络下载速度{1}k/s", new Object[]{""+progress, speedStr});
                tipMsg.setText(tip);
                break;
            case DOWNLOAD_FINISH:
                cancelUpdate = true;
                endTime = System.currentTimeMillis();
                tipMsg.setText("下载文件共费时"+((endTime-startTime)/1000)+"秒");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(mContext instanceof Load_Activity){
                    // 安装文件
                    installApk();
                    mContext.finish();
                } else
                if(mContext instanceof ActivityAbout){
                    installApk();
                    MainActivity mainActivity = ((ActivityAbout) mContext).mainActivity;
                    mContext.finish();
                    if(mainActivity!=null){
                        mainActivity.finish();
                    }
                }
                break;
            case NETWORK_EXCEPTION :
                Toast.makeText(mContext, "软件更新失败,请检查您的网络连接!", Toast.LENGTH_LONG).show();
                cancelUpdate = true;
                mDownloadDialog.dismiss();
                if(mContext instanceof Load_Activity){
                    startActivity();
                }
                break;
            default:
                break;
            }
        };
    };

    public UpdateManager(Activity context) {
        this.mContext = context;
        dialog = new CustomDialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题
        dialog.setCancelable(false);// 设置模态对话框
    }

    // 发送网络请求获取服务器版本信息
    public void checkSoftVersion(){
        RequestParams maps = new RequestParams();
        HttpManager.sendHttpRequest(mContext, Constants.SOFT_UPDATE_PATH, maps, "get", false, new HttpCallBackHandle() {
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
                                int serviceCode = result.getInt("versionCode");
                                String url = result.getString("url");
                                String versionName = result.getString("versionName");
                                serverArray = new String[3];
                                serverArray[0] = "" + serviceCode;
                                serverArray[1] = url;
                                serverArray[2] = versionName;

                                // 获取当前软件版本
                                int versionCode = getVersionCode(mContext);
                                // 版本判断
                                if (serviceCode > versionCode) {
                                    File saveFile = getDownloadApkFile();
                                    showNoticeDialog();
                                    if(saveFile.exists()){ // 文件不存在
                                    } else {// 文件存在,则检查文件的完整性
                                    }

                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                if(mContext instanceof Load_Activity){
                    Toast.makeText(mContext, "软件更新失败,请检查您的网络连接!", Toast.LENGTH_LONG).show();
                    ((Load_Activity) mContext).startMainActivity();
                }
            }
        });
    }


    /**
     * 获取软件版本号
     */
    private int getVersionCode(Context context) {
        int versionCode =  -10;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            versionCode = packageInfo.versionCode;;
            // String curVersionName = packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }

    /**
     * 显示软件更新对话框
     */
    public void showNoticeDialog() {
        dialog.setCallback(this);
        dialog.show();
        // 显示下载对话框
        dialog.softVerson.setText("最新版本:"+serverArray[2]);
    }

    /**
     * 显示软件下载对话框
     */
    public void showDownloadDialog() {
        // 构造软件下载对话框
        AlertDialog.Builder builder = new Builder(mContext);
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.activity_dialog_update, null);
        mProgress = (ProgressBar)v.findViewById(R.id.progressBar);
        tipMsg = (TextView)v.findViewById(R.id.tipMsg);
        canBtn = (Button)v.findViewById(R.id.canBtn);
        builder.setView(v);
        // 取消更新
        canBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDownloadDialog.dismiss();
                // 设置取消状态
                cancelUpdate = true;
                if(mContext instanceof Load_Activity){
                    ((Load_Activity)mContext).startMainActivity();
                }
            }
        });
        mDownloadDialog = builder.create();
        mDownloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题
        mDownloadDialog.setCancelable(false);// 设置模态对话框
        mDownloadDialog.show();
        // 下载文件
        downloadApk();
    }

    /**
     * 下载apk文件
     */
    private void downloadApk() {
        startTime = System.currentTimeMillis();
        // 启动新线程下载软件
        new downloadApkThread().start();
    }

    /**
     * 下载文件线程
     *
     * @author coolszy
     *@date 2012-4-26
     *@blog http://blog.92coding.com
     */
    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            FileOutputStream fos = null;
            try {
                File savePath = getDownloadApkFile();
                mSavePath = savePath.getPath();
                URL url = new URL(serverArray[1]);
                apkname = serverArray[1].substring(serverArray[1].lastIndexOf('/') + 1);
                // 创建连接
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                if (conn.getResponseCode()==200) {// 请求成功
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();

                    File file = new File(mSavePath);
                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath, apkname);
                    fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    startDownloadTime = System.currentTimeMillis();  // 记录本次下载开始时间
                    startDownloadSize = count;                       // 记录本次下载开始字节数
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        endDownloadSize = count;                       // 记录本次下载开始字节数
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        if (numread <= 0) {
                            // 下载完成
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);
                        fos.flush();
                        endDownloadTime = System.currentTimeMillis();  // 记录本次下载结束时间
                    } while (!cancelUpdate);// 点击取消就停止下载.
                    fos.close();
                    is.close();
                } else {
                    Log.i("TAG", "ResponseCode-------------------------------------------------------->"+conn.getResponseCode());
                    mHandler.sendEmptyMessage(NETWORK_EXCEPTION);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                mHandler.sendEmptyMessage(NETWORK_EXCEPTION);
            } catch (IOException e) {
                mHandler.sendEmptyMessage(NETWORK_EXCEPTION);
                e.printStackTrace();
            } finally {
                fos = null;
            }
            // 取消下载对话框显示
            mDownloadDialog.dismiss();
        }
    };

    /**
     * 安装APK文件
     */
    private void installApk() {
        File apkfile = new File(mSavePath, apkname);
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);
        mDownloadDialog.dismiss();
    }

    public void startActivity(){
        if(mContext instanceof Load_Activity){ // 启动应用程序
            ((Load_Activity)mContext).startMainActivity();
        } else {

        }
    }

    private File getDownloadApkFile(){
        String path = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { // SD卡存在且已挂载成功
            // --->  mnt/sdcord/household_app/
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "household_app" + File.separator;
        } else {
            // 获取文件安装包位置
            path = mContext.getApplicationContext().getPackageResourcePath();
            path = path.substring(0, path.lastIndexOf('/') + 1);
        }
        mSavePath = path;
        File savePath = new File(path);

        return savePath;
    }

}