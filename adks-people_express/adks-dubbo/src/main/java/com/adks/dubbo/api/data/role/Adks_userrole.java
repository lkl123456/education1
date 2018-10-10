package com.adks.dubbo.api.data.role;

import java.io.Serializable;

public class Adks_userrole implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer urid;
	private Integer userId;
	private Integer roleId;
	public Integer getUrid() {
		return urid;
	}
	public void setUrid(Integer urid) {
		this.urid = urid;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
