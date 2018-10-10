package com.adks.dubbo.providers.web.news;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.news.Adks_news_sort;
import com.adks.dubbo.api.interfaces.web.news.NewsSortApi;
import com.adks.dubbo.service.web.news.NewsSortWebService;

public class NewsSortApiImpl implements NewsSortApi{
	
	@Autowired
	private NewsSortWebService newsSortService;
	
	@Override
	public List<Adks_news_sort> getNewsSortList(Map<String, Object> map) {
		return newsSortService.getNewsSortList(map);
	}

	@Override
	public Map<String, Object> getNewsSortById(Integer newsSortId) {
		return newsSortService.getNewsSortById(newsSortId);
	}

	@Override
	public List<Adks_news_sort> getNewsSortListByCon(Map<String, Object> map) {
		return newsSortService.getNewsSortListByCon(map);
	}

}
