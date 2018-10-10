package com.adks.web.util.ipSeeker;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import com.adks.commons.util.ComUtil;
import com.adks.commons.util.PropertiesFactory;
/**
 * 
 * ClassName IpAddressAndIspUtil
 * @Description
 * @author xrl
 * @Date 2017年6月27日
 */
public class IpAddressAndIspUtil {
	
	public static String returnString="";
	
	/**
	 * 
	 * @Title getIpAddr
	 * @Description：获取ID地址
	 * @author xrl
	 * @Date 2017年6月27日
	 * @param request
	 * @return
	 */
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
	
	/**
	 * 
	 * @Title getRegionByIp
	 * @Description：根据IP地址获取所在地区
	 * @author xrl
	 * @Date 2017年6月27日
	 * @param ipaddress
	 * @return
	 */
	public static String getRegionByIp(String ipaddress,HttpServletRequest request){
		
		try {
			String path = request.getSession().getServletContext().getRealPath("/");
			String ipdataPath = PropertiesFactory.getProperty("adksDbClient.properties","ip.data");
			IPSeeker ipseeker = new IPSeeker(new File(path+ipdataPath));
			IPEntity ipentity = new IPEntity();  
			SplitAddress splitaddress = new SplitAddress();  
			splitaddress.SplitAddressAction(ipaddress, ipseeker, ipentity); //切分获得多级地址  
			if(ComUtil.isNotNullOrEmpty(ipaddress)){
				if (ipaddress.equals("127.0.0.1")) {
					returnString = "内部地址";
				}else {
					returnString = ipseeker.getCountry(ipaddress);
				}
			}
			return returnString;
		} catch (Exception e) {
			e.printStackTrace();
		}  
        return null;
	}
	
	/**
	 * 
	 * @Title getOperatorByIp
	 * @Description：根据IP地址获取运营商
	 * @author xrl
	 * @Date 2017年6月27日
	 * @param ipaddress
	 * @return
	 */
	public static String getOperatorByIp(String ipaddress,HttpServletRequest request){
		
		try {
			String path = request.getSession().getServletContext().getRealPath("/");
			String ipdataPath = PropertiesFactory.getProperty("adksDbClient.properties","ip.data");
			IPSeeker ipseeker = new IPSeeker(new File(path+ipdataPath));
			IPEntity ipentity = new IPEntity();  
			SplitAddress splitaddress = new SplitAddress();  
			splitaddress.SplitAddressAction(ipaddress, ipseeker, ipentity); //切分获得多级地址  
			if(ComUtil.isNotNullOrEmpty(ipaddress)){
				if (ipaddress.equals("127.0.0.1")) {
					returnString = "未知";
				}else {
					returnString = ipseeker.getIsp(ipaddress);
				}
			}
			return returnString;
		} catch (Exception e) {
			e.printStackTrace();
		}  
        return null;
	}
}
