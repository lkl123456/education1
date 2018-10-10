package com.sd.spcrm.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  时间日期帮助类
 * @author lxh
 *
 */
public class DateTimeUtil {

	// String 格式  yyyy-MM-dd
	public static final String DATE_STRING_YMD = "yyyy-MM-dd";

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	
	
	/** 获取当前系统时间
	 * @return
	 */
	public static Date getCurrentTime(){
		Calendar cld = Calendar.getInstance();
		return cld.getTime();
	}
	
	/** Date 转 String 
	 * @param date 需要转换的日期时间
	 * @param dateStr  转换后的格式
	 * @return
	 */
	public static String dateToString(Date date, String dateStr) {
		DateFormat dateFormat = new SimpleDateFormat(dateStr);
		return dateFormat.format(date);
	}

	/** String 类型日期时间转 Date
	 * @param str 时间格式（如：yyyy-MM-dd HH:mm:ss）
	 * @param dateStr 转换的格式
	 * @return
	 */
	public static Date stringToDate(String dateStr, String str) {
		DateFormat dateFormat = new SimpleDateFormat(str);
		Date date = null;
		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/** (参数格式必须为：yyyy年) - 只截取年份数字部分(yyyy)
	 * @param str 
	 * @return
	 */
	public static String getYearByStr(String str) {
		if(str.length() < 5){
			return null;
		}
		String temp = "";
		for(int i = 0; i < 4; i++){
			temp += str.charAt(i);
		}
		return temp;
	}
	
	/**   
	* 计算两个日期之间相差的天数   
	* @param smdate 较小的时间  
	* @param bdate  较大的时间  
	* @return 相差天数  
	* @throws ParseException   
	*/     
	public static int daysBetween(Date smdate,Date bdate) {  
		
		int between_daysInt = 0;
		try{
	      
		  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");   
	      smdate=sdf.parse(sdf.format(smdate));   
	      bdate=sdf.parse(sdf.format(bdate));   
	      Calendar cal = Calendar.getInstance();     
	      cal.setTime(smdate);     
	      long time1 = cal.getTimeInMillis();                  
	      cal.setTime(bdate);     
	      long time2 = cal.getTimeInMillis();          
	      long between_days=(time2-time1)/(1000*3600*24);   
	      between_daysInt = Integer.parseInt(String.valueOf(between_days));
		}catch(Exception e){
			e.printStackTrace();
		}
	      
	      return between_daysInt;            
	}
	
	/**  
	*计算两个字符串日期之间相差的天数 
	*/  
	public static int daysBetween(String smdate,String bdate) throws Exception{   
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");   
	    Calendar cal = Calendar.getInstance();     
	    cal.setTime(sdf.parse(smdate));     
	    long time1 = cal.getTimeInMillis();                  
	    cal.setTime(sdf.parse(bdate));     
	    long time2 = cal.getTimeInMillis();          
	    long between_days=(time2-time1)/(1000*3600*24);   
	             
	    return Integer.parseInt(String.valueOf(between_days));      
	}
	
	/** 时间转秒数 (00:00:00)
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static long stringToLong(String str){
		long lon = 0;
		String[] ss = str.split(":");
		/*lon = lon + Long.parseLong(ss[0]) * 3600 * 1000;
		lon = lon + Long.parseLong(ss[1]) * 60 * 1000; 
		lon = lon + Long.parseLong(ss[2]) * 1000;*/
		lon = lon + Long.parseLong(ss[0]) * 3600;
		lon = lon + Long.parseLong(ss[1]) * 60; 
		lon = lon + Long.parseLong(ss[2]);
		return lon;
	}
	
	/**  秒数转时间 (00:00:00)
	 * @param lon
	 * @return
	 * @throws ParseException
	 */
	public static String longToString(long seconds){
		
		String str = "";
		if( seconds<1 ){
			
			str = "00:00:00";
			
		}else{
		
			/*long hh = lon/3600/1000;
			long mm = (lon - hh * 3600 * 1000)/60/1000;
			long ss = (lon - hh * 3600 * 1000 - mm * 60 * 1000)/1000;*/
			long hh = seconds/3600;
			long mm = (seconds - hh * 3600)/60;
			long ss = seconds - hh * 3600 - mm * 60;
			if(hh < 10){
				str = "0" + hh + ":";
			}else{
				str = str + hh + ":";
			}
			if(mm < 10){
				str = str + "0" + mm + ":";
			}else{
				str = str + mm + ":";
			}
			if(ss < 10){
				str = str + "0" + ss;
			}else{
				str = str + ss;
			}
			
		}
		return str;
	}
	
	/**  秒数转时间 (__小时__分钟)
	 * @param lon
	 * @return
	 * @throws ParseException
	 */
	public static String longMMToString(long lon){
		long hh = lon/3600;
		long mm = (lon - hh * 3600)/60;
		return hh + " 时" + mm + " 分";
	}
	
	/**  秒数转时间 (__小时__分钟)
	 * @param lon
	 * @return
	 * @throws ParseException
	 */
	public static Map longMMToMap(long lon){
		Map<String,String> map=new HashMap<String, String>();
		long hh = lon/3600;
		long mm = (lon - hh * 3600)/60;
		map.put("hour", String.valueOf(hh));
		map.put("minute", String.valueOf(mm));
		return map;
	}
	
	/** double (10.0小时)转 String (____小时__分钟)
	 * @param db
	 * @return
	 */
	public static String doubleToString(double db){
		String[] str = (db+"").split("\\.");
		String timeStr = str[0] + " 时";
		if(str.length > 1 && Integer.parseInt(str[1]) > 0){
			String temp = str[1];
			if(Integer.parseInt(temp) > 10){
				temp = str[1].charAt(0) + "";
			}
			timeStr += (Integer.parseInt(temp) * 6) + " 分";
		}
		return timeStr;
	}
	
	public static String longToStringTime(double lon){
		Double db = (lon/3600);
		BigDecimal re = new BigDecimal(db+"");
		return re.setScale(1, BigDecimal.ROUND_HALF_UP).toString();
	}
	
	//比较两个日期的大小
	public static int compare_date(String DATE1, String DATE2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
	
	public static String integerToString(int lon){
		String str = "";
		int hh = lon/3600;
		int mm = (lon - hh * 3600)/60;
		int ss = lon - hh * 3600 - mm * 60;
		if(hh < 10){
			str = "0" + hh + ":";
		}else{
			str = str + hh + ":";
		}
		if(mm < 10){
			str = str + "0" + mm + ":";
		}else{
			str = str + mm + ":";
		}
		if(ss < 10){
			str = str + "0" + ss;
		}else{
			str = str + ss;
		}
		return str;
	}
	
	/** 时间转秒数 (00:00:00)
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Integer stringToInteger(String str){
		Integer lon = 0;
		String[] ss = str.split(":");
		lon = lon + Integer.parseInt(ss[0]) * 3600;
		lon = lon + Integer.parseInt(ss[1]) * 60; 
		lon = lon + Integer.parseInt(ss[2]);
		return lon;
	}
	
	
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 *@author: hqt
	 *@see:日期格式转换
	 *@date: Sep 28, 2013 9:27:54 AM
	 */
	public static String dateToString(Date date) {
		return dateFormat.format(date);
	}
	
	public static void main(String[] args) {
		System.out.println(longToString(1914));
	}
}
