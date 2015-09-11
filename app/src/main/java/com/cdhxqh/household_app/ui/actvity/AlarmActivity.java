package com.cdhxqh.household_app.ui.actvity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.app.HttpManager;
import com.cdhxqh.household_app.config.Constants;
import com.cdhxqh.household_app.model.Alarm;
import com.cdhxqh.household_app.model.Contacters;
import com.cdhxqh.household_app.ui.action.HttpCallBackHandle;
import com.cdhxqh.household_app.ui.adapter.AlarmAdapter;
import com.cdhxqh.household_app.ui.widget.ItemDivider;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


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
    // 返回的联系人数据
    static ArrayList<Contacters> contacts = new ArrayList<Contacters>();

    ArrayList<Contacters> retContacts = new ArrayList<Contacters>();

    ArrayList<Contacters> selectContacts = new ArrayList<Contacters>();

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

    private int[] usersid = new int[0];

    private static final int EDIT_CONTACT = 3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_of_alarm);
        findViewById();
        initView();
        getData();
    }

    protected void findViewById() {
        // list相关id
        alarm_contacts = (RecyclerView) findViewById(R.id.alarm_contacts);

        // 全选
        checkbox_all = (CheckBox) findViewById(R.id.checkbox_all);

        // 确认
        edit = (TextView) findViewById(R.id.edit);
        // 添加联系人
        addContacts = (ImageView) findViewById(R.id.title_add_id);

        // 标题标签相关id
        back_imageview_id = (ImageView) findViewById(R.id.back_imageview_id);
        titleTextView = (TextView) findViewById(R.id.title_text_id);
        title_add_id = (ImageView) findViewById(R.id.title_add_id);
    }


    protected void initView() {
        //设置标签页显示方式
        back_imageview_id.setVisibility(View.VISIBLE);
        title_add_id.setVisibility(View.GONE);
        titleTextView.setText("联系人");

        checkbox_all.setOnCheckedChangeListener(checkOnClickListener);

        back_imageview_id.setOnTouchListener(backImageViewOnTouchListener);

        //返回至登录界面事件
        back_imageview_id.setOnClickListener(backImageViewOnClickListener);

        edit.setOnClickListener(itemOnClickListener);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        //添加分割线
        alarm_contacts.addItemDecoration(new ItemDivider(this, ItemDivider.VERTICAL_LIST));
        alarm_contacts.setLayoutManager(layoutManager);
        alarm_contacts.setItemAnimator(new DefaultItemAnimator());
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
        // 发送网络请求
        RequestParams maps = new RequestParams();
        maps.put("showCount", 10000);
        maps.put("currentPage", 1);
        HttpManager.sendHttpRequest(this, Constants.CONTACT, maps, "get", false, callback);

        Intent intent = getIntent();
        if(intent!=null){
            Bundle bundle = intent.getExtras();
            selectContacts = (ArrayList<Contacters>)bundle.getSerializable("contactList");
            String users = bundle.getString("users");
            if(users!=null){
                String[] array = users.split(",");
                if(array.length ==0 || "".equals(array[0])){

                } else {
                    usersid = new int[array.length];
                    for(int i=0; i<array.length; i++){
                        usersid[i] = Integer.parseInt(array[i]);
                    }
                }
            }
        }
    }
    /**
     * 确定
     */
    private View.OnClickListener itemOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(contacts.size() <= 0) {
//                new  AlertDialog.Builder(AlarmActivity.this).setTitle("标题").setMessage("请选择联系人").setPositiveButton("确定", null).show();
                finish();
            }else {
                retContacts.clear();
                if(contactsMessage.isEmpty()){
                    contactsMessage = alarmAdapter.getContacts();
                }
                for(Contacters c : contactsMessage){
                    if(c.isFlag()){
                        retContacts.add(c);
                    }
                }
                Intent intent=new Intent();
                intent.putExtra("contactList", (Serializable) retContacts);
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

    HttpCallBackHandle callback = new HttpCallBackHandle() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseBody) {
            if(responseBody!=null){
                try {
                    JSONObject jsonObject = new JSONObject(responseBody);
                    String srrorcode = jsonObject.getString("errcode");
                    if("SECURITY-GLOBAL-S-0".equals(srrorcode)){
                        AlarmActivity.this.contacts.clear();
                        JSONObject result = jsonObject.getJSONObject("result");
                        if(result!=null){
                            JSONArray array = result.getJSONArray("list");
                            for(int index=0; index<array.length(); index++){
                                JSONObject obj = array.getJSONObject(index);
                                Contacters contacters = new Contacters();
                                int uid =  obj.getInt("id");
                                if(uid == Constants.USER_ID){
                                    continue;
                                }
                                String username = obj.getString("username");
                                String mobile = obj.getString("mobile");
                                contacters.setUid(uid);
                                contacters.setName(username);
                                contacters.setPhone(mobile);
                                contacts.add(contacters);
                            }
                            alarmAdapter.updata(contacts);
                            alarmAdapter.setSelect(usersid);
                            // alarmAdapter.dataChanged();
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
