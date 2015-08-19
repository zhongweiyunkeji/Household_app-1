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
     *��ȡ��֤��
     */
    Button get_verification_code;

    /**
     *�ֻ���
     */
    EditText registered_phone_number;

    /**
     * ��֤���
     */
    EditText sms_verification_code;

    /**
     * ��ȡ��֤�밴ť
     */
    Button restart_passworld_id;

    /**
     * �����һ�
     */
    TextView TextViewMail;

    /**
     * �ֻ����һ�
     */
    TextView TextViewPhone;

    /**
     * �ֻ�
     */
    LinearLayout phone;

    /**
     * ����
     */
    LinearLayout mail;

    private static final String TAG = "ImageButton";  //��ʾ��

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
         * ��ȡ��֤��
         */
        get_verification_code.setOnClickListener(codeOnClickListener);
        /**
         * ��ȡ����
         */
        restart_passworld_id.setOnClickListener(getPhonePassClickListener);
        //��ת�������һؽ����¼�
        TextViewMail.setOnClickListener(backImageMailOnTouchListener);
        //��ת���ֻ��һؽ����¼�
        TextViewPhone.setOnClickListener(backImagephoneClickListener);
    }

    //��ת�������һؽ����¼�
    private View.OnClickListener backImageMailOnTouchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            phone.setVisibility(View.GONE);
            mail.setVisibility(View.VISIBLE);
        }
    };

    //�ֻ��һر�ǩҳ
    private View.OnClickListener backImagephoneClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            phone.setVisibility(View.VISIBLE);
            mail.setVisibility(View.GONE);
        }
    };

    //��ȡ��֤��
    private View.OnClickListener codeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                /*
                ���������ȡ��֤��
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
//     * �����ֻ��Ż�ȡ��֤��
//     */
//    private void getPhoneCode () {
//        /**
//         * ������
//         */
//        TestClass.loading(this, getString(R.string.phone_get_err));
//
//        HttpManager.getPhoneCode(this,
//                EditText1.getText().toString(),
//                new HttpRequestHandler<Integer>() {
//                    @Override
//                    public void onSuccess(Integer data) {
//                        MessageUtils.showMiddleToast(PhoneActivity.this, "��֤�뷢�ͳɹ�");
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


    //�ֻ���ȡ����
    private View.OnClickListener getPhonePassClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                /*
                ���������ȡ����
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
//     * �����ֻ�����������
//     */
//    private void getPhonePass () {
//        /**
//         * ������
//         */
//        TestClass.loading(this, getString(R.string.phone_get_err));
//
//        HttpManager.getPhonePass(this,
//                registered_phone_number.getText().toString(),
//                new HttpRequestHandler<Integer>() {
//                    @Override
//                    public void onSuccess(Integer data) {
//                        MessageUtils.showMiddleToast(ActivityForgetPassword.this, "�������óɹ�");
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


