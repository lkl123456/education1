package com.adks.dubbo.api.data.zhiji;

import java.io.Serializable;
import java.util.List;

public class Adks_rank implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer rankId;
	private String rankName;
	private String rankCode;
	private Integer parentId;
	private String parentName;
	private Integer isdelete;//是否删除，1是2否
	private Integer orderNum;//排序字段
	private Integer zhijiId;//职级id，若不为空该条字段表示职务
	
	//以下仅仅是显示使用
	private Integer id;
	private String name;
	private String text;
	private List<Adks_rank> children;
	
	public Integer getZhijiId() {
		return zhijiId;
	}
	public void setZhijiId(Integer zhijiId) {
		this.zhijiId = zhijiId;
	}
	public List<Adks_rank> getChildren() {
		return children;
	}
	public void setChildren(List<Adks_rank> children) {
		this.children = children;
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getRankId() {
		return rankId;
	}
	public void setRankId(Integer rankId) {
		this.rankId = rankId;
	}
	public String getRankName() {
		return rankName;
	}
	public void setRankName(String rankName) {
		this.rankName = rankName;
	}
	public String getRankCode() {
		return rankCode;
	}
	public void setRankCode(String rankCode) {
		this.rankCode = rankCode;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public Integer getIsdelete() {
		return isdelete;
	}
	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
