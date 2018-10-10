package com.adks.dubbo.api.interfaces.admin.news;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.news.Adks_news_sort;
import com.adks.dubbo.commons.Page;

public interface NewsSortApi {

	/**
	 * 获取新闻分类分页列表
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getNewsSortListPage(Page<List<Map<String, Object>>> page);

	/**
	 * 获取新闻分类
	 */
	public List<Adks_news_sort> getNewsSortList(Map<String, Object> map);

	/**
	 * 保存一条新闻分类
	 * 
	 * @param adksNewsSort
	 */
	public Integer saveNewsSort(Adks_news_sort adksNewsSort);

	/**
	 * 批量删除新闻分类
	 * 
	 * @param newsSortIds
	 */
	public void deleteNewsSortByIds(Map<String, Object> map);

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
