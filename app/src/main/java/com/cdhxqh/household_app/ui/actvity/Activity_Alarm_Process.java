package com.cdhxqh.household_app.ui.actvity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.app.HttpManager;
import com.cdhxqh.household_app.config.Constants;
import com.cdhxqh.household_app.model.AlramProcessMsg;
import com.cdhxqh.household_app.ui.action.HttpCallBackHandle;
import com.cdhxqh.household_app.ui.adapter.AlramProcessAdapter;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hexian on 2015/9/11.
 */
public class Activity_Alarm_Process extends BaseActivity {

    ListView listView;
    AlramProcessAdapter adapter = new AlramProcessAdapter(this);
    ImageView backImg; // 退回按钮
    TextView titleText; // 标题
    ImageView settingImg;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alram_process_list);
        findById();
        initView();
        getData();
    }

    public void findById(){
        listView = (ListView)findViewById(R.id.alram_process);
        backImg = (ImageView)findViewById(R.id.back_imageview_id);
        titleText = (TextView)findViewById(R.id.title_text_id);
        settingImg = (ImageView)findViewById(R.id.title_add_id);
    }

    public void initView(){
        listView.setAdapter(adapter);
        titleText.setText("报警处理信息");
        settingImg.setVisibility(View.GONE);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            RequestParams maps = new RequestParams();
            maps.put("alarm_id", bundle.getInt("alarm_id"));
            HttpManager.sendHttpRequest(this, Constants.GET_ALARM_PROCESS_LIST, maps, "get", false, callback);
        }
    }


    HttpCallBackHandle callback = new HttpCallBackHandle(){

        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseBody) {
            if(responseBody!=null){
                try {
                    JSONObject object = new JSONObject(responseBody);
                    String errcode = object.getString("errcode");
                    JSONArray array = object.getJSONArray("result");
                    if("SECURITY-GLOBAL-S-0".equals(errcode)){
                        if(array!=null){
                            ArrayList<AlramProcessMsg> list = new ArrayList<AlramProcessMsg>(0);
                            for(int i=0; i<array.length(); i++){
                                JSONObject obj = array.getJSONObject(i);
                                int adid = obj.getInt("adid"); // 主键
                                int alarmid = obj.getInt("alarm_id"); // 报警信息主键
                                String status = obj.getString("alarm_status"); // 报警状态
                                long alramtime = obj.getLong("alarm_time")*1000; // 报警事件
                                int caid = obj.getInt("ca_id");  // 报警记录所属设备id
                                String description = obj.getString("description");
                                int hasdanger = obj.getInt("hasdanger");  // 是否存在安全隐患
                                int helpcheck = obj.getInt("helpcheck");  // 是否已协助核查
                                int isprocess = obj.getInt("isprocess");  // 是否已处理
                                String processResult = obj.getString("processResult");  // 处理情况说明
                                if(processResult == null || "null".equals(processResult)){
                                    processResult = "";
                                }
                                int processtime = obj.getInt("processtime"); // 报警信息主键
                                String processtimeStr = "";
                                if(processtime != 0){
                                    processtimeStr = sdf.format(new Date(processtime*1000));
                                }
                                int uid = obj.getInt("uid");// 报警记录所属用户id
                                String username = obj.getString("username");  // 处理人
                                Date time = new Date(alramtime);
                                String starttime = sdf.format(time);
                                AlramProcessMsg msg = new AlramProcessMsg(adid, alarmid, status, status, caid, description, hasdanger, helpcheck, isprocess, processResult, uid, username, starttime, processtimeStr);
                                list.add(msg);
                                adapter.update(list);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
            Log.i("TAG", "TAG");
            Log.i("TAG", "TAG");
        }

    };

}
