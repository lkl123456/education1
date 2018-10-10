package com.adks.dubbo.api.interfaces.web.grade;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.exam.Adks_exam;
import com.adks.dubbo.api.data.exam.Adks_exam_score;
import com.adks.dubbo.api.data.exam.Adks_exam_score_answer;
import com.adks.dubbo.api.data.grade.Adks_grade_exam;
import com.adks.dubbo.api.data.paper.Adks_paper;
import com.adks.dubbo.api.data.paper.Adks_paper_question;
import com.adks.dubbo.api.data.question.Adks_question;
import com.adks.dubbo.commons.Page;

/**
 * 
 * ClassName GradeApi
 * 
 * @Description：班级API
 * @author xrl
 * @Date 2017年3月22日
 */
public interface GradeExamApi {

	public Integer getGradeExamCount(Integer gradeId);

	public Integer getGradeExamUserNum(Integer gradeId, Integer userId);

	public Page<List<Adks_grade_exam>> gradeExamList(Page<List<Adks_grade_exam>> page);

	public Integer getUserExamTimes(Map map);

	public Adks_exam getExamById(Integer gradeId, Integer examId);

	public List<Adks_exam_score> getExamScoreList(Map map);

	/**
	 * 获取班级考试所添加的全部试卷信息
	 * 
	 * @param examId
	 * @return
	 */
	public List<Map<String, Object>> getExamPaperList(int examId);

	/**
	 * 获取试卷下全部试题
	 * 
	 * @param paperId
	 * @return
	 */
	public List<Adks_paper_question> getPaperQuestionListByPaperId(Integer paperId);

	/**
	 * 根据id查询试卷信息
	 * 
	 * @param id
	 * @return
	 */
	public Adks_paper getPaperById(Integer id);

	/**
	 * 获取试题信息
	 * 
	 * @param id
	 * @return
	 */
	public Adks_question getQuestionById(Integer id);

	// 保存成绩
	public Integer saveExamScore(Adks_exam_score ec);

	public void deleteExamScore(int examScoreId);

	// 保存答题记录
	public Integer saveExamScoreAnswer(Adks_exam_score_answer eca);

	public void deleteExamScoreAnswer(int examScoreId);
	//查看班级专题的档案
	public List<Adks_exam_score> getExamScoreByCon(Map map);
}
