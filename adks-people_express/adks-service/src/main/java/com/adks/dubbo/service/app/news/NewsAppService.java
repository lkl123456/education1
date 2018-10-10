package com.adks.dubbo.service.app.news;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.dao.app.news.NewsAppDao;
import com.adks.dubbo.util.NewsRedisUtil;
import com.alibaba.fastjson.JSONObject;

@Service
public class NewsAppService extends BaseService<NewsAppDao> {

	@Autowired
	private NewsAppDao newsDao;

	@Override
	protected NewsAppDao getDao() {
		return newsDao;
	}

	/**
	 * 获取新闻列表
	 * 
	 * @param authorId
	 * @return
	 */
	public List<Map<String, Object>> getNews(Map<String, Object> map) {
		return newsDao.getNews(map);
	}

	public Adks_news getById(int id) {
		return newsDao.getById(id);
	}
}
