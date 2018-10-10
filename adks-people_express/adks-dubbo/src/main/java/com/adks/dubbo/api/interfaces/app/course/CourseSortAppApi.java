package com.adks.dubbo.api.interfaces.app.course;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.course.Adks_course_sort;

public interface CourseSortAppApi {
	/**
	 * 获取分类列表一级目录
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getCourseSort();

	/**
	 * 获取分类列表 子级目录
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Map<String, Object>> getCourseSortByParent(int parentId);

	/**
	 * 根据id查询分类信息
	 * 
	 * @param id
	 * @return
	 */
	public Adks_course_sort getCourseSortById(int id, int orgId);

}
