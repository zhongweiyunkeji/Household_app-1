package com.cdhxqh.household_app.ui.actvity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cdhxqh.household_app.R;

/**
 * Created by hexian on 2015/8/19.
 */
public class Activity_soft_suggest extends BaseActivity {

    ImageView backImg;
    TextView titleTextView;
    ImageView settingImg;

    RadioButton suggest;
    RadioButton complain;
    EditText name;
    EditText phone;
    EditText reqmsg;
    ImageView photo;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_suggest);
        findViewById();
        initView();
    }

    public void findViewById() {

        backImg = (ImageView) findViewById(R.id.back_imageview_id);
        titleTextView = (TextView) findViewById(R.id.title_text_id);
        settingImg = (ImageView) findViewById(R.id.title_add_id);

        suggest = (RadioButton) findViewById(R.id.suggest);    // 建议
        complain = (RadioButton) findViewById(R.id.complain);  // 投诉
        name = (EditText) findViewById(R.id.name);         // 姓名
        phone = (EditText) findViewById(R.id.phone);        // 手机号
        reqmsg = (EditText) findViewById(R.id.reqmsg);       // 建议内容
        photo = (ImageView) findViewById(R.id.photo);       // 照片
        submit = (Button) findViewById(R.id.submit);        // 提交
    }

    public void initView() {
        titleTextView.setText("意见反馈");
        settingImg.setVisibility(View.GONE);

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
