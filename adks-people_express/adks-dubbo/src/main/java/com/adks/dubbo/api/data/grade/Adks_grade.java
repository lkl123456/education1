package com.adks.dubbo.api.data.grade;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Title: 班级 BEAN
 * </p>
 * 
 * @author: lkl
 * @date: 2017-03-07
 */
public class Adks_grade implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer gradeId;// 班级id
	private String gradeName;// 班级名称
	private String gradeDesc;// 班级描述
	private String gradeTarget;// 培训目标
	private Integer gradeState;// 1.发布；2.未发布 班级状态
	private Date startDate;// 班级开始时间
	private Date endDate;// 班级结束时间
	private String gradeImg;// 班级图片
	private Integer requiredPeriod;// 必修学时
	private Integer optionalPeriod;// 选修学时
	private Integer workRequire;// 1.选择；2.未选择 作业条件（是否有作业）
	private Integer examRequire;// 1.选择；2.未选择 考试条件（是否有考试）
	private String certificateImg;// 证书图片地址
	private String eleSeal;// 电子章地址
	private Integer isRegisit;// 1.允许报名；2.不允许报名 是否允许前台报名
	private Integer headTeacherId;// 班主任id
	private String headTeacherName;// 班主任名称
	private Integer userNum;// 班级学员数量
	private Integer requiredNum;// 班级必修课数量
	private Integer optionalNum;// 班级选修课数量
	private Integer orgId;// 学校id
	private String orgName;// 学校名称
	private String orgCode; // 学校Code
	private Integer creatorId;// 创建人id
	private String creatorName;// 创建人真实姓名
	private Date createTime;// 创建时间
	private String graduationDesc;// 毕业条件

	private String createTime_str; // 创建时间字符串
	private String startDate_str; // 开始时间字符串
	private String endDate_str; // 结束时间字符串

	private Integer isDatePast;

	// 一下为了easyui显示用
	private Integer id;
	private String name;
	private String state;
	private String text;

	/**
	 * @return the graduationDesc
	 */
	public String getGraduationDesc() {
		return graduationDesc;
	}

	/**
	 * @param graduationDesc
	 *            the graduationDesc to set
	 */
	public void setGraduationDesc(String graduationDesc) {
		this.graduationDesc = graduationDesc;
	}

	public Integer getIsDatePast() {
		return isDatePast;
	}

	public void setIsDatePast(Integer isDatePast) {
		this.isDatePast = isDatePast;
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

	public String getGradeDesc() {
		return gradeDesc;
	}

	public void setGradeDesc(String gradeDesc) {
		this.gradeDesc = gradeDesc;
	}

	public String getGradeTarget() {
		return gradeTarget;
	}

	public void setGradeTarget(String gradeTarget) {
		this.gradeTarget = gradeTarget;
	}

	public Integer getGradeState() {
		return gradeState;
	}

	public void setGradeState(Integer gradeState) {
		this.gradeState = gradeState;
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

	public String getGradeImg() {
		return gradeImg;
	}

	public void setGradeImg(String gradeImg) {
		this.gradeImg = gradeImg;
	}

	public Integer getRequiredPeriod() {
		return requiredPeriod;
	}

	public void setRequiredPeriod(Integer requiredPeriod) {
		this.requiredPeriod = requiredPeriod;
	}

	public Integer getOptionalPeriod() {
		return optionalPeriod;
	}

	public void setOptionalPeriod(Integer optionalPeriod) {
		this.optionalPeriod = optionalPeriod;
	}

	public Integer getWorkRequire() {
		return workRequire;
	}

	public void setWorkRequire(Integer workRequire) {
		this.workRequire = workRequire;
	}

	public Integer getExamRequire() {
		return examRequire;
	}

	public void setExamRequire(Integer examRequire) {
		this.examRequire = examRequire;
	}

	public String getCertificateImg() {
		return certificateImg;
	}

	public void setCertificateImg(String certificateImg) {
		this.certificateImg = certificateImg;
	}

	public String getEleSeal() {
		return eleSeal;
	}

	public void setEleSeal(String eleSeal) {
		this.eleSeal = eleSeal;
	}

	public Integer getIsRegisit() {
		return isRegisit;
	}

	public void setIsRegisit(Integer isRegisit) {
		this.isRegisit = isRegisit;
	}

	public Integer getHeadTeacherId() {
		return headTeacherId;
	}

	public void setHeadTeacherId(Integer headTeacherId) {
		this.headTeacherId = headTeacherId;
	}

	public String getHeadTeacherName() {
		return headTeacherName;
	}

	public void setHeadTeacherName(String headTeacherName) {
		this.headTeacherName = headTeacherName;
	}

	public Integer getUserNum() {
		return userNum;
	}

	public void setUserNum(Integer userNum) {
		this.userNum = userNum;
	}

	public Integer getRequiredNum() {
		return requiredNum;
	}

	public void setRequiredNum(Integer requiredNum) {
		this.requiredNum = requiredNum;
	}

	public Integer getOptionalNum() {
		return optionalNum;
	}

	public void setOptionalNum(Integer optionalNum) {
		this.optionalNum = optionalNum;
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

	public String getCreateTime_str() {
		return createTime_str;
	}

	public void setCreateTime_str(String createTime_str) {
		this.createTime_str = createTime_str;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

}
