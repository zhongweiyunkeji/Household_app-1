package com.cdhxqh.household_app.ui.fragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.Alarm;
import com.cdhxqh.household_app.ui.action.impl.AlarmOnClickCallBack;
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

    public ThreeInAlarmFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle!=null){
            showCheckBox = bundle.getBoolean("showCheckBox");
        }
        View view = inflater.inflate(R.layout.three_in_alarm, container, false);
        findViewById(view);
        initView();
        return view;
    }

    public void findViewById(View view) {
        swipeRefreshLayout =  (SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
        listView = (ListView)view.findViewById(R.id.three_in_alarm_listview);
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
