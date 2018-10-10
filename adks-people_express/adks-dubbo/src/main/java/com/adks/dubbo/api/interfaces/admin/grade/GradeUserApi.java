package com.adks.dubbo.api.interfaces.admin.grade;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.commons.Page;
/**
 * 
 * ClassName GradeUserApi
 * @Description：班级学员Api
 * @author xrl
 * @Date 2017年4月26日
 */
public interface GradeUserApi {
	
	/**
	 * 
	 * @Title getGradeUserListPage
	 * @Description：获取班级已选学员分页数据
	 * @author xrl
	 * @Date 2017年3月29日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeUserListPage(Page<List<Map<String, Object>>> page);

	/**
	 * 
	 * @Title getUserIdsByGradeId
	 * @Description：获取班级已选用户已ID
	 * @author xrl
	 * @Date 2017年3月29日
	 * @param gradeId
	 * @param orgName
	 * @return
	 */
	public List<Map<String, Object>> getUserIdsByGradeId(Integer gradeId);
	
	//得到班级总人数
	public Integer getUserCount(Integer gradeId);
	
	/**
	 * 
	 * @Title saveGradeUser
	 * @Description：保存班级
	 * @author xrl
	 * @Date 2017年3月30日
	 */
	public int saveGradeUser(Map<String, Object> map);
	
	/**
	 * 
	 * @Title getGradeHeadTeacherPage
	 * @Description：已添加到该班级的班主任
	 * @author xrl
	 * @Date 2017年3月30日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeHeadTeacherPage(Page<List<Map<String, Object>>> page);
	
	/**
	 * 
	 * @Title getHeadTeacherIdByGradeId
	 * @Description：获取班级已添加的班主任的userId
	 * @author xrl
	 * @Date 2017年3月30日
	 * @param gradeId
	 * @return
	 */
	public Map<String, Object> getHeadTeacherIdByGradeId(Integer gradeId);
	
	/**
	 * 
	 * @Title updateGradeHeadTeacher
	 * @Description：添加班级班主任
	 * @author xrl
	 * @Date 2017年3月30日
	 * @param map
	 */
	public void updateGradeHeadTeacher(Map<String, Object> map);
	
	/**
	 * 
	 * @Title getGradeUserByUserId
	 * @Description：根据userId查询班级用户信息
	 * @author xrl
	 * @Date 2017年3月30日
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getGradeUserByUserId(Map<String, Object> map);
	
	/**
	 * 
	 * @Title removeGradeUsers
	 * @Description：移除班级用户
	 * @author xrl
	 * @Date 2017年5月5日
	 * @param map
	 */
	public void removeGradeUsers(Map<String, Object> map);
	
	/**
	 * 
	 * @Title getGradeUserList
	 * @Description：获取班级学员列表
	 * @author xrl
	 * @Date 2017年5月19日
	 * @param gradeId
	 * @return
	 */
	public List<Map<String, Object>> getGradeUserList(Integer gradeId);
	
	/**
	 * 
	 * @Title updateAllGradeUserGraduate
	 * @Description:更新班级用户结业状态
	 * @author xrl
	 * @Date 2017年5月20日
	 * @param gradeId
	 * @param userId
	 */
	public void updateAllGradeUserGraduate(Integer gradeId,Integer userId);
	
	public void updateGradeUserCredit(Map<String, Object> map);
	
	/**
	 * 
	 * @Title getGradeStudyListPage
	 * @Description：获取班级学习统计列表
	 * @author xrl
	 * @Date 2017年6月14日
	 * @param map
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeStudyListPage(Page<List<Map<String, Object>>> page);
	
	/**
	 * 
	 * @Title getGradeUserByGradeUserId
	 * @Description：根据gradeUserId获取班级用户信息
	 * @author xrl
	 * @Date 2017年6月15日
	 * @param gradeUserId
	 * @return
	 */
	public Map<String, Object> getGradeUserByGradeUserId(Integer gradeUserId);
	
	/**
	 * 
	 * @Title getGradeUserByUserIdAndGradeId
	 * @Description:根据userId和gradeId获取班级学员信息
	 * @author xrl
	 * @Date 2017年6月15日
	 * @param map
	 * @return
	 */
	public Map<String, Object> getGradeUserByUserIdAndGradeId(Map<String, Object> map);
	
	/**
	 * 
	 * @Title addGradeUserCredit
	 * @Description：更新班级用户必修或选修学时
	 * @author xrl
	 * @Date 2017年6月15日
	 * @param map
	 */
	public void addGradeUserCredit(Map<String, Object> map);
	
	/**
	 * 
	 * @Title updateGradeUserGraduate
	 * @Description：根据gradeUserId更新班级学员结业状态
	 * @author xrl
	 * @Date 2017年6月15日
	 * @param gradeUserId
	 */
	public void updateGradeUserGraduate(Integer gradeUserId);
	
	/**
	 * 
	 * @Title getGradePerformanceListPage
	 * @Description:班级学员成绩分页列表
	 * @author xrl
	 * @Date 2017年6月16日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradePerformanceListPage(Page<List<Map<String, Object>>> page);
	
	/**
	 * 
	 * @Title getGradeStudyStatisticsList
	 * @Description：获取班级学习详情列表
	 * @author xrl
	 * @Date 2017年6月20日
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getGradeStudyStatisticsList(Map<String, Object> map);
	
	/**
	 * 
	 * @Title getGradePerformanceList
	 * @Description：获取班级成绩统计列表
	 * @author xrl
	 * @Date 2017年6月20日
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getGradePerformanceList(Map<String, Object> map);
}
