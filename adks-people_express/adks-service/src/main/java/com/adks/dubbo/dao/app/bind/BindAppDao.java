package com.adks.dubbo.dao.app.bind;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.user.Adks_user_bind;

@Repository
public class BindAppDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_user_bind";
	}

	public List<Adks_user_bind> getByUserId(int userId) {
		String sql = "select id,userId,remoteUserId,clientType,deviceId,bindType,deviceType,createTime,updateTime "
				+ "from adks_user_bind where userId=" + userId;
		List<Adks_user_bind> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_user_bind>() {
			@Override
			public Adks_user_bind mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_user_bind bind = new Adks_user_bind();
				bind.setId(rs.getInt("id"));
				bind.setUserId(rs.getInt("userId"));
				bind.setRemoteUserId(rs.getString("remoteUserId"));
				bind.setClientType(rs.getInt("clientType"));
				bind.setDeviceId(rs.getString("deviceId"));
				bind.setBindType(rs.getInt("bindType"));
				bind.setDeviceType(rs.getInt("deviceType"));
				bind.setUpdateTime(rs.getDate("updateTime"));

				bind.setCreateTime(rs.getDate("createTime"));
				return bind;
			}
		});
		if (reslist == null || reslist.size() <= 0)
			return null;
		return reslist;
	}

	public void deleteUserByIds(String userIds) {
		String sql = "delete from adks_user_bind where userId in ( " + userIds + " )";
		mysqlClient.update(sql, new Object[] {});
	}

	public void deleteByRemoteUserIdAndUserId(String remoteUserId, int userId) {
		String sql = "delete from adks_user_bind where remoteUserId=" + remoteUserId + " and userId !=" + userId;
		mysqlClient.update(sql, new Object[] {});
	}
}
