package com.cdhxqh.household_app.ui.actvity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.Alarm;
import com.cdhxqh.household_app.model.MyDevice;
import com.cdhxqh.household_app.ui.adapter.EquipDelAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by hexian on 2015/8/20.
 */
public class Activity_Equip_delete extends BaseActivity {

    ImageView backImg;    // 返回按钮
    TextView titleView;   // 状态栏提示文字
    ImageView settingImg; // 设置按钮文字
    ListView listView;

    EquipDelAdapter adapter;  // 适配器

    SwipeRefreshLayout swipeRefreshLayout; // 刷新控件

    CheckBox checkdAll;  // 全选按钮

    ImageView delBtn; // 删除按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equip_delete);
        findViewById();
        initView();
    }

    public void findViewById(){
        backImg = (ImageView)findViewById(R.id.back_imageview_id);
        titleView = (TextView)findViewById(R.id.title_text_id);
        settingImg = (ImageView)findViewById(R.id.title_add_id);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
        listView = (ListView)findViewById(R.id.listview);
        checkdAll = (CheckBox)findViewById(R.id.checkd_all);
        delBtn = (ImageView)findViewById(R.id.del_btn);
    }

    public void initView(){
        titleView.setText("我的设备");
        settingImg.setVisibility(View.GONE);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter = new EquipDelAdapter(this);
        listView.setAdapter(adapter);

        ArrayList<MyDevice> list = getData();
        adapter.update(list);

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
                ArrayList<MyDevice> list = adapter.getList();
                ArrayList<MyDevice> clonList = (ArrayList<MyDevice>) list.clone();
                int size = list.size();
                for (int index = 0; index < size; index++) {
                    MyDevice alarm = list.get(index);
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

    }

    public ArrayList<MyDevice> getData(){
        ArrayList<MyDevice> list = new ArrayList<MyDevice>(0);
        for(int i=0; i<20; i++){
            MyDevice divice = new MyDevice();
            divice.setStatus(true);                    // 设备状态
            divice.setName("海康DS-2CD2412F " + i);  // 设备名称
            divice.setNumber("2317635" + i);          // 设备编号
            divice.setSize(i);                         // 交表
            divice.setPlace("家里门口 "+i);           // 地点
            list.add(divice);
        }

        return list;
    }

}
