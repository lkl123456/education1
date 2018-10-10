package com.adks.dubbo.service.web.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dubbo.commons.SearchResult;
import com.adks.dubbo.dao.web.search.SolrSearchDao;

@Service
public class SearchService {
	@Autowired
	private SolrSearchDao solrSearchDao;

	public SearchResult search(String queryString, String queryType, Integer page, Integer rows) {
		// 创建查询对象
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery(queryString);
		List<String> analysis = getAnalysis(queryString);
		analysis.add(0, queryString);
		for (String string : analysis) {
			System.out.println(string);
		}
		// 设置分页
		query.setStart((page - 1) * rows);
		query.setRows(rows);
		// 设置默认搜素域
		query.set("df", "index_search");
		if (StringUtils.isNotEmpty(queryType) && !queryType.equals("0"))
			query.setFilterQueries("objectType:" + queryType);
		// 设置高亮显示
		query.setHighlight(true);
		// 设置排序
//		query.setSort("longTime", ORDER.desc);

		query.addHighlightField("newsTitle");
		query.addHighlightField("newsContent");

		query.addHighlightField("courseName");
		query.addHighlightField("courseDes");

		query.addHighlightField("authorName");

		query.addHighlightField("authorDes");

		query.addHighlightField("gradeName");
		query.addHighlightField("gradeDesc");

		//

		// 以下两个方法主要是在高亮的关键字前后加上html代码
		query.setHighlightSimplePre("<font color=\"red\">");
		query.setHighlightSimplePost("</font>");

		// 执行查询
		SearchResult searchResult = solrSearchDao.search(query);
		// 计算查询结果总页数
		long recordCount = searchResult.getRecordCount();
		long pageCount = recordCount / rows;
		if (recordCount % rows > 0) {
			pageCount++;
		}
		searchResult.setPageCount(pageCount);
		searchResult.setCurPage(page);
		searchResult.setQueryString(queryString);
		return searchResult;
	}

	/**
	 * {根据用户输入的搜索词进行分词}
	 * 
	 */
	public List<String> getAnalysis(String queryString) {
		return solrSearchDao.getAnalysis(queryString);
	}
}
