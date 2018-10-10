package com.adks.web.framework.timer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.commons.util.DateTimeUtil;
import com.adks.dubbo.api.interfaces.web.user.UserApi;

/**
 * 
 * ClassName MyTimerCleanUserOnline
 * @Description：定时任务-清理用户在线表
 * @author xrl
 * @Date 2017年6月27日
 */
public class MyTimerCleanUserOnline{

	@Autowired
	private UserApi userApi;
	
	public void execute(){  
        try{  
        	System.out.println("定时任务 执行-清除已过期在线用户");
        	//每天凌晨5点清理
        	//由于存在登录时间为2099-12-12 00:00:00的数据，所以也要清理这个时间段的用户
        	//清理adks_user_online中小于当前时间和大于当前时间的在线用户信息
    		Date nowDate=new Date();
    		Date errorDate=DateTimeUtil.stringToDate("2099-12-12 00:00:00", "yyyy-MM-dd HH:mm:ss");
    		Map<String, Object> delMap=new HashMap<>();
    		delMap.put("nowDate", nowDate);
    		delMap.put("errorDate", errorDate);
    		userApi.deleteUserOnLineBeforeNow(delMap);
    		System.out.println("##########定时任务 执行完毕 !##########");
         }catch(Exception ex){  
             ex.printStackTrace();  
         }  
     }  

}
