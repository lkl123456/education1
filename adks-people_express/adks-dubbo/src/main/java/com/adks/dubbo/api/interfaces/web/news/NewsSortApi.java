package com.adks.dubbo.api.interfaces.web.news;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.news.Adks_news_sort;

public interface NewsSortApi {

	/**
	 * 获取新闻分类
	 */
	public List<Adks_news_sort> getNewsSortList(Map<String, Object> map);

	/**
	 * 根据分类的name获取id
	 * 
	 * @param newsSortName
	 * @return
	 */
	public Map<String, Object> getNewsSortById(Integer newsSortId);

	/**
	 * 获取新闻分类
	 */
	public List<Adks_news_sort> getNewsSortListByCon(Map<String, Object> map);

}
