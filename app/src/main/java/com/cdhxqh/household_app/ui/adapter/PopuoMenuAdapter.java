package com.cdhxqh.household_app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.Item;

import java.util.ArrayList;

/**
 * Created by hexian on 2015/8/16.
 */
public class PopuoMenuAdapter<E extends Item> extends BaseAdapter {

    Context context;
    int layoutRes;
    ArrayList<Item> list = new ArrayList<Item>(0);

    public PopuoMenuAdapter(Context context, int layoutRes, ArrayList<Item> list){
        this.context = context;
        this.layoutRes = layoutRes;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Item getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layoutRes, null);
            holder = new ItemHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ItemHolder)convertView.getTag();
        }

        Item item = list.get(position);
        holder.content.setText(item.text);        // 设置文字
        holder.icon.setImageResource(item.icon);  // 设置图标
        if(position == 0){
            convertView.setBackgroundResource(R.drawable.popmenu_item_top_background);
        } else
        if(position == list.size()-1){
            convertView.setBackgroundResource(R.drawable.popmenu_item_bottom_background);
        } else {
            convertView.setBackgroundResource(R.drawable.popmenu_item_center_background);
        }

        return convertView;
    }

    public void update(ListView listView){
        notifyDataSetChanged();;
    }

    public final static class ItemHolder {

        TextView content;
        ImageView icon;

        public ItemHolder(View view){
            icon = (ImageView)view.findViewById(R.id.popmenu_icon);
            content = (TextView)view.findViewById(R.id.popmenu_content);
        }
    }

}
