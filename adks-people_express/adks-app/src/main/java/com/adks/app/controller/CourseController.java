package com.adks.app.controller;

import java.io.IOException;
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
import com.adks.dubbo.api.data.author.Adks_author;
import com.adks.dubbo.api.data.course.Adks_course_sort;
import com.adks.dubbo.api.interfaces.app.author.AuthorAppApi;
import com.adks.dubbo.api.interfaces.app.course.CourseAppApi;
import com.adks.dubbo.api.interfaces.app.course.CourseSortAppApi;
import com.adks.dubbo.api.interfaces.app.course.CourseUserAppApi;
import com.adks.dubbo.api.interfaces.app.news.NewsAppApi;
import com.adks.dubbo.api.interfaces.app.user.UserAppApi;
import com.adks.dubbo.commons.Page;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/api/course")
public class CourseController {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(CourseController.class);

	@Autowired
	private AuthorAppApi authorAppApi;
	@Autowired
	private CourseAppApi courseAppApi;
	@Autowired
	private CourseSortAppApi courseSortAppApi;
	@Autowired
	private CourseUserAppApi courseUserAppApi;
	@Autowired
	private NewsAppApi newsAppApi;
	@Autowired
	private UserAppApi userAppApi;

	/**
	 * 首页
	 *
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/index.do")
	@ResponseBody
	public JsonResponse index(String data) throws IOException {
		String error = null;
		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();
		/* 返回参数 */
		Map resultMap = new HashMap();
		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);
			int userId = MapUtils.getInteger(map, "userId");
			Map<String, Object> uMap = userAppApi.getUserByUserId(userId);
			Map queryMap = new HashMap();
			if (uMap != null && uMap.size() > 0) {

				String orgId = MapUtils.getString(uMap, "orgCode").split("A")[1];

				queryMap.put("orgId", orgId);
				queryMap.put("newsSortType", 15);// 图片新闻
				queryMap.put("num", 3);
			}
			// ##首页焦点图
			List<Map<String, Object>> listMap = newsAppApi.getNews(queryMap);
			resultMap.put("tpxwList", listMap);
		}

		if (StringUtils.isNotEmpty(error))
			return JsonResponse.error(error);
		return JsonResponse.success(resultMap);
	}

	/**
	 * 微、精彩视频;微1精彩2
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getCourseListByCwType.do")
	@ResponseBody
	public JsonResponse getCourseListByCwType(String data) throws IOException {
		String error = null;
		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();
		/* 返回参数 */
		Map resultMap = new HashMap();

		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);

			int userId = MapUtils.getInteger(map, "userId");
			int cwType = MapUtils.getInteger(map, "cwType");

			int startIndex = MapUtils.getInteger(map, "startIndex");
			int pageSize = MapUtils.getInteger(map, "pageSize");

			Map queryMap = new HashMap();
			Map<String, Object> uMap = userAppApi.getUserByUserId(userId);
			if (uMap != null && uMap.size() > 0) {
				String orgId = MapUtils.getString(uMap, "orgCode").split("A")[1];
				queryMap.put("orgId", orgId);
			}
			queryMap.put("userId", userId);

			Page page = new Page();
			page.setMap(queryMap);
			page.setPageSize(pageSize);
			page.setStartLimit(startIndex);
			if (cwType == 1)/* 微视频 */
				page = courseUserAppApi.getWeiCoursePage(page);
			else {/* 精彩视频 */
				page = courseUserAppApi.getRecommendCoursePage(page);
			}
			resultMap.put("page", page);
		} else
			error = "未接受到参数";
		if (StringUtils.isNotEmpty(error))
			return JsonResponse.error(error);
		return JsonResponse.success(resultMap);
	}

	/**
	 * 获取课程主分类
	 * 
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getCourseCatalog.do")
	@ResponseBody
	public JsonResponse getCourseCatalog(String data) throws IOException {
		/* 返回参数 */
		Map resultMap = new HashMap();
		if (StringUtils.isNotEmpty(data)) {

			/* 获取一级分类列表 */
			List<Map<String, Object>> sorts = courseSortAppApi.getCourseSort();
			if (sorts != null && sorts.size() > 0)
				resultMap.put("courseCatalogList", sorts);
			else
				resultMap.put("courseCatalogList", "");
		}

		return JsonResponse.success(resultMap);
	}

	/**
	 * 获取课程的子分类
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getCourseChildCatalog.do", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResponse getCourseChildCatalog(String data) throws IOException {
		String error = null;
		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();
		/* 返回参数 */
		Map resultMap = new HashMap();

		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);
			/* 父级课程id */
			int parentId = MapUtils.getInteger(map, "parentId");
			/* 子级课程列表 */
			List<Map<String, Object>> sorts = courseSortAppApi.getCourseSortByParent(parentId);
			resultMap.put("courseChildCatalogList", sorts);
		} else
			error = "未接受到参数";
		if (StringUtils.isNotEmpty(error))
			return JsonResponse.error(error);
		return JsonResponse.success(resultMap);
	}

	/**
	 * 获取课程分类下的全部课程列表
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getCoursesList.do", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResponse getCoursesList(String data) throws IOException {
		String error = null;
		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();
		/* 返回参数 */
		Map resultMap = new HashMap();

		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);
			/* 用户id */
			int userId = MapUtils.getInteger(map, "userId");
			/* 课程分类id */
			int courseSortId = MapUtils.getInteger(map, "courseCatalogId");

			int startIndex = MapUtils.getInteger(map, "startIndex");
			int pageSize = MapUtils.getInteger(map, "pageSize");

			Map queryMap = new HashMap();
			queryMap.put("userId", userId);

			Map<String, Object> uMap = userAppApi.getUserByUserId(userId);
			int orgId = 0;
			if (uMap != null && uMap.size() > 0) {
				orgId = Integer.valueOf(MapUtils.getString(uMap, "orgCode").split("A")[1]);
				queryMap.put("orgId", orgId);
			}

			/* 根据分类id获取分类code */
			if (courseSortId != 0) {
				Adks_course_sort sort = courseSortAppApi.getCourseSortById(courseSortId, orgId);
				if (sort != null) {
					queryMap.put("courseSortCode", sort.getCourseSortCode());
				}
			}

			Page page = new Page();
			page.setMap(queryMap);
			page.setPageSize(pageSize);
			page.setStartLimit(startIndex);
			/* 根据条件查询全部课程 */
			page = courseAppApi.getCourseListPage(page);
			resultMap.put("page", page);
		} else
			error = "未接受到参数";
		if (StringUtils.isNotEmpty(error))
			return JsonResponse.error(error);
		return JsonResponse.success(resultMap);
	}

	/**
	 * 搜索数据
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getIndexQueryCoursesList.do", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResponse getIndexQueryCoursesList(String data) throws IOException {
		String error = null;
		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();
		/* 返回参数 */
		Map resultMap = new HashMap();
		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);
			/* 解析map */
			String searchKeyValue = MapUtils.getString(map, "searchKeyValue");
			int startIndex = MapUtils.getInteger(map, "startIndex");
			int pageSize = MapUtils.getInteger(map, "pageSize");
			int userId = MapUtils.getInteger(map, "u");
			String courseId = MapUtils.getString(map, "courseId");
			Map queryMap = new HashMap();

			Map<String, Object> uMap = userAppApi.getUserByUserId(userId);
			if (uMap != null && uMap.size() > 0) {
				queryMap.put("userId", userId);
				String orgId = MapUtils.getString(uMap, "orgCode").split("A")[1];
				queryMap.put("orgId", orgId);
			}

			queryMap.put("courseName", searchKeyValue);
			if (StringUtils.isNotEmpty(courseId)) {
				queryMap.put("courseId", courseId);
			}

			Page page = new Page();
			page.setMap(queryMap);
			page.setPageSize(pageSize);
			page.setStartLimit(startIndex);

			/* 根据名称模糊查询课程 */
			page = courseAppApi.getCourseListPageByCourseName(page);
			resultMap.put("page", page);
		} else
			error = "未接受到参数";
		if (StringUtils.isNotEmpty(error))
			return JsonResponse.error(error);
		return JsonResponse.success(resultMap);
	}

	/**
	 * 讲师基本信息
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAuthorIntroductionAndCourse.do", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResponse getAuthorIntroductionAndCourse(String data) throws IOException {
		String error = null;
		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();
		/* 返回参数 */
		Map resultMap = new HashMap();
		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);
			int authorId = MapUtils.getInteger(map, "authorId");
			/* 讲师详细 */
			Adks_author author = authorAppApi.getAuthorById(authorId);
			resultMap.put("author", author);
			/* 讲师课程 */
			List<Map<String, Object>> course_users = courseAppApi.getCourseListByAuthorId(authorId);
			resultMap.put("authorCourseList", course_users);
		} else
			error = "未接受到参数";
		if (StringUtils.isNotEmpty(error))
			return JsonResponse.error(error);
		return JsonResponse.success(resultMap);
	}
}
