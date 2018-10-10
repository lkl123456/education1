package com.adks.dubbo.providers.admin.exam;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dbclient.mysql.MysqlClient;
import com.adks.dubbo.api.data.exam.Adks_exam;
import com.adks.dubbo.api.interfaces.admin.exam.ExamApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.exam.ExamScoreService;
import com.adks.dubbo.service.admin.exam.ExamService;

public class ExamApiImpl implements ExamApi {

	@Autowired
	private MysqlClient mysqlClient;

	@Autowired
	private ExamService examService;
	@Autowired
	private ExamScoreService examScoreService;

	@Override
	public Page<List<Map<String, Object>>> getExamListPage(Page<List<Map<String, Object>>> page) {
		return examService.getExamListPage(page);
	}

	@Override
	public Integer saveExam(Adks_exam exam) {
		Integer flag = 0;
		flag = examService.savePaper(exam);
		return flag;
	}

	@Override
	public void deleteExamByIds(String examIds) {
		examService.deleteExamByIds(examIds);
	}

	@Override
	public Adks_exam getExamById(Integer examId) {
		return examService.getExamById(examId);
	}

	@Override
	public Map<String, Object> getExamByName(Map<String, Object> map) {
		return examService.getExamByName(map);
	}

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
	@Override
	public List<Map<String, Object>> getExamScoreList(Integer examId, Integer userId, Integer gradeId) {
		return examScoreService.getExamScoreList(examId,userId,gradeId);
	}

	@Override
	public List<Map<String, Object>> getExamScoreByGradeIdAndUserId(Map<String,Object> map) {
		return examScoreService.getExamScoreByGradeIdAndUserId(map);
	}

}
