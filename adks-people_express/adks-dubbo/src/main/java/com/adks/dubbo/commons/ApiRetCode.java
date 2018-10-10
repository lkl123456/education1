package com.adks.dubbo.commons;

/**
 *
 * @author panpanxu
 *
 * 接口返回码定义,请注释每个返回码定义.
 *
 */
public class ApiRetCode {

    /**
     * 请求成功
     */
    public static final int SUCCESS_CODE = 200;

    /**
     * 请求失败
     */
    public static final int REQUEST_FAIL = 202;


    /**
     * 无登录状态
     */
    public static final int INVALID_USER_CODE = 300;


    /**
     * 获取数据异常
     */
    public static final int FETCH_DATA_FAIL = 501;

    /**
     * 参数错误
     */
    public static final int PARAMETER_ERROR = 502;


    /**
     * 验证码错误/无效
     */

    public static final int VERIFICATION_CODE_ERROR = 503;
    
    /**
     * 数据无权限访问
     */
    public static final int DATA_PERMISSION_DENIED_ERROR = 504;
    
    /**
     * 数据不存在
     */
    public static final int NO_DATA = 505;
    
    /**
     * 超出频次限制
     */
    public static final int EXCEED_LIMIT = 506; 

}
