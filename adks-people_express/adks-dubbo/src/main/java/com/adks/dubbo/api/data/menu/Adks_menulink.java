package com.adks.dubbo.api.data.menu;

import java.io.Serializable;
import java.util.Date;

public class Adks_menulink implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer menuLinkId;
	private String menuLinkName;
	private String linkUrl;
	private String mLinkDisplay;
	private Integer orderNum;
	private Integer menuId;
	private Integer creatorId;
	private String creatorName;
	private Date createTime;
	
	//以下是用于显示
	private Integer menuid;
	private String menuname;
	private String icon;
	private String url;
	
	public Integer getMenuid() {
		return menuid;
	}
	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getMenuLinkId() {
		return menuLinkId;
	}
	public void setMenuLinkId(Integer menuLinkId) {
		this.menuLinkId = menuLinkId;
	}
	public String getMenuLinkName() {
		return menuLinkName;
	}
	public void setMenuLinkName(String menuLinkName) {
		this.menuLinkName = menuLinkName;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getmLinkDisplay() {
		return mLinkDisplay;
	}
	public void setmLinkDisplay(String mLinkDisplay) {
		this.mLinkDisplay = mLinkDisplay;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
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
