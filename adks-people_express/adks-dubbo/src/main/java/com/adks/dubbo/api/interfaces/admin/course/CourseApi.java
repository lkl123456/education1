package com.adks.dubbo.api.interfaces.admin.course;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.data.course.Adks_course_sort;
import com.adks.dubbo.commons.Page;

public interface CourseApi {

	public List<Adks_course> getCourseListByOrgCode(String orgCode);

	public Adks_course getCourseById(Integer courseId);
	public Adks_course getCourseByName(String courseName);
	//判断能否删除分类
	public boolean courseBycourseSort(Integer courseSortId); 
	
	public void deleteCourse(String courseIds,boolean isBelong);
	
	public Integer saveCourse(Adks_course course);
	
	public Page<List<Map<String, Object>>> getCourseListPage(Page<List<Map<String, Object>>> page);
	
	public void courseUpdateData(Integer courseId,Integer flag,String result);
	//同步课程分类下课程的数量
	public void updateCourseSortCourseNum(Integer courseSortId,Integer flag);
	
	//同步课程名称
	public void checkCourseNametoTabs(Integer courseId,String courseName);
	
	public boolean canDelCourse(Integer courseId);

	//三分屏解压上传阿里云
	public String zipFileToAliyun(String courseCode,Adks_course course,String courseWareFileFileName);
	
	public void deleteAuthorCourseRedis(Integer authorId,Integer oldAuthorId);
	
	/**
	 * 
	 * @Title getCourseStatisticsListPage
	 * @Description：获取课程统计分页列表
	 * @author xrl
	 * @Date 2017年6月19日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getCourseStatisticsListPage(Page<List<Map<String, Object>>> page);
	
	/**
	 * 
	 * @Title courseApi
	 * @Description:获取课程集合
	 * @author xrl
	 * @Date 2017年6月19日
	 * @param map
	 * @return
	 */
	public List<Adks_course> getCourseStatisticsList(Map<String, Object> map);
}
