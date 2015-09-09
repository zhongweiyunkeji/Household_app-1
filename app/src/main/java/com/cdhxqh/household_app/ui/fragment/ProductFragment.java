package com.cdhxqh.household_app.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.app.HttpManager;
import com.cdhxqh.household_app.config.Constants;
import com.cdhxqh.household_app.model.MyDevice;
import com.cdhxqh.household_app.model.ProductModel;
import com.cdhxqh.household_app.ui.action.HttpCallBackHandle;
import com.cdhxqh.household_app.ui.adapter.ProductAdapter;
import com.cdhxqh.household_app.ui.widget.ItemDivider;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/13.
 */
public class ProductFragment extends BaseFragment{
    int currentPage = 1; // 当前页(索引从0开始)
    int showPage = 10;   // 每页显示
    RecyclerView list_product;
    SwipeRefreshLayout swipeRefreshLayout;
    ProductAdapter alarmAdapter;
    ArrayList<ProductModel> contacts = new ArrayList<ProductModel>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_product, container, false);
        findViewById(view);
        initView();
        return view;
    }

    protected void findViewById(View view) {
        list_product = (RecyclerView)view.findViewById(R.id.list_product);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
    }

    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        //添加分割线
        list_product.addItemDecoration(new ItemDivider(this.getActivity(), ItemDivider.VERTICAL_LIST));
        list_product.setLayoutManager(layoutManager);
        list_product.setItemAnimator(new DefaultItemAnimator());

        alarmAdapter = new ProductAdapter(getActivity(), contacts);
        list_product.setAdapter(alarmAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startAsynTask();  // 请求网络数据
            }
        });

        startAsynTask();  // 请求网络数据



    }

    /**
     * 初始化任务
     */
    private void startAsynTask() {
        RequestParams maps = new RequestParams();
        maps.put("showCount", showPage);
        maps.put("currentPage", currentPage);
        HttpManager.sendHttpRequest(getActivity(), Constants.DEVICE_LIST.trim(), maps, "get", false, responseHandler);
    }


    HttpCallBackHandle responseHandler = new HttpCallBackHandle() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseBody) {
            if(responseBody!=null){
                String resultStr = responseBody;
                ProductFragment.this.contacts.clear();;
                try {
                    JSONObject resultJson = new JSONObject(resultStr);
                    JSONObject result = resultJson.getJSONObject("result");
                    JSONArray rsList = result.getJSONArray("list");
                    for(int index=0; index<rsList.length(); index++){
                        JSONObject obj = rsList.getJSONObject(index);
                        String uid = obj.getInt("uid") + "";
                        String address = obj.getString("address");
                        String community = obj.getString("community");
                        String deviceSerial = obj.getString("deviceSerial");
                        int ca_id = obj.getInt("ca_id");

                        ProductModel model = new ProductModel();
                        model.setUid(uid);
                        model.setProduct_id(address);
                        model.setPosition_id(community);
                        model.setDeviceSerial(deviceSerial);
                        model.setCaid(ca_id);

                        ProductFragment.this.contacts.add(model);
                    }

                    if(!ProductFragment.this.contacts.isEmpty()){
                        alarmAdapter.update(ProductFragment.this.contacts, true);
                    }
                    currentPage++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
            if(swipeRefreshLayout.isRefreshing()){
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    };



}
