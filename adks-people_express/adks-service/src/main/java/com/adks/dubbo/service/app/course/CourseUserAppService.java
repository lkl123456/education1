package com.adks.dubbo.service.app.course;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.app.course.CourseUserAppDao;

@Service
public class CourseUserAppService extends BaseService<CourseUserAppDao> {

	@Autowired
	private CourseUserAppDao courseUserDao;

	@Override
	protected CourseUserAppDao getDao() {
		return courseUserDao;
	}

	/**
	 * 根据userId获取未学完的课程信息
	 * 
	 * @param map
	 * @return
	 */
	public List<Adks_course_user> getCourseListByUserId(int userId) {
		return courseUserDao.getCourseListByUserId(userId);
	}

	/**
	 * 根据userId、gradeId、status、isOver获取课程数 status：1必修；2选修 isOver：1已学完；2未学完
	 * 
	 * @param map
	 * @return
	 */
	public int getCourseCountByStatus(Map<String, Object> map) {
		return courseUserDao.getCourseCountByStatus(map);
	}

	/**
	 * 根据userId获取全部课程列表
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getCoursePage(Page<List<Map<String, Object>>> page) {
		return courseUserDao.getCoursePage(page);
	}

	/**
	 * 获取微课程列表
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getWeiCoursePage(Page<List<Map<String, Object>>> page) {
		return courseUserDao.getWeiCoursePage(page);
	}

	/**
	 * 推荐课程
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getRecommendCoursePage(Page<List<Map<String, Object>>> page) {
		return courseUserDao.getRecommendCoursePage(page);
	}

	/**
	 * 根据userId和courseId获取详细信息
	 * 
	 * @param userId
	 * @param courseId
	 * @return
	 */
	public Adks_course_user getCourseUser(int userId, int courseId) {
		return courseUserDao.getCourseUser(userId, courseId);
	}

	/**
	 * 获取班级全部课程
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeCoursePage(Page<List<Map<String, Object>>> page) {
		return courseUserDao.getGradeCoursePage(page);
	}

	/**
	 * 获取历史培训班的详细信息
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getHistoryGradeCoursePage(Page<List<Map<String, Object>>> page) {
		return courseUserDao.getHistoryGradeCoursePage(page);
	}

	public Page<List<Map<String, Object>>> getAllGradeCoursePage(Page<List<Map<String, Object>>> page) {
		return courseUserDao.getAllGradeCoursePage(page);
	}
}
