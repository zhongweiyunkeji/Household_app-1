package com.cdhxqh.household_app.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.config.Constants;
import com.cdhxqh.household_app.ui.actvity.Activity_Login;
import com.cdhxqh.household_app.ui.widget.DataCleanManager;
import com.cdhxqh.household_app.ui.widget.NetWorkUtil;
import com.cdhxqh.household_app.ui.widget.SwitchButton;
import com.cdhxqh.household_app.ui.widget.SwitchButtonIs;
import com.cdhxqh.household_app.utils.AccountUtils;

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

    /**
     * 是否的滑动开关
     */
    SwitchButtonIs wiperSwitch;

//    /**
//     *
//     */
//    DataCleanManager dataCleanManager;

    /**
     *回退
     */
    Button back_id;

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
        wiperSwitch = (SwitchButtonIs) view.findViewById(R.id.wiperSwitch);
        back_id = (Button) view.findViewById(R.id.back_id);
    }

    protected void initView() {
        catchs.setOnClickListener(catchsOnClickListener);
        back_id.setOnClickListener(backOnClickListener);
        wiperSwitch.setOnClickListener(new SwitchButtonIs.OnClickListener() {

            @Override
            public void onClick(View v) {
                Dialog();
            }
        });

        activity = this.getActivity();
    }

    /**
     * 清除缓存
     */
    private View.OnClickListener catchsOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DataCleanManager.cleanInternalCache(activity);
//            DataCleanManager.cleanDatabases(activity);
//            DataCleanManager.cleanSharedPreference(activity);
        }
    };

    /**
     * 2G或3G模式
     */
    public void getNetWork() {
        String type = NetWorkUtil.getCurrentNetworkType(activity);
        if (type.equals("2G") || type.equals("3G")) {
            Dialog();
        }
    }

    /**
     * 回退按钮
     */
    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.putExtra("close", false);
            intent.setClass(activity, Activity_Login.class);
            startActivityForResult(intent, 0);
            AccountUtils.removeAll(getActivity());
            activity.finish();
        }
    };

    /**
     * 确认或取消框
     */
    private void Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("打开后非wife网络环境下将不显示图片喔，确定打开吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //这里添加点击确定后的逻辑
//                showDialog("你选择了确定");
                wiperSwitch.setState(true);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                wiperSwitch.setState(false);
            }
        });
        builder.create().show();
    }

}
