package com.cdhxqh.household_app.ui.actvity;

import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.cdhxqh.household_app.R;

/**
 * Created by hexian on 2015/8/15.
 */
public class Activity_Help_Center_Datail extends BaseActivity {

    WebView webView;
    ImageView backImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center_detail);
        findViewById();
        initView();
    }

    public void findViewById(){
        // webView = (WebView)findViewById(R.id.help_center_detail);
        backImg = (ImageView)findViewById(R.id.back_imageview_id);
    }

    public void initView(){

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setPluginState(WebSettings.PluginState.ON);
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //handler.cancel(); // Android默认的处理方式
                handler.proceed();  // 接受所有网站的证书
                //handleMessage(Message msg); // 进行其他处理
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String url = bundle.getString("url");
            try {
                webView.loadUrl(url);  //  "https://www.baidu.com/"
            } catch (Exception e) {
                e.printStackTrace();
                // Log.e("AAAA", "");
            }
        }

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
