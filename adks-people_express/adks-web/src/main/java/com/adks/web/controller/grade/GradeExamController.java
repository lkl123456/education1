package com.adks.web.controller.grade;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.commons.util.ComUtil;
import com.adks.commons.util.Constants;
import com.adks.commons.util.DateTimeUtil;
import com.adks.commons.util.PropertiesFactory;
import com.adks.dubbo.api.data.exam.Adks_exam;
import com.adks.dubbo.api.data.exam.Adks_exam_score;
import com.adks.dubbo.api.data.exam.Adks_exam_score_answer;
import com.adks.dubbo.api.data.grade.Adks_grade_exam;
import com.adks.dubbo.api.data.paper.Adks_paper;
import com.adks.dubbo.api.data.paper.Adks_paper_question;
import com.adks.dubbo.api.data.question.Adks_question;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.api.interfaces.web.grade.GradeApi;
import com.adks.dubbo.api.interfaces.web.grade.GradeExamApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping({ "/gradeExam" })
public class GradeExamController {

	@Autowired
	private GradeExamApi gradeExamApi;

	// 班级考试
	@RequestMapping(value = "/gradeExamList.do")
	public String graduate(Page<List<Adks_grade_exam>> page, Integer gradeId, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Model model) {
		if (null == page) {
			page = new Page();
		}
		Adks_user user = (Adks_user) request.getSession().getAttribute("user");
		Map map = new HashMap<>();
		map.put("gradeId", gradeId);
		page.setMap(map);
		page = gradeExamApi.gradeExamList(page);

		model.addAttribute("precisionNum", Constants.precisionNum);
		model.addAttribute("nowDate", new Date());
		model.addAttribute("gradeId", gradeId);
		model.addAttribute("page", page);
		return "/grade/gradeExamList";
	}

	/**
	 * 根据考试ID 获取用户该考试考试次数
	 * 
	 * @param examId
	 *            考试ID
	 * @param examTimes
	 *            当前考试允许次数
	 * @throws Exception
	 */
	@RequestMapping(value = "/getResidueExamTimes.do")
	@ResponseBody
	public void getResidueExamTimes(HttpServletRequest request, HttpServletResponse response, Integer gradeId,
			Integer examId, Integer examTimes) {
		try {
			Integer result = 0;
			if (examId != null) {
				Adks_user user = (Adks_user) request.getSession().getAttribute("user");
				Map map = new HashMap();
				map.put("gradeId", gradeId);
				map.put("userId", user.getUserId());
				map.put("examId", examId);
				Integer u_times = gradeExamApi.getUserExamTimes(map);
				if (u_times <= examTimes) {
					result = examTimes - u_times;
				}
			}
			PrintWriter pw = response.getWriter();
			response.addHeader("content-type", "text/html");
			pw.write(result.toString());
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 查看考试成绩
	@RequestMapping(value = "/gradeExamScoreList.do")
	public String gradeExamScoreList(HttpServletRequest request, HttpServletResponse response, Integer gradeId,
			Integer examId, Model model) {

		Adks_user user = (Adks_user) request.getSession().getAttribute("user");
		// 得到考试信息
		Adks_exam exam = gradeExamApi.getExamById(gradeId, examId);

		if (exam != null && exam.getExamDate() != null) {
			exam.setExamDateStr(DateTimeUtil.integerToString(exam.getExamDate()));
		}

		String passFlag = "false";
		Map map = new HashMap();
		map.put("userId", user.getUserId());
		map.put("examId", examId);
		map.put("gradeId", gradeId);
		List<Adks_exam_score> esList = gradeExamApi.getExamScoreList(map);
		if (esList != null) {
			for (int i = 0; i < esList.size(); i++) {
				Adks_exam_score es = esList.get(i);
				if ("false".equals(passFlag) && es.getScore() > exam.getPassScore()) {
					passFlag = "true";
				}
				if (es.getUseTime() != null) {
					es.setUseTimeStr(DateTimeUtil.integerToString(es.getUseTime()));
				}
			}
		}

		model.addAttribute("examS", exam);
		model.addAttribute("esList", esList);
		model.addAttribute("passFlag", passFlag);
		model.addAttribute("precisionNum", Constants.precisionNum);

		return "/grade/gradeExamScoreDiv";
	}

	/**
	 * 考试信息
	 *
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/gradeExamInfo")
	@ResponseBody
	public Map<String, Object> gradeExamInfo(Integer examId, Integer gradeId, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("msg", "succ");
		Adks_user user = (Adks_user) request.getSession().getAttribute("user");
		if (user != null) {
			resMap.put("userId", user.getUserId());
		} else
			resMap.put("msg", "false");

		Adks_exam exam = gradeExamApi.getExamById(gradeId, examId);
		if (exam != null) {
			resMap.put("examId", examId);
			resMap.put("examDate", exam.getExamDate());
			resMap.put("gradeId", gradeId);
			// 根据考试获取全部试卷
			List<Map<String, Object>> list = gradeExamApi.getExamPaperList(examId);
			List paperList = new ArrayList<>();
			if (list != null && list.size() > 0) {
				for (Map<String, Object> map : list) {
					paperList.add(map.get("paperId"));
				}
			} else
				resMap.put("msg", "false");
			if (paperList != null && paperList.size() > 0) {
				// 取随机试卷id
				Random rand = new Random();
				int myRand = rand.nextInt(paperList.size());
				int paperId = (Integer) paperList.get(myRand);

				Adks_paper paper = gradeExamApi.getPaperById(paperId);
				if (paper != null) {
					resMap.put("paperId", paper.getPaperId());
					resMap.put("paperHtmlUrl", ossResource + paper.getPaperHtmlAdress());
				}
			}
		} else
			resMap.put("msg", "false");
		return resMap;
	}

	/**
	 * 保存用户答案
	 *
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/gradeExamSave")
	public void gradeExamSave(Integer gradeId, Integer examId, Integer paperId, Integer testTime, String jsTime,
			HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Adks_user user = (Adks_user) request.getSession().getAttribute("user");
		Adks_exam exam = null;
		if (gradeId != null && examId != null) {
			exam = gradeExamApi.getExamById(gradeId, examId);
		}
		Map map = new HashMap();
		map.put("userId", user.getUserId());
		map.put("examId", examId);
		map.put("gradeId", gradeId);

		// 原考试记录
		List<Adks_exam_score> exam_scores = gradeExamApi.getExamScoreList(map);

		// 总分数
		Integer scoreSum = 0;
		// 根据试卷ID获取所有试题
		List<Adks_paper_question> paper_questions = new ArrayList<Adks_paper_question>();
		List<Adks_question> danxuanList = new ArrayList<Adks_question>();
		List<Adks_question> duoxuanList = new ArrayList<Adks_question>();
		List<Adks_question> panduanList = new ArrayList<Adks_question>();
		List<Adks_question> tiankongList = new ArrayList<Adks_question>();
		List<Adks_question> wendaList = new ArrayList<Adks_question>();

		paper_questions = gradeExamApi.getPaperQuestionListByPaperId(paperId);
		if (paper_questions != null) {
			for (Adks_paper_question pq : paper_questions) {
				Adks_question question = gradeExamApi.getQuestionById(pq.getQsId());
				question.setQuestionValue(pq.getScore());
				if (question.getQuestionType() == 1) {
					danxuanList.add(question);
				} else if (question.getQuestionType() == 2) {
					duoxuanList.add(question);
				} else if (question.getQuestionType() == 3) {
					panduanList.add(question);
				} else if (question.getQuestionType() == 4) {
					tiankongList.add(question);
				} else if (question.getQuestionType() == 5) {
					wendaList.add(question);
				}
			}
		}

		List<Adks_exam_score_answer> esaList = new ArrayList<Adks_exam_score_answer>();
		Adks_exam_score_answer examScoreAnswer = null;

		// 单选题
		if (ComUtil.isNotNullOrEmpty(danxuanList)) {
			for (int i1 = 0; i1 < danxuanList.size(); i1++) {
				examScoreAnswer = new Adks_exam_score_answer();
				Adks_question que = danxuanList.get(i1);
				examScoreAnswer.setQsId(que.getQuestionId());
				Integer scoreT = 0;
				String value = request.getParameter("danxuan" + que.getQuestionId());
				if (ComUtil.isNotNullOrEmpty(value) && value.equals(que.getAnwsers())) {
					scoreT = que.getQuestionValue();
				}
				examScoreAnswer.setUserAnswer(value);
				// 累加总分数
				scoreSum = scoreSum + scoreT;
				// 加入到试题成绩记录集合中
				examScoreAnswer.setUserScore(scoreT);
				esaList.add(examScoreAnswer);
			}
		}

		// 多选题
		if (ComUtil.isNotNullOrEmpty(duoxuanList)) {
			for (int i2 = 0; i2 < duoxuanList.size(); i2++) {
				examScoreAnswer = new Adks_exam_score_answer();
				Adks_question que = duoxuanList.get(i2);
				examScoreAnswer.setQsId(que.getQuestionId());
				Integer scoreT = 0;
				String[] values = request.getParameterValues("duoxuan" + que.getQuestionId());
				if (ComUtil.isNotNullOrEmpty(values)) {
					String answerSave = "";
					String[] answer = que.getAnwsers().split(";");
					// 只判断学员答案长度等于或小于
					if (values.length != answer.length) {
						/// 为查看成绩显示一致修改：
						// 判断答案是否存在
						// boolean istrue = true;
						// 判断每个选项是否存在于试题答案中
						for (int k = 0; k < values.length; k++) {
							/*
							 * if(que.getAnswer().indexOf(values[k]) < 0){
							 * istrue = false; }
							 */
							if (!"".equals(answerSave)) {
								answerSave += ";" + values[k];
							} else {
								answerSave = values[k];
							}
						}
					} else if (values.length == answer.length) {
						// 判断答案是否存在
						boolean istrue = true;
						// 判断每个选项是否存在于试题答案中
						for (int k = 0; k < values.length; k++) {
							if (que.getAnwsers().indexOf(values[k]) < 0) {
								istrue = false;
							}
							if (!"".equals(answerSave)) {
								answerSave += ";" + values[k];
							} else {
								answerSave = values[k];
							}
						}
						if (istrue) {
							scoreT = que.getQuestionValue();
						}
					}
					examScoreAnswer.setUserAnswer(answerSave);
				}
				// 累加总分数
				scoreSum = scoreSum + scoreT;
				// 加入到试题成绩记录集合中
				examScoreAnswer.setUserScore(scoreT);
				esaList.add(examScoreAnswer);
			}
		}

		// 判断题
		if (ComUtil.isNotNullOrEmpty(panduanList)) {
			for (int i3 = 0; i3 < panduanList.size(); i3++) {
				examScoreAnswer = new Adks_exam_score_answer();
				Adks_question que = panduanList.get(i3);
				examScoreAnswer.setQsId(que.getQuestionId());
				Integer scoreT = 0;
				String value = request.getParameter("panduan" + que.getQuestionId());
				if (ComUtil.isNotNullOrEmpty(value) && value.equals(que.getAnwsers())) {
					scoreT = que.getQuestionValue();
				}
				examScoreAnswer.setUserAnswer(value);
				// 累加总分数
				scoreSum = scoreSum + scoreT;
				// 加入到试题成绩记录集合中
				examScoreAnswer.setUserScore(scoreT);
				esaList.add(examScoreAnswer);
			}
		}

		// 填空题
		if (ComUtil.isNotNullOrEmpty(tiankongList)) {
			for (int i4 = 0; i4 < tiankongList.size(); i4++) {
				examScoreAnswer = new Adks_exam_score_answer();
				Adks_question que = tiankongList.get(i4);
				examScoreAnswer.setQsId(que.getQuestionId());
				Integer scoreT = 0;
				String value = request.getParameter("tiankong" + que.getQuestionId());
				if (ComUtil.isNotNullOrEmpty(value)) {
					examScoreAnswer.setUserAnswer(value);
				}
				// 累加总分数
				scoreSum = scoreSum + scoreT;
				// 加入到试题成绩记录集合中
				examScoreAnswer.setUserScore(scoreT);
				esaList.add(examScoreAnswer);
			}
		}

		// 问答题
		if (ComUtil.isNotNullOrEmpty(wendaList)) {
			for (int i5 = 0; i5 < wendaList.size(); i5++) {
				examScoreAnswer = new Adks_exam_score_answer();
				Adks_question que = wendaList.get(i5);
				examScoreAnswer.setQsId(que.getQuestionId());
				Integer scoreT = 0;
				String value = request.getParameter("wenda" + que.getQuestionId());
				if (ComUtil.isNotNullOrEmpty(value)) {
					examScoreAnswer.setUserAnswer(value);
				}
				// 累加总分数
				scoreSum = scoreSum + scoreT;
				// 加入到试题成绩记录集合中
				examScoreAnswer.setUserScore(scoreT);
				esaList.add(examScoreAnswer);
			}
		}
		int flag = 0;
		Adks_exam_score examScore = null;
		if (exam_scores != null) {
			examScore = exam_scores.get(0);

			int lastExamScore = examScore.getScore();
			if (lastExamScore < scoreSum) {
				/* #删除考试成绩 */
				int c = examScore.getExamCounts() + 1;
				int esid = examScore.getExamScoreId();
				examScore = new Adks_exam_score();
				examScore.setExamCounts(c);
				examScore.setIsCorrent(1);
				examScore.setCorrentId(null);
				examScore.setCorrentName(null);
				examScore.setCorrentDate(null);
				examScore.setScore(scoreSum);
				examScore.setUseTime(testTime * 60 - DateTimeUtil.stringToInteger(jsTime));
				examScore.setSubmitDate(new Date());
				examScore.setExamId(examId);
				examScore.setExamName(exam.getExamName());
				examScore.setGradeId(gradeId);
				examScore.setGradeName(exam.getGradeName());
				examScore.setUserId(user.getUserId());
				examScore.setUserName(user.getUserRealName());
				examScore.setOrgId(user.getOrgId());
				examScore.setOrgName(user.getOrgName());
				examScore.setOrgCode(user.getOrgCode());
				gradeExamApi.deleteExamScoreAnswer(esid);
				gradeExamApi.deleteExamScore(esid);
				gradeExamApi.saveExamScore(examScore);
			}
		} else {
			// 保存学员考试成绩信息
			examScore = new Adks_exam_score();
			examScore.setExamCounts(1);
			examScore.setIsCorrent(1);
			examScore.setCorrentId(null);
			examScore.setCorrentName(null);
			examScore.setCorrentDate(null);
			examScore.setScore(scoreSum);
			examScore.setUseTime(testTime * 60 - DateTimeUtil.stringToInteger(jsTime));
			examScore.setSubmitDate(new Date());
			examScore.setExamId(examId);
			examScore.setExamName(exam.getExamName());
			examScore.setGradeId(gradeId);
			examScore.setGradeName(exam.getGradeName());
			examScore.setUserId(user.getUserId());
			examScore.setUserName(user.getUserRealName());
			examScore.setOrgId(user.getOrgId());
			examScore.setOrgName(user.getOrgName());
			examScore.setOrgCode(user.getOrgCode());
			flag = gradeExamApi.saveExamScore(examScore);
		}
		final Integer _esId = flag;
		final List<Adks_exam_score_answer> finalEsaList = esaList;
		for (Adks_exam_score_answer esaTemp : finalEsaList) {
			esaTemp.setExamScoreId(_esId);
			gradeExamApi.saveExamScoreAnswer(esaTemp);
		}

		PrintWriter pw = response.getWriter();
		pw.write("myCallbackFunction('succ')");
		pw.flush();
		pw.close();
	}
}
