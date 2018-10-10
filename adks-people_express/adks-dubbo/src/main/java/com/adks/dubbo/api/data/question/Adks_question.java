package com.adks.dubbo.api.data.question;

import java.io.Serializable;
import java.util.Date;

/**
 * 试题信息表
 * @author Administrator
 *
 */
public class Adks_question implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer questionId;//试题id
	private String questionName;//试题名称
	private Integer questionType;//试题类型（1.单选；2.多选；3.判断；4.填空；5.问答）
	private Integer questionValue;//试题分值
	private String optionA;//第一个选项的内容
	private String optionB;//第二个选项的内容
	private String optionC;//第三个选项的内容
	private String optionD;//第四个选项的内容
	private String optionE;//第五个选项的内容
	private String optionF;//第六个选项的内容
	private String optionG;//第七个选项的内容
	private String optionH;//第八个选项的内容
	private String anwsers;//答案
	private Integer qtSortId;//分类的id
	private String qsSortName;//分类的名称
	private String qsSortCode;//分类的code
	private Integer courseId;//课程id
	private String courseName;//课程名称
	private Integer orgId;//学校ID
	private Integer creatorId;//创建人ID
	private String creatorName;//创建人真实姓名
	private Date createTime;//创建时间
	
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public String getQuestionName() {
		return questionName;
	}
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}
	public Integer getQuestionType() {
		return questionType;
	}
	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}
	public Integer getQuestionValue() {
		return questionValue;
	}
	public void setQuestionValue(Integer questionValue) {
		this.questionValue = questionValue;
	}
	public String getOptionA() {
		return optionA;
	}
	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}
	public String getOptionB() {
		return optionB;
	}
	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}
	public String getOptionC() {
		return optionC;
	}
	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}
	public String getOptionD() {
		return optionD;
	}
	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}
	public String getOptionE() {
		return optionE;
	}
	public void setOptionE(String optionE) {
		this.optionE = optionE;
	}
	public String getOptionF() {
		return optionF;
	}
	public void setOptionF(String optionF) {
		this.optionF = optionF;
	}
	public String getOptionG() {
		return optionG;
	}
	public void setOptionG(String optionG) {
		this.optionG = optionG;
	}
	public String getOptionH() {
		return optionH;
	}
	public void setOptionH(String optionH) {
		this.optionH = optionH;
	}
	public String getAnwsers() {
		return anwsers;
	}
	public void setAnwsers(String anwsers) {
		this.anwsers = anwsers;
	}
	public Integer getQtSortId() {
		return qtSortId;
	}
	public void setQtSortId(Integer qtSortId) {
		this.qtSortId = qtSortId;
	}
	public String getQsSortName() {
		return qsSortName;
	}
	public void setQsSortName(String qsSortName) {
		this.qsSortName = qsSortName;
	}
	public String getQsSortCode() {
		return qsSortCode;
	}
	public void setQsSortCode(String qsSortCode) {
		this.qsSortCode = qsSortCode;
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
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
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
