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
import com.cdhxqh.household_app.ui.action.HttpCallBackHandle;
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
    int currentPage = 1; // 当前页(索引从0开始)
    int showPage = 10;   // 每页显示

    SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private MyDevicelistAdapter myDevicelistAdapter;
    ArrayList<MyDevice> result = new ArrayList<MyDevice>(0);
    DeviceOnClick callback = new DeviceOnClick(){
        public void callback(RecyclerView.ViewHolder holder, int position, View view, final MyDevice info){
            if(info.getUid().equals(""+ Constants.USER_ID)){ // 如果是自己的设备
                startActivity(info);
            } else { // 如果是分享别人的设备
                if (NetWorkUtil.IsNetWorkEnable(getActivity())) {
                    String userid = info.getUid();
                    RequestParams maps = new RequestParams();
                    maps.put("uid", userid);
                    HttpManager.sendHttpRequest(getActivity(), Constants.ACCESSTOKEN, maps, "get", false, new HttpCallBackHandle() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                            if (NetWorkUtil.IsNetWorkEnable(getActivity())) {
                                if (responseBody != null && !"".equals(responseBody)) {
                                    // 解析AccessToken
                                    try {
                                        JSONObject respJosn = new JSONObject(responseBody);
                                        String code = respJosn.getString("errcode");
                                        if("200".equals(code)){
                                            String result = respJosn.getString("result");
                                            if(result!=null){
                                                JSONObject resultJson = new JSONObject(result);
                                                String token = resultJson.getString("accessToken");
                                                EzvizAPI.getInstance().setAccessToken(token);
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    startActivity(info);
                                }
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                            if (NetWorkUtil.IsNetWorkEnable(getActivity())) {

                            }
                        }
                    });
                }
            }
        }
    };

    public void startActivity(MyDevice info){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("device_name", info);
        intent.putExtras(bundle);
        intent.setClass(getActivity(), Activity_Video_Control.class);
        getActivity().startActivityForResult(intent, 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mydevice, container, false);
        findById(view);
        initView();
        return view;
    }

    public void findById(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.mydevice_list);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
    }

    public void initView(){
        myDevicelistAdapter = new MyDevicelistAdapter(getActivity(), callback);
        myDevicelistAdapter.setShowSizeView(false); // 不显示报警记录总数

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(myDevicelistAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startAsynTask();  // 请求网络数据
            }
        });

        startAsynTask();  // 请求网络数据
    }

    /**
     * 初始化任务
     */
    private void startAsynTask() {
        RequestParams maps = new RequestParams();
        maps.put("showCount", showPage);
        maps.put("currentPage", currentPage);
        AsyncHttpClient client = new AsyncHttpClient();
        HttpManager.sendHttpRequest(getActivity(), Constants.DEVICE_LIST.trim(), maps, "get", false, responseHandler);
    }

    HttpCallBackHandle responseHandler = new HttpCallBackHandle() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseBody) {
            if(responseBody!=null){
                String resultStr = responseBody;
                MyDeviceFragment.this.result.clear();;
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

                    if(!MyDeviceFragment.this.result.isEmpty()){
                        myDevicelistAdapter.setShowSwitch(true);
                        myDevicelistAdapter.update(MyDeviceFragment.this.result, true);
                    }
                    currentPage++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
            if(swipeRefreshLayout.isRefreshing()){
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    };

}
