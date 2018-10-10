package com.adks.web.controller.user;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adks.commons.util.ComUtil;
import com.adks.commons.util.DateTimeUtil;
import com.adks.commons.util.PasswordUtil;
import com.adks.commons.util.RedisConstant;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.data.org.Adks_org_config;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.api.data.user.Adks_user_online;
import com.adks.dubbo.api.interfaces.web.course.CourseUserApi;
import com.adks.dubbo.api.interfaces.web.org.OrgApi;
import com.adks.dubbo.api.interfaces.web.user.UserApi;
import com.adks.web.util.BaseConfigHolder;
import com.adks.web.util.DesEncrypt;
import com.adks.web.util.ipSeeker.IpAddressAndIspUtil;
import com.alibaba.fastjson.JSONObject;

/** 
* @ClassName: LoginController 
* @Description: (前台用户登录controller) 
* @author 王文腾 
* @date 2017年4月17日 下午3:02:52 
*  
*/
@Controller
@RequestMapping(value = "/user")
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserApi userApi;

    @Autowired
    private CourseUserApi courseUserApi;
    @Autowired
    private OrgApi orgApi;

    private DecimalFormat df = new DecimalFormat("######0.00");

    @RequestMapping(value = "/toUserLogin.do", method = RequestMethod.POST)
    public String login(String loginFrom, String userName, String passWord, String randNum, String sRandNum,Integer orgId,
            HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model,RedirectAttributes  ra) {
        String message = "";
        String viewName = "/login";
        DesEncrypt des = new DesEncrypt();
        String key = BaseConfigHolder.getKey();
        userName = des.strDec(userName, key, key, key);
        passWord = des.strDec(passWord, key, key, key);
        try {

            if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWord)) {
                message = "您输入的用户名或密码有误.";
                model.addAttribute("message", message);
                return "/login";
            }
            //如果用户名\密码\验证码都不为空
            if (StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(passWord) && StringUtils.isNotEmpty(sRandNum)
                    && StringUtils.isNotEmpty(randNum)) {
                //先判断生成的验证码和输入的验证码是否一致
                if (StringUtils.equals(sRandNum, randNum)) {
                    //根据用户名获取用户
                    Adks_user user = userApi.getUserInfo(userName);
                    //判断该用户信息在redis中是否存在
                    if (null != user) {
                        //判断用户的信息
                        //判断用户的状态如果状态是2代表停用
                        if (user.getUserStatus().intValue() == 2) {
                            message = "您的账号已停用，请联系管理员！";
                        }
                        else if (null != user.getOverdate()
                                && DateTimeUtil.compare_date(DateTimeUtil.dateToString(user.getOverdate()),
                                        DateTimeUtil.dateToString(new Date())) < 0) {
                            message = "您的账号已过期，请联系管理员！";
                        }
                        else {
                            //反之则说明账号正常
                            //判断用户的密码是否正确
                            //如果查询结果显示该用户存在则将该用户放到session以及redis中
                            if (StringUtils.equals(PasswordUtil.getSHA1Password(passWord), user.getUserPassword())) {
                                //通过用户的信息统计用户的时长
                            	String time = courseUserApi.getStudyCourseTime(user.getUserId(),user.getUserName());
                            	
                                //用户名密码都正确将用户放入到session、redis中
                            	userApi.saveUserToRedis(user);
                                //登录成功往useronline表中插入数据
                                Adks_user_online userOnLine = userApi.getUserOnLineByName(user.getUserName());
                                if (userOnLine == null) {
                                    userOnLine = new Adks_user_online();
                                    userOnLine.setUserId(user.getUserId());
                                    userOnLine.setUserName(user.getUserName());
                                    userOnLine.setOrgCode(user.getOrgCode());
                                    userOnLine.setOrgId(user.getOrgId());
                                    userOnLine.setOrgName(user.getOrgName());
                                    userOnLine.setLastCheckDate(new Date());
                                    userOnLine.setSessionId(session.getId());
                                    String ipAddress=IpAddressAndIspUtil.getIpAddr(request);
                                    if(ComUtil.isNotNullOrEmpty(ipAddress)){
                                    	userOnLine.setIpAddress(ipAddress);
                                    	userOnLine.setRegion(IpAddressAndIspUtil.getRegionByIp(ipAddress, request));
                                    	userOnLine.setOperator(IpAddressAndIspUtil.getOperatorByIp(ipAddress, request));
                                    }
                                    userApi.saveUserOnLine(userOnLine);
                                }
                                else {
                                	String ipAddress=IpAddressAndIspUtil.getIpAddr(request);
                                    if(ComUtil.isNotNullOrEmpty(ipAddress)){
                                    	userOnLine.setIpAddress(ipAddress);
                                    	userOnLine.setRegion(IpAddressAndIspUtil.getRegionByIp(ipAddress, request));
                                    	userOnLine.setOperator(IpAddressAndIspUtil.getOperatorByIp(ipAddress, request));
                                    }
                                    userOnLine.setLastCheckDate(new Date());
                                    userOnLine.setSessionId(session.getId());
                                    userApi.saveUserOnLine(userOnLine);
                                }

                                HashMap<Integer, String> userSessionMap = (HashMap<Integer, String>) request
                                        .getSession().getServletContext().getAttribute("userSessionMap");
                                if (userSessionMap == null) {
                                    userSessionMap = new HashMap<Integer, String>();
                                }
                                String u_sessionId = userSessionMap.get(user.getUserId());
                                if (u_sessionId != null) {
                                    userSessionMap.remove(user.getUserId());
                                }
                                userSessionMap.put(user.getUserId(), session.getId());
                                request.getSession().getServletContext().setAttribute("userSessionMap", userSessionMap);

                                session.setAttribute("user", user);
                                /*model.addAttribute("orgId", user.getOrgId());
                                model.addAttribute("user", user);*/
                                message = "登录成功";
                                //model.addAttribute("orgId",user.getOrgId());
                                //viewName = "/index";
                                //request.setAttribute("isLogin", 1);
                                ra.addFlashAttribute("isLogin", 1);
                                String[] orgIds=user.getOrgCode().split("A");
                                if(orgIds.length>=2){
                                	viewName = "redirect:/index/index/" + orgIds[1] + ".shtml";
                                }else{
                                	viewName = "redirect:/index/index/" + orgIds[0] + ".shtml";
                                }
                                
                                return viewName;
                            }
                        }
                    }
                    else {
                        message = "您输入的用户名或密码有误";
                    }
                }
                else {
                    message = "您输入的验证码有误";
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            message = "您输入的登录信息有误";
        }
        if (message != null && !"".equals(message)) {
            model.addAttribute("message", message);
        }
        if(orgId != null){
        	Adks_org org1=orgApi.getOrgById(orgId);
        	String[] orgIds=org1.getOrgCode().split("A");
            if(orgIds.length>=2){
            	orgId= Integer.parseInt(orgIds[1]);
            }else{
            	orgId =Integer.parseInt(orgIds[0]);
            }
        }else{
        	orgId=1;
        }
        model.addAttribute("orgId", orgId);
        Adks_org_config orgConfig = orgApi.getOrgConfigByOrgId(orgId);
		model.addAttribute("orgConfig", orgConfig);// 机构配置信息，首页上边的banner图地址
        return "/login";
    }

    @RequestMapping(value = "/loginOut.do")
    public String loginOut(HttpServletRequest request, HttpServletResponse response, HttpSession session,Integer orgId, Model model) {
        Adks_user user = null;
        if (session != null && session.getAttribute("user") != null) {
            user = (Adks_user) session.getAttribute("user");
            userApi.deleteUserToRedis(user.getUserName(),user.getUserId());
            userApi.deleteUserOnLine(user.getUserId());
            session.removeAttribute("user");
            session.removeAttribute("openedCourseName");
        }
        String viewName = "";
        if(orgId!=null){
        	viewName="redirect:/index/index/" + orgId + ".shtml";
        }else{
        	viewName="redirect:/index/index/1.shtml";
        }
        return viewName;
    }
}