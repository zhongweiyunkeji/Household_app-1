package com.cdhxqh.household_app.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.FileItem;
import com.cdhxqh.household_app.ui.actvity.Activity_File_Detail_img;
import com.cdhxqh.household_app.ui.actvity.Activity_File_Detail_video;
import com.cdhxqh.household_app.ui.adapter.FileManagementExpandableListViewAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by think on 2015/8/19.
 */
public class FileManagementFragment extends BaseFragment {
    private ExpandableListView listView;
    private TextView tabsLeft;
    private TextView tabsRight;
    ArrayList<String> group = new ArrayList<String>(0);
    Map<String, List<FileItem>> child = new HashMap<String, List<FileItem>>(0);
    private FileManagementExpandableListViewAdapter adapter;
    public boolean onoff = false;
    SimpleDateFormat matter1=new SimpleDateFormat("MM-dd");
    FileItem fileItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filemanagement, container, false);
        findViewById(view);
        initView();
        return view;
    }

    public void findViewById(View view) {
        listView = (ExpandableListView) view.findViewById(R.id.fileexpandable_list);
        tabsLeft = (TextView) view.findViewById(R.id.tabs_left);
        tabsRight = (TextView) view.findViewById(R.id.tabs_right);
    }

    public void initView() {
        final int tabsLeftPadding_left = tabsLeft.getPaddingLeft();
        final int tabsTopPadding_left = tabsLeft.getPaddingTop();
        final int tabsRightPadding_left = tabsLeft.getPaddingRight();
        final int tabsBottomPadding_left = tabsLeft.getPaddingBottom();

        final int tabsLeftPadding_right = tabsRight.getPaddingLeft();
        final int tabsTopPadding_right = tabsRight.getPaddingTop();
        final int tabsRightPadding_right = tabsRight.getPaddingRight();
        final int tabsBottomPadding_right = tabsRight.getPaddingBottom();

        tabsLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabsLeft.setBackgroundResource(R.drawable.tabs_left_sel);
                tabsLeft.setTextColor(Color.parseColor("#FFFFFF"));
                tabsRight.setBackgroundResource(R.drawable.tabs_right_nol);
                tabsRight.setTextColor(Color.parseColor("#80858e"));
                tabsLeft.setPadding(tabsLeftPadding_left, tabsTopPadding_left, tabsRightPadding_left, tabsBottomPadding_left);
                tabsRight.setPadding(tabsLeftPadding_right, tabsTopPadding_right, tabsRightPadding_right, tabsBottomPadding_right);
                onoff = false;
                adapter = new FileManagementExpandableListViewAdapter(getActivity(),onoff, new ItemClickListener() {
                    @Override
                    public void onItemClick(ViewGroup parent, View convertView, ImageView imageView, int groupPosition, int childPosition, boolean isLastChild) {
                        if (onoff) {
                            Intent intent = new Intent(getActivity(), Activity_File_Detail_video.class);
                            intent.putExtra("name",child.get(group.get(groupPosition)).get(childPosition).getFilename());
                            getActivity().startActivity(intent);
                        } else {
                            Intent intent = new Intent(getActivity(), Activity_File_Detail_img.class);
                            intent.putExtra("name",child.get(group.get(groupPosition)).get(childPosition).getFilename());
                            getActivity().startActivity(intent);
                        }
                    }
                });
                listView.setAdapter(adapter);
                addimgdata();
            }
        });
        tabsRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabsLeft.setBackgroundResource(R.drawable.tabs_left_nol);
                tabsLeft.setTextColor(Color.parseColor("#80858e"));
                tabsRight.setBackgroundResource(R.drawable.tabs_right_sel);
                tabsRight.setTextColor(Color.parseColor("#FFFFFF"));
                tabsLeft.setPadding(tabsLeftPadding_left, tabsTopPadding_left, tabsRightPadding_left, tabsBottomPadding_left);
                tabsRight.setPadding(tabsLeftPadding_right, tabsTopPadding_right, tabsRightPadding_right, tabsBottomPadding_right);
                onoff = true;
                adapter = new FileManagementExpandableListViewAdapter(getActivity(), onoff, new ItemClickListener() {
                    @Override
                    public void onItemClick(ViewGroup parent, View convertView, ImageView imageView, int groupPosition, int childPosition, boolean isLastChild) {
                        if (onoff) {
                            Intent intent = new Intent(getActivity(), Activity_File_Detail_video.class);
                            intent.putExtra("name",child.get(group.get(groupPosition)).get(childPosition).getFilename());
                            getActivity().startActivity(intent);
                        } else {
                            Intent intent = new Intent(getActivity(), Activity_File_Detail_img.class);
                            intent.putExtra("name", child.get(group.get(groupPosition)).get(childPosition).getFilename());
                            getActivity().startActivity(intent);
                        }
                    }
                });
                listView.setAdapter(adapter);
                addvideodata();
            }
        });
        adapter = new FileManagementExpandableListViewAdapter(this.getActivity(),onoff, new ItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View convertView, ImageView imageView, int groupPosition, int childPosition, boolean isLastChild) {
                if (onoff) {
                    Intent intent = new Intent(getActivity(), Activity_File_Detail_video.class);
                    intent.putExtra("name",child.get(group.get(groupPosition)).get(childPosition).getFilename());
                    getActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), Activity_File_Detail_img.class);
                    intent.putExtra("name",child.get(group.get(groupPosition)).get(childPosition).getFilename());
                    getActivity().startActivity(intent);
                }
            }
        });
        listView.setAdapter(adapter);
        addimgdata();
    }

    private void addimgdata(){
        for (int i = 0; i < 3; i++) {
            if(i==0){
                group.add("今天");
            }else if(i==1){
                group.add("昨天");
            }else{//获取两天以前的日期
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, - i);
                Date monday = c.getTime();
                String preMonday = matter1.format(monday);
                group.add(preMonday);
            }
            ArrayList<FileItem> temp = new ArrayList<FileItem>(0);
            fileItem = new FileItem();
            fileItem.setFilename("海康DS-2CD" + i);
            fileItem.setFilenumber(12+i);
            fileItem.setFilesize(6.2+i);
            for (int k = 0; k < 2; k++) {
//                temp.add("海康DS-2CD" + i + k);
                temp.add(k,fileItem);
            }
            child.put(group.get(i), temp);
        }

        listView.setGroupIndicator(null);  // 去掉展开和收缩箭头
        updateExpandList(group, child);
//        addmore();
    }
    private void addvideodata(){
        for (int i = 0; i < 3; i++) {
            if(i==0){
                group.add("今天");
            }else if(i==1){
                group.add("昨天");
            }else{//获取两天以前的日期
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, - i);
                Date monday = c.getTime();
                String preMonday = matter1.format(monday);
                group.add(preMonday);
            }
            ArrayList<FileItem> temp = new ArrayList<FileItem>(0);
            fileItem = new FileItem();
            fileItem.setFilename("海康DS-2CD" + i);
            fileItem.setFilenumber(12+i);
            fileItem.setFilesize(6000+i);
            for (int k = 0; k < 2; k++) {
//                temp.add("海康DS-2CD" + i + k);
                temp.add(k,fileItem);
            }
            child.put(group.get(i), temp);
        }

        listView.setGroupIndicator(null);  // 去掉展开和收缩箭头
        updateExpandList(group, child);
    }

    private void addmore(){
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1000){
                    ArrayList<String> group = new ArrayList<String>(0);
                    Map<String, List<FileItem>> child = new HashMap<String, List<FileItem>>(0);
                    for (int i = 3; i < 8; i++) {
                        Calendar c = Calendar.getInstance();
                        c.add(Calendar.DATE, - i);
                        Date monday = c.getTime();
                        String preMonday = matter1.format(monday);
                        group.add(preMonday);
                        ArrayList<FileItem> temp = new ArrayList<FileItem>(0);
                        fileItem = new FileItem();
                        fileItem.setFilename("海康DS-2CD" + i);
                        fileItem.setFilenumber(12+i);
                        fileItem.setFilesize(6.2 + i);
                        for (int k = 0; k < 2; k++) {
                            temp.add(k,fileItem);
                        }
                        child.put(preMonday, temp);
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

    private void showimglist(){

    }
    private void showvideolist(){

    }

    public void updateExpandList(ArrayList<String> group, Map<String, List<FileItem>> child){
        if(group==null || child==null){
            throw new RuntimeException("组和子元素都不能为空");
        }
        adapter.update(group, child);
        for (int i = 0; i < adapter.getGroup().size(); i++) {  // 需要调用才方法才能默认展开ExpandableListView
            listView.expandGroup(i);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    public interface ItemClickListener {
        public void onItemClick(ViewGroup parent, View convertView, ImageView imageView, int groupPosition, int childPosition, boolean isLastChild);
    }


    final Timer timer = new Timer();
    TimerTask task;
    Handler handler;


}
