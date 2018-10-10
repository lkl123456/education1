package com.adks.dubbo.api.data.common;

import java.io.Serializable;
import java.util.Date;

/**
* <p>Title: 友情链接 BEAN</p>
* @author: lkl 
* @date: 2017-03-07 
 */
public class Adks_friendly_link implements Serializable{		
	
	private static final long serialVersionUID = 1L;
	
	private Integer fdLinkId;//友情链接id
	private String fdLinkName;//链接名称
	private String fdLinkHref;//链接地址
	private Integer orgId;//学校ID
	private String orgName;//学校名称
	private String orgCode;//分类隶属于学校的结构层次
	private Integer creatorId;//创建人ID
	private String creatorName;//创建人真实姓名
	private Date createTime;//创建时间
	private String createTimeStr;//创建时间的string类型
	
	public Integer getFdLinkId() {
		return fdLinkId;
	}
	public void setFdLinkId(Integer fdLinkId) {
		this.fdLinkId = fdLinkId;
	}
	public String getFdLinkName() {
		return fdLinkName;
	}
	public void setFdLinkName(String fdLinkName) {
		this.fdLinkName = fdLinkName;
	}
	public String getFdLinkHref() {
		return fdLinkHref;
	}
	public void setFdLinkHref(String fdLinkHref) {
		this.fdLinkHref = fdLinkHref;
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
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
	
	
}	
