package com.adks.dubbo.api.data.grade;

import java.io.Serializable;
import java.util.Date;

/**
* <p>Title: 班级课程 BEAN</p>
* @author: lkl 
* @date: 2017-03-07 
 */
public class Adks_grade_course implements Serializable{
				
	private static final long serialVersionUID = 1L;
	
	private Integer gradeCourseId;//班级课程ID
	private Integer rankNum;//排序
	private Integer gcState;//1.必修；2.选修	选修必修
	private Double credit;//50代表0.5学时；100代表1学时；150代表1.5学时；200代表2学时；	学时设定
	private Integer gradeId;//班级id
	private String gradeName;//班级名称
	private Integer courseId;//课程id
	private String courseCode;
	private String courseName;//课程名称
	private Integer courseCatalogId;//课程分类id
	private String courseCatalogName;//课程分类名称
	private String courseCatalogCode;//课程分类code
	private Integer cwType;//1.三分屏；2.单视频；3.微课；4.多媒体课程	课件类型
	private Integer creatorId;//创建人ID
	private String creatorName;//创建人真实姓名
	private Date createTime;//创建时间
	
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public Integer getGradeCourseId() {
		return gradeCourseId;
	}
	public void setGradeCourseId(Integer gradeCourseId) {
		this.gradeCourseId = gradeCourseId;
	}
	public Integer getRankNum() {
		return rankNum;
	}
	public void setRankNum(Integer rankNum) {
		this.rankNum = rankNum;
	}
	public Integer getGcState() {
		return gcState;
	}
	public void setGcState(Integer gcState) {
		this.gcState = gcState;
	}
	public Double getCredit() {
		return credit;
	}
	public void setCredit(Double credit) {
		this.credit = credit;
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
	public Integer getCourseCatalogId() {
		return courseCatalogId;
	}
	public void setCourseCatalogId(Integer courseCatalogId) {
		this.courseCatalogId = courseCatalogId;
	}
	public String getCourseCatalogName() {
		return courseCatalogName;
	}
	public void setCourseCatalogName(String courseCatalogName) {
		this.courseCatalogName = courseCatalogName;
	}
	public String getCourseCatalogCode() {
		return courseCatalogCode;
	}
	public void setCourseCatalogCode(String courseCatalogCode) {
		this.courseCatalogCode = courseCatalogCode;
	}
	public Integer getCwType() {
		return cwType;
	}
	public void setCwType(Integer cwType) {
		this.cwType = cwType;
	}
	public Integer getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
	
}
