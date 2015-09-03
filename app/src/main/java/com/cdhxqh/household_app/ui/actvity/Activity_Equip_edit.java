package com.cdhxqh.household_app.ui.actvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.MyDevice;
import com.cdhxqh.household_app.ui.adapter.EquipEditAdapter;

import java.util.ArrayList;

/**
 * Created by hexian on 2015/8/20.
 */
public class Activity_Equip_edit extends BaseActivity {

    ImageView backImg;    // 返回按钮
    TextView titleView;   // 状态栏提示文字
    ImageView settingImg; // 设置按钮文字
    ListView listView;

    EquipEditAdapter adapter;  // 适配器

    SwipeRefreshLayout swipeRefreshLayout; // 刷新控件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equip_edit);
        findViewById();
        initView();
    }

    public void findViewById(){
        backImg = (ImageView)findViewById(R.id.back_imageview_id);
        titleView = (TextView)findViewById(R.id.title_text_id);
        settingImg = (ImageView)findViewById(R.id.title_add_id);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
        listView = (ListView)findViewById(R.id.listview);
    }

    public void initView() {
        titleView.setText("我的设备");
        settingImg.setVisibility(View.GONE);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter = new EquipEditAdapter(this, new OnListViewItemClick() {
            @Override
            public void click(MyDevice myDevice, int position) {
                Intent intent = new Intent(Activity_Equip_edit.this, AddEquipmentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("action", "edit");
                bundle.putSerializable("entity", myDevice);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);

        ArrayList<MyDevice> list = getData();
        adapter.update(list);

    }

    public ArrayList<MyDevice> getData(){
        ArrayList<MyDevice> list = new ArrayList<MyDevice>(0);
        for(int i=0; i<20; i++){
            MyDevice divice = new MyDevice();
            divice.setName("海康DS-2CD2412F " + i);  // 设备名称
            divice.setNumber("2317635" + i);          // 设备编号
            divice.setSize(i);                         // 交表
            divice.setPlace("家里门口 "+i);           // 地点
            list.add(divice);
        }

        return list;
    }


    public interface OnListViewItemClick {
        public void click(MyDevice myDevice, int position);
    }

}
