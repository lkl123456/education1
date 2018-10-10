package com.adks.dubbo.service.admin.course;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.commons.util.RedisConstant;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.dao.admin.course.CourseUserDao;
import com.alibaba.fastjson.JSONObject;

@Service
public class CourseUserService extends BaseService<CourseUserDao> {

	@Autowired
	private CourseUserDao courseUserDao;

	@Override
	protected CourseUserDao getDao() {
		return courseUserDao;
	}
	
	/**
	 * 
	 * @Title getCourseUserByUserIdAndCourseId
	 * @Description
	 * @author xrl
	 * @Date 2017年6月3日
	 * @param map
	 * @return
	 */
	public Map<String, Object> getCourseUserByUserIdAndCourseId(Map<String, Object> map){
		return courseUserDao.getCourseUserByUserIdAndCourseId(map);
	}
	
	/**
	 * 
	 * @Title updateCourseUserForGrades
	 * @Description
	 * @author xrl
	 * @Date 2017年6月3日
	 * @param map
	 */
	public void updateCourseUserForGrades(Map<String, Object> map){
		courseUserDao.updateCourseUserForGrades(map);
	}
	
	public List<Map<String, Object>> getCourseUserByCourseIds(Map<String, Object> map){
		return courseUserDao.getCourseUserByCourseIds(map);
	}

}
