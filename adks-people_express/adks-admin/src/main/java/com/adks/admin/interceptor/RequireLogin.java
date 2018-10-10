package com.adks.admin.interceptor;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;
import java.lang.annotation.RetentionPolicy;

/**
 * 用户登录状态
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface  RequireLogin {
	
    ResultTypeEnum value()
            default ResultTypeEnum.json;
    
    
}
