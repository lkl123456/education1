package com.adks.dubbo.providers.admin.course;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.api.interfaces.admin.course.CourseUserApi;
import com.adks.dubbo.service.admin.course.CourseUserService;

public class CourseUserApiImpl implements CourseUserApi {

    @Autowired
    private CourseUserService courseUserService;

	@Override
	public Map<String, Object> getCourseUserByUserIdAndCourseId(Map<String, Object> map) {
		return courseUserService.getCourseUserByUserIdAndCourseId(map);
	}

	@Override
	public void updateCourseUserForGrades(Map<String, Object> map) {
		courseUserService.updateCourseUserForGrades(map);
	}

	@Override
	public List<Map<String, Object>> getCourseUserByCourseIds(Map<String, Object> map) {
		return courseUserService.getCourseUserByCourseIds(map);
	}

	@Override
	public void completeCourseUser(Map<String, Object> map,Integer courseUserId) {
		try {
            if (courseUserId!=null) {
                Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
                updateWhereConditionMap.put("courseUserId",courseUserId);
                courseUserService.update(map,updateWhereConditionMap);
            }else{
            	courseUserService.insert(map);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}

}
