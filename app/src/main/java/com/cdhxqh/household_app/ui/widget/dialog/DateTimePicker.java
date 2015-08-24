package com.cdhxqh.household_app.ui.widget.dialog;

import android.content.Context;
import android.text.format.DateFormat;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;

import com.cdhxqh.household_app.R;

import java.util.Calendar;

public class DateTimePicker extends FrameLayout
{
	private final NumberPicker mDateSpinner;
	private final NumberPicker mHourSpinner;
	private final NumberPicker mMinuteSpinner;
	private final NumberPicker name;
	private Calendar mDate;
    private int mHour,mMinute; 
    private String[] mDateDisplayValues = new String[7];
    private OnDateTimeChangedListener mOnDateTimeChangedListener;
    
    public DateTimePicker(Context context, String type)
	{
    	super(context);
    	 mDate = Calendar.getInstance();
    	 
         mHour=mDate.get(Calendar.HOUR_OF_DAY);
         mMinute=mDate.get(Calendar.MINUTE);
    	 if(type.equals("week")) {
			 inflate(context, R.layout.activity_weekdialog, this);
		 }else if(type.equals("time")) {
			 inflate(context, R.layout.activity_timedialog, this);
		 }
    	 
    	 mDateSpinner=(NumberPicker)this.findViewById(R.id.np_date);
    	 mDateSpinner.setMinValue(0);
         mDateSpinner.setMaxValue(6);
         updateDateControl();
    	 mDateSpinner.setOnValueChangedListener(mOnDateChangedListener);

		 name = (NumberPicker)this.findViewById(R.id.names);
		 name.setDisplayedValues(new String[]{"星期"});

		mHourSpinner=(NumberPicker)this.findViewById(R.id.np_hour);
    	 mHourSpinner.setMaxValue(23);
    	 mHourSpinner.setMinValue(0);
    	 mHourSpinner.setValue(mHour);
    	 mHourSpinner.setOnValueChangedListener(mOnHourChangedListener);
    	 
    	 mMinuteSpinner=(NumberPicker)this.findViewById(R.id.np_minute);
    	 mMinuteSpinner.setMaxValue(59);
    	 mMinuteSpinner.setMinValue(0);
    	 mMinuteSpinner.setValue(mMinute);
    	 mMinuteSpinner.setOnValueChangedListener(mOnMinuteChangedListener);
	}
    
    private OnValueChangeListener mOnDateChangedListener=new OnValueChangeListener()
	{
		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal)
		{
			mDate.add(Calendar.DAY_OF_MONTH, newVal - oldVal);
			updateDateControl();
			onDateTimeChanged();
		}
	};
    
    private OnValueChangeListener mOnHourChangedListener=new OnValueChangeListener()
	{
		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal)
		{
			mHour=mHourSpinner.getValue();
			onDateTimeChanged();
		}
	};
	
	  private OnValueChangeListener mOnMinuteChangedListener=new OnValueChangeListener()
		{
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal)
			{
				mMinute=mMinuteSpinner.getValue();
				onDateTimeChanged();
			}
		};
	
	private void updateDateControl()
    {
	 	Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(mDate.getTimeInMillis());
        cal.add(Calendar.DAY_OF_YEAR, -7 / 2 - 1);
        mDateSpinner.setDisplayedValues(null);
        for (int i = 0; i < 7; ++i)
        {
            cal.add(Calendar.DAY_OF_YEAR, 1);
            mDateDisplayValues[i] = (String) DateFormat.format("EEEE", cal);
        }
        mDateSpinner.setDisplayedValues(mDateDisplayValues);
        mDateSpinner.setValue(7 / 2);
        mDateSpinner.invalidate();
    }
	
	  public interface OnDateTimeChangedListener 
	  {
	        void onDateTimeChanged(DateTimePicker view, int year, int month, int day, int hour, int minute);
	  }
	
	  public void setOnDateTimeChangedListener(OnDateTimeChangedListener callback) 
	  {
	        mOnDateTimeChangedListener = callback;
	   }
	  
	  private void onDateTimeChanged() 
	  {
	        if (mOnDateTimeChangedListener != null)
	        {
	            mOnDateTimeChangedListener.onDateTimeChanged(this, mDate.get(Calendar.YEAR),
	            		mDate.get(Calendar.MONTH), mDate.get(Calendar.DAY_OF_MONTH),mHour, mMinute);
	        }
	    }
}
