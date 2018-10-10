package com.adks.dubbo.dao.admin.news;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.api.data.org.Adks_org_config;
import com.adks.dubbo.commons.Page;

@Component
public class NewsDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_news";
	}

	/**
	 * 删除、批量删除
	 * 
	 * @param newsIds
	 */
	public void deleteNewsByIds(String newsIds) {
		String sql = "delete from adks_news where newsId in ( " + newsIds + " )";
		mysqlClient.update(sql, new Object[] {});
	}

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getNewsListPage(Page<List<Map<String, Object>>> page) {
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); // limit
																			// 起始位置
		StringBuffer sqlbuffer = new StringBuffer("select * from adks_news where 1=1 ");
		StringBuffer countsql = new StringBuffer("select count(1) from adks_news where 1=1 ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件 。。
			if (map.get("newsSortId") != null) {
				sqlbuffer.append(" and newsSortId =" + map.get("newsSortId"));
				countsql.append(" and newsSortId =" + map.get("newsSortId"));
			}
			if (map.get("newsTitle") != null) {
				sqlbuffer.append(" and newsTitle like '%" + map.get("newsTitle") + "%'");
				countsql.append(" and newsTitle like '%" + map.get("newsTitle") + "%'");
			}
			if (map.get("orgCode") != null) {
				sqlbuffer.append(" and orgCode like '%" + map.get("orgCode") + "%'");
				countsql.append(" and orgCode like '%" + map.get("orgCode") + "%'");
			}
//			if (map.get("newsSortType") != null) {
//				sqlbuffer.append(" and newsSortType =" + map.get("newsSortType"));
//				countsql.append(" and newsSortType =" + map.get("newsSortType"));
//			}
		}

		// 分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> newsList = mysqlClient.queryForList(sql, new Object[0]);
		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(newsList);
		return page;
	}

	public Map<String, Object> getNewsInfoById(Integer newsId) {
		String sql = " select * from adks_news where 1=1 and newsId=" + newsId;
		return mysqlClient.queryForMap(sql, new Object[] {});
	}

	// 获取最新新闻
	public List<Adks_news> getTopCmsArticleList(Map map) {
		String sql = "select n1.newsId,n1.newsTitle ,n1.newsSortType,n1.newsFocusPic,n1.newsType,n1.newsLink,n1.newsSortId,n1.newsSortName,n1.newsHtmlAdress,n1.orgId,n1.gradeId,n1.creatorId,"
				+ "n1.creatorName,n1.createTime from adks_news n1,adks_news_sort n2 "
				+ "where n1.newsSortId=n2.newsSortId and n1.gradeId is null ";
		if (map.get("orgId") != null && !"".equals(map.get("orgId"))) {
			sql += " and n1.orgId=" + map.get("orgId");
		}
		if (map.get("newsSortType") != null && !"".equals(map.get("newsSortType"))) {
			sql += " and n2.newsSortType=" + map.get("newsSortType");
		}
		sql += " order by n1.createTime desc limit 0," + map.get("num");
		List<Adks_news> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_news>() {
			@Override
			public Adks_news mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_news news = new Adks_news();
				news.setNewsId(rs.getInt("newsId"));
				news.setNewsTitle(rs.getString("newsTitle"));
				news.setNewsFocusPic(rs.getString("newsFocusPic"));
				news.setNewsType(rs.getInt("newsType"));
				news.setNewsLink(rs.getString("newsLink"));
				news.setNewsSortId(rs.getInt("newsSortId"));
				news.setNewsSortName(rs.getString("newsSortName"));
				news.setNewsHtmlAdress(rs.getString("newsHtmlAdress"));
				news.setOrgId(rs.getInt("orgId"));
				news.setGradeId(rs.getInt("gradeId"));
				news.setCreatorId(rs.getInt("creatorId"));
				news.setCreatorName(rs.getString("creatorName"));
				news.setCreateTime(rs.getDate("createTime"));
				news.setNewsSortType(rs.getInt("newsSortType"));
				return news;
			}
		});
		if (reslist == null || reslist.size() <= 0)
			return null;
		return reslist;
	}

	public Adks_news getNewsById(Integer newsId) {
		String sql = "select newsId,newsTitle,newsFocusPic,newsContent,newsType,newsLink,newsSortId,newsSortName,newsSortType,newsHtmlAdress,orgId,gradeId,creatorId,creatorName,createTime,sysType,newsBelong from adks_news where 1=1"
				+ " and newsId=" + newsId;
		List<Adks_news> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_news>() {
			@Override
			public Adks_news mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_news news = new Adks_news();
				news.setNewsId(rs.getInt("newsId"));
				news.setNewsTitle(rs.getString("newsTitle"));
				news.setNewsFocusPic(rs.getString("newsFocusPic"));
				news.setNewsContent(rs.getBytes("newsContent"));
				news.setNewsType(rs.getInt("newsType"));
				news.setNewsLink(rs.getString("newsLink"));
				news.setNewsSortId(rs.getInt("newsSortId"));
				news.setNewsSortName(rs.getString("newsSortName"));
				news.setNewsSortType(rs.getInt("newsSortType"));
				news.setNewsHtmlAdress(rs.getString("newsHtmlAdress"));
				news.setOrgId(rs.getInt("orgId"));
				news.setGradeId(rs.getInt("gradeId"));
				news.setCreatorId(rs.getInt("creatorId"));
				news.setCreatorName(rs.getString("creatorName"));
				news.setCreateTime(rs.getDate("createTime"));
				news.setSysType(rs.getInt("sysType"));
				news.setNewsBelong(rs.getInt("newsBelong"));
				return news;
			}
		});
		if (reslist == null || reslist.size() <= 0)
			return null;
		return reslist.get(0);
	}

	// 前台界面分页
	public Page<List<Adks_news>> getNewsListPageWeb(Page<List<Adks_news>> page) {
		Integer offset = null; // limit 起始位置
		if (page.getCurrentPage() <= 1) {
			offset = 0;
			page.setCurrentPage(1);
		} else {
			offset = (page.getCurrentPage() - 1) * page.getPageSize();
		}
		StringBuffer sqlbuffer = new StringBuffer(
				"select newsId,newsTitle ,newsContent ,newsSortType,newsFocusPic,newsType,newsLink,newsSortId,"
						+ "newsSortName,newsHtmlAdress,orgId,gradeId,creatorId,creatorName,createTime from adks_news where 1=1 ");
		StringBuffer countsql = new StringBuffer("select count(1) from adks_news where 1=1 ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件 。。
			if (map.get("newsSortId") != null) {
				sqlbuffer.append(" and newsSortId=" + map.get("newsSortId"));
				countsql.append(" and newsSortId=" + map.get("newsSortId"));
			}
			if (map.get("newsTitle") != null) {
				sqlbuffer.append(" and newsTitle like '%" + map.get("newsTitle") + "%'");
				countsql.append(" and newsTitle like '%" + map.get("newsTitle") + "%'");
			}
			if (map.get("orgId") != null) {
				sqlbuffer.append(" and orgId=" + map.get("orgId"));
				countsql.append(" and orgId=" + map.get("orgId"));
			}
		}

		// 分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();

		List<Adks_news> newsList = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_news>() {
			@Override
			public Adks_news mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_news news = new Adks_news();
				news.setNewsId(rs.getInt("newsId"));
				news.setNewsTitle(rs.getString("newsTitle"));
				news.setNewsContent(rs.getBytes("newsContent"));
				news.setNewsFocusPic(rs.getString("newsFocusPic"));
				news.setNewsType(rs.getInt("newsType"));
				news.setNewsLink(rs.getString("newsLink"));
				news.setNewsSortId(rs.getInt("newsSortId"));
				news.setNewsSortName(rs.getString("newsSortName"));
				news.setNewsHtmlAdress(rs.getString("newsHtmlAdress"));
				news.setOrgId(rs.getInt("orgId"));
				news.setGradeId(rs.getInt("gradeId"));
				news.setNewsSortType(rs.getInt("newsSortType"));
				news.setCreatorId(rs.getInt("creatorId"));
				news.setCreatorName(rs.getString("creatorName"));
				news.setCreateTime(rs.getDate("createTime"));
				return news;
			}
		});

		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setTotalRecords(totalcount);
		if (totalcount % page.getPageSize() == 0) {
			page.setTotalPage(totalcount / page.getPageSize());
			page.setTotalPages(totalcount / page.getPageSize());
		} else {
			page.setTotalPage(totalcount / page.getPageSize() + 1);
			page.setTotalPages(totalcount / page.getPageSize() + 1);
		}

		page.setRows(newsList);
		return page;
	}

	/**
	 * 
	 * @Title getGradeNoticeListPage
	 * @Description:班级公告分页列表
	 * @author xrl
	 * @Date 2017年4月26日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeNoticeListPage(Page<List<Map<String, Object>>> page) {
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); // limit
																			// 起始位置
		StringBuffer sqlbuffer = new StringBuffer(
				"select newsId,newsTitle,createTime,creatorName,newsContent from adks_news where 1=1 and gradeId is not null");
		StringBuffer countsql = new StringBuffer("select count(1) from adks_news where 1=1 and gradeId is not null");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件 。。
			// gradeId条件
			if (map.get("gradeId") != null) {
				sqlbuffer.append(" and gradeId=" + map.get("gradeId"));
				countsql.append(" and gradeId=" + map.get("gradeId"));
			}
			// 模糊查询
			if (map.get("newsTitle") != null) {
				sqlbuffer.append(" and newsTitle like '%" + map.get("newsTitle") + "%'");
				countsql.append(" and newsTitle like '%" + map.get("newsTitle") + "%'");
			}
			// 若是班主任查询
			if (map.get("userId") != null) {
				sqlbuffer.append(" and gradeId in(select gradeId from adks_grade where headTeacherId="
						+ map.get("userId") + ")");
				countsql.append(" and gradeId in(select gradeId from adks_grade where headTeacherId="
						+ map.get("userId") + ")");
			}
			// 若非班主任且拥有管理权限
			if (map.get("orgCode") != null) {
				sqlbuffer.append(" and gradeId in(select gradeId from adks_grade where orgCode like '%"
						+ map.get("orgCode") + "%')");
				countsql.append(" and gradeId in(select gradeId from adks_grade where orgCode like '%"
						+ map.get("orgCode") + "%')");
			}
		}

		// 分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> newsList = mysqlClient.queryForList(sql, new Object[0]);
		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(newsList);
		return page;
	}

	/**
	 * 
	 * @Title getNewsByGradeId
	 * @Description：根据gradeId获取班级公告
	 * @author xrl
	 * @Date 2017年4月27日
	 * @param gradeId
	 * @return
	 */
	public List<Map<String, Object>> getNewsByGradeId(Integer gradeId) {
		StringBuffer sqlbuffer = new StringBuffer("select * from adks_news where 1=1 and gradeId=" + gradeId);
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> newsList = mysqlClient.queryForList(sql, new Object[0]);
		return newsList;
	}

	/**
	 * 
	 * @Title delGradeNoticeByGradeId
	 * @Description：根据gradeId删除班级公告
	 * @author xrl
	 * @Date 2017年4月27日
	 * @param gradeId
	 */
	public void delGradeNoticeByGradeId(Integer gradeId) {
		String sql = "delete from adks_news where gradeId=" + gradeId;
		mysqlClient.update(sql, new Object[] {});
	}

	/**
	 * 
	 * @Title checkNewsName
	 * @Description：检查班级公告名在该班级中的唯一性
	 * @author xrl
	 * @Date 2017年5月10日
	 * @param map
	 * @return
	 */
	public Map<String, Object> checkNewsName(Map<String, Object> map) {
		Object[] obj = new Object[] {};
		String sql = "select * from adks_news where newsTitle='" + map.get("newsTitle") + "'";
		if (map.get("gradeId") != null) {
			sql += " and gradeId=" + map.get("gradeId");
		}
		if (map.get("orgId") != null) {
			sql += " and orgId=" + map.get("orgId");
		}
		if (map.get("newsId") != null) {
			sql += " and newsId!=" + map.get("newsId");
		}
		return mysqlClient.queryForMap(sql, obj);
	}

	/**
	 * 系统公告分页列表
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getSysNoticeListPage(Page<List<Map<String, Object>>> page) {
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); // limit
																			// 起始位置
		StringBuffer sqlbuffer = new StringBuffer(
				"select newsId,newsTitle,createTime,creatorName,newsContent,newsHtmlAdress from adks_news where 1=1 and sysType = 2 ");
		StringBuffer countsql = new StringBuffer("select count(1) from adks_news where 1=1 and sysType = 2 ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件 。。
			// 模糊查询
			if (map.get("newsTitle") != null) {
				sqlbuffer.append(" and newsTitle like '%" + map.get("newsTitle") + "%'");
				countsql.append(" and newsTitle like '%" + map.get("newsTitle") + "%'");
			}
		}
		// 分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> newsList = mysqlClient.queryForList(sql, new Object[0]);
		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(newsList);
		return page;
	}
}
