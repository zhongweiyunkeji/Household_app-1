package com.cdhxqh.household_app.ui.widget.Photo;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AndroidRes {

	/**
	 * @param args
	 */
	public static LayoutInflater getLayoutInflater(Context context) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return inflater;

	}
	public static View getViewInflater(Context context,int resId) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return inflater.inflate(resId, null);

	}
	

	public static Resources getResource(Context context) {
		return context.getResources();
	}

	public static String getString(Context context, int resId) {
		return context.getResources().getString(resId);
	}

	public static String[] getResourceStringArray(Context context, int resId) {
		return context.getResources().getStringArray(resId);
	}

	public static View getView(Context context, int resId, ViewGroup parent) {
		return LayoutInflater.from(context).inflate(resId, parent, false);
	}

}
