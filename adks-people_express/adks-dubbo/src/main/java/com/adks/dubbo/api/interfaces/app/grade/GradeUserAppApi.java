package com.adks.dubbo.api.interfaces.app.grade;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.commons.Page;

public interface GradeUserAppApi {

	/**
	 * 根据userId获取的当前班级信息
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getCurrentGradeListByUserId(Page<List<Map<String, Object>>> page);

	/**
	 * 根据userId获取的历史班级信息
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getHistoryGradeListByUserId(Page<List<Map<String, Object>>> page);

	/**
	 * 根据userId和gradeId查询班级信息
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getGradeByUserIdAndGradeId(int userId, int gradeId);

	/**
	 * 根据userId和gradeId获取学员排名（必修学时+选修学时）
	 * 
	 * @param userId
	 * @return
	 */
	public int getGradeOrderRequiredPeriod(int userId, int gradeId);

	/**
	 * 获取用户在线累积课程学习时长
	 * 
	 * @return
	 */
	// public String getUserCourseStudyLong(int userId);

	/**
	 * 根据userId获取全部班级
	 * 
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getGradeListByUserId(int userId);
}
