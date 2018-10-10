package com.adks.app.framework;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adks.app.util.BaseConfigHolder;
import com.adks.commons.util.Constants;

/**
 *
 * @Description: URl 过滤器
 * @author: lxh
 * @date: 2014-8-14 下午05:31:51
 */
public class URLFilter implements Filter {

	private String passUrl;//可以不登陆通过的url

	//private UserDao userDao;

	@Override
	public void destroy() {
		this.passUrl = null;
	}


	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		System.out.println("debufr");
		//liqt： 以 /api/ 开始的是终端请求，不进入下面的权限处理
		/*if ( request.getServletPath().startsWith("/api/") || request.getServletPath().startsWith("/apitest/")) {
			chain.doFilter(request, response);
			return;
		}
		HttpSession session=request.getSession();
		if(session==null){
			//System.out.println("session为null");
		}else{
			//System.out.println("Filter sessionId："+session.getId());
		}
		//hqt： 对直接访问 jsp 的请求，直接返回到前台 image.jsp 除外
		if ( ( session ==null || session.getAttribute("user") == null )&&
				request.getServletPath().endsWith(".jsp") &&
				!request.getServletPath().endsWith("image.jsp") &&
				!request.getServletPath().endsWith("Login.jsp") &&
				!request.getServletPath().endsWith("login.jsp")
			) {
			response.sendRedirect(request.getContextPath()+"/common/toLogin.jsp");
			return;
		}

	    if(session!= null && session.getAttribute("user")!=null){
	    	// 用户登录踢出管理 start   Add  @author:yzh @date:2015-05-06
	    	HashMap<Integer, String> userSessionMap = (HashMap<Integer, String>)request.getSession().getServletContext().getAttribute("userSessionMap");
	    	if(userSessionMap == null){
	        	userSessionMap = new HashMap<Integer, String>();
	        }
	    	String u_sessionId = userSessionMap.get(((User)session.getAttribute("user")).getId());
	    	if(u_sessionId == null || !session.getId().equals(u_sessionId)){
	    		session.removeAttribute("user");
	    		response.sendRedirect(request.getContextPath()+"/user/otherPlaceLogin.do");
				return;
	    	}else{
	    		chain.doFilter(request, response);
	    		return;
	    	}
	    	// 用户登录踢出管理 end
	    }else{

	    	//判断是否是允许通过的uri
	    	boolean ispass = false;
			if((request.getServletPath().indexOf(".do")>=0)){
				if(null != passUrl && passUrl.length()>-1){
					String[] t = request.getServletPath().split("/");
					String end = t[t.length-1];
					if(passUrl.toLowerCase().indexOf(","+end.toLowerCase())>-1){
							ispass = true;
					}
				}
				if(!ispass){
					response.sendRedirect(request.getContextPath()+"/common/toLogin.jsp");
					return;
				}
			}
			chain.doFilter(request, response);
	    }*/
	}


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		try {
			passUrl = filterConfig.getInitParameter("passUrl");
			BaseConfigHolder.intiSystemValues();
			ServletContext application = filterConfig.getServletContext();
			//application.setAttribute(Constants.CACHE_CONTROL_MAX_AGE, BaseConfigHolder.getCacheControlMaxAge());
			//application.setAttribute(Constants.CACHE_CONTROL_CONTENT, BaseConfigHolder.getCacheControlContent());
			//application.setAttribute(Constants.APP_NAME, BaseConfigHolder.getAppName());
			//application.setAttribute(Constants.APP_KEYWORS, BaseConfigHolder.getAppKeyWord());
			//application.setAttribute(Constants.APP_DESCRIPTION,BaseConfigHolder.getAppDescription());
			application.setAttribute(Constants.IMG_SERVER,BaseConfigHolder.getImgServer());

			//userDao =(UserDao)SpringContextHolder.getBean("userDaoImpl");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
