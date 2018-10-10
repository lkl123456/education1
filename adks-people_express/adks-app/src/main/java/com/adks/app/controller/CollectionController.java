package com.adks.app.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.app.util.JsonResponse;
import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.api.data.user.Adks_usercollection;
import com.adks.dubbo.api.interfaces.app.course.CourseAppApi;
import com.adks.dubbo.api.interfaces.app.course.CourseUserAppApi;
import com.adks.dubbo.api.interfaces.app.user.UserAppApi;
import com.adks.dubbo.api.interfaces.app.user.UserCollectionAppApi;
import com.adks.dubbo.commons.Page;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/api/collection")
public class CollectionController {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(CollectionController.class);

	@Autowired
	private CourseAppApi courseAppApi;
	@Autowired
	private UserCollectionAppApi userCollectionAppApi;
	@Autowired
	private CourseUserAppApi courseUserAppApi;
	@Autowired
	private UserAppApi userAppApi;

	/**
	 * 获取收藏的全部课程
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listCollection.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse listCollection(String data) throws IOException {
		String error = null;
		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();
		/* 返回参数 */
		Map resultMap = new HashMap();

		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);
			/* 用户id */
			int userId = MapUtils.getInteger(map, "userId");

			int startIndex = MapUtils.getInteger(map, "startIndex");
			int pageSize = MapUtils.getInteger(map, "pageSize");

			map.put("userId", userId);

			Page page = new Page();
			page.setMap(map);
			page.setPageSize(pageSize);
			page.setStartLimit(startIndex);
			/* 根据条件查询全部课程 */
			page = userCollectionAppApi.getCollectionPage(page);

			resultMap.put("page", page);
		} else
			error = "未接受到参数";
		if (StringUtils.isNotEmpty(error))
			return JsonResponse.error(error);
		return JsonResponse.success(resultMap);
	}

	/**
	 * 添加收藏
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/addCollection.do")
	@ResponseBody
	public JsonResponse addCollection(String data) throws IOException {
		String error = null;
		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();
		/* 返回参数 */
		Map resultMap = new HashMap();

		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);

			int userId = MapUtils.getInteger(map, "userId");
			int courseId = MapUtils.getInteger(map, "courseId");

			if (courseId != 0) {
				Map<String, Object> cMap = courseAppApi.getById(courseId);
				Adks_course_user course_user = courseUserAppApi.getCourseUser(userId, courseId);
				if (cMap != null) {
					Map<String, Object> ucMap = userCollectionAppApi.getByUCId(userId, courseId);
					if (ucMap != null && ucMap.size() > 0) {
						error = "已收藏";
					} else {
						Adks_usercollection usercollection = new Adks_usercollection();
						usercollection.setUserId(userId);
						usercollection.setCourseId(courseId);
						usercollection.setCourseName(MapUtils.getString(cMap, "courseName"));
						usercollection.setCourseCode(MapUtils.getString(cMap, "courseCode"));
						usercollection.setAuthorId(MapUtils.getInteger(cMap, "authorId"));
						usercollection.setAuthorName(MapUtils.getString(cMap, "authorName"));
						usercollection.setCreateDate(new Date());
						usercollection.setPublishDate(new Date());
						usercollection.setCourseDuration(MapUtils.getInteger(cMap, "courseDuration"));
						usercollection.setCourseType(MapUtils.getInteger(cMap, "courseType"));
						usercollection.setCourseImg(MapUtils.getString(cMap, "coursePic"));
						usercollection.setCourseSortName(MapUtils.getString(cMap, "courseSortName"));
						usercollection.setCourseTimeLong(MapUtils.getString(cMap, "courseTimeLong"));
						if (course_user == null) {
							course_user = new Adks_course_user();
							course_user.setUserId(userId);
							Map<String, Object> uMap = userAppApi.getUserByUserId(userId);
							course_user.setUserName(MapUtils.getString(uMap, "userName"));
							course_user.setCourseId(courseId);
							course_user.setCourseName(MapUtils.getString(cMap, "courseName"));
							course_user.setCourseCode(MapUtils.getString(cMap, "courseCode"));
							course_user.setCourseCwType(MapUtils.getInteger(cMap, "courseType"));
							course_user.setCourseImg(MapUtils.getString(cMap, "coursePic"));
							course_user.setAuthorId(MapUtils.getInteger(cMap, "authorId"));
							course_user.setAuthorName(MapUtils.getString(cMap, "authorName"));
							course_user.setCourseDuration(MapUtils.getInteger(cMap, "courseDuration"));
							course_user.setIsOver(2);
							course_user.setXkDate(new Date());
						}
						int rCode = userCollectionAppApi.saveUserCollection(usercollection);
						System.out.println(rCode);
						if (rCode >= 1) {
							courseUserAppApi.saveCourseUser(course_user, true);
							resultMap.put("message", "收藏成功");
						} else
							error = "收藏失败";
					}
				}
			}
		} else
			error = "未接受到参数";
		if (StringUtils.isNotEmpty(error))
			return JsonResponse.error(error);
		return JsonResponse.success(resultMap);
	}

	/**
	 * 取消收藏
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/deleteCollection.do")
	@ResponseBody
	public JsonResponse deleteCollection(String data) throws IOException {
		String error = null;
		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();
		/* 返回参数 */
		Map resultMap = new HashMap();

		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);

			int userId = MapUtils.getInteger(map, "userId");
			String courseIds = MapUtils.getString(map, "courseId");
			if (StringUtils.isNotEmpty(courseIds)) {
				String[] cids = courseIds.split(";");
				if (cids != null && cids.length > 0) {
					for (int i = 0; i < cids.length; i++) {
						int courseId = Integer.valueOf(cids[i]);
						Map<String, Object> rMap = userCollectionAppApi.getByUCId(userId, courseId);
						Adks_course_user course_user = courseUserAppApi.getCourseUser(userId, courseId);
						if (rMap != null && rMap.size() > 0) {
							courseUserAppApi.saveCourseUser(course_user, false);
							int rCode = userCollectionAppApi.deleteCollection(MapUtils.getInteger(rMap, "userConId"));
							if (rCode > 0)
								resultMap.put("succ", "已取消收藏该课程");
							else
								resultMap.put("succ", "取消收藏课程失败");
						} else
							resultMap.put("succ", "该课程还未收藏");
					}
				}
			}
		} else
			error = "未接受到参数";
		if (StringUtils.isNotEmpty(error))
			return JsonResponse.error(error);
		return JsonResponse.success(resultMap);
	}
}
