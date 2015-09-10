package com.cdhxqh.household_app.api;

import android.content.Context;

import com.cdhxqh.household_app.model.Alarm;
import com.cdhxqh.household_app.model.Ec_user;
import com.cdhxqh.household_app.utils.AccountUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

    /**
     * 解析登陆信息*
     * data json数据
     * isChecked 是否保存密码
     */
    public static boolean parsingAuthStr(final Context cxt, String data) {
        try {
            JSONObject json = new JSONObject(data);
            String errcode = json.getString("errcode");
            if (errcode.equals(Message.SUCCESS_LOGIN)) {
                JSONObject result = json.getJSONObject("result");
                Ec_user ec_user = new Ec_user();
                int userId = result.getInt("id");
                ec_user.setUserId(userId);
                ec_user.setEmail(result.getString("email"));
                ec_user.setUserName(result.getString("username"));
                ec_user.setPassword(result.getString("password"));
                ec_user.setMobilePhone(result.getString("mobile"));
                AccountUtils.writeLoginMember(cxt, ec_user);

                return true;
            } else {
                return false;
            }


        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 报警*
     */
    public static ArrayList<Alarm> parsingAlarm(final Context cxt, String data) {
        try {
            ArrayList<Alarm> alarmList = new ArrayList<Alarm>();
            JSONObject json = new JSONObject(data);
            String errcode = json.getString("errcode");
            if (errcode.equals(Message.SUCCESS_LINE_PASS)) {
                return null; //成功
            } else if (errcode.equals(Message.SESSIONID_FAILE)) {
                Alarm alarm = new Alarm();
                alarm.setBack(true);
                alarmList.add(alarm);
                return alarmList;
            }else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
