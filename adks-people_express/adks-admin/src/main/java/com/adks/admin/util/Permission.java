package com.adks.admin.util;

/**
 * 用户权限定义
 * @author panpanxu
 *
 */
public enum Permission {
	AdminView("ADMIN_VIEW", 0, "查看管理员帐号"),
	
	OrgInfoView("ORGINFO_VIEW",1,"系统菜单"),
	OrgTemplateView("ORGTEMPLATE_VIEW",2,"权限管理"),
	AdminListView("ADMINLIST_VIEW",3,"资源管理"),
	ChangePwdView("CHANGEPWD_VIEW",4,"考试管理"),
	OrgListView("ORGLIST_VIEW",5,"班级管理"),
	UserListView("USERLIST_VIEW",6,"发布管理"),
	CourseListView("COURSELIST_VIEW",7,"统计管理"),
	GradeListView("GRADELIST_VIEW",8,"菜单管理"),
	GradeUserStudyView("GRADEUSERSTUDY_VIEW",9,"枚举管理");
	
	/**
	 * 权限名称
	 */
	private String name;
	
	/**
	 * 权限对应的值
	 */
	private int value;
	
	/**
	 * 权限描述
	 */
	private String description;
	
	private Permission(String name, int value, String description) {
		this.name = name;
		this.value = value;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
}
