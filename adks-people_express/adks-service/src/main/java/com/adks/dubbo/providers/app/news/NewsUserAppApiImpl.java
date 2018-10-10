package com.adks.dubbo.providers.app.news;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.news.Adks_news_user;
import com.adks.dubbo.api.interfaces.app.news.NewsUserAppApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.app.news.NewsUserAppService;

public class NewsUserAppApiImpl implements NewsUserAppApi {

	@Autowired
	private NewsUserAppService newsUserService;

	@Override
	public int getNoReadNews(Map<String, Object> map) {
		return newsUserService.getNoReadNews(map);
	}

	@Override
	public Page<List<Map<String, Object>>> getGradeNewsByUserId(Page<List<Map<String, Object>>> page) {
		return newsUserService.getGradeNewsByUserId(page);
	}

	@Override
	public Page<List<Map<String, Object>>> getSystemNews(Page<List<Map<String, Object>>> page) {
		return newsUserService.getSystemNews(page);
	}

	@Override
	public Adks_news_user getNewsInfo(int newId, int userId, int gradeId) {
		return newsUserService.getNewsInfo(newId, userId, gradeId);
	}

	@Override
	public int saveNewsIsRead(Adks_news_user newsUser) {
		Integer flag = 0;
		Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
		insertColumnValueMap.put("newsId", newsUser.getNewsId());
		insertColumnValueMap.put("userId", newsUser.getUserId());
		insertColumnValueMap.put("gradeId", newsUser.getGradeId());
		insertColumnValueMap.put("isRead", newsUser.getIsRead());
		insertColumnValueMap.put("createDate", newsUser.getCreateDate());
		if (newsUser.getId() != null) {
			Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
			updateWhereConditionMap.put("id", newsUser.getId());
			flag = newsUserService.update(insertColumnValueMap, updateWhereConditionMap);
		} else {
			flag = newsUserService.insert(insertColumnValueMap);
		}
		return flag;
	}
}
