package com.adks.dubbo.providers.admin.news;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.commons.util.RedisConstant;
import com.adks.dubbo.api.data.common.Adks_advertise;
import com.adks.dubbo.api.interfaces.admin.news.AdvertiseApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.news.AdvertiseService;
import com.alibaba.fastjson.JSONObject;

public class AdvertiseApiImpl implements AdvertiseApi {

	@Autowired
	private AdvertiseService advertiseService;

	@Override
	public Page<List<Map<String, Object>>> getAdvertiseListPage(Page<List<Map<String, Object>>> page) {
		return advertiseService.getAdvertiseListPage(page);
	}

	@Override
	public Integer saveAdvertise(Adks_advertise adksAdvertise) {
		Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		insertColumnValueMap.put("orgId", adksAdvertise.getOrgId());
		insertColumnValueMap.put("orgName", adksAdvertise.getOrgName());
		insertColumnValueMap.put("orgCode", adksAdvertise.getOrgCode());
		insertColumnValueMap.put("adImgPath", adksAdvertise.getAdImgPath());
		insertColumnValueMap.put("adLocation", adksAdvertise.getAdLocation());
		insertColumnValueMap.put("adHref", adksAdvertise.getAdHref());
		insertColumnValueMap.put("status", adksAdvertise.getStatus());
		insertColumnValueMap.put("creatorId", adksAdvertise.getCreatorId());
		insertColumnValueMap.put("creatorName", adksAdvertise.getCreatorName());
		insertColumnValueMap.put("createTime", adksAdvertise.getCreateTime());
		Integer adId=0;
		if (adksAdvertise.getAdId() != null) {
			Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
			updateWhereConditionMap.put("adId", adksAdvertise.getAdId());
			adId=advertiseService.update(insertColumnValueMap, updateWhereConditionMap);
		} else {
			adId=advertiseService.insert(insertColumnValueMap);
		}
		map.put("orgId", adksAdvertise.getOrgId());
		// 保存广告后，在redis中更细
		advertiseService.setAdvertiseToRedis(map);
		return adId;
	}

	@Override
	public Map<String, Object> getAdvertiseInfoById(Integer adId) {
		return advertiseService.getAdvertiseInfoById(adId);
	}

	@Override
	public void deleteAdvertiseByIds(Map<String, Object> map) {
		advertiseService.deleteAdvertiseByIds(map);
	}

}
