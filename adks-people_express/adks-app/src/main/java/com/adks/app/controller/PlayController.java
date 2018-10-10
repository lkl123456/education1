package com.adks.app.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.adks.app.util.MD5Encrypt;
import com.adks.commons.util.Des;
import com.adks.dubbo.api.interfaces.app.course.CourseAppApi;
import com.adks.dubbo.api.interfaces.web.course.CourseApi;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/api/play")
public class PlayController {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(PlayController.class);

	@Autowired
	private CourseAppApi courseAppApi;
	@Autowired
	private CourseApi courseApi;

	/**
	 * 获取收藏的全部课程
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@RequestMapping(value = "/listCollection.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse listCollection(String data) throws IOException {
		String error = null;
		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();
		/* 返回参数 */
		Map resultMap = new HashMap();

		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);
			/* 用户id */
			int userId = MapUtils.getInteger(map, "userId");
			String gradeId = MapUtils.getString(map, "gradeId");
			int courseId = MapUtils.getInteger(map, "courseId");

			if (courseId != 0) {
				Map<String, Object> rMap = courseAppApi.getById(courseId);
				resultMap.put("", rMap);
			}
			if (StringUtils.isEmpty(gradeId)) {
				gradeId = "CourseSuper";
			}
		} else
			error = "未接受到参数";
		if (StringUtils.isNotEmpty(error))
			return JsonResponse.error(error);
		return JsonResponse.success(resultMap);
	}

	/**
	 * 获取播放页面地址
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getView.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse getView(String data) throws IOException {
		HttpServletRequest request = null;
		String error = null;
		String path = "";
		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();
		/* 返回参数 */
		Map resultMap = new HashMap();
		Map<String, Object> rMap = new HashMap<String, Object>();

		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);
			/* 用户id */
			int userId = MapUtils.getInteger(map, "userId");
			int courseId = MapUtils.getInteger(map, "courseId");
			String gradeId = MapUtils.getString(map, "gradeId");

			if (courseId != 0) {
				rMap = courseAppApi.getById(courseId);
				resultMap.put("", rMap);
			}
			if (StringUtils.isEmpty(gradeId)) {
				gradeId = "CourseSuper";
			}

			String SessionId = userId + "";
			Long Systime = new Date().getTime();
			// 计算 Location 播放时间
			Integer Location = 0;
			// if ( ! "CourseSuper".equals( GreadId)) {
			Location = getLocation(request, userId, courseId, gradeId);
			if(Location==null){
				Location=0;
			}
			// }
			int courseType = MapUtils.getInteger(rMap, "courseType");
			// 课件地址
			String scormServer = getScormServer(request, courseId, courseType);

			String keystu = "adks2016" + courseId + userId + gradeId + Location + SessionId + courseType + Systime;
			String StudyKey = MD5Encrypt.encrypt(keystu);
			StudyKey = StudyKey.toLowerCase();

			String Cname = Des.getDesInstance().strEnc(MapUtils.getString(rMap, "courseName"), "adks1", "adks2",
					"adks3");
			// String path = scormServer + "&CourseId=" + CourseId + "&Cname=" +
			// Cname +"&StudyKey=" + StudyKey +"&CourseCode=" + course.getCode()
			// +
			// "&UserId=" + UserId + "&GreadId=" + GreadId + "&Location=" +
			// Location
			// + "&SessionId=" + SessionId + "&ScormOrVideo=" + ScormOrVideo+
			// "&Systime=" + Systime;

			// http://v1.bjadks.com/adks/";
			// http://59.46.46.81/SQ_SPCRM/scormPlay/427846/9A718450613F2E3A2BC159F08AA2E3991A843C1B5653E13D49014D222533C9DD06D5F0ECC0C9AC9EAB8B42AF7A8A84330CEBEAF64307C684/170/QS1603111457693874210/2/19/_256K/4BBE8ECB902FBD62CCCE7093534FA5F5/1478511858318/2392/1dd55fbae8ddec3322f1a06c9fefd115.shtml
			path = scormServer + "/" + courseId + "/" + Cname + "/" + courseType + "/"
					+ MapUtils.getString(rMap, "courseCode") + "/" + userId + "/" + gradeId + "/" + "_256K" + "/"
					+ Cname + "/" + Systime + "/" + Location + "/" + StudyKey + ".shtml";
			System.out.println("###########path#############"+path);
		} else
			error = "未接受到参数";
		if (StringUtils.isNotEmpty(error))
			return JsonResponse.error(error);
		return JsonResponse.success(path);
	}

	private Integer getLocation(HttpServletRequest request, Integer UserId, Integer CourseId, String GreadId) {

//		HttpClient httpclient = new DefaultHttpClient();
//
//		//String url = "http://" + request.getServerName() + ":" + request.getServerPort();
//		// 因为http://218.75.139.81:8100 这种外网地址访问不了自己。。。
//		// 对 cdgbjy.com 写死
//		// String url = "http://localhost:8000";
//		String url = "http://localhost/course/CourseUserLocation.do?userId=" + UserId + "&CourseId=" + CourseId + "&time="
//				+ (new Date()).getTime() + "&gradeId=0";
//		
//		HttpGet get = new HttpGet(url);
//		HttpResponse response = null;
		try {
//			response = httpclient.execute(get);
//			String result = EntityUtils.toString(response.getEntity());
			Integer Location=courseApi.getCourseSuperUserLocation(CourseId, UserId);

			return Location;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getScormServer(HttpServletRequest request, Integer courseId, Integer cwType) {
		String videoServer = courseAppApi.getVideoServer();
		if (cwType == 171||cwType == 683){
			videoServer = videoServer.replace("scormPlay", "mobilePlay");
		}
		return videoServer;
	}
	
}
