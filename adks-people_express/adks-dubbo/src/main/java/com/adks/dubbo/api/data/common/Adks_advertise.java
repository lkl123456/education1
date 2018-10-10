package com.adks.dubbo.api.data.common;

import java.io.Serializable;
import java.util.Date;

/**
* <p>Title: 广告位 BEAN</p>
* @author: lkl 
* @date: 2017-03-07 
 */
public class Adks_advertise implements Serializable{
			
	private static final long serialVersionUID = 1L;
	
	private Integer adId;//广告ID
	private Integer orgId;//机构ID
	private String orgName;//机构名称
	private String orgCode;//分类隶属于学校的结构层次
	private String adImgPath;//广告图片地址
	private Integer adLocation;//1.飘窗；2.中部；3.左侧竖联；4.右侧竖联；5.左下角浮动；	广告图位置
	private String adHref;//广告链接
	private Integer status;//是否启用（1.启用  2.禁止）
	private Integer creatorId;//创建人ID
	private String creatorName;//创建人真实姓名
	private Date createTime;//创建时间
	private String createTimeStr;//创建时间的string类型
	
	public Integer getAdId() {
		return adId;
	}
	public void setAdId(Integer adId) {
		this.adId = adId;
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
	public String getAdImgPath() {
		return adImgPath;
	}
	public void setAdImgPath(String adImgPath) {
		this.adImgPath = adImgPath;
	}
	public Integer getAdLocation() {
		return adLocation;
	}
	public void setAdLocation(Integer adLocation) {
		this.adLocation = adLocation;
	}
	public String getAdHref() {
		return adHref;
	}
	public void setAdHref(String adHref) {
		this.adHref = adHref;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
	
	
}
