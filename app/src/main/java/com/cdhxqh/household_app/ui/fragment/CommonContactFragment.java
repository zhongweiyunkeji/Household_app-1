package com.cdhxqh.household_app.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.Contacters;
import com.cdhxqh.household_app.ui.actvity.MainActivity;
import com.cdhxqh.household_app.ui.adapter.CommonContactAdapter;
import com.cdhxqh.household_app.ui.widget.ItemDivider;

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

    NavigationDrawerFragment.NavigationDrawerCallbacks navigationDrawerCallbacks = new MainActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_common_contact, container, false);
        findViewById(view);
        initView();
        Intent intent = new Intent();
        intent.putExtra("contactList", (Serializable) contacts);
        intent.putExtra("FragmentName", "CommonContactFragment");
        navigationDrawerCallbacks.callbackFun(intent);
        return view;
    }

//    @Override
//    public void onActivityResult(int reqCode, int resultCode, Intent data) {
//        contacts = (ArrayList<Contacters>) data.getSerializableExtra("contactList");
//        ArrayList<Contacters> contactersLists = new ArrayList<Contacters>();
//        for(int i = 0; i<contactersList.size(); i++) {
//            if(contactersList.get(i).isFlag() == true) {
//                Contacters context = new Contacters();
//                context = contactersList.get(i);
//                contactersLists.add(context);
//            }
//        }
//        super.onActivityResult(reqCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            if(contactersLists.size() > 0) {
//                user_num.setText("已选择" + contactersLists.size() + "人");
//            }else {
//                user_num.setText("可选择多人");
//            }
//        }
//    }

    protected void findViewById(View view) {
        /**
         * list相关id
         */
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

        if (contacts.size() <= 0) {
            for (int i = 0; i < 15; i++) {
                Contacters c = new Contacters();
                c.setName("张思");
                c.setType("老师");
                c.setPhone("15467656784");
                c.setFlag(false);
                contacts.add(c);
            }
        }

        commonContactAdapter = new CommonContactAdapter(getActivity(), contacts);

        alarm_contacts.setAdapter(commonContactAdapter);
    }
}
