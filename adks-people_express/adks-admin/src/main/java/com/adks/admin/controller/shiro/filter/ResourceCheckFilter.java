package com.adks.admin.controller.shiro.filter;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.adks.admin.controller.common.CommonController;

public class ResourceCheckFilter extends AccessControlFilter {
    @Autowired
    private CommonController commonController;

    private String errorUrl;

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }

    /** 
    * <p>Title: isAccessAllowed</p> 
    * <p>Description:该方法是用来判断当前的登录用户是否拥有当前访问的路径的权限，如果没有就会执行授权 的方法 </p> 
    * @param request
    * @param response
    * @param mappedValue
    * @return
    * @throws Exception 
    * @see org.apache.shiro.web.filter.AccessControlFilter#isAccessAllowed(javax.servlet.ServletRequest, javax.servlet.ServletResponse, java.lang.Object) 
    */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        HttpServletRequest hsr = (HttpServletRequest) request;
        Subject subject = getSubject(request, response);
        Map userMap = (Map) (hsr.getSession().getAttribute("user"));
        //        String url = getPathWithinApplication(request);
        //        System.out.println("filter:" + url);
        boolean flag = null == userMap ? false : true;
        if (flag) {
            flag = null == subject.getPrincipal() ? false : true;
        }
        return flag;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse hsp = (HttpServletResponse) response;
        HttpServletRequest hReq = (HttpServletRequest) request;
        //      hReq.getRequestDispatcher(this.errorUrl).forward(hReq, hsp);

        if (this.errorUrl.equals("refuse")) {
            Subject subject = getSubject(request, response);
            subject.logout();
            hsp.sendRedirect(hReq.getContextPath() + "/userLogin/userTologin");
        }
        return false;
    }
}
