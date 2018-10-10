package com.adks.dubbo.service.admin.news;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.commons.util.RedisConstant;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.common.Adks_advertise;
import com.adks.dubbo.api.data.news.Adks_news_sort;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.news.AdvertiseDao;
import com.adks.dubbo.util.AdvertiseRedisUtil;
import com.adks.dubbo.util.NewsRedisUtil;
import com.alibaba.fastjson.JSONObject;

@Service
public class AdvertiseService extends BaseService<AdvertiseDao> {

	@Autowired
	private AdvertiseDao advertiseDao;

	@Override
	protected AdvertiseDao getDao() {
		return advertiseDao;
	}

	public Page<List<Map<String, Object>>> getAdvertiseListPage(Page<List<Map<String, Object>>> page) {
		return advertiseDao.getAdvertiseListPage(page);
	}

	public void setAdvertiseToRedis(Map<String, Object> map) {
		AdvertiseRedisUtil.emptyAdvertise(MapUtils.getString(map, "orgId"));

		if (map.get("orgId") != null && !"".equals(map.get("orgId"))) {
			List<Adks_advertise> list = advertiseDao.getAdvertiseByCon(map);
			AdvertiseRedisUtil.addAdvertise(MapUtils.getInteger(map, "orgId"), list);
		}
	}

	public Map<String, Object> getAdvertiseInfoById(Integer adId) {
		return advertiseDao.getAdvertiseInfoById(adId);
	}

	public void deleteAdvertiseByIds(Map<String, Object> map) {

		String adIds = MapUtils.getString(map, "delAdIds");
		String orgIdsStr = MapUtils.getString(map, "delOrgIds");
		String[] orgIds = orgIdsStr.split(",");
		Set set = new HashSet();
		String[] orgIdsNoSame = (String[]) set.toArray(orgIds);
		String orgId = "";
		for (String string : orgIdsNoSame) {
			orgId += string + ",";
		}
		orgId = orgId.substring(0, orgId.length() - 1);

		AdvertiseRedisUtil.emptyAdvertise(orgId);
		advertiseDao.deleteAdvertiseByIds(adIds);

		List<Adks_advertise> list = advertiseDao.getAdvertiseByCon(map);
		AdvertiseRedisUtil.addAdvertise(MapUtils.getInteger(map, "orgId"), list);

	}
}
