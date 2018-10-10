package com.adks.dubbo.api.data.role;

import java.io.Serializable;

public class Adks_role_menu_link implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer rmId;
	private Integer menuLinkId;
	private Integer roleId;
	public Integer getRmId() {
		return rmId;
	}
	public void setRmId(Integer rmId) {
		this.rmId = rmId;
	}
	public Integer getMenuLinkId() {
		return menuLinkId;
	}
	public void setMenuLinkId(Integer menuLinkId) {
		this.menuLinkId = menuLinkId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
