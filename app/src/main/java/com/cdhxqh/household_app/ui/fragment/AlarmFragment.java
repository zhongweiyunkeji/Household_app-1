package com.cdhxqh.household_app.ui.fragment;

import android.app.Application;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.api.HttpRequestHandler;
import com.cdhxqh.household_app.api.Message;
import com.cdhxqh.household_app.app.HttpManager;
import com.cdhxqh.household_app.config.Constants;
import com.cdhxqh.household_app.ezviz.AlarmType;
import com.cdhxqh.household_app.model.Alarm;
import com.cdhxqh.household_app.ui.action.AlarmOnClickCallBack;
import com.cdhxqh.household_app.ui.action.DeviceOnClick;
import com.cdhxqh.household_app.ui.actvity.Activity_Alarm_Del;
import com.cdhxqh.household_app.ui.actvity.Activity_Alarm_List;
import com.cdhxqh.household_app.ui.actvity.Activity_Login;
import com.cdhxqh.household_app.ui.actvity.Activity_Video_Control;
import com.cdhxqh.household_app.ui.actvity.Activity_alarm_play;
import com.cdhxqh.household_app.ui.actvity.Load_Activity;
import com.cdhxqh.household_app.ui.actvity.MainActivity;
import com.cdhxqh.household_app.ui.adapter.AlarmItemAdapter;
import com.cdhxqh.household_app.ui.adapter.MyDevicelistAdapter;
import com.cdhxqh.household_app.ui.widget.DividerItemDecoration;
import com.cdhxqh.household_app.ui.widget.NetWorkUtil;
import com.cdhxqh.household_app.ui.widget.TestClass;
import com.cdhxqh.household_app.utils.MessageUtils;
import com.videogo.exception.BaseException;
import com.videogo.openapi.EzvizAPI;
import com.videogo.openapi.bean.req.GetAlarmInfoList;
import com.videogo.openapi.bean.req.GetCameraInfoList;
import com.videogo.openapi.bean.resp.AlarmInfo;
import com.videogo.openapi.bean.resp.CameraInfo;
import com.videogo.util.ConnectionDetector;
import com.videogo.util.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by hexian on 2015/8/17.
 */
public class AlarmFragment extends BaseFragment {

    int currentPage = 0; // 当前页(索引从0开始)
    int showPage = 10;   // 每页显示

    SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private MyDevicelistAdapter myDevicelistAdapter;
    Application application;
    EzvizAPI mEzvizAPI = EzvizAPI.getInstance();
    ArrayList<CameraInfo> result;
    DeviceOnClick callback = new DeviceOnClick(){
        public void callback(RecyclerView.ViewHolder holder, int position, View view, CameraInfo info){
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelable("device_name", info);
            bundle.putBoolean("showCheckBox", false);
            bundle.putBoolean("hideToolBar", false);
            intent.putExtras(bundle);
            intent.setClass(getActivity(), Activity_Alarm_List.class);
            getActivity().startActivity(intent);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity()!=null){
            application = (Application)getActivity().getApplication();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mydevice, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.mydevice_list);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        myDevicelistAdapter = new MyDevicelistAdapter(getActivity(), callback);
        myDevicelistAdapter.setShowDeviceSrarus(false); // 不显示设备状态
        recyclerView.setAdapter(myDevicelistAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startAsynTask();  // 请求网络数据
            }
        });

        startAsynTask();  // 请求网络数据

        return view;
    }

    /**
     * 从萤石云获取设备列表
     */
    private void addData() {
        swipeRefreshLayout.setRefreshing(false);

        myDevicelistAdapter.update(result, true);
        if(!result.isEmpty()){
            currentPage ++;
        } else {

        }
    }


    public class MyAsyncTask extends AsyncTask {

        public MyAsyncTask(){
            if(!swipeRefreshLayout.isRefreshing()){
                swipeRefreshLayout.setRefreshing(true);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            TestClass.loading(getActivity(), "正在加载数据，请稍后");
        }

        @Override
        protected Object doInBackground(Object[] params) {
            if(!isCancelled()){
                try {
                    // 设置Token
                    mEzvizAPI.setAccessToken(Constants.TOKEN_URL);
                    GetCameraInfoList getCameraInfoList = new GetCameraInfoList();
                    getCameraInfoList.setPageStart(currentPage);
                    getCameraInfoList.setPageSize(showPage);
                    int x = 1/0;  // 使用异常里面的数据来缓存
                    // 获取设备列表
                    result = (ArrayList<CameraInfo>)mEzvizAPI.getCameraInfoList(getCameraInfoList);
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                    result = new ArrayList<CameraInfo>(0);
                    CameraInfo f1 = new CameraInfo();
                    f1.setDeviceSerial("536724861");
                    f1.setPicUrl("https://i.ys7.com/assets/imgs/public/homeDevice.jpeg");
                    f1.setCameraName("C6(536724861)");
                    f1.setDeviceName(null);
                    f1.setDeviceId("e2b6ad92be744a2e8745473dd3dc3918536724861");
                    f1.setCameraId("24a7467d51a1484c9146b501f4ee34cf");
                    f1.setCameraNo(1);
                    f1.setDefence(0);
                    f1.setIsEncrypt(1);
                    f1.setIsShared(0);
                    f1.setStatus(1);

                    CameraInfo f2 = new CameraInfo();
                    f2.setDeviceSerial("536724535");
                    f2.setPicUrl("https://i.ys7.com/assets/imgs/public/homeDevice.jpeg");
                    f2.setCameraName("C6(536724535)");
                    f2.setDeviceName(null);
                    f2.setDeviceId("e2b6ad92be744a2e8745473dd3dc3918536724535");
                    f2.setCameraId("393dc7038a9041a5bd6173fea61364fc");
                    f2.setCameraNo(1);
                    f2.setDefence(0);
                    f2.setIsEncrypt(0);
                    f2.setIsShared(0);
                    f2.setStatus(1);

                    result.add(f1);
                    result.add(f2);
                    return result;
                }
            }
            if(result == null){
                result = new ArrayList<CameraInfo>(0);
            }
            return result;
        }

        @Override
        protected void onPostExecute(Object o) {
            if(NetWorkUtil.IsNetWorkEnable(getActivity()) && result!=null){
                addData();
            }
            TestClass.closeLoading();
        }

    }

    /**
     * 初始化任务
     */
    private void startAsynTask(){
        new MyAsyncTask().execute();
    }

}
