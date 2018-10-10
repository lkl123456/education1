package com.adks.dubbo.providers.web.news;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.common.Adks_advertise;
import com.adks.dubbo.api.interfaces.web.news.AdvertiseApi;
import com.adks.dubbo.service.web.news.AdvertiseWebService;

public class AdvertiseApiImpl implements AdvertiseApi{

	@Autowired
	private AdvertiseWebService advertiseService;
	
	@Override
	public Map<String, Object> getAdvertiseInfoById(Integer adId) {
		return advertiseService.getAdvertiseInfoById(adId);
	}

	public List<Adks_advertise> getAdvertiseByCon(Map map){
		return advertiseService.getAdvertiseByCon(map);
	}

	@Override
	public Adks_advertise getAdvertiseInfoByOrgIdAndLocation(Map<String, Object> map) {
		return advertiseService.getAdvertiseInfoByOrgIdAndLocation(map);
	}

}
