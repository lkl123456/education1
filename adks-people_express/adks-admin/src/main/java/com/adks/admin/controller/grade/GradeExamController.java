package com.adks.admin.controller.grade;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import com.adks.dubbo.api.interfaces.admin.grade.GradeApi;
import com.adks.dubbo.api.interfaces.admin.grade.GradeExamApi;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.commons.Page;

/**
 * 
 * ClassName GradeExamController
 * 
 * @Description：班级考试
 * @author xrl
 * @Date 2017年4月1日
 */
@Controller
@RequestMapping(value = "/gradeExam")
public class GradeExamController {

	private final Logger logger = LoggerFactory.getLogger(GradeExamController.class);

	@Autowired
	private GradeExamApi gradeExamApi;

	@Autowired
	private GradeApi gradeApi;
	@Autowired
	private LogApi logApi;

	/**
	 * 
	 * @Title gradeExamList
	 * @Description:已配置的班级考试
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param gradeId
	 * @param examName
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/gradeExamList", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public Page gradeExamList(Integer gradeId, String examName, Integer page, Integer rows) {
		System.out.println("gradeId:" + gradeId);
		Page<List<Map<String, Object>>> paramPage = new Page<List<Map<String, Object>>>();
		// 先获取grade_user分页信息，在service中，获取user表的用户信息拼接，返回controller
		// 模糊查询 的参数
		Map likemap = new HashMap();
		if (examName != null && !"".equals(examName)) {
			likemap.put("examName", examName);
		}
		if (gradeId != null && !"".equals(gradeId)) {
			likemap.put("gradeId", gradeId);
		}
		paramPage.setMap(likemap);
		paramPage.setPageSize(rows);
		paramPage.setCurrentPage(page);

		return gradeExamApi.getGradeExamListPage(paramPage);
	}

	/**
	 * 
	 * @Title gradeSelExamList
	 * @Description：未选班级考试
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param gradeId
	 * @param examName
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/gradeSelExamList", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public Page gradeSelExamList(HttpServletRequest request, HttpServletResponse response, Integer gradeId,
			String examName, Integer page, Integer rows) {
		System.out.println("gradeId" + gradeId);
		Page<List<Map<String, Object>>> paramPage = new Page<List<Map<String, Object>>>();
		try {
			// 模糊查询 的参数
			Map likemap = new HashMap();
			// 搜索的examName
			if (examName != null && !"".equals(examName)) {
				examName = java.net.URLDecoder.decode(examName, "UTF-8");
				likemap.put("examName", examName);
			}
			// 获取已加入班级的用户的ID
			List<Map<String, Object>> examIdsMap = gradeExamApi.getGradeExamByGradeId(gradeId);
			if (examIdsMap != null && examIdsMap.size() > 0) {

				StringBuilder sBuilder = new StringBuilder();
				for (Map<String, Object> map : examIdsMap) {
					if (map.get("examId") != null) {
						sBuilder.append(map.get("examId").toString() + ",");
					}
				}
				likemap.put("examIds", sBuilder.substring(0, sBuilder.length() - 1));
			}
			// 判断 部门搜索条件
			Map userMap = (Map) request.getSession().getAttribute("user");
			String orgCode = userMap.get("orgCode") + "";
			boolean isRoot = "1".equals(userMap.get("isSuper") + "") ? true : false;
			if (!isRoot) {
				likemap.put("orgCode", orgCode);
			}

			paramPage.setMap(likemap);
			paramPage.setPageSize(rows);
			paramPage.setCurrentPage(page);

			// 获取班级未选学员分页信息
			paramPage = gradeExamApi.getSelExamListPage(paramPage);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return paramPage;
	}

	/**
	 * 
	 * @Title addGradeExams
	 * @Description：添加班级考试
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param examIds
	 * @param gradeId
	 * @param gradeName
	 * @param response
	 */
	@RequestMapping(value = "/addGradeExams", method = RequestMethod.GET)
	public void addGradeExams(String examIds, Integer gradeId, String gradeName, HttpServletResponse response,HttpServletRequest request) {
		response.setContentType("application/text;charset=utf-8");
		try {
			System.out.println(examIds + "=============");
			if (examIds != null && examIds.length() > 0) {
				Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
				String[] uids = examIds.split(",");
				for (String examId : uids) {
					Map<String, Object> courseById = gradeExamApi.getExamByExamId(Integer.parseInt(examId));
					insertColumnValueMap.put("examId", examId);
					insertColumnValueMap.put("gradeId", gradeId);
					insertColumnValueMap.put("gradeName", java.net.URLDecoder.decode(gradeName, "UTF-8"));

					gradeExamApi.saveGradeExam(insertColumnValueMap);
					//数据操作成功，保存操作日志
					if(examId!=null){
						Map map=(Map)request.getSession().getAttribute("user");
						//添加/修改 
						logApi.saveLog(Integer.valueOf(examId),java.net.URLDecoder.decode(gradeName, "UTF-8"),LogUtil.LOG_GRADEEXAM_MODULEID,LogUtil.LOG_ADD_TYPE,map);
					}
				}
			}

			PrintWriter pWriter = response.getWriter();
			pWriter.write("succ");
			pWriter.flush();
			pWriter.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title removeGradeExams
	 * @Description：移除班级考试
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param examIds
	 * @param gradeId
	 * @param response
	 */
	@RequestMapping(value = "/removeGradeExams", method = RequestMethod.GET)
	public void removeGradeExams(String examIds, Integer gradeId, HttpServletResponse response,HttpServletRequest request) {
		response.setContentType("application/text;charset=utf-8");
		try {
			System.out.println(examIds + "=============");
			if (examIds != null && examIds.length() > 0) {
				Map<String, Object> updateGradeValueMap = new HashMap<String, Object>();
				Map map=(Map)request.getSession().getAttribute("user");
				String[] uids = examIds.split(",");
				for (String examId : uids) {
					updateGradeValueMap.put("gradeId", gradeId);
					updateGradeValueMap.put("examId", examId);
					gradeExamApi.removeGradeExam(updateGradeValueMap);
					//数据操作成功，保存操作日志
					if(examId!=null){
						logApi.saveLog(Integer.valueOf(examId),gradeId.toString(),LogUtil.LOG_GRADEEXAM_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
					}
				}
			}

			PrintWriter pWriter = response.getWriter();
			pWriter.write("succ");
			pWriter.flush();
			pWriter.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title gradeExamInfoList
	 * @Description:考试授权的所有班级
	 * @author shn
	 * @Date 2017年4月14日11:49:31
	 * @param gradeName
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/gradeExamInfoList", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public Page gradeExamInfoList(String gradeName, Integer examId, Integer page, Integer rows) {
		Page<List<Map<String, Object>>> paramPage = new Page<List<Map<String, Object>>>();
		// 先获取grade_user分页信息，在service中，获取user表的用户信息拼接，返回controller
		// 模糊查询 的参数
		Map likemap = new HashMap();
		try {
			if (gradeName != null && !"".equals(gradeName)) {
				likemap.put("gradeName", URLDecoder.decode(gradeName, "UTF-8"));
			}
			if (examId != null && examId != 0) {
				likemap.put("examId", examId);
			}
			paramPage.setMap(likemap);
			paramPage.setPageSize(rows);
			paramPage.setCurrentPage(page);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gradeExamApi.getGradeExamInfoListPage(paramPage);
	}

	@RequestMapping({ "/addExamGradeInfo" })
	public String ExamPaper(HttpServletRequest request, HttpServletResponse response, Model model, String examGradeid,
			String examId) throws IOException {
		model.addAttribute("examGradeid", examGradeid);
		model.addAttribute("examId", examId);
		return "exam/addExamGrade";
	}

	/**
	 * 
	 * @Title addExamGrades
	 * @Description：新增被考试授权的班级
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param examIds
	 * @param gradeId
	 * @param gradeName
	 * @param response
	 */
	@RequestMapping(value = "/addExamGrades", method = RequestMethod.GET)
	public void addExamGrades(Integer examId, String gradeIds, String gradeName, HttpServletResponse response,HttpServletRequest request) {
		response.setContentType("application/text;charset=utf-8");
		try {
			System.out.println(gradeIds + "=============");
			if (gradeIds != null && gradeIds.length() > 0) {
				Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
				String[] gradeStr = gradeIds.split(",");
				for (String gradeId : gradeStr) {

					Map<String, Object> courseById = gradeApi.loadEditGradeFormData(Integer.valueOf(gradeId));

					insertColumnValueMap.put("examId", examId);
					insertColumnValueMap.put("gradeId", Integer.valueOf(gradeId));
					insertColumnValueMap.put("gradeName", courseById.get("gradeName"));

					gradeExamApi.saveGradeExam(insertColumnValueMap);
					//数据操作成功，保存操作日志
					if(examId!=null){
						Map map=(Map)request.getSession().getAttribute("user");
						//添加/修改 
						logApi.saveLog(Integer.valueOf(examId),java.net.URLDecoder.decode(gradeName, "UTF-8"),LogUtil.LOG_GRADEEXAM_MODULEID,LogUtil.LOG_ADD_TYPE,map);
					}
				}
			}

			PrintWriter pWriter = response.getWriter();
			pWriter.write("succ");
			pWriter.flush();
			pWriter.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title removeGradeExams
	 * @Description：移除已经被考试授权的班级
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param examIds
	 * @param gradeId
	 * @param response
	 */
	@RequestMapping(value = "/removeExamGrades", method = RequestMethod.GET)
	public void removeExamGrades(Integer examId, String gradeIds, HttpServletResponse response,HttpServletRequest request) {
		response.setContentType("application/text;charset=utf-8");
		try {
			System.out.println(gradeIds + "=============");
			if (gradeIds != null && gradeIds.length() > 0) {
				Map<String, Object> updateGradeValueMap = new HashMap<String, Object>();
				String[] gradeStr = gradeIds.split(",");
				for (String gradeId : gradeStr) {
					updateGradeValueMap.put("examId", examId);
					updateGradeValueMap.put("gradeId", Integer.valueOf(gradeId));
					gradeExamApi.removeGradeExam(updateGradeValueMap);
					//数据操作成功，保存操作日志
					if(examId!=null){
						Map map=(Map)request.getSession().getAttribute("user");
						logApi.saveLog(Integer.valueOf(examId),gradeId.toString(),LogUtil.LOG_GRADEEXAM_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
					}
				}
			}

			PrintWriter pWriter = response.getWriter();
			pWriter.write("succ");
			pWriter.flush();
			pWriter.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
