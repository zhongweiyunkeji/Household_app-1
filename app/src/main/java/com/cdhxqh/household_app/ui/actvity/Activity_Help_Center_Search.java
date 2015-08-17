package com.cdhxqh.household_app.ui.actvity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.ui.adapter.HelpCenterExpandableListViewAdapter;
import com.cdhxqh.household_app.ui.fragment.HelpCenterFragement;
import com.cdhxqh.household_app.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hexian on 2015/8/17.
 */
public class Activity_Help_Center_Search extends  BaseActivity {

    TextView srarchTextView;           // title上的搜索框
    LinearLayout hintArea;              // 没有搜索内容时的提示区域
    SwipeRefreshLayout refreshLayout;  // 刷新控件
    ExpandableListView listView;         // 搜索内容展现控件
    HelpCenterExpandableListViewAdapter adapter;
    boolean onoff = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center_search);
        findViewById();
        initView();
        // onoff = ?;
    }

    public void findViewById(){
        srarchTextView = (TextView)findViewById(R.id.title_text_id);
        hintArea = (LinearLayout)findViewById(R.id.scenic_search_result_empty);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.scenic_refresh_container);
        listView = (ExpandableListView)findViewById(R.id.expan_search_listview);
    }

    public void initView(){
        UIUtils.drawableRadiusTextView(srarchTextView, this.getResources().getColor(R.color.search_textview_background));  // 画出圆角
        hintArea.setVisibility(View.GONE);   // 初始化时不显示
        initExpandableListView();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // 注册下拉刷新按钮事件
            @Override
            public void onRefresh() {

            }
        });

        srarchTextView.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<String> group = new ArrayList<String>(0);
                Map<String, List<String>> child = new HashMap<String, List<String>>(0);
                for (int i = 0; i < srarchTextView.getText().length(); i++) {
                    group.add("AAAA" + i);
                    ArrayList<String> temp = new ArrayList<String>(0);
                    for (int k = 0; k < 2; k++) {
                        temp.add("AAAAAAAA" + i + k);
                    }
                    child.put(group.get(i), temp);
                }
                adapter.reloadDate(group, child);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
    }

    private void initExpandableListView(){
        ArrayList<String> group = new ArrayList<String>(0);
        Map<String, List<String>> child = new HashMap<String, List<String>>(0);

        adapter = new HelpCenterExpandableListViewAdapter(this, new HelpCenterFragement.ItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View convertView, ImageView imageView, int groupPosition, int childPosition, boolean isLastChild) {
                Intent intent = new Intent(Activity_Help_Center_Search.this, Activity_Help_Center_Datail.class);
                Bundle bundle = new Bundle();
                if (onoff) { // 产品说明
                    //Log.e("TAG", "------------------------------------------------------------>" + onoff);
                    bundle.putString("url", "https://www.baidu.com/");
                    intent.putExtras(bundle);
                } else {  // 操作说明
                    //Log.e("TAG", "------------------------------------------------------------>" + onoff);
                    bundle.putString("url", "http://blog.csdn.net/z1074971432/article/details/7547783");
                    intent.putExtras(bundle);
                }
                Activity_Help_Center_Search.this.startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
    }

}
