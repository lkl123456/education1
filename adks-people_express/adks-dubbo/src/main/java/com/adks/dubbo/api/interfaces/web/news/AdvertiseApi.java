package com.adks.dubbo.api.interfaces.web.news;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.common.Adks_advertise;
import com.adks.dubbo.commons.Page;

public interface AdvertiseApi {
	
	/**
	 * 根据id获取广告位信息
	 * @param adId
	 * @return
	 */
	public Map<String, Object> getAdvertiseInfoById(Integer adId);
	
	public List<Adks_advertise> getAdvertiseByCon(Map map);
	
	/**
	 * 
	 * @Title getAdvertiseInfoByOrgIdAndLocation
	 * @Description:根据机构Id和位置获取广告
	 * @author xrl
	 * @Date 2017年6月14日
	 * @param map
	 * @return
	 */
	public Adks_advertise getAdvertiseInfoByOrgIdAndLocation(Map<String, Object> map);
}
