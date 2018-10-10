package com.adks.dubbo.api.interfaces.app.news;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.news.Adks_news_user;
import com.adks.dubbo.commons.Page;

public interface NewsUserAppApi {

	/**
	 * 未读消息数量
	 * 
	 * @param userId
	 * @return
	 */
	public int getNoReadNews(Map<String, Object> map);

	/**
	 * 我的班级消息
	 * 
	 * @param gradeId
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeNewsByUserId(Page<List<Map<String, Object>>> page);

	/**
	 * 我的系统消息
	 * 
	 * @param gradeId
	 * @return
	 */
	public Page<List<Map<String, Object>>> getSystemNews(Page<List<Map<String, Object>>> page);

	/**
	 * 获取消息详细信息
	 * 
	 * @param newsId
	 * @return
	 */
	public Adks_news_user getNewsInfo(int newId, int userId, int gradeId);

	/**
	 * 修改消息状态 1已读 2未读
	 * 
	 * @param newsId
	 * @return
	 */
	public int saveNewsIsRead(Adks_news_user newsUser);
}
