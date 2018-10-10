package com.adks.dubbo.api.interfaces.web.grade;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.api.data.grade.Adks_grade_course;
import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.commons.Page;

/**
 * 
 * ClassName GradeApi
 * @Description：班级API
 * @author xrl
 * @Date 2017年3月22日
 */
public interface GradeCourseApi {

	//获取班级课程
	public List<Adks_grade_course> getGradeCourseByGradeId(Map map);
	
	//得到班级课程
	public Page<List<Adks_course_user>> getAllGradeCourseList(Page<List<Adks_course_user>> page);
	
	//正在学 已学完
	public Page<List<Adks_course_user>> gradeCourseUserList(Page<List<Adks_course_user>> page);

	//未开始
	public Page<List<Adks_course_user>> gradeCourseListNoStudy(Page<List<Adks_course_user>> page);
	
	/**
	 * 
	 * @Title getGradeCourseByCourseAndGradeId
	 * @Description:根据gradeId和courseId获取班级课程信息
	 * @author xrl
	 * @Date 2017年6月3日
	 * @param map
	 * @return
	 */
	public Adks_grade_course getGradeCourseByCourseAndGradeId(Map<String, Object> map);
	
}
