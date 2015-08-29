package com.cdhxqh.household_app.ui.widget.Photo;




import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

public class AndroidClass {


	//返回android闹钟
	public static AlarmManager getAlarmManager(Context context)
	{
		return (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
	}
	//返回通知服务
	public static NotificationManager getNotificationManager(Context context)
	{
		return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	}
	//返回一个list对话框
	public static  AlertDialog.Builder getListDialogBuilder(Context c,String[] s,String t,
															OnClickListener o)
	{
		final String[] items =s;
		return  new AlertDialog.Builder(c)
				.setTitle(t)
//         .setView(v)
				.setItems(items,o);

	}
	//返回一个list对话框
	public static  AlertDialog.Builder getListDialogBuilder(Context context)
	{
		return  new AlertDialog.Builder(context);
	}





}
