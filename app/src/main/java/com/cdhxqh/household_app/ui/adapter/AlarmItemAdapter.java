package com.cdhxqh.household_app.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.Alarm;
import com.cdhxqh.household_app.ui.action.AlarmOnClickCallBack;
import com.cdhxqh.household_app.ui.actvity.Activity_Write_Information;
import com.videogo.openapi.EzvizAPI;
import com.videogo.universalimageloader.core.DisplayImageOptions;
import com.videogo.universalimageloader.core.assist.FailReason;
import com.videogo.universalimageloader.core.download.DecryptFileInfo;
import com.videogo.universalimageloader.core.listener.ImageLoadingListener;
import com.videogo.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.util.ArrayList;

/**
 * Created by hexian on 2015/8/17.
 */
public class AlarmItemAdapter extends BaseAdapter {

    Context context;
    AlarmOnClickCallBack callBack;
    ArrayList<Alarm> list = new ArrayList<Alarm>();
    boolean showCheckBox;

    public AlarmItemAdapter(Context context, AlarmOnClickCallBack callBack, boolean showCheckBox){
        this.context = context;
        this.callBack = callBack;
        this.showCheckBox = showCheckBox;
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

    private Bitmap getBitMap(int resid){
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap mBitmap = BitmapFactory.decodeResource(context.getResources(),resid, opts);
        // 缩放图片
        Matrix matrix = new Matrix();
        matrix.postScale(0.15F, 0.15F);
        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);

        return mBitmap;
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

        final Alarm alarm = list.get(position);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .needDecrypt(alarm.isEncryption())
                .considerExifParams(true)
                .showImageForEmptyUri(R.drawable.alarm_encrypt_image_mid)
                .showImageOnFail(R.drawable.alarm_encrypt_image_mid)
                .showImageOnDecryptFail(R.drawable.alarm_encrypt_image_mid)
                .extraForDownloader(new DecryptFileInfo(alarm.getSerial(), alarm.getCheckSum()))
                .build();

        String imgUrl = alarm.getImg();
        EzvizAPI.getInstance().initImageLoader(context);  // 初始化
        com.videogo.universalimageloader.core.ImageLoader mImageLoader = com.videogo.universalimageloader.core.ImageLoader.getInstance();
        mImageLoader.displayImage(imgUrl, holder.img, options,
                new ImageLoadingListener() {

                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                       // Log.e("TAG", "---------------------------------------------------------->");
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        Log.e("TAG", "---------------------------------------------------------->"+failReason.getType());
                        Log.e("TAG", "---------------------------------------------------------->"+failReason.getCause());
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        Log.e("TAG", "---------------------------------------------------------->");
                        Log.e("TAG", "---------------------------------------------------------->");
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        Log.e("TAG", "---------------------------------------------------------->");
                        Log.e("TAG", "---------------------------------------------------------->");
                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view, int current, int total) {

                    }
                });

        holder.title.setText(alarm.getTitle());
        holder.msg.setText(alarm.getMsg());
        holder.date.setText(alarm.getDate());
        // holder.icon.setImageResource(alarm.getIcon());

        final Alarm a = alarm;
        final int p = position;
        final View v = convertView;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onClick(p, v, a);
            }
        });

        if(showCheckBox){
            holder.checkbox.setVisibility(View.VISIBLE);
            if(alarm.isStatus()){
                holder.checkbox.setChecked(true);
            } else {
                holder.checkbox.setChecked(false);
            }
        }

        holder.item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, Activity_Write_Information.class);
                context.startActivity(intent);
            }
        });


        final CheckBox box = holder.checkbox;
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = box.isChecked();  // 注意：此处取得的状态是变化后的值！
                alarm.setStatus(isChecked);  // 记住选中状态
                Log.e("TAG", "-----------p = " + p + "----------->" + isChecked);
            }
        });

        return convertView;
    }

    public void selectAll(){
        select(true);
    }

    public void unselectAll(){
        select(false);
    }

    private void select(boolean flag){
        int size = list.size();
        for(int i=0; i<size; i++){
            list.get(i).setStatus(flag);
        }
    }

    public void update(ArrayList<Alarm> array) {
        for (int i = 0; i < array.size(); i++) {
            Alarm obj = array.get(i);
            boolean exist = false;
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getDate().equals(obj.getDate()) && (list.get(j).getSerial().equals(obj.getSerial()))) {
                    exist = true;
                    break;
                }
            }
            if (exist) continue;
            list.add(0, obj);
        }
        array = list;

        try {
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear(){
        this.list.clear();;
    }

    public ArrayList<Alarm> getList() {
        if(list == null){
            return new ArrayList<Alarm>(0);
        }

        return list;
    }

    /**
     * 重新加载数据
     */
    public void reload(ArrayList<Alarm> list){
        clear();
        update(list);
    }

    public static final class ItemHolder {

        CheckBox checkbox;
        ImageView img;
        TextView title;
        TextView msg;
        TextView date;
        ImageView icon;
        ImageView item_button;


        public ItemHolder(View view){
            checkbox = (CheckBox)view.findViewById(R.id.checkbox);
            img = (ImageView)view.findViewById(R.id.item_img);
            title = (TextView)view.findViewById(R.id.item_title);
            msg = (TextView)view.findViewById(R.id.item_msg);
            date = (TextView)view.findViewById(R.id.item_date);
            // icon = (ImageView)view.findViewById(R.id.item_icon);
            item_button = (ImageView)view.findViewById(R.id.item_button);
        }

    }


}
