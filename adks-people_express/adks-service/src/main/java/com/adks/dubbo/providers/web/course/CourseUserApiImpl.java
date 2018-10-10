package com.adks.dubbo.providers.web.course;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.api.data.course.CourseJson;
import com.adks.dubbo.api.interfaces.web.course.CourseUserApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.web.course.CourseUserWebService;

public class CourseUserApiImpl implements CourseUserApi {

    @Autowired
    private CourseUserWebService courseUserService;

    @Override
    public List<Adks_course_user> getTopCourseUserList(Map map) {
        return courseUserService.getTopCourseUserList(map);
    }

    @Override
    public String getStudyCourseTime(Integer userId,String username) {
        return courseUserService.getStudyCourseTime(userId,username);
    }

	@Override
	public Page<List<Adks_course_user>> getCourseUserByUserId(Page<List<Adks_course_user>> page) {
		return courseUserService.getCourseUserByUserId(page);
	}

	@Override
	public Page<List<Adks_course_user>> getUserCourseViewByUserId(Page<List<Adks_course_user>> page) {
		return courseUserService.getUserCourseViewByUserId(page);
	}

	@Override
	public Adks_course_user getCourseUserByGradeIdCourseIdAndUserIdforCourseJD(Map map) {
		return courseUserService.getCourseUserByGradeIdCourseIdAndUserIdforCourseJD(map);
	}

	@Override
	public List<Adks_course_user> getCourseUserByCon(Map map) {
		return courseUserService.getCourseUserByCon(map);
	}

	@Override
	public void updateUserStudySpeed(Map<String, CourseJson> courseJsonMap) {
		
		System.out.println("**2222222222222222******************************************************8");
		System.out.println("当前进入CourseUserApiImpl courseJsonMap size  is : "+courseJsonMap.size());
		System.out.println("**2222222222222222******************************************************8");
		 
		if (courseJsonMap != null && courseJsonMap.size()>0 ) {
			for (Object key : courseJsonMap.keySet()) {
				
				CourseJson courseJson=(CourseJson) courseJsonMap.get(key);
				
				courseUserService.updateUserStudySpeed(courseJson);
			}
		}
	}

	@Override
	public Adks_course_user getCourseUserByCourseIdAndUserId(Map<String, Object> map) {
		return courseUserService.getCourseUserByCourseIdAndUserId(map);
	}

	@Override
	public void updateCourseUserForGrades(Map<String, Object> map) {
		courseUserService.updateCourseUserForGrades(map);
	}
	
}
