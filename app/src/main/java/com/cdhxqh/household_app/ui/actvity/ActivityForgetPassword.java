package com.cdhxqh.household_app.ui.actvity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.api.HttpRequestHandler;
import com.cdhxqh.household_app.api.Message;
import com.cdhxqh.household_app.app.HttpManager;
import com.cdhxqh.household_app.ui.widget.TestClass;
import com.cdhxqh.household_app.ui.widget.TimeCountUtil;
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

    /**
     * 邮箱号
     */
    EditText EditTextMail;

    /**
     * 根据邮箱号获取密码
     */
    Button restart_passworld_mail;

    private static final String TAG = "ImageButton";  //提示框

    /**
     * 返回按钮*
     */
    private ImageView back_imageview_id;
    /**
     * 标题*
     */
    private TextView titleTextView;
    /**
     * 搜索*
     */
    private ImageView title_add_id;

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
        EditTextMail = (EditText) findViewById(R.id.EditTextMail);
        restart_passworld_mail = (Button) findViewById(R.id.restart_passworld_mail);

        /**
         * 标题标签相关id
         */
        back_imageview_id = (ImageView) findViewById(R.id.back_imageview_id);
        titleTextView = (TextView) findViewById(R.id.title_text_id);
        title_add_id = (ImageView) findViewById(R.id.title_add_id);
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
        /**
         * 根据邮箱号获取密码
          */
        restart_passworld_mail.setOnClickListener(getMailPassClickListener);

        back_imageview_id.setOnTouchListener(backImageViewOnTouchListener);

        //返回至登录界面事件
        back_imageview_id.setOnClickListener(backImageViewOnClickListener);

        //设置标签页显示方式
        back_imageview_id.setVisibility(View.VISIBLE);
        title_add_id.setVisibility(View.GONE);
        titleTextView.setText("忘记密码");
    }

    private View.OnTouchListener backImageViewOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setBackgroundColor(getResources().getColor(R.color.list_item_read));
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                v.setBackgroundColor(getResources().getColor(R.color.clarity));
            }
            return false;
        }
    };

    /**
     * 返回事件的监听*
     */
    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

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

    //根据邮箱号获取新密码
    private View.OnClickListener getMailPassClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = EditTextMail.getText().toString();
            if (str == null || "".equals(str)) {
                EditTextMail.setError(getString(R.string.email_code_null));
                EditTextMail.requestFocus();
            } else if(!TestClass.isMailNo(str)) {
                EditTextMail.setError(getString(R.string.email_style_err));
                EditTextMail.requestFocus();
            } else {
                getHttpUtil(1);
            }
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
                getHttpUtil(2);
            }
        }
    };

    //手机获取密码
    private View.OnClickListener getPhonePassClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                /*
                发送请求获取密码
                 */
            String a;
            String str = registered_phone_number.getText().toString();
            String code = sms_verification_code.getText().toString();
            if(code == null || "".equals(code)){
                sms_verification_code.setError(getString(R.string.please_in_phone_code_text));
                sms_verification_code.requestFocus();
            }else
            {
                getHttpUtil(3);
            }
        }
    };

    /**
     * 访问网络
     * @param i
     */
    private void getHttpUtil (int i) {
        /**
         * 加载中
         */
        TestClass.loading(this, getString(R.string.loading));

        if(i == 1) {
            /**
             * 邮箱获取密码
             */
            HttpManager.filterManager(null, this, true, handler, Message.PHONEPASS_URL, "email", EditTextMail.getText().toString());
        }else if(i == 2){
            /**
             * 手机获取验证码
             */
            HttpManager.filterManager(null, this, true, handler1, Message.PHONEPASS_URL, "mobile", registered_phone_number.getText().toString());
        }
        else if(i == 3){
            /**
             * 手机获取验证码
             */
            HttpManager.filterManager(null, this, true, handler, Message.PHONELINE_URL, "authstring", sms_verification_code.getText().toString());
        }
    }
    /**
     * 根据手机号获取验证码
     */
    HttpRequestHandler<String> handler1 = new HttpRequestHandler<String>() {
                    @Override
                    public void onSuccess(String data) {
                        MessageUtils.showMiddleToast(ActivityForgetPassword.this, data);
                        TestClass.closeLoading();
                        TimeCountUtil timeCountUtil = new TimeCountUtil(ActivityForgetPassword.this, 60000, 1000, get_verification_code, R.drawable.phone_test_on);
                        timeCountUtil.start();
                    }

                    @Override
                    public void onSuccess(String data, int totalPages, int currentPage) {
                        Log.i(TAG, "22222");
                    }

                    @Override
                    public void onFailure(String error) {
                        MessageUtils.showErrorMessage(ActivityForgetPassword.this, error);
                        TestClass.closeLoading();
                    }
                };

    HttpRequestHandler<String> handler =  new HttpRequestHandler<String>() {
        @Override
        public void onSuccess(String data) {
            MessageUtils.showMiddleToast(ActivityForgetPassword.this, data);
            TestClass.closeLoading();
            finish();
        }

        @Override
        public void onSuccess(String data, int totalPages, int currentPage) {
            Log.i(TAG, "22222");
        }

        @Override
        public void onFailure(String error) {
            MessageUtils.showErrorMessage(ActivityForgetPassword.this, error);
            TestClass.closeLoading();
        }
    };
}


