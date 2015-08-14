package com.cdhxqh.household_app.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.ui.actvity.BaseActivity;
import com.cdhxqh.household_app.ui.fragment.NavigationDrawerFragment;

/**
 * Created by Administrator on 2015/8/13.
 */
public class ProductFragment extends BaseFragment  implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.activity_product, container, false);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }
}
