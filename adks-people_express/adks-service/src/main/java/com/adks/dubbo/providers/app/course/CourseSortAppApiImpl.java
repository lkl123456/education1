package com.adks.dubbo.providers.app.course;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.course.Adks_course_sort;
import com.adks.dubbo.api.interfaces.app.course.CourseSortAppApi;
import com.adks.dubbo.service.app.course.CourseSortAppService;

public class CourseSortAppApiImpl implements CourseSortAppApi {

	@Autowired
	private CourseSortAppService courseSortService;

	@Override
	public List<Map<String, Object>> getCourseSort() {
		return courseSortService.getCourseSortByParent(0);
	}

	@Override
	public List<Map<String, Object>> getCourseSortByParent(int parentId) {
		return courseSortService.getCourseSortByParent(parentId);
	}

	@Override
	public Adks_course_sort getCourseSortById(int id, int orgId) {
		return courseSortService.getCourseSortById(id, orgId);
	}

}
