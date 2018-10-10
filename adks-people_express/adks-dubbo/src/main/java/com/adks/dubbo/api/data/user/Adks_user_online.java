package com.adks.dubbo.api.data.user;

import java.io.Serializable;
import java.util.Date;

public class Adks_user_online implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer userOnlineId;
	private Integer userId;
	private String userName;
	private Date lastCheckDate;
	private Integer orgId;
	private String orgName;
	private String orgCode;
	private String sessionId;
	private String ipAddress;   //登录用户IP地址
	private String region;    //登录用户所在地区
	private String operator;   //登录用户使用运营商
	

	public Integer getUserOnlineId() {
		return userOnlineId;
	}

	public void setUserOnlineId(Integer userOnlineId) {
		this.userOnlineId = userOnlineId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getLastCheckDate() {
		return lastCheckDate;
	}

	public void setLastCheckDate(Date lastCheckDate) {
		this.lastCheckDate = lastCheckDate;
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

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}
