package com.adks.dubbo.providers.app.course;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.api.interfaces.app.course.CourseUserAppApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.app.course.CourseUserAppService;

public class CourseUserAppApiImpl implements CourseUserAppApi {

	@Autowired
	private CourseUserAppService courseUserService;

	/**
	 * 根据userId获取未学完的课程信息
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public List<Adks_course_user> getCourseListByUserId(int userId) {
		return courseUserService.getCourseListByUserId(userId);
	}

	/**
	 * 根据userId、gradeId、status、isOver获取课程数 status：1必修；2选修 isOver：1已学完；2未学完
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public int getCourseCountByStatus(Map<String, Object> map) {
		return courseUserService.getCourseCountByStatus(map);
	}

	/**
	 * 根据userId获取全部课程列表
	 * 
	 * @param page
	 * @return
	 */
	@Override
	public Page<List<Map<String, Object>>> getCoursePage(Page<List<Map<String, Object>>> page) {
		return courseUserService.getCoursePage(page);
	}

	/**
	 * 获取微课程列表
	 * 
	 * @param page
	 * @return
	 */
	@Override
	public Page<List<Map<String, Object>>> getWeiCoursePage(Page<List<Map<String, Object>>> page) {
		return courseUserService.getWeiCoursePage(page);
	}

	/**
	 * 推荐课程
	 */
	@Override
	public Page<List<Map<String, Object>>> getRecommendCoursePage(Page<List<Map<String, Object>>> page) {
		return courseUserService.getRecommendCoursePage(page);
	}

	/**
	 * 根据userId和courseId获取详细信息
	 * 
	 * @param userId
	 * @param courseId
	 * @return
	 */
	@Override
	public Adks_course_user getCourseUser(int userId, int courseId) {
		return courseUserService.getCourseUser(userId, courseId);
	}

	@Override
	public Page<List<Map<String, Object>>> getGradeCoursePage(Page<List<Map<String, Object>>> page) {
		return courseUserService.getGradeCoursePage(page);
	}

	@Override
	public Integer saveCourseUser(Adks_course_user course_user, boolean type) {
		Integer flag = 0;
		Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
		if (type)
			insertColumnValueMap.put("isCollection", 1);
		else
			insertColumnValueMap.put("isCollection", 2);
		insertColumnValueMap.put("userId", course_user.getUserId());
		insertColumnValueMap.put("userName", course_user.getUserName());
		insertColumnValueMap.put("courseId", course_user.getCourseId());
		insertColumnValueMap.put("orgId", course_user.getOrgId());

		insertColumnValueMap.put("courseName", course_user.getCourseName());
		insertColumnValueMap.put("courseCode", course_user.getCourseCode());
		insertColumnValueMap.put("courseCwType", course_user.getCourseCwType());
		insertColumnValueMap.put("courseImg", course_user.getCourseImg());
		insertColumnValueMap.put("authorId", course_user.getAuthorId());
		insertColumnValueMap.put("studyCourseTime", course_user.getStudyCourseTime());
		insertColumnValueMap.put("courseDuration", course_user.getCourseDuration());
		insertColumnValueMap.put("courseDurationLong", course_user.getCourseDurationLong());
		insertColumnValueMap.put("studyAllTimeLong", course_user.getStudyAllTimeLong());
		insertColumnValueMap.put("studyAllTime", course_user.getStudyAllTime());
		insertColumnValueMap.put("lastStudyDate", course_user.getLastStudyDate());
		insertColumnValueMap.put("lastPosition", course_user.getLastPosition());
		insertColumnValueMap.put("isOver", course_user.getIsOver());
		insertColumnValueMap.put("gradeId", course_user.getGradeId());
		insertColumnValueMap.put("gradeName", course_user.getGradeName());
		insertColumnValueMap.put("xkdate", course_user.getXkDate());
		if (course_user.getCourseUserId() != null) {
			Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
			updateWhereConditionMap.put("courseUserId", course_user.getCourseUserId());
			flag = courseUserService.update(insertColumnValueMap, updateWhereConditionMap);
		} else {
			flag = courseUserService.insert(insertColumnValueMap);
		}
		return flag;
	}

	@Override
	public Page<List<Map<String, Object>>> getHistoryGradeCoursePage(Page<List<Map<String, Object>>> page) {
		return courseUserService.getHistoryGradeCoursePage(page);
	}

	@Override
	public Page<List<Map<String, Object>>> getAllGradeCoursePage(Page<List<Map<String, Object>>> page) {
		return courseUserService.getAllGradeCoursePage(page);
	}
}
