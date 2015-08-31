/* 
 * @ProjectName ezviz-openapi-android-demo
 * @Copyright HangZhou Hikvision System Technology Co.,Ltd. All Right Reserved
 * 
 * @FileName OpenYSServiceSmsCodeReq.java
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

import com.videogo.openapi.annotation.Serializable;

/**
 * 获取开通萤石云服务短信接口
 * @author chenxingyf1
 * @data 2015-7-14
 */
public class OpenYSServiceSmsCodeReq {
    @Serializable(name = "method")
    public String method = "msg/smsCode/openYSService";//方法名
    
    @Serializable(name = "params")
    public Params params = new Params();
    
    @Serializable
    public class Params {
        @Serializable(name = "phone")
        public String phone;
    }
}
