package com.cdhxqh.household_app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.MyDevice;
import com.cdhxqh.household_app.ui.adapter.MyDevicelistAdapter;
import com.cdhxqh.household_app.ui.widget.DividerItemDecoration;

import java.util.ArrayList;

/**
 * Created by think on 2015/8/15.
 */
public class MyDeviceFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private MyDevicelistAdapter myDevicelistAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mydevice, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.mydevice_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        myDevicelistAdapter = new MyDevicelistAdapter(getActivity());
        recyclerView.setAdapter(myDevicelistAdapter);
        addData();
        return view;
    }

    private void addData() {
        ArrayList<MyDevice> list = new ArrayList<MyDevice>();
        for (int i = 0; i < 3; i++) {
            MyDevice myDevice = new MyDevice();
            myDevice.setName("海康DS-2CD2412F-"+i);
            myDevice.setSize(i);
            myDevice.setPlace("地点"+i);
            myDevice.setNumber("1234567"+i);
            list.add(i,myDevice);
        }
        myDevicelistAdapter.update(list, true);
    }
}
