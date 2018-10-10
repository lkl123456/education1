package com.adks.dubbo.providers.app.news;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.api.interfaces.app.news.NewsAppApi;
import com.adks.dubbo.service.app.news.NewsAppService;

public class NewsAppApiImpl implements NewsAppApi {

	@Autowired
	private NewsAppService newsService;

	@Override
	public List<Map<String, Object>> getNews(Map<String, Object> map) {
		return newsService.getNews(map);
	}

	@Override
	public Adks_news getById(int id) {
		return newsService.getById(id);
	}
}
