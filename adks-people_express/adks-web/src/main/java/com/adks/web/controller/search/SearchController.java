package com.adks.web.controller.search;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.dubbo.api.interfaces.web.author.AuthorApi;
import com.adks.dubbo.api.interfaces.web.course.CourseApi;
import com.adks.dubbo.api.interfaces.web.grade.GradeApi;
import com.adks.dubbo.api.interfaces.web.news.NewsApi;
import com.adks.dubbo.api.interfaces.web.search.SearchApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.commons.SearchResult;

@Controller
@RequestMapping("/search")
public class SearchController {
	private final Logger logger = LoggerFactory.getLogger(SearchController.class);

	@Autowired
	private SearchApi searchApi;

	@Autowired
	private CourseApi courseApi;

	@Autowired
	private NewsApi newsApi;

	@Autowired
	private GradeApi gradeApi;

	@Autowired
	private AuthorApi authorApi;

	/**
	 * {点击头部搜索请求的地址}
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public String search(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("q") String queryString, @RequestParam("qt") String queryType, Page page) throws UnsupportedEncodingException {
		// 查询条件不能为空
		SearchResult searchResult = null;
		if (page == null) {
			page = new Page();
		}
		try {
			if (StringUtils.isNotEmpty(queryString)) {
				queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
				searchResult = searchApi.search(queryString, queryType,
						page.getCurrentPage() == 0 ? 1 : page.getCurrentPage(), page.getPageSize());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		page.setRows(searchResult);
		page.setCurrentPage(Integer.valueOf(String.valueOf(searchResult.getCurPage())));
		page.setTotalRecords(Integer.valueOf(String.valueOf(searchResult.getRecordCount())));
		page.setTotalPage(Integer.valueOf(String.valueOf(searchResult.getPageCount())));
		model.addAttribute("searchResult", searchResult);
		model.addAttribute("qt", queryType);
		model.addAttribute("queryString", queryString);
		model.addAttribute("q", new String(queryString.getBytes("utf-8"), "iso8859-1"));
		return "/search/searchResult";

	}

	/**
	 * {用于初始化solr数据库的数据}
	 * 
	 */
	@RequestMapping(value = "/initData", method = RequestMethod.GET)
	@ResponseBody
	public void initData() {
		logger.info("solr数据库开始初始化......");
		courseApi.initData();
		newsApi.initData();
		gradeApi.initData();
		authorApi.initData();
		logger.info("solr数据库初始化结束......");
	}
}
