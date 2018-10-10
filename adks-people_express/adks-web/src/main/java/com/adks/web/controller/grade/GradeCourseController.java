package com.adks.web.controller.grade;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.commons.util.ComUtil;
import com.adks.commons.util.Constants;
import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.api.data.grade.Adks_grade_course;
import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.api.interfaces.web.course.CourseUserApi;
import com.adks.dubbo.api.interfaces.web.grade.GradeCourseApi;
import com.adks.dubbo.api.interfaces.web.grade.GradeUserApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping({"/gradeCourse"})
public class GradeCourseController {

	@Autowired
	private GradeCourseApi gradeCourseApi;
	
	@Autowired
	private CourseUserApi courseUserApi;
	@Autowired
	private GradeUserApi gradeUserApi;
	
	//得到班级课程
	@RequestMapping(value="/gradeCourseList.do")
	public String gradeCourseList(HttpServletRequest request,HttpServletResponse response,Page<List<Adks_course_user>> page,Integer gradeId,String gradeName,String xsFlag,String flag,Model model){
		Adks_user user = (Adks_user) request.getSession().getAttribute("user");
		Map map = new HashMap();
		if(user != null && ComUtil.isNotNullOrEmpty(gradeId)){
			map.put("gradeId", gradeId);
			map.put("userId", user.getUserId());
		}
		/*if(ComUtil.isNotNullOrEmpty(courseCatalogCode)){
			map.put("courseCatalogCode", courseCatalogCode); //课程分类code
		}*/
		// 必修	2
		if("2".equals(flag)){
			map.put("gcState", 1);
			page.setMap(map);
			page = gradeCourseApi.getAllGradeCourseList(page);
		}
		// 选修	3
		else if("3".equals(flag)){
			page.setMap(map);
			map.put("gcState", 2);
			page = gradeCourseApi.getAllGradeCourseList(page);
		}
		//  	 正在学	5 	 已学完	6    DAO方法添加参数，为课程类型（选修/必修）
		else if("5".equals(flag)||"6".equals(flag)){
			map.put("studyed",flag);
			page.setMap(map);
			page = gradeCourseApi.gradeCourseUserList(page);
		}
		//未开始	4 
		else if("4".equals(flag)){
			page.setMap(map);
			page = gradeCourseApi.gradeCourseListNoStudy(page);
		}
		//全部
		else{
			page.setMap(map);
			page = gradeCourseApi.getAllGradeCourseList(page);
		}
		
		// 显示方式判断 （默认第一种方式-图文显示）
		if(ComUtil.isNullOrEmpty(xsFlag) || "undefined".equals(xsFlag)){
			xsFlag = "tw";
		}
		
		Integer gradeUserIsSynchronization=2;  //班级用户信息无需同步
		List<Adks_course_user> cuList=page.getRows();
		Map<Integer, Integer> gIdsMap=new HashMap<>();
		if(cuList!=null && cuList.size()>0){
			for (Adks_course_user cu: cuList) {
				Map<String, Object> courseUserMap=new HashMap<>();
				courseUserMap.put("courseId", cu.getCourseId());
				courseUserMap.put("userId", user.getUserId());
				Adks_course_user cuser=courseUserApi.getCourseUserByCourseIdAndUserId(courseUserMap);
				if(cuser!=null){
					if(ComUtil.isNotNullOrEmpty(cuser.getGrades())){
						String[] gids=null;
						gids = cuser.getGrades().split(",");
						if(gids.length>0){
							if(ArrayUtils.contains(gids,gradeId.toString())){
								gIdsMap.put(cu.getCourseId(), 2);
							}else{
								gIdsMap.put(cu.getCourseId(), 1);
							}
						}
					}else{
						gIdsMap.put(cu.getCourseId(), 2);
					}
				}else{
					gIdsMap.put(cu.getCourseId(), 3);
				}
			}
		}
		//model.addAttribute("courseCatalogName", courseCatalogName);
		//model.addAttribute("courseCatalogCode", courseCatalogCode);
		model.addAttribute("gradeId", gradeId);
		model.addAttribute("page", page);
		model.addAttribute("flag", flag);
		model.addAttribute("xsFlag", xsFlag);
		model.addAttribute("gIdsMap",gIdsMap);
		model.addAttribute("gradeUserIsSynchronization", gradeUserIsSynchronization);
		if(ComUtil.isNotNullOrEmpty(gradeName)){
			model.addAttribute("gradeName", gradeName);
		}
		model.addAttribute("precisionNum", Constants.precisionNum);
		return "/grade/gradeCourseList";
	}
	
	//得到班级课程的进度
	@RequestMapping(value="/getCourseUsersJindu.do")
	@ResponseBody
	public void getCourseUsersJindu(Integer gradeId,Integer userId,Integer courseId,HttpServletRequest request,HttpServletResponse response){
		Float data;
		Map map=new HashMap<>();
//		map.put("gradeId", gradeId);
		map.put("userId", userId);
		map.put("courseId", courseId);
		Adks_course_user cUser = courseUserApi.getCourseUserByGradeIdCourseIdAndUserIdforCourseJD(map);
		if(cUser == null){
			data = 0f;
		}else if(cUser.getStudyAllTimeLong() == null || cUser.getStudyAllTimeLong() == 0){
			data = 0f;
		}else{
			data = Float.valueOf(cUser.getStudyAllTimeLong()/Float.valueOf(cUser.getCourseDuration()));
			data = data * 100;
		}
		try {
			DecimalFormat fmt = new DecimalFormat("0"); //不保留小数位
			String dataStr = fmt.format(Double.valueOf(data+""));
			dataStr = dataStr+"%";
			response.setContentType("text/plain");
			PrintWriter pw = response.getWriter();
			pw.write(dataStr);
			pw.flush();
			pw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Title updateGradeUser
	 * @Description:同步学习记录
	 * @author xrl
	 * @Date 2017年6月3日
	 * @param courseId
	 * @param userId
	 * @param gradeId
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/updateGradeUser.do")
	@ResponseBody
	public void updateGradeUser(Integer courseId,Integer userId,Integer gradeId,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map=new HashMap<>();
		map.put("courseId", courseId);
		map.put("userId", userId);
		map.put("gradeId", gradeId);
		Adks_grade_user gu=gradeUserApi.getUserStudysFromGradeUser(userId, gradeId);
		Adks_course_user cu=courseUserApi.getCourseUserByCourseIdAndUserId(map);
		Adks_grade_course gc=gradeCourseApi.getGradeCourseByCourseAndGradeId(map);
		String grades="";
		if(cu!=null&&gu!=null&&gc!=null){
			map.put("gradeUserId", gu.getGradeUserId());
			if(ComUtil.isNotNullOrEmpty(cu.getGrades())){
				String[] gradeIds=cu.getGrades().split(",");
				if(!ArrayUtils.contains(gradeIds,gradeId.toString())){
					String ids=cu.getGrades()+","+gradeId;
					map.put("grades", ids);
					if(gc!=null){
						if(ComUtil.isNotNullOrEmpty(gc.getGcState())){
							if(gc.getGcState()==1){
								map.put("requiredPeriod", gu.getRequiredPeriod()+gc.getCredit());
							}else{
								map.put("optionalPeriod", gu.getOptionalPeriod()+gc.getCredit());
							}
						}
					}
					if(cu.getIsOver()==1){
						//更新看课记录
						courseUserApi.updateCourseUserForGrades(map);
						//更新用户学时
						gradeUserApi.updateGradeUserForCredit(map);
					}
				}
//				for (String gId : gradeIds) {
//					if (!gId.equals(gradeId.toString())) {
//						
//					}
//				}
			}
		}
		try {
			response.setContentType("text/plain");
			PrintWriter pw = response.getWriter();
			pw.write("");
			pw.flush();
			pw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
