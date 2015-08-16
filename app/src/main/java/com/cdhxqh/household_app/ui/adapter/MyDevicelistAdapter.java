package com.cdhxqh.household_app.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.MyDevice;
import com.cdhxqh.household_app.ui.actvity.Activity_Video_Control;
import com.cdhxqh.household_app.ui.actvity.TestActivity;

import java.util.ArrayList;

/**
 * Created by think on 2015/8/15.
 */
public class MyDevicelistAdapter extends RecyclerView.Adapter<MyDevicelistAdapter.ViewHolder> {
    private static final String TAG = "ReviewListAdapter";
    Context mContext;

    ArrayList<MyDevice> list=new ArrayList<MyDevice>();

    public MyDevicelistAdapter(Context context){
        this.mContext = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_mydevice_adapter, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.nameView.setText(list.get(position).getName());
        holder.sizeView.setText(list.get(position).getSize()+"");
        holder.placeView.setText(list.get(position).getPlace());
        holder.numberView.setText(list.get(position).getNumber());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("device_name", list.get(position).getName());
                intent.putExtras(bundle);
                intent.setClass(mContext,Activity_Video_Control.class);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout linearLayout;
        /**图片**/
        public ImageView iconView;
        /**角标数字**/
        public TextView sizeView;
        /**名字**/
        public TextView nameView;
        /**地点**/
        public TextView placeView;
        /**编号**/
        public TextView numberView;
        public ViewHolder(final View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.mydevice_content_id);
            iconView = (ImageView) itemView.findViewById(R.id.mydevice_ico);
            sizeView = (TextView) itemView.findViewById(R.id.mydevice_size);
            nameView = (TextView) itemView.findViewById(R.id.mydevice_name);
            placeView = (TextView) itemView.findViewById(R.id.mydevice_place);
            numberView = (TextView) itemView.findViewById(R.id.mydevice_number);
        }
    }

    public void update(ArrayList<MyDevice> data, boolean merge) {
        Log.i(TAG, "mItems=" + list.size());
        if (merge && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                MyDevice device = list.get(i);
                boolean exist = false;
                for (int j = 0; j < data.size(); j++) {
                    if (data.get(j) == device) {
                        exist = true;
                        break;
                    }
                }
                if (exist) continue;
                data.add(device);
            }
        }
        list = data;

        notifyDataSetChanged();
    }
}
