package com.adks.dubbo.dao.admin.zhiji;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.zhiji.Adks_rank;
import com.adks.dubbo.commons.Page;

@Component
public class ZhijiDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_rank";
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
	public List<Adks_rank> getZhijisListAll(){
		String sql = "select rankId as id,rankName as name,rankCode,parentId,parentName,orderNum from Adks_rank where isdelete=2 and zhijiId is null order by orderNum";
		List<Adks_rank> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_rank>() {
			@Override
			public Adks_rank mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_rank zhiji=new Adks_rank();
				zhiji.setId(rs.getInt("id"));
				zhiji.setName(rs.getString("name"));
				zhiji.setRankCode(rs.getString("rankCode"));
				zhiji.setParentId(rs.getInt("parentId"));
				zhiji.setParentName(rs.getString("parentName"));
				zhiji.setOrderNum(rs.getInt("orderNum"));
				return zhiji;
			}});
		return reslist;
	}
	
	public List<Map<String,Object>> getZhijisList() {
		String sql = "select rankId,rankName,rankCode,parentId,parentName,orderNum from Adks_rank where isdelete=2 and zhijiId is null order by rankNum";
		List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
		if(reslist == null || reslist.size() <= 0){
			return null;
		}
		return reslist;
	}
	
	public List<Adks_rank> getZhijisListByClass(Integer parentId){
		String sql = "select rankId as id,rankName as name,rankCode,parentId,parentName,orderNum from Adks_rank "
				+ "where isdelete=2 and zhijiId is null and parentId="+parentId+" order by orderNum";
		List<Adks_rank> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_rank>() {
			@Override
			public Adks_rank mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_rank zhiji=new Adks_rank();
				zhiji.setId(rs.getInt("id"));
				zhiji.setName(rs.getString("name"));
				zhiji.setRankCode(rs.getString("rankCode"));
				zhiji.setParentId(rs.getInt("parentId"));
				zhiji.setParentName(rs.getString("parentName"));
				zhiji.setOrderNum(rs.getInt("orderNum"));
				return zhiji;
			}});
		return reslist;
	}
	
	public Adks_rank getZhijiById(Integer rankId){
		String sql = "select rankId,rankName,rankCode,parentId,parentName,orderNum from Adks_rank where isdelete=2 and zhijiId is null and rankId="+rankId;
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
		if(reslist==null || reslist.size()<=0)
			return null;
		return reslist.get(0);
	}
	
	public void deleteZhijiByIds(String ids) {
		String sql = "update Adks_rank set isdelete=1 where rankId in("+ids+")";
		mysqlClient.update(sql, new Object[]{});
		String sql1 = "update Adks_rank set isdelete=1 where zhijiId in("+ids+")";
		mysqlClient.update(sql, new Object[]{});
	}
	
	public Page<List<Map<String, Object>>> getZhijiListPage(Page<List<Map<String, Object>>> page){
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
		StringBuffer sqlbuffer = new StringBuffer(" select rankId,rankName,rankCode,parentId,parentName,orderNum from Adks_rank  where isdelete=2 and zhijiId is null and rankId > 0 ");
		StringBuffer countsql = new StringBuffer("select count(rankId) from Adks_rank where isdelete=2 and zhijiId is null and rankId > 0 ");
		Map map = page.getMap();
		if(map != null && map.size() > 0){
			//添加查询条件 。。
			if(map.get("rankName") != null){
				sqlbuffer.append(" and rankName like '%" + map.get("rankName") + "%'");
				countsql.append(" and rankName like '%" + map.get("rankName") + "%'");
			}
			if(map.get("parentId") != null){
				sqlbuffer.append(" and parentId=" + map.get("parentId"));
				countsql.append(" and parentId=" + map.get("parentId"));
			}
		}
		sqlbuffer.append(" order by orderNum");
		countsql.append(" order by orderNum");
		//分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> userlist = mysqlClient.queryForList(sql, new Object[0]);
		//查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(userlist);
		return page;
	}
	
	public Map<String, Object> getZHijiByName(String name){
		String sql = "select rankId,rankName,rankCode,parentId,parentName,orderNum from Adks_rank  where isdelete=2 and zhijiId is null and"
				+ " rankName='" + name +"' ";
		return mysqlClient.queryForMap(sql, new Object[]{});
	}
	public Map<String, Object> getZhiWuByName(String name){
		String sql = "select rankId,rankName,rankCode,parentId,parentName,orderNum from Adks_rank  where isdelete=2 and zhijiId is not null and"
				+ " rankName='" + name +"' ";
		return mysqlClient.queryForMap(sql, new Object[]{});
	}
}
