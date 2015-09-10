package com.cdhxqh.household_app.ui.actvity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.ui.action.impl.OnItemClickCallBackImpl;
import com.cdhxqh.household_app.ui.adapter.UserSelectAdapter;

import java.util.ArrayList;

/**
 * Created by hexian on 2015/8/11.
 *
 * 选择角色分组主类
 */
public class Activity_Role_Group extends BaseActivity {

    TextView titleTextView;
    ImageView settingImg;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_usertype);

        findViewById();
        initView();

    }

    public void findViewById() {
        titleTextView = (TextView)findViewById(R.id.title_text_id);
        settingImg = (ImageView)findViewById(R.id.title_add_id);
        listView = (ListView)findViewById(R.id.select_listview);
    }

    public void initView() {
        titleTextView.setText("选择通知方式");
        settingImg.setVisibility(View.GONE);

        UserSelectAdapter adapter = new UserSelectAdapter(this, getData(), new OnItemClickCallBackImpl() );
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    public ArrayList<String> getData(){
        ArrayList<String> list = new ArrayList<String>(0);
        list.add("家人");
        list.add("物业");
        list.add("朋友");
        list.add("邻居");
        list.add("公安");
        list.add("消防");
        list.add("保安");
        list.add("其他");

        return list;
    }

}
