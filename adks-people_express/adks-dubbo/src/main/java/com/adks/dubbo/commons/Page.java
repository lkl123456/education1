package com.adks.dubbo.commons;

import java.io.Serializable;
import java.util.Map;

/**
 * EASY UI datagrid 查询分页结果
 * 
 * @param <T>
 *            - 任意类
 */
public class Page<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 当前页码
	 */
	private int currentPage;

	/**
	 * 每页记录数
	 */
	private int pageSize = 10;

	/**
	 * 总记录数
	 */
	private long total;

	/**
	 * 分页数据
	 */
	private T rows;

	/**
	 * 存放查询参数
	 */
	private Map<?, ?> map;

	/**
	 * 默认构造函数
	 */
	public Page() {
	}

	/**
	 * @param currentPage
	 *            - 当前页数
	 * @param pageSize
	 *            - 每页记录数
	 * @param totalCount
	 *            - 总记录数
	 * @param dataList
	 *            - 当前页数据
	 */
	public Page(int currentPage, int pageSize, long total, T rows) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.total = total;
		this.rows = rows;

		this.totalRows = Integer.parseInt(total + "");
		this.totalPages = this.totalRows / pageSize;
		int mod = this.totalRows % pageSize;
		if (mod > 0) {
			totalPages++;
		}
		// currentPage = 1;
		startRow = 0;
		this.totalRecords = this.totalRows;
		this.totalPage = this.totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public T getRows() {
		return rows;
	}

	public void setRows(T rows) {
		this.rows = rows;
	}

	public Map<?, ?> getMap() {
		return map;
	}

	public void setMap(Map<?, ?> map) {
		this.map = map;
	}

	/////////////////////// 分页标签功能添加字段 /////////////////////
	private int totalRows;// 记录总数
	private int totalPages = 0;// 总页数
	private int startRow = 0;// 当前页码开始记录
	/**
	 * 总记录数
	 */
	private int totalRecords = 0;
	/**
	 * 总页数
	 */
	private int totalPage = 0;
	/**
	 * 是第一页吗
	 */
	private boolean isFristPage = true;

	/**
	 * 是最后一页吗
	 */
	private boolean isLastPage = false;
	private String url;
	/**
	 * 当前页的起始记录数
	 */
	private int startRowPosition = 0;

	/**
	 * 当前页的最后记录数
	 */
	private int endRowPosition = 0;

	/**
	 * 最大限记录数
	 */
	private int maxRecords = 100;
	/* 用于终端分页 --------start */
	private int startIndex;
	private int startLimit;

	/**
	 * @return the startLimit
	 */
	public int getStartLimit() {
		return startLimit;
	}

	/**
	 * @param startLimit
	 *            the startLimit to set
	 */
	public void setStartLimit(int startLimit) {
		this.startLimit = startLimit;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
		currentPage = startIndex / pageSize + 1;
	}

	/* 用于终端分页 --------end */
	// 构造pager对象
	public Page(int _totalRows) {
		totalRows = _totalRows;
		totalPages = totalRows / pageSize;
		int mod = totalRows % pageSize;
		if (mod > 0) {
			totalPages++;
		}
		currentPage = 1;
		startRow = 0;
	}

	// 首页
	public void first() {
		currentPage = 1;
		startRow = 0;
	}

	// 上一页
	public void previous() {
		if (currentPage == 1) {
			return;
		}
		currentPage--;
		startRow = (currentPage - 1) * pageSize;
	}

	// 下一页
	public void next() {
		if (currentPage < totalPages) {
			currentPage++;
		}
		startRow = (currentPage - 1) * pageSize;
	}

	// 尾页
	public void last() {
		currentPage = totalPages;
		startRow = (currentPage - 1) * pageSize;
	}

	// 刷新pager对象
	public void refresh(int _currentPage) {
		currentPage = _currentPage;
		startRow = (currentPage - 1) * pageSize;
		if (totalPages > 0 && currentPage > totalPages) {
			last();
		}
	}

	public int getStartRow() {
		return startRow;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
		totalPages = totalRows / pageSize;
		int mod = totalRows % pageSize;
		if (mod > 0) {
			totalPages++;
		}
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public boolean isFristPage() {
		return isFristPage;
	}

	public void setFristPage(boolean isFristPage) {
		this.isFristPage = isFristPage;
	}

	public boolean isLastPage() {
		return isLastPage;
	}

	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	public int getStartRowPosition() {
		return startRowPosition;
	}

	public void setStartRowPosition(int startRowPosition) {
		this.startRowPosition = startRowPosition;
	}

	public int getEndRowPosition() {
		return endRowPosition;
	}

	public void setEndRowPosition(int endRowPosition) {
		this.endRowPosition = endRowPosition;
	}

	public int getMaxRecords() {
		return maxRecords;
	}

	public void setMaxRecords(int maxRecords) {
		this.maxRecords = maxRecords;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

}
