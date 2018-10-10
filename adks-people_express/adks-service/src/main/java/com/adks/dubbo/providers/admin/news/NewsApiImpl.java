package com.adks.dubbo.providers.admin.news;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dbclient.mysql.MysqlClient;
import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.api.interfaces.admin.news.NewsApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.news.NewsService;
import com.adks.dubbo.service.web.news.NewsWebService;

public class NewsApiImpl implements NewsApi {

	@Autowired
	private MysqlClient mysqlClient;

	@Autowired
	private NewsWebService newsWebService;

	@Autowired
	private NewsService NewsService;

	@Override
	public Page<List<Map<String, Object>>> getNewsListPage(Page<List<Map<String, Object>>> page) {

		return NewsService.getNewsListPage(page);
	}

	@Override
	public Integer saveNews(Adks_news adksNews) {
		int newsId = 0;
		Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();

		insertColumnValueMap.put("newsTitle", adksNews.getNewsTitle());
		insertColumnValueMap.put("newsContent", adksNews.getNewsContent());
		insertColumnValueMap.put("newsFocusPic", adksNews.getNewsFocusPic());
		insertColumnValueMap.put("newsType", adksNews.getNewsType());
		insertColumnValueMap.put("newsLink", adksNews.getNewsLink());
		insertColumnValueMap.put("newsSortId", adksNews.getNewsSortId());
		insertColumnValueMap.put("newsSortName", adksNews.getNewsSortName());
		insertColumnValueMap.put("newsHtmlAdress", adksNews.getNewsHtmlAdress());
		insertColumnValueMap.put("orgId", adksNews.getOrgId());
		insertColumnValueMap.put("orgName", adksNews.getOrgName());
		insertColumnValueMap.put("orgCode", adksNews.getOrgCode());
//		insertColumnValueMap.put("newsSortType", adksNews.getNewsSortType());
		insertColumnValueMap.put("gradeId", adksNews.getGradeId());
		insertColumnValueMap.put("creatorId", adksNews.getCreatorId());
		insertColumnValueMap.put("creatorName", adksNews.getCreatorName());
		insertColumnValueMap.put("createTime", adksNews.getCreateTime());
		insertColumnValueMap.put("newsBelong", adksNews.getNewsBelong());
		if (adksNews.getNewsId() != null) {
			Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
			updateWhereConditionMap.put("newsId", adksNews.getNewsId());
			newsId = adksNews.getNewsId();
			NewsService.update(insertColumnValueMap, updateWhereConditionMap);
		} else {
			newsId = NewsService.insert(insertColumnValueMap);
		}
		// Adks_news newsById = getNewsById(newsId);
		// newsWebService.addNewsToSolr(newsById);
		/*
		 * 对添加新闻的机构更新redis数据 此处的方法仅仅用于更新redis不涉及到数据返回
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", adksNews.getOrgId());
		//map.put("newsSortType", adksNews.getNewsSortType());
		map.put("newsSortType", adksNews.getNewsSortType());
		map.put("num", 20);
		NewsService.setNewsToRedis(map);
		return newsId;
	}

	@Override
	public void deleteNewsByIds(Map<String, Object> map) {
		NewsService.deleteNewsByIds(map);

	}

	@Override
	public Map<String, Object> getNewsInfoById(Integer newsId) {
		return NewsService.getNewsInfoById(newsId);
	}

	@Override
	public Adks_news getNewsById(Integer newsId) {
		return NewsService.getNewsById(newsId);
	}

}
