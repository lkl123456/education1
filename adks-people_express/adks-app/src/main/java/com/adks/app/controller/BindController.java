/**
 * 
 */
package com.adks.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.app.util.JsonResponse;
import com.adks.dubbo.api.data.user.Adks_user_bind;
import com.adks.dubbo.api.interfaces.app.bind.BindAppApi;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/api/push")
public class BindController {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(BindController.class);

	@Autowired
	private BindAppApi bindAppApi;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/bind.do")
	@ResponseBody
	public JsonResponse bind(String data) throws IOException {
		String error = null;

		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();
		/* 返回参数 */
		Map resultMap = new HashMap();

		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);

			/* 获取版本号 */
			int userId = MapUtils.getInteger(map, "uid");
			int deviceType = MapUtils.getInteger(map, "deviceType");// 3 anzhou
																	// 4IOS
			String clientId = MapUtils.getString(map, "clientId");// baidu
																	// userId
			String channelId = MapUtils.getString(map, "channelId");// baidu
																	// channelId

			if (userId != 0) {
				List<Adks_user_bind> userBinds = bindAppApi.getByUserId(userId);
				if (userBinds != null && userBinds.size() > 0) {
					Adks_user_bind userBind = null;
					for (Adks_user_bind ub : userBinds) {
						if (ub.getRemoteUserId().equals(clientId)) {
							userBind = ub;
						}
					}

					if (userBind == null) {
						resultMap.put("message", "您的帐号已在其他设备登录");
						bindAppApi.getByUserId(userId);
					} else {
						// 如果有其他账户登录此手机删除绑定信息
						bindAppApi.deleteByRemoteUserIdAndUserId(clientId, userId);

					}
				}
				Adks_user_bind user_bind = new Adks_user_bind();
				user_bind.setUserId(userId);
				user_bind.setRemoteUserId(clientId);
				user_bind.setBindType(3);
				user_bind.setDeviceId(channelId);
				user_bind.setDeviceType(deviceType);
				bindAppApi.saveUser(user_bind);
				resultMap.put("message", "绑定成功");
			}
		} else
			error = "未接受到参数";

		if (StringUtils.isNotEmpty(error)) {
			return JsonResponse.error(resultMap);
		} else {
			return JsonResponse.success(resultMap);
		}
	}

}
