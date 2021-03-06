package com.cdhxqh.household_app.ui.actvity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.Contacters;
import com.cdhxqh.household_app.ui.widget.SwitchButtonIs;
import com.cdhxqh.household_app.ui.widget.dialog.DateTimePickerDialog;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
        titleTextView.setText("安全服务中心");
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
        back_imageview_id.setOnTouchListener(backImageViewOnTouchListener);

        //返回至登录界面事件
        back_imageview_id.setOnClickListener(backImageViewOnClickListener);

        sb.setOnChangeListener(new SwitchButtonIs.OnChangeListener() {

            @Override
            public void onChange(SwitchButtonIs sb, boolean state) {
                // TODO Auto-generated method stub
                Log.d("switchButton", state ? "验票员" : "用户");
            }
        });

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
            finish();
        }
    };

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


//    private TextView setTime(TextView timeValue) {
//        return this.timeValue  = timeValue;
//    }

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
//            showDialog(1);
//            setTime(time_value);
            showDialog("time", time_value);
        }
    };

    /**
     * 出行时间
     */
    private View.OnClickListener outTimeOnClickListener_a = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            showDialog(1);
//            setTime(time_value_a);
            showDialog("time", time_value_a);
        }
    };

    /**
     * 出行日期
     */
    private View.OnClickListener outDateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            showDialog(2);
//            setTime(week_value);
            showDialog("week", week_value);
        }
    };

    /**
     * 出行日期
     */
    private View.OnClickListener outDateOnClickListener_a = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            showDialog(2);
//            setTime(week_value_a);
            showDialog("week", week_value_a);
        }
    };

    public void showDialog(final String type, final TextView textView) {
        DateTimePickerDialog dialog = new DateTimePickerDialog(this, System.currentTimeMillis(), type);
        dialog.setOnDateTimeSetListener(new DateTimePickerDialog.OnDateTimeSetListener() {
            public void OnDateTimeSet(AlertDialog dialog, long data, String week) {
//                Toast.makeText(SafeActivity.this, "您输入的日期是：" + getStringDate(date), Toast.LENGTH_LONG).show();
                textView.setText(getStringDate(data, type, week));
            }
        });
        dialog.show();
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     */
    public static String getStringDate(Long date, String type, String week)
    {
        String dateString = null;
        if(type.equals("week")) {
            dateString = week;
        }else if(type.equals("time")) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            dateString = formatter.format(date);
        }

        return dateString;
    }

//    @Override
//    protected Dialog onCreateDialog(int id) {
//        switch (id) {
//            case 1:
//                Log.v("Test", "--------start---------->");
//                Calendar c = Calendar.getInstance();
//                return new TimePickerDialog(this, onTimeSetListener, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
//            case 2:
//                Log.v("Test", "--------start---------->");
//                Calendar c_a = Calendar.getInstance();
//                return new DatePickerDialog(this, onDateSetListener, c_a.get(Calendar.YEAR), c_a.get(Calendar.MONTH), c_a.get(Calendar.DAY_OF_MONTH));
//            // return new TimePickerDialog(this,onTimeSetListener,22,3, true);
//        }
//        return super.onCreateDialog(id);
//    }
//
//    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
//
//        @Override
//        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//            String min;
//            timeValue.setText(hourOfDay + ":" +  (min = String.valueOf(minute).length() != 2 ? "0"+String.valueOf(minute) : String.valueOf(minute)));
//        }
//    };
//
//    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
//        @Override
//        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//            timeValue.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
//        }
//    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            AlarmActivity.contacts = null;
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
