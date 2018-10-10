package com.adks.app.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.app.util.JsonResponse;
import com.adks.dubbo.api.interfaces.app.course.CourseUserAppApi;
import com.adks.dubbo.api.interfaces.app.grade.GradeAppApi;
import com.adks.dubbo.api.interfaces.app.grade.GradeUserAppApi;
import com.adks.dubbo.api.interfaces.app.user.UserAppApi;
import com.adks.dubbo.commons.Page;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/api/grade")
public class GradeController {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(GradeController.class);

	@Autowired
	private CourseUserAppApi courseUserAppApi;
	@Autowired
	private GradeAppApi gradeAppApi;
	@Autowired
	private GradeUserAppApi gradeUserAppApi;
	@Autowired
	private UserAppApi userAppApi;

	/**
	 * 我的培训班
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/listMyGrade.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse listMyGrade(String data) throws IOException {
		String error = null;

		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();
		/* 返回参数 */
		Map resultMap = new HashMap();

		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);

			/* 解析map参数 */
			int userId = Integer.valueOf(MapUtils.getString(map, "userId"));
			int gradeType = Integer.valueOf(MapUtils.getString(map, "gradeType"));
			int startIndex = Integer.valueOf(MapUtils.getString(map, "startIndex"));
			int pageSize = Integer.valueOf(MapUtils.getString(map, "pageSize"));

			/* 用户id */
			map.put("userId", userId);

			Page page = new Page();
			page.setMap(map);
			page.setPageSize(pageSize);
			page.setStartLimit(startIndex);

			/* 当前培训班 */
			if (gradeType == 1)
				page = gradeUserAppApi.getCurrentGradeListByUserId(page);
			else if (gradeType == 2)/* 历史培训班 */
				page = gradeUserAppApi.getHistoryGradeListByUserId(page);
			resultMap.put("page", page);
		} else
			error = "未接受到参数";
		if (StringUtils.isNotEmpty(error))
			return JsonResponse.error(error);
		return JsonResponse.success(resultMap);
	}

	/**
	 * 培训班详细信息头部
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/gradeIndex.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse gradeIndex(String data) throws IOException {
		String error = null;
		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();
		/* 返回参数 */
		Map resultMap = new HashMap();
		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);

			/* 解析map参数 */
			int gradeId = MapUtils.getInteger(map, "gradeId");
			int userId = MapUtils.getInteger(map, "userId");

			Map<String, Object> gradeUserMap = gradeUserAppApi.getGradeByUserIdAndGradeId(userId, gradeId);

			String myXs = MapUtils.getString(gradeUserMap, "myXueshi");
			if (myXs == null) {
				myXs = "0.0";
			}else{
				DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
				df.setRoundingMode(RoundingMode.FLOOR);  //取消四舍五入
				Double period=Double.valueOf(myXs)/Double.valueOf(2700);
				myXs = df.format(period);//返回的是String类型的
			}

			Map<String, Object> gMap = gradeAppApi.getById(gradeId);
			String percent = "0";
			double gradeTotle = 0.0;
			String bBi = MapUtils.getString(gMap, "requiredPeriod");// 班级必修学时
			String bXue = MapUtils.getString(gMap, "optionalPeriod");// 班级选修学时
			if (StringUtils.isNotEmpty(bBi) && StringUtils.isNotEmpty(bXue)) {
				gradeTotle = Double.valueOf(bBi) + Double.valueOf(bXue);
				if (gradeTotle != 0) {
					Double value = Double.valueOf(myXs) / gradeTotle;
					BigDecimal bg = new BigDecimal(Double.valueOf(value));
					Double per_d = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
					if (per_d >= 1) {
						per_d = (double) 1;
					}
					DecimalFormat df = new DecimalFormat("0.0");
					percent = df.format(per_d * 100) + "%";
				}
			}
			resultMap.put("myXueshi", myXs);
			resultMap.put("gradeName", gradeUserMap.get("gradeName"));
			resultMap.put("logoPath", gradeUserMap.get("logoPath"));
			// ##百分比
			resultMap.put("percent", percent);
			resultMap.put("graduateDays", gradeUserMap.get("remainedDays"));

		} else
			error = "未接受到参数";
		if (StringUtils.isNotEmpty(error))
			return JsonResponse.error(error);
		return JsonResponse.success(resultMap);
	}

	/**
	 * 获取培训班详细信息
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/gradeInfo.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse gradeInfo(String data) throws IOException {
		String error = null;

		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();
		/* 返回参数 */
		Map resultMap = new HashMap();
		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);

			int gradeId = Integer.valueOf(MapUtils.getString(map, "gradeId"));
			/* 班级信息 */
			Map<String, Object> grade = gradeAppApi.getById(gradeId);

			String teacherIdStr = MapUtils.getString(grade, "headTeacherId");
			int teacherId = 0;
			if (StringUtils.isNotEmpty(teacherIdStr)) {
				teacherId = Integer.valueOf(teacherIdStr);
			}
			Map<String, Object> teacherMap = null;
			if (teacherId != 0) {
				teacherMap = userAppApi.getUserByUserId(teacherId);
			}

			resultMap.put("grade", grade);
			resultMap.put("teacher", teacherMap);
		} else
			error = "未接受到参数";
		if (StringUtils.isNotEmpty(error))
			return JsonResponse.error(error);
		return JsonResponse.success(resultMap);
	}

	/**
	 * 培训班课程列表
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listGradeCourse.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse listGradeCourse(String data) throws IOException {
		String error = null;
		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();
		/* 返回参数 */
		Map resultMap = new HashMap();
		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);

			/* 解析map参数 */
			int gradeId = Integer.valueOf(MapUtils.getString(map, "gradeId"));
			int userId = Integer.valueOf(MapUtils.getString(map, "userId"));
			int studyed = Integer.valueOf(MapUtils.getString(map, "studyed"));
			int status = Integer.valueOf(MapUtils.getString(map, "status"));
			int startIndex = Integer.valueOf(MapUtils.getString(map, "startIndex"));
			int pageSize = Integer.valueOf(MapUtils.getString(map, "pageSize"));

			Page page = new Page();
			map.put("gradeId", gradeId);
			map.put("state", status);
			map.put("dq", 1);
			map.put("userId", userId);
			page.setMap(map);
			page.setPageSize(pageSize);
			page.setStartLimit(startIndex);
			if (studyed == 1) { // 全部课程
				page = courseUserAppApi.getAllGradeCoursePage(page);
			} else if (studyed == 2) {
				page = courseUserAppApi.getGradeCoursePage(page);
			} else if (studyed == 3 || studyed == 4) {
				if (studyed == 3) {
					map.put("isOver", 2);
					page.setMap(map);
					page = courseUserAppApi.getCoursePage(page);
				} else if (studyed == 4) {
					map.put("isOver", 1);
					page.setMap(map);
					page = courseUserAppApi.getCoursePage(page);
				}
			}

			resultMap.put("page", page);
		} else
			error = "未接受到参数";
		if (StringUtils.isNotEmpty(error))
			return JsonResponse.error(error);
		return JsonResponse.success(resultMap);
	}

	/**
	 * 历史培训班详细信息
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/gradeRecord.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse gradeRecord(String data) throws IOException {
		String error = null;
		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();
		/* 返回参数 */
		Map resultMap = new HashMap();

		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);

			/* 解析map参数 */
			int userId = MapUtils.getInteger(map, "userId");
			int gradeId = MapUtils.getInteger(map, "gradeId");

			Map<String, Object> gRMap = gradeAppApi.getById(gradeId);
			Map<String, Object> uRMap = userAppApi.getUserByUserId(userId);
			resultMap.put("grade", gRMap);
			resultMap.put("user", uRMap);

			Page page = new Page();
			Map bm = new HashMap();
			bm.put("gradeId", gradeId);
			bm.put("userId", userId);
			bm.put("status", 1);
			page.setMap(bm);
			Page<List<Map<String, Object>>> bPage = courseUserAppApi.getHistoryGradeCoursePage(page);
			Map xm = new HashMap();
			xm.put("gradeId", gradeId);
			xm.put("userId", userId);
			xm.put("status", 2);
			page.setMap(xm);
			Page<List<Map<String, Object>>> xPage = courseUserAppApi.getHistoryGradeCoursePage(page);

			resultMap.put("obligatoryList", bPage);
			resultMap.put("electiveList", xPage);
		} else
			error = "未接受到参数";
		if (StringUtils.isNotEmpty(error))
			return JsonResponse.error(error);
		return JsonResponse.success(resultMap);
	}
}
