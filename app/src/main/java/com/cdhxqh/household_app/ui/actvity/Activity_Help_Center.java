package com.cdhxqh.household_app.ui.actvity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.ui.adapter.HelpCenterExpandableListViewAdapter;
import com.cdhxqh.household_app.ui.fragment.HelpCenterFragement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hexian on 2015/8/12.
 */
public class Activity_Help_Center extends BaseActivity {

    ExpandableListView listView;
    HelpCenterExpandableListViewAdapter adapter;
    TextView tabsLeft;
    TextView tabsRight;
    boolean onoff = false;   // true表示选中“产品说明”, false表示选中"操作说明"
    TextView titleText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);
        findViewById();
        initView();
        onoff = false;

        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();       // 屏幕宽（像素，如：480px）
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight();      // 屏幕高（像素，如：800px）
        Log.e("TAG" + "  getDefaultDisplay", "--------------------------->screenWidth=" + screenWidth + "; screenHeight=" + screenHeight);

    }

    public void findViewById() {
        listView = (ExpandableListView) findViewById(R.id.expan_listview);
        tabsLeft = (TextView) findViewById(R.id.tabs_left);
        tabsRight = (TextView) findViewById(R.id.tabs_right);
        titleText = (TextView) findViewById(R.id.title_text_id);
    }

    public void initView() {

        titleText.setText("帮助中心");

        final int tabsLeftPadding_left = tabsLeft.getPaddingLeft();
        final int tabsTopPadding_left = tabsLeft.getPaddingTop();
        final int tabsRightPadding_left = tabsLeft.getPaddingRight();
        final int tabsBottomPadding_left = tabsLeft.getPaddingBottom();

        final int tabsLeftPadding_right = tabsRight.getPaddingLeft();
        final int tabsTopPadding_right = tabsRight.getPaddingTop();
        final int tabsRightPadding_right = tabsRight.getPaddingRight();
        final int tabsBottomPadding_right = tabsRight.getPaddingBottom();

        // 注册事件
        tabsLeft.setOnClickListener(new View.OnClickListener() {  // 更换背景图片
            @Override
            public void onClick(View v) {
                tabsLeft.setBackgroundResource(R.drawable.tabs_left_sel);
                tabsLeft.setTextColor(Color.parseColor("#FFFFFF"));

                tabsRight.setBackgroundResource(R.drawable.tabs_right_nol);
                tabsRight.setTextColor(Color.parseColor("#7C8586"));

                tabsLeft.setPadding(tabsLeftPadding_left, tabsTopPadding_left, tabsRightPadding_left, tabsBottomPadding_left);
                tabsRight.setPadding(tabsLeftPadding_right, tabsTopPadding_right, tabsRightPadding_right, tabsBottomPadding_right);
                onoff = false;
            }
        });

        tabsRight.setOnClickListener(new View.OnClickListener() {  // 更换背景图片
            @Override
            public void onClick(View v) {
                tabsLeft.setBackgroundResource(R.drawable.tabs_left_nol);
                tabsLeft.setTextColor(Color.parseColor("#7C8586"));

                tabsRight.setBackgroundResource(R.drawable.tabs_right_sel);
                tabsRight.setTextColor(Color.parseColor("#FFFFFF"));

                tabsLeft.setPadding(tabsLeftPadding_left, tabsTopPadding_left, tabsRightPadding_left, tabsBottomPadding_left);
                tabsRight.setPadding(tabsLeftPadding_right, tabsTopPadding_right, tabsRightPadding_right, tabsBottomPadding_right);
                onoff = true;
            }
        });


        ArrayList<String> group = new ArrayList<String>(0);
        Map<String, List<String>> child = new HashMap<String, List<String>>(0);

        adapter = new HelpCenterExpandableListViewAdapter(this, new HelpCenterFragement.ItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View convertView, ImageView imageView, int groupPosition, int childPosition, boolean isLastChild) {
                if (onoff) {
                    Log.e("TAG", "------------------------------------------------------------>" + onoff);
                } else {
                    Log.e("TAG", "------------------------------------------------------------>" + onoff);
                }
            }
        });
        listView.setAdapter(adapter);

        for (int i = 0; i < 2; i++) {
            group.add("AAAA" + i);
            ArrayList<String> temp = new ArrayList<String>(0);
            for (int k = 0; k < 2; k++) {
                temp.add("AAAAAAAA" + i + k);
            }
            child.put(group.get(i), temp);
        }

        listView.setGroupIndicator(null);  // 去掉展开和收缩箭头
        updateExpandList(group, child);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1000){
                    ArrayList<String> group = new ArrayList<String>(0);
                    Map<String, List<String>> child = new HashMap<String, List<String>>(0);
                    for (int i = 2; i < 8; i++) {
                        group.add("BBB" + i);
                        ArrayList<String> temp = new ArrayList<String>(0);
                        for (int k = 0; k < 2; k++) {
                            temp.add("BBBBBBBB" + i + k);
                        }
                        child.put("BBB" + i, temp);
                    }
                    updateExpandList(group, child);
                    timer.cancel(); // 停止调度
                }
            }
        };

        task =  new  TimerTask() {
            @Override
            public void run () {
                Message message = new Message();
                message.what = 1000;
                handler.sendMessage(message);
            }
        };

        timer.schedule(task, 10000, 10000); // 开始调度


    }

    public void updateExpandList(ArrayList<String> group, Map<String, List<String>> child){
        if(group==null || child==null){
            throw new RuntimeException("组和子元素都不能为空");
        }
        adapter.update(group, child);
        for (int i = 0; i < adapter.getGroup().size(); i++) {  // 需要调用才方法才能默认展开ExpandableListView
            listView.expandGroup(i);
        }
    }


    public interface ItemClickListener {
        public void onItemClick(ViewGroup parent, View convertView, ImageView imageView, int groupPosition, int childPosition, boolean isLastChild);
    }


    final Timer timer = new Timer();
    TimerTask task;
    Handler handler;

}
