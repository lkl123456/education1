package com.adks.dubbo.api.data.course;

import java.io.Serializable;
import java.util.Date;

/**
* <p>Title: 课程信息  BEAN</p>
* @author: lkl 
* @date: 2017-03-07 
 */
public class Adks_course implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer courseId;//课程id
	private String courseName;//课程名称
	private String courseCode;//课程code
	private Integer courseType;//1.三分屏；2.单视频；3.微课；4.多媒体课程	课程类型 
	private String coursePic;//课程图片地址
	private String courseDes;//课程描述
	private Integer authorId;//讲师id
	private String authorName;//讲师名称
	private Integer courseSortId;//课程分类的id
	private String courseSortName;//课程分类名称
	private String courseSortCode;//课程分类code
	private Integer courseDuration;//课程时长（秒）
	private String courseTimeLong;//课程时长（String）
	private Integer courseStatus;//1.激活；2.冻结；	课程状态
	private Integer courseCollectNum;//课程收藏量
	private Integer courseClickNum;//课程点击量
	private Integer orgId;//学习ID
	private String orgCode;
	private String orgName;//学校名称
	private Integer isAudit;//1.审核通过；2.审核未通过；3.待审核	审核通过
	private Integer courseBelong;//1.公开；2.专属；3.私密	是否公开（私有只有班级能看，专属只有平台能看，私密后台能看可添加班级）
	private Integer isRecommend;//1.推荐；2不推荐；	是否推荐
	private String courseStream;//码流
	private Integer creatorId;//创建人ID
	private String creatorName;//创建人真实姓名
	private Date createTime;//创建时间
	private Integer courseStudiedLong;//课程播放总时长（以秒计算）
	
	//add by xrl 2017-05-06
	private String credit;  //学时，页面显示用
	
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public Integer getCourseType() {
		return courseType;
	}
	public void setCourseType(Integer courseType) {
		this.courseType = courseType;
	}
	public String getCoursePic() {
		return coursePic;
	}
	public void setCoursePic(String coursePic) {
		this.coursePic = coursePic;
	}
	public String getCourseDes() {
		return courseDes;
	}
	public void setCourseDes(String courseDes) {
		this.courseDes = courseDes;
	}
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
	public Integer getCourseDuration() {
		return courseDuration;
	}
	public void setCourseDuration(Integer courseDuration) {
		this.courseDuration = courseDuration;
	}
	public String getCourseTimeLong() {
		return courseTimeLong;
	}
	public void setCourseTimeLong(String courseTimeLong) {
		this.courseTimeLong = courseTimeLong;
	}
	public Integer getCourseStatus() {
		return courseStatus;
	}
	public void setCourseStatus(Integer courseStatus) {
		this.courseStatus = courseStatus;
	}
	public Integer getCourseCollectNum() {
		return courseCollectNum;
	}
	public void setCourseCollectNum(Integer courseCollectNum) {
		this.courseCollectNum = courseCollectNum;
	}
	public Integer getCourseClickNum() {
		return courseClickNum;
	}
	public void setCourseClickNum(Integer courseClickNum) {
		this.courseClickNum = courseClickNum;
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
	public Integer getIsAudit() {
		return isAudit;
	}
	public void setIsAudit(Integer isAudit) {
		this.isAudit = isAudit;
	}
	public Integer getCourseBelong() {
		return courseBelong;
	}
	public void setCourseBelong(Integer courseBelong) {
		this.courseBelong = courseBelong;
	}
	public Integer getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}
	public String getCourseStream() {
		return courseStream;
	}
	public void setCourseStream(String courseStream) {
		this.courseStream = courseStream;
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
	public Integer getCourseStudiedLong() {
		return courseStudiedLong;
	}
	public void setCourseStudiedLong(Integer courseStudiedLong) {
		this.courseStudiedLong = courseStudiedLong;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
}
