package com.adks.dubbo.api.data.grade;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * ClassName Adks_grade_user
 * @Description:班级学员
 * @author xrl
 * @Date 2017年5月9日
 */
public class Adks_grade_user implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer gradeUserId;   //
	private Integer isGraduate;    //是否毕业(1.代表结业；2.代表未结业)
	private Double requiredPeriod;   //已获必修课程学时
	private Double optionalPeriod;   //已获选修课程学时
	private Integer isCertificate;    //是否颁发证书(1.已颁发；2.未颁发)
	private Integer gradeId;     //班级id
	private String gradeName;   //班级名称
	private Integer userId;    //用户id
	private String userName;   //用户真实姓名
	private Integer orgId;    //学校ID
	private String orgName;    //学校名称
	private Integer creatorId;   //创建人ID
	private String creatorName;   //创建人真实姓名
	private Date createTime;     //创建时间
	
	//以下仅仅是显示
	private Float sumUserStudytime;
	private Integer userRanking;
	
	public Integer getUserRanking() {
		return userRanking;
	}
	public void setUserRanking(Integer userRanking) {
		this.userRanking = userRanking;
	}
	public Float getSumUserStudytime() {
		return sumUserStudytime;
	}
	public void setSumUserStudytime(Float sumUserStudytime) {
		this.sumUserStudytime = sumUserStudytime;
	}
	public Integer getGradeUserId() {
		return gradeUserId;
	}
	public void setGradeUserId(Integer gradeUserId) {
		this.gradeUserId = gradeUserId;
	}
	public Integer getIsGraduate() {
		return isGraduate;
	}
	public void setIsGraduate(Integer isGraduate) {
		this.isGraduate = isGraduate;
	}
	public Double getRequiredPeriod() {
		return requiredPeriod;
	}
	public void setRequiredPeriod(Double requiredPeriod) {
		this.requiredPeriod = requiredPeriod;
	}
	public Double getOptionalPeriod() {
		return optionalPeriod;
	}
	public void setOptionalPeriod(Double optionalPeriod) {
		this.optionalPeriod = optionalPeriod;
	}
	public Integer getIsCertificate() {
		return isCertificate;
	}
	public void setIsCertificate(Integer isCertificate) {
		this.isCertificate = isCertificate;
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
