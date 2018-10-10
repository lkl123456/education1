package com.adks.dubbo.api.data.course;

import java.io.Serializable;

public class CourseJson implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 课程信息对象
	 */
	private String CourseId;//课程ID
	
	private String CourseCode;//课程编号
	
	private String UserId;//用户ID
	
	private String SessionId;//SessionID
	
	private String GreadId;//班级ID
	
	private String Location;//课程时间点位置--location
	
	private String Sessiontime;//观看时长 sessiontime  多长时间
	
	private String StudyKey;//url动态加密参数
	
	private String ScormOrVideo;//Scorm或单视频
	
	private String Systime;//从web端传过来的打开播放窗口的时间毫秒数
	
	private long LastSysTime;//最后观看时间毫秒数
	
	private String Cname;//课程名称
	
	private String heartTimeLong;//心跳时间

	private String Duration;//视频总时长
	
	private String VideoRP;//视频分辨率 256_450_720_1080

	private String IsNormal;//是否正常
	
	private static CourseJson courseJson = null;
	
	public CourseJson() {}  
	
	
	public static synchronized  CourseJson getCourseJson() {  
		if (courseJson == null) {
	    
			courseJson = new CourseJson();
	     }
        return courseJson;  
    }
	
	
	public String getCourseId() {
		return CourseId;
	}

	public void setCourseId(String courseId) {
		CourseId = courseId;
	}
	
	public String getCourseCode() {
		return CourseCode;
	}

	public void setCourseCode(String courseCode) {
		CourseCode = courseCode;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getSessionId() {
		return SessionId;
	}

	public void setSessionId(String sessionId) {
		SessionId = sessionId;
	}

	public String getGreadId() {
		return GreadId;
	}

	public void setGreadId(String greadId) {
		GreadId = greadId;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public String getSessiontime() {
		return Sessiontime;
	}

	public void setSessiontime(String sessiontime) {
		Sessiontime = sessiontime;
	}

	public String getStudyKey() {
		return StudyKey;
	}

	public void setStudyKey(String studyKey) {
		StudyKey = studyKey;
	}
	
	public String getScormOrVideo() {
		return ScormOrVideo;
	}

	public void setScormOrVideo(String scormOrVideo) {
		ScormOrVideo = scormOrVideo;
	}

	public String getSystime() {
		return Systime;
	}

	public void setSystime(String systime) {
		Systime = systime;
	}

	public long getLastSysTime() {
		return LastSysTime;
	}

	public void setLastSysTime(long lastSysTime) {
		LastSysTime = lastSysTime;
	}


	public String getCname() {
		return Cname;
	}


	public void setCname(String cname) {
		Cname = cname;
	}


	public String getHeartTimeLong() {
		return heartTimeLong;
	}


	public void setHeartTimeLong(String heartTimeLong) {
		this.heartTimeLong = heartTimeLong;
	}


	public String getDuration() {
		return Duration;
	}


	public void setDuration(String duration) {
		Duration = duration;
	}
	
	public void setVideoRP(String videoRP) {
		VideoRP = videoRP;
	}
	
	public String getVideoRP() {
		return VideoRP;
	}
	
	public void setIsNormal(String isNormal) {
		IsNormal = isNormal;
	}
	
	public String getIsNormal() {
		return IsNormal;
	}
	
}
