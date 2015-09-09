package com.cdhxqh.household_app.ui.actvity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MotionEvent;
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
    static ArrayList<Contacters> contacts  = new ArrayList<Contacters>();

    /**
     * 返回按钮*
     */
    private ImageView back_imageview_id;
    /**
     * 标题*
     */
    private TextView titleTextView;
    /**
     * 搜索*
     */
    private ImageView title_add_id;

    /**
     *添加联系人
     */
    private ImageView addContacts;

    private RelativeLayout select_p;

    private TextView putConnect;

    private static final int EDIT_CONTACT = 3;

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

        /**
         * 标题标签相关id
         */
        back_imageview_id = (ImageView) findViewById(R.id.back_imageview_id);
        titleTextView = (TextView) findViewById(R.id.title_text_id);
        title_add_id = (ImageView) findViewById(R.id.title_add_id);
    }


    protected void initView() {
        //设置标签页显示方式
        back_imageview_id.setVisibility(View.VISIBLE);
        title_add_id.setVisibility(View.GONE);
        titleTextView.setText("忘记密码");

        checkbox_all.setOnCheckedChangeListener(checkOnClickListener);

        back_imageview_id.setOnTouchListener(backImageViewOnTouchListener);

        //返回至登录界面事件
        back_imageview_id.setOnClickListener(backImageViewOnClickListener);

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

    private View.OnTouchListener backImageViewOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setBackgroundColor(getResources().getColor(R.color.list_item_read));
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                v.setBackgroundColor(getResources().getColor(R.color.clarity));
            }
            return false;
        }
    };

    /**
     * 返回事件的监听*
     */
    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.putExtra("contactList", (Serializable) contactsMessage);
            setResult(RESULT_OK, intent);
            finish();
        }
    };

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
        if(contacts == null) {
            contacts = new ArrayList<Contacters>();
        }

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

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        Contacters contacters = new Contacters();

        switch (reqCode) {
            case (EDIT_CONTACT): {
                if (resultCode == Activity.RESULT_OK) {
                    contacters = (Contacters) data.getSerializableExtra("contactList");
                }
                break;
            }

        }

        if (contacters.getName() != null && contacters.getPhone() != null) {
            if (contacters.getType() == null || contacters.getType().toString().trim().equals("")) {
                contacters.setType("未分类");
            }
            updateData(contacters);
        }
    }

    public void updateData(Contacters c) {
        contacts.add(0, c);
        alarmAdapter.updata(contacts);
        alarmAdapter.dataChanged();
    }

    /**后退键
     *
     * @param keyCode
     * @param event
     * @return
     */
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
