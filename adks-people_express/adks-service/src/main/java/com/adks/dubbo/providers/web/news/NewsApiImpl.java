package com.adks.dubbo.providers.web.news;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dbclient.mysql.MysqlClient;
import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.api.data.news.Adks_news_sort;
import com.adks.dubbo.api.interfaces.web.news.NewsApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.web.news.NewsSortWebService;
import com.adks.dubbo.service.web.news.NewsWebService;
import com.adks.dubbo.service.web.search.SearchService;

public class NewsApiImpl implements NewsApi {

    @Autowired
    private MysqlClient mysqlClient;

    @Autowired
    private NewsWebService NewsService;

    @Autowired
    private SearchService searchService;
    
    @Autowired
	private NewsSortWebService newsSortWebService;

    @Override
    public Map<String, Object> getNewsInfoById(Integer newsId) {
        return NewsService.getNewsInfoById(newsId);
    }

    @Override
    public List<Adks_news> getTopCmsArticleList(Map map) {
    	Adks_news_sort sort=newsSortWebService.getNewsSortByNewsSortType(map);
    	map.put("newsSortId", sort.getNewsSortId());
        return NewsService.getTopCmsArticleList(map);
    }

    @Override
    public Adks_news getNewsById(Integer newsId) {
        return NewsService.getNewsById(newsId);
    }

    @Override
    public Page<List<Adks_news>> getNewsListPageWeb(Page<List<Adks_news>> page) {
        Page<List<Adks_news>> result = null;
//        if (null != searchService.queryNews(page)) {
//            result = searchService.queryNews(page);
//         }else {
        	 result = NewsService.getNewsListPageWeb(page);
//          }
        return result;
    }

    @Override
    public void initData() {
        NewsService.initData();
    }

}
