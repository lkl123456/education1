/**
 * 
 */
package com.adks.dubbo.api.data.news;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 *
 */
public class Adks_news_user implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer newsId;// 新闻id
	private Integer userId;// 用户id
	private Integer gradeId;// 班级id
	private Integer isRead;// 是否已查看
	private Date createDate;// 创建时间

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the newsId
	 */
	public Integer getNewsId() {
		return newsId;
	}

	/**
	 * @param newsId
	 *            the newsId to set
	 */
	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the gradeId
	 */
	public Integer getGradeId() {
		return gradeId;
	}

	/**
	 * @param gradeId
	 *            the gradeId to set
	 */
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	/**
	 * @return the isRead
	 */
	public Integer getIsRead() {
		return isRead;
	}

	/**
	 * @param isRead
	 *            the isRead to set
	 */
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
