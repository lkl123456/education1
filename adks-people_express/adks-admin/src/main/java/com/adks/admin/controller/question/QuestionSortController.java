package com.adks.admin.controller.question;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.admin.util.LogUtil;
import com.adks.dubbo.api.data.question.Adks_question_sort;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.api.interfaces.admin.question.QuestionApi;
import com.adks.dubbo.api.interfaces.admin.question.QuestionSortApi;

@Controller
@RequestMapping(value = "/questionSort")
public class QuestionSortController {
	private final Logger logger = LoggerFactory.getLogger(QuestionSortController.class);

	@Autowired
	private QuestionSortApi questionSortApi;
	@Autowired
	private QuestionApi questionApi;
	@Autowired
	private LogApi logApi;

	private Integer parent;

	// 机构界面在使用
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getQuestionSortJson", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public List<Map<String, Object>> getQuestionSortList(HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Object>> list = null;
		Map userMap = (Map) request.getSession().getAttribute("user");
		// if(parent!=null){
		// list=questionSortApi.getQuestionSortList(parent);
		// }else{
		// list=questionSortApi.getQuestionSortList(null);
		// }
		int orgId = 0;
		if (userMap != null && userMap.size() > 0) {
			orgId = Integer.valueOf(userMap.get("orgId").toString());
		}
		if (parent == null || "".equals(parent)) {
			parent = 0;
		}
		list = questionSortApi.getQuestionSortList(parent, orgId);
		return list;
	};

	@RequestMapping(value = "/saveQuestionSort", produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public void saveQuestionSort(Adks_question_sort questionSort, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String message = "error";
		Map userMap = (Map) request.getSession().getAttribute("user");
		try {
			if (questionSort != null) {
				if (questionSort.getQtSortID() == null) {
					questionSort.setCreateTime(new Date());
					Integer orgId = Integer.parseInt(userMap.get("orgId") + "");
					String orgName = userMap.get("orgName") + "";
					questionSort.setOrgId(orgId);
					questionSort.setOrgName(orgName);
				}

				Integer creatorId = Integer.parseInt(userMap.get("userId") + "");
				String creatorName = userMap.get("username") + "";
				questionSort.setCreatorId(creatorId);
				questionSort.setCreatorName(creatorName);
				Integer dataId=questionSortApi.saveQuestionSort(questionSort);
				//数据操作成功，保存操作日志
				if(dataId!=null&&dataId!=0){
					//添加/修改 
					if(questionSort.getQtSortID()==null){  //添加
						logApi.saveLog(dataId, questionSort.getQsSortName(),LogUtil.LOG_QUESTIONSORT_MODULEID,LogUtil.LOG_ADD_TYPE,userMap);
					}else{  //修改
						logApi.saveLog(questionSort.getQtSortID(), questionSort.getQsSortName(),LogUtil.LOG_QUESTIONSORT_MODULEID,LogUtil.LOG_UPDATE_TYPE,userMap);
					}
				}
				message = "succ";
			}
			PrintWriter pWriter = response.getWriter();
			pWriter.write(message.toString());
			pWriter.flush();
			pWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/toEditQuestionSort", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public Map<String, Object> toEditQuestionSort(Integer qtSortID) {
		Map<String, Object> questionSort = questionSortApi.getQuestionSortById(qtSortID);
		return questionSort;
	}

	@RequestMapping(value = "/delQuestionSort", method = RequestMethod.POST)
	public void delQuestionSort(HttpServletRequest request, HttpServletResponse response, String qtSortID) {
		response.setContentType("application/text;charset=utf-8");
		try {
			if (qtSortID != null) {
				Map userMap=(Map)request.getSession().getAttribute("user");
				List<Map<String, Object>> list = questionApi.getQuestionByqsSortId(Integer.valueOf(qtSortID));
				String qtSortIdStr = "";
				if (list != null && list.size() > 0) {
					for (Map<String, Object> map : list) {
						qtSortIdStr += map.get("questionId") + ",";
						logApi.saveLog(Integer.valueOf(map.get("questionId")+""),null,LogUtil.LOG_QUESTION_MODULEID, LogUtil.LOG_DELETE_TYPE, userMap);
					}
					qtSortIdStr = qtSortIdStr.substring(0, qtSortIdStr.length() - 1);
					if (StringUtils.isNotEmpty(qtSortIdStr)) {
						questionApi.deleteQuestionByIds(qtSortIdStr);
					}
				}
				//数据操作成功，保存操作日志
				if(qtSortID!=null){
					logApi.saveLog(Integer.valueOf(qtSortID),null,LogUtil.LOG_QUESTIONSORT_MODULEID, LogUtil.LOG_DELETE_TYPE, userMap);
				}
				questionSortApi.deleteQuestionSortByIds(qtSortID);
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
