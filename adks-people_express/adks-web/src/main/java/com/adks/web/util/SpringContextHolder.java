package com.adks.web.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 与Spring相关的工具类,该类的所有成员方法均为静态方法
 * @author lxh
 * @since 2012-08-23
 *
 */
public class SpringContextHolder implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;
	
	public static Object getBean(String name){
		return applicationContext.getBean(name);
	}
	
	public static Object getBean(Class<?> clzz){
		return getBean(clzz.getName());
	}
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextHolder.applicationContext = applicationContext;

	}

}
