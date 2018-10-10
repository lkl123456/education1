package com.adks.dubbo.api.interfaces.app.course;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.commons.Page;

public interface CourseUserAppApi {

	/**
	 * 根据userId获取未学完的课程信息
	 * 
	 * @param map
	 * @return
	 */
	public List<Adks_course_user> getCourseListByUserId(int userId);

	/**
	 * 根据userId、gradeId、status、isOver获取课程数 status：1必修；2选修 isOver：1已学完；2未学完
	 * 
	 * @param map
	 * @return
	 */
	public int getCourseCountByStatus(Map<String, Object> map);

	/**
	 * 根据userId获取课程列表
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getCoursePage(Page<List<Map<String, Object>>> page);

	/**
	 * 获取微课程列表
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getWeiCoursePage(Page<List<Map<String, Object>>> page);

	/**
	 * 推荐课程
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getRecommendCoursePage(Page<List<Map<String, Object>>> page);

	/**
	 * 根据userId和courseId获取详细信息
	 * 
	 * @param userId
	 * @param courseId
	 * @return
	 */
	public Adks_course_user getCourseUser(int userId, int courseId);

	/**
	 * 获取班级全部课程
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeCoursePage(Page<List<Map<String, Object>>> page);

	public Integer saveCourseUser(Adks_course_user course_user, boolean type);

	/**
	 * 获取历史培训班的详细信息
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getHistoryGradeCoursePage(Page<List<Map<String, Object>>> page);

	public Page<List<Map<String, Object>>> getAllGradeCoursePage(Page<List<Map<String, Object>>> page);
}
