package com.sd.spcrm.webconfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sd.spcrm.utils.LogRecordAdks;

@SuppressWarnings("serial")
public class WebConfigSpcrm  extends HttpServlet {
	
	/**
	 * 流媒体服务器地址
	 */
	public static String videoServer;
	
	/**
	 * 课件加密参数
	 */
	public static String studyKey;
	
	/**
	 * 编码配置
	 */
	public static String System_Encoding;
	
	/**
	 * Web端保存进度Url
	 */
	public static String WebUrl;
	
	/**
	 * 终Map集合数量、当size为设置数量时，自动推向web端
	 */
	public static int FinalMapSize;
	
	/**
	 * 单例的进程
	 */
	public static ProgressManagerSpcrm progressManager;
	
	/**
	 * 心跳所属平台--用于web查询打开课件的范围-web端
	 */
	public static String HeartArea;
	
	/**
	 * 当前课件服务器地址
	 */
	public static String HeartUrl;
	
	
	/**
	 * web端检测心跳的Url
	 */
	public static String WebHeartUrl;
	
	/**
	 *用打开课件超时时间
	 */
	public static int TimeLong;
	
	/**
	 * 是否开启心跳
	 */
	public static boolean heartStart;
	
	/**
	 * web主网站地址
	 */
	public static String WebPath;
	
	/**
	 * 通用服务地址
	 */
	public static String commonServiceURL_;
	/**
	 * 图片地址
	 */
	public static String userHeadPicURL_;
	
	/**
	 * 评论所属类型（eg： 课程，活动...）
	 */
	public static int commentType_;
	
	public WebConfigSpcrm() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
	public void init(ServletConfig config) throws ServletException {
		
		LogRecordAdks.info("SPCRM开始读取初始化参数");
		
		InputStream inp = WebConfigSpcrm.class.getResourceAsStream("/systemGlobals.properties");
		
		Properties prop = new Properties();
		
		try {
			
			prop.load(inp);
			
			videoServer = prop.getProperty("init.videoServer").trim();
			
			studyKey = prop.getProperty("init.studyKey").trim();
			
			System_Encoding = prop.getProperty("init.Encoding").trim();
			
			WebUrl = prop.getProperty("init.WebUrl").trim();
			
			FinalMapSize = Integer.parseInt(prop.getProperty("init.FinalMapSize").trim());
			
			HeartArea = prop.getProperty("init.HeartArea").trim();
			
			HeartUrl = prop.getProperty("init.HeartUrl").trim();
			
			WebHeartUrl = prop.getProperty("init.WebHeartUrl").trim();
			
			heartStart = Boolean.parseBoolean(prop.getProperty("init.heartStart").trim());
			
			TimeLong =  Integer.parseInt(prop.getProperty("init.TimeLong").trim());
			
			WebPath = prop.getProperty("init.WebPath").trim();
			
			commonServiceURL_ = prop.getProperty("init.commonServiceURL_").trim();
			
			userHeadPicURL_ = prop.getProperty("init.userHeadPicURL_").trim();
			
			commentType_ = Integer.parseInt(prop.getProperty("init.commentType_").trim());
			
			if (System_Encoding == "") {
				
				System_Encoding = "UTF-8";
				
			}
			//初始化实例---
			progressManager = ProgressManagerSpcrm.getProgessProgessInstance();
			
			LogRecordAdks.info("SPCRM读取初始化参数成功");
			
		} catch (Exception e) {
			
			LogRecordAdks.error("SPCRM读取初始化参数失败信息如下："+e);

			e.printStackTrace();
		}
		
	}
}
