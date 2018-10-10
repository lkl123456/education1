package com.adks.admin.controller.paper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.admin.util.FreemarkerUtil;
import com.adks.admin.util.LogUtil;
import com.adks.dubbo.api.data.paper.Adks_paper;
import com.adks.dubbo.api.data.paper.Adks_paper_question;
import com.adks.dubbo.api.data.question.Adks_question;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.api.interfaces.admin.paper.PaperApi;
import com.adks.dubbo.api.interfaces.admin.paper.PaperQuestionApi;
import com.adks.dubbo.api.interfaces.admin.question.QuestionApi;
import com.adks.dubbo.commons.Page;

import freemarker.template.TemplateException;

@Controller
@RequestMapping(value = "/paperQuestion")
public class PaperQuestionController {
	private final Logger logger = LoggerFactory.getLogger(PaperQuestionController.class);

	@Autowired
	private PaperQuestionApi paperQuestionApi;
	@Autowired
	private QuestionApi questionApi;
	@Autowired
	private PaperApi paperApi;
	@Autowired
	private LogApi logApi;

	// 机构界面在使用
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getPaperQuestionListJson", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public Page getPaperQuestionListJson(Integer page, Integer rows, Integer paperId) {
		Page<List<Map<String, Object>>> page_bean = null;
		try {
			page_bean = new Page<List<Map<String, Object>>>();
			page_bean.setCurrentPage(page);
			page_bean.setPageSize(rows);
			Map map = new HashMap<>();
			if (paperId != null) {
				map.put("paperId", paperId);
				page_bean.setMap(map);
			}
			page_bean = paperQuestionApi.getPaperQuestionListPage(page_bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page_bean;
	}

	// 修改试题分值
	@RequestMapping(value = "/savePaperQuestion", method = RequestMethod.POST, produces = {
			"application/json; charset=UTF-8" })
	@ResponseBody
	public void savePaperQuestion(Integer paperQsId, Integer score, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (paperQsId != null) {
				Map<String, Object> parmMap = paperQuestionApi.getPaperQuestionById(paperQsId);
				if (parmMap != null) {
					Adks_paper_question paperQuestion = new Adks_paper_question(
							MapUtils.getIntValue(parmMap, "paperId"), MapUtils.getIntValue(parmMap, "qsId"), score,
							MapUtils.getIntValue(parmMap, "questionType"));
					paperQuestion.setPaperQsId(paperQsId);
					Integer dataId=paperQuestionApi.savePaperQuestion(paperQuestion);
					//数据操作成功，保存操作日志
					if(dataId!=null&&dataId!=0){
						Map map=(Map)request.getSession().getAttribute("user");
						//修改 
						logApi.saveLog(paperQsId,null,LogUtil.LOG_PAPERQUESTION_MODULEID,LogUtil.LOG_UPDATE_TYPE,map);
					}
				}
				PrintWriter pWriter = response.getWriter();
				pWriter.write("succ");
				pWriter.flush();
				pWriter.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 手动添加试题
	 * 
	 * @param request
	 * @param response
	 * @param paperId
	 * @param questionId
	 * @param questionValue
	 * @param questionType
	 */
	@RequestMapping(value = "/saveCustomQuestion", method = RequestMethod.POST)
	public void saveCustomQuestion(HttpServletRequest request, HttpServletResponse response, Integer paperId,
			String questionId, String questionValue, String questionType) {
		response.setContentType("application/text;charset=utf-8");
		try {
			if (questionId.split(",").length > 1) {
				String questionIds[] = questionId.split(",");
				String questionValues[] = questionValue.split(",");
				String questionTypes[] = questionType.split(",");
				for (int i = 0; i < questionIds.length; i++) {
					Adks_paper_question paperQuestion = new Adks_paper_question(paperId,
							Integer.parseInt(questionIds[i]), Integer.parseInt(questionValues[i]),
							Integer.parseInt(questionTypes[i]));
					Integer dataId=paperQuestionApi.savePaperQuestion(paperQuestion);
					//数据操作成功，保存操作日志
					if(dataId!=null&&dataId!=0){
						//添加
						Map map=(Map)request.getSession().getAttribute("user");
						logApi.saveLog(dataId,paperId.toString(),LogUtil.LOG_PAPERQUESTION_MODULEID,LogUtil.LOG_ADD_TYPE,map);
					}
				}
			} else {
				Adks_paper_question paperQuestion = new Adks_paper_question(paperId, Integer.parseInt(questionId),
						Integer.parseInt(questionValue), Integer.parseInt(questionType));
				paperQuestionApi.savePaperQuestion(paperQuestion);
				//数据操作成功，保存操作日志
				if(paperId!=null&&paperId!=0){
					//添加
					if(questionId!=null){  //添加
						Map map=(Map)request.getSession().getAttribute("user");
						logApi.saveLog(paperId,questionId,LogUtil.LOG_PAPERQUESTION_MODULEID,LogUtil.LOG_ADD_TYPE,map);
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

	/**
	 * 随机添加试题
	 * 
	 * @param request
	 * @param response
	 * @param paperId
	 * @param nums
	 * @param questionValue
	 * @param questionType
	 */
	@RequestMapping(value = "/saveRandomQuestion", method = RequestMethod.POST)
	public void saveRandomQuestion(HttpServletRequest request, HttpServletResponse response, Integer paperId,
			String nums, String questionValue, String questionType) {
		response.setContentType("application/text;charset=utf-8");
		try {
			String questionValues[] = questionValue.split(",");
			String questionTypes[] = questionType.split(",");
			String questionNums[] = nums.split(",");
			if (nums.split(",").length > 1) {
				for (int i = 0; i < questionNums.length; i++) {
					if (StringUtils.isNotEmpty(questionNums[i])) {
						List<Map<String, Object>> list = questionApi.getRandomQuestion(
								Integer.parseInt(questionTypes[i]), Integer.parseInt(questionNums[i]));
						for (int j = 0; j < list.size(); j++) {
							Adks_paper_question paperQuestion = new Adks_paper_question(paperId,
									MapUtils.getIntValue(list.get(j), "questionId"),
									Integer.parseInt(questionValues[i]), Integer.parseInt(questionTypes[i]));
							Integer dataId=paperQuestionApi.savePaperQuestion(paperQuestion);
							//数据操作成功，保存操作日志
							if(dataId!=null&&dataId!=0){
								//添加
								Map map=(Map)request.getSession().getAttribute("user");
								logApi.saveLog(dataId,paperId.toString(),LogUtil.LOG_PAPERQUESTION_MODULEID,LogUtil.LOG_ADD_TYPE,map);
							}
						}
					}
				}
			} else {
				if (StringUtils.isNotEmpty(nums)) {
					List<Map<String, Object>> list = questionApi.getRandomQuestion(Integer.parseInt(questionTypes[0]),
							Integer.parseInt(questionNums[0]));
					for (int j = 0; j < list.size(); j++) {
						Adks_paper_question paperQuestion = new Adks_paper_question(paperId,
								MapUtils.getIntValue(list.get(j), "questionId"), Integer.parseInt(questionValues[0]),
								Integer.parseInt(questionTypes[0]));
						Integer dataId=paperQuestionApi.savePaperQuestion(paperQuestion);
						//数据操作成功，保存操作日志
						if(dataId!=null&&dataId!=0){
							//添加
							Map map=(Map)request.getSession().getAttribute("user");
							logApi.saveLog(dataId,paperId.toString(),LogUtil.LOG_PAPERQUESTION_MODULEID,LogUtil.LOG_ADD_TYPE,map);
						}
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

	@RequestMapping(value = "/delPaperQuestion", method = RequestMethod.POST)
	public void delPaperQuestion(HttpServletRequest request, HttpServletResponse response, String paperQsId) {
		response.setContentType("application/text;charset=utf-8");
		try {
			if (paperQsId != null) {
				paperQuestionApi.deletePaperQuestionByIds(paperQsId);
				Map map=(Map)request.getSession().getAttribute("user");
				String[] paperQsIds=paperQsId.split(",");
				for (String pQsId : paperQsIds) {
					//数据操作成功，保存操作日志
					if(pQsId!=null){
						logApi.saveLog(Integer.valueOf(pQsId),null,LogUtil.LOG_PAPERQUESTION_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
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

	@RequestMapping({ "/paperRandomQuestion" })
	public String home(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		return "paper/paperRandomQuestion";
	}

	@RequestMapping({ "/paperCustomQuestion" })
	public String paperCustomQuestion(HttpServletRequest request, HttpServletResponse response, Model model)
			throws IOException {
		return "paper/paperCustomQuestion";
	}

	/**
	 * 预览试卷
	 *
	 * @return
	 */
	@RequestMapping(value = "/paperView")
	public String paperView(HttpServletRequest request, HttpServletResponse response, Model model, Integer paperId)
			throws Exception {
		// 试卷ID
		Adks_paper paper = paperApi.getPaperById(paperId);
		// 获取试卷下的试题
		List<Adks_paper_question> questions = paperQuestionApi.getPaperQuestionListByPaperId(paperId);
		if (questions != null) {
			List<Map<String, Object>> dQuestion = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> dxQuestion = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> pdQuestion = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> tkQuestion = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> wdQuestion = new ArrayList<Map<String, Object>>();
			for (Adks_paper_question adks_paper_question : questions) {
				Map<String, Object> map = questionApi.getQuestionById(adks_paper_question.getQsId());
				if (map != null) {
					Integer qType = (Integer) map.get("questionType");
					if (qType == 1) {
						dQuestion.add(map);
					} else if (qType == 2) {
						dxQuestion.add(map);
					} else if (qType == 3) {
						pdQuestion.add(map);
					} else if (qType == 4) {
						tkQuestion.add(map);
					} else if (qType == 5) {
						wdQuestion.add(map);
					}
				}
			}
			model.addAttribute("dQuestion", dQuestion);// 单选试题明细
			model.addAttribute("dxQuestion", dxQuestion);// 多选试题明细
			model.addAttribute("pdQuestion", pdQuestion);// 判断试题明细
			model.addAttribute("tkQuestion", tkQuestion);// 填空试题明细
			model.addAttribute("wdQuestion", wdQuestion);// 简答试题明细
		}
		model.addAttribute("paper", paper);
		return "paper/paperView";
	}

	/**
	 * 生成html页面
	 * 
	 * @param request
	 * @param response
	 * @param paperId
	 */
	@RequestMapping(value = "/savePaperHtml", method = RequestMethod.POST)
	public void savePaperHtml(HttpServletRequest request, HttpServletResponse response, Integer paperId)
			throws TemplateException {
		response.setContentType("application/text;charset=utf-8");
		try {
			Adks_paper paper = paperApi.getPaperById(paperId);
			List<Adks_paper_question> questions = paperQuestionApi.getPaperQuestionListByPaperId(paperId);
			if (questions != null) {
				List<Adks_question> dQuestion = new ArrayList<Adks_question>();
				List<Adks_question> dxQuestion = new ArrayList<Adks_question>();
				List<Adks_question> pdQuestion = new ArrayList<Adks_question>();
				List<Adks_question> tkQuestion = new ArrayList<Adks_question>();
				List<Adks_question> wdQuestion = new ArrayList<Adks_question>();
				for (Adks_paper_question adks_paper_question : questions) {
					Adks_question question = questionApi.getById(adks_paper_question.getQsId());
					question.setQuestionValue(adks_paper_question.getScore());
					if (question != null) {
						Integer qType = (Integer) question.getQuestionType();
						if (qType == 1) {
							dQuestion.add(question);
						} else if (qType == 2) {
							dxQuestion.add(question);
						} else if (qType == 3) {
							pdQuestion.add(question);
						} else if (qType == 4) {
							tkQuestion.add(question);
						} else if (qType == 5) {
							wdQuestion.add(question);
						}
					}
				}
				String paperHtmlAdress = "";
				if (StringUtils.isNotEmpty(paper.getPaperHtmlAdress())) {
					paperHtmlAdress = paper.getPaperHtmlAdress();
				}
				String htmlPath = FreemarkerUtil.getPaperHtmlPath(paper, paperHtmlAdress, request, dQuestion,
						dxQuestion, pdQuestion, tkQuestion, wdQuestion);
				paper.setPaperHtmlAdress(htmlPath);
				paperApi.savePaper(paper);
			}

			PrintWriter pWriter = response.getWriter();
			pWriter.write("succ");
			pWriter.flush();
			pWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
