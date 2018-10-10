package com.adks.web.controller.news;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adks.commons.util.Constants;
import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.api.data.news.Adks_news_sort;
import com.adks.dubbo.api.data.news.Adks_news_type;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.interfaces.web.news.NewsApi;
import com.adks.dubbo.api.interfaces.web.news.NewsSortApi;
import com.adks.dubbo.api.interfaces.web.org.OrgApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping({ "/cms" })
public class NewsController {
	@Autowired
	private NewsApi newsApi;
	@Autowired
	private NewsSortApi newsSortApi;
	@Autowired
	private OrgApi orgApi;

	/**
	 * @Title:
	 * @Description:
	 * @Company:通知公告首页
	 * 
	 * @Version:
	 */
	@RequestMapping(value = "/newsIndex.do")
	public String index(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model,
			String newsSortType, Integer newsSortId, Integer newsId, Integer orgId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (orgId == null) {
			orgId = 1;
		}
		map.put("orgId", orgId);
		if (newsSortId == null) {
			newsSortId = 0;
		}
		if(newsSortType!=null&&"".equals(newsSortType)){
			map.put("newsSortType", newsSortType);
		}
		List<Adks_news_sort> newsSortList = newsSortApi.getNewsSortListByCon(map);
//		List<Adks_news_type> newsTypeList = getNewsType();
//		if (newsSortList != null && newsSortList.size() > 0) {
//			for (Adks_news_type newsType : newsTypeList) {
//				List<Adks_news_type> childClList = new ArrayList<Adks_news_type>();
//				for (Adks_news_sort newsSort : newsSortList) {
//					if (newsSort.getNewsSortType() == newsType.getId()) {
//						Adks_news_type ns = new Adks_news_type();
//						ns.setId(newsSort.getNewsSortId());
//						ns.setName(newsSort.getNewsSortName());
//						ns.setOrgId(newsSort.getOrgId());
//						ns.setOrgCode(newsSort.getOrgCode());
//						ns.setOrgName(newsSort.getOrgName());
//						childClList.add(ns);
//					}
//					if (newsSort.getNewsSortType() == newsSortType && newsSortId == 0) {
//						newsSortId = newsSort.getNewsSortId();
//					}
//				}
//				newsType.setChildren(childClList);
//			}
//		}
		if (newsId != null) {
			Adks_news news = newsApi.getNewsById(newsId);
			model.addAttribute("newsHtmlAdress", news.getNewsHtmlAdress());
		}
		session.setAttribute("newsTypeList", newsSortList);

		model.addAttribute("orgId", orgId);
		model.addAttribute("mainFlag", "newsIndex");
		model.addAttribute("newsSortType", newsSortType);
		model.addAttribute("newsSortId", newsSortId);
		model.addAttribute("newsId", newsId);
		return "/index/mainIndex";
	}

	/**
	 * @Title:
	 * @Description:
	 * @Company:通知公告集合
	 * 
	 * @Version:
	 */
	@RequestMapping(value = "/newsList.do")
	public String newsList(Page page, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			Model model, Integer newsSortId, Integer orgId,String newsSortType) {
		Map map = new HashMap();
		if (orgId == null) {
			orgId = 1;
		}
		Adks_org org=orgApi.getOrgById(orgId);
		map.put("orgCode", org.getOrgCode());
		map.put("newsSortId", newsSortId);
		map.put("newsSortType", newsSortType);
		if (page == null) {
			page = new Page<List<Adks_news>>();
		}
		page.setMap(map);
		page.setPageSize(Constants.PAGE_SIZE_TEN);
		page = newsApi.getNewsListPageWeb(page);

		model.addAttribute("page", page);
		model.addAttribute("newsSortType", newsSortType);
		model.addAttribute("newsSortId", newsSortId);
		model.addAttribute("orgId", orgId);
		return "/news/newsList";
	}

	/**
	 * @Title:
	 * @Description:
	 * @Company:通知公告详情
	 * 
	 * @Version:
	 */
	@RequestMapping(value = "/newsDetail.do")
	public String newsDetail(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model,
			Integer newsId, Integer newsSortId, Integer orgId, Integer newsSortType, String newsHtmlAdress) {
		Map map = new HashMap();
		if (orgId == null) {
			orgId = 1;
		}
		/*
		 * map.put("orgId", orgId); if(newsId!=null){ Adks_news
		 * news=newsApi.getNewsById(newsId); model.addAttribute("news", news); }
		 */
		model.addAttribute("newsSortType", newsSortType);
		model.addAttribute("newsSortId", newsSortId);
		model.addAttribute("orgId", orgId);
		model.addAttribute("newsHtmlAdress", newsHtmlAdress);
		return "/news/newsDetail";
	}

	public List<Adks_news_type> getNewsType() {
		List<Adks_news_type> newsTypeList = new ArrayList<Adks_news_type>();
		Adks_news_type newsType = new Adks_news_type();
		newsType.setId(15);
		newsType.setName("图片新闻");
		newsType.setText("图片新闻");
		newsTypeList.add(newsType);
		Adks_news_type newsType1 = new Adks_news_type();
		newsType1.setId(16);
		newsType1.setName("通知公告");
		newsType1.setText("通知公告");
		newsTypeList.add(newsType1);
		Adks_news_type newsType2 = new Adks_news_type();
		newsType2.setId(17);
		newsType2.setName("培训动态");
		newsType2.setText("培训动态");
		newsTypeList.add(newsType2);
		Adks_news_type newsType3 = new Adks_news_type();
		newsType3.setId(18);
		newsType3.setName("新闻资讯");
		newsType3.setText("新闻资讯");
		newsTypeList.add(newsType3);
		return newsTypeList;
	}
}
