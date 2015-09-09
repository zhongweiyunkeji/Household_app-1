package com.cdhxqh.household_app.ui.actvity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

    /**
     * 返回按钮*
     */
    private ImageView back_imageview_id;
    /**
     * 标题*
     */
    private TextView titleTextView;
    /**
     * 搜索*
     */
    private ImageView title_add_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_usertype);
        findViewById();
        initView();
    }

    private void findViewById(){
        listView = (ListView)findViewById(R.id.select_listview);

        /**
         * 标题标签相关id
         */
        back_imageview_id = (ImageView) findViewById(R.id.back_imageview_id);
        titleTextView = (TextView) findViewById(R.id.title_text_id);
        title_add_id = (ImageView) findViewById(R.id.title_add_id);
    }

    private void initView(){
        //设置标签页显示方式
        back_imageview_id.setVisibility(View.VISIBLE);
        title_add_id.setVisibility(View.GONE);
        titleTextView.setText("选择用户类型");
        UserSelectAdapter adapter = new UserSelectAdapter(this, getData(), new OnItemClickCallBackImpl() );
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        back_imageview_id.setOnTouchListener(backImageViewOnTouchListener);

        //返回至登录界面事件
        back_imageview_id.setOnClickListener(backImageViewOnClickListener);
    }

    private View.OnTouchListener backImageViewOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setBackgroundColor(getResources().getColor(R.color.list_item_read));
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                v.setBackgroundColor(getResources().getColor(R.color.clarity));
            }
            return false;
        }
    };

    /**
     * 返回事件的监听*
     */
    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

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
