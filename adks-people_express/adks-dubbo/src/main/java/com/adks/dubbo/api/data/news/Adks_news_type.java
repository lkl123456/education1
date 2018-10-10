package com.adks.dubbo.api.data.news;

import java.util.List;

//数据库中没有，仅仅为了界面显示
public class Adks_news_type {
	private Integer id;
	private String name;
	private String text;
	private Integer orgId;
	private String orgName;
	private String orgCode;
	private Integer newsSortType;
	
	public Integer getNewsSortType() {
		return newsSortType;
	}
	public void setNewsSortType(Integer newsSortType) {
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
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	private List<Adks_news_type> children;
	
	public List<Adks_news_type> getChildren() {
		return children;
	}
	public void setChildren(List<Adks_news_type> children) {
		this.children = children;
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
	
}
