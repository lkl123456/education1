package com.sd.spcrm.ftp.util;

import java.io.IOException;
import java.util.Properties;

public class ConfigReaderUtil {
	
	private static Properties proes = new Properties();
	static{
		try {
			proes.load(ConfigReaderUtil.class.getResourceAsStream("../config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static String getValue(String key){
		return (String) proes.get(key);
	}
	
	public static String getScromPath(){
		return ConfigReaderUtil.getValue("Scrom_Path");
	}
	
	public static String getUnZipPath(){
		return ConfigReaderUtil.getValue("Un_Zip_Path");
	}
	
	public static Integer getUnZipThreadNum(){
		return Integer.parseInt(ConfigReaderUtil.getValue("Un_Zip_ThreadNum"));
	}
	public static Integer getInitialDelay(){
		return Integer.parseInt(ConfigReaderUtil.getValue("initialDelay"));
	}
	public static Integer getDelay(){
		return Integer.parseInt(ConfigReaderUtil.getValue("delay"));
	}
	
	/**
	 * @Description: 设置课件本身监控是否开启
	 * @return    0  默认不开启
	 * @author: yzh
	 * @date: 2014-12-22 下午01:30:31
	 */
	public static Integer getIsMonitor(){
		String temp = ConfigReaderUtil.getValue("isMonitor");
		if(temp != null && !"".equals(temp)){
			return Integer.parseInt(temp);
		}
		return 0;
	}
	
	/**
	 * @Description: 课件服务 nginx url
	 * @return    
	 * @author: yzh
	 * @date: 2014-12-22 下午02:10:49
	 */
	public static String getVideoServerUrlStr(){
		return ConfigReaderUtil.getValue("videoServerUrl_Str");
	}
	
}
