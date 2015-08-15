package com.cdhxqh.household_app.ui.actvity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.ui.widget.SwitchButton;

import java.util.Calendar;


/**
 * Created by Administrator on 2015/8/13.
 */
public class TypeActivity extends Activity{
    /**
     * 选择按钮
     */
    SwitchButton wiperSwitch;

    /**
     *设置录像
     */
    LinearLayout set_up_tape;

    /**
     *不设置录像
     */
    TextView type_introduce;
    /**
     *显示日期
     */
    private TextView week_value, week_value_a;

    /**
     *显示时间
     */
    private TextView time_value, time_value_a;

    /**
     * 选择星期
     */
    private ImageView selectWeek_id, selectWeek_id_a;

    /**
     * 选择时间
     */
    private ImageView select_time, select_time_a;

    /**
     * 日期值
     */
    private TextView timeValue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tape);
        findViewById();
        initView();
    }

    protected void findViewById() {
        wiperSwitch = (SwitchButton) findViewById(R.id.wiperSwitch);
        set_up_tape = (LinearLayout) findViewById(R.id.set_up_tape);
        type_introduce = (TextView) findViewById(R.id.type_introduce);

        /**
         * 选择日期时间
         */
        selectWeek_id = (ImageView) findViewById(R.id.selectWeek_id);
        selectWeek_id_a = (ImageView) findViewById(R.id.selectWeek_id_a);

        select_time = (ImageView) findViewById(R.id.select_time);
        select_time_a = (ImageView) findViewById(R.id.select_time_a);

        time_value = (TextView) findViewById(R.id.time_value);
        time_value_a = (TextView) findViewById(R.id.time_value_a);

        week_value = (TextView) findViewById(R.id.week_value);
        week_value_a = (TextView) findViewById(R.id.week_value_a);
    }

    protected void initView() {
        wiperSwitch.setOnChangeListener(switchOnChangeListener);

        /**
         * 选择日期时间
         */
        select_time.setOnClickListener( outTimeOnClickListener);
        select_time_a.setOnClickListener(outTimeOnClickListener_a);

        selectWeek_id.setOnClickListener( outDateOnClickListener);
        selectWeek_id_a.setOnClickListener(outDateOnClickListener_a);
    }

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
            timeValue.setText(hourOfDay + ":" + (min = String.valueOf(minute).length() != 2 ? "0" + String.valueOf(minute) : String.valueOf(minute)));
        }
    };

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            timeValue.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
        }
    };

    private TextView setTime(TextView timeValue) {
        return this.timeValue  = timeValue;
    }

    /**
     * 按钮是否
     */
    private SwitchButton.OnChangeListener switchOnChangeListener = new SwitchButton.OnChangeListener() {
        @Override
        public void onChange(SwitchButton sb, boolean state) {
            Toast.makeText(TypeActivity.this, state ? "是" : "否", Toast.LENGTH_SHORT).show();
            if(state) {
                set_up_tape.setVisibility(View.VISIBLE);
                type_introduce.setVisibility(View.GONE);
            }else {
                set_up_tape.setVisibility(View.GONE);
                type_introduce.setVisibility(View.VISIBLE);
            }
        }
    };
}
