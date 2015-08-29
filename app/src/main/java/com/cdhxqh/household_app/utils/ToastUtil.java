package com.cdhxqh.household_app.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by hexian on 2015/8/28.
 */
public class ToastUtil {

    public static void showMessage(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG);
    }

}
