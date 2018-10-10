package com.adks.dubbo.api.data.org;

import java.io.Serializable;

//机构配置信息类
public class Adks_org_config implements Serializable{

	private Integer orgConfigId;
	private Integer orgId; //机构id
	private String orgName;//机构名称
	private String orgUrl;//机构域名
	private String orgLogoPath;//LOGO图片地址
	private String orgDesc;//描述
	private String orgBanner;//首页上边banner图地址
	private String contactUser;//联系人姓名
	private String contactPhone;//联系人电话
	private String contactQQ;//联系人QQ
	private String contactMail;//联系人邮箱
	private String copyRight;//版权信息(页面底部，版权信息)
	public void setContactUser(String contactUser) {
		this.contactUser = contactUser;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public Integer getOrgConfigId() {
		return orgConfigId;
	}
	public void setOrgConfigId(Integer orgConfigId) {
		this.orgConfigId = orgConfigId;
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
	public String getOrgUrl() {
		return orgUrl;
	}
	public void setOrgUrl(String orgUrl) {
		this.orgUrl = orgUrl;
	}
	public String getOrgLogoPath() {
		return orgLogoPath;
	}
	public void setOrgLogoPath(String orgLogoPath) {
		this.orgLogoPath = orgLogoPath;
	}
	public String getOrgDesc() {
		return orgDesc;
	}
	public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}
	public String getOrgBanner() {
		return orgBanner;
	}
	public void setOrgBanner(String orgBanner) {
		this.orgBanner = orgBanner;
	}
	public String getContactUser() {
		return contactUser;
	}
	public void contactPhone(String contactUser) {
		this.contactUser = contactUser;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactQQ() {
		return contactQQ;
	}
	public void setContactQQ(String contactQQ) {
		this.contactQQ = contactQQ;
	}
	public String getContactMail() {
		return contactMail;
	}
	public void setContactMail(String contactMail) {
		this.contactMail = contactMail;
	}
	public String getCopyRight() {
		return copyRight;
	}
	public void setCopyRight(String copyRight) {
		this.copyRight = copyRight;
	}
	
	
}
