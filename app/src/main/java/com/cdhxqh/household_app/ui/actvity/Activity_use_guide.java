package com.cdhxqh.household_app.ui.actvity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cdhxqh.household_app.R;

/**
 * Created by hexian on 2015/8/19.
 */
public class Activity_use_guide extends BaseActivity {

    ImageView backInmg;
    TextView titleText;
    ImageView settingImg;
    WebView webView;
    ProgressDialog progressDialog;   // 进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        findViewById();
        createProgressDialog();
        initView();
    }

    public void findViewById(){
        backInmg = (ImageView)findViewById(R.id.back_imageview_id);  // 退回按钮
        titleText = (TextView)findViewById(R.id.title_text_id);      // 中间title
        settingImg = (ImageView)findViewById(R.id.title_add_id);     // 右上角设置按钮
        webView = (WebView)findViewById(R.id.webview);
    }

    public void initView(){
        titleText.setText("功能介绍");
        settingImg.setVisibility(View.GONE);
        backInmg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //打开本包内asset目录下的index.html文件
        webView.loadUrl(" file:///android_asset/function.html");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
                super.onPageFinished(view, url);
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                progressDialog.dismiss();
                super.onReceivedError(view, errorCode, description, failingUrl);
                Toast.makeText(Activity_use_guide.this, "同步失败，请稍候再试", Toast.LENGTH_SHORT).show();
            }

        });


    }

    private void createProgressDialog() {
        progressDialog = ProgressDialog.show(this, null, "正在努力加载,请稍后... ...", true, true);
    }

}
