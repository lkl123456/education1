package com.adks.dubbo.commons;

import java.io.Serializable;
import java.util.List;

import com.adks.dubbo.api.data.author.Adks_author;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.api.data.news.Adks_news;

@SuppressWarnings("serial")
public class SearchResult implements Serializable {

	private String queryString;
	// 课程列表
	private List<Adks_course> course;
	// 新闻列表
	private List<Adks_news> news;
	// 专题列表
	private List<Adks_grade> grad;
	// 讲师列表
	private List<Adks_author> author;
	// 总记录数
	private long recordCount;
	// 总页数
	private long pageCount;
	// 当前页
	private long curPage;
	// 响应时间
	private long responseTime;

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}

	public long getCurPage() {
		return curPage;
	}

	public void setCurPage(long curPage) {
		this.curPage = curPage;
	}

	public List<Adks_course> getCourse() {
		return course;
	}

	public void setCourse(List<Adks_course> course) {
		this.course = course;
	}

	public List<Adks_news> getNews() {
		return news;
	}

	public void setNews(List<Adks_news> news) {
		this.news = news;
	}

	public List<Adks_grade> getGrad() {
		return grad;
	}

	public void setGrad(List<Adks_grade> grad) {
		this.grad = grad;
	}

	public List<Adks_author> getAuthor() {
		return author;
	}

	public void setAuthor(List<Adks_author> author) {
		this.author = author;
	}

}