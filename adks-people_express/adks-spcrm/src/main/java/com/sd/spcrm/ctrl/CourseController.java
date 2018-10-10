package com.sd.spcrm.ctrl;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.adks.commons.util.ComUtil;
import com.adks.commons.util.Des;
import com.adks.dubbo.api.data.course.CourseJson;
import com.sd.spcrm.utils.LogRecordAdks;
import com.sd.spcrm.utils.MD5EncryptSpcrm;
import com.sd.spcrm.webconfig.WebConfigSpcrm;

@Controller
public class CourseController {

	@RequestMapping(value="/scormplay",method=RequestMethod.POST)
	@ResponseBody
    public ModelAndView scormPlay(@ModelAttribute(value="course") CourseJson courseJson,ModelAndView mv){
		
		String courseurl = null;//课件地址
		
		String videoServer = null;//流媒体地址
		
		String updatepar = "";//修改参数
		
		String Studypar = "";//加密参数
		
		boolean isLogin = false;//是否登录
		
		try {
			
			String thisK =WebConfigSpcrm.studyKey+courseJson.getCourseId()+courseJson.getUserId()+courseJson.getGreadId()+courseJson.getLocation()+courseJson.getSessionId()
											+courseJson.getScormOrVideo()+courseJson.getSystime();
			
			//判断登录  设置相关属性
			if (ComUtil.isNotNullOrEmpty(courseJson.getSessionId()) && ComUtil.isNotNullOrEmpty(courseJson.getUserId())) {
				
				if (courseJson.getLocation()==null||courseJson.getLocation().equals("")||courseJson.getLocation().equals("null")||courseJson.getLocation().equals("undefined")) {
					//获取不到时间点，从头开始播放
					courseJson.setLocation("0");
				}
				
				if (courseJson.getGreadId()==null||courseJson.getGreadId().equals("")||courseJson.getGreadId().equals("null") || courseJson.getGreadId().equals("CourseSuper")) {
					//不是班级课程 设为课件超市课程
					courseJson.setGreadId("CourseSuper");
				}
				
				isLogin = true;
			}else {
				isLogin = false;
			}
			
			//设置三分屏和单视频 流媒体参数
			if (courseJson.getCourseCode()!=null&&!courseJson.getCourseCode().equals("")&&!courseJson.getCourseCode().equals("null")) {
				
				if (courseJson.getScormOrVideo().equals("170")) {
					//三分屏播放
					courseurl = courseJson.getCourseCode()+"/index.html";
					videoServer =WebConfigSpcrm.videoServer+courseJson.getCourseCode()+"/";
				}else {
					//单视频播放
					videoServer =WebConfigSpcrm.videoServer+courseJson.getCourseCode();
				}
			}
			
			//比较两次加密参数是否正确，防止篡改参数，如有修改，提示警告信息
			if (courseJson.getStudyKey() != null && !courseJson.getStudyKey().equals("")) {
				
				if (courseJson.getStudyKey().toUpperCase().equals(MD5EncryptSpcrm.encrypt(thisK))) {
					updatepar = "true";
				}else {
					updatepar = "false";
					System.out.println("用户ID"+courseJson.getUserId()+":打开课件ID"+courseJson.getCourseId()+"修改URL参数");
				}
			}
			
			//登录用户判断条件
			if (isLogin) {
				//判断参数个数是否全
				if (ComUtil.isNullOrEmpty(courseJson.getSystime()) || ComUtil.isNullOrEmpty(courseJson.getCname()) ||
					ComUtil.isNullOrEmpty(courseJson.getCourseCode()) || ComUtil.isNullOrEmpty(courseJson.getCourseId()) ||
					ComUtil.isNullOrEmpty(courseJson.getGreadId()) || ComUtil.isNullOrEmpty(courseJson.getLocation()) ||
					ComUtil.isNullOrEmpty(courseJson.getScormOrVideo()) || ComUtil.isNullOrEmpty(courseJson.getSessionId()) ||
					ComUtil.isNullOrEmpty(courseJson.getStudyKey()) || ComUtil.isNullOrEmpty(courseJson.getUserId())) {
					
					Studypar = "timeout";
					System.out.println("用户ID"+courseJson.getUserId()+":打开课件ID"+courseJson.getCourseId()+"参数个数不全");
				}
				
				//判断 登录用户是否超时
				if (courseJson.getLocation().contains("text/javascript")) {
					Studypar = "sessionout";
					System.out.println("用户ID"+courseJson.getUserId()+":打开课件ID"+courseJson.getCourseId()+"Session过期");
				}
			}
			
			//判断用户打开窗口时间差值是否超过5秒 时间戳
			
			
			if (ComUtil.isNotNullOrEmpty(courseJson.getHeartTimeLong())) {
				
				if (courseJson.getHeartTimeLong().equals(MD5EncryptSpcrm.encrypt(WebConfigSpcrm.studyKey))) {
					
					Studypar = "";
				}else {
					Long thisTimeLong = System.currentTimeMillis();
					Long heartTimeLong = Long.parseLong(Des.getDesInstance().strDec(courseJson.getHeartTimeLong().toString(), "adks123", "adks321", "adks213"));
					if ((thisTimeLong - heartTimeLong)/1000 > WebConfigSpcrm.TimeLong || (thisTimeLong - heartTimeLong) < 0) {
						Studypar = "timeout";
						System.out.println("用户ID"+courseJson.getUserId()+":打开课件ID"+courseJson.getCourseId()+"超时--时间差："+(thisTimeLong - heartTimeLong)/1000);
					}
				}
			}
			
		} catch (Exception e) {
			LogRecordAdks.info("打开课程发生错误信息如下："+e);
			e.printStackTrace();
		}
		
		//课程名称
		mv.addObject("CourseName", Des.getDesInstance().strDec(courseJson.getCname(), "adks1","adks2","adks3"));
		mv.addObject("Course", courseJson);
		mv.addObject("videoServer", videoServer);
		mv.addObject("StudySecret", Studypar);
		
		//如果是未登录...不计时..去除播放参数个数条件判断
//		if (!isLogin && Studypar.equals("timeout")) {
//			Studypar = "loginOut";
//		}
		
		//判断是否非法操作
		if (updatepar.equals("false") || Studypar.equals("timeout") || Studypar.equals("sessionout")) {
			
			mv.setViewName("/fail");//非法操作
		}else{//正常学习
			if (courseJson.getScormOrVideo().equals("170")) {
				mv.addObject("courseurl", courseurl);
				if (isLogin) {
					mv.setViewName("/scormPlay");//正常登录播放
				}else {
					mv.setViewName("/scormPlayTest");//未登录或者未有用户信息 则只打开播放窗口等参数
				}
				
			}else {
				if (isLogin) {
					mv.setViewName("/videoPlay");//正常登录播放
				}else {
					mv.setViewName("/videoPlayTest");//未登录或者未有用户信息 则只打开播放窗口等参数
				}
			}
		}
		
		return mv;
    }
	
	@RequestMapping(value="/videoPlay",method=RequestMethod.GET)
	@ResponseBody
    public ModelAndView videoPlay(@ModelAttribute(value="course") CourseJson courseJson,ModelAndView mv){
		
		String courseurl = null;//课件地址
		
		String videoServer = null;//流媒体地址
		
		String updatepar = "";//修改参数
		
		String Studypar = "";//加密参数
		
		boolean isLogin = false;//是否登录
		
		try {
			
			String thisK =WebConfigSpcrm.studyKey+courseJson.getCourseId()+courseJson.getUserId()+courseJson.getGreadId()+courseJson.getLocation()+courseJson.getSessionId()
											+courseJson.getScormOrVideo()+courseJson.getSystime();
			
			//判断登录  设置相关属性
			if (ComUtil.isNotNullOrEmpty(courseJson.getSessionId()) && ComUtil.isNotNullOrEmpty(courseJson.getUserId())) {
				
				if (courseJson.getLocation()==null||courseJson.getLocation().equals("")||courseJson.getLocation().equals("null")||courseJson.getLocation().equals("undefined")) {
					//获取不到时间点，从头开始播放
					courseJson.setLocation("0");
				}
				
				if (courseJson.getGreadId()==null||courseJson.getGreadId().equals("")||courseJson.getGreadId().equals("null") || courseJson.getGreadId().equals("CourseSuper")) {
					//不是班级课程 设为课件超市课程
					courseJson.setGreadId("CourseSuper");
				}
				
				isLogin = true;
			}else {
				isLogin = false;
			}
			
			//设置三分屏和单视频 流媒体参数
			if (courseJson.getCourseCode()!=null&&!courseJson.getCourseCode().equals("")&&!courseJson.getCourseCode().equals("null")) {
				
				if (courseJson.getScormOrVideo().equals("170")) {
					//三分屏播放
					courseurl = courseJson.getCourseCode()+"/index.html";
					videoServer =WebConfigSpcrm.videoServer+courseJson.getCourseCode()+"/";
				}else {
					//单视频播放
					videoServer =WebConfigSpcrm.videoServer+courseJson.getCourseCode();
				}
			}
			
			//比较两次加密参数是否正确，防止篡改参数，如有修改，提示警告信息
			if (courseJson.getStudyKey() != null && !courseJson.getStudyKey().equals("")) {
				
				if (courseJson.getStudyKey().toUpperCase().equals(MD5EncryptSpcrm.encrypt(thisK))) {
					updatepar = "true";
				}else {
					updatepar = "false";
					System.out.println("用户ID"+courseJson.getUserId()+":打开课件ID"+courseJson.getCourseId()+"修改URL参数");
				}
			}
			
			//登录用户判断条件
			if (isLogin) {
				//判断参数个数是否全
				if (ComUtil.isNullOrEmpty(courseJson.getSystime()) || ComUtil.isNullOrEmpty(courseJson.getCname()) ||
					ComUtil.isNullOrEmpty(courseJson.getCourseCode()) || ComUtil.isNullOrEmpty(courseJson.getCourseId()) ||
					ComUtil.isNullOrEmpty(courseJson.getGreadId()) || ComUtil.isNullOrEmpty(courseJson.getLocation()) ||
					ComUtil.isNullOrEmpty(courseJson.getScormOrVideo()) || ComUtil.isNullOrEmpty(courseJson.getSessionId()) ||
					ComUtil.isNullOrEmpty(courseJson.getStudyKey()) || ComUtil.isNullOrEmpty(courseJson.getUserId())) {
					
					Studypar = "timeout";
					System.out.println("用户ID"+courseJson.getUserId()+":打开课件ID"+courseJson.getCourseId()+"参数个数不全");
				}
				
				//判断 登录用户是否超时
				if (courseJson.getLocation().contains("text/javascript")) {
					Studypar = "sessionout";
					System.out.println("用户ID"+courseJson.getUserId()+":打开课件ID"+courseJson.getCourseId()+"Session过期");
				}
			}
			
			//判断用户打开窗口时间差值是否超过5秒 时间戳
			
			
			if (ComUtil.isNotNullOrEmpty(courseJson.getHeartTimeLong())) {
				
				if (courseJson.getHeartTimeLong().equals(MD5EncryptSpcrm.encrypt(WebConfigSpcrm.studyKey))) {
					
					Studypar = "";
				}else {
					Long thisTimeLong = System.currentTimeMillis();
					Long heartTimeLong = Long.parseLong(Des.getDesInstance().strDec(courseJson.getHeartTimeLong().toString(), "adks123", "adks321", "adks213"));
					if ((thisTimeLong - heartTimeLong)/1000 > WebConfigSpcrm.TimeLong || (thisTimeLong - heartTimeLong) < 0) {
						Studypar = "timeout";
						System.out.println("用户ID"+courseJson.getUserId()+":打开课件ID"+courseJson.getCourseId()+"超时--时间差："+(thisTimeLong - heartTimeLong)/1000);
					}
				}
			}
			
		} catch (Exception e) {
			LogRecordAdks.info("打开课程发生错误信息如下："+e);
			e.printStackTrace();
		}
		
		//课程名称
		mv.addObject("CourseName", Des.getDesInstance().strDec(courseJson.getCname(), "adks1","adks2","adks3"));
		mv.addObject("Course", courseJson);
		mv.addObject("videoServer", videoServer);
		mv.addObject("StudySecret", Studypar);
		
		//如果是未登录...不计时..去除播放参数个数条件判断
//		if (!isLogin && Studypar.equals("timeout")) {
//			Studypar = "loginOut";
//		}
		
		//判断是否非法操作
		if (updatepar.equals("false") || Studypar.equals("timeout") || Studypar.equals("sessionout")) {
			
			mv.setViewName("/fail");//非法操作
		}else{//正常学习
			if (courseJson.getScormOrVideo().equals("170")) {
				mv.addObject("courseurl", courseurl);
				if (isLogin) {
					mv.setViewName("/scormPlay");//正常登录播放
				}else {
					mv.setViewName("/scormPlayTest");//未登录或者未有用户信息 则只打开播放窗口等参数
				}
				
			}else {
				if (isLogin) {
					mv.setViewName("/courseMain");//正常登录播放
				}else {
					mv.setViewName("/videoPlayTest");//未登录或者未有用户信息 则只打开播放窗口等参数
				}
			}
		}
		
		return mv;
    }
	
	/**
	 * 预览上传的视频
	 * @param courseJson
	 * @param mv
	 * @return
	 */
	@RequestMapping(value="/courseView",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView courseView(@ModelAttribute(value="course") CourseJson courseJson,ModelAndView mv){
		
		String courseurl = null;//课件地址
		String videoServer = null;//流媒体地址

		if (courseJson.getScormOrVideo().equals("170")) {
			//三分屏播放
			courseurl = courseJson.getCourseCode()+"/index.html";
			videoServer =WebConfigSpcrm.videoServer+courseJson.getCourseCode()+"/";
			mv.setViewName("/scormView");
			mv.addObject("courseurl", courseurl);
		}else {
			//单视频播放,单视频走H5
//			videoServer =WebConfig.videoServer+courseJson.getCourseCode();
//			mv.setViewName("/videoView");
		}
			
		//课程名称
		mv.addObject("CourseName", Des.getDesInstance().strDec(courseJson.getCname(), "adks1","adks2","adks3"));
		mv.addObject("Course", courseJson);
		mv.addObject("videoServer", videoServer);
		
		return mv;
	}
}
