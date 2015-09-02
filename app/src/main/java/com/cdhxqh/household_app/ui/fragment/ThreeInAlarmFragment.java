package com.cdhxqh.household_app.ui.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.ezviz.AlarmType;
import com.cdhxqh.household_app.model.Alarm;
import com.cdhxqh.household_app.ui.action.AlarmOnClickCallBack;
import com.cdhxqh.household_app.ui.actvity.Activity_Alarm_Del;
import com.cdhxqh.household_app.ui.actvity.Activity_alarm_play;
import com.cdhxqh.household_app.ui.actvity.MainActivity;
import com.cdhxqh.household_app.ui.adapter.AlarmItemAdapter;
import com.cdhxqh.household_app.ui.widget.TestClass;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.videogo.alarm.AlarmLogInfoEx;
import com.videogo.alarm.AlarmLogInfoManager;
import com.videogo.exception.BaseException;
import com.videogo.exception.ErrorCode;
import com.videogo.openapi.EzvizAPI;
import com.videogo.openapi.bean.req.GetAlarmInfoList;
import com.videogo.openapi.bean.req.GetCameraInfoList;
import com.videogo.openapi.bean.resp.AlarmInfo;
import com.videogo.openapi.bean.resp.CameraInfo;
import com.videogo.universalimageloader.core.DisplayImageOptions;
import com.videogo.universalimageloader.core.assist.FailReason;
import com.videogo.universalimageloader.core.download.DecryptFileInfo;
import com.videogo.universalimageloader.core.listener.ImageLoadingListener;
import com.videogo.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.videogo.util.ConnectionDetector;
import com.videogo.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by hexian on 2015/8/17.
 */
public class ThreeInAlarmFragment extends BaseFragment {

    SwipeRefreshLayout swipeRefreshLayout;
    ListView listView;
    AlarmItemAdapter adapter;
    boolean showCheckBox = false;
    boolean hideToolBar = false;
    RelativeLayout relativeLayout;

    CheckBox checkdAll;
    ImageView delBtn;
    int currentPage = 1;
    int showPage = 10;

    EzvizAPI mEzvizAPI = EzvizAPI.getInstance();

    public ThreeInAlarmFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle!=null){
            showCheckBox = bundle.getBoolean("showCheckBox");
            hideToolBar = bundle.getBoolean("hideToolBar");
        }
        View view = inflater.inflate(R.layout.three_in_alarm, container, false);
        findViewById(view);
        initView();
        return view;
    }

    private void initData(){
        task.execute();
    }

    public void findViewById(View view) {
        swipeRefreshLayout =  (SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
        listView = (ListView)view.findViewById(R.id.three_in_alarm_listview);
        relativeLayout = (RelativeLayout)view.findViewById(R.id.operate_area);
        checkdAll = (CheckBox)view.findViewById(R.id.checkd_all);
        delBtn = (ImageView)view.findViewById(R.id.del_btn);
    }

    public void initView() {

        adapter = new AlarmItemAdapter(getActivity(), new AlarmOnClickCallBack() {
            @Override
            public void onClick(int position, View convertView, Alarm alarm) {
                if(getActivity() instanceof MainActivity){
                    ThreeInAlarmFragment inFragment = (ThreeInAlarmFragment)getFragmentManager().findFragmentByTag("ThreeInAlarmFragment");
                    if(inFragment!=null){
                        Intent intent = new Intent(getActivity(),  Activity_alarm_play.class);
                        Bundle bundle = new Bundle();
                        intent.putExtras(bundle);
                        startActivity(intent);
                        Toast.makeText(getActivity(), "" + getActivity().getClass().getName(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else
                if(getActivity() instanceof Activity_Alarm_Del){
                    ThreeInAlarmFragment inFragment = (ThreeInAlarmFragment)getFragmentManager().findFragmentByTag("ThreeInAlarmFragment");
                    if(inFragment!=null){
                        Intent intent = new Intent(getActivity(),  Activity_alarm_play.class);
                        Bundle bundle = new Bundle();
                        intent.putExtras(bundle);
                        startActivity(intent);
                        Toast.makeText(getActivity(), "" + getActivity().getClass().getName(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        }, showCheckBox);
        listView.setAdapter(adapter);

        if(hideToolBar){
            relativeLayout.setVisibility(View.GONE);
        } else {
            relativeLayout.setVisibility(View.VISIBLE);
        }

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




    private GetAlarmMessageTask task = new GetAlarmMessageTask();

    /**
     * 获取事件消息任务, 目前能查看到报警信息，但是无法查看加密的图片，可参考demo项目里面的NotifierAdapter功能
     */
    private class GetAlarmMessageTask extends AsyncTask<String, Void, ArrayList<AlarmInfo>> {

        public GetAlarmMessageTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            TestClass.loading(getActivity(), "正在加载数据，请稍后");
        }

        @Override
        protected ArrayList<AlarmInfo> doInBackground(String... params) {
            if (!ConnectionDetector.isNetworkAvailable(getActivity())) {
                return new ArrayList<AlarmInfo>(0);
            }

            try {
                mEzvizAPI.setAccessToken("at.7xuar1gr0g4cmq1d75ypl15u2it0faqn-2rrghtr7r4-07azpnm-1ya5libcl");
                GetCameraInfoList getCameraInfoList = new GetCameraInfoList();
                getCameraInfoList.setPageStart(0);
                getCameraInfoList.setPageSize(10);
                List<CameraInfo> list = mEzvizAPI.getCameraInfoList(getCameraInfoList);

                ArrayList<AlarmInfo> result = new ArrayList<AlarmInfo>(0);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date curDate = new Date();
                Calendar startTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
                Calendar endTime =Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
                startTime.setTime(curDate);
                endTime.setTime(curDate);
                startTime.add(Calendar.MONTH, -3);// 3月前(自动维护年月的进制)
                for(int k=0; k<list.size(); k++){
                    // 获取设备列表
                    CameraInfo mCameraInfo = list.get(k);

                    GetAlarmInfoList getAlarmInfoList = new GetAlarmInfoList();
                    String path = mCameraInfo.getCameraId();
                    getAlarmInfoList.setCameraId(path);
                    getAlarmInfoList.setStartTime(Utils.calendar2String(startTime));
                    getAlarmInfoList.setEndTime(Utils.calendar2String(endTime));
                    getAlarmInfoList.setStatus(2);
                    getAlarmInfoList.setAlarmType(-1); // 获取全部报警记录(报警类型可参考文档：萤石平台接口使用说明文档(最新版).doc)

                    getAlarmInfoList.setPageStart(currentPage);
                    getAlarmInfoList.setPageSize(showPage);

                    ArrayList<AlarmInfo> infoList = (ArrayList<AlarmInfo>)mEzvizAPI.getAlarmInfoList(getAlarmInfoList);

                    result.addAll(infoList);
                }

                return result;
            } catch (BaseException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<AlarmInfo> result) {
            super.onPostExecute(result);

            ArrayList<Alarm> list = new ArrayList<Alarm>(0);

            for(int t=0; t<result.size(); t++) {
                AlarmInfo info = result.get(t);
                Log.e("TAG", "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++》" + info.getDeviceSerial());
                Log.e("TAG", "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++》" + info.getAlarmPicUrl());
                Log.e("TAG", "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++》" + info.getAlarmCloud());
                Log.e("TAG", "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++》" + info.getAlarmEncryption());
                Log.e("TAG", "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++》" + info.getAlarmId());
                Log.e("TAG", "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++》" + info.getAlarmName());
                Log.e("TAG", "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++》" + info.getAlarmStart());
                Log.e("TAG", "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++》" + info.getAlarmType());
                Log.e("TAG", "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++》" + info.getCheckSum());
                Log.e("TAG", "\n\n\n\n\n\n\n\n");

                AlarmType alarmType = AlarmType.getAlarmTypeById(info.getAlarmType());
                String alarmTypeName = "";
                if (alarmType != AlarmType.UNKNOWN) {
                    alarmTypeName = getActivity().getResources().getString(alarmType.getTextResId());
                }

                Alarm alarm = new Alarm(info.getAlarmStart(), R.drawable.ic_menu_alarm_orange,
                        "http://c.hiphotos.baidu.com/news/w%3D638/sign=b918710f45a98226b8c12824b283b97a/e824b899a9014c083cdadbdf0c7b02087af4f4e3.jpg",
                        alarmTypeName, info.getAlarmName());
                list.add(alarm);
            }

            adapter.update(list);
            if(adapter.getList().isEmpty()){
                // 显示没有数据
            }

            TestClass.closeLoading();

            /*
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            final LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(params);
            ImageView imgView= new ImageView(getActivity());

            // 正常的图片
            //imgView.setImageResource(R.drawable.btn_image_sel);
            //linearLayout.addView(imgView, params);

            //  获取网络图片
            // ImageLoader.getInstance().displayImage("http://pic002.cnblogs.com/images/2010/71187/2010110819223040.png", imgView);

            AlarmType alarmType = AlarmType.getAlarmTypeById(alarmInfo.getAlarmType());
            if (alarmType.hasCamera()) {
                String serial = "536724535"; //alarmInfo.getDeviceSerial();
                String checkSum = alarmInfo.getCheckSum();
                com.videogo.universalimageloader.core.ImageLoader mImageLoader = com.videogo.universalimageloader.core.ImageLoader.getInstance();
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .needDecrypt(alarmInfo.getAlarmEncryption())
                        .considerExifParams(true)
                        .showImageForEmptyUri(R.drawable.btn_image_sel)
                        .showImageOnFail(R.drawable.btn_videocarema_nol)
                        .showImageOnDecryptFail(R.drawable.btn_alarm_nol)
                        .extraForDownloader(new DecryptFileInfo(serial, checkSum))
                        .build();

                mImageLoader.cancelDisplayTask(imgView);
                mImageLoader.displayImage("https://whpic.ys7.com:8009/p/GPjuG_536724535_at.7xuar1gr0g4cmq1d75ypl15u2it0faqn-2rrghtr7r4-07azpnm-1ya5libcl", imgView, options,
                        new ImageLoadingListener() {

                            @Override
                            public void onLoadingStarted(String imageUri, View view) {
                                Log.e("TAG", "---------------------------------------------------------->");
                                Log.e("TAG", "---------------------------------------------------------->");
                                Log.e("TAG", "---------------------------------------------------------->");
                                Log.e("TAG", "---------------------------------------------------------->");
                                Log.e("TAG", "---------------------------------------------------------->");
                            }

                            @Override
                            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                                Log.e("TAG", "---------------------------------------------------------->"+failReason.getType());
                                Log.e("TAG", "---------------------------------------------------------->"+failReason.getCause());
                                Log.e("TAG", "---------------------------------------------------------->");
                                Log.e("TAG", "---------------------------------------------------------->");
                                Log.e("TAG", "---------------------------------------------------------->");
                            }

                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                Log.e("TAG", "---------------------------------------------------------->");
                                Log.e("TAG", "---------------------------------------------------------->");
                                Log.e("TAG", "---------------------------------------------------------->");
                                Log.e("TAG", "---------------------------------------------------------->");
                                Log.e("TAG", "---------------------------------------------------------->");
                            }

                            @Override
                            public void onLoadingCancelled(String imageUri, View view) {
                                Log.e("TAG", "---------------------------------------------------------->");
                                Log.e("TAG", "---------------------------------------------------------->");
                                Log.e("TAG", "---------------------------------------------------------->");
                                Log.e("TAG", "---------------------------------------------------------->");
                                Log.e("TAG", "---------------------------------------------------------->");
                            }

                        }, new ImageLoadingProgressListener() {

                            @Override
                            public void onProgressUpdate(String imageUri, View view, int current, int total) {

                            }
                        });


                builder.setView(linearLayout);
                builder.show();
            }
            */

        }
    }

}
