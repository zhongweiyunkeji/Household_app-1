package com.cdhxqh.household_app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.ui.fragment.HelpCenterFragement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hexian on 2015/8/12.
 */
public class HelpCenterExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<String> group = new ArrayList<String>(0);
    private Map<String, List<String>> child = new HashMap<String, List<String>>(0);

    HelpCenterFragement.ItemClickListener itemClickListener;

    public HelpCenterExpandableListViewAdapter(Context context, HelpCenterFragement.ItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
        if(itemClickListener == null){
            throw new RuntimeException("二级节点的监听器不能为空");
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child.get(group.get(groupPosition)).get(childPosition);
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child.get(group.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderGroup holder = null;
        if(convertView==null){
            convertView =  LayoutInflater.from(context).inflate(R.layout.help_center_adapter_group, null);
            holder = new ViewHolderGroup(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderGroup)convertView.getTag();
        }

        holder.textView.setText(group.get(groupPosition));
        holder.imgView.setImageResource(R.drawable.ic_menu_camerasb);
        convertView.setClickable(true);  // 设置点击事件无效
        // ImageLoader.getInstance().displayImage("http://www.baidu.com/abc.png", holder.imgView);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        ViewHolderChild holder = null;
        if(convertView==null){
            convertView =  LayoutInflater.from(context).inflate(R.layout.help_center_adapter_item, null);
            holder = new ViewHolderChild(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderChild)convertView.getTag();
        }

        holder.imgView.setImageResource(R.drawable.ic_menu_camerasb);
        holder.textView.setText(child.get(group.get(groupPosition)).get(childPosition));

        final View view = convertView;
        final ImageView imageView= holder.imgView;
        final int groupIndex = groupPosition;
        final int childIndex = childPosition;
        final boolean hasLastChild = !isLastChild;
        final   ImageView rightView =  holder.rightView;
        holder.rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(parent, view, rightView, groupIndex, childIndex, hasLastChild);
                }
            }
        });

        return convertView;
    }

    public void update(List<String> glist, Map<String, List<String>> ilist){
        for(String order : glist){
            boolean flag = true;
            for(String module : group){
                if(order.equals(module)){
                    flag = false;
                    break;
                }
            }
            if(flag){
                group.add(order);
                child.put(order, ilist.get(order));
            }
        }

        notifyDataSetChanged();
    }

    /**
     * 清除ListView数据
     */
    public void clear(){
        group.clear();
        child.clear();
    }

    /**
     *
     */
    public void reloadDate(List<String> glist, Map<String, List<String>> ilist){
        clear();
        update(glist, ilist);
    }



    public ArrayList<String> getGroup() {
        return group;
    }

    public static final class ViewHolderGroup {

        ImageView imgView;
        TextView textView;

        public ViewHolderGroup(View view){
            imgView = (ImageView)view.findViewById(R.id.group_img);
            textView = (TextView)view.findViewById(R.id.group_text);
        }

    }

    public static final class ViewHolderChild {

        ImageView imgView;
        TextView textView;
        ImageView rightView;

        public ViewHolderChild(View view){
            imgView = (ImageView)view.findViewById(R.id.item_img);
            textView = (TextView)view.findViewById(R.id.item_text);
            rightView = (ImageView)view.findViewById(R.id.item_right);
        }

    }

}