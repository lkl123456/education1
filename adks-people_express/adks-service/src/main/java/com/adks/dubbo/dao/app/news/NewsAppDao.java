package com.adks.dubbo.dao.app.news;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.news.Adks_news;

@Repository
public class NewsAppDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_news";
	}

	/**
	 * 获取新闻列表
	 * 
	 * @param authorId
	 * @return
	 */
	public List<Map<String, Object>> getNews(Map<String, Object> map) {
		String sql = "select n.newsId,n.newsTitle,IFNULL(n.newsFocusPic,'') as newsFocusPic,n.newsHtmlAdress,n.createTime "
				+ "FROM adks_news n,adks_news_sort ns where 1=1 and n.newsSortId = ns.newsSortId and n.gradeId is null and n.newsBelong=1 ";
		if (map.get("orgId") != null && !"".equals(map.get("orgId"))) {
			sql += " and n.orgId=" + map.get("orgId");
		}
		if (map.get("newsSortId") != null && !"".equals(map.get("newsSortId"))) {
			sql += " and ns.newsSortId=" + map.get("newsSortId");
		}
		sql += " order by n.createTime desc limit 0," + map.get("num");
		return mysqlClient.queryForList(sql, new Object[0]);
	}

	public Adks_news getById(int id) {
		String sql = "select newsId,newsTitle,newsFocusPic,newsContent,newsType,newsLink,newsSortId,newsSortName,newsHtmlAdress,orgId,gradeId,creatorId,creatorName,createTime from adks_news where 1=1"
				+ " and newsId=" + id;
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
				news.setNewsHtmlAdress(rs.getString("newsHtmlAdress"));
				news.setOrgId(rs.getInt("orgId"));
				news.setGradeId(rs.getInt("gradeId"));
				news.setCreatorId(rs.getInt("creatorId"));
				news.setCreatorName(rs.getString("creatorName"));
				news.setCreateTime(rs.getDate("createTime"));
				return news;
			}
		});
		if (reslist == null || reslist.size() <= 0)
			return null;
		return reslist.get(0);
	}
}
