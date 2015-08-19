package com.cdhxqh.household_app.ui.actvity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.Window;
import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.ui.widget.dialog.CustomDialog;

/**
 * Created by hexian on 2015/8/19.
 */
public class ActivityAbout extends BaseActivity {

    TextView softUpdate;
    TextView softGuide;
    TextView softCriticism;
    TextView companyIntrodution;
    TextView welcomePage;
    TextView softFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        findViewById();
        initView();
    }

    public void findViewById(){
         softUpdate = (TextView)findViewById(R.id.soft_update);                     // 版本更新
         softGuide = (TextView)findViewById(R.id.soft_guide);                       // 功能介绍
         softCriticism = (TextView)findViewById(R.id.soft_criticism);              // 给我评分
         companyIntrodution = (TextView)findViewById(R.id.company_introduction);  // 公司介绍
         welcomePage = (TextView)findViewById(R.id.welcome_page);                  // 欢迎页
         softFeedback = (TextView)findViewById(R.id.soft_feedback);                // 意见反馈
    }

    public void initView(){
        softUpdate.setOnClickListener(new View.OnClickListener() {// 版本更新
            @Override
            public void onClick(View v) {
                CustomDialog dialog = new CustomDialog(ActivityAbout.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题
                dialog.setCancelable(false);// 设置模态对话框
                dialog.show();  // 显示对话框
            }
        });

        softGuide.setOnClickListener(new View.OnClickListener() {// 功能介绍
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityAbout.this, Activity_use_guide.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        softCriticism.setOnClickListener(new View.OnClickListener() {// 给我评分
            @Override
            public void onClick(View v) {

            }
        });

        companyIntrodution.setOnClickListener(new View.OnClickListener() {// 公司介绍
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityAbout.this, Activity_company_introduce.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        welcomePage.setOnClickListener(new View.OnClickListener() {// 欢迎页
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putBoolean("ActivityAbout", true);
                intent.putExtras(bundle);
                intent.setClass(ActivityAbout.this, Activity_First_login.class);
                startActivity(intent);
            }
        });

        softFeedback.setOnClickListener(new View.OnClickListener() { // 意见反馈
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                intent.setClass(ActivityAbout.this, Activity_soft_suggest.class);
                startActivity(intent);
            }
        });
    }

}