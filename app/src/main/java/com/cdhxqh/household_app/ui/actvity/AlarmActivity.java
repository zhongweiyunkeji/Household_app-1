package com.cdhxqh.household_app.ui.actvity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
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

import java.io.Serializable;
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
    private static ArrayList<Contacters> contactsMessage = new ArrayList<Contacters>();
    static ArrayList<Contacters> contacts;

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
        getData();
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

        contacts = new ArrayList<Contacters>();
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

        if(contacts.size()<=0) {
            for (int i = 0; i < 15; i++) {
                Contacters c = new Contacters();
                c.setName("张思");
                c.setType("老师");
                c.setPhone("15467656784");
                c.setFlag(false);
                contacts.add(c);
            }
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

    public static void update(ArrayList<Contacters> contactersList_a) {
        contactsMessage = contactersList_a;
    }

    public void getData() {
        if(contacts.size() > 0) {
            contacts = (ArrayList<Contacters>) getIntent().getSerializableExtra("contactList");
        }
    }
    /**
     * 确定
     */
    private View.OnClickListener itemOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(contactsMessage.size() <= 0) {
//                new  AlertDialog.Builder(AlarmActivity.this).setTitle("标题").setMessage("请选择联系人").setPositiveButton("确定", null).show();
                finish();
            }else {
                Intent intent=new Intent();
                intent.putExtra("contactList", (Serializable) contactsMessage);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Intent intent=new Intent();
            intent.putExtra("contactList", (Serializable) contactsMessage);
            setResult(RESULT_OK, intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
