package com.adks.dubbo.api.data.menu;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Adks_menu implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer menuId;
	private String menuName;
	private String menuDesc;
	private Integer orderNum;
	private Integer creatorId;
	private String creatorName;
	private Date createTime;
	
	//首页菜单显示用
	private String icon;
	private List<Adks_menulink> menus;
	private String menuname;
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public List<Adks_menulink> getMenus() {
		return menus;
	}
	public void setMenus(List<Adks_menulink> menus) {
		this.menus = menus;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuDesc() {
		return menuDesc;
	}
	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
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
