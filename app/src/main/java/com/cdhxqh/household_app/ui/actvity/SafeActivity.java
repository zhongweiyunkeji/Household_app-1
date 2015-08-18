package com.cdhxqh.household_app.ui.actvity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.Contacters;
import com.cdhxqh.household_app.ui.widget.SwitchButtonIs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Administrator on 2015/8/11.
 */
public class SafeActivity extends BaseActivity {
    /**
     * 选择星期
     */
    private ImageView selectWeek_id, selectWeek_id_a;

    /**
     * 选择时间
     */
    private ImageView select_time, select_time_a;
    /**
     * 滑动按钮
     */
    SwitchButtonIs sb;

    /**
     *显示日期
     */
    private TextView week_value, week_value_a;

    /**
     *显示时间
     */
    private TextView time_value, time_value_a;

    private TextView timeValue;

    private ImageView contactser;

    Animation animation1, animation2;

    /**
     * 创建联系人
     */
    private LinearLayout create_user;

    private TextView putConnect;

    private static final int PICK_CONTACT = 2;
    /**
     * 联系人、手机号、取消
     */
    private TextView contacts_value, phone_num;

    //声明姓名，电话
    private String usernumber;

    //是否录像
    private ImageView isVideo_a_b;

    /**
     * 添加联系人菜单
     */
    private LinearLayout select_user;

//    private RelativeLayout select_p;

    /**
     *已选择联系人
     */
    ArrayList<Contacters> contactersList;

    /**
     *显示已选择联系人
     */
    TextView user_num;

    /**
     *察看联系人
     */
    LinearLayout view_user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_service);
        findViewById();
//        getData();
        initView();
    }

    protected void findViewById() {
        selectWeek_id = (ImageView) findViewById(R.id.selectWeek_id);
        selectWeek_id_a = (ImageView) findViewById(R.id.selectWeek_id_a);

        select_time = (ImageView) findViewById(R.id.select_time);
        select_time_a = (ImageView) findViewById(R.id.select_time_a);

        time_value = (TextView) findViewById(R.id.time_value);
        time_value_a = (TextView) findViewById(R.id.time_value_a);

        sb = (SwitchButtonIs) findViewById(R.id.wiperSwitch);

        week_value = (TextView) findViewById(R.id.week_value);
        week_value_a = (TextView) findViewById(R.id.week_value_a);

        contactser = (ImageView)findViewById(R.id.contactser);

        isVideo_a_b = (ImageView) findViewById(R.id.isVideo_a_b);

        /**
         * 导入通讯录
         */
        putConnect = (TextView) findViewById(R.id.putConnect);

//        /**
//         * 取消
//         */
//        cancel = (TextView) findViewById(R.id.cancel);

        contactser = (ImageView) findViewById(R.id.contactser);

        /**
         * 添加联系人菜单
         */
        select_user = (LinearLayout) findViewById(R.id.select_user);

        /**
         * 常用联系人
         */
        create_user = (LinearLayout) findViewById(R.id.create_user);

        /**
         * 添加联系人图片
         */
        select_user = (LinearLayout) findViewById(R.id.select_user);

//        /**
//         * 联系人灰色界面
//         */
//        select_p = (RelativeLayout) findViewById(R.id.select_p);
        user_num = (TextView)findViewById(R.id.user_num);

//        view_user = (LinearLayout) findViewById(R.id.view_user);
    }

    protected void initView() {
        select_time.setOnClickListener( outTimeOnClickListener);
        select_time_a.setOnClickListener(outTimeOnClickListener_a);

        selectWeek_id.setOnClickListener( outDateOnClickListener);
        selectWeek_id_a.setOnClickListener(outDateOnClickListener_a);

        contactser.setOnClickListener(contactserOnClickListener);

        contactser.setOnClickListener(addContactOnClickListener);

//        create_user.setOnClickListener(createOnClickListener);

        isVideo_a_b.setOnClickListener(videoOnClickListener);

//        view_user.setOnClickListener(viewOnClickListener);

        user_num.setText("可选择多人");

//        cancel.setOnClickListener(cancelOnClickListener);

//        select_p.setOnClickListener(cancelOnClickListener);

//        /**
//         * 导入通讯录
//         */
//        putConnect.setOnClickListener(connectOnClickListener);

        sb.setOnChangeListener(new SwitchButtonIs.OnChangeListener() {

            @Override
            public void onChange(SwitchButtonIs sb, boolean state) {
                // TODO Auto-generated method stub
                Log.d("switchButton", state ? "验票员" : "用户");
                Toast.makeText(SafeActivity.this, state ? "验票员" : "用户", Toast.LENGTH_SHORT).show();
            }
        });

    }

//    final Activity a = this;
//    private View.OnClickListener cancelOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            new CartoonDisplay(a, 1).display();
//        }
//    };

    /**
     * 是否录像
     */
    private View.OnClickListener videoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(SafeActivity.this,TypeActivity.class);
            startActivity(intent);
        }
    };

    /**
     * 导入通讯录
     */
//    final Context a = this;
//    private View.OnClickListener connectOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            ContactsTool contactsTool = new ContactsTool();
//            contactsTool.testGetContacts(a);
//            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//            startActivityForResult(intent, PICK_CONTACT);
//        }
//    };
//
//    @Override
//    public void onActivityResult(int reqCode, int resultCode, Intent data) {
//        super.onActivityResult(reqCode, resultCode, data);
//
//        switch (reqCode) {
//            case (PICK_CONTACT) :
//                if (resultCode == Activity.RESULT_OK) {
//                    Uri contactData = data.getData();
//                    Cursor c =  managedQuery(contactData, null, null, null, null);
//                    if (c.moveToFirst()) {
//                        ContentResolver reContentResolverol = getContentResolver();
//                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                        contacts_value.setText(name);
//                        //条件为联系人ID
//                        String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
//                        Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                                null,
//                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
//                                null,
//                                null);
//                        while (phone.moveToNext()) {
//                            usernumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                            phone_num.setText(usernumber);
//                            if(is == true){
//                                is = false;
//                                select_user.startAnimation(animation2);
//                                select_p.setVisibility(View.GONE);
//                                select_user.setVisibility(View.GONE);
//                            }
//                        }
//                    }
//                    }
//                break;
//                }
//
//        }

//    /**
//     * 察看联系人
//     */
//    private View.OnClickListener viewOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            if(contactersList != null) {
//                if(contactersList.size() > 0) {
//                    Intent intent = new Intent();
//                    intent.setClass(SafeActivity.this, ViewUserActivity.class);
//                    intent.putExtra("contactList", (Serializable) contactersList);
//                    startActivityForResult(intent, PICK_CONTACT);
//                }
//            }
//        }
//    };

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        contactersList = (ArrayList<Contacters>) data.getSerializableExtra("contactList");
        ArrayList<Contacters> contactersLists = new ArrayList<Contacters>();
        for(int i = 0; i<contactersList.size(); i++) {
            if(contactersList.get(i).isFlag() == true) {
                Contacters context = new Contacters();
                context = contactersList.get(i);
                contactersLists.add(context);
            }
        }
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if(contactersLists.size() > 0) {
                user_num.setText("已选择" + contactersLists.size() + "人");
            }else {
                user_num.setText("可选择多人");
            }
        }
//        if(resultCode == Activity.RESULT_FIRST_USER) {
//            if(contactersLists.size() > 0) {
//                user_num.setText("已选择" + contactersLists.size() + "人");
//            }else {
//                user_num.setText("可选择多人");
//            }
//        }
    }

    /**
     * 添加联系人
     */
    private View.OnClickListener addContactOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(SafeActivity.this, AlarmActivity.class);
            intent.putExtra("contactList", (Serializable) contactersList);
            startActivityForResult(intent, PICK_CONTACT);
        }
    };

//    private View.OnClickListener createOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent intent=new Intent();
////            intent.setClass(SafeActivity.this,AlarmActivity.class);
//            startActivity(intent);
//        }
//    };

//    private View.OnClickListener selectOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            if(is == true){
//                is = false;
//                select_user.startAnimation(animation2);
//                select_p.setVisibility(View.GONE);
//                select_user.setVisibility(View.GONE);
//            }
//        }
//    };

    private TextView setTime(TextView timeValue) {
        return this.timeValue  = timeValue;
    }

    /**
     * 选择联系人
     */
    private View.OnClickListener contactserOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
//            intent.setClass(SafeActivity.this,AlarmActivity.class);
            startActivity(intent);
        }
    };

    /**
     * 出行时间
     */
    private View.OnClickListener outTimeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDialog(1);
            setTime(time_value);
        }
    };

    /**
     * 出行时间
     */
    private View.OnClickListener outTimeOnClickListener_a = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDialog(1);
            setTime(time_value_a);
        }
    };

    /**
     * 出行日期
     */
    private View.OnClickListener outDateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDialog(2);
            setTime(week_value);
        }
    };

    /**
     * 出行日期
     */
    private View.OnClickListener outDateOnClickListener_a = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDialog(2);
            setTime(week_value_a);
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                Log.v("Test", "--------start---------->");
                Calendar c = Calendar.getInstance();
                return new TimePickerDialog(this, onTimeSetListener, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
            case 2:
                Log.v("Test", "--------start---------->");
                Calendar c_a = Calendar.getInstance();
                return new DatePickerDialog(this, onDateSetListener, c_a.get(Calendar.YEAR), c_a.get(Calendar.MONTH), c_a.get(Calendar.DAY_OF_MONTH));
            // return new TimePickerDialog(this,onTimeSetListener,22,3, true);
        }
        return super.onCreateDialog(id);
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String min;
            timeValue.setText(hourOfDay + ":" +  (min = String.valueOf(minute).length() != 2 ? "0"+String.valueOf(minute) : String.valueOf(minute)));
        }
    };

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            timeValue.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            AlarmActivity.contacts = null;
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
