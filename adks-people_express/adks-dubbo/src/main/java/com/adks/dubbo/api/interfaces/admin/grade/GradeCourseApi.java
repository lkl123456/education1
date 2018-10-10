package com.adks.dubbo.api.interfaces.admin.grade;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.commons.Page;

/**
 * 
 * ClassName GradeCourseApi
 * @Description：班级课程Api
 * @author xrl
 * @Date 2017年3月31日
 */
public interface GradeCourseApi {

	/**
	 * 
	 * @Title getGradeCourseListPage
	 * @Description：获取班级已选课程分页列表
	 * @author xrl
	 * @Date 2017年3月31日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String,Object>>> getGradeCourseListPage(Page<List<Map<String,Object>>> page);
	
	/**
	 * 
	 * @Title getCourseListPage
	 * @Description：班级未选课程分页列表
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String,Object>>> getCourseListPage(Page<List<Map<String,Object>>> page);
	
	/**
	 * 
	 * @Title getCourseByCourse
	 * @Description：根据courseId获取课程信息
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param courseId
	 */
	public Map<String, Object> getCourseByCourseId(Integer courseId);
	
	/**
	 * 
	 * @Title saveGradeCourse
	 * @Description:添加班级课程
	 * @author xrl
	 * @Date 2017年4月1日
	 */
	public Integer saveGradeCourse(Map<String, Object> map);
	
	/**
	 * 
	 * @Title getGradeCourseNum
	 * @Description：获取班级选修、必修课程数量
	 * @author xrl
	 * @Date 2017年6月27日
	 * @param map
	 * @return
	 */
	public Map<String, Object> getGradeCourseNum(Map<String, Object> map);
	
	/**
	 * 
	 * @Title updateGradeCourseNum
	 * @Description:更新班级课程数量
	 * @author xrl
	 * @Date 2017年6月27日
	 * @param map
	 */
	public void updateGradeCourseNum(Map<String, Object> map);
	
	/**
	 * 
	 * @Title removeGradeCourse
	 * @Description：移除班级课程
	 * @author xrl
	 * @Date 2017年3月31日
	 * @param map
	 */
	public void removeGradeCourse(Map<String, Object> map);
	
	/**
	 * 
	 * @Title getGradeCourseByGradeId
	 * @Description：获取班级已选课程
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param gradeId
	 * @return
	 */
	public List<Map<String,Object>> getCourseIdByGradeId(Integer gradeId);
	
	/**
	 * 
	 * @Title setGradeCoursesType
	 * @Description：设置班级课程类型（选修/必修）
	 * @author xrl
	 * @Date 2017年4月27日
	 * @param map
	 */
	public void setGradeCoursesType(Map<String, Object> map);
	
	/**
	 * 
	 * @Title getGradeCourseCredits
	 * @Description：获取班级学员已获得的总学时
	 * @author xrl
	 * @Date 2017年6月12日
	 * @param map
	 * @return
	 */
	public Double getGradeCourseCredits(Map<String, Object> map);
	
	/**
	 * 
	 * @Title getGradeCourseCredit
	 * @Description:获取班级总必修/选修
	 * @author xrl
	 * @Date 2017年6月12日
	 * @param map
	 * @return
	 */
	public Double getGradeCourseCredit(Map<String, Object> map);
	
	/**
	 * 
	 * @Title getGradeUserCoursePage
	 * @Description：获取班级学员课程分页列表
	 * @author xrl
	 * @Date 2017年6月15日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeUserCoursePage(Page<List<Map<String, Object>>> page);
	
	/**
	 * 
	 * @Title getGradeCourseByGradeIdAndCourseId
	 * @Description:根据gradeId和courseId获取班级课程信息
	 * @author xrl
	 * @Date 2017年6月15日
	 * @param map
	 * @return
	 */
	public Map<String, Object> getGradeCourseByGradeIdAndCourseId(Map<String, Object> map);
	
	/**
	 * 
	 * @Title getGradeUserCourseList
	 * @Description：获取班级学员学课信息
	 * @author xrl
	 * @Date 2017年6月16日
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getGradeUserCourseList(Map<String, Object> map);
}
