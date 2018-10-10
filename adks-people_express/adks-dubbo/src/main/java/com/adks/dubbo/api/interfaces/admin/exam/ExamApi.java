package com.adks.dubbo.api.interfaces.admin.exam;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.exam.Adks_exam;
import com.adks.dubbo.commons.Page;

public interface ExamApi {

	public Integer saveExam(Adks_exam exam);

	public void deleteExamByIds(String exam);

	public Adks_exam getExamById(Integer examId);

	public Page<List<Map<String, Object>>> getExamListPage(Page<List<Map<String, Object>>> page);

	public Map<String, Object> getExamByName(Map<String, Object> map);
	
	/**
	 * 
	 * @Title getExamScoreList
	 * @Description：获取学员考试详情
	 * @author xrl
	 * @Date 2017年5月20日
	 * @param examId
	 * @param userId
	 * @param gradeId
	 * @return
	 */
	public List<Map<String, Object>> getExamScoreList(Integer examId,Integer userId,Integer gradeId);
	
	/**
	 * 
	 * @Title getExamScoreByGradeId
	 * @Description：获取班级学员考试信息
	 * @author xrl
	 * @Date 2017年6月16日
	 * @param gradeId
	 * @return
	 */
	public List<Map<String, Object>> getExamScoreByGradeIdAndUserId(Map<String, Object> map);
}
