package com.cdhxqh.household_app.ui.action;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.videogo.openapi.bean.resp.CameraInfo;

/**
 * Created by hexian on 2015/9/3.
 */
public interface DeviceOnClick {

    public abstract void callback(RecyclerView.ViewHolder holder, int position, View view, CameraInfo info);

}
