package com.cdhxqh.household_app.ui.fragment;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.api.HttpRequestHandler;
import com.cdhxqh.household_app.api.Message;
import com.cdhxqh.household_app.app.HttpManager;
import com.cdhxqh.household_app.config.Constants;
import com.cdhxqh.household_app.model.Alarm;
import com.cdhxqh.household_app.ui.actvity.Activity_Login;
import com.cdhxqh.household_app.ui.actvity.Load_Activity;
import com.cdhxqh.household_app.ui.actvity.MainActivity;
import com.cdhxqh.household_app.ui.widget.TestClass;
import com.cdhxqh.household_app.utils.MessageUtils;

import java.util.ArrayList;

/**
 * Created by hexian on 2015/8/17.
 */
public class AlarmFragment extends BaseFragment {

    TextView leftTab;
    TextView rightTab;
    public boolean flag = false;
    private static final String count = "10";
    private static String currentPage = "1";

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
        leftTab.measure(0, 0);
        rightTab.measure(0, 0);

        /*final int tabsLeftPadding_left = leftTab.getPaddingLeft();
        final int tabsTopPadding_left = leftTab.getPaddingTop();
        final int tabsRightPadding_left = leftTab.getPaddingRight();
        final int tabsBottomPadding_left = leftTab.getPaddingBottom();

        final int tabsLeftPadding_right = rightTab.getPaddingLeft();
        final int tabsTopPadding_right = rightTab.getPaddingTop();
        final int tabsRightPadding_right = rightTab.getPaddingRight();
        final int tabsBottomPadding_right = rightTab.getPaddingBottom();*/

        final int leftWidth = leftTab.getMeasuredWidth();
        final int leftHeight = leftTab.getMeasuredHeight();
        final int rightWidth = rightTab.getMeasuredWidth();
        final int rightHeight = rightTab.getMeasuredHeight();


        leftTab.setOnClickListener(new View.OnClickListener() {  // 三个月内
            @Override
            public void onClick(View v) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)leftTab.getLayoutParams();
                params.weight = leftWidth;
                params.height = leftHeight;
                params.weight = 1;

                params = (LinearLayout.LayoutParams)rightTab.getLayoutParams();
                params.weight = rightWidth;
                params.height = rightHeight;
                params.weight = 1;

                leftTab.setBackgroundResource(R.drawable.tabs_left_sel);
                leftTab.setTextColor(Color.parseColor("#FFFFFF"));
                rightTab.setBackgroundResource(R.drawable.tabs_right_nol);
                rightTab.setTextColor(Color.parseColor("#7C8586"));
                /*leftTab.setPadding(tabsLeftPadding_left, tabsTopPadding_left, tabsRightPadding_left, tabsBottomPadding_left);
                rightTab.setPadding(tabsLeftPadding_right, tabsTopPadding_right, tabsRightPadding_right, tabsBottomPadding_right);*/
                if(flag){
                    return;
                }

                initThreeInFragment();
            }
        });

        rightTab.setOnClickListener(new View.OnClickListener() {  // 三个月前
            @Override
            public void onClick(View v) {// 三个月前
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)leftTab.getLayoutParams();
                params.weight = leftWidth;
                params.height = leftHeight;
                params.weight = 1;

                params = (LinearLayout.LayoutParams)rightTab.getLayoutParams();
                params.weight = rightWidth;
                params.height = rightHeight;
                params.weight = 1;

                leftTab.setBackgroundResource(R.drawable.tabs_left_nol);
                leftTab.setTextColor(Color.parseColor("#7C8586"));
                rightTab.setBackgroundResource(R.drawable.tabs_right_sel);
                rightTab.setTextColor(Color.parseColor("#FFFFFF"));
                /*leftTab.setPadding(tabsLeftPadding_left, tabsTopPadding_left, tabsRightPadding_left, tabsBottomPadding_left);
                rightTab.setPadding(tabsLeftPadding_right, tabsTopPadding_right, tabsRightPadding_right, tabsBottomPadding_right);*/
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
        Bundle bundle = new Bundle();
        bundle.putBoolean("showCheckBox", false);
        bundle.putBoolean("hideToolBar", true);
        in.setArguments(bundle);
        transaction.replace(R.id.alarm_container, in, "ThreeInAlarmFragment");
        transaction.commit();
        flag = true;
        getHttpUtil(1);
    }

    public void initThreeOutFragment() {
        FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
        ThreeOutAlarmFragment out = new ThreeOutAlarmFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("showCheckBox", false);
        bundle.putBoolean("hideToolBar", true);
        out.setArguments(bundle);
        transaction.replace(R.id.alarm_container, out, "ThreeOutAlarmFragment");
        transaction.commit();
        flag = false;
        getHttpUtil(2);
    }

    public void update() {

    }

    /**
     * 访问网络
     * @param
     */
    private void getHttpUtil (int i) {
        /**
         * 加载中
         */
        SharedPreferences myShared = getActivity().getSharedPreferences(Constants.USER_INFO, Context.MODE_PRIVATE);
        TestClass.loading(getActivity(), getString(R.string.loading));
        if(i == 1) {
            HttpManager.filterManager(myShared.getString(Constants.SESSIONIDTRUE, ""), getActivity(), true, handler, Message.ALARM, "datenote", "showCount", "currentPage", "before", count, currentPage);
        }else if(i == 2){
            HttpManager.filterManager(myShared.getString(Constants.SESSIONIDTRUE, ""), getActivity(), true, handler, Message.ALARM, "datenote", "showCount", "currentPage", "after", count, currentPage);
        }
    }

    /**
     *
     */
    HttpRequestHandler<ArrayList<Alarm>> handler = new HttpRequestHandler<ArrayList<Alarm>>() {
        @Override
        public void onSuccess(ArrayList<Alarm> data) {
            TestClass.closeLoading();
            if(data != null) {
                if(data.get(0).isBack() == true) {
//                    MessageUtils.showErrorMessage(getActivity(), "会话过期，请重新登录");
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), Activity_Login.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }
            }

        }

        @Override
        public void onSuccess(ArrayList<Alarm> data, int totalPages, int currentPage) {

        }

        @Override
        public void onFailure(String error) {
            MessageUtils.showErrorMessage(getActivity(), error);
            TestClass.closeLoading();
        }
    };
}
