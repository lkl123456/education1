package com.sd.spcrm.webconfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.adks.dubbo.api.data.course.CourseJson;


/** 
 * 初始化进程类，采用单例模式  
 * ConcurrentHashMap 
 * @author 李 博
 *  
 */  
public class ProgressManagerSpcrm { 
 
	private static ProgressManagerSpcrm ProgressInstance = null;
	
	private Map<String, CourseJson> CourseJsonMap = null; 
	
	private ProgressManagerSpcrm() {
		
		CourseJsonMap = new ConcurrentHashMap<String, CourseJson>();
	}  
	
	/**
	 * 获取单例进程
	 */
	public static  synchronized ProgressManagerSpcrm getProgessProgessInstance() {  
        if (ProgressInstance == null) {
        	
            ProgressInstance = new ProgressManagerSpcrm();
        	
        }
        return ProgressInstance;  
    }
	
	
	/**
	 * 给map中存储值
	 * @param key
	 * @param CourseJson
	 * @throws Exception
	 */
	public void setCourseJsonMap(String key,CourseJson courseJson) throws Exception {  
		
		CourseJsonMap.put(key, courseJson);
	}
	
	/**
	 *  获取当前map存储的值
	 * @return
	 */
	public  Map<String, CourseJson> getCourseJsonMap() {
		
		return CourseJsonMap;
	}
	
	/**
	 * 重新创建CourseJsonMap
	 */
	public void createCourseJsonMap() {
		
		CourseJsonMap = new ConcurrentHashMap<String, CourseJson>();
	}
}
