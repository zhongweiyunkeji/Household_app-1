package com.cdhxqh.household_app.ui.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.format.DateUtils;

import java.util.Calendar;

public class DateTimePickerDialog extends AlertDialog implements OnClickListener
{
    private DateTimePicker mDateTimePicker;
    private Calendar mDate = Calendar.getInstance();
    private OnDateTimeSetListener mOnDateTimeSetListener;
    private String type;
    private long date;

    @SuppressWarnings("deprecation")
    public DateTimePickerDialog(Context context, long date, String type)
    {
        super(context);
        this.date = date;
        this.type = type;
        mDateTimePicker = new DateTimePicker(context, type);
        setView(mDateTimePicker);
        mDateTimePicker.setOnDateTimeChangedListener(new DateTimePicker.OnDateTimeChangedListener() {
            @Override
            public void onDateTimeChanged(DateTimePicker view, int year, int month, int day, int hour, int minute) {
                mDate.set(Calendar.YEAR, year);
                mDate.set(Calendar.MONTH, month);
                mDate.set(Calendar.DAY_OF_MONTH, day);
                mDate.set(Calendar.HOUR_OF_DAY, hour);
                mDate.set(Calendar.MINUTE, minute);
                mDate.set(Calendar.SECOND, 0);
                updateTitle(mDate.getTimeInMillis());
            }
        });

        setButton("设置", this);
        setButton2("取消", (OnClickListener) null);
        mDate.setTimeInMillis(date);
        updateTitle(mDate.getTimeInMillis());
    }

    public interface OnDateTimeSetListener
    {
        void OnDateTimeSet(AlertDialog dialog, long data, String week);
    }

    private void updateTitle(long date)
    {
        if(type.equals("week")) {
            setTitle("设置星期");
        } else if(type.equals("time")) {
            setTitle("设置");
        }
    }

    public void setOnDateTimeSetListener(OnDateTimeSetListener callBack)
    {
        mOnDateTimeSetListener = callBack;
    }

    public void onClick(DialogInterface arg0, int arg1)
    {
        if (mOnDateTimeSetListener != null)
        {
            if(type.equals("time")) {
                mOnDateTimeSetListener.OnDateTimeSet(this, mDate.getTimeInMillis(),  "");
            }else if(type.equals("week")) {
                mOnDateTimeSetListener.OnDateTimeSet(this, mDate.getTimeInMillis(),  DateUtils.formatDateTime(this.getContext(), mDate.getTimeInMillis(), DateUtils.FORMAT_SHOW_WEEKDAY));
            }
        }
    }
}
