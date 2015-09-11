package com.cdhxqh.household_app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.Alarm;
import com.cdhxqh.household_app.model.AlramProcessMsg;
import com.cdhxqh.household_app.utils.ToastUtil;

import java.util.ArrayList;
/**
 * Created by hexian on 2015/9/11.
 */
public class AlranProcessAdapter extends BaseAdapter {

    Context context;
    ArrayList<AlramProcessMsg> list = new ArrayList<AlramProcessMsg>(0);

    public AlranProcessAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public AlramProcessMsg getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AlramProcessMsg msg = list.get(position);
        ItemHolder holder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_alram_process_list_item, null);
            holder = new ItemHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ItemHolder)convertView.getTag();
        }

        holder.username.setText(msg.getUsername());
        holder.alarmtime.setText(msg.getStarttime());
        holder.alarmprocessmsg.setText(msg.getProcessResult());


        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showMessage(context, ""+msg.getAdid());
            }
        });

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void update( ArrayList<AlramProcessMsg> array){
        for (int i = 0; i < array.size(); i++) {
            AlramProcessMsg obj = array.get(i);
            boolean exist = false;
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getAdid() == obj.getAdid()) {
                    exist = true;
                    break;
                }
            }
            if (exist) continue;
            list.add(0, obj);
        }
        array = list;
        notifyDataSetChanged();
    }

    public static final class ItemHolder {

        TextView username;
        TextView alarmtime;
        TextView alarmprocessmsg;
        ImageView item;

        public ItemHolder(View view){
            username = (TextView)view.findViewById(R.id.username);
            alarmtime = (TextView)view.findViewById(R.id.alarmtime);
            alarmprocessmsg = (TextView)view.findViewById(R.id.alarmprocessmsg);
            item = (ImageView)view.findViewById(R.id.item_icon);
        }

    }

}
