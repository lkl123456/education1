package com.adks.dubbo.api.interfaces.app.user;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.user.Adks_usercollection;
import com.adks.dubbo.commons.Page;

public interface UserCollectionAppApi {
	/**
	 * 收藏列表
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getCollectionPage(Page<List<Map<String, Object>>> page);

	/**
	 * 添加收藏
	 * 
	 * @param usercollection
	 * @return
	 */
	public int saveUserCollection(Adks_usercollection usercollection);

	/**
	 * 删除收藏
	 * 
	 * @param userId
	 * @param courseId
	 * @return
	 */
	public int deleteCollection(int id);

	/**
	 * 根据userId和courseId验证是否收藏过
	 * 
	 * @param userId
	 * @param courseId
	 * @return
	 */
	public Map<String, Object> getByUCId(int userId, int courseId);
}
