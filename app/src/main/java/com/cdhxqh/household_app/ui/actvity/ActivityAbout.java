package com.cdhxqh.household_app.ui.actvity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cdhxqh.household_app.R;

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
         softUpdate = (TextView)findViewById(R.id.soft_update);                     // �汾����
         softGuide = (TextView)findViewById(R.id.soft_guide);                       // ���ܽ���
         softCriticism = (TextView)findViewById(R.id.soft_criticism);              // ��������
         companyIntrodution = (TextView)findViewById(R.id.company_introduction);  // ��˾����
         welcomePage = (TextView)findViewById(R.id.welcome_page);                  // ��ӭҳ
         softFeedback = (TextView)findViewById(R.id.soft_feedback);                // �������
    }

    public void initView(){
        softUpdate.setOnClickListener(new View.OnClickListener() {// �汾����
            @Override
            public void onClick(View v) {

            }
        });

        softGuide.setOnClickListener(new View.OnClickListener() {// ���ܽ���
            @Override
            public void onClick(View v) {

            }
        });

        softCriticism.setOnClickListener(new View.OnClickListener() {// ��������
            @Override
            public void onClick(View v) {

            }
        });

        companyIntrodution.setOnClickListener(new View.OnClickListener() {// ��˾����
            @Override
            public void onClick(View v) {

            }
        });

        welcomePage.setOnClickListener(new View.OnClickListener() {// ��ӭҳ
            @Override
            public void onClick(View v) {

            }
        });

        softFeedback.setOnClickListener(new View.OnClickListener() { // �������
            @Override
            public void onClick(View v) {

            }
        });
    }

}
