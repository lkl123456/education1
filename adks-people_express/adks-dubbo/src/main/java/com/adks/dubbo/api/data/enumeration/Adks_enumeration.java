package com.adks.dubbo.api.data.enumeration;

import java.io.Serializable;
import java.util.Date;

public class Adks_enumeration implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer enId;
	private String enName;
	private String enDisplay;
	private String enDesc;
	private Integer enumerationType;
	private Integer creatorId;
	private String creatorName;
	private Date createTime;
	
	//以下仅仅显示使用
	private String text;
	
	public Integer getEnId() {
		return enId;
	}
	public void setEnId(Integer enId) {
		this.enId = enId;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getEnDisplay() {
		return enDisplay;
	}
	public void setEnDisplay(String enDisplay) {
		this.enDisplay = enDisplay;
	}
	public String getEnDesc() {
		return enDesc;
	}
	public void setEnDesc(String enDesc) {
		this.enDesc = enDesc;
	}
	public Integer getEnumerationType() {
		return enumerationType;
	}
	public void setEnumerationType(Integer enumerationType) {
		this.enumerationType = enumerationType;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
