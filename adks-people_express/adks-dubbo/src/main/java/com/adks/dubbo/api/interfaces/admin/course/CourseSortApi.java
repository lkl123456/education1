package com.adks.dubbo.api.interfaces.admin.course;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.course.Adks_course_sort;
import com.adks.dubbo.commons.Page;

public interface CourseSortApi {

	/*
	 * 以下是课程分类
	 * */
	public Integer saveCourseSort(Adks_course_sort courseSort);
	
	public List<Adks_course_sort> getCourseSortByParent(Integer parentId);
	
	public Adks_course_sort getCourseSortById(Integer courseSortId);
	
	public Adks_course_sort getCourseSortByName(String courseSortName);
	
	//获取课程分类分页
	public Page<List<Map<String, Object>>> getCourseSortListPage(Page<List<Map<String, Object>>> page);
	
	public void deleteCourseSort(String courseSortCode);
	
	public void updateNameUnifed(Integer courseSortId,String courseSortName);
	//课程分类的树
	public List<Adks_course_sort> getCourseSortsListAll(Map map);
	
	//得到父id为0的课程分类
	public List<Adks_course_sort> getCourseSortParentIsZero();
}
