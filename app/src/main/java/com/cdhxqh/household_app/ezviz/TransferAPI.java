package com.cdhxqh.household_app.ezviz;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.videogo.exception.BaseException;
import com.videogo.exception.ErrorCode;
import com.videogo.openapi.EzvizAPI;
import com.videogo.openapi.ReflectionUtils;

/*
 * 通过透传接口实现平台对应业务接口
 */
public class TransferAPI {
    public static final String RESULT = "result";
    
    public static final int SUSCCEED = 200;
    
    public static final String CODE = "code";
    
    public static final String MSG = "msg";
    
    public static boolean paserCode(String reponse) throws BaseException, JSONException {
        JSONObject jsonObject = new JSONObject(reponse);
        JSONObject result = jsonObject.getJSONObject(RESULT);
        int resultCode = result.optInt(CODE, ErrorCode.ERROR_WEB_NETWORK_EXCEPTION);
        String resultDesc = result.optString(MSG, "Resp Error:" + resultCode);
        
        if (resultCode == SUSCCEED) {
            return true;
        } else {
            throw new BaseException(resultDesc, resultCode);
        }
    }
    
	//布撤防接口
	public static boolean updateDefence(String deviceSerial, int isDefence) throws BaseException, JSONException {
		UpdateDefenceReq updateDefenceReq = new UpdateDefenceReq();
		updateDefenceReq.params.accessToken = EzvizAPI.getInstance().getAccessToken();
		updateDefenceReq.params.deviceSerial = deviceSerial;
		updateDefenceReq.params.isDefence = isDefence;
		JSONObject reqObj = ReflectionUtils.convObjectToJSON(updateDefenceReq);
		String response = EzvizAPI.getInstance().transferAPI(reqObj.toString());
		return paserCode(response);
	}
	
	//获取开通萤石云服务短信接口
    public static boolean openYSServiceSmsCode(String phone) throws BaseException, JSONException {
        OpenYSServiceSmsCodeReq openYSServiceSmsCodeReq = new OpenYSServiceSmsCodeReq();
        openYSServiceSmsCodeReq.params.phone = phone;
        JSONObject reqObj = ReflectionUtils.convObjectToJSON(openYSServiceSmsCodeReq);
        String response = EzvizAPI.getInstance().transferAPI(reqObj.toString());
        return paserCode(response);
    }
    
    //开通萤石云服务接口
    public static boolean openYSService(String phone, String smsCode) throws BaseException, JSONException {
        OpenYSServiceReq openYSServiceReq = new OpenYSServiceReq();
        openYSServiceReq.params.phone = phone;
        openYSServiceReq.params.smsCode = smsCode;
        JSONObject reqObj = ReflectionUtils.convObjectToJSON(openYSServiceReq);
        String response = EzvizAPI.getInstance().transferAPI(reqObj.toString());
        Log.i("BJJ","response:" + response);
        return paserCode(response);
    }
    
    //获取安全验证短信接口
    public static boolean getSecureSmcCode() throws BaseException, JSONException {
        GetSecureSmcCodeReq getSecureSmcCodeReq = new GetSecureSmcCodeReq();
        getSecureSmcCodeReq.params.accessToken = EzvizAPI.getInstance().getAccessToken();
        JSONObject reqObj = ReflectionUtils.convObjectToJSON(getSecureSmcCodeReq);
        String response = EzvizAPI.getInstance().transferAPI(reqObj.toString());
        return paserCode(response);
    }
    
    //安全验证接口
    public static boolean secureValidate(String smsCode) throws BaseException, JSONException {
        SecureValidateReq secureValidateReq = new SecureValidateReq();
        secureValidateReq.params.smsCode = smsCode;
        secureValidateReq.params.accessToken = EzvizAPI.getInstance().getAccessToken();
        JSONObject reqObj = ReflectionUtils.convObjectToJSON(secureValidateReq);
        String response = EzvizAPI.getInstance().transferAPI(reqObj.toString());
        return paserCode(response);
    }
}
