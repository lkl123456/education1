package com.adks.admin.controller.grade;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.admin.util.ExcelUtil;
import com.adks.commons.util.ComUtil;
import com.adks.commons.util.PropertiesFactory;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.interfaces.admin.course.CourseApi;
import com.adks.dubbo.api.interfaces.admin.course.CourseUserApi;
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
/**
 * 
 * ClassName GradeStudyController
 * @Description:班级学习统计
 * @author xrl
 * @Date 2017年6月14日
 */
@Controller
@RequestMapping(value = "/gradeStudy")
public class GradeStudyController {

	private final Logger logger = LoggerFactory.getLogger(GradeStudyController.class);
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
	private CourseApi courseApi;
	@Autowired
	private CourseUserApi courseUserApi;
	@Autowired
	private GradeWorkApi gradeWorkApi;
	@Autowired
	private GradeExamApi gradeExamApi;
	@Autowired
	private ExamApi examApi;
	
	/**
	 * 
	 * @Title toGradeStudyList
	 * @Description：仅页面跳转，跳转到班级学习统计列表页
	 * @author xrl
	 * @Date 2017年6月14日
	 * @param model
	 * @return
	 */
	@RequestMapping({ "/toGradeStudyList" })
	public String toGradeStudyList(Model model) {
		logger.debug("@@@@@@跳转到班级学习统计列表页");
		return "/grade/gradeStudyStatisticsList";
	}
	
	/**
	 * 
	 * @Title getGradeStudyListJson
	 * @Description：班级学习统计
	 * @author xrl
	 * @Date 2017年6月14日
	 * @param gradeId
	 * @param userName
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getGradeStudyListJson", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public Page getGradeStudyListJson(Integer gradeId,String userName,String userOrgCode,Integer page, Integer rows,
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
			}
			paramPage.setMap(likemap);
			paramPage.setPageSize(rows);
			paramPage.setCurrentPage(page);

			paramPage = gradeUserApi.getGradeStudyListPage(paramPage);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return paramPage;
	}
	
	/**
	 * 
	 * @Title updateGradeUserGraduate
	 * @Description
	 * @author xrl
	 * @Date 2017年6月15日
	 * @param gradeUserIds
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/updateGradeUserGraduate",method=RequestMethod.GET)
	public void updateGradeUserGraduate(String gradeUserIds,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("application/text;charset=utf-8");
    	try {
    		if(gradeUserIds != null && gradeUserIds.length() > 0){
    			Map<String,Object> insertColumnValueMap = new HashMap<String,Object>();
    			String[] guids = gradeUserIds.split(",");
    			for (String gradeUserId : guids) {
    				gradeUserApi.updateGradeUserGraduate(Integer.valueOf(gradeUserId));
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
	 * @Title toGradeUserCourseInfo
	 * @Description：跳转到学习详情
	 * @author xrl
	 * @Date 2017年6月15日
	 * @param gradeUserId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping({ "/toGradeUserCourseInfo" })
	public String toGradeUserCourseInfo(Integer gradeUserId,Model model,HttpServletRequest request, HttpServletResponse response) {
		logger.debug("@@@@@@跳转到学习详情");
		//获取班级用户信息
		Map<String, Object> gradeUserMap=gradeUserApi.getGradeUserByGradeUserId(gradeUserId);
		//获取用户信息
		Map<String, Object> userMap=null;
		//获取班级信息
		Map<String, Object> gradeMap=null;
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
		//df.setRoundingMode(RoundingMode.FLOOR);  //取消四舍五入
		if(gradeUserMap.size()>0){
			if(gradeUserMap.get("userId")!=null){
				userMap=userApi.getUserInfoById((Integer)gradeUserMap.get("userId"));
				if(userMap.get("userName")!=null){
					gradeUserMap.put("uName", userMap.get("userName"));
				}
				if(userMap.get("headPhoto")!=null){
					gradeUserMap.put("headPhoto", userMap.get("headPhoto"));
				}
				if(userMap.get("orgName")!=null){
					gradeUserMap.put("orgName", userMap.get("orgName"));
				}
			}
			if(gradeUserMap.get("gradeId")!=null){
				gradeMap=gradeApi.getGradeByGradeId((Integer)gradeUserMap.get("gradeId"));
			}
			if(gradeUserMap.get("requiredPeriod")!=null){
				Double requiredPeriod=(Double)gradeUserMap.get("requiredPeriod");
				Double rPeriod = requiredPeriod/Double.valueOf(2700);
				String rp = df.format(rPeriod);//返回的是String类型的
				gradeUserMap.put("requiredPeriod",rp);
			}
			if(gradeUserMap.get("optionalPeriod")!=null){
				Double optionalPeriod=(Double)gradeUserMap.get("optionalPeriod");
				Double oPeriod = optionalPeriod/Double.valueOf(2700);
				String op = df.format(oPeriod);//返回的是String类型的
				gradeUserMap.put("optionalPeriod",op);
			}
		}
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
	    model.addAttribute(ossResource, ossResource);
		model.addAttribute("grade", gradeMap);
		model.addAttribute("gradeUser", gradeUserMap);
		return "/grade/gradeUserCourseInfo";
	}
	
	/**
	 * 
	 * @Title getGradeUserCourseListJson
	 * @Description：获取班级学员课程分页列表
	 * @author xrl
	 * @Date 2017年6月15日
	 * @param gradeId
	 * @param userId
	 * @param courseName
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getGradeUserCourseListJson", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public Page getGradeUserCourseListJson(Integer gradeId,Integer userId,String courseName,
			Integer page, Integer rows,HttpServletRequest request, HttpServletResponse response) {
		Page<List<Map<String, Object>>> paramPage = new Page<List<Map<String, Object>>>();
		try {
			// 模糊查询 的参数
			Map likemap = new HashMap();
			if (courseName != null && !"".equals(courseName)) {
				courseName = java.net.URLDecoder.decode(courseName, "UTF-8");
				likemap.put("courseName", courseName);
			}
			if(gradeId!=null){
				likemap.put("gradeId", gradeId);
			}
			if(userId!=null){
				likemap.put("userId", userId);
			}
			paramPage.setMap(likemap);
			paramPage.setPageSize(rows);
			paramPage.setCurrentPage(page);

			paramPage = gradeCourseApi.getGradeUserCoursePage(paramPage);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return paramPage;
	}
	
	/**
	 * 
	 * @Title updateGradeUserCourse
	 * @Description:完成学习
	 * @author xrl
	 * @Date 2017年6月15日
	 * @param courseId
	 * @param userId
	 * @param isOver
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updateGradeUserCourse", method = RequestMethod.GET, produces = { "text/html;charset=utf-8" })
   	@ResponseBody
   	public Map<String, Object> updateGradeUserCourse(Integer gradeId,Integer userId,String courseIds
   			,HttpServletRequest request,HttpServletResponse response) {
		if(gradeId!=null&&userId!=null&&courseIds.length()>0){
			Map<String,Object> selMap = new HashMap<String,Object>();
			if(courseIds != null && courseIds.length() > 0){
    			selMap.put("gradeId", gradeId);
    			selMap.put("userId", userId);
    			String[] cids = courseIds.split(",");
    			for (String courseId : cids) {
    				selMap.put("courseId", courseId);
    				Map<String, Object> cuMap=courseUserApi.getCourseUserByUserIdAndCourseId(selMap);
    				if(cuMap.size()<=0){
    					Adks_course course=courseApi.getCourseById(Integer.valueOf(courseId));
    					Map<String, Object> courseuser=new HashMap<>();
    					courseuser.put("userId",userId);
    					courseuser.put("courseId",course.getCourseId());
    					courseuser.put("courseCode",course.getCourseCode());
    					courseuser.put("courseName",course.getCourseName());
    					courseuser.put("isOver",1);
    					courseuser.put("grades",gradeId.toString());
    					courseuser.put("authorId",course.getAuthorId());
    					courseuser.put("courseCwType",course.getCourseType());
    					courseuser.put("courseDuration",course.getCourseDuration());
    					courseuser.put("courseDurationLong",course.getCourseTimeLong());
    					courseuser.put("lastPosition",course.getCourseDuration());
    					courseuser.put("isCollection",2);
    					courseuser.put("xkDate",new Date());
    					courseuser.put("lastStudyDate",new Date());
    					courseuser.put("studyAllTimeLong",course.getCourseDuration());
    					courseuser.put("studyAllTime",course.getCourseTimeLong());
    					courseuser.put("courseImg",course.getCoursePic());
    					courseuser.put("studyCourseTime",course.getCourseDuration());
    					courseUserApi.completeCourseUser(courseuser,null);
    				}else{
    					if(cuMap.get("isOver")!=null){
    						if((Integer)cuMap.get("isOver")==2){
    							Map<String, Object> updateMap=new HashMap<>();
    							if(ComUtil.isNotNullOrEmpty((String)cuMap.get("grades"))){
    								String[] ids=((String)cuMap.get("grades")).split(",");
    								if(!ArrayUtils.contains(ids,gradeId.toString())){
    									updateMap.put("grades", (String)cuMap.get("grades")+","+gradeId);
    								}
    							}else{
    								updateMap.put("grades",gradeId.toString());
    							}
    							updateMap.put("isOver",1);
    							updateMap.put("lastPosition",cuMap.get("courseDuration"));
    							updateMap.put("lastStudyDate",new Date());
    							updateMap.put("studyAllTimeLong",cuMap.get("courseDuration"));
    							updateMap.put("studyAllTime",cuMap.get("studyAllTime"));
    							updateMap.put("studyCourseTime",cuMap.get("courseDuration"));
    							courseUserApi.completeCourseUser(updateMap,(Integer)cuMap.get("courseUserId"));
        					}
    					}
    				}
    				Map<String, Object> gradeCourse=gradeCourseApi.getGradeCourseByGradeIdAndCourseId(selMap);
        			Map<String, Object> gradeUser=gradeUserApi.getGradeUserByUserIdAndGradeId(selMap);
        			if(gradeUser!=null&&gradeUser.size()>0){
        				if(gradeCourse!=null&&gradeCourse.size()>0){
        					if((Integer)gradeCourse.get("gcState")==1){
        						if(gradeUser.get("requiredPeriod")==null){
        							selMap.put("requiredPeriod",gradeCourse.get("credit"));
        						}else{
        							selMap.put("requiredPeriod",(Double)gradeCourse.get("credit")+(Double)gradeUser.get("requiredPeriod"));
        						}
        					}else if((Integer)gradeCourse.get("gcState")==2){
        						if(gradeUser.get("optionalPeriod")==null){
        							selMap.put("optionalPeriod",gradeCourse.get("credit"));
        						}else{
        							selMap.put("optionalPeriod",(Double)gradeCourse.get("credit")+(Double)gradeUser.get("optionalPeriod"));
        						}
        					}
        					gradeUserApi.addGradeUserCredit(selMap);
        				}
        			}
				}
    			//判断是否毕业
    			//获取班级信息
    			Map<String, Object> gradeMap=gradeApi.getGradeByGradeId(gradeId);
    			//结业条件 必修学时 选修学时  
    			Map<String, Object> selMap1=new HashMap<>();
    			selMap1.put("gradeId", gradeId);
    			Double requiredPeriod=0.0;
    			if(gradeMap.get("requiredPeriod")!=null){
    				//获取班级必修学时
    				selMap1.put("gcState", 1);
    				requiredPeriod=gradeCourseApi.getGradeCourseCredit(selMap1);
    				if(requiredPeriod==null){
    					requiredPeriod=0.0;
    				}
    				//requiredPeriod=(Integer)gradeMap.get("requiredPeriod");
    			}
    			Double optionalPeriod=0.0;
    			if(gradeMap.get("optionalPeriod")!=null){
    				//获取班级选修学时
    				selMap1.put("gcState", 2);
    				optionalPeriod=gradeCourseApi.getGradeCourseCredit(selMap1);
    				if(optionalPeriod==null){
    					optionalPeriod=0.0;
    				}
    				//optionalPeriod=(Integer)gradeMap.get("optionalPeriod");
    			}
    			Integer workRequire=2;
    			if(gradeMap.get("workRequire")!=null){
    				workRequire=(Integer)gradeMap.get("workRequire");   //论文（作业）   有：1   无：2
    			}
    			Integer examRequire=2;
    			if(gradeMap.get("examRequire")!=null){
    				examRequire=(Integer)gradeMap.get("examRequire");   //考试                	 有：1  无：2
    			}
    			
    			Integer target=2;   //毕业需满足的条件数，初始值为2，选修学时、必修学时
    			
    			//获取班级作业信息
    			List<Map<String, Object>> gradeWorkList=new ArrayList<>();
    			if(workRequire==1){
    				gradeWorkList=gradeWorkApi.getGradeWorkListByGradeId(gradeId);
    				target+=1;
    			}
    			//获取班级考试信息
    			List<Map<String, Object>> gradeExamList=new ArrayList<>();
    			if(examRequire==1){
    				gradeExamList=gradeExamApi.getGradeExamByGradeId(gradeId);
    				target+=1;
    			}
    			Map gradeUserMapCon=new HashMap<>();
    			gradeUserMapCon.put("gradeId", gradeId);
    			gradeUserMapCon.put("userId", userId);
    			Map gradeUserMap=gradeUserApi.getGradeUserByUserIdAndGradeId(gradeUserMapCon);
    			Integer condition=0;  //已完成的条件数
				//判断用户必修学时
				if(gradeUserMap.get("requiredPeriod")!=null){
					if((Double)gradeUserMap.get("requiredPeriod")>=requiredPeriod){
						condition+=1;
					}
				}
				//判断用户选修学时
				if(gradeUserMap.get("optionalPeriod")!=null){
					if((Double)gradeUserMap.get("optionalPeriod")>=optionalPeriod){
						condition+=1;
					}
				}
				//判断用户作业（论文）
				if(workRequire==1){
					if(gradeWorkList.size()>0){
						Integer gradeWorkNum=gradeWorkList.size();   //班级作业数
						Integer complete=0;    //完成数量
						for (Map<String, Object> gradeWork : gradeWorkList) {
							//根据班级作业ID和用户ID获取学员提交作业
							Map<String, Object> gradeWorkReply=gradeWorkApi.getGradeWorkReplyByUserIdAndGradeWorkId((Integer)gradeWork.get("gradeWorkId"),(Integer)gradeUserMap.get("userId"));
							if(gradeWorkReply.get("workScore")!=null){
								if((Integer)gradeWorkReply.get("workScore")>=60){
									complete+=1;
								}
							}
						}
						if(complete>=gradeWorkNum){
							condition+=1;
						}
					}
					
				}
				//判断用户考试
				if(examRequire==1){
					if(gradeExamList.size()>0){
						Integer gradeExamNum=gradeExamList.size();   //班级考试数
						Integer complete=0;    //完成数量
						for (Map<String, Object> gradeExam : gradeExamList) {
							//根据班级I、用户ID、考试ID获取学员考试分数
							List<Map<String, Object>> examScoreList=examApi.getExamScoreList((Integer)gradeExam.get("examId"),(Integer)gradeUserMap.get("userId"),(Integer)gradeMap.get("gradeId"));
							for (Map<String, Object> examScore : examScoreList) {
								if(examScore.get("score")!=null){
									if((Integer)examScore.get("score")>=60){
										complete+=1;
										break;
									}
								}
							}
						}
						if(complete>=gradeExamNum){
							condition+=1;
						}
					}
				}
				//若是条件满足，则更新班级用户结业状态
				if(condition>=target){
					gradeUserApi.updateAllGradeUserGraduate(gradeId,userId);
				}
    		}
			Map<String, Object> map = new HashMap<>();
			map.put("mesg", "succ");
			return map;
		}
		return null;
   	}
	
	/**
	 * 
	 * @Title exportGradeStudyStatistics
	 * @Description：导出班级学习数据
	 * @author xrl
	 * @Date 2017年6月20日
	 * @param exGradeId
	 * @param esOrgCode
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/exportGradeStudyStatistics", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse<Object> exportGradeStudyStatistics(String exGradeId,String exOrgCode, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<>();
		if(exGradeId!=null&&exGradeId!=""){
			map.put("gradeId", exGradeId);
		}
		if(exOrgCode!=null&&exOrgCode!=""){
			map.put("orgCode",exOrgCode);
		}
		Map<String, Object> gradeMap=gradeApi.getGradeByGradeId(Integer.valueOf(exGradeId));
		List<Map<String, Object>> gradeUserMap=gradeUserApi.getGradeStudyStatisticsList(map);
		if(gradeUserMap!=null&&gradeUserMap.size()>0){
			Long time=(new Date()).getTime();
			String fileName="";
			if(gradeMap!=null&&gradeMap.size()>0){
				fileName=(String)gradeMap.get("gradeName")+"-学员学习统计"+time;
			}else{
				fileName=time.toString();
			}
			try {
				ExcelUtil.downloadExcelGradeStudyStatistics(gradeUserMap, response, fileName.toString());
			} catch (Exception e) {
				e.printStackTrace();
				return ApiResponse.fail(500, "导出失败");
			}
			return ApiResponse.success("导出成功", null);
		}
		return ApiResponse.fail(500, "导出失败");
	}
}
