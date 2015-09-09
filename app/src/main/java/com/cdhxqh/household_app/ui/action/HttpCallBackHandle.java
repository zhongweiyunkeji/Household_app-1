package com.cdhxqh.household_app.ui.action;

import org.apache.http.Header;

/**
 * Created by hexian on 2015/9/8.
 */
public interface HttpCallBackHandle {

    public void onSuccess(int statusCode, Header[] headers,String responseBody);

    public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error);

}
