package com.adks.dubbo.providers.web.grade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.exam.Adks_exam;
import com.adks.dubbo.api.data.exam.Adks_exam_score;
import com.adks.dubbo.api.data.exam.Adks_exam_score_answer;
import com.adks.dubbo.api.data.grade.Adks_grade_exam;
import com.adks.dubbo.api.data.paper.Adks_paper;
import com.adks.dubbo.api.data.paper.Adks_paper_question;
import com.adks.dubbo.api.data.question.Adks_question;
import com.adks.dubbo.api.interfaces.web.grade.GradeExamApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.web.grade.ExamScoreAnswerWebService;
import com.adks.dubbo.service.web.grade.ExamScoreWebService;
import com.adks.dubbo.service.web.grade.GradeExamWebService;

/**
 * 
 * 班级考试的api
 */
public class GradeExamApiImpl implements GradeExamApi {

	@Autowired
	private GradeExamWebService gradeExamService;
	@Autowired
	private ExamScoreWebService examScoreService;
	@Autowired
	private ExamScoreAnswerWebService examScoreAnswerService;

	@Override
	public Integer getGradeExamCount(Integer gradeId) {
		return gradeExamService.getGradeExamCount(gradeId);
	}

	@Override
	public Integer getGradeExamUserNum(Integer gradeId, Integer userId) {
		return gradeExamService.getGradeExamUserNum(gradeId, userId);
	}

	@Override
	public Page<List<Adks_grade_exam>> gradeExamList(Page<List<Adks_grade_exam>> page) {
		return gradeExamService.gradeExamList(page);
	}

	@Override
	public Integer getUserExamTimes(Map map) {
		return gradeExamService.getUserExamTimes(map);
	}

	@Override
	public Adks_exam getExamById(Integer gradeId, Integer examId) {
		return gradeExamService.getExamById(gradeId, examId);
	}

	@Override
	public List<Adks_exam_score> getExamScoreList(Map map) {
		return gradeExamService.getExamScoreList(map);
	}

	@Override
	public List<Map<String, Object>> getExamPaperList(int examId) {
		return gradeExamService.getExamPaperList(examId);
	}

	@Override
	public List<Adks_paper_question> getPaperQuestionListByPaperId(Integer paperId) {
		return gradeExamService.getPaperQuestionListByPaperId(paperId);
	}

	@Override
	public Adks_paper getPaperById(Integer id) {
		return gradeExamService.getPaperById(id);
	}

	@Override
	public Adks_question getQuestionById(Integer id) {
		return gradeExamService.getQuestionById(id);
	}

	@Override
	public Integer saveExamScore(Adks_exam_score ec) {
		Integer flag = 0;
		Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
		insertColumnValueMap.put("score", ec.getScore());
		insertColumnValueMap.put("useTime", ec.getUseTime());
		insertColumnValueMap.put("submitDate", ec.getSubmitDate());
		insertColumnValueMap.put("examCounts", ec.getExamCounts());
		insertColumnValueMap.put("isCorrent", ec.getIsCorrent());
		insertColumnValueMap.put("correntId", ec.getCorrentId());
		insertColumnValueMap.put("correntName", ec.getCorrentName());
		insertColumnValueMap.put("correntDate", ec.getCorrentDate());
		insertColumnValueMap.put("examId", ec.getExamId());
		insertColumnValueMap.put("examName", ec.getExamName());
		insertColumnValueMap.put("gradeId", ec.getGradeId());

		insertColumnValueMap.put("gradeName", ec.getGradeName());
		insertColumnValueMap.put("userId", ec.getUserId());
		insertColumnValueMap.put("userName", ec.getUserName());
		insertColumnValueMap.put("orgId", ec.getOrgId());
		insertColumnValueMap.put("orgName", ec.getOrgName());
		insertColumnValueMap.put("orgCode", ec.getOrgCode());

		if (ec.getExamScoreId() != null) {
			Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
			updateWhereConditionMap.put("examScoreId", ec.getExamScoreId());
			flag = examScoreService.update(insertColumnValueMap, updateWhereConditionMap);

		} else {
			flag = examScoreService.insert(insertColumnValueMap);
		}
		return flag;
	}

	@Override
	public Integer saveExamScoreAnswer(Adks_exam_score_answer eca) {
		Integer flag = 0;
		Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
		insertColumnValueMap.put("userAnswer", eca.getUserAnswer());
		insertColumnValueMap.put("userScore", eca.getUserScore());
		insertColumnValueMap.put("examScoreId", eca.getExamScoreId());
		insertColumnValueMap.put("qsId", eca.getQsId());

		if (eca.getEsaId() != null) {
			Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
			updateWhereConditionMap.put("esaId", eca.getEsaId());
			flag = examScoreAnswerService.update(insertColumnValueMap, updateWhereConditionMap);

		} else {
			flag = examScoreAnswerService.insert(insertColumnValueMap);
		}
		return flag;
	}

	@Override
	public void deleteExamScore(int examScoreId) {
		examScoreService.deleteExamScore(examScoreId);
	}

	@Override
	public void deleteExamScoreAnswer(int examScoreId) {
		examScoreAnswerService.deleteExamScoreAnswer(examScoreId);
	}

	@Override
	public List<Adks_exam_score> getExamScoreByCon(Map map) {
		return examScoreService.getExamScoreByCon(map);
	}

}
