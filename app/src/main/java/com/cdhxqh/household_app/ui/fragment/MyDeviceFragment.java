package com.cdhxqh.household_app.ui.fragment;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.MyDevice;
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

    private RecyclerView recyclerView;
    private MyDevicelistAdapter myDevicelistAdapter;
    Application application;
    EzvizAPI mEzvizAPI = EzvizAPI.getInstance();
    ArrayList<CameraInfo> result;
    AsyncTask task = new AsyncTask() {
        @Override
        protected Object doInBackground(Object[] params) {
            try {
                // 设置Token
                mEzvizAPI.setAccessToken("at.0nf6rqqz9rjls1u92cbzo7c28zmnprtr-96623ark6g-1dnt99a-rwoxhw6hx");
                GetCameraInfoList getCameraInfoList = new GetCameraInfoList();
                getCameraInfoList.setPageStart(0);
                getCameraInfoList.setPageSize(10);
                // 获取设备列表
                result = (ArrayList<CameraInfo>)mEzvizAPI.getCameraInfoList(getCameraInfoList);
                System.out.println("--------------------------->");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            if(NetWorkUtil.IsNetWorkEnable(getActivity()) && result!=null){
                addData();
            }
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        myDevicelistAdapter = new MyDevicelistAdapter(getActivity());
        recyclerView.setAdapter(myDevicelistAdapter);
        task.execute();
        return view;
    }

    /**
     * 从萤石云获取设备列表
     */
    private void addData() {
       myDevicelistAdapter.update(result, true);
       System.out.println("--------------------------->");
    }


}
