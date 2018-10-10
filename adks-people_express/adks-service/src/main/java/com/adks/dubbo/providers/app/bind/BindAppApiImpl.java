package com.adks.dubbo.providers.app.bind;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.user.Adks_user_bind;
import com.adks.dubbo.api.interfaces.app.bind.BindAppApi;
import com.adks.dubbo.service.app.bind.BindAppService;

public class BindAppApiImpl implements BindAppApi {

	@Autowired
	private BindAppService bindService;

	
	@Override
	public List<Adks_user_bind> getByUserId(int userId) {
		return bindService.getByUserId(userId);
	}

	@Override
	public void deleteUserByIds(String userIds) {
		bindService.deleteUserByIds(userIds);
	}

	@Override
	public void deleteByRemoteUserIdAndUserId(String remoteUserId, int userId) {
		bindService.deleteByRemoteUserIdAndUserId(remoteUserId, userId);
	}

	@Override
	public void saveUser(Adks_user_bind bind) {
		Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
		insertColumnValueMap.put("userId", bind.getUserId());
		insertColumnValueMap.put("remoteUserId", bind.getRemoteUserId());
		insertColumnValueMap.put("clientType", bind.getClientType());
		insertColumnValueMap.put("deviceId", bind.getDeviceId());
		insertColumnValueMap.put("bindType", bind.getBindType());
		insertColumnValueMap.put("deviceType", bind.getDeviceType());

		if (bind.getId() != null) {
			Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
			updateWhereConditionMap.put("id", bind.getUserId());
			insertColumnValueMap.put("updateTime", new Date());
			bindService.update(insertColumnValueMap, updateWhereConditionMap);
		} else {
			insertColumnValueMap.put("createTime", new Date());
			bindService.insert(insertColumnValueMap);
		}
	}
}
