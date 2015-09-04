package com.cdhxqh.household_app.ui.actvity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.config.Constants;
import com.cdhxqh.household_app.ezviz.AlarmType;
import com.cdhxqh.household_app.model.Alarm;
import com.cdhxqh.household_app.ui.action.AlarmOnClickCallBack;
import com.cdhxqh.household_app.ui.adapter.AlarmItemAdapter;
import com.cdhxqh.household_app.ui.widget.TestClass;
import com.videogo.exception.BaseException;
import com.videogo.openapi.EzvizAPI;
import com.videogo.openapi.bean.req.GetAlarmInfoList;
import com.videogo.openapi.bean.resp.AlarmInfo;
import com.videogo.openapi.bean.resp.CameraInfo;
import com.videogo.util.ConnectionDetector;
import com.videogo.util.Utils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by hexian on 2015/9/3.
 */
public class Activity_Alarm_List extends BaseActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    ListView listView;
    AlarmItemAdapter adapter;
    RelativeLayout relativeLayout;
    int currentPage = 1;
    int showPage = 10;
    EzvizAPI mEzvizAPI = EzvizAPI.getInstance();
    CameraInfo info;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ImageView backImg;
    TextView titleText;
    ImageView settingImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_alarm_list);
        getData();
        findViewById();
        initView();
    }

    public void getData(){
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            info = (CameraInfo)bundle.getParcelable("device_name");
        }
    }

    public void findViewById() {
        swipeRefreshLayout =  (SwipeRefreshLayout)findViewById(R.id.swipe_container);
        listView = (ListView)findViewById(R.id.three_in_alarm_listview);
        relativeLayout = (RelativeLayout)findViewById(R.id.operate_area);
        backImg = (ImageView)findViewById(R.id.back_imageview_id);
        titleText = (TextView)findViewById(R.id.title_text_id);
        settingImg = (ImageView)findViewById(R.id.title_add_id);
    }

    public void initView() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleText.setText("报警记录");
        settingImg.setVisibility(View.GONE);

        adapter = new AlarmItemAdapter(this, new AlarmOnClickCallBack() {
            @Override
            public void onClick(int position, View convertView, Alarm alarm) {
                Intent intent = new Intent(Activity_Alarm_List.this,  Activity_alarm_play.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivity(intent);
                // Toast.makeText(getActivity(), "" + getActivity().getClass().getName(), Toast.LENGTH_SHORT).show();
            }
        }, false);
        listView.setAdapter(adapter);

        relativeLayout.setVisibility(View.GONE);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 获取网络数据
                initData();
            }
        });

        // 获取网络数据
        initData();

    }

    private void initData(){
        new GetAlarmMessageTask().execute();
    }


    /**
     * 获取事件消息任务, 目前能查看到报警信息，但是无法查看加密的图片，可参考demo项目里面的NotifierAdapter功能
     */
    private class GetAlarmMessageTask extends AsyncTask<String, Void, ArrayList<AlarmInfo>> {

        public GetAlarmMessageTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            TestClass.loading(Activity_Alarm_List.this, "正在加载数据，请稍后");
        }

        @Override
        protected ArrayList<AlarmInfo> doInBackground(String... params) {
            if (!ConnectionDetector.isNetworkAvailable(Activity_Alarm_List.this)) {
                return new ArrayList<AlarmInfo>(0);
            }
            ArrayList<AlarmInfo> result = new ArrayList<AlarmInfo>(0);
            try {
                mEzvizAPI.setAccessToken(Constants.TOKEN_URL);
                Date curDate = new Date();
                Calendar startTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
                Calendar endTime =Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
                startTime.setTime(curDate);
                endTime.setTime(curDate);
                startTime.add(Calendar.MONTH, -3);// 3月前(自动维护年月的进制)
                // 获取设备列表
                GetAlarmInfoList getAlarmInfoList = new GetAlarmInfoList();
                String cameraid = info.getCameraId();
                getAlarmInfoList.setCameraId(cameraid);
                getAlarmInfoList.setStartTime(Utils.calendar2String(startTime));
                getAlarmInfoList.setEndTime(Utils.calendar2String(endTime));
                getAlarmInfoList.setPageStart(currentPage);
                getAlarmInfoList.setPageSize(showPage);

                getAlarmInfoList.setStatus(2);
                getAlarmInfoList.setAlarmType(-1); // 获取全部报警记录(报警类型可参考文档：萤石平台接口使用说明文档(最新版).doc)

                result = (ArrayList<AlarmInfo>)mEzvizAPI.getAlarmInfoList(getAlarmInfoList);

                return result;
            } catch (BaseException e) {
                e.printStackTrace();
                result = new ArrayList<AlarmInfo>(0);
                for(int i=0; i<3; i++){
                    AlarmInfo alarmInfo = new AlarmInfo();
                    alarmInfo.setAlarmId(""+(1000+i));
                    alarmInfo.setAlarmIsEncyption(true);
                    alarmInfo.setAlarmName("设备名称");
                    alarmInfo.setAlarmPicUrl("http://");
                    alarmInfo.setAlarmStart("2013-09-03 15:29:4"+i);
                    alarmInfo.setDeviceSerial("" + (1000 + i));
                    alarmInfo.setAlarmType(10002);
                    result.add(alarmInfo);
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<AlarmInfo> result) {
            super.onPostExecute(result);

            ArrayList<Alarm> list = new ArrayList<Alarm>(0);

            for(int t=0; t<result.size(); t++) {
                AlarmInfo info = result.get(t);
                AlarmType alarmType = AlarmType.getAlarmTypeById(info.getAlarmType());
                String alarmTypeName = "";
                if (alarmType != AlarmType.UNKNOWN) {
                    alarmTypeName = Activity_Alarm_List.this.getResources().getString(alarmType.getTextResId());
                }

                Alarm alarm = new Alarm(info.getAlarmStart(), R.drawable.ic_menu_alarm_orange,
                        "http://c.hiphotos.baidu.com/news/w%3D638/sign=b918710f45a98226b8c12824b283b97a/e824b899a9014c083cdadbdf0c7b02087af4f4e3.jpg",
                        alarmTypeName, info.getAlarmName());
                list.add(alarm);
            }

            adapter.update(list);
            if(!list.isEmpty()){
                currentPage++;
            } else {
                // 显示没有数据

            }
            swipeRefreshLayout.setRefreshing(false);
            TestClass.closeLoading();
        }
    }



}
