package com.adks.dubbo.api.interfaces.app.user;

import com.adks.dubbo.api.data.user.Adks_user_online;

public interface UserOnlineAppApi {
	/**
	 * 保存用户在线表（临时表）
	 * 
	 * @param user_online
	 * @return
	 */
	public int saveUserOnline(Adks_user_online user_online);

	/**
	 * 根据用户名称删除在线数据
	 * 
	 * @param userName
	 */
	public void deleteUserOnline(String userName);
}
