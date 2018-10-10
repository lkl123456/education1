package com.adks.dubbo.api.interfaces.admin.course;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.course.Adks_course_sort;
import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.commons.Page;

public interface CourseUserApi {

	/**
	 * 
	 * @Title getCourseUserByUserIdAndCourseId
	 * @Description
	 * @author xrl
	 * @Date 2017年6月3日
	 * @param map
	 * @return
	 */
	public Map<String, Object> getCourseUserByUserIdAndCourseId(Map<String, Object> map);
	
	/**
	 * 
	 * @Title updateCourseUserForGrades
	 * @Description
	 * @author xrl
	 * @Date 2017年6月3日
	 * @param map
	 */
	public void updateCourseUserForGrades(Map<String, Object> map);
	
	/**
	 * 
	 * @Title getCourseUserByCourseIds
	 * @Description
	 * @author xrl
	 * @Date 2017年6月4日
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getCourseUserByCourseIds(Map<String, Object> map);
	
	/**
	 * 
	 * @Title completeCourseUser
	 * @Description
	 * @author xrl
	 * @Date 2017年6月6日
	 * @param map
	 */
	public void completeCourseUser(Map<String, Object> map,Integer courseUserId);
	
}
