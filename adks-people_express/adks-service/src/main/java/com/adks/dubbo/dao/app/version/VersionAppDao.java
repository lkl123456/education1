package com.adks.dubbo.dao.app.version;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.version.Adks_version;

@Component
public class VersionAppDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_version";
	}

	/**
	 * 获取版本信息
	 * 
	 * @param versionCode
	 * @return
	 */
	public Adks_version getVersion() {
		Object[] obj = new Object[] {};
		String sql = " select id,versionCode,apkUrl,createTime from adks_version";
		List<Adks_version> reslist = mysqlClient.query(sql, obj, new RowMapper<Adks_version>() {
			@Override
			public Adks_version mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_version version = new Adks_version();
				version.setId(rs.getInt("id"));
				version.setVersionCode(rs.getString("versionCode"));
				version.setApkUrl(rs.getString("apkUrl"));
				version.setCreateTime(rs.getDate("createTime"));
				return version;
			}
		});
		if (reslist == null || reslist.size() <= 0)
			return null;
		return reslist.get(0);
	}
}
