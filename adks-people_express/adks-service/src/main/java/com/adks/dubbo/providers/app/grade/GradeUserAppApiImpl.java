package com.adks.dubbo.providers.app.grade;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.interfaces.app.grade.GradeUserAppApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.app.grade.GradeUserAppService;

public class GradeUserAppApiImpl implements GradeUserAppApi {

	@Autowired
	private GradeUserAppService gradeUserService;

	/**
	 * 根据userId获取的当前班级信息
	 * 
	 * @param page
	 * @return
	 */
	@Override
	public Page<List<Map<String, Object>>> getCurrentGradeListByUserId(Page<List<Map<String, Object>>> page) {
		return gradeUserService.getCurrentGradeListByUserId(page);
	}

	/**
	 * 根据userId获取的历史班级信息
	 * 
	 * @param page
	 * @return
	 */
	@Override
	public Page<List<Map<String, Object>>> getHistoryGradeListByUserId(Page<List<Map<String, Object>>> page) {
		return gradeUserService.getHistoryGradeListByUserId(page);
	}

	/**
	 * 根据userId和gradeId查询班级信息
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public Map<String, Object> getGradeByUserIdAndGradeId(int userId, int gradeId) {
		return gradeUserService.getGradeByUserIdAndGradeId(userId, gradeId);
	}

	/**
	 * 根据userId和gradeId获取学员排名（必修学时+选修学时）
	 * 
	 * @param userId
	 * @return
	 */
	public int getGradeOrderRequiredPeriod(int userId, int gradeId) {
		return gradeUserService.getGradeOrderRequiredPeriod(userId, gradeId);
	}

	@Override
	public List<Map<String, Object>> getGradeListByUserId(int userId) {
		return gradeUserService.getGradeListByUserId(userId);
	}

	// @Override
	// public String getUserCourseStudyLong(int userId) {
	// Map<String, Object> map =
	// gradeUserService.getUserCourseStudyLong(userId);
	// return (String) map.get("time");
	// }

}
