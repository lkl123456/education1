package com.adks.dubbo.providers.web.grade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.api.interfaces.web.grade.GradeUserApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.web.grade.GradeUserWebService;
/**
 * 
 * ClassName GradeUserApiImpl
 * @Description：班级用户API实现
 * @author xrl
 * @Date 2017年3月30日
 */
public class GradeUserApiImpl implements GradeUserApi {
	
	@Autowired
	private GradeUserWebService gradeUserService;
	
	/**
	 * 
	 * @Title getUserIdsByGradeId
	 * @Description：班级已选用户
	 * @author xrl
	 * @Date 2017年3月29日
	 * @param gradeId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getUserIdsByGradeId(Integer gradeId) {
		Map<String,Object> queryColumnValueMap = new HashMap<>();
		queryColumnValueMap.put("gradeId", gradeId);
		List<String> returnList = new ArrayList<>();
		returnList.add("userId");
		return gradeUserService.query(queryColumnValueMap,returnList,null);
	}
	/**
	 * 
	 * @Title getHeadTeacherIdByGradeId
	 * @Description：获取班级已添加的班主任的userId
	 * @author xrl
	 * @Date 2017年3月30日
	 * @param gradeId
	 * @return
	 */
	@Override
	public Map<String, Object> getHeadTeacherIdByGradeId(Integer gradeId) {
		return gradeUserService.getHeadTeacherIdByGradeId(gradeId);
	}

	/**
	 * 
	 * @Title getGradeUserByUserId
	 * @Description:根据userId查询班级用户信息
	 * @author xrl
	 * @Date 2017年3月30日
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getGradeUserByUserId(Integer userId){
		return gradeUserService.getGradeUserByUserId(userId);
	}
	
	public List<Adks_grade_user> getTopCourseStudyTimeUserList(Map map){
		return gradeUserService.getTopCourseStudyTimeUserList(map);
	}
	@Override
	public Adks_grade_user getUserStudysFromGradeUser(Integer userId, Integer gradeId) {
		return gradeUserService.getUserStudysFromGradeUser(userId,gradeId);
	}
	@Override
	public String getUserTotleXS(Integer id) {
		return gradeUserService.getUserTotleXS(id);
	}
	@Override
	public Adks_grade_user getGradeUserByCon(Integer gradeId, Integer userId) {
		return gradeUserService.getGradeUserByCon(gradeId,userId);
	}
	@Override
	public void updateGradeUserForCredit(Map<String, Object> map) {
		gradeUserService.updateGradeUserForCredit(map);
	}
	
	
}
