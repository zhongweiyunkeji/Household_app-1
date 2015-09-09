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
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
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
import com.cdhxqh.household_app.model.MyDevice;
import com.cdhxqh.household_app.ui.action.AlarmOnClickCallBack;
import com.cdhxqh.household_app.ui.action.DeviceOnClick;
import com.cdhxqh.household_app.ui.action.HttpCallBackHandle;
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
import com.loopj.android.http.RequestParams;
import com.videogo.exception.BaseException;
import com.videogo.openapi.EzvizAPI;
import com.videogo.openapi.bean.req.GetAlarmInfoList;
import com.videogo.openapi.bean.req.GetCameraInfoList;
import com.videogo.openapi.bean.resp.AlarmInfo;
import com.videogo.openapi.bean.resp.CameraInfo;
import com.videogo.util.ConnectionDetector;
import com.videogo.util.Utils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    int currentPage = 1; // 当前页(索引从0开始)
    int showPage = 10;   // 每页显示

    SwipeRefreshLayout swipeRefreshLayout;
    ListView listView;
    AlarmItemAdapter adapter;
    MyDevice info;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    ArrayList<Alarm> list = new ArrayList<Alarm>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.device_alarm_list, container, false);
        findViewById(view);
        initView();
        return view;
    }

    public void findViewById(View view){
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        listView = (ListView) view.findViewById(R.id.three_in_alarm_listview);
    }

    public void initView(){
        adapter = new AlarmItemAdapter(getActivity(), new AlarmOnClickCallBack() {
            @Override
            public void onClick(int position, View convertView, Alarm alarm) {

            }
        }, false);
        listView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("TAG", "TAG");
                startAsynTask();  // 请求网络数据
            }
        });

        startAsynTask();  // 请求网络数据
    }

    /**
     * 初始化任务
     */
    private void startAsynTask(){
        RequestParams maps = new RequestParams();
        maps.put("showCount", showPage);
        maps.put("currentPage", currentPage);
        HttpManager.sendHttpRequest(getActivity(), Constants.ALARM_LIST, maps, "get", false, callback);
    }

    HttpCallBackHandle callback = new HttpCallBackHandle() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseBody) {
            try {
                list.clear();;
                JSONObject response = new JSONObject(responseBody);
                String errcode =  response.getString("errcode");
                if("SECURITY-GLOBAL-S-0".equals(errcode)){// 返回数据成功
                    JSONArray jsonList = response.getJSONObject("result").getJSONArray("list");
                    if(jsonList!=null){
                        int length = jsonList.length();
                        for(int index=0; index<length; index++){
                            JSONObject jsonObject = jsonList.getJSONObject(index);
                            jsonObject.getInt("alarm_id");   // 自己平台报警记录id
                            jsonObject.getInt("ca_id");      // 设备编号
                            int uid = jsonObject.getInt("uid");        // 用户id
                            jsonObject.getString("alarm_type");  // 报警类型
                            jsonObject.getInt("alarm_time");     // 报警事件
                            jsonObject.getString("alarm_image"); // 报警图片
                            jsonObject.getString("alarm_video"); // 报警视频
                            jsonObject.getString("alarm_result");// 报警结果
                            String status = jsonObject.getString("alarm_status"); // 报警状态   新增 已协助  已处理  已关闭  已取消
                            jsonObject.getString("alarmId");       // 萤石报警记录id
                            jsonObject.getString("alarmName");    // 报警名称
                            String img = jsonObject.getString("alarmPicUrl");  // 报警图片
                            String startTime = jsonObject.getString("alarmStart");   // 报警开始时间
                            int type = jsonObject.getInt("alarmType");       // 报警类型
                            jsonObject.getInt("channelNo");       // 通道编号
                            jsonObject.getInt("isCheck");         // 是否查看
                            String username = jsonObject.getString("username");         // 是否查看
                            String installAddr = jsonObject.getString("address");         // 设备安装位置
                            String familyAddr = jsonObject.getString("community");         // 设备安装位置

                            AlarmType alarmType = AlarmType.getAlarmTypeById(type);
                            String alarmTypeName = null;
                            if (alarmType != AlarmType.UNKNOWN) {
                                alarmTypeName = getActivity().getResources().getString(alarmType.getTextResId());
                            }


                            Alarm alarm = new Alarm();
                            alarm.setImg(img);
                            alarm.setDate(startTime);
                            alarm.setTitle(username + " " + installAddr + " " + alarmTypeName);
                            alarm.setMsg(familyAddr);
                            if("新增".equals(status) && (Constants.USER_ID == uid)){
                                alarm.setIcon(R.drawable.btn_dxz);
                            } else
                            if("新增".equals(status) && (Constants.USER_ID != uid)){
                                alarm.setIcon(R.drawable.btn_dcl);
                            } else
                            if("已处理".equals(status)){
                                alarm.setIcon(R.drawable.btn_ycl);
                            }
                            list.add(alarm);
                        }

                        currentPage++;
                    }

                    if(!list.isEmpty()){
                        adapter.update(list);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(swipeRefreshLayout.isRefreshing()){
                swipeRefreshLayout.setRefreshing(false);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
            Log.i("statusCode", "statusCode");
            Log.i("statusCode", "statusCode");
            if(swipeRefreshLayout.isRefreshing()){
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    };

}
