package com.cdhxqh.household_app.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.ui.widget.DataCleanManager;
import com.cdhxqh.household_app.ui.widget.NetWorkUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/18.
 */
public class SettingFragment extends BaseFragment {
    /**
     * 2G或3G
     */
    private RelativeLayout install_flow;

    /**
     * 清除缓存
     */
    private RelativeLayout catchs;

    Activity activity;

//    /**
//     * 清除缓存
//     */
//    DataCleanManager dataCleanManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_settings, container, false);
        findViewById(view);
        initView();
        return view;
    }

    protected void findViewById(View view) {
        install_flow = (RelativeLayout) view.findViewById(R.id.install_flow);
        catchs = (RelativeLayout) view.findViewById(R.id.catchs);
    }

    protected void initView() {
        install_flow.setOnClickListener(installFlowOnClickListener);
        catchs.setOnClickListener(catchsOnClickListener);

        activity = this.getActivity();
    }

    /**
     * 清除缓存
     */
    private View.OnClickListener catchsOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            DataCleanManager.cleanInternalCache(activity);
//            DataCleanManager.cleanDatabases(activity);
//            DataCleanManager.cleanSharedPreference(activity);
        }
    };

    /**
     * 2G或3G模式
     */
    private View.OnClickListener installFlowOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String type = NetWorkUtil.getCurrentNetworkType(activity);
            if (type.equals("Wi-Fi") || type.equals("4G")) {
                new AlertDialog.Builder(activity).setMessage("wife网络环境").setPositiveButton("确定", null).show();
            } else if (type.equals("2G") || type.equals("3G")) {
                new AlertDialog.Builder(activity).setMessage("打开后非wife网络环境下将不显示图片喔，确定打开吗？").setPositiveButton("确定", null).show();
            }
        }
    };

}
