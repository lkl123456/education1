package com.adks.dubbo.api.interfaces.admin.news;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.common.Adks_advertise;
import com.adks.dubbo.commons.Page;

public interface AdvertiseApi {
	/**
	 * 获取广告位分页列表
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getAdvertiseListPage(Page<List<Map<String, Object>>> page);

	/**
	 * 保存、修改一条Advertise
	 * 
	 * @param adksAdvertise
	 */
	public Integer saveAdvertise(Adks_advertise adksAdvertise);

	/**
	 * 根据id获取广告位信息
	 * 
	 * @param adId
	 * @return
	 */
	public Map<String, Object> getAdvertiseInfoById(Integer adId);

	/**
	 * 删除数据
	 * 
	 * @param adIds
	 */
	public void deleteAdvertiseByIds(Map<String, Object> map);

}
