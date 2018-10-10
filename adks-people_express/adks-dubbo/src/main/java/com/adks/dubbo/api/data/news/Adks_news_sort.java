package com.adks.dubbo.api.data.news;

import java.io.Serializable;
import java.util.Date;

/**
* <p>Title: 新闻信息分类 BEAN</p>
* @author: lkl 
* @date: 2017-03-07 
 */
public class Adks_news_sort implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer newsSortId;//分类id
	private String newsSortName;//分类名称
	private Integer newsSortLocation;//分类排序
	private String newsSortType;//新闻分类类型（首字母缩写）
	private Integer orgId;//分类隶属于哪一个学校（学校id）
	private String orgName;//分类隶属于哪一个学校
	private String orgCode;//分类隶属于学校的结构层次
	private Integer creatorId;//创建人ID
	private String creatorName;//创建人真实姓名
	private Date createTime;//创建时间
	
	//以下仅仅是显示使用
	private String createTimeStr;//创建时间的string类型
	private Integer id;
	private String name;
	
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
	public Integer getNewsSortId() {
		return newsSortId;
	}
	public void setNewsSortId(Integer newsSortId) {
		this.newsSortId = newsSortId;
	}
	public String getNewsSortName() {
		return newsSortName;
	}
	public void setNewsSortName(String newsSortName) {
		this.newsSortName = newsSortName;
	}
	public Integer getNewsSortLocation() {
		return newsSortLocation;
	}
	public void setNewsSortLocation(Integer newsSortLocation) {
		this.newsSortLocation = newsSortLocation;
	}
	public String getNewsSortType() {
		return newsSortType;
	}
	public void setNewsSortType(String newsSortType) {
		this.newsSortType = newsSortType;
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
