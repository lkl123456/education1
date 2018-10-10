package com.adks.dubbo.api.data.role;

import java.io.Serializable;
import java.util.Date;

public class Adks_role implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer roleId;
	private String roleName;
	private String displayRef;
	private String roleDes;
	private Integer isGlob;
	private Integer creatorId;
	private String creatorName;
	private Date createTime;
	
	//以下是显示使用
	private Integer id;
	private String name;
	private String text; 
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDisplayRef() {
		return displayRef;
	}
	public void setDisplayRef(String displayRef) {
		this.displayRef = displayRef;
	}
	public String getRoleDes() {
		return roleDes;
	}
	public void setRoleDes(String roleDes) {
		this.roleDes = roleDes;
	}
	public Integer getIsGlob() {
		return isGlob;
	}
	public void setIsGlob(Integer isGlob) {
		this.isGlob = isGlob;
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
