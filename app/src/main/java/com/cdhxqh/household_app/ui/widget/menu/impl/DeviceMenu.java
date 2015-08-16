package com.cdhxqh.household_app.ui.widget.menu.impl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.Item;
import com.cdhxqh.household_app.ui.adapter.PopuoMenuAdapter;
import com.cdhxqh.household_app.ui.widget.menu.PopMenu;

import java.util.ArrayList;

/**
 * Created by hexian on 2015/8/16.
 */
public class DeviceMenu extends PopMenu {

    ImageView hintImg;

    public DeviceMenu(Context context, View headView, View footerView,int popWinRes, int listItemRes) {
        super(context, headView, footerView);
        this.popWinRes = popWinRes;
        this.listItemRes = listItemRes;
        init();
    }

    @Override
    protected ListView findListView(View view) {
        return (ListView) view.findViewById(R.id.menu_listview);
    }

    @Override
    protected View onCreateView(Context context) {
        // R.layout.menu_mydevice 是popupmenu的布局文件   R.layout.menu_mydevice
        View view = LayoutInflater.from(context).inflate(popWinRes, null);
        hintImg = (ImageView)view.findViewById(R.id.head_hint_img);

        return view;
    }

    @Override
    protected PopuoMenuAdapter<Item> onCreateAdapter(Context context, ArrayList<Item> items) {
        // R.layout.menu_mydevice_item 是item的布局文件  R.layout.menu_mydevice_item
        return new PopuoMenuAdapter<Item>(context,listItemRes , items);
    }

    public ImageView getHintImg() {
        return hintImg;
    }

}
