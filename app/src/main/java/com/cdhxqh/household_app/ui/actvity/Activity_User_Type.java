package com.cdhxqh.household_app.ui.actvity;

import android.os.Bundle;
import android.widget.ListView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.ui.action.impl.OnItemClickCallBackImpl;
import com.cdhxqh.household_app.ui.adapter.UserSelectAdapter;

import java.util.ArrayList;

/**
 * Created by hexian on 2015/8/10.
 * 选择用户类型
 */

public class Activity_User_Type extends BaseActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_usertype);
        findViewById();
        initView();
    }

    private void findViewById(){
        listView = (ListView)findViewById(R.id.select_listview);
    }

    private void initView(){
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
        list.add("安保");
        list.add("其他");

        return list;
    }


}
