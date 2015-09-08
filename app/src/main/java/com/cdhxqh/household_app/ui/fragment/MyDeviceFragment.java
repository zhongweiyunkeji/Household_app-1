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
import com.cdhxqh.household_app.app.HttpManager;
import com.cdhxqh.household_app.config.Constants;
import com.cdhxqh.household_app.model.MyDevice;
import com.cdhxqh.household_app.ui.action.DeviceOnClick;
import com.cdhxqh.household_app.ui.actvity.Activity_Video_Control;
import com.cdhxqh.household_app.ui.adapter.MyDevicelistAdapter;
import com.cdhxqh.household_app.ui.widget.DividerItemDecoration;
import com.cdhxqh.household_app.ui.widget.NetWorkUtil;
import com.cdhxqh.household_app.ui.widget.TestClass;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.videogo.openapi.EzvizAPI;
import com.videogo.openapi.bean.req.GetCameraInfoList;
import com.videogo.openapi.bean.resp.AlarmInfo;
import com.videogo.openapi.bean.resp.CameraInfo;
import com.videogo.realplay.RealPlayerHelper;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
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
    ArrayList<MyDevice> result = new ArrayList<MyDevice>(0);
    DeviceOnClick callback = new DeviceOnClick(){
        public void callback(RecyclerView.ViewHolder holder, int position, View view, MyDevice info){
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("device_name", info);
            intent.putExtras(bundle);
            intent.setClass(getActivity(),Activity_Video_Control.class);
            getActivity().startActivityForResult(intent, 0);
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
        myDevicelistAdapter.setShowSwitch(true);
        myDevicelistAdapter.update(result, true);
        if (!result.isEmpty()) {
            currentPage++;
        } else {

        }
    }

    /**
     * 初始化任务
     */
    private void startAsynTask() {
        TestClass.loading(getActivity(), "正在加载数据，请稍后");
        getDeviceList();
    }

    private void getDeviceList(){
        RequestParams maps = new RequestParams();
        maps.put("showCount", showPage);
        maps.put("currentPage", currentPage);
        AsyncHttpClient client = new AsyncHttpClient();
        HttpManager.sendHttpRequest(getActivity(), Constants.DEVICE_LIST, maps, responseHandler, "get");
    }

    AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            if(responseBody!=null){
                String resultStr = new String(responseBody, Charset.forName("utf-8"));
                try {
                    JSONObject resultJson = new JSONObject(resultStr);
                    JSONObject result = resultJson.getJSONObject("result");
                    JSONArray rsList = result.getJSONArray("list");
                    for(int index=0; index<rsList.length(); index++){
                        JSONObject obj = rsList.getJSONObject(index);
                        String uid = obj.getInt("uid") + "";
                        boolean ispublic = obj.getBoolean("ispublic");
                        boolean isEncrypt = obj.getInt("isEncrypt")==1 ? true : false;
                        String cameraId = obj.getString("cameraId");
                        String deviceName = obj.getString("deviceName");
                        int defence = obj.getInt("defence");
                        int ca_id = obj.getInt("ca_id");
                        String deviceId = obj.getString("deviceId");
                        String picUrl = obj.getString("picUrl");
                        int cameraNo = obj.getInt("cameraNo");
                        boolean status = obj.getString("status").trim().equals("1") ? true : false;
                        String cameraName = obj.getString("cameraName");
                        boolean isShared = obj.getInt("isShared")==1 ? true : false;
                        String deviceSerial = obj.getString("deviceSerial");

                        MyDevice device = new MyDevice(ca_id, cameraId, cameraNo, defence, deviceName, deviceSerial, isEncrypt, ispublic, isShared, picUrl, status, uid, deviceId, cameraName);
                        MyDeviceFragment.this.result.add(device);
                    }

                    addData();

                    currentPage ++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            TestClass.closeLoading();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            if(responseBody!=null){
                String result = new String(responseBody, Charset.forName("utf-8"));
            }
            TestClass.closeLoading();
        }
    };

}
