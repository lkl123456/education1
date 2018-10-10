package com.adks.admin.controller.question;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.adks.admin.util.ExcelUtil;
import com.adks.admin.util.LogUtil;
import com.adks.dubbo.api.data.question.Adks_question;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.api.interfaces.admin.question.QuestionApi;
import com.adks.dubbo.api.interfaces.admin.question.QuestionSortApi;
import com.adks.dubbo.commons.ApiResponse;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping(value = "/question")
public class QuestionController {
	private final Logger logger = LoggerFactory.getLogger(QuestionController.class);

	@Autowired
	private QuestionApi questionApi;

	@Autowired
	private QuestionSortApi questionSortApi;
	@Autowired
	private LogApi logApi;

	@RequestMapping({ "/questionList" })
	public String home(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		logger.debug("进入 QuestionController  questionList...");
		System.out.println("进入 QuestionController  questionList...");
		return "question/questionList";
	}

	// 机构界面在使用
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getQuestionListJson", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public Page getQuestionListJson(HttpServletRequest request, Integer page, Integer rows, String s_questionName,
			String s_questionType, Integer s_qtSortId, String s_courseName, Integer s_questionValue_start,
			Integer s_questionValue_end) {

		Page<List<Map<String, Object>>> page_bean = null;
		try {
			Map userMap = (Map) request.getSession().getAttribute("user");
			page_bean = new Page<List<Map<String, Object>>>();
			page_bean.setCurrentPage(page);
			page_bean.setPageSize(rows);
			Map map = new HashMap<>();
			if (userMap != null) {
				map.put("orgId", userMap.get("orgId"));
				page_bean.setMap(map);
			}
			if (s_questionName != null) {
				s_questionName = java.net.URLDecoder.decode(s_questionName, "UTF-8");
				map.put("questionName", s_questionName);
				page_bean.setMap(map);
			}
			if (s_courseName != null) {
				s_courseName = java.net.URLDecoder.decode(s_courseName, "UTF-8");
				map.put("courseName", s_courseName);
				page_bean.setMap(map);
			}
			if (s_questionValue_start != null) {
				map.put("questionValue_start", s_questionValue_start);
				page_bean.setMap(map);
			}
			if (s_questionValue_end != null) {
				map.put("questionValue_end", s_questionValue_end);
				page_bean.setMap(map);
			}
			if (s_questionType != null) {
				map.put("questionType", s_questionType);
				page_bean.setMap(map);
			}
			if (s_qtSortId != null) {
				Map<String, Object> questionSortMap = questionSortApi.getQuestionSortById(s_qtSortId);
				map.put("qsSortCode", MapUtils.getString(questionSortMap, "qsSortCode"));
				page_bean.setMap(map);
			}
			page_bean = questionApi.getQuestionListPage(page_bean);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return page_bean;
	}

	@RequestMapping(value = "/saveQuestion", produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public void saveQuestion(Adks_question question, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		System.out.println("############");
		String message = "error";
		try {
			if (question != null && question.getQuestionName() != null) {
				Map<String, Object> checkMap = new HashMap<String, Object>();
				if (question.getQuestionId() != null) {
					checkMap.put("questionId", question.getQuestionId());
				}
				checkMap.put("questionName", question.getQuestionName());
				Map<String, Object> questionMap = questionApi.getQuestionByName(checkMap);
				if (questionMap.size() <= 0) {

					Map<String, Object> questionSort = questionSortApi.getQuestionSortById(question.getQtSortId());
					if (questionSort != null) {
						question.setQsSortName(MapUtils.getString(questionSort, "qsSortName"));
						question.setQsSortCode(MapUtils.getString(questionSort, "qsSortCode"));
					}
					String optionA = question.getOptionA();
					String optionB = question.getOptionB();
					String optionC = question.getOptionC();
					String optionD = question.getOptionD();
					String optionE = question.getOptionE();
					String optionF = question.getOptionF();
					String optionG = question.getOptionG();
					String optionH = question.getOptionH();
					String anwsers = question.getAnwsers();
					if (anwsers.length() > 0) {
						question.setAnwsers(anwsers.substring(0, anwsers.length() - 1));
					}
					if (question.getQuestionType() == 1) {
						question.setOptionA(optionA.substring(0, optionA.length() - 1));
						question.setOptionB(optionB.substring(0, optionB.length() - 1));
						question.setOptionC(optionC.substring(0, optionC.length() - 1));
						question.setOptionD(optionD.substring(0, optionD.length() - 1));
						question.setOptionE(optionE.substring(0, optionE.length() - 1));
						question.setOptionF(optionF.substring(0, optionF.length() - 1));
						question.setOptionG(optionG.substring(0, optionG.length() - 1));
						question.setOptionH(optionH.substring(0, optionH.length() - 1));
					} else if (question.getQuestionType() == 2) {
						question.setOptionA(optionA.substring(1, optionA.length()));
						question.setOptionB(optionB.substring(1, optionB.length()));
						question.setOptionC(optionC.substring(1, optionC.length()));
						question.setOptionD(optionD.substring(1, optionD.length()));
						question.setOptionE(optionE.substring(1, optionE.length()));
						question.setOptionF(optionF.substring(1, optionF.length()));
						question.setOptionG(optionG.substring(1, optionG.length()));
						question.setOptionH(optionH.substring(1, optionH.length()));
					} else if (question.getQuestionType() == 3) {
						question.setOptionA("正确");
						question.setOptionB("错误");
					}
					Map userMap = (Map) request.getSession().getAttribute("user");

					Integer orgId = Integer.parseInt(userMap.get("orgId") + "");
					question.setOrgId(orgId);
					question.setCreateTime(new Date());
					Integer creatorId = Integer.parseInt(userMap.get("userId") + "");
					String creatorName = userMap.get("username") + "";
					question.setCreatorId(creatorId);
					question.setCreatorName(creatorName);
					Integer dataId=questionApi.saveQuestion(question);
					Map map=(Map)request.getSession().getAttribute("user");
					//数据操作成功，保存操作日志
					if(dataId!=null&&dataId!=0){
						//添加/修改 
						if(question.getQuestionId()==null){  //添加
							logApi.saveLog(dataId, question.getQuestionName(),LogUtil.LOG_QUESTION_MODULEID,LogUtil.LOG_ADD_TYPE,map);
						}else{  //修改
							logApi.saveLog(question.getQuestionId(), question.getQuestionName(),LogUtil.LOG_QUESTION_MODULEID,LogUtil.LOG_UPDATE_TYPE,map);
						}
					}
					message = "succ";
				} else {
					message = "sameQuestionName";
				}
			}
			PrintWriter pWriter = response.getWriter();
			pWriter.write(message.toString());
			pWriter.flush();
			pWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/delQuestion", method = RequestMethod.POST)
	public void delQuestion(HttpServletRequest request, HttpServletResponse response, String questionId) {
		response.setContentType("application/text;charset=utf-8");
		try {
			if (questionId != null) {
				questionApi.deleteQuestionByIds(questionId);
				Map map=(Map)request.getSession().getAttribute("user");
				String id[]=questionId.split(",");
				for (String qId : id) {
					//数据操作成功，保存操作日志
					if(qId!=null){
						logApi.saveLog(Integer.valueOf(qId),null,LogUtil.LOG_QUESTION_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
					}
				}
			}
			PrintWriter pWriter = response.getWriter();
			pWriter.write("succ");
			pWriter.flush();
			pWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/getRandomQuestionPaper", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public List<Map<String, Object>> getRandomQuestionPaper(HttpServletRequest request, Integer s_qtSortId,
			Model model) {
		String qsSortCode = "";
		if (s_qtSortId != null) {
			Map<String, Object> questionSortMap = questionSortApi.getQuestionSortById(s_qtSortId);
			qsSortCode = MapUtils.getString(questionSortMap, "qsSortCode");
		}
		Map userMap = (Map) request.getSession().getAttribute("user");

		Integer orgId = Integer.parseInt(userMap.get("orgId") + "");
		List<Map<String, Object>> list = null;
		list = questionApi.getRandomQuestionPaper(qsSortCode, orgId);
		model.addAttribute("size", list.size());
		return list;
	};

	/**
	 * 试题批量导入
	 * 
	 * @author SUNHENAN
	 * @dete 2017年4月11日14:17:25
	 * @param question
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/importQuestions", produces = {
			"application/json; charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse<Object> importQuestions(Adks_question question,
			@RequestParam(value = "importExcelFile", required = false) MultipartFile importExcelFile,
			HttpServletRequest request, HttpServletResponse response) {
		if (importExcelFile != null && !StringUtils.isEmpty(importExcelFile.getOriginalFilename())) {
			InputStream inputStream = null;
			try {
				inputStream = importExcelFile.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (inputStream != null) {
				ApiResponse<List<Adks_question>> questionsResult = ExcelUtil.readExcelToQuestion(inputStream);
				if (questionsResult.isSuccess()) {
					List<Adks_question> questions = questionsResult.getBody();
					System.out.println("读取的excel获取条数：" + questions.size());

					if (question != null) {
						Map<String, Object> questionSort = questionSortApi.getQuestionSortById(question.getQtSortId());
						if (questionSort != null) {
							for (Adks_question q : questions) {
								q.setQtSortId(question.getQtSortId());
								q.setQsSortName(MapUtils.getString(questionSort, "qsSortName"));
								q.setQsSortCode(MapUtils.getString(questionSort, "qsSortCode"));
								// 课程id
								// 课程名称
								// 学校ID
								Map userMap = (Map) request.getSession().getAttribute("user");
								Integer userId = Integer.parseInt(userMap.get("userId") + "");
								String username = userMap.get("username") + "";
								q.setOrgId(Integer.valueOf(userMap.get("orgId").toString()));
								q.setCreatorId(userId);
								q.setCreatorName(username);
								q.setCreateTime(new Date());
								questionApi.saveQuestion(q);
							}
						}
					}
					return ApiResponse.success("导入成功!共导入" + questions.size() + "条数据", null);
				} else {
					return ApiResponse.fail(500, questionsResult.getMessage());
				}
			} else {
				return ApiResponse.fail(500, "File inputStream is null !");
			}
		}
		return null;
	}

	/**
	 * 试题批量导出
	 * 
	 * @param questionIds(试题id用,分割)
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/exportQuestions", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse<Object> exportQuestions(String questionIds, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<Adks_question> questions = new ArrayList<Adks_question>();
		String[] question = questionIds.split(",");
		if (question != null) {
			for (int i = 0; i < question.length; i++) {
				Map<String, Object> mq = questionApi.getQuestionById(Integer.valueOf(question[i]));
				if (mq != null) {
					Adks_question q = new Adks_question();
					q.setQuestionType(Integer.valueOf(MapUtils.getString(mq, "questionType")));
					q.setQuestionValue(Integer.valueOf(MapUtils.getString(mq, "questionValue")));
					q.setQuestionName(MapUtils.getString(mq, "questionName"));

					q.setOptionA(MapUtils.getString(mq, "optionA"));
					q.setOptionB(MapUtils.getString(mq, "optionB"));
					q.setOptionC(MapUtils.getString(mq, "optionC"));
					q.setOptionD(MapUtils.getString(mq, "optionD"));
					q.setOptionE(MapUtils.getString(mq, "optionE"));
					q.setOptionF(MapUtils.getString(mq, "optionF"));
					q.setOptionG(MapUtils.getString(mq, "optionG"));
					q.setOptionH(MapUtils.getString(mq, "optionH"));

					q.setAnwsers(MapUtils.getString(mq, "anwsers"));
					questions.add(q);
				}
			}
			try {
				ExcelUtil.downloadExcelQuestion(questions, response, "export_questions");
			} catch (Exception e) {
				e.printStackTrace();
				return ApiResponse.fail(500, "该execl文档存在错误，请与下载模板格式一致，重新上传！");
			}
			return ApiResponse.success("导出成功", null);
		}
		return ApiResponse.fail(500, "导出失败");
	}
}
