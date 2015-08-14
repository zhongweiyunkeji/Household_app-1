package com.cdhxqh.household_app.ui.actvity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.ui.action.impl.OnItemClickCallBackImpl;
import com.cdhxqh.household_app.ui.widget.SwitchButton;

import java.util.regex.Pattern;

/**
 * Created by hexian on 2015/8/13.
 */

public class Activity_Registry_User extends BaseActivity {

    ImageView backImg;     // 退回按钮
    ImageView settingImg; // 设置按钮

    LinearLayout layout1; //  填写手机号部分
    LinearLayout layout2; //  设置密码部分
    LinearLayout layout3; //  短信验证部分

    Button phoneBtn;      // 填写手机号下一步按钮
    Button pwdBtn;        // 输入密码下一步按钮
    Button sendMsgBtn;   // 发送验证码按钮
    Button validBtn;     // 注册用户按钮

    EditText phoneTextView; // 手机号输入框

    TextView writePhone;       // 箭头框
    TextView writeWwd;         // 箭头框
    TextView writeSendmsg;    // 箭头框
    TextView userTypeText;    // 选择用户

    EditText pewTextView;     // 密码输入框的值

    SwitchButton switchButton;// 开关按钮

    public static final int ACTIVITY_REGISTRY_REQUEST1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById();

        initView();
    }

    public void findViewById() {
        backImg = (ImageView)findViewById(R.id.back_imageview_id);
        settingImg = (ImageView)findViewById(R.id.title_add_id);

        layout1 = (LinearLayout)findViewById(R.id.reg_layou_1);
        layout2 = (LinearLayout)findViewById(R.id.reg_layou_2);
        layout3 = (LinearLayout)findViewById(R.id.reg_layou_3);

        writePhone =       (TextView)findViewById(R.id.write_phone);
        writeWwd =         (TextView)findViewById(R.id.write_pwd);
        writeSendmsg =  (TextView)findViewById(R.id.write_sendmsg);

        phoneBtn = (Button)findViewById(R.id.reg_phone_next_btn);
        pwdBtn = (Button)findViewById(R.id.reg_pwd_btn);
        sendMsgBtn = (Button)findViewById(R.id.reg_senmsg_btn);
        validBtn = (Button)findViewById(R.id.reg_msg_btn);

        phoneTextView = (EditText)findViewById(R.id.reg_phone_text);

        userTypeText = (EditText)findViewById(R.id.reg_repwd_input);

        switchButton = (SwitchButton)findViewById(R.id.switchButton);

        pewTextView = (EditText)findViewById(R.id.reg_pwd_input);

    }

    public void initView(){
        settingImg.setVisibility(View.GONE);
        layout1.setVisibility(View.VISIBLE);
        layout2.setVisibility(View.GONE);
        layout3.setVisibility(View.GONE);

        phoneBtn.setOnClickListener(new View.OnClickListener() {  // 填写手机号下一步按钮事件
            @Override
            public void onClick(View v) {
                String phone = phoneTextView.getText().toString();
                boolean flag = Activity_Registry_User.this.isMobileNO(phone);
                if (flag) {
                    if (phone.length() == 11) {
                        showLayout(Activity_Registry_User.this.layout2); // 显示regLayou2
                        setTextViewBackground(writeWwd);              // 设置背景颜色
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "请输入11位的手机号", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                } else {
                    String msg = "手机号格式不正确";
                    if (phone.length() == 0) {
                        msg = "请输入手机号";
                    }
                    Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        pwdBtn.setOnClickListener(new View.OnClickListener() {  // 输入密码下一步按钮事件
            @Override
            public void onClick(View v) {
                showLayout(layout3);
                setTextViewBackground(writeSendmsg);         // 设置背景颜色
            }
        });

        sendMsgBtn.setOnClickListener(new View.OnClickListener() { // 发送验证码按钮
            @Override
            public void onClick(View v) {

            }
        });

        validBtn.setOnClickListener(new View.OnClickListener() { // 注册用户按钮事件
            @Override
            public void onClick(View v) {

            }
        });

        // 退回按钮事件
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visible_layout1 = layout1.getVisibility();
                int visible_layout2 = layout2.getVisibility();
                int visible_layout3 = layout3.getVisibility();
                if (Activity_Registry_User.this != null) {
                    // 隐藏软键盘
                    InputMethodManager inputmanger = (InputMethodManager) Activity_Registry_User.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputmanger.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                if (View.VISIBLE == visible_layout1) {
                    finish();
                } else
                if (View.VISIBLE == visible_layout2) {
                    showLayout(Activity_Registry_User.this.layout1); // 显示regLayou1
                    setTextViewBackground(writePhone);           // 设置背景颜色
                } else
                if (View.VISIBLE == visible_layout3) {
                    showLayout(Activity_Registry_User.this.layout2); // 显示regLayou2
                    setTextViewBackground(writeWwd);              // 设置背景颜色
                }

            }
        });

        userTypeText.setOnClickListener(new View.OnClickListener() {// 选择用户Activity
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Registry_User.this, Activity_User_Type.class);
                Bundle bundle = new Bundle();
                String text = userTypeText.getText().toString();
                if(!"".equals(text)){
                    intent.putExtras(bundle);
                    bundle.putString("text", text);
                }
                try {
                    startActivityForResult(intent, ACTIVITY_REGISTRY_REQUEST1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        switchButton.setOnChangeListener(new SwitchButton.OnChangeListener() {  // 注册开关按钮事件
            @Override
            public void onChange(SwitchButton sb, boolean state) {
                if (state) {
                    //设置密码可见
                    pewTextView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置密码不可见
                    pewTextView.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        validBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Registry_User.this, Activity_Registry_Result.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    /**
     * 验证手机号格式
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles){
        mobiles = (mobiles==null? "" : mobiles);
        return Pattern.compile("^[1][3,4,5,8][0-9]{9}$").matcher(mobiles).matches();
    }

    /**
     * 隐藏：填写手机号、设置密码和短信验证Layout
     */
    public void showLayout(ViewGroup viewGroup){
        // 隐藏控件
        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.GONE);
        layout3.setVisibility(View.GONE);

        if(viewGroup!=null){
            viewGroup.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 传入参数为需要更改Label
     * 更改填写手机号、设置密码和短信验证的TextView背景颜色
     */
    public void setTextViewBackground(TextView textView){
        writePhone.setTextColor(0xFF4B4F52);
        writeWwd.setTextColor(0xFF4B4F52);
        writeSendmsg.setTextColor(0xFF4B4F52);
        textView.setTextColor(0xFF03A5F9);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((OnItemClickCallBackImpl.ACTIVITY_REGISTRY_RESPONSE1 == resultCode) && (Activity_Registry_User.ACTIVITY_REGISTRY_REQUEST1 == requestCode)){
            Bundle bundle = data.getExtras();
            String text = bundle.getString("text");
            userTypeText.setText(text);
            Log.i("", "----------------------->"+text);
        }
    }
}
