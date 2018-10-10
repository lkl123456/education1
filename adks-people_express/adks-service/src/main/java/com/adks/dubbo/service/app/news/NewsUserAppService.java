package com.adks.dubbo.service.app.news;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.news.Adks_news_user;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.app.news.NewsUserAppDao;

@Service
public class NewsUserAppService extends BaseService<NewsUserAppDao> {

	@Autowired
	private NewsUserAppDao newsUserDao;

	@Override
	protected NewsUserAppDao getDao() {
		return newsUserDao;
	}

	/**
	 * 未读消息数量
	 * 
	 * @param userId
	 * @return
	 */
	public int getNoReadNews(Map<String, Object> map) {
		return newsUserDao.getNoReadNews(map);
	}

	/**
	 * 我的班级消息
	 * 
	 * @param gradeId
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeNewsByUserId(Page<List<Map<String, Object>>> page) {
		return newsUserDao.getGradeNewsByUserId(page);
	}

	/**
	 * 我的系统消息
	 * 
	 * @param gradeId
	 * @return
	 */
	public Page<List<Map<String, Object>>> getSystemNews(Page<List<Map<String, Object>>> page) {
		return newsUserDao.getSystemNews(page);
	}

	/**
	 * 获取消息详细信息
	 * 
	 * @param newsId
	 * @return
	 */
	public Adks_news_user getNewsInfo(int newId, int userId, int gradeId) {
		return newsUserDao.getNewsInfo(newId, userId, gradeId);
	}
}
