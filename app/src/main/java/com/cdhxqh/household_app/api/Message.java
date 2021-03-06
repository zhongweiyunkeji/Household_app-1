package com.cdhxqh.household_app.api;

import android.os.Environment;

import com.cdhxqh.household_app.config.Constants;


public class Message {

    /**
     ******************************************* 接口参数设置信息开始 ******************************************
     * 这里编写与服务器交互需要的url
     */
    //远程服务地址
    public static final String BASE_URL = Constants.BASE_URL;

    //远程服务器地址

//    public static final String BASE_URL = "http://192.168.1.99:8080/security/";

    /**登录用户接口**/
    public static final String LOGIN_URL = BASE_URL+"user/login";

    /**邮箱获取密码接口**/
    public static final String PHONEPASS_URL = BASE_URL+"user/reset";

    /**验证码密码接口**/
    public static final String PHONELINE_URL = BASE_URL+"user/doReset";

    /**用户注册验证码 **/

    public static final String REG_CODE_URL = BASE_URL+"ecsusers/doCheck";

    /**用户注册**/

    public static final String REG_URL = BASE_URL+"ecsusers/doCheck";

    /**报警**/

    public static final String ALARM = BASE_URL+"alarm/pagelist";


    /**获取景点门票**/
    public static final String Goods_URL = BASE_URL+"ecscategory/goodspage";

    // 订单列表
    public static final String OTDER_LIST_URL = BASE_URL+"ecsorder/list";

    // 取消订单号
    public static final String OEDER_CANCEL_URL = BASE_URL+"ecsorder/cancel";

    // 删除订单号
    public static final String OEDER_DELETE_URL = BASE_URL+"ecsorder/change";

    /**景区列表**/
    public static final String TICKETS_URL = BASE_URL+"ecsbrand/brandlist";

    /**根据景区ID获取景点列表接口**/
    public static final String ATTRACTIONS_URL = BASE_URL+"ecsbrand/articlelist";



    /**根据景区ID获取门票列表**/
    public static  final String GOODSLIST_URL = BASE_URL+"ecscategory/goodslist";

    public static final String CATEGORY_URL = BASE_URL+"ecscategory/categorylist";

    public static final String CREATORDER_URL = BASE_URL+"ecsorder/create";

    public static final String STOCK_URL = BASE_URL+"ecsorder/justInv";

    public static final String UPDATE_STOCK_URL = BASE_URL+"ecsorder/change";

	/**验票员登录接口**/
	public static final String TICKET_URL = BASE_URL+"ecsusers/adminlogin";

	/**  验票地址  **/
	public static final String CHECK_TICKET_URL = BASE_URL+"checkticket/check";

    /**
     ******************************************* 接口参数设置信息结束 ******************************************
     *
     */



    /**
     ******************************************* 参数设置信息开始 ******************************************
     * 系统存放路径定义
     */




    // 保存参数文件夹名称
    public static final String SHARED_PREFERENCE_NAME = "itau_jingdong_prefs";

    // SDCard路径
    public static final String SD_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath();

    // 图片存储路径
    public static final String BASE_PATH = SD_PATH + "/tracel/images/";

    // 缓存图片路径
    public static final String BASE_IMAGE_CACHE = BASE_PATH + "cache/images/";


    // 手机IMEI号码
    public static String IMEI = "";

    // 手机号码
    public static String TEL = "";

    // 屏幕高度
    public static int SCREEN_HEIGHT = 800;

    // 屏幕宽度
    public static int SCREEN_WIDTH = 480;

    // 屏幕密度
    public static float SCREEN_DENSITY = 1.5f;

    // 分享成功
    public static final int SHARE_SUCCESS = 0X1000;

    // 分享取消
    public static final int SHARE_CANCEL = 0X2000;

    // 分享失败
    public static final int SHARE_ERROR = 0X3000;

    // 开始执行
    public static final int EXECUTE_LOADING = 0X4000;

    // 正在执行
    public static final int EXECUTE_SUCCESS = 0X5000;

    // 执行完成
    public static final int EXECUTE_FAILED = 0X6000;

    // 加载数据成功
    public static final int LOAD_DATA_SUCCESS = 0X7000;

    // 加载数据失败
    public static final int LOAD_DATA_ERROR = 0X8000;

    // 动态加载数据
    public static final int SET_DATA = 0X9000;

    // 未登录
    public static final int NONE_LOGIN = 0X10000;

    /**
     ******************************************* 参数设置信息结束 ******************************************
     */


    /**
     * ********************状态参数信息设置开始*************************************
     */

    //登录成功
    public static final String SUCCESS_LOGIN = "SECURITY-USER-S-101";

    // 登录超时
    public static final String LOGIN_TIMEOUT = "ZWTICKET-GLOBAL-E-4";

    //邮箱发送成功
    public static final String SUCCESS_EMAIL = "SECURITY-GLOBAL-S-6";

    //邮箱未注册
    public static final String FAILURE_EMAIL = "ZWTICKET-GLOBAL-E-7";

    //短信发送成功
    public static final String SUCCESS_LINE = "ZWTICKET-GLOBAL-S-10";

    //短信发送成功
    public static final String SUCCESS_PHONE_CODE = "ZWTICKET-GLOBAL-E-11";

    //验证码验证成功
    public static final String SUCCESS_LINE_PASS = "SECURITY-GLOBAL-S-10";

    //验证码验证失败
    public static final String FAILURE_LINE_PASS = "ZWTICKET-GLOBAL-E-13";

    //验证码验证失效
    public static final String RUNTIME_LINE_PASS = "ZWTICKET-GLOBAL-S-15";

    //密码重置成功
    public static final String SUCCESSE_LINE_PASS = "SECURITY-GLOBAL-S-16";

    //密码重置失败
    public static final String PHONE_LINE_PASS = "ZWTICKET-GLOBAL-E-17";

    //请求成功
    public static final String REQUEST_SUCCESS = "ZWTICKET-GLOBAL-S-0";

    //库存充足
    public static final String STOCK_SUCCESS = "ZWTICKET-ORDER-S-303";

    //库存不足
    public static final String SESSIONID_FAILE = "SECURITY-GLOBAL-E-4";

    //订单账号为空
    public static final String STOCK_FAILE_NULL = "ZWTICKET-ORDER-E-304";

    //订单账号为空
    public static final String STOCK_FAILE_STYLE = "ZWTICKET-ORDER-E-305";

    //支付失败
    public static final String STOCK_FAILE_PAY = "ZWTICKET-ORDER-E-306";




    /**
     * ********************状态参数信息设置结束*************************************
     */


    /**缓存参数设置开始**/
    public static final String USER_CACHE = "userId";

    /**缓存参数设置结束**/

    /**返回状态标识设置开始**/

    public static final int STATUS_CODE_1000 =1000;

    /**返回状态标识设置结束**/

    public static final String SCENICE_SEARCH_URL = BASE_URL + "ecsbrand/brandlist";

    public static final String ATTRACTIONS_SEARCH_URL = BASE_URL + "ecsbrand/articlelist";

    public static final String REG_CHECK_CODE = BASE_URL + "user/check";

}
