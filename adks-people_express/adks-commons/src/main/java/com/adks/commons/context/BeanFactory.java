package com.adks.commons.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author panpanxu
 * BeanFactory提供getBean接口从Spring ApplicationContext里获取到已注册的Bean
 */
public class BeanFactory implements ApplicationContextAware {
	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		BeanFactory.applicationContext = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}

}
