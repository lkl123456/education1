package com.adks.admin.interceptor;

/** 
 * @ClassName: ResultTypeEnum 
 * @Description: TODO(返回结果的类型) 
 * @author harry 
 * @date 2017年1月5日 下午5:09:54 
 *  
*/
public enum ResultTypeEnum {
	 //页面dirct
    page,
    //response write json
    json,
    //验证码校验
    image_code,
    //短信码校验
    phone_code
}