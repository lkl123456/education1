package com.adks.dubbo.providers.app.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.user.Adks_user_online;
import com.adks.dubbo.api.interfaces.app.user.UserOnlineAppApi;
import com.adks.dubbo.service.app.user.UserOnlineAppService;

public class UserOnlineAppApiImpl implements UserOnlineAppApi {

	@Autowired
	private UserOnlineAppService userOnlineservice;

	/**
	 * 保存用户在线表（临时表）
	 */
	@Override
	public int saveUserOnline(Adks_user_online user_online) {
		Integer flag = 0;
		Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
		try {
			insertColumnValueMap.put("userId", user_online.getUserId());
			insertColumnValueMap.put("userName", user_online.getUserName());
			insertColumnValueMap.put("lastCheckDate", user_online.getLastCheckDate());
			insertColumnValueMap.put("orgId", user_online.getOrgId());
			insertColumnValueMap.put("orgName", user_online.getOrgName());
			insertColumnValueMap.put("orgCode", user_online.getOrgCode());
			insertColumnValueMap.put("sessionId", user_online.getSessionId());
			if (user_online.getUserOnlineId() != null) {
				Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
				updateWhereConditionMap.put("userOnlineId", user_online.getUserOnlineId());
				flag = userOnlineservice.update(insertColumnValueMap, updateWhereConditionMap);
			} else {
				flag = userOnlineservice.insert(insertColumnValueMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 根据用户名称删除在线数据
	 */
	@Override
	public void deleteUserOnline(String userName) {
		userOnlineservice.deleteUserOnline(userName);
	}
}
