package com.adks.dubbo.api.data.course;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* <p>Title: 学员观看  BEAN</p>
* @author: lkl 
* @date: 2017-03-07 
 */
public class Adks_course_user implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer courseUserId;//课程观看id
	private Integer userId;//用户id
	private String userName;
	private Integer courseId;//课程id
	private Integer orgId;
	private String courseName;//课程名称
	private String courseCode;//课程编码
	private Integer courseCwType;//课程类型
	private String courseImg;//课程图片
	private Integer authorId;//讲师id
	private Integer studyCourseTime;//学课时长
	private Integer courseDuration;//课程时长（秒）
	private String courseDurationLong;
	private Integer studyAllTimeLong;//学习课程总时长
	private String studyAllTime;//学习总时长
	private Date lastStudyDate;
	private Integer lastPosition;//上一次学习的时间点
	private Integer isOver;//学习状态，1已学完，2没有
	private Integer gradeId;
	private String gradeName;
	private Integer isCollection;//是否收藏，1收藏，2没有
	private Date xkDate;
	private String grades;
	
	//显示使用
	private String lastPositionStr;
	private Integer gradeCourseId;
	private Integer gcState;
	private String courseTimeLong;
	private Integer credit;
	private String authorName;
	private Integer isSynchronization;  //课程是否需要同步   需要：1  不需要：2
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public Integer getGradeCourseId() {
		return gradeCourseId;
	}
	public void setGradeCourseId(Integer gradeCourseId) {
		this.gradeCourseId = gradeCourseId;
	}
	public Integer getGcState() {
		return gcState;
	}
	public void setGcState(Integer gcState) {
		this.gcState = gcState;
	}
	public String getCourseTimeLong() {
		return courseTimeLong;
	}
	public void setCourseTimeLong(String courseTimeLong) {
		this.courseTimeLong = courseTimeLong;
	}
	public Integer getCredit() {
		return credit;
	}
	public void setCredit(Integer credit) {
		this.credit = credit;
	}
	public Date getXkDate() {
		return xkDate;
	}
	public void setXkDate(Date xkDate) {
		this.xkDate = xkDate;
	}
	public Integer getCourseUserId() {
		return courseUserId;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public void setCourseUserId(Integer courseUserId) {
		this.courseUserId = courseUserId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getLastPositionStr() {
		return lastPositionStr;
	}
	public void setLastPositionStr(String lastPositionStr) {
		this.lastPositionStr = lastPositionStr;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public Integer getCourseCwType() {
		return courseCwType;
	}
	public void setCourseCwType(Integer courseCwType) {
		this.courseCwType = courseCwType;
	}
	public String getCourseImg() {
		return courseImg;
	}
	public void setCourseImg(String courseImg) {
		this.courseImg = courseImg;
	}
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	public Integer getStudyCourseTime() {
		return studyCourseTime;
	}
	public void setStudyCourseTime(Integer studyCourseTime) {
		this.studyCourseTime = studyCourseTime;
	}
	public Integer getCourseDuration() {
		return courseDuration;
	}
	public void setCourseDuration(Integer courseDuration) {
		this.courseDuration = courseDuration;
	}
	public String getCourseDurationLong() {
		return courseDurationLong;
	}
	public void setCourseDurationLong(String courseDurationLong) {
		this.courseDurationLong = courseDurationLong;
	}
	public Integer getStudyAllTimeLong() {
		return studyAllTimeLong;
	}
	public void setStudyAllTimeLong(Integer studyAllTimeLong) {
		this.studyAllTimeLong = studyAllTimeLong;
	}
	public String getStudyAllTime() {
		return studyAllTime;
	}
	public void setStudyAllTime(String studyAllTime) {
		this.studyAllTime = studyAllTime;
	}
	public Date getLastStudyDate() {
		return lastStudyDate;
	}
	public void setLastStudyDate(Date lastStudyDate) {
		this.lastStudyDate = lastStudyDate;
	}
	public Integer getLastPosition() {
		return lastPosition;
	}
	public void setLastPosition(Integer lastPosition) {
		this.lastPosition = lastPosition;
	}
	public Integer getIsOver() {
		return isOver;
	}
	public void setIsOver(Integer isOver) {
		this.isOver = isOver;
	}
	public Integer getGradeId() {
		return gradeId;
	}
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public Integer getIsCollection() {
		return isCollection;
	}
	public void setIsCollection(Integer isCollection) {
		this.isCollection = isCollection;
	}
	public String getGrades() {
		return grades;
	}
	public void setGrades(String grades) {
		this.grades = grades;
	}
	public Integer getIsSynchronization() {
		return isSynchronization;
	}
	public void setIsSynchronization(Integer isSynchronization) {
		this.isSynchronization = isSynchronization;
	}
	
}
