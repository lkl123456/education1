package com.adks.dubbo.dao.app.news;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.news.Adks_news_user;
import com.adks.dubbo.commons.Page;

@Component
public class NewsUserAppDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_news_user";
	}

	/**
	 * 未读消息数量
	 * 
	 * @param userId
	 * @return
	 */
	public int getNoReadNews(Map<String, Object> map) {

		StringBuffer countsql = new StringBuffer(
				"select count(1)-count(nu.isRead) from (select a.newsId,a.newsTitle,a.newsHtmlAdress,a.createTime from adks_news a where 1=1 ");
		// 起始位置
		if (map != null && map.size() > 0) {
			// 添加查询条件 。。
			if (map.get("gradeIds") != null) {
				countsql.append(" and a.gradeId in (" + map.get("gradeIds") + ")");
			}
			if (map.get("gradeId") != null) {
				countsql.append(" and a.gradeId =" + map.get("gradeId"));
			}

			countsql.append(") as n LEFT JOIN (select b.newsId,b.isRead from adks_news_user b where 1=1");

			if (map.get("gradeIds") != null) {
				countsql.append(" and b.gradeId in (" + map.get("gradeIds") + ")");
			}
			if (map.get("gradeId") != null) {
				countsql.append(" and b.gradeId =" + map.get("gradeId"));
			}
			if (map.get("userId") != null) {
				countsql.append(" and b.userId =" + map.get("userId"));
			}
		}

		countsql.append(") as nu on n.newsId = nu.newsId ");

		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);

		return totalcount;
	}

	/**
	 * 我的班级消息
	 * 
	 * @param gradeId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page<List<Map<String, Object>>> getGradeNewsByUserId(Page<List<Map<String, Object>>> page) {
		Integer offset = page.getStartLimit(); // limit
		StringBuffer sqlbuffer = new StringBuffer("");
		StringBuffer countsql = new StringBuffer("");
		sqlbuffer
				.append("select n.newsId,n.gradeId,n.newsTitle,n.newsHtmlAdress,ifnull(nu.isRead,0) as isRead,n.createTime "
						+ "from (select a.newsId,a.gradeId,a.newsTitle,a.newsHtmlAdress,a.createTime from adks_news a where 1=1  ");
		countsql.append(
				"select count(1) from (select a.newsId,a.newsTitle,a.newsHtmlAdress,a.createTime from adks_news a where 1=1 ");
		// 起始位置
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件 。。
			if (map.get("gradeIds") != null) {
				sqlbuffer.append(" and a.gradeId in (" + map.get("gradeIds") + ")");
				countsql.append(" and a.gradeId in (" + map.get("gradeIds") + ")");
			}
			if (map.get("gradeId") != null) {
				sqlbuffer.append(" and a.gradeId =" + map.get("gradeId"));
				countsql.append(" and a.gradeId =" + map.get("gradeId"));
			}

			sqlbuffer.append(") as n LEFT JOIN (select b.newsId,b.isRead from adks_news_user b where 1=1");
			countsql.append(") as n LEFT JOIN (select b.newsId,b.isRead from adks_news_user b where 1=1");

			if (map.get("gradeIds") != null) {
				sqlbuffer.append(" and b.gradeId in (" + map.get("gradeIds") + ")");
				countsql.append(" and b.gradeId in (" + map.get("gradeIds") + ")");
			}
			if (map.get("gradeId") != null) {
				sqlbuffer.append(" and b.gradeId =" + map.get("gradeId"));
				countsql.append(" and b.gradeId =" + map.get("gradeId"));
			}
			if (map.get("userId") != null) {
				sqlbuffer.append(" and b.userId =" + map.get("userId"));
				countsql.append(" and b.userId =" + map.get("userId"));
			}
		}

		sqlbuffer.append(") as nu on n.newsId = nu.newsId");
		countsql.append(") as nu on n.newsId = nu.newsId");
		// 分页
		sqlbuffer.append(" ORDER BY isRead asc,n.createTime desc limit ").append(offset).append(",")
				.append(page.getPageSize());
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> newsList = mysqlClient.queryForList(sql, new Object[0]);

		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(newsList);
		return page;
	}

	/**
	 * 我的系统消息
	 * 
	 * @param gradeId
	 * @return
	 */
	public Page<List<Map<String, Object>>> getSystemNews(Page<List<Map<String, Object>>> page) {
		Integer offset = page.getStartLimit(); // limit

		StringBuffer sqlbuffer = new StringBuffer("select * from adks_news where 1=1 and sysType=2 ");
		StringBuffer countsql = new StringBuffer("select count(1) from adks_news where 1=1 and sysType=2 ");

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
	 * 获取用户消息详细信息
	 * 
	 * @param newsId
	 * @return
	 */
	public Adks_news_user getNewsInfo(int newId, int userId, int gradeId) {
		String sql = "select id,newsId,userId,gradeId,isRead,createDate from adks_news_user where newsId=" + newId
				+ " and userId =" + userId + " and gradeId=" + gradeId;
		List<Adks_news_user> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_news_user>() {
			@Override
			public Adks_news_user mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_news_user news_user = new Adks_news_user();
				news_user.setId(rs.getInt("id"));
				news_user.setNewsId(rs.getInt("newsId"));
				news_user.setUserId(rs.getInt("userId"));
				news_user.setGradeId(rs.getInt("gradeId"));
				news_user.setIsRead(rs.getInt("isRead"));
				news_user.setCreateDate(rs.getDate("createDate"));

				return news_user;
			}
		});
		if (reslist == null || reslist.size() <= 0)
			return null;
		return reslist.get(0);
	}
}
