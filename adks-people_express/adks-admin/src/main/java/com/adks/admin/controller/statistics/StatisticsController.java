package com.adks.admin.controller.statistics;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.interfaces.admin.course.CourseApi;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.api.interfaces.admin.user.UserOnlineApi;
import com.adks.dubbo.commons.ApiResponse;
import com.adks.dubbo.commons.Page;

/**
 * 
 * ClassName StatisticsController
 * @Description：统计
 * @author xrl
 * @Date 2017年6月19日
 */
@Controller
@RequestMapping(value = "/statistics")
public class StatisticsController {

	private final Logger logger = LoggerFactory.getLogger(StatisticsController.class);
	@Autowired
	private CourseApi courseApi;
	@Autowired
	private UserOnlineApi userOnlineApi;
	@Autowired
	private LogApi logApi;
	
	/**
	 * 
	 * @Title manager
	 * @Description：仅页面跳转
	 * @author xrl
	 * @Date 2017年6月19日
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toCourseStatisticsList")
	public String toCourseStatisticsList(Model model) {
		logger.debug("进入课程统计页面...");
		return "/statistics/courseStatisticsList";
	}
	
	/**
	 * 
	 * @Title getCourseStatisticsListJson
	 * @Description：获取课程统计分页列表
	 * @author xrl
	 * @Date 2017年6月19日
	 * @param request
	 * @param response
	 * @param orgId
	 * @param courseSortCode
	 * @param courseName
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/getCourseStatisticsListJson")
	@ResponseBody
	public Page<List<Map<String, Object>>> getCourseStatisticsListJson(String courseOrgCode, String courseSortCode, String courseName, Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response) {
		Page<List<Map<String, Object>>> page_bean = null;
		try {
			Map userMap = (Map) request.getSession().getAttribute("user");
			page_bean = new Page<List<Map<String, Object>>>();
			page_bean.setCurrentPage(page);
			page_bean.setPageSize(rows);
			Map<String, Object> map = new HashMap<>();
			if (courseName != null && !"".equals(courseName)) {
				courseName = java.net.URLDecoder.decode(courseName, "UTF-8");
				map.put("courseName", courseName);
			}
			boolean isSuperManager = "1".equals(userMap.get("isSuper") + "") ? true : false;
			if(courseOrgCode!=null&&courseOrgCode!=""){
				map.put("orgCode",courseOrgCode);
			}else if (isSuperManager) {
				map.put("orgCode", "0A");
			} else {
				String orgCode = userMap.get("orgCode").toString();
				map.put("orgCode", orgCode);
			}
			if (courseSortCode != null && !"".equals(courseSortCode)) {
				map.put("courseSortCode", courseSortCode);
			}
			page_bean.setMap(map);
			page_bean = courseApi.getCourseStatisticsListPage(page_bean);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return page_bean;
	}
	
	/**
	 * 
	 * @Title exportCourseStatistics
	 * @Description：导出课程统计信息
	 * @author xrl
	 * @Date 2017年6月19日
	 * @param questionIds
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/exportCourseStatistics", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse<Object> exportCourseStatistics(String excourseOrgCode,String excourseSortCode, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map userMap = (Map) request.getSession().getAttribute("user");
		Map<String, Object> map = new HashMap<>();
		boolean isSuperManager = "1".equals(userMap.get("isSuper") + "") ? true : false;
		if(excourseOrgCode!=null&&excourseOrgCode!=""){
			map.put("orgCode",excourseOrgCode);
		}else if (isSuperManager) {
			map.put("orgCode", "0A");
		}else {
			String orgCode = userMap.get("orgCode").toString();
			map.put("orgCode", orgCode);
		}
		if (excourseSortCode != null && !"".equals(excourseSortCode)) {
			map.put("courseSortCode", excourseSortCode);
		}
		List<Adks_course> courseList=courseApi.getCourseStatisticsList(map);
		if(courseList!=null&&courseList.size()>0){
			Long fileName=(new Date()).getTime();
			try {
				ExcelUtil.downloadExcelCourseStatistics(courseList, response, fileName.toString());
			} catch (Exception e) {
				e.printStackTrace();
				return ApiResponse.fail(500, "导出失败");
			}
			return ApiResponse.success("导出成功", null);
		}
		return ApiResponse.fail(500, "导出失败");
	}
	
	/**
	 * 
	 * @Title toUserOnlineStatisticsList
	 * @Description：仅页面跳转
	 * @author xrl
	 * @Date 2017年6月22日
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toUserOnlineStatisticsList")
	public String toUserOnlineStatisticsList(Model model) {
		logger.debug("进入在线用户统计页面...");
		return "/statistics/userOnlineStatisticsList";
	}
	
	
	@RequestMapping(value = "/getUserOnlineStatisticsListJson")
	@ResponseBody
	public Page<List<Map<String, Object>>> getUserOnlineStatisticsListJson(String username,String userOrgCode,Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response) {
		Page<List<Map<String, Object>>> page_bean = null;
		try {
			Map userMap = (Map) request.getSession().getAttribute("user");
			page_bean = new Page<List<Map<String, Object>>>();
			page_bean.setCurrentPage(page);
			page_bean.setPageSize(rows);
			Map<String, Object> map = new HashMap<>();
			boolean isSuperManager = "1".equals(userMap.get("isSuper") + "") ? true : false;
			if(userOrgCode!=null&&userOrgCode!=""){
				map.put("orgCode",userOrgCode);
			}else if (isSuperManager) {
				map.put("orgCode", "0A");
			} else {
				String orgCode = userMap.get("orgCode").toString();
				map.put("orgCode", orgCode);
			}
			if(username !=null && !"".equals(username)){
				username = java.net.URLDecoder.decode(username, "UTF-8");
				map.put("username", username);
			}
			page_bean.setMap(map);
			page_bean = userOnlineApi.getUserOnlineStatisticsListPage(page_bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page_bean;
	}
	
	//进入系统操作日志页面
	@RequestMapping(value = "/toLogStatisticsList")
	public String toLogStatisticsList(Model model) {
		logger.debug("进入系统操作日志页面...");
		return "/statistics/logStatisticsList";
	}
	
	//得到系统操作日志分页的数据
	@RequestMapping(value = "/getLogStatisticsListJson")
	@ResponseBody
	public Page<List<Map<String, Object>>> getLogStatisticsListJson(Integer moduleId,Integer operateType,String orgCode,String minTime,String maxTime,Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response) {
		Page<List<Map<String, Object>>> page_bean = null;
		try {
			Map userMap = (Map) request.getSession().getAttribute("user");
			page_bean = new Page<List<Map<String, Object>>>();
			page_bean.setCurrentPage(page);
			page_bean.setPageSize(rows);
			Map<String, Object> map = new HashMap<>();
			boolean isSuperManager = "1".equals(userMap.get("isSuper") + "") ? true : false;
			if(orgCode!=null&&orgCode!=""){
				map.put("orgCode",orgCode);
			}else if (isSuperManager) {
				map.put("orgCode", "0A");
			} else {
				String userOrgCode = userMap.get("orgCode").toString();
				map.put("orgCode", userOrgCode);
			}
			if(moduleId != null){
				map.put("moduleId", moduleId);
			}
			if(operateType != null){
				map.put("operateType", operateType);
			}
			if(maxTime !=null && !"".equals(maxTime)){
				map.put("maxTime", maxTime);
			}
			if(minTime!=null && !"".equals(minTime)){
				map.put("minTime", minTime);
			}
			page_bean.setMap(map);
			page_bean = logApi.getLogStatisticsListJson(page_bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page_bean;
	}
}
