package com.adks.dubbo.api.interfaces.admin.news;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.commons.Page;

public interface NewsApi {

	/**
	 * 获取新闻分页列表
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getNewsListPage(Page<List<Map<String, Object>>> page);

	/**
	 * 保存一条新闻
	 * 
	 * @param adksNews
	 */
	Integer saveNews(Adks_news adksNews);

	/**
	 * 批量删除新闻
	 * 
	 * @param newsIds
	 */
	void deleteNewsByIds(Map<String, Object> map);

	/**
	 * 根据id获取新闻信息
	 * 
	 * @param newsId
	 * @return
	 */
	Map<String, Object> getNewsInfoById(Integer newsId);

	Adks_news getNewsById(Integer newsId);
}
