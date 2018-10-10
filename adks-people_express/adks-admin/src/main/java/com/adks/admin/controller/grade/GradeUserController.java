package com.adks.admin.controller.grade;

import java.io.PrintWriter;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.admin.util.LogUtil;
import com.adks.commons.util.ComUtil;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.api.data.zhiji.Adks_rank;
import com.adks.dubbo.api.interfaces.admin.course.CourseApi;
import com.adks.dubbo.api.interfaces.admin.course.CourseUserApi;
import com.adks.dubbo.api.interfaces.admin.exam.ExamApi;
import com.adks.dubbo.api.interfaces.admin.grade.GradeApi;
import com.adks.dubbo.api.interfaces.admin.grade.GradeCourseApi;
import com.adks.dubbo.api.interfaces.admin.grade.GradeExamApi;
import com.adks.dubbo.api.interfaces.admin.grade.GradeUserApi;
import com.adks.dubbo.api.interfaces.admin.grade.GradeWorkApi;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.api.interfaces.admin.user.UserApi;
import com.adks.dubbo.api.interfaces.admin.zhiji.ZhijiApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping(value="/gradeUser")
public class GradeUserController {
	
	private final Logger logger = LoggerFactory.getLogger(GradeUserController.class);
	@Autowired
	private GradeUserApi gradeUserApi;
	@Autowired
	private UserApi userApi;
	@Autowired
	private GradeApi gradeApi;
	@Autowired
	private GradeWorkApi gradeWorkApi;
	@Autowired
	private GradeExamApi gradeExamApi;
	@Autowired
	private ExamApi examApi;
	@Autowired
	private GradeCourseApi gradeCourseApi;
	@Autowired
	private CourseUserApi courseUserApi;
	@Autowired
	private ZhijiApi zhijiApi;
	@Autowired
	private CourseApi courseApi;
	@Autowired
	private LogApi logApi;
	
	/**
	 * 
	 * @Title gradeUserList
	 * @Description：班级已选学员列表
	 * @author xrl
	 * @Date 2017年3月29日
	 * @param gradeId
	 * @param userName
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="/gradeUserList", produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public Page gradeUserList(Integer gradeId,String userRealName,String orgCodes,Integer page,Integer rows){
    	System.out.println("gradeId 班级学员:"+gradeId);
    	Page<List<Map<String, Object>>> paramPage = new Page<List<Map<String, Object>>>();
    	try {
    		//先获取grade_user分页信息，在service中，获取user表的用户信息拼接，返回controller
    		//模糊查询 的参数
    		Map likemap = new HashMap();
    		if(userRealName != null && !"".equals(userRealName)){
    			userRealName = java.net.URLDecoder.decode(userRealName, "UTF-8");
    			likemap.put("userName", userRealName);
    		}
    		if(gradeId != null && !"".equals(gradeId)){
    			likemap.put("gradeId", gradeId);
    		}
    		if(orgCodes!=null&&!"".equals(orgCodes)){
    			likemap.put("orgCode", orgCodes);
    		}
    		paramPage.setMap(likemap);
    		paramPage.setPageSize(rows);
    		paramPage.setCurrentPage(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
	   	return gradeUserApi.getGradeUserListPage(paramPage);
    }
	
	/**
	 * 
	 * @Title gradeSelUserList
	 * @Description：班级未选学员列表
	 * @author xrl
	 * @Date 2017年3月29日
	 * @param gradeId
	 * @param orgId
	 * @param userName
	 * @param page
	 * @param rows
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value="/gradeSelUserList", produces = {"application/json; charset=UTF-8"})
    @ResponseBody
	public Page gradeSelUserList(Integer gradeId,String orgCodes,String userName,Integer rankId,Integer positionId,Integer userStatus,
			HttpServletResponse response,HttpServletRequest request,Integer page,Integer rows){
    	System.out.println("gradeId 班级未选用户:"+gradeId);
    	Page<List<Map<String, Object>>> paramPage = new Page<List<Map<String, Object>>>();
    	try {
    		//模糊查询 的参数
    		Map likemap = new HashMap();
    		//搜索的用户名
    		if(userName != null && !"".equals(userName)){
    			userName = java.net.URLDecoder.decode(userName, "UTF-8");
    			likemap.put("userName", userName);
    		}
    		//获取已加入班级的用户的ID
    		List<Map<String, Object>> userIdsMap = gradeUserApi.getUserIdsByGradeId(gradeId);
    		if(userIdsMap != null && userIdsMap.size() > 0){
    			
    			StringBuilder sBuilder = new StringBuilder();
    			for (Map<String, Object> map : userIdsMap) {
    				if(map.get("userId") != null){
    					sBuilder.append(map.get("userId").toString() +",");
    				}
    			}
    			likemap.put("userIds", sBuilder.substring(0, sBuilder.length()-1));
    		}
    		//判断 部门搜索条件
    		Map userMap=(Map)request.getSession().getAttribute("user");
    		String orgCode=userMap.get("orgCode")+"";
    		boolean isRoot="1".equals(userMap.get("isSuper")+"")?true:false;
    		if(!isRoot&&orgCodes==null){
    			likemap.put("orgCode", orgCode);
    		}
    		if(orgCodes != null && orgCodes.length() > 0){
    			likemap.put("orgCode", orgCodes);
    		}
    		
    		//职务
    		if(rankId!=null&&rankId!=0){
    			likemap.put("rankId", rankId);
    		}
    		if(positionId!=null){
    			likemap.put("positionId", positionId);
    		}
    		
    		//用户状态
    		if(userStatus!=null){
    			likemap.put("userStatus", userStatus);
    		}
    		
    		paramPage.setMap(likemap);
    		paramPage.setPageSize(rows);
    		paramPage.setCurrentPage(page);
    		
    		//获取班级未选学员分页信息
    		paramPage=userApi.getUserListPage(paramPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	   	
    	return paramPage;
    }
	
	/**
	 * 
	 * @Title addGradeUsers
	 * @Description:添加班级学员
	 * @author xrl
	 * @Date 2017年3月30日
	 * @param userIds
	 * @param gradeId
	 * @param response
	 */
    @RequestMapping(value="/addGradeUsers",method=RequestMethod.GET)
    public void addGradeUsers (String userIds,Integer gradeId,String gradeName,HttpServletRequest request,HttpServletResponse response){
    	response.setContentType("application/text;charset=utf-8");
    	try {
    		System.out.println(userIds+"=============");
    		if(userIds != null && userIds.length() > 0){
    			Map<String, Object> grade=gradeApi.getGradeByGradeId(gradeId);
    			Map<String,Object> insertColumnValueMap = new HashMap<String,Object>();
    			String[] uids = userIds.split(",");
    			for (String userId : uids) {
    				Map<String, Object> userById = userApi.getUserInfoByUserId(Integer.parseInt(userId));
    				insertColumnValueMap.put("userId", userId);
    				insertColumnValueMap.put("userName", userById.get("userRealName"));
    				insertColumnValueMap.put("gradeId", gradeId);
    				insertColumnValueMap.put("gradeName", grade.get("gradeName"));
    				insertColumnValueMap.put("orgId", userById.get("orgId"));
    				insertColumnValueMap.put("orgName", userById.get("orgName"));
    				Map userMap=(Map)request.getSession().getAttribute("user");
					Integer creatorId = Integer.parseInt(userMap.get("userId")+"");
					String creatorName = userMap.get("username")+"";
					
    				insertColumnValueMap.put("creatorId", creatorId);
    				insertColumnValueMap.put("creatorName", creatorName);
    				insertColumnValueMap.put("createTime", new Date());
    				//以下默认值
    				insertColumnValueMap.put("isGraduate", 2);  //是否毕业(1.代表结业；2.代表未结业)
    				insertColumnValueMap.put("isCertificate", 2);  //已获必修课程学时
    				insertColumnValueMap.put("requiredPeriod", 0);  //已获选修课程学时
    				insertColumnValueMap.put("optionalPeriod", 0);  //是否颁发证书(1.已颁发；2.未颁发)
    				
    				Integer dataId=gradeUserApi.saveGradeUser(insertColumnValueMap);
    				//数据操作成功，保存操作日志
					if(dataId!=null){
						//添加/修改 
						logApi.saveLog(dataId, grade.get("gradeName").toString(),LogUtil.LOG_GRADEUSER_MODULEID,LogUtil.LOG_ADD_TYPE,userMap);
					}
				}
    			//更新班级学员数量
    			Map<String, Object> paramValue=new HashMap<>();
    			Map<String, Object> updateWhereConditionMap=new HashMap<String,Object>();
    			Integer guList=gradeUserApi.getUserCount(gradeId);
    			paramValue.put("userNum",guList);
    			updateWhereConditionMap.put("gradeId", gradeId);
    			gradeApi.updateGradeUserNum(paramValue,updateWhereConditionMap);
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
     * @Title removeGradeUsers
     * @Description：移除班级用户
     * @author xrl
     * @Date 2017年5月5日
     * @param gradeUserIds
     * @param gradeId
     * @param response
     */
    @RequestMapping(value="/removeGradeUsers",method=RequestMethod.GET)
    public void removeGradeUsers(String gradeUserIds,Integer gradeId,HttpServletResponse response,HttpServletRequest request){
    	response.setContentType("application/text;charset=utf-8");
    	try {
    		System.out.println(gradeUserIds+"=============");
    		if(gradeUserIds != null && gradeUserIds.length() > 0){
    			Map<String,Object> removeGradeValueMap = new HashMap<String,Object>();
    			Map map=(Map)request.getSession().getAttribute("user");
    			String[] guId = gradeUserIds.split(",");
    			for (String gradeUserId : guId) {
    				removeGradeValueMap.put("gradeId", gradeId);
    				removeGradeValueMap.put("gradeUserId", gradeUserId);
    				gradeUserApi.removeGradeUsers(removeGradeValueMap);
    				//数据操作成功，保存操作日志
					if(gradeUserId!=null){
						logApi.saveLog(Integer.valueOf(gradeUserId),null,LogUtil.LOG_GRADEUSER_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
					}
				}
    		}
    		//更新班级学员人数
    		Map<String, Object> paramValue=new HashMap<>();
			Map<String, Object> updateWhereConditionMap=new HashMap<String,Object>();
			Integer guList=gradeUserApi.getUserCount(gradeId);
			paramValue.put("userNum",guList);
			updateWhereConditionMap.put("gradeId", gradeId);
			gradeApi.updateGradeUserNum(paramValue,updateWhereConditionMap);
    		
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
     * @Title gradeHeadTeacher
     * @Description：获取班级班主任列表
     * @author xrl
     * @Date 2017年3月30日
     * @param gradeId
     * @param userRealName
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value="/gradeHeadTeacherList", produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public Page gradeHeadTeacher(Integer gradeId,String userRealName,Integer page,Integer rows){
    	System.out.println("gradeId 班级学员:"+gradeId);
    	Page<List<Map<String, Object>>> paramPage = new Page<List<Map<String, Object>>>();
    	try {
    		//模糊查询 的参数
    		Map likemap = new HashMap();
    		if(userRealName != null && !"".equals(userRealName)){
    			userRealName = java.net.URLDecoder.decode(userRealName, "UTF-8");
    			likemap.put("userRealName", userRealName);
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
    	
	   	return gradeUserApi.getGradeHeadTeacherPage(paramPage);
    }
    
    /**
     * 
     * @Title gradeSelHeadTeacherPage
     * @Description：获取未选择的班主任
     * @author xrl
     * @Date 2017年3月30日
     * @param gradeId
     * @param orgIds
     * @param userName
     * @param page
     * @param rows
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value="/gradeSelHeadTeacherPage", produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public Page gradeSelHeadTeacherPage(HttpServletRequest request,HttpServletResponse response,Integer gradeId,String orgCodes,String userRealName,Integer page,Integer rows){
    	System.out.println("gradeId 班级未选用户:"+gradeId);
    	Page<List<Map<String, Object>>> paramPage = new Page<List<Map<String, Object>>>();
    	try {
    		//模糊查询 的参数
    		Map likemap = new HashMap();
    		//搜索的用户名
    		if(userRealName != null && !"".equals(userRealName)){
    			userRealName = java.net.URLDecoder.decode(userRealName, "UTF-8");
    			likemap.put("userRealName", userRealName);
    		}
    		//获取已加入班级的班主任的ID(一个班只有一个班主任)
    		Map<String, Object> headTeacher = gradeUserApi.getHeadTeacherIdByGradeId(gradeId);
    		if(headTeacher.size()>0){
    			likemap.put("userIds", headTeacher.get("headTeacherId"));
    		}
    		//判断 搜索条件
    		if(orgCodes == null){
    			Map<String, Object> gradeMap=gradeApi.getGradeByGradeId(gradeId);
    			if(gradeMap != null){
        			likemap.put("orgCode", gradeMap.get("orgCode"));
        		}
    		}
    		
    		if(orgCodes!=null&&!"".equals(orgCodes)){
    			likemap.put("orgCodes",orgCodes);
    		}
    		
    		paramPage.setMap(likemap);
    		paramPage.setPageSize(rows);
    		paramPage.setCurrentPage(page);
    		
    		//获取班级未选学员分页信息
    		paramPage=userApi.getSelGradeHeadTeacherPage(paramPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	   	
    	return paramPage;
    }
    
    /**
     * 
     * @Title addGradeHeadTeacher
     * @Description：添加班级班主任
     * @author xrl
     * @Date 2017年3月30日
     * @param userIds
     * @param gradeId
     * @param gradeName
     * @param response
     */
    @RequestMapping(value="/addGradeHeadTeacher",method=RequestMethod.GET)
    public void addGradeHeadTeacher(String userIds,Integer gradeId,String gradeName,HttpServletRequest request,HttpServletResponse response){
    	response.setContentType("application/text;charset=utf-8");
    	try {
    		System.out.println(userIds+"=============");
    		if(userIds != null && userIds.length() > 0){
    			Map<String, Object> grade=gradeApi.getGradeByGradeId(gradeId);
    			Map<String,Object> insertColumnValueMap = new HashMap<String,Object>();
    			Map<String,Object> insertGradeValueMap = new HashMap<String,Object>();
    			String[] uids = userIds.split(",");
    			for (String userId : uids) {
    				Map<String, Object> userById = userApi.getUserInfoByUserId(Integer.parseInt(userId));
    				Map<String, Object> selMap= new HashMap<String,Object>();
    				selMap.put("gradeId", gradeId);
    				selMap.put("userId", userId);
    				Map<String, Object> gradeUser=gradeUserApi.getGradeUserByUserId(selMap);
    				if(gradeUser.size()==0){
    					insertColumnValueMap.put("userId", userId);
    					insertColumnValueMap.put("userName", userById.get("userRealName"));
    					insertColumnValueMap.put("gradeId", gradeId);
    					insertColumnValueMap.put("gradeName", grade.get("gradeName"));
    					Map userMap=(Map)request.getSession().getAttribute("user");
						Integer creatorId = Integer.parseInt(userMap.get("userId")+"");
						String creatorName = userMap.get("username")+"";
    					Integer orgId=Integer.parseInt(userMap.get("orgId")+"");
    					String orgName=userMap.get("orgName")+"";
    					
    					insertColumnValueMap.put("orgId", orgId);
    					insertColumnValueMap.put("orgName", orgName);
    					
    					insertColumnValueMap.put("creatorId", creatorId);
    					insertColumnValueMap.put("creatorName", creatorName);
    					insertColumnValueMap.put("createTime", new Date());
    					//以下默认值
    					insertColumnValueMap.put("isGraduate", 2);  //是否毕业(1.代表结业；2.代表未结业)
    					insertColumnValueMap.put("isCertificate", 2);  //已获必修课程学时
    					insertColumnValueMap.put("requiredPeriod", 0);  //已获选修课程学时
    					insertColumnValueMap.put("optionalPeriod", 0);  //是否颁发证书(1.已颁发；2.未颁发)
    					
    					Integer dataId=gradeUserApi.saveGradeUser(insertColumnValueMap);
    					//数据操作成功，保存操作日志
    					if(dataId!=null){
    						//添加/修改 
    						logApi.saveLog(dataId,grade.get("gradeName").toString(),LogUtil.LOG_GRADEUSER_MODULEID,LogUtil.LOG_ADD_TYPE,userMap);
    					}
    				}
    				
    				insertGradeValueMap.put("gradeId", gradeId);
    				insertGradeValueMap.put("headTeacherId", Integer.valueOf(userId));
    				insertGradeValueMap.put("headTeacherName", userById.get("userRealName").toString());
    				gradeUserApi.updateGradeHeadTeacher(insertGradeValueMap);
    				//数据操作成功，保存操作日志
					if(userId!=null){
						Map map=(Map)request.getSession().getAttribute("user");
						//添加
						logApi.saveLog(Integer.valueOf(userId),grade.get("gradeName").toString(),LogUtil.LOG_HEADTEADTEACHER_MODULEID,LogUtil.LOG_ADD_TYPE,map);
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
     * @Title removeGradeHeadTeacher
     * @Description：移除班级班主任
     * @author xrl
     * @Date 2017年3月30日
     * @param userIds
     * @param gradeId
     * @param response
     */
    @RequestMapping(value="/removeGradeHeadTeacher",method=RequestMethod.GET)
    public void removeGradeHeadTeacher(String userIds,Integer gradeId,HttpServletResponse response,HttpServletRequest request){
    	response.setContentType("application/text;charset=utf-8");
    	try {
    		System.out.println(userIds+"=============");
    		if(userIds != null && userIds.length() > 0){
    			Map<String,Object> updateGradeValueMap = new HashMap<String,Object>();
    			String[] uids = userIds.split(",");
    			for (String userId : uids) {
    				updateGradeValueMap.put("gradeId", gradeId);
    				updateGradeValueMap.put("headTeacherId",null);
    				updateGradeValueMap.put("headTeacherName","");
    				gradeUserApi.updateGradeHeadTeacher(updateGradeValueMap);
    				//数据操作成功，保存操作日志
					if(gradeId!=null){
						Map map=(Map)request.getSession().getAttribute("user");
						logApi.saveLog(Integer.valueOf(gradeId),null,LogUtil.LOG_HEADTEADTEACHER_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
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
     * @Title updateAllGradeUserGraduate
     * @Description：更新用户结业状态
     * @author xrl
     * @Date 2017年5月19日
     * @param gradeId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/updateAllGradeUserGraduate", method = RequestMethod.GET, produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public Map<String, Object> updateAllGradeUserGraduate(Integer gradeId, HttpServletRequest request,HttpServletResponse response) {
		if (gradeId != null) {
			//获取班级信息
			Map<String, Object> gradeMap=gradeApi.getGradeByGradeId(gradeId);
			//结业条件 必修学时 选修学时  
			Map<String, Object> selMap=new HashMap<>();
			selMap.put("gradeId", gradeMap.get("gradeId"));
			Double requiredPeriod=0.0;
			if(gradeMap.get("requiredPeriod")!=null){
				//获取班级必修学时
				selMap.put("gcState", 1);
				requiredPeriod=gradeCourseApi.getGradeCourseCredit(selMap);
				if(requiredPeriod==null){
					requiredPeriod=0.0;
				}
				//requiredPeriod=(Integer)gradeMap.get("requiredPeriod");
			}
			Double optionalPeriod=0.0;
			if(gradeMap.get("optionalPeriod")!=null){
				//获取班级选修学时
				selMap.put("gcState", 2);
				optionalPeriod=gradeCourseApi.getGradeCourseCredit(selMap);
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
			
			List<Map<String, Object>> gradeUserList=gradeUserApi.getGradeUserList(gradeId);
			for (Map<String, Object> gradeUserMap : gradeUserList) {
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
					gradeUserApi.updateAllGradeUserGraduate((Integer)gradeMap.get("gradeId"),(Integer)gradeUserMap.get("userId"));
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
     * @Title updateAllCourseUser
     * @Description：同步所有学员课程进度
     * @author xrl
     * @Date 2017年6月3日
     * @param gradeId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/updateAllCourseUser", method = RequestMethod.GET, produces = { "text/html;charset=utf-8" })
   	@ResponseBody
   	public Map<String, Object> updateAllCourseUser(Integer gradeId, HttpServletRequest request,HttpServletResponse response) {
   		if (gradeId != null) {
   			//获取班级所有用户
   			List<Map<String, Object>> guList=gradeUserApi.getGradeUserList(gradeId);
   			List<Map<String, Object>> gcList=gradeCourseApi.getCourseIdByGradeId(gradeId);
   			for (Map<String, Object> gu : guList) {
				//获取班级所有课程
   				for (Map<String, Object> gc : gcList) {
					//获取用户看课记录
   					Map<String, Object> selMap=new HashMap<>();
   					selMap.put("courseId", gc.get("courseId"));
   					selMap.put("userId", gu.get("userId"));
   					Map<String, Object> courseUser=courseUserApi.getCourseUserByUserIdAndCourseId(selMap);
   					if(courseUser.size()>0){
   						if((Integer)courseUser.get("isOver")==1){
   							if(ComUtil.isNotNullOrEmpty((String)courseUser.get("grades"))){
   								String[] ids=((String)courseUser.get("grades")).split(",");
   								if(!ArrayUtils.contains(ids,gradeId.toString())){
   									selMap.put("grades", (String)courseUser.get("grades")+","+gradeId);
   									courseUserApi.updateCourseUserForGrades(selMap);
   								}
   							}else{
   								selMap.put("grades", gradeId);
   								courseUserApi.updateCourseUserForGrades(selMap);
   							}
   	   					}
   					}
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
   	 * @Title updateAllGradeUserCredit
   	 * @Description：更新班级学员学时
   	 * @author xrl
   	 * @Date 2017年6月6日
   	 * @param gradeId
   	 * @param request
   	 * @param response
   	 * @return
   	 */
    @RequestMapping(value = "/updateAllGradeUserCredit", method = RequestMethod.GET, produces = { "text/html;charset=utf-8" })
   	@ResponseBody
   	public Map<String, Object> updateAllGradeUserCredit(Integer gradeId, HttpServletRequest request,HttpServletResponse response) {
   		if (gradeId != null) {
   			Map<String, Object> selMap=new HashMap<>();
   			selMap.put("gradeId",gradeId);
   			//获取班级所有用户
   			List<Map<String, Object>> guList=gradeUserApi.getGradeUserList(gradeId);
   			//获取班级课程的课程ID
   			List<Map<String, Object>> courseIdsMap=gradeCourseApi.getCourseIdByGradeId(gradeId);
   			//拼接courseId
   			StringBuilder sBuilder = new StringBuilder();
   			if(courseIdsMap != null && courseIdsMap.size() > 0){
    			for (Map<String, Object> map : courseIdsMap) {
    				if(map.get("courseId") != null){
    					sBuilder.append(map.get("courseId").toString() +",");
    				}
    			}
    		}
   			selMap.put("courseIds",sBuilder.substring(0, sBuilder.length()-1));
   			for (Map<String, Object> gu : guList) {
   				selMap.put("userId",gu.get("userId"));
   				//获取所有学完的课程
   				List<Map<String, Object>> courseUserList=courseUserApi.getCourseUserByCourseIds(selMap);
   				//获取累加学时
   				if(courseUserList.size()>0){
	   				StringBuilder cBuilder = new StringBuilder();
	   	   			if(courseUserList != null && courseUserList.size() > 0){
	   	    			for (Map<String, Object> map : courseUserList) {
	   	    				if(map.get("courseId") != null){
	   	    					cBuilder.append(map.get("courseId").toString() +",");
	   	    				}
	   	    			}
	   	    		}
   	   				selMap.put("cids", cBuilder.substring(0, cBuilder.length()-1));
   	   				selMap.put("gcState",1);
   	   				Double requiredPeriod=gradeCourseApi.getGradeCourseCredits(selMap);
   	   				if(requiredPeriod==null){
   	   					requiredPeriod=0.0;
   	   				}
   	   				selMap.put("requiredPeriod",requiredPeriod);
   	   				selMap.put("gcState",2);
   	   				Double optionalPeriod=gradeCourseApi.getGradeCourseCredits(selMap);
   	   				if(optionalPeriod==null){
   	   					optionalPeriod=0.0;
   	   				}
   	   				selMap.put("optionalPeriod",optionalPeriod);
   	   				gradeUserApi.updateGradeUserCredit(selMap);
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
     * @Title completeCourseUser
     * @Description：完成学习
     * @author xrl
     * @Date 2017年6月6日
     * @param gradeId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/completeCourseUser", method = RequestMethod.GET, produces = { "text/html;charset=utf-8" })
   	@ResponseBody
   	public Map<String, Object> completeCourseUser(Integer gradeId, HttpServletRequest request,HttpServletResponse response) {
    	if (gradeId != null) {
   			//获取班级所有用户
   			List<Map<String, Object>> guList=gradeUserApi.getGradeUserList(gradeId);
   			List<Map<String, Object>> gcList=gradeCourseApi.getCourseIdByGradeId(gradeId);
   			for (Map<String, Object> gu : guList) {
				//获取班级所有课程
   				for (Map<String, Object> gc : gcList) {
					//获取用户看课记录
   					Map<String, Object> selMap=new HashMap<>();
   					selMap.put("courseId", gc.get("courseId"));
   					selMap.put("userId", gu.get("userId"));
   					Map<String, Object> courseUser=courseUserApi.getCourseUserByUserIdAndCourseId(selMap);
   					if(courseUser.size()>0){
   						if((Integer)courseUser.get("isOver")==2){
   							Map<String, Object> updateMap=new HashMap<>();
   							if(ComUtil.isNotNullOrEmpty((String)courseUser.get("grades"))){
   								String[] ids=((String)courseUser.get("grades")).split(",");
   								if(!ArrayUtils.contains(ids,gradeId.toString())){
   									updateMap.put("grades", (String)courseUser.get("grades")+","+gradeId);

   								}
   							}else{
   								updateMap.put("grades",gradeId.toString());
   							}
   							updateMap.put("isOver",1);
   							updateMap.put("lastPosition",courseUser.get("courseDuration"));
   							updateMap.put("lastStudyDate",new Date());
   							updateMap.put("studyAllTimeLong",courseUser.get("courseDuration"));
   							updateMap.put("studyAllTime",courseUser.get("studyAllTime"));
   							updateMap.put("studyCourseTime",courseUser.get("courseDuration"));
   							courseUserApi.completeCourseUser(updateMap,(Integer)courseUser.get("courseUserId"));
   	   					}else{
   	   						Map<String, Object> updateMap=new HashMap<>();
							if(ComUtil.isNotNullOrEmpty((String)courseUser.get("grades"))){
								String[] ids=((String)courseUser.get("grades")).split(",");
								if(!ArrayUtils.contains(ids,gradeId.toString())){
									updateMap.put("grades", (String)courseUser.get("grades")+","+gradeId);
									courseUserApi.completeCourseUser(updateMap,(Integer)courseUser.get("courseUserId"));
								}
							}else{
								updateMap.put("grades",gradeId.toString());
								courseUserApi.completeCourseUser(updateMap,(Integer)courseUser.get("courseUserId"));
							}
   	   					}
   					}else{
   						Map<String, Object> courseuser=new HashMap<>();
   						Adks_course course=courseApi.getCourseById((Integer)gc.get("courseId"));
   						if(course!=null){
   							courseuser.put("userId",gu.get("userId"));
   							courseuser.put("courseId",course.getCourseId());
   							courseuser.put("courseCode",course.getCourseCode());
   							courseuser.put("courseName",course.getCourseName());
   							courseuser.put("isOver",1);
   							courseuser.put("grades",gradeId.toString());
   							courseuser.put("authorId",course.getAuthorId());
   							//courseuser.put("authorName",course.getAuthorName());
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
   						}
   					}
				}
			}
   			Map<String, Object> map = new HashMap<>();
   			map.put("mesg", "succ");
   			return map;
   		}
   		return null;
   	}
}
