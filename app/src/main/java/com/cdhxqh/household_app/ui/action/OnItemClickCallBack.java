package com.cdhxqh.household_app.ui.action;

import android.view.View;

import com.cdhxqh.household_app.ui.adapter.UserSelectAdapter;

/**
 * Created by hexian on 2015/8/11.
 */
public interface OnItemClickCallBack {

    public abstract void onClick(UserSelectAdapter adapter, View view, int position);

}
