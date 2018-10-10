package com.adks.admin.controller.exam;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.admin.util.LogUtil;
import com.adks.commons.util.DateUtils;
import com.adks.dubbo.api.data.exam.Adks_exam;
import com.adks.dubbo.api.interfaces.admin.exam.ExamApi;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping(value = "/exam")
public class ExamController {
	private final Logger logger = LoggerFactory.getLogger(ExamController.class);

	@Autowired
	private ExamApi examApi;
	@Autowired
	private LogApi logApi;

	@RequestMapping({ "/examList" })
	public String home(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		return "exam/examList";
	}

	// 机构界面在使用
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getExamListJson", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public Page getExamListJson(HttpServletRequest request, Integer page, Integer rows, String s_examName) {
		Page<List<Map<String, Object>>> page_bean = null;
		try {
			page_bean = new Page<List<Map<String, Object>>>();
			page_bean.setCurrentPage(page);
			page_bean.setPageSize(rows);
			Map map = new HashMap<>();
			Map userMap = (Map) request.getSession().getAttribute("user");
			if (userMap != null) {
				map.put("orgId", userMap.get("orgId"));
				page_bean.setMap(map);
			}
			if (StringUtils.isNotBlank(s_examName)) {
				s_examName = java.net.URLDecoder.decode(s_examName, "UTF-8");
				map.put("examName", s_examName);
				page_bean.setMap(map);
			}
			page_bean = examApi.getExamListPage(page_bean);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return page_bean;
	}

	@RequestMapping(value = "/saveExam", method = RequestMethod.POST, produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public Map<String, Object> saveExam(Adks_exam exam, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("*****************");
		response.setContentType("text/html;charset=utf-8");
		try {
			if (exam != null && exam.getExamName() != null) {
				Map<String, Object> checkMap = new HashMap<String, Object>();
				if (exam.getExamId() != null) {
					checkMap.put("examId", exam.getExamId());
				}
				checkMap.put("examName", exam.getExamName());
				Map<String, Object> examMap = examApi.getExamByName(checkMap);
				if (examMap.size() <= 0) {
					if (exam.getStartDate_str() != null) {
						exam.setStartDate(DateUtils.getStr2LDate(exam.getStartDate_str()));
					}
					if (exam.getEndDate_str() != null) {
						exam.setEndDate(DateUtils.getStr2LDate(exam.getEndDate_str()));
					}
					Integer examId;

					Map userMap = (Map) request.getSession().getAttribute("user");
					Integer creatorId = Integer.parseInt(userMap.get("userId") + "");
					String creatorName = userMap.get("username") + "";

					Integer orgId = Integer.parseInt(userMap.get("orgId") + "");
					String orgName = userMap.get("orgName") + "";

					exam.setOrgId(orgId);
					exam.setOrgName(orgName);
					exam.setCreatorId(creatorId);
					exam.setCreatorName(creatorName);
					exam.setCreateTime(new Date());
					Integer dataId=examId = examApi.saveExam(exam);
					//数据操作成功，保存操作日志
					if(dataId!=null&&dataId!=0){
						//添加/修改 
						if(exam.getExamId()==null){  //添加
							logApi.saveLog(dataId,exam.getExamName(),LogUtil.LOG_EXAM_MODULEID,LogUtil.LOG_ADD_TYPE,userMap);
						}else{  //修改
							logApi.saveLog(exam.getExamId(),exam.getExamName(),LogUtil.LOG_EXAM_MODULEID,LogUtil.LOG_UPDATE_TYPE,userMap);
						}
					}
					Map<String, Object> resMap = new HashMap<>();
					resMap.put("msg", "succ");
					resMap.put("examId", examId);
					return resMap;
				} else {
					Map<String, Object> resMap = new HashMap<>();
					resMap.put("mesg", "sameExamName");
					return resMap;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/delExam", method = RequestMethod.POST)
	public void delExam(HttpServletRequest request, HttpServletResponse response, String examId) {
		response.setContentType("application/text;charset=utf-8");
		try {
			if (examId != null) {
				examApi.deleteExamByIds(examId);
				String[] examIds=examId.split(",");
				Map map=(Map)request.getSession().getAttribute("user");
				for (String id : examIds) {
					//数据操作成功，保存操作日志
					if(id!=null){
						logApi.saveLog(Integer.valueOf(id),null,LogUtil.LOG_EXAM_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
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

	@RequestMapping({ "/examPaperList" })
	public String examPaperList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws IOException {
		String examId = request.getParameter("examId");
		model.addAttribute("examId", examId);
		return "exam/examPaperList";
	}

	@RequestMapping({ "/examGradeList" })
	public String examGradeList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws IOException {
		String examId = request.getParameter("examId");
		model.addAttribute("examId", examId);
		return "exam/examGradeList";
	}
}
