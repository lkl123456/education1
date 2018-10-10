package com.adks.dubbo.api.interfaces.app.user;

import java.util.Map;

import com.adks.dubbo.api.data.user.Adks_user;

public interface UserAppApi {
	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param username
	 * @return
	 */
	public Adks_user getUserByUserName(String username);

	/**
	 * 根据userId获取用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getUserByUserId(int userId);

	/**
	 * 保存用户信息
	 * 
	 * @param user
	 */
	public boolean saveUser(Adks_user user);
	
}
