package com.adks.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 样例程序 =》跳转后台主页面
 */
@Controller
@RequestMapping({""})
public class HomeController {
	private final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	
	@RequestMapping({"/home"})
    public String home(HttpServletRequest request,Model model) {
    	logger.debug("登录成功后,跳转到管理系统首页!");
    	return "/index";
    }
}
