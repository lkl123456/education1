package com.sd.spcrm.webconfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.course.CourseJson;
import com.adks.dubbo.api.interfaces.web.course.CourseUserApi;

public class MyThreadTimerAll{
	private Map<String , CourseJson> TurnMap=null;//转移储存的map
	
	@Autowired
	private CourseUserApi courseuserApi;
	
	public void startRun() {  
		
	   TurnMap = new ConcurrentHashMap<String, CourseJson>();
	   
	   ProgressManagerSpcrm progressManager = ProgressManagerSpcrm.getProgessProgessInstance();
	   
	   Map<String , CourseJson> courseMap = progressManager.getCourseJsonMap();
	   
//	   System.out.println("********************当前已进入MyThreadTimerAll-&courseMap.size  is :"+courseMap.size());
	   
	   if (courseMap.size() > 0) {
			
		   TurnMap=courseMap;
		   
		   //重新创建CourseMap
		   progressManager.createCourseJsonMap();
		   
		   if (TurnMap.size()>0) {
			   courseuserApi.updateUserStudySpeed(TurnMap);
		   }
		   
	   }
	} 
}
