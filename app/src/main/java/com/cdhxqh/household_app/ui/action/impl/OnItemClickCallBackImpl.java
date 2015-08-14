package com.cdhxqh.household_app.ui.action.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.cdhxqh.household_app.ui.action.OnItemClickCallBack;
import com.cdhxqh.household_app.ui.actvity.Activity_Registry_User;
import com.cdhxqh.household_app.ui.actvity.Activity_User_Type;
import com.cdhxqh.household_app.ui.adapter.UserSelectAdapter;

/**
 * Created by hexian on 2015/8/11.
 */
public class OnItemClickCallBackImpl implements OnItemClickCallBack {

    public static final int ACTIVITY_REGISTRY_RESPONSE1 = 0;

    public OnItemClickCallBackImpl(){

    }

    public void onClick(UserSelectAdapter adapter, View view, int position){
        Context ctx = adapter.getContext();
        if(ctx instanceof Activity_User_Type){
            adapter.setIndex(position);      // 关键代码
            adapter.notifyDataSetChanged(); // 关键代码
            Toast.makeText(adapter.getContext(), "您选择的是：" + adapter.getList().get(position), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            String text =  ((TextView)((ViewGroup)view).getChildAt(0)).getText().toString();
            bundle.putString("text", text);
            intent.putExtras(bundle);
            intent.setClass(ctx, Activity_Registry_User.class);
            ((Activity_User_Type)ctx).setResult(ACTIVITY_REGISTRY_RESPONSE1, intent);
            ((Activity_User_Type)ctx).finish();

            return;
        }
        adapter.setIndex(position);      // 关键代码
        adapter.notifyDataSetChanged(); // 关键代码
        Toast.makeText(adapter.getContext(), "您选择的是：" + adapter.getList().get(position), Toast.LENGTH_SHORT).show();
    }

}
