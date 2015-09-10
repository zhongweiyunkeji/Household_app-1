package com.cdhxqh.household_app.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.app.HttpManager;
import com.cdhxqh.household_app.config.Constants;
import com.cdhxqh.household_app.model.Contacters;
import com.cdhxqh.household_app.ui.action.HttpCallBackHandle;
import com.cdhxqh.household_app.ui.actvity.MainActivity;
import com.cdhxqh.household_app.ui.adapter.CommonContactAdapter;
import com.cdhxqh.household_app.ui.widget.ItemDivider;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/15.
 */
public class CommonContactFragment extends BaseFragment {
    /**
     *listView
     */
    RecyclerView list_product;

    /**
     * list
     */
    private RecyclerView alarm_contacts;

    /**
     * 全选
     */
    private CheckBox checkbox_all;

    /**
     * 适配器
     */
    private CommonContactAdapter commonContactAdapter;

    /**
     * 确认
     */
    private TextView edit;

    /**
     * 联系人信息
     */
    private static ArrayList<Contacters> contactsMessage = new ArrayList<Contacters>();
    static ArrayList<Contacters> contacts = new ArrayList<Contacters>();

    /**
     *添加联系人
     */
    private ImageView addContacts;

    private RelativeLayout select_p;

    private TextView putConnect;

    Contacters contacters = new Contacters();

    NavigationDrawerFragment.NavigationDrawerCallbacks navigationDrawerCallbacks;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        navigationDrawerCallbacks = (MainActivity)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_common_contact, container, false);
        findViewById(view);
        initView();
        getData();
        Intent intent = new Intent();
        intent.putExtra("contactList", (Serializable)contacts);
        intent.putExtra("FragmentName", "CommonContactFragment");
        navigationDrawerCallbacks.callbackFun(intent);
        return view;
    }

    protected void findViewById(View view) {
        alarm_contacts = (RecyclerView) view.findViewById(R.id.alarm_contacts);
    }

    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        //添加分割线
        alarm_contacts.addItemDecoration(new ItemDivider(getActivity(), ItemDivider.VERTICAL_LIST));
        alarm_contacts.setLayoutManager(layoutManager);
        alarm_contacts.setItemAnimator(new DefaultItemAnimator());

        commonContactAdapter = new CommonContactAdapter(getActivity(), contacts);
        alarm_contacts.setAdapter(commonContactAdapter);
    }

    public void getData(){
        // 发送网络请求
        RequestParams maps = new RequestParams();
        maps.put("showCount", 10000);
        maps.put("currentPage", 1);
        HttpManager.sendHttpRequest(getActivity(), Constants.CONTACT, maps, "get", false, callback);

       /* if (contacts.size() <= 0) {
            for (int i = 0; i < 5; i++) {
                Contacters c = new Contacters();
                c.setName("张思");
                c.setType("老师");
                c.setPhone("15467656784");
                c.setFlag(false);
                contacts.add(c);
            }
        }*/
    }

    public void updateList(ArrayList<Contacters> contacterseList)  {
        this.contacts = contacterseList;
        commonContactAdapter.updata(contacts);
        commonContactAdapter.dataChanged();
    }

    public void setData(Contacters c) {
        contacts.add(0, c);
        commonContactAdapter.updata(contacts);
        commonContactAdapter.dataChanged();
    }

    HttpCallBackHandle callback = new HttpCallBackHandle() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseBody) {
            if(responseBody!=null){
                try {
                    JSONObject jsonObject = new JSONObject(responseBody);
                    String srrorcode = jsonObject.getString("errcode");
                    if("SECURITY-GLOBAL-S-0".equals(srrorcode)){
                        CommonContactFragment.this.contacts.clear();
                        JSONObject result = jsonObject.getJSONObject("result");
                        if(result!=null){
                            JSONArray array = result.getJSONArray("list");
                            for(int index=0; index<array.length(); index++){
                                JSONObject obj = array.getJSONObject(index);
                                Contacters contacters = new Contacters();
                                int uid =  obj.getInt("id");
                                String username = obj.getString("username");
                                String mobile = obj.getString("mobile");
                                contacters.setUid(uid);
                                contacters.setName(username);
                                contacters.setPhone(mobile);
                                contacts.add(contacters);
                            }
                            commonContactAdapter.updata(contacts);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
            Log.i("TAG", "TAG");
            Log.i("TAG", "TAG");
        }
    };


}
