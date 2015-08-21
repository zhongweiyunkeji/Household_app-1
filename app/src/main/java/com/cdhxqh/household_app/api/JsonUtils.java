package com.cdhxqh.household_app.api;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/8/20.
 */
public class JsonUtils {
    /**
     * 邮箱验证*
     */
    public static int parsingEmailStr(final Context cxt, String data) {
        try {
            JSONObject json = new JSONObject(data);
            String errcode = json.getString("errcode");
            if (errcode.equals(Message.SUCCESS_EMAIL)) {
                return 1; //成功
            } else if (errcode.equals(Message.FAILURE_EMAIL)) {
                return 2; //邮箱未注册
            } else {
                return 3; //邮箱注册失败
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取验证码*
     */
    public static int parsingPhoneCode(final Context cxt, String data) {
        try {
            JSONObject json = new JSONObject(data);
            String errcode = json.getString("errcode");
            if (errcode.equals(Message.SUCCESS_LINE_PASS)) {
                return 1; //成功
            } else if (errcode.equals(Message.FAILURE_EMAIL)) {
                return 2; //邮箱未注册
            } else {
                return 3; //邮箱注册失败
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 手机重置密码*
     */
    public static int parsingPhonePassword(final Context cxt, String data) {
        try {
            JSONObject json = new JSONObject(data);
            String errcode = json.getString("errcode");
            if (errcode.equals(Message.SUCCESS_LINE_PASS)) {
                return 1; //成功
            } else if (errcode.equals(Message.FAILURE_EMAIL)) {
                return 2; //邮箱未注册
            } else {
                return 3; //邮箱注册失败
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
