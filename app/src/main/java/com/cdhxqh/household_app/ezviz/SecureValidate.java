/* 
 * @ProjectName ezviz-openapi-android-demo
 * @Copyright HangZhou Hikvision System Technology Co.,Ltd. All Right Reserved
 * 
 * @FileName SecureValidate.java
 * @Description 这里对文件进行描述
 * 
 * @author chenxingyf1
 * @data 2015-7-14
 * 
 * @note 这里写本文件的详细功能描述和注释
 * @note 历史记录
 * 
 * @warning 这里写本文件的相关警告
 */
package com.cdhxqh.household_app.ezviz;

import org.json.JSONException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cdhxqh.household_app.R;
import com.videogo.exception.BaseException;
import com.videogo.exception.ErrorCode;
import com.videogo.openapi.EzvizAPI;
import com.videogo.util.ConnectionDetector;
import com.videogo.util.Utils;

/**
 * 在此对类做相应的描述
 * @author chenxingyf1
 * @data 2015-7-14
 */
public class SecureValidate {
    public interface SecureValidateListener {
        void onSecureValidate(int result);
    }
    
    public static void secureValidateDialog(final Context context, final SecureValidateListener l) {
        LayoutInflater mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup smsVerifyView = (ViewGroup) mLayoutInflater.inflate(R.layout.open_ysservice_dialog, null, true);
        
        final EditText phoneEt = (EditText) smsVerifyView.findViewById(R.id.phone_et);
        phoneEt.setVisibility(View.GONE);
        final EditText smsCodeEt = (EditText) smsVerifyView.findViewById(R.id.sms_code_et);
        final Button getSmsBtn = (Button) smsVerifyView.findViewById(R.id.get_sms_code_btn);
        getSmsBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new GetSmsCodeTask(context).execute();
            }
            
        });

        new  AlertDialog.Builder(context)  
        .setTitle(R.string.secure_validate)   
        .setIcon(android.R.drawable.ic_dialog_info)   
        .setView(smsVerifyView)
        .setPositiveButton(R.string.submit_secure_validate, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                new SecureValidateTask(context, l,  
                        smsCodeEt.getText().toString()).execute();
            }
            
        })   
        .setNegativeButton(R.string.cancel, null)
        .show();  
    }
    
    private static class GetSmsCodeTask extends AsyncTask<Void, Void, Boolean> {
        private Context mContext;
        private Dialog mWaitDialog;
        private int mErrorCode = 0;
        
        public GetSmsCodeTask(Context context) {
            mContext = context;
        }
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mWaitDialog = new WaitDialog(mContext, android.R.style.Theme_Translucent_NoTitleBar);
            mWaitDialog.setCancelable(false);
            mWaitDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if (!ConnectionDetector.isNetworkAvailable(mContext)) {
                mErrorCode = ErrorCode.ERROR_WEB_NET_EXCEPTION;
                return false;
            }

            try {
                return TransferAPI.getSecureSmcCode();
            } catch (BaseException e) {
                mErrorCode = e.getErrorCode();
                return false;
            } catch (JSONException e) {
                mErrorCode = ErrorCode.ERROR_WEB_NET_EXCEPTION;
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mWaitDialog.dismiss();

            if (mErrorCode != 0)
                onError(mErrorCode);
        }

        protected void onError(int errorCode) {
            switch (errorCode) {
                case ErrorCode.ERROR_WEB_SESSION_ERROR:
                case ErrorCode.ERROR_WEB_SESSION_EXPIRE:
                case ErrorCode.ERROR_WEB_HARDWARE_SIGNATURE_ERROR:
                    EzvizAPI.getInstance().gotoLoginPage();
                    break;

                default:
                    Utils.showToast(mContext, R.string.get_sms_code_fail, mErrorCode);
                    break;
            }
        }
    }
    
    private static class SecureValidateTask extends AsyncTask<Void, Void, Boolean> {
        private Context mContext;
        private SecureValidateListener mSecureValidateListener;
        private String mSmsCode;
        private String mPhone;
        private Dialog mWaitDialog;
        private int mErrorCode = 0;
        
        public SecureValidateTask(Context context, SecureValidateListener l, String smsCode) {
            mContext = context;
            mSecureValidateListener = l;
            mSmsCode = smsCode;
        }
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mWaitDialog = new WaitDialog(mContext, android.R.style.Theme_Translucent_NoTitleBar);
            mWaitDialog.setCancelable(false);
            mWaitDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if (!ConnectionDetector.isNetworkAvailable(mContext)) {
                mErrorCode = ErrorCode.ERROR_WEB_NET_EXCEPTION;
                return false;
            }

            try {
                return TransferAPI.secureValidate(mSmsCode);
            } catch (BaseException e) {
                mErrorCode = e.getErrorCode();
                return false;
            } catch (JSONException e) {
                mErrorCode = ErrorCode.ERROR_WEB_NET_EXCEPTION;
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mWaitDialog.dismiss();
            
            if (mErrorCode == 0) {
                Utils.showToast(mContext, R.string.secure_validate_success);
                mSecureValidateListener.onSecureValidate(mErrorCode);
            } else {
                onError(mErrorCode);
            }
        }

        protected void onError(int errorCode) {
            switch (errorCode) {
                case ErrorCode.ERROR_WEB_SESSION_ERROR:
                case ErrorCode.ERROR_WEB_SESSION_EXPIRE:
                case ErrorCode.ERROR_WEB_HARDWARE_SIGNATURE_ERROR:
                    EzvizAPI.getInstance().gotoLoginPage();
                    break;
                default:
                    Utils.showToast(mContext, R.string.secure_validatee_fail, mErrorCode);
                    break;
            }
        }
    }
}
