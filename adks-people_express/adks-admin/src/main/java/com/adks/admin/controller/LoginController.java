package com.adks.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.admin.util.Constants;
import com.adks.admin.util.CookieUtil;
import com.adks.admin.util.PasswordUtil;
import com.adks.dubbo.commons.ApiResponse;

/** 
 * @ClassName: LoginController 
 * @Description: TODO() 
 * @author harry 
 * @date 2017年1月4日 下午1:47:42 
 *  
*/
@Controller
@RequestMapping(value = "/sso")
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    //	@Resource
    //	private OauthApi oauthApi;

    @RequestMapping({ "/tologin" })
    public String tologin() {
        logger.debug("@@@@@@跳转后登录页面");
        return "/login";
    }

    @RequestMapping({ "/noPermission" })
    public String noPermission() {
        logger.debug("没有权限页面提示");
        return "/noPermission";
    }


    private void doResponse(ApiResponse apiResponse, HttpServletResponse response, HttpServletRequest request)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String message = "";
        if (null != apiResponse) {
            /*	ObjectMapper mapper = new ObjectMapper();
            	message = mapper.writeValueAsString(apiResponse);*/
        }
        if (apiResponse.isSuccess()) {
            HttpSession session = request.getSession();
            session.setAttribute(Constants.SESSION_USER_PERMISSIONS, "logined");

        }
        if (StringUtils.isEmpty(message)) {
            return;
        }

        //response.getOutputStream().write("用户名或密码错误".getBytes("UTF-8"));
        PrintWriter pw = response.getWriter();
        try {
            pw.print(message);
            pw.flush();
        }
        finally {
            pw.close();
        }
    }


}
