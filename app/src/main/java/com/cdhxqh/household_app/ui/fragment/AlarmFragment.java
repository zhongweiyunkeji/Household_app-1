package com.cdhxqh.household_app.ui.fragment;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.cdhxqh.household_app.R;

/**
 * Created by hexian on 2015/8/17.
 */
public class AlarmFragment extends BaseFragment {

    TextView leftTab;
    TextView rightTab;
    public boolean flag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        flag = false;
        View view = inflater.inflate(R.layout.fragment_alarm_list, container, false);
        findViewById(view);
        initView();
        return view;
    }

    public void findViewById(View view) {
        leftTab =  (TextView)view.findViewById(R.id.tabs_left);
        rightTab = (TextView)view.findViewById(R.id.tabs_right);
    }

    public void initView() {

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
        FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
        ThreeInAlarmFragment in = new ThreeInAlarmFragment();
        transaction.replace(R.id.alarm_container, in, "ThreeInAlarmFragment");
        transaction.commit();
        flag = true;
    }

    public void initThreeOutFragment() {
        FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
        ThreeOutAlarmFragment out = new ThreeOutAlarmFragment();
        transaction.replace(R.id.alarm_container, out, "ThreeOutAlarmFragment");
        transaction.commit();
        flag = false;
    }






}
