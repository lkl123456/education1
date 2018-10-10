package com.adks.admin.interceptor;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.adks.dbclient.redis.RedisClient;
import com.alibaba.fastjson.JSONObject;

public class LoginAnnotationInterceptor extends HandlerInterceptorAdapter {

	final Logger logger = LoggerFactory.getLogger(LoginAnnotationInterceptor.class);;
	
    @Resource
    private RedisClient redisClient;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	
    	logger.info("****************************************拦截了 requestUri{} " ,request.getRequestURI());
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            HandlerMethod _handler = (HandlerMethod) handler;
            RequireLogin login = _handler.getMethodAnnotation(RequireLogin.class);
            // 没有声明权限,放行
            //String accessToken = CookieUtil.getCookieValue(request, CookieUtil.COOKIE_ID);
            //调用oauth api 检验token是否有效，有效，返回uname---user唯一标示
            if (null == login) {
              return true;
            }
            Map map=(Map) request.getSession().getAttribute("user");
            String username=MapUtils.getString(map, "username");
            
            /*String user=redisClient.get("user");
            String username = null;
            if(user!=null && !"".equals(user)){
            	username=user.split(";")[1];
            }*/
            logger.info(" loginannotation interceptor throug accessToken {} username {}:",username);
            
            if(StringUtils.isEmpty(username)){
            	 if (login.value() == ResultTypeEnum.page) {
 	                //跳转 到 后台 管理 平台的 登录 页面
                 	request.getRequestDispatcher("/sso/tologin").forward(request, response);
 	                     
                 } else if (login.value() == ResultTypeEnum.json) {
                     response.setCharacterEncoding("utf-8");
                     response.setContentType("text/html;charset=UTF-8");
                     OutputStream out = response.getOutputStream();
                     PrintWriter pw = new PrintWriter(new OutputStreamWriter(out, "utf-8"));
                     JSONObject retInfo=new JSONObject();
                     retInfo.put("data", "请您登录!");
                     //JSON.toJSONString(retInfo)
                     pw.println(retInfo.toJSONString());
                     pw.flush();
                     pw.close();
                 }
                 return false;
        	}
            //获取用户信息，set到request放行
            //Map<String, Object> user = userOrgWebApi.getUserInfoByUname(checkToken,null);
            //request.setAttribute("uname", checkToken);
            request.setAttribute("username", username);
            return true;
        } else {
            return true;
        }
    }
}
