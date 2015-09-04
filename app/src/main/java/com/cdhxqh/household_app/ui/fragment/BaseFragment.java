package com.cdhxqh.household_app.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.cdhxqh.household_app.model.Ec_user;
import com.cdhxqh.household_app.ui.actvity.BaseActivity;
import com.cdhxqh.household_app.utils.AccountUtils;


/**
 * Created by yw on 2015/5/3.
 */
public class BaseFragment extends Fragment  {
    private static final String TAG="BaseFragment";

    protected boolean mIsLogin;

    protected Ec_user ec_user;
    protected BackHandledInterface mBackHandledInterface;

//    protected FootUpdate mFootUpdate = new FootUpdate();

    public static interface BackHandledInterface {
        public abstract void setSelectedFragment(BaseFragment selectedFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsLogin = AccountUtils.isLogined(getActivity());
        ec_user = AccountUtils.readLoginMember(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public boolean onBackPressed() {
        return false;
    }


    final public BaseActivity getBaseActivity() {
        return ((BaseActivity) super.getActivity());
    }

}
