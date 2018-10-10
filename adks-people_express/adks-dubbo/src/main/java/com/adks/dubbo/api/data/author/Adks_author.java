package com.adks.dubbo.api.data.author;

import java.io.Serializable;
import java.util.Date;

/**
* 作者
 */
public class Adks_author implements Serializable{
			
	private static final long serialVersionUID = 1L;
	
	private Integer authorId;//作者id
	private String authorName;//作者名称
	private String authorDes;//讲师简介
	private Integer authorSex;//性别，1男2女
	private String authorPhoto;//讲师图片
	private String authorFirstLetter;//首字母，后台总动匹配
	private Integer orgId;//机构Id
	private String orgName;//机构名称
	private String orgCode;//机构编码
	private Integer creatorId;//创建人ID
	private String creatorName;//创建人真实姓名
	private Date createTime;//创建时间
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getAuthorDes() {
		return authorDes;
	}
	public void setAuthorDes(String authorDes) {
		this.authorDes = authorDes;
	}
	public Integer getAuthorSex() {
		return authorSex;
	}
	public void setAuthorSex(Integer authorSex) {
		this.authorSex = authorSex;
	}
	public String getAuthorPhoto() {
		return authorPhoto;
	}
	public void setAuthorPhoto(String authorPhoto) {
		this.authorPhoto = authorPhoto;
	}
	public String getAuthorFirstLetter() {
		return authorFirstLetter;
	}
	public void setAuthorFirstLetter(String authorFirstLetter) {
		this.authorFirstLetter = authorFirstLetter;
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
	
}
