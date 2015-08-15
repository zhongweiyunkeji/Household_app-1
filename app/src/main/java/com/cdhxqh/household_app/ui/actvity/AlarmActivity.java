package com.cdhxqh.household_app.ui.actvity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.Contacters;
import com.cdhxqh.household_app.ui.adapter.AlarmAdapter;
import com.cdhxqh.household_app.ui.widget.ItemDivider;

import java.util.ArrayList;


/**
 * Created by Administrator on 2015/8/10.
 */
public class AlarmActivity extends Activity{
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
    private AlarmAdapter alarmAdapter;

    /**
     * 确认
     */
    private TextView edit;

    /**
     * 联系人信息
     */
    private ArrayList<Contacters> contactsMessage;
    ArrayList<Contacters> contacts = new ArrayList<Contacters>();

    /**
     *添加联系人
     */
    private ImageView addContacts;

    private RelativeLayout select_p;

    private TextView putConnect;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_of_alarm);
        findViewById();
        initView();
    }

    protected void findViewById() {
        /**
         * list相关id
         */
        alarm_contacts = (RecyclerView) findViewById(R.id.alarm_contacts);

        /**
         * 全选
         */
        checkbox_all = (CheckBox) findViewById(R.id.checkbox_all);

        /**
         * 确认
         */
        edit = (TextView) findViewById(R.id.edit);
        /**
         * 添加联系人
         */
        addContacts = (ImageView) findViewById(R.id.title_add_id);
    }


    protected void initView() {
        checkbox_all.setOnCheckedChangeListener(checkOnClickListener);

        edit.setOnClickListener(itemOnClickListener);

//        addContacts.setOnClickListener(addContactOnClickListener);



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        //添加分割线
        alarm_contacts.addItemDecoration(new ItemDivider(this, ItemDivider.VERTICAL_LIST));
        alarm_contacts.setLayoutManager(layoutManager);
        alarm_contacts.setItemAnimator(new DefaultItemAnimator());

        for(int i = 0; i < 15; i++) {
            Contacters c = new Contacters();
            c.setName("张思");
            c.setType("老师");
            c.setPhone("15467656784");
            c.setFlag(false);
            contacts.add(c);
        }

        alarmAdapter = new AlarmAdapter(this, contacts);

        alarm_contacts.setAdapter(alarmAdapter);
    }

    /**
     * 全选按钮事件
     */
    private CompoundButton.OnCheckedChangeListener checkOnClickListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                alarmAdapter.setIsSelected();
            }else {
                alarmAdapter.inverseSelected();
            }
        }
    };

    /**
     * 确定
     */
    private View.OnClickListener itemOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            contactsMessage = new ArrayList<Contacters>();
            ArrayList<Contacters> contacts = alarmAdapter.getContacts();
            for(int i = 0; i<contacts.size(); i++) {
                if(contacts.get(i).isFlag() == true) {
                    Contacters context = new Contacters();
                    context = contacts.get(i);
                    contactsMessage.add(context);
                }
            }
            if(contactsMessage.size() <= 0) {
                new  AlertDialog.Builder(AlarmActivity.this).setTitle("标题").setMessage("请选择联系人").setPositiveButton("确定", null).show();
            }else {
                Intent intent = new Intent();
                intent.setClass(AlarmActivity.this, SafeActivity.class);
                startActivity(intent);
            }
        }
    };
}
