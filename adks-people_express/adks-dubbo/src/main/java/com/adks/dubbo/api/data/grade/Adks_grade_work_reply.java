package com.adks.dubbo.api.data.grade;

import java.io.Serializable;
import java.util.Date;

/**
* <p>Title: 班级作业回答  BEAN</p>
* @author: lkl 
* @date: 2017-03-07 
 */
public class Adks_grade_work_reply implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer gradeWorkReplyId;//班级作业ID
	private byte[] workAnswer;//答案
	private String submitFilePath;//附件路径（学员）
	private Integer workScore;//批改成绩
	private Date submitDate;//提交时间
	private Date correctDate;//批改时间
	private Integer isCorrent;//1.已批改；2.未批改；是否批改
	private Integer workId;//作业id
	private String workTitle;//作业标题
	private Integer studentId;//学员id
	private String studentName;//学员名称
	private Integer correctId;//批改人id
	private String correntName;//批改人名称
	
	//页面显示用
	private String workAnswerStr;  //答案
	private Integer gradeWorkId;
	
	public Integer getGradeWorkId() {
		return gradeWorkId;
	}
	public void setGradeWorkId(Integer gradeWorkId) {
		this.gradeWorkId = gradeWorkId;
	}
	public Integer getGradeWorkReplyId() {
		return gradeWorkReplyId;
	}
	public void setGradeWorkReplyId(Integer gradeWorkReplyId) {
		this.gradeWorkReplyId = gradeWorkReplyId;
	}
	public byte[] getWorkAnswer() {
		return workAnswer;
	}
	public void setWorkAnswer(byte[] workAnswer) {
		this.workAnswer = workAnswer;
	}
	public String getSubmitFilePath() {
		return submitFilePath;
	}
	public void setSubmitFilePath(String submitFilePath) {
		this.submitFilePath = submitFilePath;
	}
	public Integer getWorkScore() {
		return workScore;
	}
	public void setWorkScore(Integer workScore) {
		this.workScore = workScore;
	}
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	public Date getCorrectDate() {
		return correctDate;
	}
	public void setCorrectDate(Date correctDate) {
		this.correctDate = correctDate;
	}
	public Integer getIsCorrent() {
		return isCorrent;
	}
	public void setIsCorrent(Integer isCorrent) {
		this.isCorrent = isCorrent;
	}
	public Integer getWorkId() {
		return workId;
	}
	public void setWorkId(Integer workId) {
		this.workId = workId;
	}
	public String getWorkTitle() {
		return workTitle;
	}
	public void setWorkTitle(String workTitle) {
		this.workTitle = workTitle;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Integer getCorrectId() {
		return correctId;
	}
	public void setCorrectId(Integer correctId) {
		this.correctId = correctId;
	}
	public String getCorrentName() {
		return correntName;
	}
	public void setCorrentName(String correntName) {
		this.correntName = correntName;
	}
	public String getWorkAnswerStr() {
		return workAnswerStr;
	}
	public void setWorkAnswerStr(String workAnswerStr) {
		this.workAnswerStr = workAnswerStr;
	}
}
