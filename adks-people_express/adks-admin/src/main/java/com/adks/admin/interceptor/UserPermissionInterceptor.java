package com.adks.admin.interceptor;

import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.adks.admin.util.Constants;
import com.adks.admin.util.Permission;

/**
 * 用户权限拦截器
 * @author panpanxu
 *
 */
public class UserPermissionInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(UserPermissionInterceptor.class);

    @SuppressWarnings("unchecked")
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
    	System.out.println("************************权限拦截！");
        if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
        	return true;
        }
    	RequirePermission permissionAnnotation = ((HandlerMethod) handler).getMethodAnnotation(RequirePermission.class);
    	if (permissionAnnotation == null) {
            return true;
        }
    	HttpSession session = request.getSession();
    	Set<Permission> userPermissions = (Set<Permission>) session.getAttribute(Constants.SESSION_USER_PERMISSIONS);
    	if (userPermissions == null) {
    		logger.info("用户权限是空。。。。。");
    		request.getRequestDispatcher("/sso/noPermission").forward(request, response);
    		return false;
    	}
    	System.out.println("userPermissions size:"+userPermissions.size());
    	
    	Permission[] requiredPermissions = permissionAnnotation.value();
        if (requiredPermissions.length == 0) {
        	return true;
        }
        
        return hasPermission(request,response,userPermissions, requiredPermissions);
    }

    /**
     * 判断当前用户是否有该权限 
     * @param userPermissions - 用户所有权限
     * @param requiredPermissions - 访问当前方法所需要的权限
     * @return
     * @throws Exception 
     * @throws ServletException 
     */
    private boolean hasPermission(HttpServletRequest request,HttpServletResponse response,Set<Permission> userPermissions, Permission[] requiredPermissions) throws  Exception{
        for(Permission permission : requiredPermissions){
            if(userPermissions.contains(permission)){
            	logger.info("用户有该权限。。。。。");
                return true;
            }
        }
        logger.info("用户没有该权限。。。。。");
		
    	request.getRequestDispatcher("/sso/noPermission").forward(request, response);
        return false;
    }

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
