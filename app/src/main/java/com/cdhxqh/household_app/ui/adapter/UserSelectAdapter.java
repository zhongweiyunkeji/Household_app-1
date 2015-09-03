package com.cdhxqh.household_app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.ui.action.OnItemClickCallBack;

import java.util.ArrayList;

/**
 * Created by hexian on 2015/8/10.
 */
public class UserSelectAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> list = new ArrayList<String>(0);
    private int index = -1;  // 记录当前选中的单选按钮
    OnItemClickCallBack callBack;


    public UserSelectAdapter(Context context, ArrayList<String> list, OnItemClickCallBack callBack){
        this.context = context;
        this.list = list;
        this.callBack = callBack;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ItemHolder holder = null;
        if(convertView == null){
            convertView =  LayoutInflater.from(context).inflate(R.layout.select_usertype_adapter_item, null);
            holder = new ItemHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ItemHolder)convertView.getTag();
        }

        holder.textView.setText(list.get(position));


        convertView.setOnClickListener(new View.OnClickListener() {  // 注册监听事件
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.onClick(UserSelectAdapter.this, v, position);
                }
            }
        });

        final View v = convertView;
        holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {// 注册单选按钮变更事件
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (callBack != null) {
                    callBack.onClick(UserSelectAdapter.this, v, position);
                }
            }
        });

        if(index == position){// 关键代码
            holder.radioButton.setChecked(true);
        } else {// 关键代码
            holder.radioButton.setChecked(false);
        }


        return convertView;
    }

    public static class ItemHolder {
        TextView textView;
        RadioButton radioButton;

        public ItemHolder(View view){
            textView = (TextView)view.findViewById(R.id.select_item_text);
            radioButton = (RadioButton)view.findViewById(R.id.select_item_radiobutton);
        }

    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Context getContext() {
        return context;
    }

    public ArrayList<String> getList() {
        return list;
    }
}
