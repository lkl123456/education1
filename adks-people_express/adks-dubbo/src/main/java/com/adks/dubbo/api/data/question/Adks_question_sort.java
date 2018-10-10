package com.adks.dubbo.api.data.question;

import java.io.Serializable;
import java.util.Date;

/**
 * 试题分类表（题库表）
 * @author Administrator
 *
 */
public class Adks_question_sort implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer qtSortID;//分类的id
	private String qsSortName;//分类的名称
	private String qsSortCode;//分类的code
	private Integer qsNum;//分类试题量
	private Integer parentQsSortID;//父id
	private String parentQsSortName;//父名称
	private Integer orgId;//学校Id
	private String orgName;//学校名
	private Integer creatorId;//创建人ID
	private String creatorName;//创建人真实姓名
	private Date createTime;//创建时间
	
	public Integer getQtSortID() {
		return qtSortID;
	}
	public void setQtSortID(Integer qtSortID) {
		this.qtSortID = qtSortID;
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
	public Integer getQsNum() {
		return qsNum;
	}
	public void setQsNum(Integer qsNum) {
		this.qsNum = qsNum;
	}
	public Integer getParentQsSortID() {
		return parentQsSortID;
	}
	public void setParentQsSortID(Integer parentQsSortID) {
		this.parentQsSortID = parentQsSortID;
	}
	public String getParentQsSortName() {
		return parentQsSortName;
	}
	public void setParentQsSortName(String parentQsSortName) {
		this.parentQsSortName = parentQsSortName;
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
