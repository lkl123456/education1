package com.adks.dubbo.api.data.grade;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * ClassName Adks_grade_exam
 * @Description：班级考试
 * @author xrl
 * @Date 2017年5月9日
 */
public class Adks_grade_exam  implements Serializable{

	private Integer gradeExamId;
	private Integer examId;
	private Integer gradeId;
	private String gradeName;
	
	//以下仅仅是显示使用
	private Date startDate;
	private Date endDate;
	private String examName;
	private Integer scoreSum;
	private Integer passScore;
	private Integer examTimes;
	private Integer examDate;
	private Integer score;
	private Integer useTime;
	private Integer examCounts;
	
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
	public Integer getExamCounts() {
		return examCounts;
	}
	public void setExamCounts(Integer examCounts) {
		this.examCounts = examCounts;
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
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
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
	public Integer getExamDate() {
		return examDate;
	}
	public void setExamDate(Integer examDate) {
		this.examDate = examDate;
	}
	public Integer getGradeExamId() {
		return gradeExamId;
	}
	public void setGradeExamId(Integer gradeExamId) {
		this.gradeExamId = gradeExamId;
	}
	public Integer getExamId() {
		return examId;
	}
	public void setExamId(Integer examId) {
		this.examId = examId;
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
}
