package com.adks.dubbo.service.web.grade;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.exam.Adks_exam;
import com.adks.dubbo.api.data.exam.Adks_exam_score;
import com.adks.dubbo.api.data.grade.Adks_grade_exam;
import com.adks.dubbo.api.data.paper.Adks_paper;
import com.adks.dubbo.api.data.paper.Adks_paper_question;
import com.adks.dubbo.api.data.question.Adks_question;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.web.grade.GradeExamWebDao;

@Service
public class GradeExamWebService extends BaseService<GradeExamWebDao> {

	@Autowired
	private GradeExamWebDao gradeExamDao;

	@Override
	protected GradeExamWebDao getDao() {
		return gradeExamDao;
	}

	public Integer getGradeExamCount(Integer gradeId) {
		return gradeExamDao.getGradeExamCount(gradeId);
	}

	public Integer getGradeExamUserNum(Integer gradeId, Integer userId) {
		return gradeExamDao.getGradeExamUserNum(gradeId, userId);
	}

	public Page<List<Adks_grade_exam>> gradeExamList(Page<List<Adks_grade_exam>> page) {
		return gradeExamDao.gradeExamList(page);
	}

	public Integer getUserExamTimes(Map map) {
		return gradeExamDao.getUserExamTimes(map);
	}

	public Adks_exam getExamById(Integer gradeId, Integer examId) {
		return gradeExamDao.getExamById(gradeId, examId);
	}

	public List<Adks_exam_score> getExamScoreList(Map map) {
		return gradeExamDao.getExamScoreList(map);
	}

	/**
	 * 获取班级考试所添加的全部试卷信息
	 * 
	 * @param examId
	 * @return
	 */
	public List<Map<String, Object>> getExamPaperList(int examId) {
		return gradeExamDao.getExamPaperList(examId);
	}

	/**
	 * 获取试卷下全部试题
	 * 
	 * @param paperId
	 * @return
	 */
	public List<Adks_paper_question> getPaperQuestionListByPaperId(Integer paperId) {
		return gradeExamDao.getPaperQuestionListByPaperId(paperId);
	}

	/**
	 * 根据试卷id获取信息
	 * 
	 * @param id
	 * @return
	 */
	public Adks_paper getPaperById(Integer id) {
		return gradeExamDao.getPaperById(id);
	}

	public Adks_question getQuestionById(Integer id) {
		return gradeExamDao.getQuestionById(id);
	}
}
