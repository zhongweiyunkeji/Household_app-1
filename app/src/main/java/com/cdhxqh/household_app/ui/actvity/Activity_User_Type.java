package com.cdhxqh.household_app.ui.actvity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.ui.action.impl.OnItemClickCallBackImpl;
import com.cdhxqh.household_app.ui.adapter.UserSelectAdapter;

import java.util.ArrayList;

/**
 * Created by hexian on 2015/8/10.
 * 选择用户类型
 */

public class Activity_User_Type extends Activity {

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
        list.add("家庭用户");
        list.add("社区物业");
        list.add("保安公司");

        return list;
    }


}
