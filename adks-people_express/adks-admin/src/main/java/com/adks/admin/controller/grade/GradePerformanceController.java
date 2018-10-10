package com.adks.admin.controller.grade;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
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

import com.adks.admin.util.ExcelUtil;
import com.adks.commons.util.DateTimeUtil;
import com.adks.commons.util.PropertiesFactory;
import com.adks.dubbo.api.interfaces.admin.enumeration.EnumerationApi;
import com.adks.dubbo.api.interfaces.admin.exam.ExamApi;
import com.adks.dubbo.api.interfaces.admin.grade.GradeApi;
import com.adks.dubbo.api.interfaces.admin.grade.GradeCourseApi;
import com.adks.dubbo.api.interfaces.admin.grade.GradeExamApi;
import com.adks.dubbo.api.interfaces.admin.grade.GradeUserApi;
import com.adks.dubbo.api.interfaces.admin.grade.GradeWorkApi;
import com.adks.dubbo.api.interfaces.admin.role.UserroleApi;
import com.adks.dubbo.api.interfaces.admin.user.UserApi;
import com.adks.dubbo.commons.ApiResponse;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping(value = "/gradePerformance")
public class GradePerformanceController {

	private final Logger logger = LoggerFactory.getLogger(GradePerformanceController.class);
	
	@Autowired
	private UserroleApi userroleApi;
	@Autowired
	private GradeUserApi gradeUserApi;
	@Autowired
	private GradeApi gradeApi;
	@Autowired
	private UserApi userApi;
	@Autowired
	private GradeCourseApi gradeCourseApi;
	@Autowired
	private ExamApi examApi;
	@Autowired
	private GradeWorkApi gradeWorkApi;
	@Autowired
	private EnumerationApi enumerationApi;
	
	/**
	 * 
	 * @Title toGradeStudyList
	 * @Description:仅页面跳转，跳转到班级成绩分页列表
	 * @author xrl
	 * @Date 2017年6月16日
	 * @param model
	 * @return
	 */
	@RequestMapping({ "/toGradePerformanceList" })
	public String toGradeStudyList(Model model) {
		logger.debug("@@@@@@跳转到班级学习统计列表页");
		return "/grade/gradePerformanceList";
	}
	
	/**
	 * 
	 * @Title getGradePerformanceListJson
	 * @Description:班级学员成绩分页列表
	 * @author xrl
	 * @Date 2017年6月16日
	 * @param gradeId
	 * @param userName
	 * @param userOrgCode
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getGradePerformanceListJson", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public Page getGradePerformanceListJson(Integer gradeId,String userName,String userOrgCode,Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response) {
		Page<List<Map<String, Object>>> paramPage = new Page<List<Map<String, Object>>>();
		try {
			// 模糊查询 的参数
			Map likemap = new HashMap();
			if (userName != null && !"".equals(userName)) {
				userName = java.net.URLDecoder.decode(userName, "UTF-8");
				likemap.put("userName", userName);
			}
			Map userMap = (Map) request.getSession().getAttribute("user");
			Integer userId = Integer.parseInt(userMap.get("userId") .toString());
			String orgCode = userMap.get("orgCode").toString();
			boolean isRoot = "1".equals(userMap.get("isSuper") + "") ? true : false;
			List<Map<String, Object>> userRole = userroleApi.getRoleListByUser(userId);
			Integer roleId = (Integer) userRole.get(0).get("roleId");
			if (!isRoot) {
				if (roleId == 5 || "5".equals(roleId)) {
					likemap.put("userId", userId);
				} else {
					likemap.put("orgCode", orgCode);
				}
			}
			if (gradeId != null && !"".equals(gradeId)) {
				likemap.put("gradeId", gradeId);
			}
			if(userOrgCode!=null){
				likemap.put("userOrgCode", userOrgCode);
				likemap.put("orgCode", null);
			}
			if((userOrgCode==null)&&(!isRoot)){
				likemap.put("userOrgCode", orgCode);
				likemap.put("orgCode", null);
			}
			paramPage.setMap(likemap);
			paramPage.setPageSize(rows);
			paramPage.setCurrentPage(page);

			paramPage = gradeUserApi.getGradePerformanceListPage(paramPage);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return paramPage;
	}
	
	/**
	 * 
	 * @Title toGradeUserInfo
	 * @Description:查看档案
	 * @author xrl
	 * @Date 2017年6月16日
	 * @param userId
	 * @param gradeId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping({ "/toGradeUserInfo" })
	public String toGradeUserInfo(Integer userId,Integer gradeId,
			Model model,HttpServletRequest request, HttpServletResponse response) {
		if(userId!=null&&gradeId!=null){
			DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
			df.setRoundingMode(RoundingMode.FLOOR);  //取消四舍五入
			Map<String, Object> selMap=new HashMap<>();
			selMap.put("userId", userId);
			selMap.put("gradeId", gradeId);
			//获取用户信息
			Map<String, Object> user=userApi.getUserInfoById(userId);
			if(user!=null&&user.size()>0){
				if(user.get("userNationality")!=null){
					Map<String, Object> enuValue=enumerationApi.getEnumerationValueById((Integer)user.get("userNationality"));
					model.addAttribute("userNationality",enuValue.get("enName"));
				}
			}
			//获取班级信息
			Map<String, Object> grade=gradeApi.getGradeByGradeId(gradeId);
			if(grade!=null&&grade.size()>0){
				if(grade.get("startDate")!=null){
					String start=DateTimeUtil.dateToString((Date)grade.get("startDate"), "yyyy-MM-dd");
					grade.put("startDate", start);
				}
				if(grade.get("endDate")!=null){
					String end=DateTimeUtil.dateToString((Date)grade.get("endDate"), "yyyy-MM-dd");
					grade.put("endDate", end);
				}
			}
			//获取班级用户信息
			Map<String, Object> gradeUser=gradeUserApi.getGradeUserByUserIdAndGradeId(selMap);
			if(gradeUser!=null&&gradeUser.size()>0){
				if(gradeUser.get("requiredPeriod")!=null){
					Double requiredPeriod = (Double)gradeUser.get("requiredPeriod")/Double.valueOf(2700);
					String rPeriod = df.format(requiredPeriod);//返回的是String类型的
					gradeUser.put("requiredPeriod", rPeriod);
				}
				if(gradeUser.get("optionalPeriod")!=null){
					Double optionalPeriod = (Double)gradeUser.get("optionalPeriod")/Double.valueOf(2700);
					String oPeriod = df.format(optionalPeriod);//返回的是String类型的
					gradeUser.put("optionalPeriod", oPeriod);
				}
			}
			String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
			//必修课列表
			selMap.put("gcState",1);
			List<Map<String, Object>> requiredCourseUserList=gradeCourseApi.getGradeUserCourseList(selMap);
			if(requiredCourseUserList!=null&&requiredCourseUserList.size()>0){
				for (Map<String, Object> rCoursemap : requiredCourseUserList) {
					if(rCoursemap.get("credit")!=null){
						Double credit=(Double)rCoursemap.get("credit");
						Double cred = credit/Double.valueOf(2700);
						String cre = df.format(cred);//返回的是String类型的
						rCoursemap.put("credit", cre);
					}
					if(rCoursemap.get("credit")!=null){
						String xkDate=DateTimeUtil.dateToString((Date)rCoursemap.get("xkDate"), "yyyy-MM-dd HH:mm:ss");
						rCoursemap.put("xkDate", xkDate);
					}
					Float data;
					if(rCoursemap.get("studyAllTimeLong") == null){
						data = 0f;
					}else if(rCoursemap.get("studyAllTimeLong") == null || (Integer)rCoursemap.get("studyAllTimeLong") == 0){
						data = 0f;
					}else{
						data = Float.valueOf((Integer)rCoursemap.get("studyAllTimeLong")/Float.valueOf((Integer)rCoursemap.get("courseDuration")));
						data = data * 100;
					}
					DecimalFormat fmt = new DecimalFormat("0"); //不保留小数位
					String dataStr = fmt.format(Double.valueOf(data+""));
					dataStr = dataStr+"%";
					rCoursemap.put("jindu", dataStr);
				}
			}
			
			//选修课列表
			selMap.put("gcState",2);
			List<Map<String, Object>> optionalCourseUserList=gradeCourseApi.getGradeUserCourseList(selMap);
			if(optionalCourseUserList!=null&&optionalCourseUserList.size()>0){
				for (Map<String, Object> oCoursemap : optionalCourseUserList) {
					if(oCoursemap.get("credit")!=null){
						Double credit=(Double)oCoursemap.get("credit");
						Double cred = credit/Double.valueOf(2700);
						String cre = df.format(cred);//返回的是String类型的
						oCoursemap.put("credit", cre);
					}
					if(oCoursemap.get("credit")!=null){
						String xkDate=DateTimeUtil.dateToString((Date)oCoursemap.get("xkDate"), "yyyy-MM-dd HH:mm:ss");
						oCoursemap.put("xkDate", xkDate);
					}
					Float data;
					if(oCoursemap.get("studyAllTimeLong") == null){
						data = 0f;
					}else if(oCoursemap.get("studyAllTimeLong") == null || (Integer)oCoursemap.get("studyAllTimeLong") == 0){
						data = 0f;
					}else{
						data = Float.valueOf((Integer)oCoursemap.get("studyAllTimeLong")/Float.valueOf((Integer)oCoursemap.get("courseDuration")));
						data = data * 100;
					}
					DecimalFormat fmt = new DecimalFormat("0"); //不保留小数位
					String dataStr = fmt.format(Double.valueOf(data+""));
					dataStr = dataStr+"%";
					oCoursemap.put("jindu", dataStr);
				}
			}
			
			//班级考试列表
			List<Map<String, Object>> examScoreList=examApi.getExamScoreByGradeIdAndUserId(selMap);
			Integer examScore=0;
			if(examScoreList!=null&&examScoreList.size()>0){
				for (Map<String, Object> map : examScoreList) {
					examScore+=(Integer)map.get("score");
				}
			}
			gradeUser.put("examScore", examScore);
			//班级作业列表
			List<Map<String, Object>> gradeWorkReplyList=gradeWorkApi.getGradeWorkReplyByGradeIdAndUserId(selMap);
			Integer workScore=0;
			if(gradeWorkReplyList!=null&&gradeWorkReplyList.size()>0){
				for (Map<String, Object> map : gradeWorkReplyList) {
					workScore+=(Integer)map.get("workScore");
				}
			}
			gradeUser.put("workScore", workScore);
			
			model.addAttribute("user", user);
			model.addAttribute("grade", grade);
			model.addAttribute("gradeUser", gradeUser);
			model.addAttribute("requiredCourseUserList", requiredCourseUserList);
			model.addAttribute("optionalCourseUserList", optionalCourseUserList);
			model.addAttribute("examScoreList", examScoreList);
			model.addAttribute("gradeWorkReplyList", gradeWorkReplyList);
			model.addAttribute("ossResource", ossResource);
		}
		return "/grade/gradeUserInfo";
	}
	
	/**
	 * 
	 * @Title exportGradePerformance
	 * @Description：导出班级成绩统计信息
	 * @author xrl
	 * @Date 2017年6月20日
	 * @param exGradeId
	 * @param exOrgCode
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/exportGradePerformance", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse<Object> exportGradePerformance(String exGradeId,String exOrgCode, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<>();
		if(exGradeId!=null&&exGradeId!=""){
			map.put("gradeId", exGradeId);
		}
		if(exOrgCode!=null&&exOrgCode!=""){
			map.put("orgCode",exOrgCode);
		}
		Map<String, Object> gradeMap=gradeApi.getGradeByGradeId(Integer.valueOf(exGradeId));
		List<Map<String, Object>> gradeUserMap=gradeUserApi.getGradePerformanceList(map);
		if(gradeUserMap!=null&&gradeUserMap.size()>0){
			Long time=(new Date()).getTime();
			String fileName="";
			if(gradeMap!=null&&gradeMap.size()>0){
				fileName=(String)gradeMap.get("gradeName")+"-学员成绩统计"+time;
			}else{
				fileName=time.toString();
			}
			try {
				ExcelUtil.downloadExcelGradePerformance(gradeUserMap, response, fileName.toString());
			} catch (Exception e) {
				e.printStackTrace();
				return ApiResponse.fail(500, "导出失败");
			}
			return ApiResponse.success("导出成功", null);
		}
		return ApiResponse.fail(500, "导出失败");
	}
}
