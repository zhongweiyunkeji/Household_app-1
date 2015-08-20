package com.cdhxqh.household_app.ui.adapter;

import android.content.Context;
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
import com.cdhxqh.household_app.model.MyDevice;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by hexian on 2015/8/20.
 */
public class EquipDelAdapter extends BaseAdapter {

    ArrayList<MyDevice> list = new ArrayList<MyDevice>(0);
    Context context;

    public EquipDelAdapter(Context context){
        this.context = context;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_mydevice_adapter_delete, null);
            holder = new ItemHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ItemHolder)convertView.getTag();
        }

        final MyDevice myDevice = (MyDevice)list.get(position);

        ImageLoader.getInstance().displayImage("http://tech.cnr.cn/techgd/20150819/W020150819406395420486.jpg", holder.imageView);  // 设备图片
        holder.sizeText.setText(""+myDevice.getSize());
        holder.divName.setText(myDevice.getName());
        holder.divAddr.setText(myDevice.getPlace());
        holder.divNum.setText(myDevice.getName());
        holder.dicStatuis.setText("在线");

        final CheckBox box = holder.checkBox;
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = box.isChecked();  // 注意：此处取得的状态是变化后的值！
                myDevice.setStatus(isChecked);  // 记住选中状态
            }
        });

        if(myDevice.isStatus()){
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        return convertView;
    }

    public void update(ArrayList<MyDevice> iistItem) {
        for (int i = 0; i < iistItem.size(); i++) {
            MyDevice obj = iistItem.get(i);
            boolean exist = false;
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getName() == obj.getName()) {
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

    public void clear(){
        this.list.clear();;
    }

    /**
     * 重新加载数据
     */
    public void reload(ArrayList<MyDevice> list){
        clear();
        update(list);
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

    public ArrayList<MyDevice> getList() {
        if(list == null){
            return new ArrayList<MyDevice>(0);
        }

        return list;
    }

    public static final class ItemHolder {

        CheckBox checkBox;
        ImageView imageView;
        TextView sizeText;
        TextView divName;
        TextView divAddr;
        TextView divNum;
        TextView dicStatuis;

        public ItemHolder(View view){
            checkBox = (CheckBox)view.findViewById(R.id.checkbox_single);  // 复选框
            imageView = (ImageView)view.findViewById(R.id.mydevice_ico);  // 图标
            sizeText = (TextView)view.findViewById(R.id.mydevice_size);    // 角标

            divName = (TextView)view.findViewById(R.id.mydevice_name);    // 设备名称
            divAddr = (TextView)view.findViewById(R.id.mydevice_place);    // 地点
            divNum = (TextView)view.findViewById(R.id.mydevice_number);    // 编号

            dicStatuis = (TextView)view.findViewById(R.id.mydevice_open);    // 状态
        }

    }

}
