package com.cdhxqh.household_app.ui.actvity;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.ui.fragment.ThreeInAlarmFragment;
import com.cdhxqh.household_app.ui.fragment.ThreeOutAlarmFragment;

/**
 * Created by hexian on 2015/8/18.
 */
public class Activity_Alarm_Del extends BaseActivity {

    TextView leftTab;
    TextView rightTab;
    //RelativeLayout relativeLayout;
    public boolean flag = false;

    ImageView backImg;
    TextView titleTextView;
    ImageView addImg;

    CheckBox checkdAll;
    ImageView delBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        flag = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
        findViewById();
        initView();
    }

    public void findViewById(){
        backImg =  (ImageView)findViewById(R.id.back_imageview_id);
        titleTextView = (TextView)findViewById(R.id.title_text_id);

        addImg =  (ImageView)findViewById(R.id.title_add_id);
        leftTab =  (TextView)findViewById(R.id.tabs_left);
        rightTab = (TextView)findViewById(R.id.tabs_right);
    }

    public void initView(){

        titleTextView.setText("报警记录");
        addImg.setVisibility(View.GONE);

        backImg.setOnClickListener(new View.OnClickListener() {  // 退回按钮事件
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final int tabsLeftPadding_left = leftTab.getPaddingLeft();
        final int tabsTopPadding_left = leftTab.getPaddingTop();
        final int tabsRightPadding_left = leftTab.getPaddingRight();
        final int tabsBottomPadding_left = leftTab.getPaddingBottom();

        final int tabsLeftPadding_right = rightTab.getPaddingLeft();
        final int tabsTopPadding_right = rightTab.getPaddingTop();
        final int tabsRightPadding_right = rightTab.getPaddingRight();
        final int tabsBottomPadding_right = rightTab.getPaddingBottom();

        leftTab.setOnClickListener(new View.OnClickListener() {  // 三个月内
            @Override
            public void onClick(View v) {
                leftTab.setBackgroundResource(R.drawable.tabs_left_sel);
                leftTab.setTextColor(Color.parseColor("#FFFFFF"));
                rightTab.setBackgroundResource(R.drawable.tabs_right_nol);
                rightTab.setTextColor(Color.parseColor("#7C8586"));
                leftTab.setPadding(tabsLeftPadding_left, tabsTopPadding_left, tabsRightPadding_left, tabsBottomPadding_left);
                rightTab.setPadding(tabsLeftPadding_right, tabsTopPadding_right, tabsRightPadding_right, tabsBottomPadding_right);
                if(flag){
                    return;
                }

                initThreeInFragment();
            }
        });

        rightTab.setOnClickListener(new View.OnClickListener() {  // 三个月前
            @Override
            public void onClick(View v) {// 三个月钱前
                leftTab.setBackgroundResource(R.drawable.tabs_left_nol);
                leftTab.setTextColor(Color.parseColor("#7C8586"));
                rightTab.setBackgroundResource(R.drawable.tabs_right_sel);
                rightTab.setTextColor(Color.parseColor("#FFFFFF"));
                leftTab.setPadding(tabsLeftPadding_left, tabsTopPadding_left, tabsRightPadding_left, tabsBottomPadding_left);
                rightTab.setPadding(tabsLeftPadding_right, tabsTopPadding_right, tabsRightPadding_right, tabsBottomPadding_right);
                if(!flag){
                    return;
                }


                initThreeOutFragment();
            }
        });

        initThreeInFragment();

    }

    public void initThreeInFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        ThreeInAlarmFragment in = new ThreeInAlarmFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("showCheckBox", true);
        bundle.putBoolean("hideToolBar", false);
        in.setArguments(bundle);
        transaction.replace(R.id.alarm_container, in, "ThreeInAlarmFragment");
        transaction.commit();
        flag = true;
    }

    public void initThreeOutFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        ThreeOutAlarmFragment out = new ThreeOutAlarmFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("showCheckBox", true);
        bundle.putBoolean("hideToolBar", false);
        out.setArguments(bundle);
        transaction.replace(R.id.alarm_container, out, "ThreeOutAlarmFragment");
        transaction.commit();
        flag = false;
    }





}
