package com.cdhxqh.household_app.ui.actvity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cdhxqh.household_app.R;

/**
 * Created by hexian on 2015/8/14.
 */
public class Activity_Registry_Result extends BaseActivity {

    TextView error_code;
    TextView hint_code;
    ImageView backImg;     // 退回按钮
    ImageView settingImg; // 设置按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry_result);
        findViewById();
        initView();
    }

    public void findViewById(){
        error_code = (TextView)findViewById(R.id.error_code);
        hint_code = (TextView)findViewById(R.id.hint_code);
        settingImg = (ImageView)findViewById(R.id.title_add_id);
        backImg = (ImageView)findViewById(R.id.back_imageview_id);
    }

    public void initView(){
        settingImg.setVisibility(View.GONE);

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(Activity_Registry_Result.this, Activity_Registry_User.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivity(intent);*/
                Activity_Registry_Result.this.finish();
            }
        });

        SpannableStringBuilder style=new SpannableStringBuilder("如忘记密码，请点击找回密码!");
        // style.setSpan(new ForegroundColorSpan(Color.parseColor("#06ACE0")), 9, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );  // 不包含结尾
        style.setSpan(new ClickableSpan() {

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(ds.linkColor);
                ds.setUnderlineText(false); //去掉下划线
                ds.setColor(Color.parseColor("#06ACE0"));  // 设置点击后颜色
            }
            @Override
            public void onClick(View widget) {
                Log.i("aaa", "--------------------------------------->");
                Toast.makeText(Activity_Registry_Result.this, widget.toString(), Toast.LENGTH_LONG);
            }
        }, 9, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        hint_code.setText(style);//将其添加到tv中
    }

}
