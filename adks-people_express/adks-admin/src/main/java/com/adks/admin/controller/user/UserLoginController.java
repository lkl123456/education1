package com.adks.admin.controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.admin.controller.common.CommonController;
import com.adks.admin.util.Constants;
import com.adks.admin.util.PasswordUtil;
import com.adks.commons.util.PropertiesFactory;
import com.adks.commons.util.RedisConstant;
import com.adks.dubbo.api.data.org.Adks_org_config;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.api.data.user.Adks_userVo;
import com.adks.dubbo.api.interfaces.admin.org.OrgApi;
import com.adks.dubbo.api.interfaces.admin.user.UserApi;
import com.adks.dubbo.commons.ApiResponse;
import com.adks.dubbo.commons.ApiRetCode;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.internal.ws.processor.model.Model;

@Controller
@RequestMapping(value = "/userLogin")
public class UserLoginController {

    private final Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    @Autowired
    private CommonController commonController;

    @Resource
    private UserApi userApi;
    @Resource
    private OrgApi orgApi;

    @RequestMapping({ "/userTologin" })
    public String tologin() {
        logger.debug("@@@@@@跳转后登录页面");
        return "/login";
    }

    @RequestMapping({ "/noPermission" })
    public String noPermission() {
        logger.debug("没有权限页面提示");
        return "/noPermission";
    }

    @ResponseBody
    @RequestMapping(value = "/userlogin", method = RequestMethod.POST)
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsernamePasswordToken token = null;
        ApiResponse<Map> fail = null;
        try {
            String username = request.getParameter("username").trim();
            String userPassword = request.getParameter("userPassword").trim();
//            System.out.println("当前密码：" + PasswordUtil.getSHA1Password(userPassword));
            /*
             * shiro登录方式：根据用户名获取密码，密码为null非法用户；有密码检查是否用户填写的密码
             * 登录成功后无需往httpsession中存放当前用户，这样就跟web容器绑定，关联太紧密；它自己创建
             * subject对象，实现自己的session。这个跟web容器脱离，实现松耦合。
             */
            //调用shiro判断当前用户是否是系统用户
            Subject subject = SecurityUtils.getSubject(); //得到当前用户
            //shiro是将用户录入的登录名和密码（未加密）封装到token对象中
            token = new UsernamePasswordToken(username, PasswordUtil.getSHA1Password(userPassword));
//            System.out.println("为了验证登录用户而封装的token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
            //设置记住我
            token.setRememberMe(true);
            subject.login(token);
//            logger.info("用户名 【" + ((Adks_userVo) subject.getPrincipal()).getUserName() + "】密码：【"
//                    + ((Adks_userVo) subject.getPrincipal()).getUserPassword() + "】登录成功.");
            Adks_userVo adks_userVo = commonController.getUserBySession();
//            logger.info("adks_userVo" + JSON.toJSONString(adks_userVo));
            ApiResponse<Map> loginResult = userApi.loginByName(username, PasswordUtil.getSHA1Password(userPassword));
            //登录成功将用户信息放到session中
            request.getSession().setAttribute("user", loginResult.getBody());
            //将机构配置信息放入到session中
            request.getSession().setAttribute("orgConfigList", loginResult.getBody()==null?"":loginResult.getBody().get("orgConfigList"));
            //将OSS配置信息放入到session中
            String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
            request.getSession().setAttribute("ossResource", ossResource);
            doResponse(loginResult, response, request);

        }
        catch (UnknownAccountException ex) {
            // 用户名没有找到。
            logger.info("用户名为【" + token.getPrincipal() + "】不存在");
            fail = ApiResponse.fail(ApiRetCode.PARAMETER_ERROR, "用户名为【" + token.getPrincipal() + "】不存在");
            doResponse(fail, response, request);
        }
        catch (IncorrectCredentialsException ex) {
            // 用户名密码不匹配。
            logger.info("用户名为【 " + token.getPrincipal() + " 】密码错误！");
        }
        catch (LockedAccountException lae) {
            logger.info("用户名为【" + token.getPrincipal() + " 】的账户锁定，请联系管理员。");
            fail = ApiResponse.fail(ApiRetCode.PARAMETER_ERROR, "用户名为【" + token.getPrincipal() + "】的账户锁定，请联系管理员。");
            doResponse(fail, response, request);
        }
        catch (DisabledAccountException dax) {
            logger.info("用户名为:【" + token.getHost() + "】用户已经被禁用.");
            fail = ApiResponse.fail(ApiRetCode.PARAMETER_ERROR, "用户已经被禁用");
            doResponse(fail, response, request);
        }
        catch (ExcessiveAttemptsException eae) {
            logger.info("用户名为:【" + token.getHost() + "】的用户登录次数过多，有暴力破解的嫌疑.");
        }
        catch (ExpiredCredentialsException eca) {
            logger.info("用户名为:【" + token.getHost() + "】用户凭证过期.");
        }
        catch (AuthenticationException e) {
            // 其他的登录错误
            e.printStackTrace();
        }
        catch (Exception e) {
            logger.error("login failed. username:{}", request.getParameter("abc"), e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        finally {
            fail = ApiResponse.fail(ApiRetCode.PARAMETER_ERROR, "用户名或密码错误");
            doResponse(fail, response, request);
        }
    }

    private void doResponse(ApiResponse apiResponse, HttpServletResponse response, HttpServletRequest request)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String message = "";
        if (null != apiResponse) {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.writeValueAsString(apiResponse);
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
