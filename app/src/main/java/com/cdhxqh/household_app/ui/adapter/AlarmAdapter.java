package com.cdhxqh.household_app.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.Contacters;
import com.cdhxqh.household_app.ui.actvity.AddContacterActivity;
import com.cdhxqh.household_app.ui.actvity.AlarmActivity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/10.
 */
public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {
    Context mContext;
    ArrayList<Contacters> contacts;
    private static final int EDIT_CONTACT = 3;

    public AlarmAdapter(Context paramContext, ArrayList<Contacters> contacts) {
        this.mContext = paramContext;
        this.contacts = contacts;
    }

    public void updata(ArrayList<Contacters> cList) {
        contacts = cList;
        notifyDataSetChanged();
    }

    public void setIsSelected() {
        for(int i = 0; i<contacts.size(); i++) {
            contacts.get(i).setFlag(true);
        }
        AlarmActivity.update(contacts);
        dataChanged();
    }

    public void inverseSelected() {
        for(int i = 0; i<contacts.size(); i++) {
            contacts.get(i).setFlag(false);
        }
        AlarmActivity.update(contacts);
        dataChanged();
    }

    public void setSelect(int[] ids){
        for(Contacters c : contacts){
            for(int i=0; i<ids.length; i++){
                if(c.getUid() == ids[i]){
                    c.setFlag(true);
                }
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_type_of_alarm_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        holder.contacts_name.setText(contacts.get(i).getName());
        holder.contacts_phone.setText("手机号:" + contacts.get(i).getPhone());
        holder.checkbox_single.setChecked(contacts.get(i).isFlag());
        final int item = i;
        final ViewHolder h = holder;
        holder.checkbox_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contacts.get(item).setFlag(h.checkbox_single.isChecked());
                AlarmActivity.update(contacts);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        // 联系人姓名
        private TextView contacts_name;
        // 联系人电话
        private TextView contacts_phone;
        // 选择框
        private CheckBox checkbox_single;

        public ViewHolder(View view) {
            super(view);
            contacts_name = (TextView) view.findViewById(R.id.contacts_name);
            contacts_phone = (TextView) view.findViewById(R.id.contacts_phone);
            checkbox_single = (CheckBox) view.findViewById(R.id.checkbox_single);
        }
    }

    // 刷新list
    public void dataChanged() {
        // 通知listView刷新
        notifyDataSetChanged();
    }

    public ArrayList<Contacters> getContacts() {
        return contacts;
    }

}
