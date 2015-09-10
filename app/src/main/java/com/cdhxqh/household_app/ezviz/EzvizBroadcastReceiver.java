package com.cdhxqh.household_app.ezviz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.videogo.androidpn.AndroidpnUtils;
import com.videogo.androidpn.Constants;
import com.videogo.constant.Constant;
import com.videogo.constant.IntentConsts;
import com.videogo.openapi.EzvizAPI;
import com.videogo.util.Utils;

/**
 * 监听广播
 * 
 * @author fangzhihua
 * @data 2013-1-17
 */
public class EzvizBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "EzvizBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            EzvizAPI.getInstance().refreshNetwork();
        } else if(action.equals(Constant.ADD_DEVICE_SUCCESS_ACTION)) {

        } else if(action.equals(Constant.OAUTH_SUCCESS_ACTION)) {

        } else if (Constants.NOTIFICATION_RECEIVED_ACTION.equals(action)) {
            // 监听报警推送消息
            NotifierUtils.showNotification(context, intent);
        }
    }
}
