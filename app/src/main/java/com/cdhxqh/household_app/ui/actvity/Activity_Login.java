package com.cdhxqh.household_app.ui.actvity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.config.Constants;

/**
 * Created by think on 2015/8/17.
 */
public class Activity_Login extends BaseActivity{
    private EditText username;
    private EditText password;
    private CheckBox isremenber;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(myshared.getBoolean(Constants.ISFIRST, true)){
            Intent intent = new Intent();
            intent.setClass(Activity_Login.this, Activity_First_login.class);
            startActivity(intent);
            this.finish();
        } else {
            findViewById();
            initView();
        }
    }

    protected void findViewById() {
        username = (EditText) findViewById(R.id.user_edittext_id);
        password = (EditText) findViewById(R.id.passworld_edittext_id);
        isremenber = (CheckBox) findViewById(R.id.isremenber_password);
        login = (Button) findViewById(R.id.login_btn_id);
    }

    protected void initView() {
        username.setText(myshared.getString(Constants.NAME_KEY, ""));
        password.setText(myshared.getString(Constants.PASS_KEY, ""));
        isremenber.setChecked(myshared.getBoolean(Constants.ISREMENBER, false));
        login.setOnClickListener(loginonclick);
    }

    private View.OnClickListener loginonclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences.Editor editor = myshared.edit();
            editor.putString(Constants.NAME_KEY, username.getText().toString());
            if(isremenber.isChecked()){
                editor.putString(Constants.PASS_KEY,password.getText().toString());
            }else{
                editor.putString(Constants.PASS_KEY, "");
            }
            editor.putBoolean(Constants.ISREMENBER,isremenber.isChecked());
            editor.commit();
            logon();
        }
    };

    private void logon(){
        Intent intent = new Intent();
        intent.setClass(this,MainActivity.class);
        startActivity(intent);
        this.finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
