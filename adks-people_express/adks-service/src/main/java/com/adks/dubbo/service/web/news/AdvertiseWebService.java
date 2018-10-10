package com.adks.dubbo.service.web.news;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.common.Adks_advertise;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.web.news.AdvertiseWebDao;
import com.adks.dubbo.util.AdvertiseRedisUtil;
import com.adks.dubbo.util.NewsRedisUtil;
import com.alibaba.fastjson.JSONObject;

@Service
public class AdvertiseWebService extends BaseService<AdvertiseWebDao> {

	@Autowired
	private AdvertiseWebDao advertiseDao;

	@Override
	protected AdvertiseWebDao getDao() {
		return advertiseDao;
	}

	public Page<List<Map<String, Object>>> getAdvertiseListPage(Page<List<Map<String, Object>>> page) {
		return advertiseDao.getAdvertiseListPage(page);
	}

	public Map<String, Object> getAdvertiseInfoById(Integer adId) {
		return advertiseDao.getAdvertiseInfoById(adId);
	}

	public void deleteAdvertiseByIds(String adIds) {
		advertiseDao.deleteAdvertiseByIds(adIds);
	}

	public List<Adks_advertise> getAdvertiseByCon(Map map) {
		List<Adks_advertise> list = new ArrayList<Adks_advertise>();

		if (map.get("orgId") != null && !"".equals(map.get("orgId"))) {
			String result = AdvertiseRedisUtil.getAdvertise(MapUtils.getInteger(map, "orgId"), false);
			if (StringUtils.isNotEmpty(result)) {
				if (!result.equals("Nodata"))
					list = JSONObject.parseArray(result, Adks_advertise.class);
			} else {
				list = advertiseDao.getAdvertiseByCon(map);
				AdvertiseRedisUtil.addAdvertise(MapUtils.getInteger(map, "orgId"), list);
			}
		}
		return list;
	}
	
	/**
	 * 
	 * @Title getAdvertiseInfoByOrgIdAndLocation
	 * @Description
	 * @author xrl
	 * @Date 2017年6月14日
	 * @param map
	 * @return
	 */
	public Adks_advertise getAdvertiseInfoByOrgIdAndLocation(Map<String, Object> map){
		return advertiseDao.getAdvertiseInfoByOrgIdAndLocation(map);
	}
}
