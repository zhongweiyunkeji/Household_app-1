package com.cdhxqh.household_app.ui.widget;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdhxqh.household_app.R;

/**
 * Created by Administrator on 2015/8/14.
 */
public class CartoonDisplay {
    /**
     * 动画
     */
    Animation animation1, animation2;

    /**
     * 添加联系人菜单
     */
    private  LinearLayout select_user;

    /**
     * 关闭灰色界面
     */
    private  RelativeLayout select_p;

    /**
     * 判断关闭或打开
     */
    private static boolean is = false;

    private TextView create_user, putConnect;

    public CartoonDisplay(Activity activity, int item, String value)
    {
        animation1 = AnimationUtils.loadAnimation(activity,
                R.anim.activity_open);
        animation2 = AnimationUtils.loadAnimation(activity,
                R.anim.activity_close);

        /**
         * 添加联系人菜单
         */
        select_user = (LinearLayout) activity.findViewById(R.id.select_user);

        /**
         * 联系人
         */
        select_p = (RelativeLayout) activity.findViewById(R.id.select_p);

        create_user = (TextView) activity.findViewById(R.id.create_user);

        /**
         * 手机通讯录
         */
        putConnect = (TextView) activity.findViewById(R.id.putConnect);

        /**
         * 选择模式
         */
        if(item == 1) {
            create_user.setVisibility(View.VISIBLE);
            create_user.setText(value);
        }
    }


    ;

    public void display(){
        if(is == true){
            is = false;
            select_user.startAnimation(animation2);
            select_p.setVisibility(View.GONE);
            select_user.setVisibility(View.GONE);
        }else {
            is = true;
            select_p.setVisibility(View.VISIBLE);
            select_user.startAnimation(animation1);
            select_user.setVisibility(View.VISIBLE);
        }
    }


}
