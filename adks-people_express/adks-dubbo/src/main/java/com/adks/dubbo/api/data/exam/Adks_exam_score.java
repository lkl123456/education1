package com.adks.dubbo.api.data.exam;

import java.io.Serializable;
import java.util.Date;

public class Adks_exam_score implements Serializable {

	private Integer examScoreId;
	private Integer score;
	private Integer useTime;
	private Date submitDate;
	private Integer examCounts;
	private Integer isCorrent;
	private Integer correntId;
	private String correntName;
	private Date correntDate;
	private Integer examId;
	private String examName;
	private Integer gradeId;
	private String gradeName;
	private Integer userId;
	private String userName;
	private Integer orgId;
	private String orgName;
	private String orgCode;
	
	//以下是显示使用
	private String useTimeStr;
	private Integer examDate;
	
	
	public Integer getExamDate() {
		return examDate;
	}
	public void setExamDate(Integer examDate) {
		this.examDate = examDate;
	}
	public String getUseTimeStr() {
		return useTimeStr;
	}
	public void setUseTimeStr(String useTimeStr) {
		this.useTimeStr = useTimeStr;
	}
	public Integer getExamScoreId() {
		return examScoreId;
	}
	public void setExamScoreId(Integer examScoreId) {
		this.examScoreId = examScoreId;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getUseTime() {
		return useTime;
	}
	public void setUseTime(Integer useTime) {
		this.useTime = useTime;
	}
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	public Integer getExamCounts() {
		return examCounts;
	}
	public void setExamCounts(Integer examCounts) {
		this.examCounts = examCounts;
	}
	public Integer getIsCorrent() {
		return isCorrent;
	}
	public void setIsCorrent(Integer isCorrent) {
		this.isCorrent = isCorrent;
	}
	public Integer getCorrentId() {
		return correntId;
	}
	public void setCorrentId(Integer correntId) {
		this.correntId = correntId;
	}
	public String getCorrentName() {
		return correntName;
	}
	public void setCorrentName(String correntName) {
		this.correntName = correntName;
	}
	public Date getCorrentDate() {
		return correntDate;
	}
	public void setCorrentDate(Date correntDate) {
		this.correntDate = correntDate;
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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
}
