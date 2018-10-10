package com.adks.dubbo.service.web.news;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.news.Adks_news_sort;
import com.adks.dubbo.dao.web.news.NewsSortWebDao;
import com.adks.dubbo.util.NewsRedisUtil;
import com.alibaba.fastjson.JSONObject;

@Service
public class NewsSortWebService extends BaseService<NewsSortWebDao> {

	@Autowired
	private NewsSortWebDao newsSortDao;

	@Override
	protected NewsSortWebDao getDao() {
		return newsSortDao;
	}

	public List<Adks_news_sort> getNewsSortList(Map<String, Object> map) {
		return newsSortDao.getNewsSortList(map);
	}

	public Map<String, Object> getNewsSortById(Integer newsSortId) {
		return newsSortDao.getNewsSortById(newsSortId);
	}

	public List<Adks_news_sort> getNewsSortListByCon(Map<String, Object> map) {
		List<Adks_news_sort> list = new ArrayList<>();
		list = newsSortDao.getNewsSortListByCon(map);
//		if (map.get("orgId") != null && !"".equals(map.get("orgId"))) {
//			String result = NewsRedisUtil.getNews(6, MapUtils.getInteger(map, "orgId"), false);
//			if (StringUtils.isNotEmpty(result)) {
//				if (!result.equals("Nodata"))
//					list = JSONObject.parseArray(result, Adks_news_sort.class);
//			} else {
//				NewsRedisUtil.addNewsSort(MapUtils.getInteger(map, "orgId"), list);
//			}
//		}
		return list;
	}
	
	public Adks_news_sort getNewsSortByNewsSortType(Map<String, Object> map){
		return newsSortDao.getNewsSortByNewsSortType(map);
	}
}
