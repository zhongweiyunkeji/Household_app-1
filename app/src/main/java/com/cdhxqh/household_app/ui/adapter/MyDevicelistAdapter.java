package com.cdhxqh.household_app.ui.adapter;

import android.app.Dialog;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.config.Constants;
import com.cdhxqh.household_app.ezviz.TransferAPI;
import com.cdhxqh.household_app.ezviz.WaitDialog;
import com.cdhxqh.household_app.model.Alarm;
import com.cdhxqh.household_app.model.MyDevice;
import com.cdhxqh.household_app.ui.action.DeviceOnClick;
import com.cdhxqh.household_app.ui.actvity.Activity_Video_Control;
import com.videogo.exception.BaseException;
import com.videogo.exception.ErrorCode;
import com.videogo.openapi.bean.resp.CameraInfo;
import com.videogo.util.ConnectionDetector;
import com.videogo.util.Utils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by think on 2015/8/15.
 */
public class MyDevicelistAdapter extends RecyclerView.Adapter<MyDevicelistAdapter.ViewHolder> {
    private static final String TAG = "ReviewListAdapter";
    Context mContext;

    private boolean showSizeView = true;
    private boolean showDeviceSrarus = true;
    private boolean showSwitch = false;
    DeviceOnClick callback;

    ArrayList<MyDevice> list=new ArrayList<MyDevice>();

    public MyDevicelistAdapter(Context context, DeviceOnClick callback){
        this.mContext = context;
        this.callback = callback;
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
        final MyDevice info  = list.get(position);
        holder.nameView.setText(list.get(position).getDeviceName());  // 摄像头名称
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

        if(showSwitch){
            if(info.getUid().equals(""+Constants.USER_ID)){
                if (info.getDefence() == 1) {// 是否打开设备监控
                    holder.udstatus.setSelected(true);
                } else {
                    holder.udstatus.setSelected(false);
                }
            } else {
                holder.udstatus.setVisibility(View.GONE);
            }
            holder.udstatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new UpdateDefenceTask(info, mContext, MyDevicelistAdapter.this).execute();
                }
            });
        } else {
            holder.udstatus.setVisibility(View.GONE);
        }


        if(showDeviceSrarus){
            String status = info.isStatus() ? "在线" : "离线";
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

        final ViewHolder h = holder;
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback!=null){
                    callback.callback(h, position, v, info);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setShowSwitch(boolean showSwitch) {
        this.showSwitch = showSwitch;
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
        public ImageButton udstatus;
        public ViewHolder(final View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.mydevice_content_id);
            iconView = (ImageView) itemView.findViewById(R.id.mydevice_ico);
            sizeView = (TextView) itemView.findViewById(R.id.mydevice_size);
            nameView = (TextView) itemView.findViewById(R.id.mydevice_name);
            placeView = (TextView) itemView.findViewById(R.id.mydevice_place);
            numberView = (TextView) itemView.findViewById(R.id.mydevice_number);
            status = (TextView) itemView.findViewById(R.id.mydevice_open);
            udstatus = (ImageButton) itemView.findViewById(R.id.tab_devicedefence_btn);
        }
    }

    public void update(ArrayList<MyDevice> data, boolean merge) {
        Log.i(TAG, "mItems=" + list.size());
        if (merge) {
            for (int i = 0; i < data.size(); i++) {
                MyDevice device = data.get(i);
                boolean exist = false;
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(j).getDeviceSerial().equals(device.getDeviceSerial())) {
                        exist = true;
                        break;
                    }
                }
                if (exist) continue;
                list.add(0, device);
            }
        }
        data = list;

        // 反转设备信息
        Collections.reverse(list);

        notifyDataSetChanged();
    }

    public void setShowDeviceSrarus(boolean showDeviceSrarus) {
        this.showDeviceSrarus = showDeviceSrarus;
    }

    public void setShowSizeView(boolean showSizeView) {
        this.showSizeView = showSizeView;
    }



    private class UpdateDefenceTask extends AsyncTask<Void, Void, Boolean> {
        private MyDevice mCameraInfo;
        private Dialog mWaitDialog;
        private int mErrorCode;
        Context context;
        MyDevicelistAdapter adapter;

        public UpdateDefenceTask(MyDevice cameraInfo, Context context, MyDevicelistAdapter adapter) {
            mCameraInfo = cameraInfo;
            this.context = context;
            this.adapter = adapter;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mWaitDialog = new WaitDialog(context, android.R.style.Theme_Translucent_NoTitleBar);
            mWaitDialog.setCancelable(false);
            mWaitDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if (!ConnectionDetector.isNetworkAvailable(context)) {
                return null;
            }

            try {
                return TransferAPI.updateDefence(mCameraInfo.getDeviceSerial(), mCameraInfo.getDefence());
            } catch (BaseException e) {
                e.printStackTrace();
                mErrorCode = e.getErrorCode();
            } catch (JSONException e) {
                e.printStackTrace();
                mErrorCode = ErrorCode.ERROR_WEB_PARAM_ERROR;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mWaitDialog.dismiss();

            if (result != null && result) {
                Utils.showToast(context, mCameraInfo.getDefence() == 0 ? R.string.device_defence_open_success : R.string.device_defence_close_success);
                mCameraInfo.setDefence(mCameraInfo.getDefence()==0?1:0);
                if(adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            } else {
                Utils.showToast(context, mCameraInfo.getDefence()==0?R.string.device_defence_open_fail:R.string.device_defence_close_fail, mErrorCode);
            }
        }
    }
}
