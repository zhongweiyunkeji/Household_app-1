package com.cdhxqh.household_app.ui.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.cdhxqh.household_app.R;

/**
 * Created by hexian on 2015/8/19.
 */
public class CustomDialog extends Dialog {

    int dialogResult;

    public CustomDialog(Activity context) {
       super(context);
       setOwnerActivity(context);
        // onCreate();
   }

    @Override
    public void onCreate(Bundle bundle) {
        setContentView(R.layout.dialog_layout);
        findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                dismiss(); // 关闭对话框
            }
        });
        findViewById(R.id.okBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                dismiss(); // 关闭对话框
            }
        });
    }

}
