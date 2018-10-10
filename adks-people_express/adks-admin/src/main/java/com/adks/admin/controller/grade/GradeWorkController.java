package com.adks.admin.controller.grade;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.adks.admin.util.LogUtil;
import com.adks.commons.oss.OSSUploadUtil;
import com.adks.commons.util.DateTimeUtil;
import com.adks.commons.util.DateUtils;
import com.adks.commons.util.PropertiesFactory;
import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.api.data.grade.Adks_grade_work;
import com.adks.dubbo.api.data.grade.Adks_grade_work_reply;
import com.adks.dubbo.api.interfaces.admin.grade.GradeWorkApi;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.api.interfaces.admin.role.UserroleApi;
import com.adks.dubbo.commons.Page;

/**
 * 
 * ClassName GradeWorkController
 * @Description：班级作业
 * @author xrl
 * @Date 2017年3月22日
 */
@Controller
@RequestMapping(value="/gradeWork")
public class GradeWorkController {

	private final Logger logger = LoggerFactory.getLogger(GradeWorkController.class);
	
	@Resource
	private GradeWorkApi gradeWorkApi;
	@Autowired
	private UserroleApi userroleApi;
	@Autowired
	private LogApi logApi;

	
	/**
	 * 
	 * @Title getDeptsJson
	 * @Description：加载班级作业左侧——班级树
	 * @author xrl
	 * @Date 2017年3月25日
	 * @return
	 */
	@RequestMapping(value="/getGradesJson", produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public List<Adks_grade> getDeptsJson(HttpServletRequest request,HttpServletResponse response) {
    	
    	Map userMap=(Map)request.getSession().getAttribute("user");
		Integer orgId = Integer.parseInt(userMap.get("orgId")+"");
    	Integer userId=Integer.parseInt(userMap.get("userId")+"");
		String orgCode = userMap.get("orgCode")+"";
		boolean isRoot = "1".equals(userMap.get("isSuper")+"")?true:false;
		Map likemap = new HashMap();
		likemap.put("orgId", orgId);
		List<Map<String, Object>> userRole=userroleApi.getRoleListByUser(userId);
		Integer roleId=(Integer) userRole.get(0).get("roleId");
		if (!isRoot) {
			if(roleId==5||"5".equals(roleId)){
				likemap.put("userId", userId);
			}else{
				if(orgCode.length()>=3){
					orgCode=orgCode.substring(0,3);
					likemap.put("orgCode", orgCode);
				}else{
					likemap.put("orgCode", orgCode);
				}
			}
		}
    	List<Adks_grade> maps = gradeWorkApi.getGradesJson(likemap);
    	return maps;
    }
    
    /**
     * 
     * @Title getGradeWorkListJson
     * @Description：获取班级作业分页列表
     * @author xrl
     * @Date 2017年3月25日
     * @param gradeId
     * @param workTitle
     * @param page
     * @param rows
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value="getGradeWorkListJson", produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public Page getGradeWorkListJson(HttpServletRequest request,String gradeId,String workTitle,Integer page,Integer rows){
    	logger.debug("@@@@@@@@加载班级作业列表");
    	Page<List<Map<String, Object>>> paramPage = new Page<List<Map<String, Object>>>();
    	try {
    		Map likemap = new HashMap();
    		if(workTitle!=null&&workTitle!=""){
    			workTitle = java.net.URLDecoder.decode(workTitle, "UTF-8");
    			likemap.put("workTitle", workTitle);
    		}
    		Map userMap=(Map)request.getSession().getAttribute("user");
    		Integer userId = Integer.parseInt(userMap.get("userId")+"");
    		String orgCode = userMap.get("orgCode")+"";
    		boolean isRoot = "1".equals(userMap.get("isSuper")+"")?true:false;
    		List<Map<String, Object>> userRole = userroleApi.getRoleListByUser(userId);
    		Integer roleId = (Integer) userRole.get(0).get("roleId");
    		if (!isRoot||(gradeId==null&&"".equals(gradeId))) {
    			if (roleId == 5||"5".equals(roleId)) {
    				likemap.put("userId", userId);
    			} else {
    				likemap.put("orgCode", orgCode);
    			}
    		}
    		if(gradeId!=null&&!"".equals(gradeId)){
    			likemap.put("gradeId", gradeId);
    		}
    		paramPage.setMap(likemap);
    		paramPage.setPageSize(rows);
    		paramPage.setCurrentPage(page);
    		
    		paramPage=gradeWorkApi.getGradeWorkListPage(paramPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return paramPage;
    }
    
    /**
     * 
     * @Title saveGradeWork
     * @Description：保存班级作业
     * @author xrl
     * @Date 2017年3月29日
     * @param gradeWork
     * @param s_startDate_str
     * @param s_endDate_str
     * @param request
     * @return
     */
    @RequestMapping(value="saveGradeWork",method=RequestMethod.POST,produces={"text/html;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> saveGradeWork(Adks_grade_work gradeWork,String ztree_gradeId,String s_startDate_str,String s_endDate_str,String ztree_gradeName,HttpServletRequest request,
    		@RequestParam(value = "filePathFile", required = false) MultipartFile filePathFile){
		if(gradeWork!=null&&gradeWork.getWorkTitle()!=null){
			Map<String, Object> checkMap=new HashMap<String,Object>();
			if(gradeWork.getGradeWorkId()!=null){
				checkMap.put("gradeWorkId", gradeWork.getGradeWorkId());
			}
			checkMap.put("workTitle", gradeWork.getWorkTitle());
			checkMap.put("gradeId", gradeWork.getGradeId());
			Map<String, Object> gradeWorkMap=gradeWorkApi.checkWorkTitle(checkMap);
			if(gradeWorkMap.size()<=0){
				if(s_startDate_str != null){
					gradeWork.setStartDate(DateUtils.getStr2LDate(s_startDate_str));
	    		}
	    		if(s_endDate_str != null){
	    			gradeWork.setEndDate(DateUtils.getStr2LDate(s_endDate_str));
	    		}
	    		if(filePathFile!=null){
	    			try {
						String filePath=PropertiesFactory.getProperty("ossConfig.properties", "oss.Download_Path");
						String ossResource=PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
						//获取文件类型
						String fileType=filePathFile.getOriginalFilename();
						fileType = fileType.substring(fileType.lastIndexOf(".") + 1, fileType.length());
						if(fileType!=null&&fileType!=""){
							//将MultipartFile转换成文件流
							//CommonsMultipartFile cf= (CommonsMultipartFile)filePathFile; 
							//DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
							//File file = fi.getStoreLocation();
							InputStream is=filePathFile.getInputStream();
							if(gradeWork.getFilePath()==null||"".equals(gradeWork.getFilePath())){
								//上传附件
								String new_Path=OSSUploadUtil.uploadFileNewName(is,fileType, filePath);
								String[] paths=new_Path.split("/");
								String code="/"+paths[paths.length-2]+"/"+paths[paths.length-1];
								gradeWork.setFilePath(code);
							}else if(gradeWork.getFilePath()!=null&&!"".equals(gradeWork.getFilePath())){
								String new_Path=OSSUploadUtil.replaceFile(is, fileType, ossResource+gradeWork.getFilePath(), filePath);
								String[] paths=new_Path.split("/");
								String code="/"+paths[paths.length-2]+"/"+paths[paths.length-1];
								gradeWork.setFilePath(code);
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
	    		}
	    		Map userMap=(Map)request.getSession().getAttribute("user");
				Integer creatorId = Integer.parseInt(userMap.get("userId")+"");
				String creatorName = userMap.get("username")+"";
	    		gradeWork.setCreatorId(creatorId);
	    		gradeWork.setCreatorName(creatorName);
	    		gradeWork.setCreateTime(new Date());
	    		gradeWork.setGradeId(Integer.valueOf(ztree_gradeId));
	    		gradeWork.setGradeName(ztree_gradeName);
	    		Integer dataId=gradeWorkApi.saveGradeWork(gradeWork);
	    		//数据操作成功，保存操作日志
				if(dataId!=null&&dataId!=0){
					Map map=(Map)request.getSession().getAttribute("user");
					//添加/修改 
					if(gradeWork.getGradeWorkId()==null){  //添加
						logApi.saveLog(dataId,gradeWork.getWorkTitle(),LogUtil.LOG_GRADEWORK_MODULEID,LogUtil.LOG_ADD_TYPE,map);
					}else{  //修改
						logApi.saveLog(dataId,gradeWork.getWorkTitle(),LogUtil.LOG_GRADEWORK_MODULEID,LogUtil.LOG_UPDATE_TYPE,map);
					}
				}
				Map<String, Object> resMap = new HashMap<>();
				resMap.put("mesg", "succ");
				return resMap;
			}else{
				Map<String, Object> resMap = new HashMap<>();
				resMap.put("mesg", "sameName");
				return resMap;
			}
			
		}
		return null;
    }
    
    /**
     * 
     * @Title delGradeWork
     * @Description:删除班级作业
     * @author xrl
     * @Date 2017年4月26日
     * @param request
     * @param response
     * @param ids
     */
	@RequestMapping(value = "/delGradeWork", method = RequestMethod.POST)
	public void delGradeWork(HttpServletRequest request, HttpServletResponse response, String ids) {
		response.setContentType("application/text;charset=utf-8");
		try {
			if (ids != null) {
				gradeWorkApi.deleteGradeWorkByIds(ids);
				Map map=(Map)request.getSession().getAttribute("user");
				String[] gradeWorkIds=ids.split(",");
				for (String gradeWorkId : gradeWorkIds) {
					//数据操作成功，保存操作日志
					if(gradeWorkId!=null){
						logApi.saveLog(Integer.valueOf(gradeWorkId),null,LogUtil.LOG_GRADEWORK_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
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
	
	/**
	 * 
	 * @Title selGradeWork
	 * @Description:查看学员提交作业-页面跳转
	 * @author xrl
	 * @Date 2017年4月28日
	 * @param gradeId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/selGradeWork", method = RequestMethod.GET)
    public String selGradeWork(Integer gradeWorkId, Model model) {
    	model.addAttribute("gradeWorkId", gradeWorkId);
    	return "/grade/gradeWorkReplyList";
    }
    
	/**
	 * 
	 * @Title getGradeWorkRreplyListJson
	 * @Description：班级学员提交作业分页列表
	 * @author xrl
	 * @Date 2017年4月28日
	 * @param gradeWorkId
	 * @param studentName
	 * @param page
	 * @param rows
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value="getGradeWorkRreplyListJson", produces = {"application/json; charset=UTF-8"})
    @ResponseBody
	public Page getGradeWorkRreplyListJson(String gradeWorkId,String studentName,Integer page,Integer rows){
		logger.debug("@@@@@@@@加载班级学员作业列表");
		String ossResource=PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
    	Page<List<Map<String, Object>>> paramPage = new Page<List<Map<String, Object>>>();
    	Map likemap = new HashMap();
    	if(studentName!=null&&studentName!=""){
    		likemap.put("studentName", studentName);
    	}
    	likemap.put("workId", gradeWorkId);
    	paramPage.setMap(likemap);
	   	paramPage.setPageSize(rows);
	   	paramPage.setCurrentPage(page);
	   	
	   	paramPage=gradeWorkApi.getGradeWorkReplyListPage(paramPage);
	   	List<Map<String, Object>> gradeWorkReplyList=paramPage.getRows();
	   	for (Map<String, Object> map : gradeWorkReplyList) {
			map.put("submitFilePath", ossResource+map.get("submitFilePath"));
			map.put("workAnswerStr",new String((byte[])map.get("workAnswer")));
			System.out.println("workAnswerStr:"+new String((byte[])map.get("workAnswer")));
		}
    	return paramPage;
	}
	
	/**
	 * 
	 * @Title saveGradeWorkReply
	 * @Description：批改作业
	 * @author xrl
	 * @Date 2017年4月28日
	 * @param gradeWorkReply
	 * @param request
	 * @return
	 */
	@RequestMapping(value="saveGradeWorkReply",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> saveGradeWorkReply(Adks_grade_work_reply gradeWorkReply,HttpServletRequest request){
		Map<String, Object> updateMap=new HashMap<String,Object>();
		if(gradeWorkReply!=null&&gradeWorkReply.getGradeWorkReplyId()!=null){
			Map userMap=(Map)request.getSession().getAttribute("user");
			Integer creatorId = Integer.parseInt(userMap.get("userId")+"");
			String creatorName = userMap.get("username")+"";
    		updateMap.put("correctId", creatorId);
    		updateMap.put("correntName", creatorName);
    		updateMap.put("correctDate", DateTimeUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
    		updateMap.put("workScore", gradeWorkReply.getWorkScore());
    		updateMap.put("gradeWorkReplyId", gradeWorkReply.getGradeWorkReplyId());
    		updateMap.put("isCorrent", 1);
    		gradeWorkApi.saveGradeWorkReply(updateMap);
    		//数据操作成功，保存操作日志
			if(gradeWorkReply.getGradeWorkReplyId()!=null&&gradeWorkReply.getGradeWorkReplyId()!=0){
				//批改
				logApi.saveLog(gradeWorkReply.getGradeWorkReplyId(),creatorName,LogUtil.LOG_GRADEWORK_MODULEID,LogUtil.LOG_CORRECT_TYPE,userMap);
			}
			Map<String, Object> resMap = new HashMap<>();
			resMap.put("mesg", "succ");
			return resMap;
		}
		return null;
    }
    
	/**
	 * 
	 * @Title delGradeWorkReply
	 * @Description：删除班级学员作业
	 * @author xrl
	 * @Date 2017年4月28日
	 * @param gradeWorkReplyIds
	 * @param request
	 * @param response
	 */
    @RequestMapping(value = "/delGradeWorkReply", method = RequestMethod.GET)
	public void delGradeWorkReply(String gradeWorkReplyIds, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/text;charset=utf-8");
		try {
			if (gradeWorkReplyIds != null && gradeWorkReplyIds.length() > 0) {
				String[] gradeWorkReplyArr = gradeWorkReplyIds.split(",");
				for (int i = 0; i < gradeWorkReplyArr.length; i++) {
					Integer gradeWorkReplyId = Integer.valueOf(gradeWorkReplyArr[i]);
					gradeWorkApi.delGradeWorkReplyByGradeWorkReplyId(gradeWorkReplyId);
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
     * @Title checkWorkTitle
     * @Description：检查班级作业名在该班级中是否重复
     * @author xrl
     * @Date 2017年5月9日
     * @param workTitle
     * @param gradeId
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkWorkTitle", method = RequestMethod.POST)
	public void checkGradeWorkName(String workTitle,Integer gradeId,HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("application/text;charset=utf-8");
		String result = "";
		try {
			Map<String, Object> selMap = new HashMap<String, Object>();
			workTitle = java.net.URLDecoder.decode(workTitle, "UTF-8");
			selMap.put("workTitle", workTitle);
			selMap.put("gradeId", gradeId);
			Map<String, Object> gradeMap = gradeWorkApi.checkWorkTitle(selMap);
			PrintWriter pWriter = response.getWriter();
			if (gradeMap.size() > 0) {
				pWriter.write("succ");
			} else {
				pWriter.write("false");
			}
			pWriter.flush();
			pWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
