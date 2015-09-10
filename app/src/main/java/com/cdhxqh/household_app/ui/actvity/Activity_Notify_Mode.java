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
 * 手机通知方式主类
 */
public class Activity_Notify_Mode extends BaseActivity {

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
        list.add("手机短信");
        list.add("手机彩信");

        return list;
    }

}
