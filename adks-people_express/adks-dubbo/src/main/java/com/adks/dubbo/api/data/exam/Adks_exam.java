package com.adks.dubbo.api.data.exam;

import java.io.Serializable;
import java.util.Date;

/**
 * 考试表（用于统一考试时使用）
 * @author Administrator
 *
 */
public class Adks_exam implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer examId;//考试ID
	private String examName;//考试名称
	private String examDesc;//考试描述
	private Integer examDate;//考试时长
	private Date startDate;//开始时间
	private Date endDate;//结束时间
	private Integer scoreSum;//总分
	private Integer passScore;//及格分数
	private Integer examTimes;//考试次数
	private Integer orgId;//学校ID
	private String orgName;//学校名
	private Integer creatorId;//创建人ID
	private String creatorName;//创建人真实姓名
	private Date createTime;//创建时间
	
	//以下是显示使用
	private Integer gradeId;
	private String gradeName;
	private String startDate_str;   //开始时间字符串
	private String endDate_str;     //结束时间字符串
	private String examDateStr;
	
	
	public String getExamDateStr() {
		return examDateStr;
	}
	public void setExamDateStr(String examDateStr) {
		this.examDateStr = examDateStr;
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
	
	public String getStartDate_str() {
		return startDate_str;
	}
	public void setStartDate_str(String startDate_str) {
		this.startDate_str = startDate_str;
	}
	public String getEndDate_str() {
		return endDate_str;
	}
	public void setEndDate_str(String endDate_str) {
		this.endDate_str = endDate_str;
	}
	public Integer getExamId() {
		return examId;
	}
	public void setExamId(Integer examId) {
		this.examId = examId;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getExamDesc() {
		return examDesc;
	}
	public void setExamDesc(String examDesc) {
		this.examDesc = examDesc;
	}
	public Integer getExamDate() {
		return examDate;
	}
	public void setExamDate(Integer examDate) {
		this.examDate = examDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getScoreSum() {
		return scoreSum;
	}
	public void setScoreSum(Integer scoreSum) {
		this.scoreSum = scoreSum;
	}
	public Integer getPassScore() {
		return passScore;
	}
	public void setPassScore(Integer passScore) {
		this.passScore = passScore;
	}
	public Integer getExamTimes() {
		return examTimes;
	}
	public void setExamTimes(Integer examTimes) {
		this.examTimes = examTimes;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
