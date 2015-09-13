package com.cdhxqh.household_app.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.config.Constants;
import com.cdhxqh.household_app.model.Alarm;
import com.cdhxqh.household_app.model.AlramProcessMsg;
import com.cdhxqh.household_app.ui.actvity.Activity_Write_Information;
import com.cdhxqh.household_app.ui.actvity.DealInformationActivity;
import com.cdhxqh.household_app.utils.ToastUtil;
import com.cdhxqh.household_app.zxing.decoding.Intents;

import java.util.ArrayList;
/**
 * Created by hexian on 2015/9/11.
 */
public class AlramProcessAdapter extends BaseAdapter {

    public static final ThreadLocal<AlramProcessMsg> sedMsgLocal = new ThreadLocal<AlramProcessMsg>();

    Context context;
    private int alram_uid;  // 记录报警信息的uid
    ArrayList<AlramProcessMsg> list = new ArrayList<AlramProcessMsg>(0);

    public AlramProcessAdapter(Context context){
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
        holder.alarmtime.setText(msg.getProcesstimeStr());
        holder.alarmprocessmsg.setText(msg.getProcessResult());


        String status = msg.getAlrmstatus();
        int uid = msg.getUid();
        int icon = -1;
        if("新增".equals(status) && (Constants.USER_ID == uid)){
            icon = R.drawable.btn_dcl;// 待处理
        } else
        if("新增".equals(status) && (Constants.USER_ID != uid)){
            icon = R.drawable.btn_dxz;  // 待协助
        } else
        if("已协助".equals(status)){
            icon = R.drawable.ic_helped;
        } else
        if("已处理".equals(status)){
            icon = R.drawable.btn_ycl;
        } else
        if("已关闭".equals(status)){
            icon = R.drawable.ic_stoped;
        } else
        if("已取消".equals(status)){
            icon = R.drawable.ic_canceled;
        }
        if(icon!=-1){
            holder.item.setBackgroundResource(icon);
        }

        holder.item.setOnClickListener(new View.OnClickListener() {  // 从图标转过去
            @Override
            public void onClick(View v) {
                startActivity(msg);
            }
        });
        holder.alram_item.setOnClickListener(new View.OnClickListener() {  // 从线性布局转过去
            @Override
            public void onClick(View v) {
                startActivity(msg);
            }
        });

        return convertView;
    }

    public void startActivity(AlramProcessMsg msg){
        Intent intent = new Intent(context, Activity_Write_Information.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("MPROCESSMSG", msg);
        AlramProcessAdapter.sedMsgLocal.remove();  // 每次设置值前先移除上一次设置的值
        sedMsgLocal.set(msg);
        bundle.putInt("alram_uid", alram_uid);
        intent.putExtras(bundle);
        ((Activity)context).startActivityForResult(intent, 1000);
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
        LinearLayout alram_item;

        public ItemHolder(View view){
            alram_item = (LinearLayout)view.findViewById(R.id.alram_item);
            username = (TextView)view.findViewById(R.id.username);
            alarmtime = (TextView)view.findViewById(R.id.alarmtime);
            alarmprocessmsg = (TextView)view.findViewById(R.id.alarmprocessmsg);
            item = (ImageView)view.findViewById(R.id.item_icon);
        }

    }

    public int getUid() {
        return alram_uid;
    }

    public void setUid(int uid) {
        this.alram_uid = uid;
    }
}
