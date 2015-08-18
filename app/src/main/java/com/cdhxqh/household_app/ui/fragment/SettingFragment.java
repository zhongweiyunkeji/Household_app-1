package com.cdhxqh.household_app.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.ui.widget.DataCleanManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/18.
 */
public class SettingFragment extends BaseFragment{
    /**
     * 省流量
     */
    private RelativeLayout install_flow;

    /**
     *清除缓存
     */
    private RelativeLayout catchs;

    /**
     * 清除缓存
     */
    DataCleanManager dataCleanManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_my_settings, container, false);
        findViewById(view);
        initView();
        return view;
    }

    protected void findViewById(View view) {
        install_flow = (RelativeLayout)view.findViewById(R.id.install_flow);
        catchs = (RelativeLayout)view.findViewById(R.id.catchs);
    }

    protected void initView() {
//        install_flow.setOnClickListener(installFlowOnClickListener);
        catchs.setOnClickListener(catchsOnClickListener);
    }

    /**
     * 清除缓存
     */
    final Activity activity = this.getActivity();
    private View.OnClickListener catchsOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            DataCleanManager.cleanInternalCache(activity);//清除本应用内部缓存
//            DataCleanManager.cleanDatabases(activity);//清除本应用所有数据库
//            DataCleanManager.cleanSharedPreference(activity);//清除本应用SharedPreference
        }
    };

}
