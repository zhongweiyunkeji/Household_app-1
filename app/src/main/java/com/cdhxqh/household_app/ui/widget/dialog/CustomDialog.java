package com.cdhxqh.household_app.ui.widget.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.utils.filedownload.UpdateManager;

import java.lang.reflect.Method;

/**
 * Created by hexian on 2015/8/19.
 */
public class CustomDialog extends Dialog {

    Activity mContext;
    public TextView softVerson;
    private UpdateManager manager;

    public CustomDialog(Activity context) {
       super(context);
        this.mContext = context;
       setOwnerActivity(context);
   }

    @Override
    public void onCreate(Bundle bundle) {
        setContentView(R.layout.dialog_layout);
        softVerson = (TextView)findViewById(R.id.softVerson);
        findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) { // 取消
                dismiss(); // 关闭对话框
                try {
                    if (manager != null) {
                        manager.startActivity();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.okBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {// 现在更新
                dismiss(); // 关闭对话框
                try {
                    if (manager != null) {
                        manager.showDownloadDialog();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setCallback(UpdateManager manager){
        this.manager= manager;
    }


}
