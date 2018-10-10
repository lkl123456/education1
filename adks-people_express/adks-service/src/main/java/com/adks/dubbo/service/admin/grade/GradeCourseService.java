package com.adks.dubbo.service.admin.grade;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.commons.util.RedisConstant;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.grade.Adks_grade_course;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.grade.GradeCourseDao;
import com.adks.dubbo.util.CourseRedisUtil;
import com.alibaba.fastjson.JSONObject;
/**
 * 
 * ClassName GradeCourseService
 * @Description：班级课程
 * @author xrl
 * @Date 2017年3月31日
 */
@Service
public class GradeCourseService extends BaseService<GradeCourseDao> {

	@Autowired
	private GradeCourseDao gradeCourseDao;
	
	@Override
	protected GradeCourseDao getDao() {
		return gradeCourseDao;
	}
	
	/**
	 * 
	 * @Title getGradeCourseListPage
	 * @Description：班级已选课程分页列表
	 * @author xrl
	 * @Date 2017年3月31日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeCourseListPage(Page<List<Map<String, Object>>> page){
		return gradeCourseDao.getGradeCourseListPage(page);
	}
	
	/**
	 * 
	 * @Title getCourseListPage
	 * @Description：班级未选课程分页列表
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getCourseListPage(Page<List<Map<String, Object>>> page){
		return gradeCourseDao.getCourseListPage(page);
	}
	
	/**
	 * 
	 * @Title getCourseByCourse
	 * @Description:根据courseId获取课程信息
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param courseId
	 * @return
	 */
	public Map<String, Object> getCourseByCourseId(Integer courseId){
		return gradeCourseDao.getCourseByCourseId(courseId);
	}
	
	/**
	 * 
	 * @Title removeGradeCourse
	 * @Description：移除班级课程
	 * @author xrl
	 * @Date 2017年3月31日
	 * @param map
	 */
	public void removeGradeCourse(Map<String, Object> map){
		gradeCourseDao.removeGradeCourse(map);
	}
	
	/**
	 * 
	 * @Title delGradeCourseByGradeId
	 * @Description：根据gradeId删除班级课程
	 * @author xrl
	 * @Date 2017年4月27日
	 * @param gradeId
	 */
	public void delGradeCourseByGradeId(Integer gradeId){
		gradeCourseDao.delGradeCourseByGradeId(gradeId);
	}
	
	/**
	 * 
	 * @Title setGradeCoursesType
	 * @Description：设置班级课程类型（选修/必修）
	 * @author xrl
	 * @Date 2017年4月27日
	 * @param map
	 */
	public void setGradeCoursesType(Map<String, Object> map){
		gradeCourseDao.setGradeCoursesType(map);
	}
	
	public void deleteGradeCourseRedis(String redisType){
		CourseRedisUtil.emptyCourse(redisType);
	}
	/**
	 * 
	 * @Title getGradeCourseNum
	 * @Description：获取班级课程数量
	 * @author xrl
	 * @Date 2017年5月22日
	 * @param map
	 * @return
	 */
	public Map<String, Object> getGradeCourseNum(Map<String, Object> map){
		return gradeCourseDao.getGradeCourseNum(map);
	}
	
	/**
	 * 
	 * @Title getGradeCourseCredits
	 * @Description
	 * @author xrl
	 * @Date 2017年6月12日
	 * @param map
	 * @return
	 */
	public Double getGradeCourseCredits(Map<String, Object> map){
		return gradeCourseDao.getGradeCourseCredits(map);
	}
	
	/**
	 * 
	 * @Title getGradeCourseCredit
	 * @Description
	 * @author xrl
	 * @Date 2017年6月12日
	 * @param map
	 * @return
	 */
	public Double getGradeCourseCredit(Map<String, Object> map){
		return gradeCourseDao.getGradeCourseCredit(map);
	}
	
	/**
	 * 
	 * @Title getGradeUserCoursePage
	 * @Description：获取班级学员课程分页列表
	 * @author xrl
	 * @Date 2017年6月15日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeUserCoursePage(Page<List<Map<String, Object>>> page){
		return gradeCourseDao.getGradeUserCoursePage(page);
	}
	
	/**
	 * 
	 * @Title getGradeCourseByGradeIdAndCourseId
	 * @Description:根据gradeId和courseId获取班级课程信息
	 * @author xrl
	 * @Date 2017年6月15日
	 * @param map
	 * @return
	 */
	public Map<String, Object> getGradeCourseByGradeIdAndCourseId(Map<String, Object> map){
		return gradeCourseDao.getGradeCourseByGradeIdAndCourseId(map);
	}
	
	/**
	 * 
	 * @Title getGradeUserCourseList
	 * @Description：获取班级学员学课信息
	 * @author xrl
	 * @Date 2017年6月16日
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getGradeUserCourseList(Map<String, Object> map){
		return gradeCourseDao.getGradeUserCourseList(map);
	}
}
