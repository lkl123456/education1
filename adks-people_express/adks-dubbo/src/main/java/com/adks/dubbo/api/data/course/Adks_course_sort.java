package com.adks.dubbo.api.data.course;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* <p>Title: 课程分类  BEAN</p>
* @author: lkl 
* @date: 2017-03-07 
 */
public class Adks_course_sort implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer courseSortId;//课程分类id
	private String courseSortName;//分类名称
	private String courseSortCode;//分类code
	private String courseSortImgpath;//图片（只有一级分类有）
	private Integer courseParentId;//父id
	private String courseParentName;//父名称
	private Integer courseNum;//课程量
	private Integer orgId;//学校ID
	private String orgCode;
	private String orgName;//学校名称
	private Integer creatorId;//创建人ID
	private String creatorName;//创建人真实姓名
	private Date createTime;//创建时间
	
	//以下是显示使用
	private Integer id;
	private String name;
	private String text;
	private boolean isParent;//ztree使用判断是否是父节点
	
	//dtree是否是父节点
	private Integer pId;
	
	public Integer getPId() {
		return pId;
	}
	public void setPId(Integer pId) {
		this.pId = pId;
	}
	public boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}
	private List<Adks_course_sort> children;
	
	public List<Adks_course_sort> getChildren() {
		return children;
	}
	public void setChildren(List<Adks_course_sort> children) {
		this.children = children;
	}
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
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public Integer getCourseSortId() {
		return courseSortId;
	}
	public void setCourseSortId(Integer courseSortId) {
		this.courseSortId = courseSortId;
	}
	public String getCourseSortName() {
		return courseSortName;
	}
	public void setCourseSortName(String courseSortName) {
		this.courseSortName = courseSortName;
	}
	public String getCourseSortCode() {
		return courseSortCode;
	}
	public void setCourseSortCode(String courseSortCode) {
		this.courseSortCode = courseSortCode;
	}
	public String getCourseSortImgpath() {
		return courseSortImgpath;
	}
	public void setCourseSortImgpath(String courseSortImgpath) {
		this.courseSortImgpath = courseSortImgpath;
	}
	public Integer getCourseParentId() {
		return courseParentId;
	}
	public void setCourseParentId(Integer courseParentId) {
		this.courseParentId = courseParentId;
	}
	public String getCourseParentName() {
		return courseParentName;
	}
	public void setCourseParentName(String courseParentName) {
		this.courseParentName = courseParentName;
	}
	public Integer getCourseNum() {
		return courseNum;
	}
	public void setCourseNum(Integer courseNum) {
		this.courseNum = courseNum;
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
