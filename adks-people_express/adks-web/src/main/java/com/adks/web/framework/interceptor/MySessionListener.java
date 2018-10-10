package com.adks.web.framework.interceptor;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.api.interfaces.web.user.UserApi;
import com.adks.web.util.SpringContextHolder;



/**
 * session监听器  （监听session创建  销毁）
 * @Description:
 * @author: lxh
 * @date: 2014-10-11 下午01:17:31
 */
public class MySessionListener implements HttpSessionListener {
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		System.out.println("创建sessionId："+session.getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// session销毁  删除 用户登录信息
		HttpSession session = event.getSession();
		Adks_user user=(Adks_user)session.getAttribute("user");
		if(user!=null){
			UserApi userApi=(UserApi)this.getObjectFromApplication(session.getServletContext(), "webUserApi"); 
			userApi.deleteUserOnLine(user.getUserId());
			userApi.deleteUserToRedis(user.getUserName(),user.getUserId());
			System.out.println("销毁sessionId："+session.getId()+",用户名："+user.getUserName());
			// 用户登录踢出管理 start   Add  @author:yzh @date:2015-05-06
			HashMap<Integer, String> userSessionMap = (HashMap<Integer, String>)session.getServletContext().getAttribute("userSessionMap");
			if(userSessionMap != null && userSessionMap.get(user.getUserId()) != null){
				userSessionMap.remove(user.getUserId());
			}
			session.removeAttribute("user");
			// 用户登录踢出管理 end
		}
	}
	
	/** 
     * 通过WebApplicationContextUtils 得到Spring容器的实例。根据bean的名称返回bean的实例。 
     * @param servletContext：ServletContext上下文。 
     * @param beanName:要取得的Spring容器中Bean的名称。 
     * @return 返回Bean的实例。 
     */  
    private Object getObjectFromApplication(ServletContext servletContext,String beanName){  
        //通过WebApplicationContextUtils 得到Spring容器的实例。  
        ApplicationContext application=WebApplicationContextUtils.getWebApplicationContext(servletContext);  
        //返回Bean的实例。  
        return application.getBean(beanName);  
    }
	
}