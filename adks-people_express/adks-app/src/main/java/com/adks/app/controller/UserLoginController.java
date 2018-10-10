package com.adks.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.app.util.JsonResponse;
import com.adks.commons.util.PasswordUtil;
import com.adks.commons.util.PropertiesFactory;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.api.data.user.Adks_user_online;
import com.adks.dubbo.api.interfaces.app.user.UserAppApi;
import com.adks.dubbo.api.interfaces.app.user.UserOnlineAppApi;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/api/user")
public class UserLoginController {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(UserLoginController.class);

	@Autowired
	private UserAppApi userAppApi;
	@Autowired
	private UserOnlineAppApi userOnlineAppApi;

	/**
	 * 登录
	 * 
	 * @param data
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/login.do")
	@ResponseBody
	public JsonResponse login(String data) throws IOException {
		String error = null;
		String ossPath = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");

		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();
		/* 返回参数 */
		Map resultMap = new HashMap();

		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);

			/* 获取用户填写的用户名和密码 */
			String userName = MapUtils.getString(map, "userName");
			String passWord = MapUtils.getString(map, "passWord");

			if (StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(passWord)) {

				Adks_user user = null;

				System.out.println(userName);
				// ##缓存获取用户信息，缓存空取数据库
				user = userAppApi.getUserByUserName(userName);

				if (user != null) {
					if (!(PasswordUtil.getSHA1Password(passWord.trim())).equals(user.getUserPassword()))
						error = "您输入的用户名或密码有误！";
					else if (user.getUserStatus() == 2) /* userstatus:1激活；2停用 */
						error = "您的账号已停用，请联系管理员！";
					else {
						/* 登录成功,把用户信息保存到 用户在线表中 */
						Adks_user_online userOnline = new Adks_user_online();
						userOnline.setUserId(user.getUserId());// 用户id
						userOnline.setUserName(user.getUserName());// 用户名称
						userOnline.setLastCheckDate(user.getOverdate());// 上次登录时间
						userOnline.setOrgId(user.getOrgId());
						userOnline.setOrgName(user.getOrgName());
						userOnline.setOrgCode(user.getOrgCode());
						// ##上次登录的sessionId
						userOnlineAppApi.saveUserOnline(userOnline);

						resultMap.put("userId", user.getUserId());
						/* 图片服务接口 */
						resultMap.put("imgServer", ossPath);
						// ##如果用户是第一次手机登陆，保存到安装记录表中
					}

				} else
					error = "用户名或密码错误";
			}
		} else
			error = "未接受到参数";

		if (StringUtils.isNotEmpty(error)) {
			return JsonResponse.error(error);
		} else {
			return JsonResponse.success(resultMap);
		}
	}

	/**
	 * 退出
	 * 
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loginOut")
	@ResponseBody
	public JsonResponse loginOut(HttpSession session) throws IOException {

		Map resultMap = new HashMap();
		if (session != null && session.getAttribute("user") != null) {
			Adks_user user = (Adks_user) session.getAttribute("user");
			session.removeAttribute("user");
			session.removeAttribute("openedCourseName");
			/* 删除 用户在线临时表中的 该用户信息 */
			userOnlineAppApi.deleteUserOnline(user.getUserName());
			resultMap.put("message", "退出成功");
		} else
			resultMap.put("message", "用户未登录");
		return JsonResponse.success(resultMap);
	}
}
