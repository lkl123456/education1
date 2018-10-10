package com.adks.web.controller.course;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.dubbo.api.interfaces.web.course.CourseApi;

@Controller
@RequestMapping({"/course"})
public class CoursePlayController {
	@Autowired
	private CourseApi courseApi;
	
	//得到课程的location
	@RequestMapping(value = "/CourseUserLocation.do")
	@ResponseBody
	public void CourseUserLocation(Integer courseId,Integer gradeId,Integer userId, HttpServletRequest request,HttpServletResponse response){
		Integer Location= 0 ;
		PrintWriter print=null;
		try {
			if (gradeId != null && gradeId!=0 && !gradeId.equals("CourseSuper")&& !gradeId.equals("undefined")&& !gradeId.equals("")) {
				//班级用户
				Location=courseApi.getCourseUserLocation(courseId,gradeId,userId);
			}else {
				//课件超市
				Location=courseApi.getCourseSuperUserLocation(courseId,userId);
			}
			response.setContentType("application/text");
			print = response.getWriter();
			print.write(Location.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			print.flush();
			print.close();
			
		}
	}
	
	//得到播放地址
	@RequestMapping(value = "/getVideoServer.do")
	@ResponseBody
	public void getVideoServer(Integer courseId,Integer courseType, HttpServletRequest request,HttpServletResponse response){
		String VideoServer= "" ;
		PrintWriter print=null;
		try {
			VideoServer=courseApi.getVideoServer(courseId);
			if (courseType != null && courseType == 170) {
				//三分屏
				VideoServer=VideoServer!=null?VideoServer:"";
			}else {
				//普通视频
				VideoServer=VideoServer!=null?VideoServer.replace("scormPlay", "videoPlayGJ"):"";
				//App端播放地址
//				VideoServer=VideoServer!=null?VideoServer.replace("scormPlay", "mobilePlay"):"";
			}
			/*更新课程点击量 start*/
			if(null != courseId && !"".equals(courseId)){
				courseApi.updateCourseClickNum(courseId); 	//更新课程点击量 +1
			}
			/*更新课程点击量 end*/ 
			
			response.setContentType("application/text");
			print = response.getWriter();
			print.write(VideoServer);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			print.flush();
			print.close();
			
		}
	}
	
	//得到当前课程的码流
	@RequestMapping(value = "/getCourseVideoRP.do")
	@ResponseBody
	public void getCourseVideoRP(Integer courseId, HttpServletRequest request,HttpServletResponse response){
		String  VideoRP= "_256K" ;
		PrintWriter print=null;
		try {
			VideoRP=courseApi.getCourseVideoRP(courseId);
			
			response.setContentType("application/text");
			print = response.getWriter();
			print.write(VideoRP);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			print.flush();
			print.close();
			
		}
	}
}
