package com.adks.dubbo.api.interfaces.admin.grade;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.api.data.grade.Adks_grade_work;
import com.adks.dubbo.api.data.grade.Adks_grade_work_reply;
import com.adks.dubbo.commons.Page;

public interface GradeWorkApi {
	
	/**
	 * 
	 * @Title getGradesJson
	 * @Description：获取班级树信息
	 * @author xrl
	 * @Date 2017年3月25日
	 * @return
	 */
	public List<Adks_grade> getGradesJson(Map<String, Object> map);

	/**
	 * 
	 * @Title getGradeWorkListPage
	 * @Description：班级作业分页列表
	 * @author xrl
	 * @Date 2017年3月25日
	 * @param paramPage
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeWorkListPage(Page<List<Map<String, Object>>> paramPage);
	
	/**
	 * 
	 * @Title saveGradeWork
	 * @Description:添加班级作业
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param gradeWork
	 */
	public Integer saveGradeWork(Adks_grade_work gradeWork);
	
	/**
	 * 
	 * @Title deleteGradeWorkByIds
	 * @Description：删除班级作业
	 * @author xrl
	 * @Date 2017年4月26日
	 * @param ids
	 */
	public void deleteGradeWorkByIds(String ids);
	
	/**
	 * 
	 * @Title getGradeWorkReplyListPage
	 * @Description：班级学员提交作业分页列表
	 * @author xrl
	 * @Date 2017年4月28日
	 * @param paramPage
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeWorkReplyListPage(Page<List<Map<String, Object>>> paramPage);
	
	/**
	 * 
	 * @Title saveGradeWorkReply
	 * @Description：保存批改作业
	 * @author xrl
	 * @Date 2017年4月28日
	 * @param gradeWorkReply
	 */
	public void saveGradeWorkReply(Map<String, Object> updateMap);
	
	/**
	 * 
	 * @Title delGradeWorkReplyByGradeWorkReplyId
	 * @Description：删除班级学员作业
	 * @author xrl
	 * @Date 2017年4月28日
	 * @param gradeWorkReplyId
	 */
	public void delGradeWorkReplyByGradeWorkReplyId(Integer gradeWorkReplyId);
	
	/**
	 * 
	 * @Title checkWorkTitle
	 * @Description：检查班级作业名在该班级中是否重复
	 * @author xrl
	 * @Date 2017年5月9日
	 * @param map
	 * @return
	 */
	public Map<String, Object> checkWorkTitle(Map<String, Object> map);
	
	/**
	 * 
	 * @Title getGradeWorkListByGradeId
	 * @Description：获取班级作业（论文）
	 * @author xrl
	 * @Date 2017年5月20日
	 * @param gradeId
	 * @return
	 */
	public List<Map<String, Object>> getGradeWorkListByGradeId(Integer gradeId);
	
	/**
	 * 
	 * @Title getGradeWorkReplyByUserIdAndGradeWorkId
	 * @Description：根据班级作业ID和用户ID获取班级学员提交作业
	 * @author xrl
	 * @Date 2017年5月20日
	 * @param gradeWorkId
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getGradeWorkReplyByUserIdAndGradeWorkId(Integer gradeWorkId,Integer userId);
	
	/**
	 * 
	 * @Title getGradeWorkReplyByGradeIdAndUserId
	 * @Description：班级学员作业列表
	 * @author xrl
	 * @Date 2017年6月16日
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getGradeWorkReplyByGradeIdAndUserId(Map<String, Object> map);
}
