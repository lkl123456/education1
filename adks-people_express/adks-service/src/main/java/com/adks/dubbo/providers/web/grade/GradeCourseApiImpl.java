package com.adks.dubbo.providers.web.grade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.api.data.grade.Adks_grade_course;
import com.adks.dubbo.api.interfaces.web.grade.GradeCourseApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.web.grade.GradeCourseWebService;
/**
 * 
 * ClassName GradeUserApiImpl
 * @Description：班级用户API实现
 * @author xrl
 * @Date 2017年3月30日
 */
public class GradeCourseApiImpl implements GradeCourseApi {
	
	@Autowired
	private GradeCourseWebService gradeCourseService;
	
	//获取班级课程集合
	@Override
	public List<Adks_grade_course> getGradeCourseByGradeId(Map map) {
		return gradeCourseService.getGradeCourseByGradeId(map);
	}

	@Override
	public Page<List<Adks_course_user>> getAllGradeCourseList(Page<List<Adks_course_user>> page) {
		return gradeCourseService.getAllGradeCourseList(page);
	}

	@Override
	public Page<List<Adks_course_user>> gradeCourseUserList(Page<List<Adks_course_user>> page) {
		return gradeCourseService.gradeCourseUserList(page);
	}

	@Override
	public Page<List<Adks_course_user>> gradeCourseListNoStudy(Page<List<Adks_course_user>> page) {
		return gradeCourseService.gradeCourseListNoStudy(page);
	}

	@Override
	public Adks_grade_course getGradeCourseByCourseAndGradeId(Map<String, Object> map) {
		return gradeCourseService.getGradeCourseByCourseAndGradeId(map);
	}
	
	
}
