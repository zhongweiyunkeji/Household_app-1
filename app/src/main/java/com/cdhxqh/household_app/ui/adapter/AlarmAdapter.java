package com.cdhxqh.household_app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.Contacters;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/10.
 */
public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {
    Context mContext;
    ArrayList<Contacters> contacts;

    public AlarmAdapter(Context paramContext, ArrayList<Contacters> contacts) {
        this.mContext = paramContext;
        this.contacts = contacts;
    }

    public void setIsSelected() {
        for(int i = 0; i<contacts.size(); i++) {
            contacts.get(i).setFlag(true);
        }
        dataChanged();
    }

    public void inverseSelected() {
        for(int i = 0; i<contacts.size(); i++) {
            contacts.get(i).setFlag(false);
        }
        dataChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_type_of_alarm_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        holder.contacts_name.setText(contacts.get(i).getName() + "\u3000(" + contacts.get(i).getType() + ")");
        holder.contacts_phone.setText("手机号:"  + contacts.get(i).getPhone());
        holder.checkbox_single.setChecked(contacts.get(i).isFlag());
        final int item = i;
//        final ViewHolder holders = holder;
//        final Animation animation2 = AnimationUtils.loadAnimation(mContext, R.anim.activity_close);
//        final boolean iss = is;
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (iss == true) {
//                    select_user.setVisibility(View.GONE);
//                    select_user.startAnimation(animation2);
//                    setIs(false);
//                    AlarmActivity.setIs(false);
//                }
//            }
//        });

            holder.checkbox_single.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    contacts.get(item).setFlag(isChecked);
                }
            });
    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * 联系人姓名
         */
        private TextView contacts_name;
        /**
         * 联系人电话
         */
        private TextView contacts_phone;

        /**
         *checkBox
         */
        private CheckBox checkbox_single;

        public ViewHolder(View view) {
            super(view);
            contacts_name = (TextView) view.findViewById(R.id.contacts_name);
            contacts_phone = (TextView) view.findViewById(R.id.contacts_phone);
            checkbox_single = (CheckBox) view.findViewById(R.id.checkbox_single);
        }
    }

    // 刷新list
    private void dataChanged() {
        // 通知listView刷新
        notifyDataSetChanged();
    }

    public ArrayList<Contacters> getContacts() {
        return contacts;
    }

//    /**
//     * 判断是否选择
//     */
//    public void setIs(boolean is) {
//        this.is = is;
//        notifyDataSetChanged();
//    }
}
