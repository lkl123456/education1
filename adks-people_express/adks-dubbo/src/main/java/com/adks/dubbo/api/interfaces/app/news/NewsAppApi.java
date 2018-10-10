package com.adks.dubbo.api.interfaces.app.news;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.news.Adks_news;

public interface NewsAppApi {

	/**
	 * 获取新闻列表
	 * 
	 * @param authorId
	 * @return
	 */
	public List<Map<String, Object>> getNews(Map<String, Object> map);

	public Adks_news getById(int id);
}
