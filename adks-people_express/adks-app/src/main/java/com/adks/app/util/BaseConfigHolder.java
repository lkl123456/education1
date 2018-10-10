package com.adks.app.util;

import java.util.Properties;

import com.adks.commons.util.ComUtil;
import com.adks.commons.util.Constants;

/**
 * 
 * @Description:
 * @author: lxh
 * @date: 2014-8-14 下午05:33:08
 */
public class BaseConfigHolder {
	
	private static BaseFrameworkConfig config ;
	public static String CONFIG_FILE_NAME = "ossConfig";
	public static String RELOAD = "base.config.reload";
	public static String systemName;
	public static String systemkeywords;
	public static String systemdescription;
	public static String cacheControlContent;
	public static String cacheControlMaxAge;
	public static String imgServer;
	public static boolean singleRedis;
	public static boolean clusterRedis;
	public static String pcVideoServer;
	public static String mobileServer;
	public static String mmsIp;
	public static String mmsPort;
	public static String mmsName;
	public static String gradeSchooleWorkPath;  // 存放班级论文、学员论文附件路径
	public static String userHeadPicpathkPath ; // 存放个人头像路径
	public static boolean isMonitor;
	public static String monitorTime;
	public static String videoServer;
	
	public static boolean heartStart;
	public static Integer heartTime;
	public static boolean isMail;
	public static String mailFrom;
	public static String[] mailTo;
	
	public static Integer lockCount;  // 登录失败多少次将被锁住
	public static Integer lockTimeOut;  // 在多长时间内登录失败超过限制将被锁住（单位：小时）
	public static String key;
	
	//以下是短信
	public static String sendsmsurl;
	public static String banlanceurl;
	public static String account;
	public static String password;
	
	public static void intiSystemValues(){
		/*getAppName();
		getAppKeyWord();
		getAppDescription();
		getCacheControlContent();
		getCacheControlMaxAge();*/
		getImgServer();
		/*getSingleRedis();
		getClusterRedis();
		getGradeSchooleWorkPath();
		getUserHeadPicpathkPath();
		getMmsIp();
		getMmsPort();
		getMmsName();
		getPcVideoServer();
		getMobileServer();
		getIsMonitor();
		getMonitorTime();
		getHeartStart();
		getHeartTime();
		getIsMail();
		getMailTo();
		getMailFrom();
		getLockCount();
		getLockTimeOut();
		getSendsmsurl();
		getBanlanceurl();
		getAccount();
		getPassword();
		getKey();
		getVideoServer();*/
	}
	
	public static String getSendsmsurl(){
		if(sendsmsurl == null){
			sendsmsurl = new String(getProperties().getProperty("base.sendsmsurl"));
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		return sendsmsurl;
	}
	public static String getBanlanceurl(){
		if(banlanceurl == null){
			banlanceurl = new String(getProperties().getProperty("base.banlanceurl"));
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		return banlanceurl;
	}
	public static String getAccount(){
		if(account == null){
			account = new String(getProperties().getProperty("base.account"));
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		return account;
	}
	public static String getPassword(){
		if(password == null){
			password = new String(getProperties().getProperty("base.password"));
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		return password;
	}
	
	public static Properties getProperties(){
		if(config == null)
			config = (BaseFrameworkConfig)SpringContextHolder.getBean(CONFIG_FILE_NAME);
		return config.getConfig();
	}
	
	public static boolean getFrameworkConfigReload(){
		
		String reload = getProperties().getProperty(RELOAD);
		if(reload == null)
			return false;
		
		return Boolean.parseBoolean(reload);
	}
	 
	public static String getAppName(){
		if(systemName == null){
				systemName = new String(getProperties().getProperty("app.name"));
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		return systemName;
	}
	
	public static String getAppKeyWord(){
		if(systemkeywords == null){
				systemkeywords = new String(getProperties().getProperty("app.keywords"));
		}
		return systemkeywords;
	}
	
	public static String getAppDescription(){
		if(systemdescription == null){
				systemdescription = new String(getProperties().getProperty("app.description"));
		}
		return systemdescription;
	}
	
	public static String getCacheControlContent() {
		if(cacheControlContent == null){
			cacheControlContent = getProperties().getProperty(Constants.CACHE_CONTROL_CONTENT);
			return cacheControlContent;
		}
		return cacheControlContent;
	}
	
	public static String getCacheControlMaxAge() {
		if(cacheControlMaxAge == null){
			cacheControlMaxAge = getProperties().getProperty(Constants.CACHE_CONTROL_MAX_AGE);
			return cacheControlMaxAge;
		}
		return cacheControlMaxAge;
	}
	
	public static String getImgServer() {
		if(imgServer == null){
			imgServer = getProperties().getProperty("img.server");
			return imgServer==null?imgServer:imgServer.trim();
		}
		return imgServer;
	}
	

	/**
	 * @Description: redis服务 单个
	 * @author: lxh
	 * @date:
	 */
	public static Boolean getSingleRedis() {
		singleRedis = false;
		String singleRedisFlagStr = getProperties().getProperty("redis.single.flag");
		ObjectNull();
		if(ComUtil.isNullOrEmpty(singleRedisFlagStr)){
			singleRedis = false;
		}else if("1".equals(singleRedisFlagStr.trim())){
			singleRedis = true;
		}
		return singleRedis;
	}
	

	/**
	 * @Description: redis服务 集群
	 * @author: lxh
	 * @date:
	 */
	public static Boolean getClusterRedis() {
		clusterRedis = false;
		String clusterRedisFlagStr = getProperties().getProperty("redis.cluster.flag");
		ObjectNull();
		if(ComUtil.isNullOrEmpty(clusterRedisFlagStr)){
			clusterRedis = false;
		}else if("1".equals(clusterRedisFlagStr.trim())){
			clusterRedis = true;
		}
		return clusterRedis;
	}
	
	private static void ObjectNull(){
		if(getFrameworkConfigReload()){
			config = null;
		}
	}
	
	/** 班级作业上传
	 * @return
	 */
	public static String getGradeSchooleWorkPath(){
		if(gradeSchooleWorkPath == null){
			gradeSchooleWorkPath = new String(getProperties().getProperty("base.path.grade.schoolework"));
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		return gradeSchooleWorkPath;
	}
	/** 个人头像上传
	 * @return
	 */
	public static String getUserHeadPicpathkPath(){
		if(userHeadPicpathkPath == null){
			userHeadPicpathkPath = new String(getProperties().getProperty("base.path.user.photo"));
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		return userHeadPicpathkPath;
	}
	
	
	public static String getPcVideoServer()
	  {
	    if (pcVideoServer == null) {
	    	pcVideoServer = new String(getProperties().getProperty("base.video.pcVideoServer"));
	    }

	    return pcVideoServer;
	  }

	 public static String getMobileServer()
	  {
	    if (mobileServer == null) {
	    	mobileServer = new String(getProperties().getProperty("base.video.mobileServer"));
	    }

	    return mobileServer;
	  }
	
	
	public static String getMmsIp(){
		if(mmsIp == null){
			mmsIp = new String(getProperties().getProperty("base.mms.ip"));
		}
		return mmsIp;
	}
	
	public static String getMmsPort(){
		if(mmsPort == null){
			mmsPort = new String(getProperties().getProperty("base.mms.port"));
		}
		return mmsPort;
	}
	
	public static String getMmsName(){
		if(mmsName == null){
			mmsName = new String(getProperties().getProperty("base.mms.name"));
		}
		return mmsName;
	}
	
	/**
	 *@author: hqt
	 *@see: 配置视频播放 页面，是否需要登录？
	 *@date: 2014-8-29 下午03:25:55
	 */
	public static boolean getVideoStudyLogin(){
		
		String videoStudyLogin = getProperties().getProperty("base.course.videoStudyLogin");
		if(videoStudyLogin == null)
			return false;
		
		return Boolean.parseBoolean(videoStudyLogin);
		
	}
	
	public static Boolean getIsMonitor() {
		isMonitor = false;
		String isMonitorStr = getProperties().getProperty("base.course.isMonitor");

		if(ComUtil.isNotNullOrEmpty(isMonitorStr)){
			isMonitor=Boolean.parseBoolean(isMonitorStr);
		}
		return isMonitor;
	}
	
	public static String getMonitorTime(){
		if(monitorTime == null){
			monitorTime = new String(getProperties().getProperty("base.course.monitorTime"));
		}
		return monitorTime;
	}
	
	public static Integer getHeartTime(){
		if(heartTime == null){
			heartTime = Integer.parseInt(getProperties().getProperty("base.heartTime"));
		}
		return heartTime;
	}
	
	public static Boolean getHeartStart(){
		
		heartStart = false;
		
		String heartStartStr = getProperties().getProperty("base.heartStart");

		if(ComUtil.isNotNullOrEmpty(heartStartStr)){
			
			heartStart=Boolean.parseBoolean(heartStartStr);
		}
		
		return heartStart;
	}
	
	public static Boolean getIsMail(){
		
		isMail = false;
		
		String isMailStr = getProperties().getProperty("base.isMail");

		if(ComUtil.isNotNullOrEmpty(isMailStr)){
			
			isMail=Boolean.parseBoolean(isMailStr);
		}
		
		return isMail;
	}
	
	public static String[]  getMailTo(){
		
		String mailToStr = getProperties().getProperty("base.mailTo");
		
		if(ComUtil.isNotNullOrEmpty(mailToStr)){
			mailTo =mailToStr.split(";") ;
		}
		
		return mailTo;
	}
	
	public static String  getMailFrom(){
		
		if(ComUtil.isNullOrEmpty(mailFrom)){
			mailFrom =getProperties().getProperty("base.mailFrom");
		}
		
		return mailFrom;
	}
	//前台登录 用户名 密码 加密 key
	public static String  getKey(){
		
		if(ComUtil.isNullOrEmpty(key)){
			key =getProperties().getProperty("base.key");
		}
		
		return key;
	}
	
	public static Integer  getLockCount(){
		if(ComUtil.isNullOrEmpty(lockCount)){
			lockCount = 3; // 默认3次
			String temp = getProperties().getProperty("base.lock.lockCount");
			if(ComUtil.isNotNullOrEmpty(temp)){
				lockCount = Integer.parseInt(temp);
			}
		}
		return lockCount;
	}
	
	public static Integer  getLockTimeOut(){
		if(ComUtil.isNullOrEmpty(lockTimeOut)){
			lockTimeOut = 24; // 默认24小时
			String temp = getProperties().getProperty("base.lock.lockTimeOut");
			if(ComUtil.isNotNullOrEmpty(temp)){
				lockTimeOut = Integer.parseInt(temp);
			}
		}
		return lockTimeOut;
	}
	
	public static String getVideoServer(){
		if(videoServer == null){
			videoServer = new String(getProperties().getProperty("base.video.mobileServer"));
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		return videoServer;
	}
	
}
