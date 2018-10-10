package com.adks.admin.controller.grade;

import java.io.PrintWriter;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.admin.util.LogUtil;
import com.adks.dubbo.api.interfaces.admin.grade.GradeApi;
import com.adks.dubbo.api.interfaces.admin.grade.GradeCourseApi;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.commons.Page;
/**
 * 
 * ClassName GradeCourseController
 * @Description：班级课程
 * @author xrl
 * @Date 2017年3月31日
 */
@Controller
@RequestMapping(value="/gradeCourse")
public class GradeCourseController {

	private final Logger logger = LoggerFactory.getLogger(GradeCourseController.class);
	@Autowired
	private GradeCourseApi gradeCourseApi;
	@Autowired
	private GradeApi gradeApi;
	@Autowired
	private LogApi logApi;
	
	/**
	 * 
	 * @Title gradeCourseList
	 * @Description：获取班级已选课程分页列表
	 * @author xrl
	 * @Date 2017年3月31日
	 * @param gradeId
	 * @param courseName
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="/getGradeCourseList", produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public Page getGradeCourseList(Integer gradeId,String courseName,Integer page,Integer rows){
    	System.out.println("gradeId 班级学员:"+gradeId);
    	Page<List<Map<String, Object>>> paramPage = new Page<List<Map<String, Object>>>();
    	try {
    		//模糊查询 的参数
    		Map likemap = new HashMap();
    		if(courseName != null && !"".equals(courseName)){
    			courseName = java.net.URLDecoder.decode(courseName, "UTF-8");
    			likemap.put("courseName", courseName);
    		}
    		if(gradeId != null && !"".equals(gradeId)){
    			likemap.put("gradeId", gradeId);
    		}
    		paramPage.setMap(likemap);
    		paramPage.setPageSize(rows);
    		paramPage.setCurrentPage(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
	   	return gradeCourseApi.getGradeCourseListPage(paramPage);
    }
	
	/**
	 * 
	 * @Title gradeSelCourseList
	 * @Description：班级未选课程分页列表
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param gradeId
	 * @param courseName
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="/gradeSelCourseList", produces = {"application/json; charset=UTF-8"})
    @ResponseBody
	public Page gradeSelCourseList(HttpServletResponse response,HttpServletRequest request,Integer gradeId,String courseName,String courseSortIds,Integer page,Integer rows){
    	Page<List<Map<String, Object>>> paramPage = new Page<List<Map<String, Object>>>();
    	try {
    		Map userMap=(Map)request.getSession().getAttribute("user");
    		//模糊查询 的参数
    		Map likemap = new HashMap();
    		if(courseName != null && !"".equals(courseName)){
    			courseName = java.net.URLDecoder.decode(courseName, "UTF-8");
    			likemap.put("courseName", courseName);
    		}
    		//获取已加入班级的课程的ID
    		List<Map<String, Object>> courseIdsMap = gradeCourseApi.getCourseIdByGradeId(gradeId);
    		if(courseIdsMap != null && courseIdsMap.size() > 0){
    			
    			StringBuilder sBuilder = new StringBuilder();
    			for (Map<String, Object> map : courseIdsMap) {
    				if(map.get("courseId") != null){
    					sBuilder.append(map.get("courseId").toString() +",");
    				}
    			}
    			likemap.put("courseIds", sBuilder.substring(0, sBuilder.length()-1));
    		}
    		boolean isRoot="1".equals(userMap.get("isSuper")+"")?true:false;
    		if(!isRoot||courseSortIds==null){
    			String orgCode=(String)userMap.get("orgCode");
    			likemap.put("orgCode", orgCode);
    		}
    		System.out.println("courseSortIds============="+courseSortIds);
    		if(courseSortIds != null && courseSortIds.length() > 0){
    			likemap.put("courseSortIds", courseSortIds);
    		}
    		paramPage.setMap(likemap);
    		paramPage.setPageSize(rows);
    		paramPage.setCurrentPage(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
	   	return gradeCourseApi.getCourseListPage(paramPage);
	}
	
	/**
	 * 
	 * @Title addGradeCourses
	 * @Description：添加班级课程
	 * @author xrl
	 * @Date 2017年4月1日
	 * @param courseIds
	 * @param gradeId
	 * @param gradeName
	 * @param response
	 */
	@RequestMapping(value="/addGradeCourses",method=RequestMethod.GET)
	public void addGradeCourses(String courseIds,Integer gradeId,String gradeName,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("application/text;charset=utf-8");
    	try {
    		System.out.println(courseIds+"=============");
    		Map<String, Object> grade=gradeApi.getGradeByGradeId(gradeId);
    		if(courseIds != null && courseIds.length() > 0){
    			Map<String,Object> insertColumnValueMap = new HashMap<String,Object>();
    			String[] uids = courseIds.split(",");
    			for (String courseId : uids) {
    				Map<String, Object> courseById = gradeCourseApi.getCourseByCourseId(Integer.parseInt(courseId));
    				insertColumnValueMap.put("courseId", courseId);
    				insertColumnValueMap.put("gradeId", gradeId);
    				insertColumnValueMap.put("gradeName", grade.get("gradeName"));
    				insertColumnValueMap.put("cwType", courseById.get("courseType"));
    				insertColumnValueMap.put("courseName", courseById.get("courseName"));
    				insertColumnValueMap.put("courseCode", courseById.get("courseCode"));
    				insertColumnValueMap.put("courseCatalogId", courseById.get("courseSortId"));
    				insertColumnValueMap.put("courseCatalogName", courseById.get("courseSortName"));
    				insertColumnValueMap.put("courseCatalogCode", courseById.get("courseSortCode"));
    				Map userMap=(Map)request.getSession().getAttribute("user");
					Integer creatorId = Integer.parseInt(userMap.get("userId")+"");
					String creatorName = userMap.get("username")+"";
					
    				insertColumnValueMap.put("creatorId", creatorId);
    				insertColumnValueMap.put("creatorName", creatorName);
    				insertColumnValueMap.put("createTime", new Date());
    				insertColumnValueMap.put("gcState", 2);
    				if(courseById.get("courseDuration")!=null&&courseById.get("courseDuration")!=""){
    					Integer hours=(Integer)courseById.get("courseDuration");
    					Double hourse = Double.valueOf(hours)/Double.valueOf(2700);
    					DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
    					df.setRoundingMode(RoundingMode.FLOOR);  //取消四舍五入
    					String credit = df.format(hourse);//返回的是String类型的
    					insertColumnValueMap.put("credit",Double.valueOf((Integer)courseById.get("courseDuration")));
    				}
    				
    				Integer dataId=gradeCourseApi.saveGradeCourse(insertColumnValueMap);
    				//数据操作成功，保存操作日志
					if(courseId!=null){
						//添加/修改 
						logApi.saveLog(Integer.valueOf(courseId),grade.get("gradeName").toString(),LogUtil.LOG_GRADECOURSE_MODULEID,LogUtil.LOG_ADD_TYPE,userMap);
					}
				}
    			//获取班级选修、必修课程数量
    			Map<String, Object> selMap=new HashMap<>();
    			selMap.put("gradeId", gradeId);
    			Map<String, Object> gradeCourseNum=gradeCourseApi.getGradeCourseNum(selMap);
    			//更新班级课程数量
    			gradeCourseApi.updateGradeCourseNum(gradeCourseNum);
    			
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
	 * @Title removeGradeCourses
	 * @Description：移除班级课程
	 * @author xrl
	 * @Date 2017年3月31日
	 * @param courseIds
	 * @param gradeId
	 * @param response
	 */
	@RequestMapping(value="/removeGradeCourses",method=RequestMethod.GET)
    public void removeGradeCourses(String courseIds,Integer gradeId,HttpServletResponse response,HttpServletRequest request){
    	response.setContentType("application/text;charset=utf-8");
    	try {
    		System.out.println(courseIds+"=============");
    		if(courseIds != null && courseIds.length() > 0){
    			Map<String,Object> updateGradeValueMap = new HashMap<String,Object>();
    			String[] uids = courseIds.split(",");
    			Map map=(Map)request.getSession().getAttribute("user");
    			for (String courseId : uids) {
    				updateGradeValueMap.put("gradeId", gradeId);
    				updateGradeValueMap.put("courseId",courseId);
    				gradeCourseApi.removeGradeCourse(updateGradeValueMap);
    				//数据操作成功，保存操作日志
					if(courseId!=null){
						logApi.saveLog(Integer.valueOf(courseId),gradeId.toString(),LogUtil.LOG_GRADECOURSE_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
					}
				}
    			//获取班级选修、必修课程数量
    			Map<String, Object> selMap=new HashMap<>();
    			selMap.put("gradeId", gradeId);
    			Map<String, Object> gradeCourseNum=gradeCourseApi.getGradeCourseNum(selMap);
    			//更新班级课程数量
    			gradeCourseApi.updateGradeCourseNum(gradeCourseNum);
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
	 * @Title setGradeCoursesType
	 * @Description：设置班级课程类型（选修/必修）
	 * @author xrl
	 * @Date 2017年4月27日
	 * @param courseIds
	 * @param gradeId
	 * @param response
	 */
	@RequestMapping(value="/setGradeCoursesType",method=RequestMethod.GET)
	public void setGradeCoursesType(String courseIds,Integer gradeId,Integer type,HttpServletResponse response){
		response.setContentType("application/text;charset=utf-8");
    	try {
    		System.out.println(courseIds+"=============");
    		if(courseIds != null && courseIds.length() > 0){
    			Map<String,Object> updateGradeValueMap = new HashMap<String,Object>();
    			String[] uids = courseIds.split(",");
    			for (String courseId : uids) {
    				updateGradeValueMap.put("gradeId", gradeId);
    				updateGradeValueMap.put("courseId",courseId);
    				if(type==1){
    					updateGradeValueMap.put("gcState", type);
    				}else{
    					updateGradeValueMap.put("gcState", type);
    				}
    				gradeCourseApi.setGradeCoursesType(updateGradeValueMap);
				}
    			//获取班级选修、必修课程数量
    			Map<String, Object> selMap=new HashMap<>();
    			selMap.put("gradeId", gradeId);
    			Map<String, Object> gradeCourseNum=gradeCourseApi.getGradeCourseNum(selMap);
    			//更新班级课程数量
    			gradeCourseApi.updateGradeCourseNum(gradeCourseNum);
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
