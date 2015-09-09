package com.cdhxqh.household_app.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.ProductModel;
import com.cdhxqh.household_app.ui.adapter.ProductAdapter;
import com.cdhxqh.household_app.ui.widget.ItemDivider;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/13.
 */
public class ProductFragment extends BaseFragment{
    /**
     *listView
     */
    RecyclerView list_product;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_product, container, false);
        findViewById(view);
        initView();
        return view;
    }

    protected void findViewById(View view) {
        list_product = (RecyclerView)view.findViewById(R.id.list_product);
    }

    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        //添加分割线
        list_product.addItemDecoration(new ItemDivider(this.getActivity(), ItemDivider.VERTICAL_LIST));
        list_product.setLayoutManager(layoutManager);
        list_product.setItemAnimator(new DefaultItemAnimator());

        ArrayList<ProductModel> contacts = new ArrayList<ProductModel>();
        for(int i = 0; i < 15; i++) {
            ProductModel c = new ProductModel();
            c.setProduct_id("监控");
            c.setPosition_id("阳台");
            c.setMonitor_time_id("开启");
            contacts.add(c);
        }

        ProductAdapter alarmAdapter = new ProductAdapter(this.getActivity(), contacts);

        list_product.setAdapter(alarmAdapter);

    }
}
