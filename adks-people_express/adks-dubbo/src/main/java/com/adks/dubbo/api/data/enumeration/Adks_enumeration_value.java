package com.adks.dubbo.api.data.enumeration;

import java.io.Serializable;
import java.util.Date;

public class Adks_enumeration_value implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer enValueId;
	private String valName;
	private String valDisplay;
	private String valDesc;
	private Integer enId;
	private Integer creatorId;
	private String creatorName;
	private Date createTime;
	
	//以下仅仅显示使用
	private Integer id;
	private String enName;
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	private String text;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getEnValueId() {
		return enValueId;
	}
	public void setEnValueId(Integer enValueId) {
		this.enValueId = enValueId;
	}
	public String getValName() {
		return valName;
	}
	public void setValName(String valName) {
		this.valName = valName;
	}
	public String getValDisplay() {
		return valDisplay;
	}
	public void setValDisplay(String valDisplay) {
		this.valDisplay = valDisplay;
	}
	public String getValDesc() {
		return valDesc;
	}
	public void setValDesc(String valDesc) {
		this.valDesc = valDesc;
	}
	public Integer getEnId() {
		return enId;
	}
	public void setEnId(Integer enId) {
		this.enId = enId;
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
