package com.adks.admin.controller.paper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
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
import com.adks.dubbo.api.data.paper.Adks_paper;
import com.adks.dubbo.api.interfaces.admin.exam.ExamApi;
import com.adks.dubbo.api.interfaces.admin.exam.ExamPaperApi;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.api.interfaces.admin.paper.PaperApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping(value = "/paper")
public class PaperController {
	private final Logger logger = LoggerFactory.getLogger(PaperController.class);

	@Autowired
	private PaperApi paperApi;
	@Autowired
	private ExamPaperApi examPaperApi;
	@Autowired
	private ExamApi examApi;
	@Autowired
	private LogApi logApi;

	@RequestMapping({ "/paperList" })
	public String home(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		logger.debug("进入 PaperController  paperList...");
		return "paper/paperList";
	}

	// 机构界面在使用
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getPaperListJson", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public Page getPaperListJson(HttpServletRequest request, Integer page, Integer rows, String s_paperName) {
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
			if (s_paperName != null) {
				s_paperName = java.net.URLDecoder.decode(s_paperName, "UTF-8");
				map.put("paperName", s_paperName);
				page_bean.setMap(map);
			}
			page_bean = paperApi.getPaperListPage(page_bean);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return page_bean;
	}

	@RequestMapping(value = "/savePaper", method = RequestMethod.POST, produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public Map<String, Object> savePaper(Adks_paper paper, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		Map userMap = (Map) request.getSession().getAttribute("user");
		try {
			if (paper != null && paper.getPaperName() != null) {
				Map<String, Object> checkMap = new HashMap<String, Object>();
				if (paper.getPaperId() != null) {
					checkMap.put("paperId", paper.getPaperId());
				}
				checkMap.put("paperName", paper.getPaperName());
				Map<String, Object> paperMap = paperApi.getPaperByName(checkMap);
				if (paperMap.size() <= 0) {
					Integer paperId;
					Integer orgId = Integer.parseInt(userMap.get("orgId") + "");
					String orgName = userMap.get("orgName") + "";
					Integer creatorId = Integer.parseInt(userMap.get("userId") + "");
					String creatorName = userMap.get("username") + "";
					if (paper.getPaperId() != null) {
						Adks_paper paperParm = paperApi.getPaperById(paper.getPaperId());
						paperParm.setOrgId(orgId);
						paperParm.setOrgName(orgName);
						paperParm.setCreatorId(creatorId);
						paperParm.setCreatorName(creatorName);
						paperParm.setPaperName(paper.getPaperName());
						paperApi.savePaper(paperParm);
						paperId = paper.getPaperId();
					} else {
						paper.setCreateTime(new Date());
						paper.setOrgId(orgId);
						paper.setOrgName(orgName);
						paper.setCreatorId(creatorId);
						paper.setCreatorName(creatorName);
						paperId = paperApi.savePaper(paper);
					}
					//数据操作成功，保存操作日志
					if(paperId!=null&&paperId!=0){
						//添加/修改 
						if(paper.getPaperId()==null){  //添加
							logApi.saveLog(paperId,paper.getPaperName(),LogUtil.LOG_PAPER_MODULEID,LogUtil.LOG_ADD_TYPE,userMap);
						}else{  //修改
							logApi.saveLog(paper.getPaperId(),paper.getPaperName(),LogUtil.LOG_PAPER_MODULEID,LogUtil.LOG_UPDATE_TYPE,userMap);
						}
					}
					Map<String, Object> resMap = new HashMap<>();
					resMap.put("msg", "succ");
					resMap.put("paperId", paperId);
					return resMap;
				} else {
					Map<String, Object> resMap = new HashMap<>();
					resMap.put("mesg", "samePaperName");
					return resMap;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/delPaper", method = RequestMethod.POST)
	public void delPaper(HttpServletRequest request, HttpServletResponse response, String paperId) {
		response.setContentType("application/text;charset=utf-8");
		try {
			if (paperId != null) {
				paperApi.deletePaperByIds(paperId);
				Map map=(Map)request.getSession().getAttribute("user");
				String[] paperIds=paperId.split(",");
				for (String pId : paperIds) {
					//数据操作成功，保存操作日志
					if(pId!=null){
						logApi.saveLog(Integer.valueOf(pId),null,LogUtil.LOG_PAPER_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
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

	@RequestMapping({ "/toConfigurePaper" })
	public String toConfigurePaper(Integer paperId, Model model) {
		logger.debug("配置试卷..." + paperId);
		Adks_paper paper = paperApi.getPaperById(paperId);
		model.addAttribute("paper", paper);
		return "paper/configurePaper";
	}

	@RequestMapping(value = "/isUpdate", method = RequestMethod.POST)
	public void isUpdate(HttpServletRequest request, HttpServletResponse response, Integer paperId)
			throws ParseException {
		response.setContentType("application/text;charset=utf-8");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(sdf.format(new Date()));
			PrintWriter pWriter = response.getWriter();
			if (paperId != null) {
				List<Map<String, Object>> list = examPaperApi.getExamByPaperId(paperId);
				if (list != null && list.size() > 0) {
					for (Map<String, Object> map : list) {
						int examId = MapUtils.getInteger(map, "examId");
						Adks_exam exam = examApi.getExamById(examId);
						if (exam != null) {
							Date startTime = exam.getStartDate();
							Date entTime = exam.getEndDate();
							if (startTime.before(date) && entTime.after(date) || startTime.equals(date)
									|| entTime.equals(date)) {
								pWriter.write("false");
							} else {
								pWriter.write("succ");
							}
						}
					}
				}
			}
			pWriter.flush();
			pWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
