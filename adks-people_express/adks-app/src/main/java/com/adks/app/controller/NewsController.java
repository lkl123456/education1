/**
 * 
 */
package com.adks.app.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.app.util.JsonResponse;
import com.adks.dubbo.api.data.news.Adks_news_user;
import com.adks.dubbo.api.interfaces.app.grade.GradeUserAppApi;
import com.adks.dubbo.api.interfaces.app.news.NewsUserAppApi;
import com.adks.dubbo.commons.Page;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/api/notice")
public class NewsController {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(NewsController.class);

	@Autowired
	private NewsUserAppApi newsUserAppApi;
	@Autowired
	private GradeUserAppApi gradeUserAppApi;

	/**
	 * 我的消息列表
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/listGradeNotice.do")
	@ResponseBody
	public JsonResponse listGradeNotice(String data) throws IOException {
		String error = null;

		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();
		/* 返回参数 */
		Map resultMap = new HashMap();

		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);

			int userId = MapUtils.getInteger(map, "userId");
			int gradeId = MapUtils.getInteger(map, "gradeId");

			int startIndex = MapUtils.getInteger(map, "startIndex");
			int pageSize = MapUtils.getInteger(map, "pageSize");

			Map queryMap = new HashMap();
			queryMap.put("userId", userId);
			queryMap.put("gradeId", gradeId);
			Page page = new Page();
			page.setMap(queryMap);
			page.setPageSize(pageSize);
			page.setStartLimit(startIndex);

			page = newsUserAppApi.getGradeNewsByUserId(page);
			resultMap.put("page", page);
		} else
			error = "未接受到参数";

		if (StringUtils.isNotEmpty(error)) {
			return JsonResponse.error(error);
		} else {
			return JsonResponse.success(resultMap);
		}
	}

	/**
	 * 我的消息未读数量
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/noticeCount.do")
	@ResponseBody
	public JsonResponse noticeCount(String data) throws IOException {
		String error = null;
		int num = 0;

		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();

		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);

			int userId = MapUtils.getInteger(map, "userId");
			String gradeId = MapUtils.getString(map, "gradeId");

			Map queryMap = new HashMap();
			queryMap.put("userId", userId);
			if (StringUtils.isEmpty(gradeId)) {
				List<Map<String, Object>> list = gradeUserAppApi.getGradeListByUserId(userId);
				String gradeIds = "";

				if (list != null && list.size() > 0) {
					for (Map<String, Object> map2 : list) {
						gradeIds += map2.get("gradeId") + ",";
					}
					gradeIds = gradeIds.substring(0, gradeIds.length() - 1);
					queryMap.put("gradeIds", gradeIds);
				}
			} else
				queryMap.put("gradeId", gradeId);
			if (userId != 0) {
				num = newsUserAppApi.getNoReadNews(queryMap);
			}
		} else
			error = "未接受到参数";

		if (StringUtils.isNotEmpty(error)) {
			return JsonResponse.error(error);
		} else {
			return JsonResponse.success(num);
		}
	}

	/**
	 * 我的消息列表
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/listNotice.do")
	@ResponseBody
	public JsonResponse listNotice(String data) throws IOException {
		String error = null;

		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();
		/* 返回参数 */
		Map resultMap = new HashMap();

		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);

			int userId = MapUtils.getInteger(map, "userId");
			int noticeType = MapUtils.getInteger(map, "noticeType");

			int startIndex = MapUtils.getInteger(map, "startIndex");
			int pageSize = MapUtils.getInteger(map, "pageSize");

			List<Map<String, Object>> list = gradeUserAppApi.getGradeListByUserId(userId);
			String gradeIds = "";

			if (list != null && list.size() > 0) {
				for (Map<String, Object> map2 : list) {
					gradeIds += map2.get("gradeId") + ",";
				}
				gradeIds = gradeIds.substring(0, gradeIds.length() - 1);

				Map queryMap = new HashMap();
				queryMap.put("userId", userId);
				queryMap.put("gradeIds", gradeIds);
				Page page = new Page();
				page.setMap(queryMap);
				page.setPageSize(pageSize);
				page.setStartLimit(startIndex);

				if (noticeType == 1) {// 系统消息
					page = newsUserAppApi.getSystemNews(page);
				} else {// 班级消息
					page = newsUserAppApi.getGradeNewsByUserId(page);
				}
				resultMap.put("page", page);
			} else {
				error = "暂无数据！";
			}

		} else
			error = "未接受到参数";

		if (StringUtils.isNotEmpty(error)) {
			return JsonResponse.error(error);
		} else {
			return JsonResponse.success(resultMap);
		}
	}

	/**
	 * 消息详情
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/detailNotice.do")
	@ResponseBody
	public JsonResponse detailNotice(String data) throws IOException {
		String error = null;

		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();

		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);

			int userId = MapUtils.getInteger(map, "userId");
			int newsId = MapUtils.getInteger(map, "noticeId");
			int gradeId = MapUtils.getInteger(map, "gradeId");

			Adks_news_user news_user = newsUserAppApi.getNewsInfo(newsId, userId, gradeId);
			if (news_user == null) {
				news_user = new Adks_news_user();
				news_user.setNewsId(newsId);
				news_user.setUserId(userId);
				news_user.setGradeId(gradeId);
				news_user.setIsRead(1);
				news_user.setCreateDate(new Date());
				newsUserAppApi.saveNewsIsRead(news_user);
			}
		} else
			error = "未接受到参数";

		if (StringUtils.isNotEmpty(error)) {
			return JsonResponse.error(error);
		} else {
			return JsonResponse.success("");
		}
	}
}
