package com.adks.dubbo.dao.admin.news;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.news.Adks_news_sort;
import com.adks.dubbo.commons.Page;

@Component
public class NewsSortDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_news_sort";
	}

	/**
	 * 删除、批量删除
	 * 
	 * @param newsSortIds
	 */
	public void deleteNewsSortByIds(String newsSortIds) {
		String sql = "delete from adks_news_sort where newsSortId in ( " + newsSortIds + " )";
		mysqlClient.update(sql, new Object[] {});
	}

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getNewsSortListPage(Page<List<Map<String, Object>>> page) {
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); // limit
																			// 起始位置
		StringBuffer sqlbuffer = new StringBuffer("select * from adks_news_sort where 1=1 ");
		StringBuffer countsql = new StringBuffer("select count(1) from adks_news_sort where 1=1 ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件 。。
//			if (map.get("orgId") != null) {
//				sqlbuffer.append(" and orgId =" + map.get("orgId"));
//				countsql.append(" and orgId =" + map.get("orgId"));
//			}
			if (map.get("newsSortName") != null) {
				sqlbuffer.append(" and newsSortName like '%" + map.get("newsSortName") + "%'");
				countsql.append(" and newsSortName like '%" + map.get("newsSortName") + "%'");
			}
//			if (map.get("orgCode") != null) {
//				sqlbuffer.append(" and orgCode like '%" + map.get("orgCode") + "%'");
//				countsql.append(" and orgCode like '%" + map.get("orgCode") + "%'");
//			}
//			if (map.get("newsSortType") != null) {
//				sqlbuffer.append(" and newsSortType =" + map.get("newsSortType"));
//				countsql.append(" and newsSortType =" + map.get("newsSortType"));
//			}
		}

		// 分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> newsSortList = mysqlClient.queryForList(sql, new Object[0]);

		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(newsSortList);
		return page;
	}

	public List<Adks_news_sort> getNewsSortList(Map<String, Object> map) {
		String sql = "select newsSortId as id,newsSortName as name,newsSortType,orgId,orgName,orgCode from adks_news_sort where 1=1 ";
//		if (map.get("orgCode") != null && !"".equals(map.get("orgCode"))) {
//			sql += " and orgCode like '%" + map.get("orgCode") + "%'";
//		}
		sql += " order by createTime desc";
		List<Adks_news_sort> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_news_sort>() {
			@Override
			public Adks_news_sort mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_news_sort newsSort = new Adks_news_sort();
				newsSort.setNewsSortId(rs.getInt("id"));
				newsSort.setId(rs.getInt("id"));
				newsSort.setNewsSortName(rs.getString("name"));
				newsSort.setName(rs.getString("name"));
				newsSort.setNewsSortType(rs.getString("newsSortType"));
				newsSort.setOrgCode(rs.getString("orgCode"));
				newsSort.setOrgId(rs.getInt("orgId"));
				newsSort.setOrgName(rs.getString("orgName"));
				return newsSort;
			}
		});
		if (reslist == null || reslist.size() <= 0)
			return null;
		return reslist;
	}

	public Map<String, Object> getNewsSortById(Integer newsSortId) {
		String sql = "select * from adks_news_sort where 1=1 and newsSortId=" + newsSortId;
		return mysqlClient.queryForMap(sql, new Object[] {});
	}

	public List<Adks_news_sort> getNewsSortListByCon(Map<String, Object> map) {
		String sql = "select newsSortId,newsSortName ,newsSortLocation ,newsSortType,orgId,orgName,orgCode,creatorId,creatorName,createTime from adks_news_sort where 1=1 ";
//		if (map.get("orgId") != null && !"".equals(map.get("orgId"))) {
//			sql += " and orgId=" + map.get("orgId");
//		}
//		if (map.get("orgCode") != null && !"".equals(map.get("orgCode"))) {
//			sql += " and orgCode like '" + map.get("orgCode") + "'";
//		}
		if (map.get("newsSortType") != null && !"".equals(map.get("newsSortType"))) {
			sql += " and newsSortType=" + map.get("newsSortType");
		}
		sql += " order by newsSortLocation";
		List<Adks_news_sort> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_news_sort>() {
			@Override
			public Adks_news_sort mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_news_sort newsSort = new Adks_news_sort();
				newsSort.setNewsSortId(rs.getInt("newsSortId"));
				newsSort.setNewsSortName(rs.getString("newsSortName"));
				newsSort.setNewsSortLocation(rs.getInt("newsSortLocation"));
				newsSort.setNewsSortType(rs.getString("newsSortType"));
				newsSort.setOrgCode(rs.getString("orgCode"));
				newsSort.setOrgId(rs.getInt("orgId"));
				newsSort.setOrgName(rs.getString("orgName"));
				newsSort.setCreatorId(rs.getInt("creatorId"));
				newsSort.setCreatorName(rs.getString("creatorName"));
				newsSort.setCreateTime(rs.getDate("createTime"));
				return newsSort;
			}
		});
		if (reslist == null || reslist.size() <= 0)
			return null;
		return reslist;
	}
}
