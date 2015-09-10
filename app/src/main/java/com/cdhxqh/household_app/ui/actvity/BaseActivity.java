package com.cdhxqh.household_app.ui.actvity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;

import com.cdhxqh.household_app.app.AppManager;
import com.cdhxqh.household_app.config.Constants;
import com.cdhxqh.household_app.model.Ec_user;
import com.cdhxqh.household_app.utils.AccountUtils;

public class BaseActivity extends ActionBarActivity implements AccountUtils.OnAccountListener {
    protected SharedPreferences myshared;
    protected boolean mIsLogin;
    protected Ec_user ec_user;
    protected InputMethodManager imm;
    private TelephonyManager tManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        //添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        mIsLogin = AccountUtils.isLogined(this);
        if (mIsLogin)
            ec_user = AccountUtils.readLoginMember(this);
        AccountUtils.registerAccountListener(this);

        tManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    public void init() {
        myshared = this.getSharedPreferences(Constants.USER_INFO, Context.MODE_PRIVATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    public void onLogout() {
        mIsLogin = false;
    }

    @Override
    public void onLogin(Ec_user member) {
        mIsLogin = true;
        ec_user = member;
    }
}
