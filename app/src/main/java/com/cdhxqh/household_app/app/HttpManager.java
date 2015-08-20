package com.cdhxqh.household_app.app;

import android.content.Context;

import com.cdhxqh.household_app.api.ErrorType;
import com.cdhxqh.household_app.api.HttpRequestHandler;
import com.cdhxqh.household_app.api.JsonUtils;
import com.cdhxqh.household_app.api.Message;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/8/19.
 */
public class HttpManager {
    /**
     *
     *
     * @param cxt      Context对象
     * @param refresh false表是从缓存中读取
     * @param handler handle
     * @param url     访问地址
     * @param args    参数
     */
    public static <E> void filterManager(final Context cxt, final boolean refresh, final HttpRequestHandler<E> handler, final String url, final String...args) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams maps = new RequestParams();
        for(int i = 0; i<args.length/2; i++) {
            String key = args[i];
            String value = args[i+args.length/2];
            maps.put(key, value);
        }
        seletctHttpMethod(handler, url, client, cxt, maps);
    }

    public static <E> void seletctHttpMethod(final HttpRequestHandler<E> handler, final String url, final  AsyncHttpClient client,final Context cxt, final RequestParams maps) {
        switch (url) {
            /**
             * 邮箱重置密码、手机获取验证码
             */
            case (Message.PHONEPASS_URL) :
                if (maps.has("email")) {

                    client.post(url, maps, new TextHttpResponseHandler() {

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            ErrorType.SafeHandler.onFailure(handler, ErrorType.errorMessage(cxt, ErrorType.ErrorGetNotificationFailure));
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            //解析返回的Json数据
                            try {
                                JSONObject jsonObject = new JSONObject(responseString);
                                int code = JsonUtils.parsingEmailStr(cxt, responseString);
                                if (code == 1) {
                                    ErrorType.SafeHandler.onSuccess(handler, jsonObject.getString("errmsg"));
                                } else if (code == 2) {
                                    ErrorType.SafeHandler.onFailure(handler, jsonObject.getString("errmsg"));
                                } else if (code == 3) {
                                    ErrorType.SafeHandler.onFailure(handler, jsonObject.getString("errmsg"));
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
                            ErrorType.SafeHandler.onFailure(handler, ErrorType.errorMessage(cxt, ErrorType.ErrorGetNotificationFailure));
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            //解析返回的Json数据
                            try {
                                JSONObject jsonObject = new JSONObject(responseString);
                                int code = JsonUtils.parsingPhoneCode(cxt, responseString);
                                if (code == 1) {
                                    ErrorType.SafeHandler.onSuccess(handler, jsonObject.getString("errmsg"));
                                } else if (code == 2) {
                                    ErrorType.SafeHandler.onFailure(handler, jsonObject.getString("errmsg"));
                                } else if (code == 3) {
                                    ErrorType.SafeHandler.onFailure(handler, jsonObject.getString("errmsg"));
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
                        ErrorType.SafeHandler.onFailure(handler, ErrorType.errorMessage(cxt, ErrorType.ErrorGetNotificationFailure));
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        //解析返回的Json数据
                        try {
                            JSONObject jsonObject = new JSONObject(responseString);
                            int code = JsonUtils.parsingPhonePassword(cxt, responseString);
                            if (code == 1) {
                                ErrorType.SafeHandler.onSuccess(handler, jsonObject.getString("errmsg"));
                            } else if (code == 2) {
                                ErrorType.SafeHandler.onFailure(handler, jsonObject.getString("errmsg"));
                            } else if (code == 3) {
                                ErrorType.SafeHandler.onFailure(handler, jsonObject.getString("errmsg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        }
    }
}
