package com.sd.spcrm.webconfig;

import java.util.Date;

import com.adks.dubbo.api.data.course.HeartBeat;
import com.sd.spcrm.utils.PostHttpClientUtil;


public class HeartBeatTimer{
	
	public void startRun() {  
		
		//先判断是否开启心跳检测
		if (WebConfigSpcrm.heartStart) {
			HeartBeat heartBeat =HeartBeat.getHeartBeatInstance();
			
			heartBeat.setArea(WebConfigSpcrm.HeartArea);
			heartBeat.setUrl(WebConfigSpcrm.HeartUrl);
			heartBeat.setState("alive");
			//记录心跳时间毫秒数--此处必须记录课程服务端--发送心跳的时间-用户打开课件是要传回来 判断是否时间差 判断用户打开链接有效期
			heartBeat.setRecvedTimeLong(System.currentTimeMillis());
			if (heartBeat != null) {
				
//				PostHttpClientUtil.sendHeartPost(WebConfigSpcrm.WebHeartUrl, heartBeat);
			}
		}
	} 
}
