package com.adks.dubbo.dao.app.user;

import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;

@Component
public class UserOnlineAppDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_user_online";
	}

	/**
	 * 根据用户名称删除在线数据
	 * 
	 * @param userName
	 */
	public void deleteUserOnline(String userName) {
		String sql = " delete from adks_user_online where userName = " + userName;
		mysqlClient.update(sql, new Object[] {});
	}
}
