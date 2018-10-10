package com.adks.dubbo.service.web.news;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.web.news.NewsWebDao;
import com.adks.dubbo.util.HtmlPaserUtil;
import com.adks.dubbo.util.NewsRedisUtil;
import com.alibaba.fastjson.JSONObject;

@Service
public class NewsWebService extends BaseService<NewsWebDao> {

	@Autowired
	private SolrServer solrServer;

	@Autowired
	private NewsWebDao newsDao;

	@Override
	protected NewsWebDao getDao() {
		return newsDao;
	}

	public Map<String, Object> getNewsInfoById(Integer newsId) {
		return newsDao.getNewsInfoById(newsId);
	}

	public List<Adks_news> getTopCmsArticleList(Map map) {

		List<Adks_news> list = new ArrayList<>();
		String newsSortType = MapUtils.getString(map, "newsSortType");
		String result = "";
		if (map.get("orgId") != null && !"".equals(map.get("orgId"))) {
			result = NewsRedisUtil.getNews(newsSortType, MapUtils.getInteger(map, "orgId"), false);
			if (StringUtils.isNotEmpty(result)) {
				if (!result.equals("Nodata"))
					list = JSONObject.parseArray(result, Adks_news.class);
			} else {
				list = newsDao.getTopCmsArticleList(map);
				NewsRedisUtil.addNews(newsSortType, MapUtils.getInteger(map, "orgId"), list);
			}
		}
		return list;
	}

	public Adks_news getNewsById(Integer newsId) {
		return newsDao.getNewsById(newsId);
	}

	// 前台界面分页
	public Page<List<Adks_news>> getNewsListPageWeb(Page<List<Adks_news>> page) {
		return newsDao.getNewsListPageWeb(page);
	}

	public void initData() {
		List<Adks_news> list = newsDao.queryAll();
		try {
			for (Adks_news news : list) {
				addOneToSolr(news);
			}
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addNewsToSolr(Adks_news news) {
		try {
			addOneToSolr(news);
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addOneToSolr(Adks_news news) throws Exception {
		SolrInputDocument document = new SolrInputDocument();
		document.setField("id", "newsId_" + news.getNewsId());
		document.setField("newsId", news.getNewsId());
		document.setField("orgId", news.getOrgId());
		document.setField("newsTitle", news.getNewsTitle());
		document.setField("newsFocusPic", news.getNewsFocusPic());
		document.setField("newsHtmlAdress", news.getNewsHtmlAdress());
		document.setField("newsSortType", news.getNewsSortType());
		document.setField("newsType", news.getNewsType());
		document.setField("newsLink", news.getNewsLink());
		document.setField("gradeId", news.getGradeId());
		document.setField("creatorId", news.getCreatorId());
		document.setField("newsSortId", news.getNewsSortId());
		document.setField("newsSortName", news.getNewsSortName());
		document.setField("creatorName", news.getCreatorName());
		document.setField("createTime", news.getCreateTime());

		String desc = new String(news.getNewsContent());
		if (desc != null && !desc.equals("")) {
			try {
				desc = "<body>" + desc + "</body>";
				desc = HtmlPaserUtil.readByHtml(desc);
				if (desc != null && !desc.equals("")) {
					desc = HtmlPaserUtil.ReplaceHtml(desc);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		document.setField("newsContent", desc);
		document.setField("longTime", news.getCreateTime().getTime());
		document.setField("objectType", 2);
		// 写入索引库
		solrServer.add(document);
	}
}
