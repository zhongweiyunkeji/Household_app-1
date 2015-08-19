package com.cdhxqh.household_app.ui.actvity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.api.HttpRequestHandler;
import com.cdhxqh.household_app.app.HttpManager;
import com.cdhxqh.household_app.ui.widget.TestClass;
import com.cdhxqh.household_app.utils.MessageUtils;

/**
 * Created by Administrator on 2015/8/19.
 */
public class ActivityForgetPassword extends BaseActivity{
    /**
     *获取验证码
     */
    Button get_verification_code;

    /**
     *手机号
     */
    EditText registered_phone_number;

    /**
     * 验证码号
     */
    EditText sms_verification_code;

    /**
     * 获取验证码按钮
     */
    Button restart_passworld_id;

    /**
     * 邮箱找回
     */
    TextView TextViewMail;

    /**
     * 手机号找回
     */
    TextView TextViewPhone;

    /**
     * 手机
     */
    LinearLayout phone;

    /**
     * 邮箱
     */
    LinearLayout mail;

    private static final String TAG = "ImageButton";  //提示框

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        findViewById();
        initView();
    }

    public void findViewById(){
        registered_phone_number = (EditText) findViewById(R.id.registered_phone_number);
        get_verification_code = (Button) findViewById(R.id.get_verification_code);
        restart_passworld_id = (Button) findViewById(R.id.restart_passworld_id);
        sms_verification_code = (EditText) findViewById(R.id.sms_verification_code);
        TextViewMail = (TextView) findViewById(R.id.TextViewMail);
        TextViewPhone = (TextView) findViewById(R.id.TextViewPhone);
        phone = (LinearLayout) findViewById(R.id.phone);
        mail = (LinearLayout) findViewById(R.id.mail);
    }

    public void initView() {
        /**
         * 获取验证码
         */
        get_verification_code.setOnClickListener(codeOnClickListener);
        /**
         * 获取密码
         */
        restart_passworld_id.setOnClickListener(getPhonePassClickListener);
        //跳转至邮箱找回界面事件
        TextViewMail.setOnClickListener(backImageMailOnTouchListener);
        //跳转至手机找回界面事件
        TextViewPhone.setOnClickListener(backImagephoneClickListener);
    }

    //跳转至邮箱找回界面事件
    private View.OnClickListener backImageMailOnTouchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            phone.setVisibility(View.GONE);
            mail.setVisibility(View.VISIBLE);
        }
    };

    //手机找回标签页
    private View.OnClickListener backImagephoneClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            phone.setVisibility(View.VISIBLE);
            mail.setVisibility(View.GONE);
        }
    };

    //获取验证码
    private View.OnClickListener codeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                /*
                发送请求获取验证码
                 */
            String str = registered_phone_number.getText().toString();
            if (str == null || "".equals(str)) {
                registered_phone_number.setError(getString(R.string.please_in_phone_number_text));
                registered_phone_number.requestFocus();
            }else  if (!TestClass.isMobileNO(str)) {
                registered_phone_number.setError(getString(R.string.phone_get_err));
                registered_phone_number.requestFocus();
            }else {
//                getPhoneCode();
            }
        }
    };

    //    /**
//     * 根据手机号获取验证码
//     */
//    private void getPhoneCode () {
//        /**
//         * 加载中
//         */
//        TestClass.loading(this, getString(R.string.phone_get_err));
//
//        HttpManager.getPhoneCode(this,
//                EditText1.getText().toString(),
//                new HttpRequestHandler<Integer>() {
//                    @Override
//                    public void onSuccess(Integer data) {
//                        MessageUtils.showMiddleToast(PhoneActivity.this, "验证码发送成功");
//                        TestClass.closeLoading();
//                        TimeCountUtil timeCountUtil = new TimeCountUtil(PhoneActivity.this, 60000, 1000, info_button_id, R.drawable.phone_test_on);
//                        timeCountUtil.start();
//                    }
//
//                    @Override
//                    public void onSuccess(Integer data, int totalPages, int currentPage) {
//                        Log.i(TAG, "22222");
//                    }
//
//                    @Override
//                    public void onFailure(String error) {
//                        MessageUtils.showErrorMessage(PhoneActivity.this, error);
//                        TestClass.closeLoading();
//                    }
//                });
//    }


    //手机获取密码
    private View.OnClickListener getPhonePassClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                /*
                发送请求获取密码
                 */
            String str = registered_phone_number.getText().toString();
            String code = sms_verification_code.getText().toString();
            if (str == null || "".equals(str)) {
                registered_phone_number.setError(getString(R.string.please_in_phone_number_text));
                registered_phone_number.requestFocus();
            }else  if (!TestClass.isMobileNO(str)) {
                registered_phone_number.setError(getString(R.string.phone_get_err));
                registered_phone_number.requestFocus();
            }else if(code == null || "".equals(code)){
                sms_verification_code.setError(getString(R.string.please_in_phone_code_text));
                sms_verification_code.requestFocus();
            }else
            {
//                getPhonePass ();
            }
        }
    };

//    /**
//     * 根据手机号重置密码
//     */
//    private void getPhonePass () {
//        /**
//         * 加载中
//         */
//        TestClass.loading(this, getString(R.string.phone_get_err));
//
//        HttpManager.getPhonePass(this,
//                registered_phone_number.getText().toString(),
//                new HttpRequestHandler<Integer>() {
//                    @Override
//                    public void onSuccess(Integer data) {
//                        MessageUtils.showMiddleToast(ActivityForgetPassword.this, "密码重置成功");
//                        TestClass.closeLoading();
//                        finish();
//                    }
//
//                    @Override
//                    public void onSuccess(Integer data, int totalPages, int currentPage) {
//                        Log.i(TAG, "22222");
//                    }
//
//                    @Override
//                    public void onFailure(String error) {
//                        MessageUtils.showErrorMessage(PhoneActivity.this, error);
//                        TestClass.closeLoading();
//                    }
//                });
//    }
}


