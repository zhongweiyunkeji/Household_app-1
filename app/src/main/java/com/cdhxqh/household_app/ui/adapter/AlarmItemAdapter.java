package com.cdhxqh.household_app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.Alarm;
import com.cdhxqh.household_app.ui.action.impl.AlarmOnClickCallBack;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by hexian on 2015/8/17.
 */
public class AlarmItemAdapter extends BaseAdapter {

    Context context;
    AlarmOnClickCallBack callBack;
    ArrayList<Alarm> list = new ArrayList<Alarm>();

    public AlarmItemAdapter(Context context, AlarmOnClickCallBack callBack){
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder holder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.alarm_item, null);
            holder = new ItemHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ItemHolder)convertView.getTag();
        }

        Alarm alarm = list.get(position);
        ImageLoader.getInstance().displayImage(alarm.getImg(), holder.img);
        holder.title.setText(alarm.getTitle());
        holder.msg.setText(alarm.getMsg());
        holder.date.setText(alarm.getDate());
        holder.icon.setImageResource(alarm.getIcon());

        final Alarm a = alarm;
        final int p = position;
        final View v = convertView;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onClick(p, v, a);
            }
        });

        return convertView;
    }

    public void update(ArrayList<Alarm> iistItem) {
        for (int i = 0; i < iistItem.size(); i++) {
            Alarm obj = iistItem.get(i);
            boolean exist = false;
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getTitle() == obj.getTitle()) {
                    exist = true;
                    break;
                }
            }
            if (exist) continue;
            list.add(0, obj);
        }
        iistItem = list;

        try {
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final class ItemHolder {

        ImageView img;
        TextView title;
        TextView msg;
        TextView date;
        ImageView icon;

        public ItemHolder(View view){
            img = (ImageView)view.findViewById(R.id.item_img);
            title = (TextView)view.findViewById(R.id.item_title);
            msg = (TextView)view.findViewById(R.id.item_msg);
            date = (TextView)view.findViewById(R.id.item_date);
            icon = (ImageView)view.findViewById(R.id.item_icon);
        }

    }


}