package com.cdhxqh.household_app.config;

/**
 * Created by think on 2015/8/17.
 */
public class Constants {
    public static final String USER_INFO = "userinfo";
    public static final String NAME_KEY = "name_key";
    public static final String PASS_KEY = "pass_key";
    public static final String ISREMENBER = "isRemenber";
    public static final String ISFIRST = "isFirst";
    public static final String SESSIONID = "sessionId";
    public static final String SESSIONIDTRUE = "sessionIdTrue";
    public static final String LOGINUSERID = "LOGINUSERID";
    public static final String TOKEN = "TOKEN";


    /**设置Token**/
//    public static final String TOKEN_URL = "at.akpf73cs0opka1vmbbve5iavcb107cwo-2o7upbvuw0-07pjh1e-vtzavaaih";
    public static String TOKEN_URL = "at.1svw6lci0h93an1q7v6yace5db49gxzs-22y7368pu9-1qmmc0t-osttsfrfl";

    /**视频截图保存路径**/
    public static final String VIDEO_PATH="/storage/sdcard1/";

    // 182.92.158.158   192.168.1.101
    public static final String BASE_URL = "http://182.92.158.158:8080/security/";

    // 设备列表
    public static final String DEVICE_LIST = BASE_URL + "yscamera/list";

    // 报警记录
    public static final String ALARM_LIST = BASE_URL + "/ysalarm/list";

    // 用户登录的ID
    public static int USER_ID = -10;

    // 根据公户id获取accessToken
    public static final String ACCESSTOKEN = BASE_URL + "/ys/accessToken";

    // 根据设备id获取单个设备
    public static final String SINGLE_DEVICE = BASE_URL + "/yscamera/getInfo";

}
