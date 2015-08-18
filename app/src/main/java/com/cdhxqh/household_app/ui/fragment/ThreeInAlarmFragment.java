package com.cdhxqh.household_app.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.Alarm;
import com.cdhxqh.household_app.ui.action.AlarmOnClickCallBack;
import com.cdhxqh.household_app.ui.adapter.AlarmItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hexian on 2015/8/17.
 */
public class ThreeInAlarmFragment extends BaseFragment {

    SwipeRefreshLayout swipeRefreshLayout;
    ListView listView;
    AlarmItemAdapter adapter;
    boolean showCheckBox = false;
    boolean hideToolBar = false;
    RelativeLayout relativeLayout;

    CheckBox checkdAll;
    ImageView delBtn;

    public ThreeInAlarmFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle!=null){
            showCheckBox = bundle.getBoolean("showCheckBox");
            hideToolBar = bundle.getBoolean("hideToolBar");
        }
        View view = inflater.inflate(R.layout.three_in_alarm, container, false);
        findViewById(view);
        initView();
        return view;
    }

    public void findViewById(View view) {
        swipeRefreshLayout =  (SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
        listView = (ListView)view.findViewById(R.id.three_in_alarm_listview);
        relativeLayout = (RelativeLayout)view.findViewById(R.id.operate_area);
        checkdAll = (CheckBox)view.findViewById(R.id.checkd_all);
        delBtn = (ImageView)view.findViewById(R.id.del_btn);
    }

    public void initView() {

        adapter = new AlarmItemAdapter(getActivity(), new AlarmOnClickCallBack() {
            @Override
            public void onClick(int position, View convertView, Alarm alarm) {
                AlarmFragment fragment = (AlarmFragment)getFragmentManager().findFragmentByTag("AlarmFragment");
                if(fragment!=null){
                    Toast.makeText(getActivity(), "" + fragment.flag, Toast.LENGTH_SHORT).show();
                }
            }
        }, showCheckBox);
        listView.setAdapter(adapter);

        if(hideToolBar){
            relativeLayout.setVisibility(View.GONE);
        } else {
            relativeLayout.setVisibility(View.VISIBLE);
        }

        checkdAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {  // 全选按钮事件
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(adapter!=null){
                    if(isChecked){
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
                ArrayList<Alarm> list = adapter.getList();
                ArrayList<Alarm> clonList = (ArrayList<Alarm>) list.clone();
                int size = list.size();
                for (int index = 0; index < size; index++) {
                    Alarm alarm = list.get(index);
                    boolean flag = alarm.isStatus();
                    if (flag) {
                        clonList.remove(alarm);
                    }
                }
                adapter.reload(clonList);
                adapter.reload(clonList);
                if(adapter.getList().size() == 0){
                    checkdAll.setChecked(false);
                }
            }
        });

        ArrayList<Alarm> list = new ArrayList<Alarm>(0);
        for(int i=0; i<10; i++){
            Alarm alarm = new Alarm("2015-08-17 18:51:22", R.drawable.ic_menu_alarm_orange,
                    "http://c.hiphotos.baidu.com/news/w%3D638/sign=b918710f45a98226b8c12824b283b97a/e824b899a9014c083cdadbdf0c7b02087af4f4e3.jpg",
                    "海康DS-2CD2412F IN " +i, "家里门口 - 有可疑人员移动 "+i);
            list.add(alarm);
        }
        adapter.update(list);

    }

}
