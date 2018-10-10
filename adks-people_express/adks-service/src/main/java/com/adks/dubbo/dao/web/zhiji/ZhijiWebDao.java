package com.adks.dubbo.dao.web.zhiji;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.zhiji.Adks_rank;

@Component
public class ZhijiWebDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_rank";
	}

	public List<Adks_rank> getZhijiListAll() {
		String sql = "select rankId ,rankName ,rankCode,parentId,parentName,orderNum from Adks_rank where isdelete=2 and zhijiId is null order by orderNum";
		List<Adks_rank> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_rank>() {
			@Override
			public Adks_rank mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_rank zhiji=new Adks_rank();
				zhiji.setRankId(rs.getInt("rankId"));
				zhiji.setRankName(rs.getString("rankName"));
				zhiji.setRankCode(rs.getString("rankCode"));
				zhiji.setParentId(rs.getInt("parentId"));
				zhiji.setParentName(rs.getString("parentName"));
				zhiji.setOrderNum(rs.getInt("orderNum"));
				return zhiji;
			}});
		return reslist;
	}
	
	public Map<String, Object> getZHijiByName(String name) {
		String sql = "select rankId ,rankName ,rankCode,parentId,parentName,orderNum from Adks_rank where isdelete=2 and zhijiId is not null and  rankName='"+name+"'";
		List<Map<String,Object>> list=mysqlClient.queryForList(sql, null);
		if(list==null || list.size()<=0){
			return null;
		}else{
			return list.get(0);
		}
	}
	public List<Adks_rank> getZhijiListByCon(Integer rankId) {
		String sql = "select rankId ,rankName ,rankCode,parentId,parentName,orderNum from Adks_rank where isdelete=2 and zhijiId ="+rankId+" order by orderNum";
		List<Adks_rank> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_rank>() {
			@Override
			public Adks_rank mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_rank zhiji=new Adks_rank();
				zhiji.setRankId(rs.getInt("rankId"));
				zhiji.setRankName(rs.getString("rankName"));
				zhiji.setRankCode(rs.getString("rankCode"));
				zhiji.setParentId(rs.getInt("parentId"));
				zhiji.setParentName(rs.getString("parentName"));
				zhiji.setOrderNum(rs.getInt("orderNum"));
				return zhiji;
			}});
		return reslist;
	}
	
}
