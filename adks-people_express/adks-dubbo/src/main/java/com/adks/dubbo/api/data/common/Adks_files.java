package com.adks.dubbo.api.data.common;

import java.io.Serializable;
import java.util.Date;

/**
* <p>Title: 教参BEAN</p>
* @author: lkl 
* @date: 2017-03-07 
 */
public class Adks_files implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	private Integer filesId;//教参id
	private String filesName;//教参名称
	private Integer filesType;//1.word;2.pdf;3.excel;4.ppt;5.zip;6.视频	教参类型（带默认图片）
	private String filesDes;//教参简介（不带图片）
	private String filesUrl;//下载地址
	private Integer gradeId;//班级ID
	private String gradeName;//班级名称
	private Integer orgId;//学校id
	private String orgName;//学校name
	private String orgCode;//分类隶属于学校的结构层次
	private Integer filesBelong;//1.公开；2.专属；3.私密	是否公开（私有只有班级能看，专属只有平台能看）
	private Integer creatorId;//创建人ID
	private String creatorName;//创建人真实姓名
	private Date createTime;//创建时间
	private Integer downloadNum;//下载量
	private String createTimeStr;//创建时间的string类型
	
	public Integer getFilesId() {
		return filesId;
	}
	public void setFilesId(Integer filesId) {
		this.filesId = filesId;
	}
	public String getFilesName() {
		return filesName;
	}
	public void setFilesName(String filesName) {
		this.filesName = filesName;
	}
	public Integer getFilesType() {
		return filesType;
	}
	public void setFilesType(Integer filesType) {
		this.filesType = filesType;
	}
	public String getFilesDes() {
		return filesDes;
	}
	public void setFilesDes(String filesDes) {
		this.filesDes = filesDes;
	}
	public String getFilesUrl() {
		return filesUrl;
	}
	public void setFilesUrl(String filesUrl) {
		this.filesUrl = filesUrl;
	}
	public Integer getGradeId() {
		return gradeId;
	}
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
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
	public Integer getFilesBelong() {
		return filesBelong;
	}
	public void setFilesBelong(Integer filesBelong) {
		this.filesBelong = filesBelong;
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
	public Integer getDownloadNum() {
		return downloadNum;
	}
	public void setDownloadNum(Integer downloadNum) {
		this.downloadNum = downloadNum;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
	
}
