package com.adks.admin.controller.exam;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.admin.util.LogUtil;
import com.adks.dubbo.api.data.exam.Adks_exam;
import com.adks.dubbo.api.data.exam.Adks_exam_paper;
import com.adks.dubbo.api.data.paper.Adks_paper;
import com.adks.dubbo.api.interfaces.admin.exam.ExamApi;
import com.adks.dubbo.api.interfaces.admin.exam.ExamPaperApi;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.api.interfaces.admin.paper.PaperApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping(value = "/examPaper")
public class ExamPaperController {
	private final Logger logger = LoggerFactory.getLogger(ExamPaperController.class);

	@Autowired
	private ExamPaperApi examPaperApi;
	@Autowired
	private ExamApi examApi;
	@Autowired
	private PaperApi paperApi;
	@Autowired
	private LogApi logApi;

	// 机构界面在使用
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getExamPaperListJson", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public Page getExamPaperListJson(Integer page, Integer rows, Integer examId, String s_paperName) {
		Page<List<Map<String, Object>>> page_bean = null;
		try {
			page_bean = new Page<List<Map<String, Object>>>();
			page_bean.setCurrentPage(page);
			page_bean.setPageSize(rows);
			Map map = new HashMap<>();
			if (s_paperName != null) {
				map.put("paperName", java.net.URLDecoder.decode(s_paperName, "UTF-8"));
				page_bean.setMap(map);
			}
			if (examId != null) {
				map.put("examId", examId);
				page_bean.setMap(map);
			}
			page_bean = examPaperApi.getExamPaperListPage(page_bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page_bean;
	}

	@RequestMapping({ "/addExamPaper" })
	public String ExamPaper(HttpServletRequest request, HttpServletResponse response, Model model, int examId,
			String epid) throws IOException {
		Adks_exam exam = examApi.getExamById(examId);
		if (exam != null)
			model.addAttribute("scoreSum", exam.getScoreSum());
		model.addAttribute("epid", epid);
		return "exam/addExamPaper";
	}

	@RequestMapping(value = "/delExamPaper", method = RequestMethod.POST)
	public void delPaperQuestion(HttpServletRequest request, HttpServletResponse response, String id) {
		response.setContentType("application/text;charset=utf-8");
		try {
			if (id != null) {
				Map map=(Map)request.getSession().getAttribute("user");
				examPaperApi.deleteExamPaperByIds(id);
				String[] ids=id.split(",");
				for (String examPaperId : ids) {
					//数据操作成功，保存操作日志
					if(examPaperId!=null){
						logApi.saveLog(Integer.valueOf(examPaperId),null,LogUtil.LOG_EXAMPAPER_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
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

	@RequestMapping(value = "/saveExamPaper", method = RequestMethod.POST)
	public void saveExamPaper(HttpServletRequest request, HttpServletResponse response, Integer examId, String paperId,
			String paperName) {
		response.setContentType("application/text;charset=utf-8");
		try {
			Map map=(Map)request.getSession().getAttribute("user");
			if (paperId.split(",").length > 1) {
				String paperIds[] = paperId.split(",");
				String paperNames[] = paperName.split(",");
				for (int i = 0; i < paperIds.length; i++) {
					Adks_paper p = paperApi.getPaperById(Integer.parseInt(paperIds[i]));
					Adks_exam_paper examPaper = new Adks_exam_paper(Integer.parseInt(paperIds[i]), examId,
							paperNames[i], p.getPaperHtmlAdress());
					examPaperApi.saveExamPaper(examPaper);
					//数据操作成功，保存操作日志
					if(examId!=null&&examId!=0){
						//添加/修改 
						if(examPaper.getPaperId()!=null){  //添加
							logApi.saveLog(examId,p.getPaperId().toString(),LogUtil.LOG_EXAMPAPER_MODULEID,LogUtil.LOG_ADD_TYPE,map);
						}
					}
				}
			} else {
				Adks_paper p = paperApi.getPaperById(Integer.parseInt(paperId));
				Adks_exam_paper examPaper = new Adks_exam_paper(Integer.parseInt(paperId), examId, paperName,
						p.getPaperHtmlAdress());
				examPaperApi.saveExamPaper(examPaper);
				//数据操作成功，保存操作日志
				if(examId!=null&&examId!=0){
					//添加/修改 
					if(examPaper.getPaperId()!=null){  //添加
						logApi.saveLog(examId,p.getPaperId().toString(),LogUtil.LOG_EXAMPAPER_MODULEID,LogUtil.LOG_ADD_TYPE,map);
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
}
