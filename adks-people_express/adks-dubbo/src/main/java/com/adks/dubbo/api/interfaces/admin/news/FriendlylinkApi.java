package com.adks.dubbo.api.interfaces.admin.news;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.common.Adks_friendly_link;
import com.adks.dubbo.commons.Page;

public interface FriendlylinkApi {
	
	/**
	 * 获取友情链接分页列表
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getFriendlylinkListPage(Page<List<Map<String, Object>>> page);
	
	/**
	 * 保存一条友情链接
	 * @param adksFriendlylink
	 */
	Integer saveFriendlylink(Adks_friendly_link adksFriendlylink);
	
	
	/**
	 * 批量删除友情链接
	 * @param fdLinkIds
	 */
	void deleteFriendlylinkByIds(String fdLinkIds);
	
	/**
	 * 根据id获取友情链接
	 * @param fdLinkId
	 * @return
	 */
	Map<String, Object> getFriendlylinkInfoById(Integer fdLinkId);
}
