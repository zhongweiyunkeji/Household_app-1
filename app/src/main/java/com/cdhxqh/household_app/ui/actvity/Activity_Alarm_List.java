package com.cdhxqh.household_app.ui.actvity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import java.util.Collections;
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
    int currentPage = 0;
    int showPage = 30;
    EzvizAPI mEzvizAPI = EzvizAPI.getInstance();
    CameraInfo info;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ImageView backImg;
    TextView titleText;
    ImageView settingImg;

    CheckBox checkdAll;
    ImageView delBtn;

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

        checkdAll = (CheckBox)findViewById(R.id.checkd_all);
        delBtn = (ImageView)findViewById(R.id.del_btn);


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
               /* Intent intent = new Intent(Activity_Alarm_List.this,  Activity_alarm_play.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivity(intent);*/
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
/*

        checkdAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {  // 全选按钮事件
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (adapter != null) {
                    if (isChecked) {
                        adapter.selectAll();
                    } else {
                        adapter.unselectAll();
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() { // 删除按钮事件
            @Override
            public void onClick(View v) {
                ArrayList<Alarm> list = adapter.getList();
                ArrayList<Alarm> clonList = (ArrayList<Alarm>) list.clone();
                int size = list.size();
                for (int index = 0; index < size; index++) {
                    Alarm alarm = list.get(index);
                    boolean flag = alarm.isStatus();
                    if (flag) {
                        clonList.remove(alarm);
                    }
                }
                Collections.reverse(clonList);
                adapter.reload(clonList);
                if (adapter.getList().size() == 0) {
                    checkdAll.setChecked(false);
                }
            }
        });
*/

        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:{

                    }
                    case MotionEvent.ACTION_UP:{

                    }
                    case MotionEvent.ACTION_MOVE:{

                    }
                }
                return false;
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount == totalItemCount){ // 滚动到最底部

                } else
                if(firstVisibleItem == 0){

                }
               // Log.e("TAG", "--------------------------->firstVisibleItem="+firstVisibleItem+", visibleItemCount="+visibleItemCount+", totalItemCount="+totalItemCount);
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

                // int x = 1/0;  // 使用异常里面的数据来缓存
                result = (ArrayList<AlarmInfo>)mEzvizAPI.getAlarmInfoList(getAlarmInfoList);

                return result;
            } catch (Exception e) {
                e.printStackTrace();
                result = new ArrayList<AlarmInfo>(0);
                for(int i=0; i<30; i++){
                    AlarmInfo alarmInfo = new AlarmInfo();
                    alarmInfo.setAlarmId("" + (1000 + i));
                    alarmInfo.setAlarmIsEncyption(true);
                    alarmInfo.setAlarmName("设备名称");
                    alarmInfo.setAlarmPicUrl("http://");
                    alarmInfo.setAlarmStart("2013-09-03 15:29:4" + i);
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

                Alarm alarm = new Alarm(info.getAlarmStart(), R.drawable.ic_menu_alarm_orange, info.getAlarmPicUrl(), alarmTypeName, info.getAlarmName());
                alarm.setEncryption(info.getAlarmEncryption());
                alarm.setSerial(alarm.getSerial());
                alarm.setCheckSum(alarm.getCheckSum());
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
