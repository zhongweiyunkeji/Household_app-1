package com.cdhxqh.household_app.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
import com.videogo.openapi.bean.resp.CameraInfo;

import java.util.ArrayList;

/**
 * Created by think on 2015/8/15.
 */
public class MyDevicelistAdapter extends RecyclerView.Adapter<MyDevicelistAdapter.ViewHolder> {
    private static final String TAG = "ReviewListAdapter";
    Context mContext;

    private boolean showSizeView = true;
    private boolean showDeviceSrarus = true;

    ArrayList<CameraInfo> list=new ArrayList<CameraInfo>();

    public MyDevicelistAdapter(Context context){
        this.mContext = context;
    }

    private Bitmap getBitMap(int resid){
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap mBitmap = BitmapFactory.decodeResource(mContext.getResources(),resid, opts);
        // 缩放图片
        Matrix matrix = new Matrix();
        matrix.postScale(0.15F, 0.15F);
        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);

        return mBitmap;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_mydevice_adapter, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CameraInfo info  = list.get(position);
        holder.nameView.setText(list.get(position).getCameraName());  // 摄像头名称
        if(showSizeView){
            holder.sizeView.setText(0+"");
            holder.sizeView.setVisibility(View.VISIBLE);
        } else {
            holder.sizeView.setVisibility(View.GONE);
        }
        String name = "";
        int imgResid = 0;
        if(0 == position){
            // name = "办公室";
            imgResid = R.drawable.camera_office;
        } else
        if(1 == position){
            // name = "楼道";
            imgResid = R.drawable.camera_corridor;
        } else
        if(2 == position){
            // name = "操场";
            imgResid = R.drawable.camera_playground;
        }

        holder.iconView.setImageBitmap(getBitMap(imgResid));
        // holder.placeView.setText(name);
        holder.placeView.setVisibility(View.GONE);
        holder.numberView.setText(info.getDeviceSerial());
        if(showDeviceSrarus){
            String status = (info.getStatus() == 1) ? "在线" : "离线";
            holder.status.setText(status);
            if("在线".equals(status)){
                holder.status.setTextColor(mContext.getResources().getColor(R.color.green));
            } else
            if("离线".equals(status)){
                holder.status.setTextColor(mContext.getResources().getColor(R.color.grgray));
            }
            holder.status.setVisibility(View.VISIBLE);
        } else {
            holder.status.setVisibility(View.GONE);
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelable("device_name", info);
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
        public TextView status;
        public ViewHolder(final View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.mydevice_content_id);
            iconView = (ImageView) itemView.findViewById(R.id.mydevice_ico);
            sizeView = (TextView) itemView.findViewById(R.id.mydevice_size);
            nameView = (TextView) itemView.findViewById(R.id.mydevice_name);
            placeView = (TextView) itemView.findViewById(R.id.mydevice_place);
            numberView = (TextView) itemView.findViewById(R.id.mydevice_number);
            status = (TextView) itemView.findViewById(R.id.mydevice_open);
        }
    }

    public void update(ArrayList<CameraInfo> data, boolean merge) {
        Log.i(TAG, "mItems=" + list.size());
        if (merge && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                CameraInfo device = list.get(i);
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

    public void setShowDeviceSrarus(boolean showDeviceSrarus) {
        this.showDeviceSrarus = showDeviceSrarus;
    }

    public void setShowSizeView(boolean showSizeView) {
        this.showSizeView = showSizeView;
    }
}
