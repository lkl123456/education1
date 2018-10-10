package com.adks.dubbo.dao.app.author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.author.Adks_author;

@Repository
public class AuthorAppDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_author";
	}

	/**
	 * 根据讲师id获取讲师信息
	 * 
	 * @param authorId
	 * @return
	 */
	public Adks_author getAuthorById(int authorId) {
		String sql = "select authorId,authorName,authorDes as resume,authorSex,authorPhoto as picPath,authorFirstLetter,orgId,orgName,orgCode,"
				+ "creatorId,creatorName,createTime from Adks_author where authorId=" + authorId;
		List<Adks_author> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_author>() {
			@Override
			public Adks_author mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_author author = new Adks_author();
				author.setAuthorId(rs.getInt("authorId"));
				author.setAuthorName(rs.getString("authorName"));
				author.setAuthorDes(rs.getString("resume"));
				author.setAuthorSex(rs.getInt("authorSex"));
				author.setAuthorPhoto(rs.getString("picPath"));
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
}
