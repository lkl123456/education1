package com.sd.spcrm.ctrl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.commons.util.ComUtil;
import com.adks.commons.util.DateTimeUtil;
import com.adks.commons.util.MD5Encrypt;
import com.adks.dubbo.api.data.course.CourseJson;
import com.sd.spcrm.utils.LogRecordAdks;
import com.sd.spcrm.webconfig.ProgressManagerSpcrm;
import com.sd.spcrm.webconfig.WebConfigSpcrm;

@Controller
public class AddCourseController {

	@RequestMapping(value="/AddCourse",method=RequestMethod.POST)
    @ResponseBody
    public void addCourse(@ModelAttribute(value="course") CourseJson courseJson, BindingResult result ){
        
		if(ComUtil.isNotNullOrEmpty(courseJson.getCourseId()) &&
		           ComUtil.isNotNullOrEmpty(courseJson.getCourseCode())&&
		           ComUtil.isNotNullOrEmpty(courseJson.getGreadId())&&
		           ComUtil.isNotNullOrEmpty(courseJson.getLocation())&&
		           ComUtil.isNotNullOrEmpty(courseJson.getScormOrVideo())&&
				   ComUtil.isNotNullOrEmpty(courseJson.getSessionId())&&
				   ComUtil.isNotNullOrEmpty(courseJson.getSessiontime())&&
				   ComUtil.isNotNullOrEmpty(courseJson.getSystime())&&
				   ComUtil.isNotNullOrEmpty(courseJson.getUserId())&&
				   !courseJson.getSessiontime().equals("00:00:00")&&
				   !courseJson.getDuration().equals("undefined")&&
				   !courseJson.getDuration().equals("00:00:00")){
			
//			System.out.println("********************当前已进入addCourse********************************");
			ProgressManagerSpcrm progressManager = ProgressManagerSpcrm.getProgessProgessInstance();
	    	
	    	Map<String, CourseJson> CourseJsonMap = progressManager.getCourseJsonMap();
	    	
	    	StringBuffer keyBuffer=new StringBuffer(200);
	    	
	    	keyBuffer.append("UserId"+courseJson.getUserId()+"_");
	    	keyBuffer.append("GreadId"+courseJson.getGreadId()+"_");
	    	keyBuffer.append("CourseId"+courseJson.getCourseId()+"_");
	    	keyBuffer.append("CourseCode"+courseJson.getCourseCode()+"_");
	    	keyBuffer.append("SessionId"+courseJson.getSessionId());
	    	/*从key中先去除SysTime 不区分是第几次打开的窗口 每次都是播放时长累计*/
	    	
	    	try {
	    		//参数加密、防作弊
	    		courseJson.setStudyKey((MD5Encrypt.encrypt(WebConfigSpcrm.studyKey)));
	    		//存入当前时间的毫秒数
	    		courseJson.setLastSysTime(System.currentTimeMillis());
	    		//设置课件超市存放GreadId 为CourseSuper
	    		if(ComUtil.isNotNullOrEmpty(courseJson.getGreadId())){
	    			
	    			if(courseJson.getGreadId().equals("0") || courseJson.getGreadId().equals("CourseSuper")){
	    				
	    				courseJson.setGreadId("CourseSuper");
	    			}
	    		}
	    		
	    		if (CourseJsonMap.get(keyBuffer.toString())==null) {
	    			//CourseMap不存在Course、直接put
//	    			System.out.println("********************当前已进入addCourse---新增-----setCourseJsonMap********************************");
	    			progressManager.setCourseJsonMap(keyBuffer.toString(), courseJson);
				}else {
					//CourseMap中存在当前Course，执行时长累计
//					System.out.println("********************当前已进入addCourse---更新-----setCourseJsonMap********************************");
					if (courseJson.getSessiontime()!=null && !courseJson.getSessiontime().equals("null") && !courseJson.getSessiontime().equals("00:00:00")) {
						//从courseJsonMap 取出已存储的进度信息 转换成courseJson
						CourseJson oldCourse = CourseJsonMap.get(keyBuffer.toString());
						
						Integer oldSessionTimeInt = DateTimeUtil.stringToInteger(oldCourse.getSessiontime());
						
						Integer newSessionTimeInt = DateTimeUtil.stringToInteger(courseJson.getSessiontime());
						
						oldCourse.setSessiontime(DateTimeUtil.integerToString(oldSessionTimeInt+newSessionTimeInt));//时长累计 存入当前课程
					}
					
				}
				
			} catch (Exception e) {
				
				LogRecordAdks.error("添加课程发生错误信息如下"+e);
				
				e.printStackTrace();
			}
		}else{
        	LogRecordAdks.info("未进入AddCourse--getCourseId()："+courseJson.getCourseId()
    				+"-getCourseCode():"+courseJson.getCourseCode()
    				+"-getDuration():"+courseJson.getDuration()
    				+"-getGreadId():"+courseJson.getGreadId()
    				+"-getLocation():"+courseJson.getLocation()
    				+"-getScormOrVideo():"+courseJson.getScormOrVideo()
    				+"-getSessionId():"+courseJson.getSessionId()
    				+"-getSessiontime():"+courseJson.getSessiontime()
    				+"-getSystime():"+courseJson.getSystime()
    				+"-getUserId():"+courseJson.getUserId()
    		);
        }
		
    }
	
	
	
	@RequestMapping(value="/CourseMapJson")
    @ResponseBody
    public Map<String , CourseJson> showCourseMapJson(HttpServletRequest req){
        
		return WebConfigSpcrm.progressManager.getCourseJsonMap();

    }
	

}
