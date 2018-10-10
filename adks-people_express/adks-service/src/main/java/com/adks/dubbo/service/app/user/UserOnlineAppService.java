package com.adks.dubbo.service.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.dao.app.user.UserOnlineAppDao;

@Service
public class UserOnlineAppService extends BaseService<BaseDao> {

	@Autowired
	private UserOnlineAppDao userOnlinedao;

	@Override
	protected UserOnlineAppDao getDao() {
		return userOnlinedao;
	}

	/**
	 * 根据用户名称删除在线数据
	 * 
	 * @param userName
	 */
	public void deleteUserOnline(String userName) {
		userOnlinedao.deleteUserOnline(userName);
	}
}
