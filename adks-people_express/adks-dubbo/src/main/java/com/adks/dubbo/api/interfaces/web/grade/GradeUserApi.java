package com.adks.dubbo.api.interfaces.web.grade;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.commons.Page;

public interface GradeUserApi {
	
	/*得到用户在平台上的总学时*/
	public String getUserTotleXS(Integer id);
	
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
	 * @Title getGradeUserByUserId
	 * @Description：根据userId查询班级用户信息
	 * @author xrl
	 * @Date 2017年3月30日
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getGradeUserByUserId(Integer userId);
	
	public List<Adks_grade_user> getTopCourseStudyTimeUserList(Map map);
	//学员已获得分数，学时，论文，考试，讨论分数
	public Adks_grade_user getUserStudysFromGradeUser(Integer userId,Integer gradeId);
	
	public Adks_grade_user getGradeUserByCon(Integer gradeId,Integer userId);
	
	/**
	 * 
	 * @Title updateGradeUserForCredit
	 * @Description
	 * @author xrl
	 * @Date 2017年6月3日
	 */
	public void updateGradeUserForCredit(Map<String, Object> map);
}
