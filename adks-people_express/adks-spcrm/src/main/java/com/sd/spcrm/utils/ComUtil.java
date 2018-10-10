package com.sd.spcrm.utils;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.w3c.dom.NodeList;

/** 
 * 公用方法帮助类
 * @author lxh
 *
 */
public class ComUtil {

	/**
	 * 判断字符串是否为空
	 * 
	 * @param s
	 *            待判断的字符串
	 * @return boolean 返回 真 假
	 */
	public static boolean isNullOrEmpty(String s) {
		return (s == null) ||(s == "undefined") || (s.length() <= 0);
	}

	public static boolean isNotNullOrEmpty(String s) {
		return !isNullOrEmpty(s);
	}
	
	/**
	 *@author: hqt
	 *@see: 增加 double 判断 
 	 *@date: Oct 20, 2013 2:22:09 PM
	 */
	public static boolean isNullOrEmpty(Double s) {
		return (s == null) || (s.intValue() <= 0);
	}
	
	/**
	 *@author: hqt
	 *@see: 增加 double 判断
	 *@date: Oct 20, 2013 2:23:20 PM
	 */
	public static boolean isNotNullOrEmpty(Double s) {
		return !isNullOrEmpty(s);
	}
	
	public static boolean isNullOrEmpty(Float s) {
		return (s == null) || (s.intValue() <= 0);
	}
	
	public static boolean isNotNullOrEmpty(Float s) {
		return !isNullOrEmpty(s);
	}

	public static boolean isNullOrEmpty(Long s) {
		return (s == null) || (s <= 0);
	}

	public static boolean isNotNullOrEmpty(Long s) {
		return !isNullOrEmpty(s);
	}

	public static boolean isNullOrEmpty(Integer s) {
		//return (s == null) || (s.intValue() <= 0);
		return s == null;
	}

	public static boolean isNotNullOrEmpty(Integer s) {
		return !isNullOrEmpty(s);
	}
	public static boolean isNullOrEmpty(Map<?, ?> map) {
		return (map == null) || (map.isEmpty());
	}


	public static boolean isNotNullOrEmpty(Map<?, ?> map) {
		return !isNullOrEmpty(map);
	}

	public static boolean isNullOrEmpty(List<?> l) {
		return (l == null) || (l.isEmpty());
	}

	public static boolean isNotNullOrEmpty(List<?> l) {
		return !((l == null) || (l.isEmpty()));
	}

	public static boolean isNullOrEmpty(Object[] o) {
		return (o == null) || (o.length <= 0);
	}

	public static boolean isNotNullOrEmpty(Object[] o) {
		return !isNullOrEmpty(o);
	}

	public static boolean isNullOrEmpty(int[] o) {
		return (o == null) || (o.length <= 0);
	}

	public static boolean isNullOrEmpty(Vector<?> v) {
		return (v == null) || (v.size() <= 0);
	}

	public static boolean isNullOrEmpty(NodeList nl) {
		return (nl == null) || (nl.getLength() <= 0);
	}
	
	/** 计算二个数的百分比（不计算大于100%的，保留一位小数） 由于页面需要，%符号不自动添加
	 * @param num1 较大的数
	 * @param num2 较小的数
	 * @return 百分比
	 */
	public static String getPercent(double num1, double num2){
		if(num2 == 0){
			return "0";
		}
		if(num2 >= num1){
			return "100";
		}
		double temp = num2 * 100 / num1;
		String tempStr = (temp + "").charAt(0) + "";
		if(temp >= 10){
			tempStr += ((temp + "").charAt(1) + "");
			if((temp + "").charAt(3) != '0'){
				tempStr += (temp + "").charAt(2) + ((temp + "").charAt(3) + "");
			}
		}else{
			if((temp + "").charAt(2) != '0'){
				tempStr += (temp + "").charAt(1) + ((temp + "").charAt(2) + "");
			}
		}
		return tempStr;
	}
	
	public static String getIpAddr(HttpServletRequest request) {    
	    String ip = request.getHeader("x-forwarded-for");    
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	        ip = request.getHeader("Proxy-Client-IP");    
	    }    
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	        ip = request.getHeader("WL-Proxy-Client-IP");    
	    }    
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	        ip = request.getRemoteAddr();    
	    }    
	    return ip;    
	}
	
	public static String getNullString(String str){
		if(str == null || str.equals("null"))
			return "";
		return str;
	}
	
	//截取附件路径
	public static String getNewStr(String str){
		if(str == null || str.equals("null")){
			return "";
		}
		if(str.lastIndexOf("_")<0 || str.lastIndexOf("/") < 0){
			return str;
		}
		str = str.substring(str.lastIndexOf("/")+1,str.lastIndexOf("_"))+str.substring(str.lastIndexOf("."));
		return str;
	}
	
	/**
	 * @Description: FTP文件下载文件名称截取（取最后一“-”之后的）
	 * @param str
	 * @return    
	 * @throws: 
	 * @author: yzh
	 * @date: 2013-12-9 下午05:00:06
	 */
	public static String getFtpUploadFileNameStr(String fileNameDB){
		if(fileNameDB == null || fileNameDB.equals("null")){
			return "";
		}
		if(fileNameDB.lastIndexOf("_")<0 || fileNameDB.lastIndexOf("/") < 0){
			return fileNameDB;
		}
		String[] strs = fileNameDB.split("_");
		return fileNameDB.substring(0, fileNameDB.lastIndexOf("/")) + "/" + strs[strs.length - 1];
	}
	
	//字符串是否是数字
	public static boolean isNum(String str) {
		boolean isNum = str.matches("[0-9]+"); 
		return isNum;
	}
	
	
	
	/**
	 * @Description: 获取当前浏览器版本信息
	 * @param userAgent
	 * @author: yzh
	 * @date: 2014-9-16 下午04:52:07
	 */
	public static int getIeVersion(String userAgent){
		int fullVersion1 = -1;
		try {
			StringTokenizer st = new StringTokenizer(userAgent, ";");
			if(st.hasMoreTokens()){
				st.nextToken();
				if(st.hasMoreTokens()){
					// 得到用户的浏览器名
					String userBrowser = st.nextToken(); // MSIE 10.0 MSIE 9.0
					StringTokenizer _st = new StringTokenizer(userBrowser, " ");
					if(_st.hasMoreTokens()){
						_st.nextToken();
						if(_st.hasMoreTokens()){
							String _vstr = _st.nextToken();
							if (isNumeric(_vstr) && isNotNullOrEmpty(_vstr)) {
								Float f = (Float.parseFloat(_vstr));
								fullVersion1 = f.intValue();
							}
							//System.out.println("浏览器版本：" + fullVersion);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return fullVersion1;
	}
	
	/**
	 * @Description: 判断是否是数字
	 * @param str
	 * @author: yzh
	 * @date: 2014-9-17 下午03:49:11
	 */
	public static boolean isNumeric(String str){ 
		for (int i = str.length();--i>=0;){ 
			if (!Character.isDigit(str.charAt(i))){ 
				if(!".".equals(str.charAt(i)+"")){
					return false; 
				}
			} 
		} 
		return true;
	}
	
	
}
