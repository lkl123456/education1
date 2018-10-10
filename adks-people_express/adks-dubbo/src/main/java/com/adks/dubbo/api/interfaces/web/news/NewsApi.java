package com.adks.dubbo.api.interfaces.web.news;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.commons.Page;

public interface NewsApi {

    /**
     * 根据id获取新闻信息
     * @param newsId
     * @return
     */
    Map<String, Object> getNewsInfoById(Integer newsId);

    Adks_news getNewsById(Integer newsId);

    //获取最新新闻
    public List<Adks_news> getTopCmsArticleList(Map map);

    //前台新闻分页
    public Page<List<Adks_news>> getNewsListPageWeb(Page<List<Adks_news>> page);

    void initData();
}
