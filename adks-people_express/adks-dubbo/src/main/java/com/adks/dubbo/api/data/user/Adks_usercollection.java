package com.adks.dubbo.api.data.user;

import java.io.Serializable;
import java.util.Date;

public class Adks_usercollection implements Serializable{

	private Integer userConId;
	private Integer courseId;
	private String courseName;
	private String courseCode;
	private Integer userId;
	private Integer authorId;
	private String authorName;
	private Date createDate;
	private Date publishDate;
	private Integer courseDuration;
	private Integer courseType;
	private String courseImg;
	private String courseSortName;
	private String courseTimeLong;
	
	//显示使用
	private Integer studyTimeLong;
	private String lastPositionStr;
	
	public String getLastPositionStr() {
		return lastPositionStr;
	}
	public void setLastPositionStr(String lastPositionStr) {
		this.lastPositionStr = lastPositionStr;
	}
	public Integer getStudyTimeLong() {
		return studyTimeLong;
	}
	public void setStudyTimeLong(Integer studyTimeLong) {
		this.studyTimeLong = studyTimeLong;
	}
	public String getCourseSortName() {
		return courseSortName;
	}
	public void setCourseSortName(String courseSortName) {
		this.courseSortName = courseSortName;
	}
	public String getCourseTimeLong() {
		return courseTimeLong;
	}
	public void setCourseTimeLong(String courseTimeLong) {
		this.courseTimeLong = courseTimeLong;
	}
	public Integer getUserConId() {
		return userConId;
	}
	public void setUserConId(Integer userConId) {
		this.userConId = userConId;
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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public Integer getCourseDuration() {
		return courseDuration;
	}
	public void setCourseDuration(Integer courseDuration) {
		this.courseDuration = courseDuration;
	}
	public Integer getCourseType() {
		return courseType;
	}
	public void setCourseType(Integer courseType) {
		this.courseType = courseType;
	}
	public String getCourseImg() {
		return courseImg;
	}
	public void setCourseImg(String courseImg) {
		this.courseImg = courseImg;
	}
	
	
}
