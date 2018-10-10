package com.adks.dubbo.providers.admin.grade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.interfaces.admin.grade.GradeCourseApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.grade.GradeCourseService;
import com.adks.dubbo.service.admin.grade.GradeService;

/**
 * 
 * ClassName GradeCourseApiImpl
 * @Description：班级课程Api实现
 * @author xrl
 * @Date 2017年3月31日
 */
public class GradeCourseApiImpl implements GradeCourseApi {

	@Autowired
	private GradeCourseService gradeCourseService;
	@Autowired
	private GradeService gradeService;
	
	/**
	 * 
	 * @Title getGradeCourseListPage
	 * @Description：班级已选课程分页列表
	 * @author xrl
	 * @Date 2017年3月31日
	 * @param page
	 * @return
	 */
	@Override
	public Page<List<Map<String, Object>>> getGradeCourseListPage(Page<List<Map<String, Object>>> page) {
		return gradeCourseService.getGradeCourseListPage(page);
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
	@Override
	public Page<List<Map<String, Object>>> getCourseListPage(Page<List<Map<String, Object>>> page) {
		return gradeCourseService.getCourseListPage(page);
	}
	
	/**
	 * 
	 * @Title getCourseByCourse
	 * @Description：根据courseId获取课程信息
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param courseId
	 * @return
	 */
	public Map<String, Object> getCourseByCourseId(Integer courseId){
		return gradeCourseService.getCourseByCourseId(courseId);
	}
	
	/**
	 * 
	 * @Title saveGradeCourse
	 * @Description：
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param map
	 */
	public Integer saveGradeCourse(Map<String,Object> insertColumnValueMap){
		//添加班级课程
		Integer gradeCourseId=gradeCourseService.insert(insertColumnValueMap);
//		//获取班级选修、必修课程数量
//		Map<String, Object> gradeCourseNum=gradeCourseService.getGradeCourseNum(insertColumnValueMap);
//		//更新班级课程数量
//		gradeService.updateGradeCourseNum(gradeCourseNum);
		//清除专题培训课程，培训班的课程
		String gradeId=insertColumnValueMap.get("gradeId")+"";
		gradeCourseService.deleteGradeCourseRedis(gradeId+"-register_grade_course");
		return gradeCourseId;
	}
	
	/**
	 * 
	 * @Title getGradeCourseNum
	 * @Description：获取班级选修、必修课程数量
	 * @author xrl
	 * @Date 2017年6月27日
	 * @param map
	 * @return
	 */
	public Map<String, Object> getGradeCourseNum(Map<String, Object> map){
		return gradeCourseService.getGradeCourseNum(map);
	}
	
	/**
	 * 
	 * @Title updateGradeCourseNum
	 * @Description:更新班级课程数量
	 * @author xrl
	 * @Date 2017年6月27日
	 * @param map
	 */
	public void updateGradeCourseNum(Map<String, Object> map){
		//更新班级课程数量
		gradeService.updateGradeCourseNum(map);
	}

	/**
	 * 
	 * @Title removeGradeCourse
	 * @Description：移除班级课程
	 * @author xrl
	 * @Date 2017年3月31日
	 * @param map
	 */
	@Override
	public void removeGradeCourse(Map<String, Object> map) {
		String gradeId=map.get("gradeId")+"";
		//移除班级课程
		gradeCourseService.removeGradeCourse(map);
//		//获取班级选修、必修课程数量
//		Map<String, Object> gradeCourseNum=gradeCourseService.getGradeCourseNum(map);
//		//更新班级课程数量
//		gradeService.updateGradeCourseNum(gradeCourseNum);
		//清除专题培训课程，培训班的课程
		gradeCourseService.deleteGradeCourseRedis(gradeId+"-register_grade_course");
	}
	
	/**
	 * 
	 * @Title getGradeCourseByGradeId
	 * @Description：获取班级已选课程
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param gradeId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getCourseIdByGradeId(Integer gradeId) {
		Map<String,Object> queryColumnValueMap = new HashMap<>();
		queryColumnValueMap.put("gradeId", gradeId);
		List<String> returnList = new ArrayList<>();
		returnList.add("courseId");
		return gradeCourseService.query(queryColumnValueMap,returnList,null);
	}

	/**
	 * 
	 * @Title setGradeCoursesType
	 * @Description：设置班级课程类型（选修/必修）
	 * @author xrl
	 * @Date 2017年4月27日
	 * @param map
	 */
	@Override
	public void setGradeCoursesType(Map<String, Object> map) {
		//修改课程必修、选修状态
		gradeCourseService.setGradeCoursesType(map);
//		//获取班级选修、必修课程数量
//		Map<String, Object> gradeCourseNum=gradeCourseService.getGradeCourseNum(map);
//		//更新班级课程数量
//		gradeService.updateGradeCourseNum(gradeCourseNum);
		//更新班级集合redis
		//gradeCourseService.updateGradeCourseRedis(String.valueOf(map.get("gradeId")));
	}

	@Override
	public Double getGradeCourseCredits(Map<String, Object> map) {
		return gradeCourseService.getGradeCourseCredits(map);
	}

	@Override
	public Double getGradeCourseCredit(Map<String, Object> map) {
		return gradeCourseService.getGradeCourseCredit(map);
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
	@Override
	public Page<List<Map<String, Object>>> getGradeUserCoursePage(Page<List<Map<String, Object>>> page) {
		return gradeCourseService.getGradeUserCoursePage(page);
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
	@Override
	public Map<String, Object> getGradeCourseByGradeIdAndCourseId(Map<String, Object> map) {
		return gradeCourseService.getGradeCourseByGradeIdAndCourseId(map);
	}

	@Override
	public List<Map<String, Object>> getGradeUserCourseList(Map<String, Object> map) {
		return gradeCourseService.getGradeUserCourseList(map);
	}

}
