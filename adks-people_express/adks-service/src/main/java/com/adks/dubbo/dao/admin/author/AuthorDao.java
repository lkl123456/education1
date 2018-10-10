package com.adks.dubbo.dao.admin.author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.adks.commons.util.DateUtils;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.author.Adks_author;
import com.adks.dubbo.commons.Page;

@Repository
public class AuthorDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_author";
	}

	public void deleteAuthor(String ids) {
		String sql = "delete from Adks_author where authorId in ( " + ids + " )";
		mysqlClient.update(sql, new Object[] {});
	}

	public Adks_author getAuthorByName(Map<String, Object> map) {
		String sql = "select authorId,authorName,authorDes,authorSex,authorPhoto,authorFirstLetter,orgId,orgName,orgCode,"
				+ "creatorId,creatorName,createTime from Adks_author where authorName='" + map.get("authorName") + "'";
		if (map.get("orgId") != null) {
			sql += " and orgId=" + map.get("orgId");
		}
		if (map.get("authorId") != null) {
			sql += " and authorId!=" + map.get("authorId");
		}
		List<Adks_author> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_author>() {
			@Override
			public Adks_author mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_author author = new Adks_author();
				author.setAuthorId(rs.getInt("authorId"));
				author.setAuthorName(rs.getString("authorName"));
				author.setAuthorDes(rs.getString("authorDes"));
				author.setAuthorSex(rs.getInt("authorSex"));
				author.setAuthorPhoto(rs.getString("authorPhoto"));
				author.setAuthorFirstLetter(rs.getString("authorFirstLetter"));
				author.setOrgCode(rs.getString("orgCode"));
				author.setOrgId(rs.getInt("orgId"));
				author.setOrgName(rs.getString("orgName"));
				author.setCreatorId(rs.getInt("creatorId"));
				author.setCreatorName(rs.getString("creatorName"));
				author.setCreateTime(rs.getDate("createTime"));
				return author;
			}
		});
		if (reslist == null || reslist.size() <= 0)
			return null;
		return reslist.get(0);
	}

	public Adks_author getAuthorById(Integer authorId) {
		String sql = "select authorId,authorName,authorDes,authorSex,authorPhoto,authorFirstLetter,orgId,orgName,orgCode,"
				+ "creatorId,creatorName,createTime from Adks_author where authorId='" + authorId + "'";
		List<Adks_author> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_author>() {
			@Override
			public Adks_author mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_author author = new Adks_author();
				author.setAuthorId(rs.getInt("authorId"));
				author.setAuthorName(rs.getString("authorName"));
				author.setAuthorDes(rs.getString("authorDes"));
				author.setAuthorSex(rs.getInt("authorSex"));
				author.setAuthorPhoto(rs.getString("authorPhoto"));
				author.setAuthorFirstLetter(rs.getString("authorFirstLetter"));
				author.setOrgCode(rs.getString("orgCode"));
				author.setOrgId(rs.getInt("orgId"));
				author.setOrgName(rs.getString("orgName"));
				author.setCreatorId(rs.getInt("creatorId"));
				author.setCreatorName(rs.getString("creatorName"));
				author.setCreateTime(rs.getDate("createTime"));
				return author;
			}
		});
		if (reslist == null || reslist.size() <= 0)
			return null;
		return reslist.get(0);
	}

	public Page<List<Map<String, Object>>> getAuthorListPage(Page<List<Map<String, Object>>> page) {
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); // limit
																			// 起始位置
		StringBuffer sqlbuffer = new StringBuffer(
				" select authorId,authorName,authorDes,authorSex,authorPhoto,authorFirstLetter,orgId,orgName,orgCode,"
						+ "creatorId,creatorName,createTime from Adks_author where 1=1 and authorId > 0 ");
		StringBuffer countsql = new StringBuffer("select count(authorId) from Adks_author where 1=1 and authorId > 0 ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件 。。
			if (map.get("authorName") != null) {
				sqlbuffer.append(" and authorName like '%" + map.get("authorName") + "%'");
				countsql.append(" and authorName like '%" + map.get("authorName") + "%'");
			}
			if (map.get("orgCode") != null) {
				sqlbuffer.append(" and orgCode like '%" + map.get("orgCode") + "%'");
				countsql.append(" and orgCode like '%" + map.get("orgCode") + "%'");
			}
		}

		// 分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> userlist = mysqlClient.queryForList(sql, new Object[0]);
		for (Map<String, Object> map2 : userlist) {
			if (map2.get("createTime") != null) {
				map2.put("createTime",
						DateUtils.getDate2SStr(DateUtils.getStr2LDate(map2.get("createTime").toString())));
			}
		}
		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(userlist);
		return page;
	}

	public boolean courseByauthorId(Integer authorId) {
		boolean falg = false;
		String sql = "select courseId from Adks_course where authorId=" + authorId;
		List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
		if (reslist == null || reslist.size() <= 0) {
			falg = true;
		}
		return falg;
	}

	public void checkCourseNameByAuthor(Integer authorId, String authorName) {
		String sql = "update adks_course set authorName='" + authorName + "' where authorId=" + authorId;
		mysqlClient.update(sql, new Object[] {});
	}

	public List<Adks_author> queryAll() {
		List<Adks_author> list = new ArrayList<>();
		String sql = "SELECT authorId,authorName,authorPhoto,authorSex,authorDes,orgId,createTime FROM adks_author";
		List<Map<String, Object>> queryList = mysqlClient.queryList(sql);
		String createTime = null;
		try {
			Adks_author author = null;
			for (Map<String, Object> map : queryList) {
				author = new Adks_author();
				if (null != map.get("createTime")) {
					createTime = (String) map.get("createTime").toString();
					map.remove("createTime");
					author.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createTime));
				}
				BeanUtils.populate(author, map);
				list.add(author);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
