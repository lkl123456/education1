package com.adks.dubbo.api.data.news;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Title: 新闻信息 BEAN
 * </p>
 * 
 * @author: lkl
 * @date: 2017-03-07
 */
public class Adks_news implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer newsId;// 新闻id
	private String newsTitle;// 新闻名称
	private byte[] newsContent;// 新闻内容（blob）
	private String newsFocusPic;// 新闻焦点图	private Integer newsType;// 新闻类型（文本型、链接型）
	private String newsLink;// 新闻链接
	private Integer newsSortId;// 新闻分类Id
	private String newsSortName;// 新闻分类name
	private String newsHtmlAdress;// 新闻html路径
	private Integer orgId;// orgId
	private String orgName;
	private String orgCode;
	private Integer newsSortType;
	private Integer gradeId;// 班级id（表示是否是班级公告）
	private Integer creatorId;// 创建人ID
	private String creatorName;// 创建人真实姓名
	private Date createTime;// 创建时间
	private String createTimeStr;// 创建时间的string类型
	private Integer sysType;// 新闻类型（0/新闻 1/班级公告 2/系统公告）
	private Integer newsType;
	private Integer newsBelong;  //是否公开（公开全平台可看，专属只有平台能看） 1.公开；2.专属  

	private String content;// 新闻内容 str

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the sysType
	 */
	public Integer getSysType() {
		return sysType;
	}

	/**
	 * @param sysType
	 *            the sysType to set
	 */
	public void setSysType(Integer sysType) {
		this.sysType = sysType;
	}

	// add by xrl 2017-04-26
	private String gradeName; // 班级名-页面显示用

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

	public Integer getNewsSortType() {
		return newsSortType;
	}

	public void setNewsSortType(Integer newsSortType) {
		this.newsSortType = newsSortType;
	}

	public Integer getNewsId() {
		return newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public byte[] getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(byte[] newsContent) {
		this.newsContent = newsContent;
	}

	public String getNewsFocusPic() {
		return newsFocusPic;
	}

	public void setNewsFocusPic(String newsFocusPic) {
		this.newsFocusPic = newsFocusPic;
	}

	public Integer getNewsType() {
		return newsType;
	}

	public void setNewsType(Integer newsType) {
		this.newsType = newsType;
	}

	public String getNewsLink() {
		return newsLink;
	}

	public void setNewsLink(String newsLink) {
		this.newsLink = newsLink;
	}

	public Integer getNewsSortId() {
		return newsSortId;
	}

	public void setNewsSortId(Integer newsSortId) {
		this.newsSortId = newsSortId;
	}

	public String getNewsSortName() {
		return newsSortName;
	}

	public void setNewsSortName(String newsSortName) {
		this.newsSortName = newsSortName;
	}

	public String getNewsHtmlAdress() {
		return newsHtmlAdress;
	}

	public void setNewsHtmlAdress(String newsHtmlAdress) {
		this.newsHtmlAdress = newsHtmlAdress;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
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

	public Integer getNewsBelong() {
		return newsBelong;
	}

	public void setNewsBelong(Integer newsBelong) {
		this.newsBelong = newsBelong;
	}

}
