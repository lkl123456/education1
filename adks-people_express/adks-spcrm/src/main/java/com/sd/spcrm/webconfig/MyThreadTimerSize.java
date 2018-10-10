package com.sd.spcrm.webconfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.course.CourseJson;
import com.adks.dubbo.api.interfaces.web.course.CourseUserApi;

public class MyThreadTimerSize{
	
	private Map<String , CourseJson> TurnMap=null;
	
	@Autowired
	private CourseUserApi courseuserApi;
	
	public void startRun() {  
			
		   TurnMap = new ConcurrentHashMap<String, CourseJson>();
		   
		   ProgressManagerSpcrm progressManager = ProgressManagerSpcrm.getProgessProgessInstance();
		   
		   Map<String , CourseJson> courseJsonMap = progressManager.getCourseJsonMap();
		   
		   if (courseJsonMap.size() >= WebConfigSpcrm.FinalMapSize) {
			   
			   TurnMap=courseJsonMap;
			   
			   //重新创建CourseMap
			   progressManager.createCourseJsonMap();
			   
			   courseuserApi.updateUserStudySpeed(TurnMap);
		   }
		   
	} 
}
