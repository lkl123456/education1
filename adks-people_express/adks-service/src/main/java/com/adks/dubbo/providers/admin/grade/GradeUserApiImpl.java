package com.adks.dubbo.providers.admin.grade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.api.interfaces.admin.grade.GradeUserApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.exam.ExamScoreService;
import com.adks.dubbo.service.admin.grade.GradeExamService;
import com.adks.dubbo.service.admin.grade.GradeService;
import com.adks.dubbo.service.admin.grade.GradeUserService;
import com.adks.dubbo.service.admin.grade.GradeWorkService;
/**
 * 
 * ClassName GradeUserApiImpl
 * @Description：班级用户API实现
 * @author xrl
 * @Date 2017年3月30日
 */
public class GradeUserApiImpl implements GradeUserApi {
	
	@Autowired
	private GradeUserService gradeUserService;
	@Autowired
	private GradeWorkService gradeWorkService;
	@Autowired
	private GradeExamService gradeExamService;
	@Autowired
	private ExamScoreService examScoreService;
	@Autowired
	private GradeService gradeService;
	
	/**
	 * 
	 * @Title getGradeUserListPage
	 * @Description：获取班级已选学员信息
	 * @author xrl
	 * @Date 2017年3月29日
	 * @param page
	 * @return
	 */
	@Override
	public Page<List<Map<String, Object>>> getGradeUserListPage(Page<List<Map<String, Object>>> page) {
		return gradeUserService.getGradeUserListPage(page);
	}

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

	public Integer getUserCount(Integer gradeId){
		return gradeUserService.getUserCount(gradeId);
	}
	
	@Override
	public int saveGradeUser(Map<String, Object> map) {
		return gradeUserService.insert(map);
	}

	/**
	 * 
	 * @Title getGradeHeadTeacherPage
	 * @Description：已添加到该班级的班主任
	 * @author xrl
	 * @Date 2017年3月30日
	 * @param page
	 * @return
	 */
	@Override
	public Page<List<Map<String, Object>>> getGradeHeadTeacherPage(Page<List<Map<String, Object>>> page) {
		return gradeUserService.getGradeHeadTeacherPage(page);
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
	 * @Title updateGradeHeadTeacher
	 * @Description：添加班级班主任
	 * @author xrl
	 * @Date 2017年3月30日
	 * @param map
	 */
	@Override
	public void updateGradeHeadTeacher(Map<String, Object> map) {
		gradeUserService.updateGradeHeadTeacher(map);
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
	public Map<String, Object> getGradeUserByUserId(Map<String, Object> map){
		return gradeUserService.getGradeUserByUserId(map);
	}
	
	/**
	 * 
	 * @Title removeGradeUsers
	 * @Description：移除班级用户
	 * @author xrl
	 * @Date 2017年5月5日
	 * @param map
	 */
	@Override
	public void removeGradeUsers(Map<String, Object> map) {
		//删除班级作业
//		List<Map<String, Object>> gradeWorkMap=gradeWorkService.getGradeWotkListByGradeId((Integer)map.get("gradeId"));
//		if(gradeWorkMap.size()>0){
//			for (Map<String, Object> gradeWork : gradeWorkMap) {
//				map.put("gradeWorkId", gradeWork.get("gradeWorkId"));
//				gradeWorkService.removeGradeWorkReply(map);
//			}
//		}
		//删除班级学员考试成绩
//		List<Map<String, Object>> gradeExamMap=gradeExamService.getGradeExamListByGradeId((Integer)map.get("gradeId"));
//		if(gradeExamMap.size()>0){
//			for (Map<String, Object> gradeExam : gradeExamMap) {
//				map.put("examId", gradeExam.get("examId"));
//				examScoreService.removeGradeUserScore(map);
//			}
//		}
		//删除班级学员
		gradeUserService.removeGradeUsers(map);
		//减少班级学员数量
//		Map<String, Object> gradeMap=gradeService.getGradeById((Integer)map.get("gradeId"));
//		if((Integer)gradeMap.get("userNum")>0){
//			Integer userNum=(Integer)gradeMap.get("userNum")-1;
//			map.put("userNum", userNum);
//			gradeService.updateGradeUserNum(map);
//		}
	}

	/**
	 * 
	 * @Title getGradeUserList
	 * @Description：获取班级学员列表
	 * @author xrl
	 * @Date 2017年5月19日
	 * @param gradeId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getGradeUserList(Integer gradeId) {
		return gradeUserService.getGradeUserList(gradeId);
	}

	/**
	 * 
	 * @Title updateAllGradeUserGraduate
	 * @Description:更新班级用户结业状态
	 * @author xrl
	 * @Date 2017年5月20日
	 * @param gradeId
	 * @param userId
	 */
	@Override
	public void updateAllGradeUserGraduate(Integer gradeId, Integer userId) {
		gradeUserService.updateAllGradeUserGraduate(gradeId,userId);
	}

	@Override
	public void updateGradeUserCredit(Map<String, Object> map) {
		gradeUserService.updateGradeUserCredit(map);
	}

	/**
	 * 
	 * @Title getGradeStudyListPage
	 * @Description：获取班级学习统计列表
	 * @author xrl
	 * @Date 2017年6月14日
	 * @param map
	 * @return
	 */
	@Override
	public Page<List<Map<String, Object>>> getGradeStudyListPage(Page<List<Map<String, Object>>> page) {
		return gradeUserService.getGradeStudyListPage(page);
	}

	/**
	 * 
	 * @Title getGradeUserByGradeUserId
	 * @Description：根据gradeUserId获取班级用户信息
	 * @author xrl
	 * @Date 2017年6月15日
	 * @param gradeUserId
	 * @return
	 */
	@Override
	public Map<String, Object> getGradeUserByGradeUserId(Integer gradeUserId) {
		return gradeUserService.getGradeUserByGradeUserId(gradeUserId);
	}

	/**
	 * 
	 * @Title getGradeUserByUserIdAndGradeId
	 * @Description:根据userId和gradeId获取班级学员信息
	 * @author xrl
	 * @Date 2017年6月15日
	 * @param map
	 * @return
	 */
	@Override
	public Map<String, Object> getGradeUserByUserIdAndGradeId(Map<String, Object> map) {
		return gradeUserService.getGradeUserByUserIdAndGradeId(map);
	}

	/**
	 * 
	 * @Title addGradeUserCredit
	 * @Description：更新班级用户必修或选修学时
	 * @author xrl
	 * @Date 2017年6月15日
	 * @param map
	 */
	@Override
	public void addGradeUserCredit(Map<String, Object> map) {
		gradeUserService.addGradeUserCredit(map);
	}

	/**
	 * 
	 * @Title updateGradeUserGraduate
	 * @Description
	 * @author xrl
	 * @Date 2017年6月15日
	 * @param gradeUserId
	 */
	@Override
	public void updateGradeUserGraduate(Integer gradeUserId) {
		Map<String, Object> updateWhereConditionMap=new HashMap<>();
		updateWhereConditionMap.put("gradeUserId", gradeUserId);
		Map<String, Object> paramValue=new HashMap<>();
		paramValue.put("isGraduate", 1);
		gradeUserService.update(paramValue, updateWhereConditionMap);
	}

	/**
	 * 
	 * @Title getGradePerformanceListPage
	 * @Description
	 * @author xrl
	 * @Date 2017年6月16日
	 * @param page
	 * @return
	 */
	@Override
	public Page<List<Map<String, Object>>> getGradePerformanceListPage(Page<List<Map<String, Object>>> page) {
		return gradeUserService.getGradePerformanceListPage(page);
	}

	/**
	 * 
	 * @Title getGradeStudyStatisticsList
	 * @Description：获取班级学习详情列表
	 * @author xrl
	 * @Date 2017年6月20日
	 * @param map
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getGradeStudyStatisticsList(Map<String, Object> map) {
		return gradeUserService.getGradeStudyStatisticsList(map);
	}

	/**
	 * 
	 * @Title getGradePerformanceList
	 * @Description：获取班级成绩统计列表
	 * @author xrl
	 * @Date 2017年6月20日
	 * @param map
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getGradePerformanceList(Map<String, Object> map) {
		return gradeUserService.getGradePerformanceList(map);
	}
}
