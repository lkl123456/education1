package com.adks.dubbo.service.admin.news;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.commons.oss.OSSUploadUtil;
import com.adks.commons.util.PropertiesFactory;
import com.adks.commons.util.RedisConstant;
import com.adks.dbclient.redis.RedisClient;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.news.NewsDao;
import com.adks.dubbo.dao.web.search.SolrDeleteDao;
import com.adks.dubbo.util.NewsRedisUtil;
import com.alibaba.fastjson.JSONObject;

@Service
public class NewsService extends BaseService<NewsDao> {
	@Autowired
	private RedisClient redisClient;

	@Autowired
	private SolrDeleteDao solrDeleteDao;

	@Autowired
	private NewsDao newsDao;

	@Override
	protected NewsDao getDao() {
		return newsDao;
	}

	public Page<List<Map<String, Object>>> getNewsListPage(Page<List<Map<String, Object>>> page) {
		return newsDao.getNewsListPage(page);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void deleteNewsByIds(Map<String, Object> map) {

		String orgId_typesStr = MapUtils.getString(map, "delOrgId_types");
		String newsIds = MapUtils.getString(map, "delNewsIds");
		String[] orgId_types = orgId_typesStr.split(",");

		Set set = new HashSet();
		for (int i = 0; i < orgId_types.length; i++) {
			set.add(orgId_types[i]);
		}
		String[] orgId_typesNoSame = (String[]) set.toArray(new String[set.size()]);

		if (orgId_typesNoSame != null && orgId_typesNoSame.length > 0) {
			for (String string : orgId_typesNoSame) {
				if (StringUtils.isNotEmpty(string)) {
					String[] types = string.split("_");
					int orgId = Integer.valueOf(types[0]);
					String code = String.valueOf(types[1]);
//					int code = Integer.valueOf(types[1]);

//					int typeCode = 0;
//					if (code == 15) {
//						typeCode = 1;
//					} else if (code == 16) {
//						typeCode = 4;
//					} else if (code == 17) {
//						typeCode = 2;
//					} else if (code == 18) {
//						typeCode = 3;
//					}
					NewsRedisUtil.emptyNews(orgId + "_" + code);
					newsDao.deleteNewsByIds(newsIds);
					map.put("newsSortId", code);
					List<Adks_news> list = newsDao.getTopCmsArticleList(map);
					NewsRedisUtil.addNews(code, MapUtils.getInteger(map, "orgId"), list);
				}
			}
		}
	}

	public Map<String, Object> getNewsInfoById(Integer newsId) {
		return newsDao.getNewsInfoById(newsId);
	}

	public Adks_news getNewsById(Integer newsId) {
		return newsDao.getNewsById(newsId);
	}

	// 前台界面分页
	public Page<List<Adks_news>> getNewsListPageWeb(Page<List<Adks_news>> page) {
		return newsDao.getNewsListPageWeb(page);
	}

	/**
	 * 
	 * @Title getGradeNoticeListPage
	 * @Description:班级公告分页列表
	 * @author xrl
	 * @Date 2017年4月26日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeNoticeListPage(Page<List<Map<String, Object>>> page) {
		return newsDao.getGradeNoticeListPage(page);
	}

	/**
	 * 
	 * @Title delGradeNoticeByGradeId
	 * @Description：根据gradeId删除班级公告
	 * @author xrl
	 * @Date 2017年4月27日
	 * @param gradeId
	 */
	public void delGradeNoticeByGradeId(Integer gradeId) {
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		List<Map<String, Object>> newsList = newsDao.getNewsByGradeId(gradeId);
		for (Map<String, Object> map : newsList) {
			String newsHtmlAdress = (String) map.get("newsHtmlAdress");
			if (newsHtmlAdress != null && !"".equals(newsHtmlAdress)) {
				String newsHtmlAdressPath = ossResource + newsHtmlAdress;
				OSSUploadUtil.deleteFile(newsHtmlAdressPath);
			}
		}
		newsDao.delGradeNoticeByGradeId(gradeId);
	}

	public void setNewsToRedis(Map<String, Object> map) {
		String type = MapUtils.getString(map, "newsSortType");//  首字母缩写
		int orgId = MapUtils.getInteger(map, "orgId");
		NewsRedisUtil.emptyNews(MapUtils.getInteger(map, "orgId") + "_" + type);
		List<Adks_news> list = newsDao.getTopCmsArticleList(map);
		NewsRedisUtil.addNews(type, orgId, list);
	}

	/**
	 * 
	 * @Title checkNewsName
	 * @Description：检查班级公告名在该班级中的唯一性
	 * @author xrl
	 * @Date 2017年5月10日
	 * @param map
	 * @return
	 */
	public Map<String, Object> checkNewsName(Map<String, Object> map) {
		return newsDao.checkNewsName(map);
	}

	public Page<List<Map<String, Object>>> getSysNoticeListPage(Page<List<Map<String, Object>>> page) {
		return newsDao.getSysNoticeListPage(page);
	}

	public void setTopCmsArticleList(Map<String, Object> map) {
		String newsRedisName = RedisConstant.adks_people_express_new;
		if (map.get("orgId") != null && !"".equals(map.get("orgId"))) {
			newsRedisName += "orgId_" + map.get("orgId") + "_";
		}
		if (map.get("newsSortId") != null && !"".equals(map.get("newsSortId"))) {
			newsRedisName += "newsSortId_" + map.get("newsSortId");
		}
//		if (map.get("newsSortType") != null && !"".equals(map.get("newsSortType"))) {
//			newsRedisName += "newsSortType_" + map.get("newsSortType");
//		}
		if (newsRedisName.endsWith("_"))
			newsRedisName = newsRedisName.substring(0, newsRedisName.length() - 1);
		List<Adks_news> list = newsDao.getTopCmsArticleList(map);
		redisClient.set(newsRedisName, JSONObject.toJSONString(list));
	}

	/**
	 * 
	 * @Title deleteGradeNoticeById
	 * @Description：删除班级公告
	 * @author xrl
	 * @Date 2017年5月20日
	 * @param newsId
	 */
	public void deleteGradeNoticeById(Integer newsId) {
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		Adks_news news = newsDao.getNewsById(newsId);
		if (news.getNewsHtmlAdress() != null && !"".equals(news.getNewsHtmlAdress())) {
			OSSUploadUtil.deleteFile(ossResource + news.getNewsHtmlAdress());
		}
		newsDao.deleteNewsByIds(String.valueOf(newsId));
	}
}
