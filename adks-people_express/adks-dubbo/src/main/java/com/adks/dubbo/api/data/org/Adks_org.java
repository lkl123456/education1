package com.adks.dubbo.api.data.org;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* <p>Title: 作者  BEAN</p>
* @author: lkl 
* @date: 2017-03-07 
 */
public class Adks_org implements Serializable,Comparable<Adks_org>{
	
	private static final long serialVersionUID = 1L;
	
	private Integer orgId;//机构id
	private String orgName;//机构名称
	private String orgCode;//机构代码
	private Integer parentId;
	private String parentName;
	
	private Integer creatorId;//创建人ID
	private String creatorName;//创建人真实姓名
	private Date createtime;//创建时间
	private Integer usernum;
	private Integer orgstudylong;
	
	//一下为了easyui显示用
	private Integer id;
	private String name;
	private String state;
	private String text;
	private boolean isParent;//ztree使用判断是否是父节点
	private Float avgXs;  //机构平均学时
	
	public boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}
	public Float getAvgXs() {
		return avgXs;
	}
	public void setAvgXs(Float avgXs) {
		this.avgXs = avgXs;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	private List<Adks_org> children;
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
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
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
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Integer getUsernum() {
		return usernum;
	}
	public void setUsernum(Integer usernum) {
		this.usernum = usernum;
	}
	public Integer getOrgstudylong() {
		return orgstudylong;
	}
	public void setOrgstudylong(Integer orgstudylong) {
		this.orgstudylong = orgstudylong;
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
	public List<Adks_org> getChildren() {
		return children;
	}
	public void setChildren(List<Adks_org> children) {
		this.children = children;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int compareTo(Adks_org o) {
		return o.getAvgXs().compareTo(this.getAvgXs());
	}
	
	
}
