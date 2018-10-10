package com.adks.dubbo.service.app.grade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.app.grade.GradeUserAppDao;
import com.alibaba.fastjson.JSONObject;

@Service
public class GradeUserAppService extends BaseService<GradeUserAppDao> {
	@Autowired
	private GradeUserAppDao gradeUserDao;

	@Override
	protected GradeUserAppDao getDao() {
		return gradeUserDao;
	}

	/**
	 * 根据userId获取的当前班级信息
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getCurrentGradeListByUserId(Page<List<Map<String, Object>>> page) {
		return gradeUserDao.getCurrentGradeListByUserId(page);
	}

	/**
	 * 根据userId获取的历史班级信息
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getHistoryGradeListByUserId(Page<List<Map<String, Object>>> page) {
		return gradeUserDao.getHistoryGradeListByUserId(page);
	}

	/**
	 * 根据userId和gradeId查询班级信息
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getGradeByUserIdAndGradeId(int userId, int gradeId) {
		Map<String, Object> map = null;
		map = gradeUserDao.getGradeByUserIdAndGradeId(userId, gradeId);
		return map;
	}

	/**
	 * 根据userId和gradeId获取学员排名（必修学时+选修学时）
	 * 
	 * @param userId
	 * @return
	 */
	public int getGradeOrderRequiredPeriod(Integer userId, Integer gradeId) {
		return gradeUserDao.getGradeOrderRequiredPeriod(userId, gradeId);
	}

	/**
	 * 根据userId获取全部班级
	 * 
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getGradeListByUserId(int userId) {
		return gradeUserDao.getGradeListByUserId(userId);
	}
}
