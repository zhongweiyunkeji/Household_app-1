package com.cdhxqh.household_app.ui.fragment;

import android.app.Application;
import android.content.Intent;
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

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.config.Constants;
import com.cdhxqh.household_app.model.MyDevice;
import com.cdhxqh.household_app.ui.action.DeviceOnClick;
import com.cdhxqh.household_app.ui.actvity.Activity_Video_Control;
import com.cdhxqh.household_app.ui.adapter.MyDevicelistAdapter;
import com.cdhxqh.household_app.ui.widget.DividerItemDecoration;
import com.cdhxqh.household_app.ui.widget.NetWorkUtil;
import com.videogo.openapi.EzvizAPI;
import com.videogo.openapi.bean.req.GetCameraInfoList;
import com.videogo.openapi.bean.resp.CameraInfo;
import com.videogo.realplay.RealPlayerHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by think on 2015/8/15.
 */
public class MyDeviceFragment extends BaseFragment {
    private static final String TAG="MyDeviceFragment";
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
            intent.putExtras(bundle);
            intent.setClass(getActivity(),Activity_Video_Control.class);
            getActivity().startActivity(intent);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            application = (Application) getActivity().getApplication();
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
        myDevicelistAdapter.setShowSizeView(false); // 不显示报警记录总数
        recyclerView.setAdapter(myDevicelistAdapter);
        swipeRefreshLayout.setRefreshing(true);

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
        if (!result.isEmpty()) {
            currentPage++;
        } else {

        }
    }

    public class MyAsyncTask extends AsyncTask {

        public MyAsyncTask() {
            if (!swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(true);
            }
        }

        @Override
        protected Object doInBackground(Object[] params) {
            if (!isCancelled()) {
                try {
                    // 设置Token
                    mEzvizAPI.setAccessToken(Constants.TOKEN_URL);
                    GetCameraInfoList getCameraInfoList = new GetCameraInfoList();
                    getCameraInfoList.setPageStart(currentPage);
                    getCameraInfoList.setPageSize(showPage);
                    // 获取设备列表
                    result = (ArrayList<CameraInfo>) mEzvizAPI.getCameraInfoList(getCameraInfoList);
                    Log.i(TAG,"result="+result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return new ArrayList<CameraInfo>(0);
        }

        @Override
        protected void onPostExecute(Object o) {
            if (NetWorkUtil.IsNetWorkEnable(getActivity()) && result != null) {
                Log.i(TAG,"112345");
                addData();
            }
        }

    }

    /**
     * 初始化任务
     */
    private void startAsynTask() {
        new MyAsyncTask().execute();
    }

}
