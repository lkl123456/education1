package com.adks.dubbo.providers.app.course;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.interfaces.app.course.CourseAppApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.app.course.CourseAppService;

public class CourseAppApiImpl implements CourseAppApi {

	@Autowired
	private CourseAppService courseService;

	/**
	 * 课程列表(激活、审核通过)
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getCourseListPage(Page<List<Map<String, Object>>> page) {
		return courseService.getCourseListPage(page);
	}

	/**
	 * 获取课程详细信息
	 */
	@Override
	public Map<String, Object> getById(int id) {
		return courseService.getCourseById(id);
	}

	@Override
	public Page<List<Map<String, Object>>> getCourseListPageByCourseName(Page<List<Map<String, Object>>> page) {
		return courseService.getCourseListPageByCourseName(page);
	}

	@Override
	public String getVideoServer() {
		return courseService.getVideoServer();
	}

	@Override
	public List<Map<String, Object>> getCourseListByAuthorId(int authorId) {
		return courseService.getCourseListByAuthorId(authorId);
	}
}
