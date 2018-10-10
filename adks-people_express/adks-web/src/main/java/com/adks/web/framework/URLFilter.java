package com.adks.web.framework;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.adks.commons.util.Constants;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.web.util.BaseConfigHolder;

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
		
		request.getContextPath();
		request.getCookies();
		request.getRequestedSessionId();
		request.getParameterMap();
		String url=request.getRequestURL().toString();
		if(url.indexOf(";")>=0){
			url=url.substring(0, url.indexOf(";"));
			response.sendRedirect(url);
			return;
		}
		/*Cookie cookie = new Cookie("jsessionid", "2jcligmgi6fh");
		cookie.setMaxAge(Integer.MAX_VALUE);
		response.addCookie(cookie);*/
		
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
				!request.getServletPath().endsWith("loginforward.jsp") &&
				!request.getServletPath().endsWith("login.jsp")
			) {
			response.sendRedirect(request.getContextPath()+"/common/loginforward.jsp");
			return;
		}
	    if(session!= null && session.getAttribute("user")!=null){
	    	// 用户登录踢出管理 start   Add  @author:yzh @date:2015-05-06
	    	HashMap<Integer, String> userSessionMap = (HashMap<Integer, String>)request.getSession().getServletContext().getAttribute("userSessionMap");
	    	if(userSessionMap == null){
	        	userSessionMap = new HashMap<Integer, String>();
	        }
	    	String u_sessionId = userSessionMap.get(((Adks_user)session.getAttribute("user")).getUserId());
	    	if(u_sessionId == null || !session.getId().equals(u_sessionId)){
	    		session.removeAttribute("user");
	    		response.sendRedirect(request.getContextPath()+"/common/loginforward.jsp");
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
					response.sendRedirect(request.getContextPath()+"/common/loginforward.jsp");
					return;
				}
			}
			//拦截.shtml
			if((request.getServletPath().indexOf(".shtml")>=0)){
				Pattern pattern = Pattern.compile("[0-9]*"); 
				if(null != passUrl && passUrl.length()>-1){
					String[] t = request.getServletPath().split("/");
					String end = t[t.length-1];
					for(int i=0;i<t.length;i++){
						String s=t[i];
						s=s.indexOf(".")>=0?s.substring(0, s.indexOf(".")):s;
						s=s.indexOf("_")>=0?s.substring(0, s.indexOf("_")):s;
						//System.out.println(s+":"+s.matches("[0-9]*"));
						if(!"".equals(s) && pattern.matcher(s).matches()){
							end=i==0?"":(t[i-1]+".do");
							break;
						}
					}
					if(passUrl.toLowerCase().indexOf(","+end.toLowerCase())>-1){
							ispass = true;
					}
				}
				if(!ispass){
					response.sendRedirect(request.getContextPath()+"/common/loginforward.jsp");
					return;
				}
			}
			chain.doFilter(request, response);
	    }
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
