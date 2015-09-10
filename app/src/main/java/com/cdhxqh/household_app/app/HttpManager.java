package com.cdhxqh.household_app.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.cdhxqh.household_app.api.ErrorType;
import com.cdhxqh.household_app.api.HttpRequestHandler;
import com.cdhxqh.household_app.api.JsonUtils;
import com.cdhxqh.household_app.api.Message;
import com.cdhxqh.household_app.config.Constants;
import com.cdhxqh.household_app.model.Alarm;
import com.cdhxqh.household_app.ui.action.HttpCallBackHandle;
import com.cdhxqh.household_app.ui.actvity.Activity_Login;
import com.cdhxqh.household_app.ui.widget.NetWorkUtil;
import com.cdhxqh.household_app.ui.widget.TestClass;
import com.cdhxqh.household_app.utils.SafeHandler;
import com.cdhxqh.household_app.utils.ToastUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2015/8/19.
 */
public class HttpManager {

    private static final String TAG="HttpManager12";

    /**
     *
     *
     * @param cxt      Context对象
     * @param refresh false表是从缓存中读取
     * @param handler handle
     * @param url     访问地址
     * @param args    参数
     */
    public static <E> void filterManager(final String SESSIONID, final Context cxt, final boolean refresh, final HttpRequestHandler<E> handler, final String url, final String...args) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams maps = new RequestParams();
        for(int i = 0; i<args.length/2; i++) {
            String key = args[i];
            String value = args[i+args.length/2];
            maps.put(key, value);
        }
        seletctHttpMethod(SESSIONID,handler, url, client, cxt, maps);
    }

    public static <E> void seletctHttpMethod(final String SESSIONID,final HttpRequestHandler<E> handler, final String url, final  AsyncHttpClient client,final Context cxt, final RequestParams maps) {
        switch (url) {
            /**
             * 邮箱重置密码、手机获取验证码
             */
            case (Message.PHONEPASS_URL) :
                if (maps.has("email")) {

                    client.post(url, maps, new TextHttpResponseHandler() {

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            SafeHandler.onFailure(handler, ErrorType.errorMessage(cxt, ErrorType.ErrorGetNotificationFailure));
                            Log.i("TAG", "");
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            if(checkSession(responseString)){  // 检查session是否过期, 过期将转到登录页面
                                redirectLoginPage(cxt);
                                return;
                            }
                            //解析返回的Json数据
                            try {
                                JSONObject jsonObject = new JSONObject(responseString);
                                int code = JsonUtils.parsingEmailStr(cxt, responseString);
                                if (code == 1) {
                                    SafeHandler.onSuccess(handler, (E)jsonObject.getString("errmsg"));
                                } else if (code == 2) {
                                    SafeHandler.onFailure(handler, jsonObject.getString("errmsg"));
                                } else if (code == 3) {
                                    SafeHandler.onFailure(handler, jsonObject.getString("errmsg"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }  else if(maps.has("mobile")){
                    client.post(url, maps, new TextHttpResponseHandler() {

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                            SafeHandler.onFailure(handler, ErrorType.errorMessage(cxt, ErrorType.ErrorGetNotificationFailure));
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            if(checkSession(responseString)){  // 检查session是否过期, 过期将转到登录页面
                                redirectLoginPage(cxt);
                                return;
                            }
                            //解析返回的Json数据
                            try {
                                JSONObject jsonObject = new JSONObject(responseString);
                                int code = JsonUtils.parsingPhoneCode(cxt, responseString);
                                if (code == 1) {
                                    SafeHandler.onSuccess(handler, (E)jsonObject.getString("errmsg"));
                                } else if (code == 2) {
                                    SafeHandler.onFailure(handler, jsonObject.getString("errmsg"));
                                } else if (code == 3) {
                                    SafeHandler.onFailure(handler, jsonObject.getString("errmsg"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                break;

            /**
             * 手机号重置密码
             */
            case (Message.PHONELINE_URL) :
                client.post(url, maps, new TextHttpResponseHandler() {

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        SafeHandler.onFailure(handler, ErrorType.errorMessage(cxt, ErrorType.ErrorGetNotificationFailure));
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        if(checkSession(responseString)){  // 检查session是否过期, 过期将转到登录页面
                            redirectLoginPage(cxt);
                            return;
                        }
                        //解析返回的Json数据
                        try {
                            JSONObject jsonObject = new JSONObject(responseString);
                            int code = JsonUtils.parsingPhonePassword(cxt, responseString);
                            if (code == 1) {
                                SafeHandler.onSuccess(handler, (E)jsonObject.getString("errmsg"));
                            } else if (code == 2) {
                                SafeHandler.onFailure(handler, jsonObject.getString("errmsg"));
                            } else if (code == 3) {
                                SafeHandler.onFailure(handler, jsonObject.getString("errmsg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;

            /**
             * 用户登录
             */
            case (Message.LOGIN_URL) :
                client.post(url, maps, new TextHttpResponseHandler() {

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        SafeHandler.onFailure(handler, ErrorType.errorMessage(cxt, ErrorType.ErrorGetNotificationFailure));
                        Log.i("TAG", "TAG");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Object[] objectList = new Object[4];
                        //解析返回的Json数据
                            boolean flag = JsonUtils.parsingAuthStr(cxt, responseString);
                            if (flag) {
                                for (Header h : headers) {
                                    String name = h.getName();
                                    if ("Set-Cookie".equals(name) && (-1 != url.indexOf(Message.LOGIN_URL))) {
                                        String cookie = h.getValue();
                                        objectList[1] = cookie.split(";")[0].split("=")[1];
                                        objectList[0] = getCurrentTime();
                                        break;
                                    }
                                }
                                try {
                                    JSONObject obj = new JSONObject(responseString);
                                    String result = obj.getString("result");
                                    JSONObject rs = new JSONObject(result);
                                    String token = rs.getString("accessToken");
                                    Constants.USER_ID = rs.getInt("id");
                                    objectList[3] = new Integer(Constants.USER_ID);
                                    if(token!=null){
                                        JSONObject access = new JSONObject(token);
                                        String accessToken = access.getString("accessToken");
                                        objectList[2] = accessToken;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                SafeHandler.onSuccess(handler, (E)objectList);
                            } else {
                                SafeHandler.onFailure(handler, "登录失败");
                            }
                    }
                });
                break;

            /**
             * 报警
             */
            case (Message.ALARM) :
                client.addHeader("Cookie", "JSESSIONID=" + SESSIONID);
                client.get(url, maps, new TextHttpResponseHandler() {

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        SafeHandler.onFailure(handler, ErrorType.errorMessage(cxt, ErrorType.ErrorGetNotificationFailure));
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        if(checkSession(responseString)){  // 检查session是否过期, 过期将转到登录页面
                            redirectLoginPage(cxt);
                            return;
                        }
                        //解析返回的Json数据 ArrayList<Alarm>
                        try {
                            JSONObject jsonObject = new JSONObject(responseString);
                            String errmsg = (String) jsonObject.getString("errmsg");
                            ArrayList<Alarm> arrayList = JsonUtils.parsingAlarm(cxt, responseString);


                            SafeHandler.onSuccess(handler, (E) arrayList);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            /**
             * 注册
             */
            case (Message.REG_CHECK_CODE) :{
                client.addHeader("Cookie", "JSESSIONID=" + SESSIONID);
                client.get(url, maps, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        SafeHandler.onFailure(handler, "错误消息");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        //解析返回的Json数据 ArrayList<Alarm>
                        try {
                            // {"errcode":"SECURITY-GLOBAL-E-12","errmsg":"该手机已被注册过,无法再次进行注册","result":null}
                            JSONObject jsonObject = new JSONObject(responseString);
                            String code = jsonObject.getString("errcode");
                            if ("SECURITY-GLOBAL-E-12".equals(code)) {// 已注册
                                SafeHandler.onSuccess(handler, (E)new String[]{code});
                                return;
                            } else {
                                SafeHandler.onSuccess(handler, (E)new String[]{code, });
                            }
                            SafeHandler.onSuccess(handler, null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            }

        }
    }

    public static Long getCurrentTime() {
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间
        String nowtime = d.format(new Date());
        long currentTime = 0;
        try {
            currentTime = d.parse(nowtime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentTime;
    }

    public static void sendHttpRequest(final Context context, final String url, final RequestParams maps, String method, final boolean showDialog, final HttpCallBackHandle callback){
        SharedPreferences myShared = context.getSharedPreferences(Constants.USER_INFO, Context.MODE_PRIVATE);
        String  sessionid = myShared.getString(Constants.SESSIONIDTRUE, "");
        if(method == null){
            method = "post";
        }
        method = method.toLowerCase().trim();
        final String m = method;
        if (NetWorkUtil.IsNetWorkEnable(context)) { // 检查网络是否开启
            final AsyncHttpClient client = new AsyncHttpClient();
            if(!"".equals(sessionid)){
                client.addHeader("Cookie", "JSESSIONID=" + sessionid);
            }

            if(showDialog){
                // TestClass.loading((Activity) context, "正在努力加载，请稍后... ...");
                Log.e("TAG", "thread 01 ------------------------------------------------------->"+Thread.currentThread().getName());
            }

            TextHttpResponseHandler ayncHandler = new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    try {
                        callback.onFailure(statusCode, headers, responseString, throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(showDialog){
                        // TestClass.closeLoading();
                    }
                    Log.e("TAG", "thread 03 ------------------------------------------------------->"+Thread.currentThread().getName());
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    try {
                        if(checkSession(responseString)){  // 检查session是否过期, 过期将转到登录页面
                            redirectLoginPage(context);
                            return;
                        }
                        callback.onSuccess(statusCode, headers, responseString);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(showDialog){
                        //  TestClass.closeLoading();
                    }
                    Log.e("TAG", "thread 02 ------------------------------------------------------->"+Thread.currentThread().getName());
                }
            };

            if("post".equals(m)){
                client.post(context, url, maps, ayncHandler);
            } else  {
                client.get(context, url, maps, ayncHandler);
            }
        }
    }

    public static boolean checkSession(String resStr){
        try {
            JSONObject json = new JSONObject(resStr);
            String errcode = json.getString("errcode");
            if("SECURITY-GLOBAL-E-4".equals(errcode)){
                return true;
            }
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void redirectLoginPage(Context context){
        Intent intent = new Intent(context, Activity_Login.class);
        context.startActivity(intent);
        ToastUtil.showMessage(context, "回话已超时，请重启新登录");
    }


}
