package com.cdhxqh.household_app.ui.actvity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.api.HttpRequestHandler;
import com.cdhxqh.household_app.api.Message;
import com.cdhxqh.household_app.app.HttpManager;
import com.cdhxqh.household_app.config.Constants;
import com.cdhxqh.household_app.ui.widget.SwitchButton;
import com.cdhxqh.household_app.ui.widget.TestClass;
import com.cdhxqh.household_app.utils.MessageUtils;

/**
 * 登录界面
 */
public class Activity_Login extends BaseActivity{
    private static final String TAG="Activity_Login";

    /**用户名**/
    private EditText username;
    /**密码**/
    private EditText password;
    /**记住密码**/
    private CheckBox isremenber;
    /**登录按钮**/
    private Button login;

    /**忘记密码**/
    private TextView TextViewPassWord;
    /**注册按钮**/
    Button regBtn;
    boolean close = true;
    SharedPreferences.Editor editor;
    SwitchButton switchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getData();
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


    public void getData() {
        close =  getIntent().getBooleanExtra("close", true);
    }

    protected void findViewById() {
        username = (EditText) findViewById(R.id.user_edittext_id);
        password = (EditText) findViewById(R.id.passworld_edittext_id);
        isremenber = (CheckBox) findViewById(R.id.isremenber_password);
        login = (Button) findViewById(R.id.login_btn_id);
        TextViewPassWord = (TextView) findViewById(R.id.TextViewPassWord);

        regBtn = (Button)findViewById(R.id.registered_btn_id);
        switchButton = (SwitchButton) findViewById(R.id.wiperSwitch1);
    }

    protected void initView() {
        editor = myshared.edit();
        if(close == false) {
            editor.putBoolean(Constants.ISREMENBER, false);
            editor.putString(Constants.PASS_KEY, "");
            editor.commit();
            init();
        }
        username.setText(myshared.getString(Constants.NAME_KEY, ""));
        password.setText(myshared.getString(Constants.PASS_KEY, ""));
        isremenber.setChecked(myshared.getBoolean(Constants.ISREMENBER, false));
        login.setOnClickListener(loginonclick);
        TextViewPassWord.setOnClickListener(passWordOnClickListener);
        switchButton.setOnChangeListener(switchOnClickListener);


        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Login.this, Activity_Registry_User.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private View.OnClickListener loginonclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editor.putString(Constants.NAME_KEY, username.getText().toString());
            if(isremenber.isChecked()){
                editor.putString(Constants.PASS_KEY, password.getText().toString());
            }else{
                editor.putString(Constants.PASS_KEY, "");
            }
            editor.putBoolean(Constants.ISREMENBER, isremenber.isChecked());
            editor.commit();
            if(username.getText().toString() == null || username.getText().toString().equals("")) {
                username.setError(getString(R.string.username_null));
                username.requestFocus();
            }else if(password.getText().toString() == null || password.getText().toString().equals("")){
                password.setError(getString(R.string.password_null));
                password.requestFocus();
            }else {
                getHttpUtil();
//                Intent intent = new Intent();
//                intent.setClass(Activity_Login.this,MainActivity.class);
//                startActivity(intent);
            }
        }
    };

    private SwitchButton.OnChangeListener switchOnClickListener = new SwitchButton.OnChangeListener() {
        @Override
        public void onChange(SwitchButton sb, boolean state) {
            // TODO Auto-generated method stub
            Log.d("switchButton", state ? "验票员" : "用户");
            Toast.makeText(Activity_Login.this, state ? "密码可见" : "密不可见码", Toast.LENGTH_SHORT).show();
            if(state) {
                password.setInputType(View.GONE);
            }else {
                password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        }
    };

    /**
     * 访问网络
     * @param
     */
    private void getHttpUtil () {
        /**
         * 加载中
         */
        TestClass.loading(this, getString(R.string.login_hint_text));
        HttpManager.filterManager(null, this, true, handler, Message.LOGIN_URL, "loginName", "password", username.getText().toString(), password.getText().toString());
    }

    /**
     * 登录
     */
    HttpRequestHandler<Object[]> handler = new HttpRequestHandler<Object[]>() {
        @Override
        public void onSuccess(Object[] data) {
            Toast.makeText(Activity_Login.this,"登录成功",Toast.LENGTH_SHORT).show();
            editor.putLong(Constants.SESSIONID, (Long)data[0]);
            editor.putString(Constants.SESSIONIDTRUE, (String) data[1]);
            editor.commit();
            TestClass.closeLoading();
            Intent intent = new Intent();
            intent.setClass(Activity_Login.this,MainActivity.class);
            startActivity(intent);
            Activity_Login.this.finish();
        }

        @Override
        public void onSuccess(Object[] data, int totalPages, int currentPage) {

        }

        @Override
        public void onFailure(String error) {
            MessageUtils.showErrorMessage(Activity_Login.this, error);
            TestClass.closeLoading();
        }
    };

    private View.OnClickListener passWordOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(Activity_Login.this,ActivityForgetPassword.class);
            startActivity(intent);
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
