package com.cdhxqh.household_app.ui.widget;

import android.app.Activity;
import android.app.ProgressDialog;

import com.cdhxqh.household_app.R;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/8/19.
 */
public class TestClass {
    /**
     * 正在加载
     */
    private static ProgressDialog progressDialog;

    /*
    手机验证
     */
    public static boolean isMobileNO(String mobiles) {
        mobiles = (mobiles == null ? "" : mobiles);
        return Pattern.compile("^[1][3,4,5,8][0-9]{9}$").matcher(mobiles).matches();
    }

    /*
    邮箱验证
     */
    public static boolean isMailNo(String mail) {
        mail = (mail == null ? "" : mail);
        return Pattern.compile("^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$").matcher(mail).matches();
    }

    /**
     * 加载中
     */
    public static void loading(Activity activity, String message) {
        progressDialog = ProgressDialog.show(activity, null, message, true, true);
    }

    /**
     * 结束加载
     */
    public static void closeLoading() {
        progressDialog.dismiss();
    }
}
